package edu.neu.ccs.cs5004.problem1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MediaLibrary implements IMediaLibrary {

  private Map<Integer, MultiMedia> libraryMap;

  /**
   * A class that represents media library.
   *
   * @param libraryMap is hashmap that has alias as key and MultiMedia object as value.
   */

  public MediaLibrary(Map<Integer, MultiMedia> libraryMap) {
    this.libraryMap = libraryMap;
  }

  public Map<Integer, MultiMedia> getLibraryMap() {
    return libraryMap;
  }

  public void setLibraryMap(Map<Integer, MultiMedia> libraryMap) {
    this.libraryMap = libraryMap;
  }


  /**
   * checks for equality.
   *
   * @param obj input object.
   * @return true if equal, false if not.
   */

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof MediaLibrary)) {
      return false;
    }
    MediaLibrary that = (MediaLibrary) obj;
    return Objects.equals(getLibraryMap(), that.getLibraryMap());
  }


  public int hashCode() {
    return Objects.hash(getLibraryMap());
  }

  /**
   * When given a director's name, this method returns the list of movies of the director. It is
   * arranged in order of most recent to least recent movie.
   *
   * @param director name of director.
   * @return list of movies of that director sorted appropriately.
   */

  @Override
  public List<MultiMedia> getMediaByDirector(String director) {
    for (int alias : this.getLibraryMap().keySet()) {
      List<Director> dirList = this.getLibraryMap().get(alias).getDirectorsList();
      for (Director inDir : dirList) {
        if (inDir.getName().equals(director)) {
          return getSortedListOfMedia(inDir.getListOfMedia());
        }
      }
    }
    return null;
  }

  /**
   * This method returns the title of the most streamed multimedia.
   *
   * @return the title in form of string.
   */

  public String returnMostStreamed() {
    List<MultiMedia> streamList = new ArrayList<>();
    for (Integer alias : this.getLibraryMap().keySet()) {
      streamList.add(this.getLibraryMap().get(alias));
    }
    Collections.sort(streamList, (obj1, obj2) -> {
      int stream1 = obj1.getStreamingRequest();
      int stream2 = obj2.getStreamingRequest();
      if (stream1 > stream2) {
        return 1;
      }
      return -1;
    });
    Collections.reverse(streamList);
    return streamList.get(0).getTitle();

  }

  /**
   * For a given alias, it returns the number of times the media asssociated with that alias is
   * streamed.
   *
   * @param alias alias in form of int.
   * @return number of times media is streamed.
   */

  @Override
  public int streamingStatistic(Integer alias) {
    if (!(this.getLibraryMap().containsKey(alias))) {
      System.err.println("wrong alias");
    } else {
      return this.getLibraryMap().get(alias).getStreamingRequest();
    }
    return 0;
  }

  /**
   * For every time a multimedia is asked for playing, request for that is updated
   *
   * @param alias alias of movie/TV series given as input.
   */

  @Override
  public void updateRequest(Integer alias) {
    Map<Integer, MultiMedia> newMap = this.getLibraryMap();
    if (newMap.containsKey(alias)) {
      MultiMedia newRequest = this.getLibraryMap().get(alias);
      newRequest.setStreamingRequest(newRequest.getStreamingRequest() + 1);

      newMap.replace(alias, newRequest);
      this.setLibraryMap(newMap);
      return;
    } else {
      System.err.println("alias not found");
    }
  }

  /**
   * Adds media to library if the alias is not present already.
   *
   * @param inputMedia movie/TV series to be added.
   * @throws DuplicateFoundException if the alias already exists.
   */

  @Override
  public void addMediaToLibrary(MultiMedia inputMedia) throws DuplicateFoundException {
    Map<Integer, MultiMedia> newLibraryMap = this.getLibraryMap();
    Integer alias = inputMedia.hashCode();
    if (this.getLibraryMap().containsKey(alias)) {
      throw new DuplicateFoundException("Alias present! Try again");
    }
    this.updateDirector(newLibraryMap, inputMedia);
    this.updateActor(newLibraryMap, inputMedia);
    newLibraryMap.put(alias, inputMedia);
    this.setLibraryMap(newLibraryMap);
  }

  /**
   * updates medialist of director across library if the director is present in inputMedia.
   *
   * @param mediaLib   media library.
   * @param inputMedia movie/TV series given as input.
   */

  private void updateDirector(Map<Integer, MultiMedia> mediaLib, MultiMedia inputMedia) {
    List<Director> mediaDir = inputMedia.getDirectorsList();
    for (int key : mediaLib.keySet()) {
      MultiMedia media = mediaLib.get(key);
      List<Director> directorList = media.getDirectorsList();
      for (Director director : mediaDir) {
        for (Director director1 : directorList) {
          if (director.getName().equals(director1.getName())) {
            List<MultiMedia> oldMedia = director1.getListOfMedia();
            oldMedia.add(inputMedia);
            director1.setListOfMedia(oldMedia);
          }
        }
      }
      MultiMedia updatedmedia = mediaLib.get(key);
      updatedmedia.setDirectorsList(directorList);
      mediaLib.replace(key, updatedmedia);
    }
  }

  /**
   * updates list of multimedia of actor across library if the actor is present in inputMedia, media
   * to be added to library.
   *
   * @param mediaLib   media library.
   * @param inputMedia media to be added to library.
   */

  private void updateActor(Map<Integer, MultiMedia> mediaLib, MultiMedia inputMedia) {
    List<Actors> mediaAct = inputMedia.getActorsList();
    for (int key : mediaLib.keySet()) {
      MultiMedia media = mediaLib.get(key);
      List<Actors> actorList = media.getActorsList();
      for (Actors actors : mediaAct) {
        for (Actors actor1 : actorList) {
          if (actors.getName().equals(actor1.getName())) {
            List<MultiMedia> oldMedia = actors.getListOfMedia();
            oldMedia.add(inputMedia);
            actors.setListOfMedia(oldMedia);
          }
        }
      }
      MultiMedia updatedMedia = mediaLib.get(key);
      updatedMedia.setActorsList(actorList);
      mediaLib.replace(key, updatedMedia);
    }
  }

  /**
   * Returns a sorted list of multimedia.
   *
   * @param listOfMedia list of movie/Tv series.
   * @return list sorted on basis of newest to oldest.
   */

  private List<MultiMedia> getSortedListOfMedia(List<MultiMedia> listOfMedia) {
    List<MultiMedia> sortMediaList = listOfMedia;
    Collections.sort(sortMediaList, new Comparator<MultiMedia>() {
      public int compare(MultiMedia obj1, MultiMedia obj2) {
        int year1 = obj1.getYear();
        int year2 = obj2.getYear();
        if (year1 == year2) {
          return 0;
        }
        if (year1 > year2) {
          return 1;
        }
        return -1;
      }
    });
    Collections.reverse(sortMediaList);
    return sortMediaList;
  }

}
