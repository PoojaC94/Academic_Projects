package edu.neu.ccs.cs5004.supermarket;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OnlineOrderTest {

  private Supermarket myMart;
  private Customer youngKid;
  private AbstractGroceryItem deliciousBeer;
  private AbstractGroceryItem tastyCheese;
  private AbstractGroceryItem pinkSalmon;
  private AbstractHouseholdItem thickPaperTowels;
  private AbstractHouseholdItem fragrantShampoo;
  private OnlineOrder testOrder;

  @Before
  public void setUp() throws Exception {
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
  }

  @Test
  public void checkAge() {
    boolean underAge = testOrder.checkAge(youngKid.getCustomerAge(), deliciousBeer);
    Assert.assertEquals(true, underAge);
    boolean notUnderAge = testOrder.checkAge(youngKid.getCustomerAge(), tastyCheese);
    Assert.assertEquals(false, notUnderAge);
  }

  @Test
  public void substituteProduct() {
    AbstractHouseholdItem subTowels = new PaperTowels("UltraSoftie",
        "Towelie Coporation", 5, 6,
        0, 6);
    AbstractHouseholdItem fragrantShampoo1 = new Shampoo("Rose's Shampoo",
        "Rose Pharmaceuticals", 4, 0,
        0, 12);
    AbstractHouseholdItem fragrantShampoo2 = new Shampoo("Rose's Shampoo",
        "Rose Pharmaceuticals", 4, 5,
        0, 12);
    AbstractHouseholdItem subTowels1 = new PaperTowels("UltraSoftie",
        "Towelie Coporation", 5, 0,
        0, 6);

    Supermarket newInventory = testOrder.getSupermarket();
    newInventory.addItemToInventory(subTowels);
    List<AbstractProduct> newList = youngKid.getShoppingCart();
    newInventory.addItemToInventory(fragrantShampoo2);
    newList.add(fragrantShampoo1);
    newList.add(subTowels1);
    youngKid.setShoppingCart(newList);
    testOrder.setSupermarket(newInventory);

    Assert.assertEquals(5, youngKid.getShoppingCart().size());
    List<AbstractProduct> newList1 = testOrder.substituteProduct(youngKid.getShoppingCart());
    youngKid.setShoppingCart(newList1);
    Assert.assertEquals(4, youngKid.getShoppingCart().size());


  }

  @Test
  public void checkWeight() {
    AbstractGroceryItem pinkSalmon1 = new Salmon("Sam's Super Salmon", "Sam Smith",
        12, 2, 0, 3);
    AbstractGroceryItem pinkSalmon2 = new Salmon("Sam's Super Salmon", "Sam Smith",
        12, 2, 0, 2);
    Assert.assertEquals(false, testOrder.checkWeight(pinkSalmon1, pinkSalmon2));
    Assert.assertEquals(true, testOrder.checkWeight(pinkSalmon2, pinkSalmon1));

  }

  @Test
  public void checkUnit() {
    AbstractHouseholdItem thickPaperTowels1 = new PaperTowels("UltraSoft",
        "Towelie Coporation", 5, 6,
        0, 7);
    AbstractHouseholdItem thickPaperTowels2 = new PaperTowels("UltraSoft",
        "Towelie Coporation", 5, 6,
        0, 5);
    Assert.assertEquals(false, testOrder.checkUnit(thickPaperTowels1, thickPaperTowels2));
    Assert.assertEquals(true, testOrder.checkUnit(thickPaperTowels2, thickPaperTowels1));
  }

  @Test
  public void updateInventory() {
    testOrder.updateInventory(youngKid.getShoppingCart());
    for (AbstractProduct item : testOrder.getSupermarket().getAllStock()) {
      if (item instanceof Salmon) {
        Assert.assertEquals(1, item.getProductQuantity());
        break;
      } else if (item instanceof Shampoo) {
        Assert.assertNotEquals(2, item.getProductQuantity());
      }
    }
  }

  @Test
  public void checkAvailability() {
    AbstractHouseholdItem fragrantShampooTest = new Shampoo("Antidandruff Shampoo",
        "Nivea Pharmaceuticals", 8, 2,
        0, 12);
    Assert.assertEquals(false, testOrder.checkAvailability(fragrantShampooTest));
    Assert.assertEquals(true, testOrder.checkAvailability(fragrantShampoo));

  }

  @Test
  public void printReceipt() {
    Receipt testReceipt = testOrder.printReceipt(youngKid, youngKid.getShoppingCart());
    Assert.assertEquals(1, testReceipt.getRemovedProducts().size());
    Assert.assertEquals(deliciousBeer, testReceipt.getRemovedProducts().get(0));
    Assert.assertEquals(32, testReceipt.getTotalPrice(), 0.5);
    System.out.println("Crazy");
    System.out.println(testOrder.printReceipt(youngKid, youngKid.getShoppingCart()));
  }


  @Test
  public void removeFromShoppingCart() throws ExceedsQuantityException {
    AbstractHouseholdItem subTowels = new PaperTowels("UltraSoftie",
        "Towelie Coporation", 5, 6,
        0, 6);
    List<AbstractProduct> shopping = youngKid.getShoppingCart();
    Assert.assertEquals(3, youngKid.getShoppingCart().size());
    testOrder.removeFromShoppingCart(shopping, subTowels);
    youngKid.setShoppingCart(shopping);
    Assert.assertEquals(3, youngKid.getShoppingCart().size());
    testOrder.removeFromShoppingCart(shopping, fragrantShampoo);
    youngKid.setShoppingCart(shopping);
    Assert.assertEquals(2, youngKid.getShoppingCart().size());
  }


  @Test
  public void addToShoppingCart() throws ExceedsQuantityException {
    AbstractHouseholdItem subTowels = new PaperTowels("UltraSoftie",
        "Towelie Coporation", 5, 6,
        0, 6);
    List<AbstractProduct> shopping = youngKid.getShoppingCart();
    Assert.assertEquals(3, youngKid.getShoppingCart().size());
    testOrder.addToShoppingCart(shopping, subTowels);
    youngKid.setShoppingCart(shopping);
    testOrder.addToShoppingCart(shopping, fragrantShampoo);
    youngKid.setShoppingCart(shopping);
    Assert.assertEquals(4, youngKid.getShoppingCart().size());

  }
}