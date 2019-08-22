package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NonmovingViolationTest {

  private NonmovingViolation sampleNonmovingViolation1;
  private NonmovingViolation sampleNonmovingViolation2;

  @Before
  public void setUp() {
    sampleNonmovingViolation1 = new NonmovingViolation();
    sampleNonmovingViolation2 = new NonmovingViolation();
  }

  @Test
  public void addMovingViolation() {
    Assert.assertEquals(sampleNonmovingViolation1.getListOfViolations().size(), 3);
    sampleNonmovingViolation1.addNonmovingViolation("driving while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertEquals(sampleNonmovingViolation1.getListOfViolations().size(), 4);
  }

  @Test
  public void removeMovingViolation() {
    Assert.assertEquals(sampleNonmovingViolation1.getListOfViolations().size(), 3);
    sampleNonmovingViolation1.addNonmovingViolation("driving while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertEquals(sampleNonmovingViolation1.getListOfViolations().size(), 4);

    sampleNonmovingViolation1.removeNonmovingViolation("paperwork issues");
    Assert.assertEquals(sampleNonmovingViolation1.getListOfViolations().size(), 3);
  }

  @Test
  public void getMovingViolation() {
    System.out.println(sampleNonmovingViolation1.getListOfViolations());
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleNonmovingViolation1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleNonmovingViolation1.equals(sampleNonmovingViolation2));
  }

  @Test
  public void equalsToTest2() {
    sampleNonmovingViolation1.addNonmovingViolation("driving while watching Netflix",
        LocalDate.of(2019, 4, 1));
    Assert.assertFalse(sampleNonmovingViolation1.equals(sampleNonmovingViolation2));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleNonmovingViolation2.hashCode());
  }
}