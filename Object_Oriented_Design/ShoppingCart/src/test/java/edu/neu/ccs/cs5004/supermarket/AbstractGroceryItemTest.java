package edu.neu.ccs.cs5004.supermarket;

import org.junit.Assert;

public class AbstractGroceryItemTest {

  private AbstractGroceryItem item;
  private Salmon item1;
  private Beer item2;
  private Cheese item3;
  private int productWeight;
  private int minimumAgeToBuy;
  private String productiveManufacturer;
  private String productName;
  private int productPrice;
  private int quantity;


  @org.junit.Before
  public void setUp() {
    this.minimumAgeToBuy = 5;
    this.productiveManufacturer = "Food Inc.";
    this.productName = "Meat";
    this.productPrice = 5;
    this.productWeight = 3;
    this.quantity = 3;
    this.item = new Beer(this.productName, this.productiveManufacturer, this.productPrice,
        this.quantity,
        this.minimumAgeToBuy, this.productWeight);
    this.item1 = new Salmon(this.productName, this.productiveManufacturer, this.productPrice,
        this.quantity,
        this.minimumAgeToBuy, this.productWeight);
    this.item2 = new Beer(this.productName, this.productiveManufacturer, this.productPrice,
        this.quantity,
        this.minimumAgeToBuy, this.productWeight);
    this.item3 = new Cheese(this.productName, this.productiveManufacturer, this.productPrice,
        this.quantity,
        this.minimumAgeToBuy, this.productWeight);
  }

  @org.junit.Test
  public void getProductWeight() {
    Assert.assertEquals(this.productWeight, this.item.getProductWeight(), 0);
    Assert.assertEquals(this.productWeight, this.item1.getProductWeight(), 0);
    Assert.assertEquals(this.productWeight, this.item2.getProductWeight(), 0);
    Assert.assertEquals(this.productWeight, this.item3.getProductWeight(), 0);

  }

  @org.junit.Test
  public void setProductWeight() {
    double set = 1.0;
    this.item.setProductWeight(set);
    this.item1.setProductWeight(set);
    this.item2.setProductWeight(set);
    this.item3.setProductWeight(set);
    Assert.assertEquals(set, this.item.getProductWeight(), 0);
    Assert.assertEquals(set, this.item1.getProductWeight(), 0);
    Assert.assertEquals(set, this.item2.getProductWeight(), 0);
    Assert.assertEquals(set, this.item3.getProductWeight(), 0);
  }
}