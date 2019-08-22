package edu.neu.ccs.cs5004.problem2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VehicleTest {

  private Vehicle sampleVehicle1;
  private Vehicle sampleVehicle2;
  private Vehicle sampleVehicle3;

  @Before
  public void setUp() {
    Name sampleVehicleOwner1 = new Name("Billy", "Bob");
    Name sampleVehicleOwner2 = new Name("Joe", "Bob");
    Name sampleVehicleOwner3 = new Name("Joe", "Bob");
    sampleVehicle1 = new Vehicle("Ford", "Focus",
        2015, sampleVehicleOwner1);
    sampleVehicle2 = new Vehicle("Ford", "Focus",
        2015, sampleVehicleOwner2);
    sampleVehicle3 = new Vehicle("Ford", "Focus",
        2015, sampleVehicleOwner3);
  }

  @Test
  public void getVehicleMake() {
    Assert.assertEquals(sampleVehicle1.getVehicleMake(), "Ford");
  }

  @Test
  public void getVehicleModel() {
    Assert.assertEquals(sampleVehicle1.getVehicleModel(), "Focus");
  }

  @Test
  public void getVehicleYear() {
    Assert.assertEquals(sampleVehicle1.getVehicleYear(), 2015);
  }

  @Test
  public void getVehicleOwner() {
    Assert.assertEquals(sampleVehicle1.getVehicleOwner().toString(), "Billy Bob");
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleVehicle1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleVehicle2.equals(sampleVehicle3));
  }

  @Test
  public void equalsToTest2() {
    Assert.assertFalse(sampleVehicle1.equals(sampleVehicle2));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleVehicle1.hashCode());
  }
}