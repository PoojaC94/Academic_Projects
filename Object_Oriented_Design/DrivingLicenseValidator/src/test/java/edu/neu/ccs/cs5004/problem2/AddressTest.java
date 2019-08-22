package edu.neu.ccs.cs5004.problem2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressTest {

  private Address sampleAddress1;
  private Address sampleAddress2;
  private Address sampleAddress3;

  @Before
  public void setUp() {
    sampleAddress1 = new Address(123, "4th Avenue",
        "Seattle", "Washington", 56789);
    sampleAddress2 = new Address(123, "4th Avenue",
        "Seattle", "Washington", 56789);
    sampleAddress3 = new Address(007, "Bond Avenue",
        "Seattle", "Washington", 56789);
  }

  @Test
  public void getBuildingNumber() {
    Assert.assertEquals(sampleAddress1.getBuildingNumber(), 123);
  }

  @Test
  public void getStreetName() {
    Assert.assertEquals(sampleAddress1.getStreetName(), "4th Avenue");
  }

  @Test
  public void getCityName() {
    Assert.assertEquals(sampleAddress1.getCityName(), "Seattle");
  }

  @Test
  public void getStateName() {
    Assert.assertEquals(sampleAddress1.getStateName(), "Washington");
  }

  @Test
  public void getCountryName() {
    Assert.assertEquals(sampleAddress1.getPostalCode(), 56789);
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleAddress1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleAddress1.equals(sampleAddress2));
  }

  @Test
  public void equalsToTest2() {
    Assert.assertFalse(sampleAddress1.equals(sampleAddress3));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleAddress1.hashCode());
  }
}