#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>

#include "includes/QueryProtocol.h"
//#include "QueryClient.h"

char *port_string = "1500";
unsigned short int port;
char *ip = "127.0.0.1";

#define BUFFER_SIZE 1000


void RunQuery(char *query) {
  struct addrinfo hints, *result;
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET; // IPv4 only 
  hints.ai_socktype = SOCK_STREAM; // TCP
  int s;
  int sock_fd = socket(AF_INET, SOCK_STREAM, 0);
  s = getaddrinfo(ip, port_string, &hints, &result);
   // If I can't get the address, write an error. 
   if (s != 0) {
     fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
     exit(1);
   }
   // Try to connect; if I can't, write an error. 
   if(connect(sock_fd, result->ai_addr, result->ai_addrlen) == -1){
      perror("connect");
      exit(2);
   } else {
      printf("Connection is good!\n");
      char resp[1000];
      int len = read(sock_fd, resp, 999);
      resp[len] = '\0';
      printf("RECEIVED: %s\n", resp);
      if (CheckAck(&resp) != 0) {
        fprintf(stderr, "ACK Not found\n");
	exit(1);
    }
  }
  freeaddrinfo(result);
  send_message(query, sock_fd);
  char resp[1000];
  int len = read(sock_fd, resp, 999);
  SendAck(sock_fd);
  int num_of_result = atoi(resp);
  resp[len] = '\0';
  if (num_of_result == 0) {
    printf("result not found\n");
    len = read(sock_fd, resp, 999);
    resp[len] = '\0';
    
    if (CheckGoodbye(&resp) == 0) {
     close(sock_fd);
     printf("connection closed\n");
    }
    close(sock_fd); 
  } else {
  for (int i = 0; i < num_of_result; i++) {
    read_response(sock_fd);
    SendAck(sock_fd);
  }
  close(sock_fd);
  }
  // Find the address

  // Create the socket

  // Connect to the server

  // Do the query-protocol

  // Close the connection
  
}

void send_message(char *msg, int sock_fd){
 printf("SENDING: %s", msg);
 printf("===\n");
 write(sock_fd, msg, strlen(msg));
}

void read_response(int sock_fd){
 // Response
 char resp[1000];
 int len = read(sock_fd, resp, 999);
 int num_of_result = atoi(resp);
 resp[len] = '\0';
   printf("%s\n", resp);
}

void RunPrompt() {
  char input[BUFFER_SIZE];

  while (1) {
    printf("Enter a term to search for, or q to quit: ");
    scanf("%s", input);
    printf("input was: %s\n", input);
    if (strlen(input) == 1) {
      if (input[0] == 'q') {
        printf("Thanks for playing! \n");
        return;
      }
    }
    printf("\n\n");
    RunQuery(input);
  }
}

int main(int argc, char **argv) {
  if (argc != 3) {
   fprintf(stderr,"usage: talker hostname message\n");
      exit(1);
  }
  printf("assign ports and ip\n");
  port_string = argv[2];
  printf("%s\n", argv[1]);
  printf("IP assigned\n");
  ip = argv[1];
  printf("ports assigned\n");
  RunPrompt();
  return 0;
}
