package edu.neu.ccs.cs5004.supermarket;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReceiptTest {

  private Supermarket myMart;
  private Customer youngKid;
  private AbstractGroceryItem deliciousBeer;
  private AbstractGroceryItem tastyCheese;
  private AbstractGroceryItem pinkSalmon;
  private AbstractHouseholdItem thickPaperTowels;
  private AbstractHouseholdItem fragrantShampoo;
  private OnlineOrder testOrder;
  private Receipt receipt;
  private Receipt receipt2;
  private List availableProducts;
  private List unavailableProducts;
  private List removedProducts;
  private int totalPrice;

  @Before
  public void setUp() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> youngKidCart = new ArrayList<>();

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

    totalPrice = 3;

    myMart = new Supermarket(myMartGroceryStock, myMartHouseholdStock);
    myMart.addItemToInventory(thickPaperTowels);
    myMart.addItemToInventory(fragrantShampoo);
    myMart.addItemToInventory(tastyCheese);
    myMart.addItemToInventory(deliciousBeer);
    youngKidCart.add(fragrantShampoo);
    youngKidCart.add(deliciousBeer);
    youngKidCart.add(pinkSalmon);
    youngKid = new Customer("Boy", 17, youngKidCart, myMart);
    testOrder = new OnlineOrder(myMart);
    this.receipt = new Receipt(totalPrice, myMartGroceryStock, myMartHouseholdStock, youngKidCart);
    this.receipt2 = new Receipt(totalPrice, myMartGroceryStock, myMartHouseholdStock, youngKidCart);

  }

  @Test
  public void getTotalPrice() {
    Assert.assertEquals(this.totalPrice, this.receipt.getTotalPrice(), 0);
  }

  @Test
  public void getReceivedProducts() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> youngKidCart = new ArrayList<>();

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

    totalPrice = 3;

    myMart = new Supermarket(myMartGroceryStock, myMartHouseholdStock);
    myMart.addItemToInventory(thickPaperTowels);
    myMart.addItemToInventory(fragrantShampoo);
    myMart.addItemToInventory(tastyCheese);
    myMart.addItemToInventory(deliciousBeer);
    youngKidCart.add(fragrantShampoo);
    youngKidCart.add(deliciousBeer);
    youngKidCart.add(pinkSalmon);
    youngKid = new Customer("Boy", 17, youngKidCart, myMart);
    testOrder = new OnlineOrder(myMart);
    this.receipt = new Receipt(totalPrice, myMartGroceryStock, myMartHouseholdStock, youngKidCart);
    System.out.println(this.receipt.getReceivedProducts());
    Assert.assertEquals(myMartGroceryStock, this.receipt.getReceivedProducts());
  }

  @Test
  public void getRemovedProducts() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> youngKidCart = new ArrayList<>();

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

    totalPrice = 3;

    myMart = new Supermarket(myMartGroceryStock, myMartHouseholdStock);
    myMart.addItemToInventory(thickPaperTowels);
    myMart.addItemToInventory(fragrantShampoo);
    myMart.addItemToInventory(tastyCheese);
    myMart.addItemToInventory(deliciousBeer);
    youngKidCart.add(fragrantShampoo);
    youngKidCart.add(deliciousBeer);
    youngKidCart.add(pinkSalmon);
    youngKid = new Customer("Boy", 17, youngKidCart, myMart);
    testOrder = new OnlineOrder(myMart);
    this.receipt = new Receipt(totalPrice, myMartGroceryStock, myMartHouseholdStock, youngKidCart);
    Assert.assertEquals(myMartHouseholdStock, this.receipt.getRemovedProducts());
  }

  @Test
  public void getUnavailableProducts() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> youngKidCart = new ArrayList<>();

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

    totalPrice = 3;

    myMart = new Supermarket(myMartGroceryStock, myMartHouseholdStock);
    myMart.addItemToInventory(thickPaperTowels);
    myMart.addItemToInventory(fragrantShampoo);
    myMart.addItemToInventory(tastyCheese);
    myMart.addItemToInventory(deliciousBeer);
    youngKidCart.add(fragrantShampoo);
    youngKidCart.add(deliciousBeer);
    youngKidCart.add(pinkSalmon);
    youngKid = new Customer("Boy", 17, youngKidCart, myMart);
    testOrder = new OnlineOrder(myMart);
    this.receipt = new Receipt(totalPrice, myMartGroceryStock, myMartHouseholdStock, youngKidCart);
    System.out.println(this.receipt.getReceivedProducts());
    Assert.assertEquals(youngKidCart, this.receipt.getUnavailableProducts());
  }

  @Test
  public void equals() {
    List<AbstractGroceryItem> myMartGroceryStock = new ArrayList<>();
    List<AbstractHouseholdItem> myMartHouseholdStock = new ArrayList<>();
    List<AbstractProduct> youngKidCart = new ArrayList<>();
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

    totalPrice = 3;

    myMart = new Supermarket(myMartGroceryStock, myMartHouseholdStock);
    myMart.addItemToInventory(thickPaperTowels);
    myMart.addItemToInventory(fragrantShampoo);
    myMart.addItemToInventory(tastyCheese);
    myMart.addItemToInventory(deliciousBeer);
    youngKidCart.add(fragrantShampoo);
    youngKidCart.add(deliciousBeer);
    youngKidCart.add(pinkSalmon);
    youngKid = new Customer("Boy", 17, youngKidCart, myMart);
    testOrder = new OnlineOrder(myMart);
    Assert.assertEquals(this.receipt,
        new Receipt(totalPrice, myMartGroceryStock, myMartHouseholdStock, youngKidCart));
  }

  @Test
  public void hashCodeTest() {
    Assert.assertTrue(receipt.hashCode() == receipt2.hashCode());
  }

  @Test
  public void toStringTest() {
    System.out.println(this.receipt);
    Assert.assertEquals(this.receipt.toString(),
        "Receipt{totalPrice=3.0, availableProducts=[AbstractProduct{productName='Bob's Cheese', productManufacturer='Bob's Grocers', productPrice=2.0, productQuantity=8, minimumAgeToBuy=0}, AbstractProduct{productName='Super Beer', productManufacturer='Super Industries', productPrice=4.0, productQuantity=5, minimumAgeToBuy=21}], removedProducts=[AbstractProduct{productName='UltraSoft', productManufacturer='Towelie Coporation', productPrice=5.0, productQuantity=6, minimumAgeToBuy=0}, AbstractProduct{productName='Rose's Shampoo', productManufacturer='Rose Pharmaceuticals', productPrice=8.0, productQuantity=2, minimumAgeToBuy=0}], unavailableProducts=[AbstractProduct{productName='Rose's Shampoo', productManufacturer='Rose Pharmaceuticals', productPrice=8.0, productQuantity=2, minimumAgeToBuy=0}, AbstractProduct{productName='Super Beer', productManufacturer='Super Industries', productPrice=4.0, productQuantity=5, minimumAgeToBuy=21}, AbstractProduct{productName='Sam's Super Salmon', productManufacturer='Sam Smith', productPrice=12.0, productQuantity=2, minimumAgeToBuy=0}]}");
    Assert.assertNotEquals(this.receipt.toString(), "Hi");
  }
}