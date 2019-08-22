package edu.neu.ccs.cs5004.problem1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReleaseYearTest {
  private ReleaseYear theYear;
  private int year;

  @Before
  public void setUp() throws Exception {
    this.year = 76;
    this.theYear = new ReleaseYear(this.year);
  }

  @Test
  public void getYear() {
    Assert.assertEquals(this.year, this.theYear.getYear());

  }

  @Test
  public void setYear() {
    int newYear = 2000;
    this.theYear.setYear(newYear);
    Assert.assertEquals(newYear, this.theYear.getYear());
  }


}