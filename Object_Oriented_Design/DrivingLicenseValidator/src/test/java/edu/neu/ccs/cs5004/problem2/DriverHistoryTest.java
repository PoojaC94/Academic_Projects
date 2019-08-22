package edu.neu.ccs.cs5004.problem2;

import org.junit.Before;
import org.junit.Test;

public class DriverHistoryTest {

  private DriverHistory sampleDriverHistory;

  @Before
  public void setUp() {
    MovingViolation sampleMovingViolation = new MovingViolation();
    NonmovingViolation sampleNonmovingViolation = new NonmovingViolation();

    sampleDriverHistory = new DriverHistory(sampleMovingViolation, sampleNonmovingViolation);
  }

  @Test
  public void getMovingViolation() {
    System.out.println(sampleDriverHistory.getMovingViolations());
  }

  @Test
  public void getNonmovingViolation() {
    System.out.println(sampleDriverHistory.getNonmovingViolations());
  }
}