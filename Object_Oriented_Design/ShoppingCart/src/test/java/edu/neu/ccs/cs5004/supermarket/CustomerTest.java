package edu.neu.ccs.cs5004.supermarket;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

  private Supermarket myMart;
  private Customer oldLady;
  private AbstractGroceryItem deliciousBeer;
  private AbstractGroceryItem tastyCheese;
  private AbstractGroceryItem pinkSalmon;
  private AbstractHouseholdItem thickPaperTowels;
  private AbstractHouseholdItem fragrantShampoo;

  @Before
  public void setUp() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> oldLadyCart = new ArrayList<>();

    deliciousBeer = new Beer("Super Beer", "Super Industries",
        4, 5, 21, 0.5);
    tastyCheese = new Cheese("Bob's Cheese", "Bob's Grocers",
        2, 8, 0, 0.2);
    pinkSalmon = new Salmon("Sam's Super Salmon", "Sam Smith",
        12, 2, 0, 3);
    fragrantShampoo = new Shampoo("Rose's Shampoo",
        "Rose Pharmaceuticals", 8, 2,
        0, 12);
    thickPaperTowels = new PaperTowels("UltraSoft",
        "Towelie Coporation", 5, 6,
        0, 7);

    myMart = new Supermarket(myMartGroceryStock, myMartHouseholdStock);
    myMart.addItemToInventory(thickPaperTowels);
    myMart.addItemToInventory(fragrantShampoo);
    myMart.addItemToInventory(tastyCheese);
    myMart.addItemToInventory(deliciousBeer);

    oldLady = new Customer("Granny", 75, oldLadyCart, myMart);

    OnlineOrder testOrder = new OnlineOrder(myMart);
  }

  @Test
  public void addToShoppingCart1() {
    Assert.assertEquals(oldLady.getShoppingCart().size(), 0);
    Assert.assertEquals(myMart.getAllStock().size(), 4);

    oldLady.addToShoppingCart(pinkSalmon);

    Assert.assertEquals(oldLady.getShoppingCart().size(), 0);
    Assert.assertEquals(myMart.getAllStock().size(), 4);
  }

  @Test
  public void addToShoppingCart2() {
    Assert.assertEquals(myMart.getAllStock().size(), 4);

    oldLady.addToShoppingCart(thickPaperTowels);

    Assert.assertEquals(oldLady.getShoppingCart().size(), 1);
    Assert.assertEquals(myMart.getAllStock().size(), 4);
  }

  @Test
  public void addToShoppingCart3() {
    Assert.assertEquals(oldLady.getShoppingCart().size(), 0);
    Assert.assertEquals(myMart.getAllStock().size(), 4);

    oldLady.addToShoppingCart(pinkSalmon);
    oldLady.addToShoppingCart(tastyCheese);
    Assert.assertEquals(oldLady.getShoppingCart().size(), 1);
    Assert.assertEquals(myMart.getAllStock().size(), 4);

    oldLady.addToShoppingCart(deliciousBeer);
    Assert.assertEquals(oldLady.getShoppingCart().size(), 2);
    Assert.assertEquals(myMart.getAllStock().size(), 4);
  }

  @Test
  public void removeFromShoppingCart1() {
    Assert.assertEquals(oldLady.getShoppingCart().size(), 0);

    oldLady.addToShoppingCart(pinkSalmon);  // Not in stock
    oldLady.addToShoppingCart(tastyCheese);
    oldLady.addToShoppingCart(deliciousBeer);
    Assert.assertEquals(oldLady.getShoppingCart().size(), 2);

    oldLady.removeFromShoppingCart(pinkSalmon);  // Not in cart
    Assert.assertEquals(oldLady.getShoppingCart().size(), 2);
  }

  @Test
  public void removeFromShoppingCart2() {
    Assert.assertEquals(oldLady.getShoppingCart().size(), 0);

    oldLady.addToShoppingCart(tastyCheese);
    oldLady.addToShoppingCart(deliciousBeer);
    Assert.assertEquals(oldLady.getShoppingCart().size(), 2);

    oldLady.removeFromShoppingCart(deliciousBeer);
    Assert.assertEquals(oldLady.getShoppingCart().size(), 1);

    oldLady.addToShoppingCart(fragrantShampoo);
    Assert.assertEquals(oldLady.getShoppingCart().size(), 2);
  }

  @Test
  public void getCustomerName() {
    Assert.assertEquals(oldLady.getCustomerName(), "Granny");
  }

  @Test
  public void getCustomerAge() {
    Assert.assertEquals(oldLady.getCustomerAge(), 75);
  }

  @Test
  public void getShoppingCart() {
    Assert.assertNotNull(oldLady.getShoppingCart());
  }

  @Test
  public void processOrder() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> oldLadyCart = new ArrayList<>();
    Receipt receipt = new Receipt(0, myMartGroceryStock, myMartHouseholdStock, oldLadyCart);
    Assert.assertEquals(oldLady.processOrder(), receipt);
  }
}

