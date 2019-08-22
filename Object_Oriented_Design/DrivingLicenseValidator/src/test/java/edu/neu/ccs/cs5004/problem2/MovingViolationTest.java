package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovingViolationTest {

  private MovingViolation sampleMovingViolation1;
  private MovingViolation sampleMovingViolation2;

  @Before
  public void setUp() {
    sampleMovingViolation1 = new MovingViolation();
    sampleMovingViolation2 = new MovingViolation();
  }

  @Test
  public void addMovingViolation() {
    Assert.assertEquals(sampleMovingViolation1.getListOfViolations().size(), 7);
    sampleMovingViolation1.addMovingViolation("driving while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertEquals(sampleMovingViolation1.getListOfViolations().size(), 8);

  }

  @Test
  public void removeMovingViolation() {
    Assert.assertEquals(sampleMovingViolation1.getListOfViolations().size(), 7);
    sampleMovingViolation1.addMovingViolation("driving while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertEquals(sampleMovingViolation1.getListOfViolations().size(), 8);

    sampleMovingViolation1.removeMovingViolation("speeding");
    Assert.assertEquals(sampleMovingViolation1.getListOfViolations().size(), 7);
  }

  @Test
  public void getMovingViolation() {
    System.out.println(sampleMovingViolation1.getListOfViolations());
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleMovingViolation1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleMovingViolation1.equals(sampleMovingViolation2));
  }

  @Test
  public void equalsToTest2() {
    sampleMovingViolation1.addMovingViolation("driving while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertFalse(sampleMovingViolation1.equals(sampleMovingViolation2));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleMovingViolation1.hashCode());
  }
}