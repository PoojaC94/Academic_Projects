package edu.neu.ccs.cs5004.supermarket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractHouseholdItemTest {

  private PaperTowels paperTowels;
  private String productName;
  private String productManufacturer;
  private int productPrice;
  private int minimumAgeToBuy;
  private int numUnits;
  private int quantity;

  @Before
  public void setUp() {
    this.productName = "Roll";
    this.productManufacturer = "Bounty";
    this.productPrice = 3;
    this.minimumAgeToBuy = 0;
    this.numUnits = 1;
    this.quantity = 2;
    this.paperTowels = new PaperTowels(this.productName, this.productManufacturer,
        this.productPrice,
        this.quantity, this.minimumAgeToBuy, this.numUnits);
  }

  @Test
  public void getNumUnits() {
    Assert.assertEquals(this.numUnits, this.paperTowels.getNumUnits());
  }

  @Test
  public void setNumUnits() {
    int newNum = 3;
    this.paperTowels.setNumUnits(newNum);
    Assert.assertEquals(newNum, this.paperTowels.getNumUnits());
  }
}