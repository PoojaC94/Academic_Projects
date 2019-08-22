package edu.neu.ccs.cs5004.problem1;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractArtistsTest {

  List<MultiMedia> mediaList;
  AbstractArtists testArtist;
  MultiMedia testMedia;
  List<Actors> actorList;
  List<Director> directorList;
  Actors actorTest;
  Director directorTest;

  @Before
  public void setUp() throws Exception {
    mediaList = new ArrayList<>();
    testArtist = new AbstractArtists("Ellie Thompson", mediaList);

    actorList = new ArrayList<>();
    directorList = new ArrayList<>();
    actorTest = new Actors("Jenny May", mediaList);
    directorTest = new Director("John Paul", mediaList);
    actorList.add(actorTest);
    directorList.add(directorTest);
    testMedia = new MultiMedia("Under the Stars", 2019, directorList, actorList);
  }

  @Test
  public void getName() {
    Assert.assertEquals("Ellie Thompson", testArtist.getName());
    Assert.assertNotEquals("Ellie", testArtist.getName());
  }

  @Test
  public void getListOfMedia() {
    Assert.assertEquals(mediaList, testArtist.getListOfMedia());
  }

  @Test
  public void setName() {
    testArtist.setName("Mollie");
    Assert.assertEquals("Mollie", testArtist.getName());
    Assert.assertNotEquals("Ellie Thompson", testArtist.getName());
  }

  @Test
  public void setListOfMedia() {
    Assert.assertEquals(0, testArtist.getListOfMedia().size());
    mediaList.add(testMedia);
    testArtist.setListOfMedia(mediaList);
    Assert.assertEquals(1, testArtist.getListOfMedia().size());
  }
}