package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CrashTest {

  private Crash sampleListOfCrashes1;
  private Crash sampleListOfCrashes2;
  private Crash sampleListOfCrashes3;

  @Before
  public void setUp() {
    sampleListOfCrashes1 = new Crash();
    sampleListOfCrashes2 = new Crash();
    sampleListOfCrashes3 = new Crash();
  }

  @Test
  public void addCrash() {
    Assert.assertEquals(sampleListOfCrashes1.getListOfCrashes().size(), 3);
    sampleListOfCrashes1.addCrash("crashed while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertEquals(sampleListOfCrashes1.getListOfCrashes().size(), 4);
  }

  @Test
  public void removeCrash() {
    Assert.assertEquals(sampleListOfCrashes1.getListOfCrashes().size(), 3);
    sampleListOfCrashes1.addCrash("crashed while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertEquals(sampleListOfCrashes1.getListOfCrashes().size(), 4);

    sampleListOfCrashes1.removeCrash("fender-bender");
    Assert.assertEquals(sampleListOfCrashes1.getListOfCrashes().size(), 3);
  }

  @Test
  public void getListOfCrashes() {
    System.out.println(sampleListOfCrashes1.getListOfCrashes());
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleListOfCrashes1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleListOfCrashes1.equals(sampleListOfCrashes2));
  }

  @Test
  public void equalsToTest2() {
    sampleListOfCrashes3 = new Crash();
    sampleListOfCrashes3.addCrash("crashed while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertFalse(sampleListOfCrashes1.equals(sampleListOfCrashes3));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleListOfCrashes1.hashCode());
  }
}