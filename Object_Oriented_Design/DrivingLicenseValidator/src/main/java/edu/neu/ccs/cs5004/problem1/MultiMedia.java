package edu.neu.ccs.cs5004.problem1;

import java.util.List;
import java.util.Objects;

public class MultiMedia {

  private String title;
  private int year;
  private List<Director> directorsList;
  private List<Actors> actorsList;
  private int streamingRequest = 0;

  /**
   * Class of multimedia where
   *
   * @param title         is the title.
   * @param year          is year of release.
   * @param directorsList list of directors.
   * @param actorsList    list of actors.
   */

  public MultiMedia(String title,
      int year, List<Director> directorsList,
      List<Actors> actorsList) {

    this.title = title;
    this.year = year;
    this.directorsList = directorsList;
    this.actorsList = actorsList;

  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Accesses static method in Release Year to return correct year format, which is four digits.
   *
   * @return String year  a four digit string year
   */

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public List<Director> getDirectorsList() {
    return directorsList;
  }

  public void setDirectorsList(List<Director> directorsList) {
    this.directorsList = directorsList;
  }

  public List<Actors> getActorsList() {
    return actorsList;
  }

  public void setActorsList(List<Actors> actorsList) {

    this.actorsList = actorsList;
  }

  public int getStreamingRequest() {
    return streamingRequest;
  }

  public void setStreamingRequest(int streamingRequest) {
    this.streamingRequest = streamingRequest;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof MultiMedia)) {
      return false;
    }
    MultiMedia that = (MultiMedia) obj;
    return getYear() == that.getYear()
        && getStreamingRequest() == that.getStreamingRequest()
        && getTitle().equals(that.getTitle())
        && getDirectorsList().equals(that.getDirectorsList())
        && getActorsList().equals(that.getActorsList());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getTitle(), getYear(), getDirectorsList(), getActorsList(), getStreamingRequest());
  }
}