package edu.neu.ccs.cs5004.problem2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VehicleHistoryTest {

  private VehicleHistory sampleVehicleHistory;

  @Before
  public void setUp() {
    MovingViolation sampleMovingViolation = new MovingViolation();
    Crash sampleCrash = new Crash();

    sampleVehicleHistory = new VehicleHistory(sampleMovingViolation, sampleCrash);
  }

  @Test
  public void getMovingViolation() {
    System.out.println(sampleVehicleHistory.getMovingViolation());
    Assert.assertNotNull(sampleVehicleHistory.getMovingViolation());
  }

  @Test
  public void getCrash() {
    System.out.println(sampleVehicleHistory.getCrash());
    Assert.assertNotNull(sampleVehicleHistory.getCrash());
  }
}