package edu.neu.ccs.cs5004.supermarket;

import java.util.List;

/**
 * An interface that contains methods for operations performed at store checkout.
 */
interface ICheckoutOrder {

  /**
   * Given a customer's age, remove any items from their shopping cart if they don't meet the age
   * requirement for purchasing the item.
   *
   * @param customerAge the age of the customer
   * @param product     the list of products in the customer's shopping card
   * @return true if the customer meets the age requirement, false otherwise
   */
  boolean checkAge(int customerAge, AbstractProduct product);

  /**
   * A method that takes in a customer's shopping cart and returns a new shopping cart with items
   * substituted.
   *
   * @param shoppingCart the customer's shopping cart
   * @return a new shopping cart with the product substituted
   */
  List<AbstractProduct> substituteProduct(List<AbstractProduct> shoppingCart);

  /**
   * Given a customer's shopping cart update the supermarket's inventory with the items from the
   * shopping cart deducted.
   *
   * @param shoppingCart the customer's shopping cart
   */
  void updateInventory(List<AbstractProduct> shoppingCart);

  /**
   * Given a product, check if each item is available in the supermarket's inventory.
   *
   * @param product the product to check the availability of
   * @return true if the item is in stock, false otherwise
   */
  boolean checkAvailability(AbstractProduct product);

  /**
   * Given a customer's shopping cart, makes a receipt out of it.
   *
   * @param shoppingCart the customer's shopping cart
   * @param customer     the customer who will get the receipt
   * @return a new Receipt object
   */
  Receipt printReceipt(Customer customer, List<AbstractProduct> shoppingCart);
}
