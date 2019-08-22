package edu.neu.ccs.cs5004.problem1;

import java.util.List;

public class AbstractArtists {

  private String name;
  private List<MultiMedia> listOfMedia;

  /**
   * Artist class where:
   *
   * @param name        name of artist.
   * @param listOfMedia works of the artist.
   */

  public AbstractArtists(String name,
      List<MultiMedia> listOfMedia) {
    this.name = name;
    this.listOfMedia = listOfMedia;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<MultiMedia> getListOfMedia() {

    return listOfMedia;
  }

  public void setListOfMedia(List<MultiMedia> listOfMedia) {
    this.listOfMedia = listOfMedia;
  }

}
