package edu.neu.ccs.cs5004.supermarket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractProductTest {

  private AbstractProduct item;
  private AbstractProduct item2;
  private int productWeight;
  private int minimumAgeToBuy;
  private String productiveManufacturer;
  private String productName;
  private int productPrice;
  private int quantity;

  @Before
  public void setUp() {
    this.minimumAgeToBuy = 21;
    this.productiveManufacturer = "Coors";
    this.productName = "Coors";
    this.productPrice = 3;
    this.productWeight = 1;
    this.quantity = 3;
    this.item = new Beer(this.productName, this.productiveManufacturer, this.productPrice,
        this.quantity,
        this.minimumAgeToBuy, this.productWeight);
    this.item2 = new Beer(this.productName, this.productiveManufacturer, this.productPrice,
        this.quantity,
        this.minimumAgeToBuy, this.productWeight);
  }

  @Test
  public void isAvailable() {
    Assert.assertTrue(this.item.isAvailable(3));
    Assert.assertFalse(this.item.isAvailable(4));
  }

  @Test
  public void decreaseQuantity() {
    System.out.println("Total quantity:");
    System.out.println(this.item.getProductQuantity());
    System.out.println("Decrease quantity by 1:");
    this.item.decreaseQuantity(1);
    System.out.println(this.item.getProductQuantity());
    Assert.assertEquals(2, this.item.getProductQuantity());
    System.out.println("Decrease quantity by 2:");
    this.item.decreaseQuantity(2);
    System.out.println(this.item.getProductQuantity());
    Assert.assertEquals(0, this.item.getProductQuantity());
  }

  @Test
  public void increaseQuantity() {
    System.out.println("Total quantity:");
    System.out.println(this.item.getProductQuantity());
    System.out.println("Increase quantity by 1:");
    this.item.increaseQuantity(1);
    System.out.println(this.item.getProductQuantity());
    Assert.assertEquals(4, this.item.getProductQuantity());
    System.out.println("Increase quantity by 20:");
    this.item.increaseQuantity(4);
    System.out.println(this.item.getProductQuantity());
    Assert.assertEquals(8, this.item.getProductQuantity());
  }

  @Test
  public void calculateTotalPrice() {
    System.out.println("Total quantity:");
    System.out.println(this.item.getProductQuantity());
    System.out.println("Total price:");
    System.out.println(this.item.calculateTotalPrice());
    Assert.assertEquals(9.0, this.item.calculateTotalPrice(), 0);
    System.out.println("Increase quantity by 3:");
    this.item.increaseQuantity(3);
    System.out.println(this.item.getProductQuantity());
    System.out.println("New total price:");
    System.out.println(this.item.calculateTotalPrice());
    Assert.assertEquals(18.0, this.item.calculateTotalPrice(), 0);
  }

  @Test
  public void getProductPrice() {
    Assert.assertEquals(this.productPrice, this.item.getProductPrice(), 0);
  }

  @Test
  public void getProductQuantity() {
    Assert.assertEquals(this.quantity, this.item.getProductQuantity());
  }

  @Test
  public void setProductQuantity() {
    int newQuantity = 5;
    this.item.setProductQuantity(newQuantity);
    Assert.assertEquals(newQuantity, this.item.getProductQuantity());
  }

  @Test
  public void getMinimumAgeToBuy() {
    Assert.assertEquals(this.minimumAgeToBuy, this.item.getMinimumAgeToBuy());
  }

  @Test
  public void setMinimumAgeToBuy() {
    int newAge = 18;
    this.item.setMinimumAgeToBuy(newAge);
    Assert.assertEquals(newAge, this.item.getMinimumAgeToBuy());
  }

  @Test
  public void equals() {
    Assert.assertEquals(this.item,
        new Beer(this.productName, this.productiveManufacturer, this.productPrice, this.quantity,
            this.minimumAgeToBuy, this.productWeight));
  }

  @Test
  public void hashCodeTest() {
    Assert.assertTrue(item.hashCode() == item2.hashCode());
  }

  @Test
  public void toStringTest() {
    System.out.println(this.item);
    Assert.assertEquals(this.item.toString(),
        "AbstractProduct{productName='Coors', productManufacturer='Coors', productPrice=3.0, productQuantity=3, minimumAgeToBuy=21}");
  }
}
