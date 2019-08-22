#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <signal.h>


#include "QueryProtocol.h"
#include "MovieSet.h"
#include "MovieIndex.h"
#include "DocIdMap.h"
#include "htll/Hashtable.h"
#include "htll/LinkedList.h"
#include "QueryProcessor.h"
#include "FileParser.h"
#include "FileCrawler.h"

DocIdMap docs;
Index docIndex;

#define BUFFER_SIZE 1000
#define SEARCH_RESULT_LENGTH 1500
char movieSearchResult[SEARCH_RESULT_LENGTH];
int sock_fd;

int Cleanup();

void sigint_handler(int sig) {
  write(0, "Exit signal sent. Cleaning up...\n", 34);
  Cleanup();
  exit(0);
}


void Setup(char *dir) {
  printf("Crawling directory tree starting at: %s\n", dir);
  // Create a DocIdMap
  docs = CreateDocIdMap();
  CrawlFilesToMap(dir, docs);
  printf("Crawled %d files.\n", NumElemsInHashtable(docs));

  // Create the index
  docIndex = CreateIndex();

  // Index the files
  printf("Parsing and indexing files...\n");
  ParseTheFiles(docs, docIndex);
  printf("%d entries in the index.\n", NumElemsInHashtable(docIndex->ht));
}

int Cleanup() {
  close(sock_fd);
  DestroyOffsetIndex(docIndex);
  DestroyDocIdMap(docs);
  return 0;
}

void recieve_message(int client_fd){
  char buffer[1000];
  int len = read(client_fd, buffer, sizeof(buffer) - 1);
  buffer[len] = '\0';
  printf("SERVER RECEIVED: %s \n", buffer); 
}

void send_message(char *msg, int sock_fd){
  write(sock_fd, msg, strlen(msg));
}

int main(int argc, char **argv) {

  // Get args
  if (argc != 3) {
   fprintf(stderr,"usage: talker hostname message\n");
      exit(1);
  }
  
  // Setup graceful exit
  struct sigaction kill;
  char *dir_to_crawl;
  char *port;
  dir_to_crawl = argv[1];
  port = argv[2];
  kill.sa_handler = sigint_handler;
  kill.sa_flags = 0;  // or SA_RESTART
  sigemptyset(&kill.sa_mask);

  if (sigaction(SIGINT, &kill, NULL) == -1) {
    perror("sigaction");
    exit(1);
  }

  Setup(dir_to_crawl);
  int s;
  // Step 1: Get Address stuff
  struct addrinfo hints, *result, *p;
  
  // Setting up the hints struct...
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET;
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_flags = AI_PASSIVE;
  s = getaddrinfo("localhost", port, &hints, &result);
  if (s != 0) {
    fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
    exit(1);
  }
  for(p = result; p != NULL; p = p->ai_next) {
    if ((sock_fd = socket(p->ai_family, p->ai_socktype,
          p->ai_protocol)) == -1) {
      perror("server: socket");
      continue;
    }
  // Step 2: Create the socket

  // Step 3: Bind the socket
    if (bind(sock_fd, result->ai_addr, result->ai_addrlen) != 0) {
          perror("bind()");
     exit(1);
    }
   break;
  }
  if (p == NULL)  {
    fprintf(stderr, "server: failed to bind\n");
    exit(1);
  }

   if (listen(sock_fd, 10) != 0) {
     perror("listen()");
     exit(1);
   }
   
  struct sockaddr_in *result_addr = (struct sockaddr_in *) result->ai_addr;
  printf("Listening on file descriptor %d, port %d\n", sock_fd,
	 ntohs(result_addr->sin_port));
  freeaddrinfo(result);
  
  while (1) {
  printf("Waiting for connection...\n");
  int client_fd = accept(sock_fd, NULL, NULL);
  printf("Connection made: client_fd=%d\n", client_fd);
  if (SendAck(client_fd) != 0) {
    fprintf(stderr, "ACK Not found\n");
    exit(1);
  }
  char buffer[1000];
  int len = read(client_fd, buffer, sizeof(buffer) - 1);
  buffer[len] = '\0';
  printf("SERVER RECEIVED: %s \n", buffer);
  SearchResultIter results = FindMovies(docIndex, buffer);
  if (results == NULL) {
    printf("No results for this term. Please try another.\n");
    SendGoodbye(client_fd);
    close(client_fd);
    
  } else {
  int result_num =  NumResultsInIter(results);
  printf("num of results is %d\n", result_num);
  char num[4] = {};
  sprintf(num, "%d", result_num);
  printf("%s\n", num);
  send_message(num, client_fd);
  char resp[1000];
  len = read(client_fd, resp, 999);
  resp[len] = '\0';
  if (CheckAck(&resp) != 0) {
    fprintf(stderr, "ACK Not found\n");
    exit(1);
  }
  printf("check results\n");
  SearchResult sr = (SearchResult)malloc(sizeof(*sr));
  if (sr == NULL) {
    printf("Couldn't malloc SearchResult in main.c\n");
    close(client_fd);
    Cleanup();
    exit(1);
  }
  SearchResultGet(results, sr);
  if (CopyRowFromFile(sr, docs, &movieSearchResult) != 0) {
    printf(stderr, "failed to get results\n");
    close(client_fd);
    Cleanup();
    exit(1);
  }
  send_message(&movieSearchResult, client_fd);
  while (SearchResultIterHasMore(results) != 0) {
    result =  SearchResultNext(results);
    if (result < 0) {
      printf("error retrieving result\n");
      break;
    }
    len = read(client_fd, resp, 999);
    resp[len] = '\0';
    if (CheckAck(&resp) != 0) {
      fprintf(stderr, "ACK Not found\n");
      exit(1);
    }
    SearchResultGet(results, sr);
    if (CopyRowFromFile(sr, docs,  &movieSearchResult) != 0) {
      printf(stderr, "failed to get results\n");
      close(client_fd);
      Cleanup();
      exit(1);
    }
    send_message(&movieSearchResult, client_fd);
    }
    SendGoodbye(client_fd);
    free(sr);
    DestroySearchResultIter(results);
    close(client_fd);
   }
  }
  close(sock_fd);
  Cleanup();
  return 0;
}
