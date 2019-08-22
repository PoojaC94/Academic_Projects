package edu.neu.ccs.cs5004.supermarket;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupermarketTest {

  private Supermarket mySupermarket;
  private AbstractGroceryItem deliciousBeer;
  private AbstractGroceryItem tastyCheese;
  private AbstractHouseholdItem fragrantShampoo;

  @Before
  public void setUp() {
    List<AbstractGroceryItem> myGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myHouseholdStock = new ArrayList<>();
    mySupermarket = new Supermarket(myGroceryStock, myHouseholdStock);
    deliciousBeer = new Beer("Super Beer", "Super Industries",
        4, 5, 21, 0.5);
    tastyCheese = new Cheese("Bob's Cheese", "Bob's Grocers",
        2, 8, 0, 0.2);
    fragrantShampoo = new Shampoo("Rose's Shampoo",
        "Rose Pharmaceuticals", 8, 2,
        0, 12);
  }

  @Test
  public void addItemToInventory1() {
    mySupermarket.addItemToInventory(deliciousBeer);
    mySupermarket.addItemToInventory(tastyCheese);
    System.out.println(Supermarket.printStock(mySupermarket.getGroceryStock()));
  }

  @Test
  public void addItemToInventory2() {
    mySupermarket.addItemToInventory(fragrantShampoo);
    Assert.assertEquals(mySupermarket.getHouseholdStock().size(), 1);
    System.out.println(Supermarket.printStock(mySupermarket.getHouseholdStock()));
  }

  @Test
  public void removeItemFromInventory1() {
    Assert.assertEquals(mySupermarket.getHouseholdStock().size(), 0);
  }

  @Test
  public void removeItemFromInventory2() {
    mySupermarket.addItemToInventory(deliciousBeer);
    mySupermarket.addItemToInventory(tastyCheese);
    mySupermarket.addItemToInventory(tastyCheese);
    mySupermarket.addItemToInventory(tastyCheese);
    Assert.assertEquals(mySupermarket.getGroceryStock().size(), 4);

    mySupermarket.getGroceryStock().remove(deliciousBeer);
    Assert.assertEquals(mySupermarket.getGroceryStock().size(), 3);
  }

  @Test
  public void removeItemFromInventory3() throws ExceedsQuantityException {
    mySupermarket.addItemToInventory(fragrantShampoo);
    System.out.println(mySupermarket.getHouseholdStock().size());
    mySupermarket.removeItemFromInventory(fragrantShampoo);
    Assert.assertEquals(mySupermarket.getGroceryStock().size(), 0);
  }

  @Test
  public void getTotalRetailValue1() {
    mySupermarket.addItemToInventory(deliciousBeer);
    mySupermarket.addItemToInventory(tastyCheese);
    Assert.assertEquals(mySupermarket.getTotalRetailValue(mySupermarket.getGroceryStock()),
        36.0, 1);
  }

  @Test
  public void getTotalRetailValue2() {
    mySupermarket.addItemToInventory(fragrantShampoo);
    Assert.assertEquals(mySupermarket.getTotalRetailValue(mySupermarket.getHouseholdStock()),
        16.0, 1);
  }

  @Test
  public void getGroceryStock() {
    mySupermarket.addItemToInventory(deliciousBeer);
    Assert.assertNotNull(mySupermarket.getGroceryStock());
    System.out.println(mySupermarket.getGroceryStock());
  }

  @Test
  public void getHouseholdStock() {
    mySupermarket.addItemToInventory(fragrantShampoo);
    Assert.assertNotNull(mySupermarket.getHouseholdStock());
    System.out.println(mySupermarket.getHouseholdStock());
  }

  @Test
  public void getQuantityInventoryTest() {
    Assert.assertFalse(this.mySupermarket.getQuantityInventory(deliciousBeer));
    Assert.assertFalse(this.mySupermarket.getQuantityInventory(fragrantShampoo));
    Assert.assertFalse(this.mySupermarket.getQuantityInventory(tastyCheese));
  }

  @Test(expected = ExceedsQuantityException.class)
  public void TestForException() throws ExceedsQuantityException {
    // size of inventory already @ zero so exceptions are thrown
    this.mySupermarket.removeItemFromInventory(deliciousBeer);
    this.mySupermarket.removeItemFromInventory(fragrantShampoo);
    this.mySupermarket.removeItemFromInventory(tastyCheese);
  }
}