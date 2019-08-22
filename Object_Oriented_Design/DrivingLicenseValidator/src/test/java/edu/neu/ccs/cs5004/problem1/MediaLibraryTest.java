package edu.neu.ccs.cs5004.problem1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MediaLibraryTest {

  private List<MultiMedia> movieList1 = new ArrayList();
  private List<Director> directorList1 = new ArrayList();
  private List<Actors> actorList1 = new ArrayList();
  private List<MultiMedia> movieList2 = new ArrayList();
  private Director director1;
  private Actors actor1;
  private MultiMedia movie1;
  private Integer alias1;
  private Map<Integer, MultiMedia> libMap = new HashMap();
  private MediaLibrary testLib = new MediaLibrary(libMap);


  @Before
  public void setUp() throws Exception {

    director1 = new Director("Quentin Tarantino", movieList1);
    actor1 = new Actors("Samuel Jackson", movieList2);
    movie1 = new MultiMedia("Hateful Eight", 2016, directorList1, actorList1);

    List<MultiMedia> list1 = director1.getListOfMedia();
    list1.add(movie1);
    director1.setListOfMedia(list1);
    List<MultiMedia> list2 = actor1.getListOfMedia();
    list2.add(movie1);
    actor1.setListOfMedia(list2);
    List<Director> newDirList = movie1.getDirectorsList();
    newDirList.add(director1);
    movie1.setDirectorsList(newDirList);
    List<Actors> newActorList = movie1.getActorsList();
    newActorList.add(actor1);
    movie1.setActorsList(newActorList);
    alias1 = movie1.hashCode();
    testLib.addMediaToLibrary(movie1);

  }

  @Test
  public void getMediaByDirector() throws DuplicateFoundException {
    Assert.assertEquals("Hateful Eight",
        testLib.getMediaByDirector("Quentin Tarantino").get(0).getTitle());

    List<Director> directorList2 = new ArrayList<>();
    directorList2.add(director1);
    List<Actors> actorList2 = new ArrayList();
    actorList2.add(actor1);

    MultiMedia movie2 = new MultiMedia("Pulp Fiction", 2018, directorList2, actorList2);
    List<MultiMedia> directorUpdate = director1.getListOfMedia();
    directorUpdate.add(movie2);
    director1.setListOfMedia(directorUpdate);
    testLib.addMediaToLibrary(movie2);
    Assert.assertEquals(2, testLib.getLibraryMap().size());
    Assert.assertEquals("Pulp Fiction",
        testLib.getMediaByDirector("Quentin Tarantino").get(0).getTitle());
  }

  @Test
  public void returnMostStreamed() throws DuplicateFoundException {

    MultiMedia movie2 = new MultiMedia("Pulp Fiction", 2018, directorList1, actorList1);
    Integer alias2 = movie2.hashCode();
    testLib.addMediaToLibrary(movie2);
    testLib.updateRequest(alias2);
    testLib.updateRequest(alias2);
    testLib.updateRequest(alias2);
    testLib.updateRequest(alias1);
    testLib.updateRequest(alias1);
    testLib.updateRequest(alias1);
    testLib.updateRequest(alias2);
    testLib.updateRequest(alias2);
    Assert.assertNotEquals("Hateful Eight", testLib.returnMostStreamed());
    Assert.assertEquals("Pulp Fiction", testLib.returnMostStreamed());

  }

  @Test
  public void streamingStatistic() {
    Assert.assertEquals(0, testLib.streamingStatistic(alias1));
    testLib.updateRequest(alias1);
    Assert.assertEquals(1, testLib.streamingStatistic(alias1));
    Assert.assertNotEquals(0, testLib.streamingStatistic(alias1));
  }

  @Test
  public void updateRequest() {
    testLib.updateRequest(alias1);
    Assert.assertEquals(1, testLib.streamingStatistic(alias1));
    Assert.assertNotEquals(0, testLib.streamingStatistic(alias1));

  }

  @Test
  public void addMediaToLibrary() throws DuplicateFoundException {
    directorList1.add(director1);
    actorList1.add(actor1);

    MultiMedia movie2 = new MultiMedia("Pulp Fiction", 1994, directorList1, actorList1);

    testLib.addMediaToLibrary(movie2);

    Assert.assertEquals(2, testLib.getLibraryMap().size());
    Assert.assertNotEquals(1, testLib.getLibraryMap().size());
  }

  @Test(expected = DuplicateFoundException.class)
  public void addMediaToLibraryFail() throws DuplicateFoundException {
    testLib.addMediaToLibrary(movie1);
    Assert.assertEquals(2, testLib.getLibraryMap().size());
  }

}