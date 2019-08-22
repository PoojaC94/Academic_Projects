package edu.neu.ccs.cs5004.problem1;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MultiMediaTest {

  List<Actors> actorList;
  List<Director> directorList;
  MultiMedia testMedia;
  Actors actorTest;
  Director directorTest;
  List<MultiMedia> mediaList;

  @Before
  public void setUp() throws Exception {
    mediaList = new ArrayList<>();
    actorList = new ArrayList<>();
    directorList = new ArrayList<>();
    actorTest = new Actors("Jenny May", mediaList);
    directorTest = new Director("John Paul", mediaList);
    actorList.add(actorTest);
    directorList.add(directorTest);
    testMedia = new MultiMedia("Under the Stars", 2019, directorList, actorList);

  }

  @Test
  public void getTitle() {
    Assert.assertEquals("Under the Stars", testMedia.getTitle());
  }

  @Test
  public void setTitle() {
    testMedia.setTitle("Starz");
    Assert.assertEquals("Starz", testMedia.getTitle());
  }

  @Test
  public void getYear() {
    Assert.assertEquals(2019, testMedia.getYear());
  }

  @Test
  public void setYear() {
    Assert.assertEquals(2019, testMedia.getYear());
    testMedia.setYear(2018);
    Assert.assertEquals(2018, testMedia.getYear());
  }

  @Test
  public void getDirectorsList() {
    Assert.assertEquals(directorList, testMedia.getDirectorsList());
  }

  @Test
  public void setDirectorsList() {
    List<Director> directorList1 = new ArrayList<>();
    testMedia.setDirectorsList(directorList1);
    Assert.assertEquals(directorList1, testMedia.getDirectorsList());
  }

  @Test
  public void getActorsList() {
    Assert.assertEquals(actorList, testMedia.getActorsList());
  }

  @Test
  public void setActorsList() {
    List<Actors> actorList1 = new ArrayList<>();
    testMedia.setActorsList(actorList1);
    Assert.assertEquals(actorList1, testMedia.getActorsList());
  }

  @Test
  public void getStreamingRequest() {
    Assert.assertEquals(0, testMedia.getStreamingRequest());
  }

  @Test
  public void setStreamingRequest() {
    testMedia.setStreamingRequest(1);
    Assert.assertEquals(1, testMedia.getStreamingRequest());
  }

}