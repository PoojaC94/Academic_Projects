package edu.neu.ccs.cs5004.problem2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NameTest {

  private Name sampleDriverName1;
  private Name sampleDriverName2;
  private Name sampleDriverName3;

  @Before
  public void setUp() {
    sampleDriverName1 = new Name("John", "Doe");
    sampleDriverName2 = new Name("Jane", "Doe");
    sampleDriverName3 = new Name("John", "Doe");
  }

  @Test
  public void getFirstName() {
    Assert.assertEquals(sampleDriverName1.getFirstName(), "John");
  }

  @Test
  public void getLastName() {
    Assert.assertEquals(sampleDriverName1.getLastName(), "Doe");
  }

  @Test
  public void toStringTest() {
    Assert.assertEquals(sampleDriverName1.toString(), "John Doe");
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleDriverName1.equals(sampleDriverName3));
  }

  @Test
  public void equalsToTest2() {
    Assert.assertFalse(sampleDriverName1.equals(sampleDriverName2));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleDriverName1.hashCode());
  }
}