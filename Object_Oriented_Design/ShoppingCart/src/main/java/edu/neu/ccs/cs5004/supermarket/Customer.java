package edu.neu.ccs.cs5004.supermarket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a customer shopping at a supermarket.
 */
class Customer {

  private String customerName;
  private List<AbstractProduct> shoppingCart;
  private int customerAge;
  private Supermarket supermarket;

  /**
   * A constructor for a customer shopping at a supermarket.
   *
   * @param customerName the name of the customer
   * @param customerAge  the age of the customer
   * @param shoppingCart the customer's shopping card
   * @param supermarket  the supermarket the customer is shopping at
   */
  Customer(String customerName, int customerAge,
      List<AbstractProduct> shoppingCart, Supermarket supermarket) {
    this.customerName = customerName;
    this.customerAge = customerAge;
    this.shoppingCart = shoppingCart;
    this.supermarket = supermarket;
  }

  /**
   * Given a product, add it to the customer's shopping cart.
   *
   * @param product the product to add to the shopping cart
   */
  void addToShoppingCart(AbstractProduct product) {
    Set<AbstractProduct> temp = new HashSet<>(
        this.supermarket.getAllStock());  // Remove duplicates from stock list
    if (temp.contains(product) && product.getProductQuantity() > 0) {
      this.shoppingCart.add(product);
    } else {
      System.err.println("This product is not in stock.");
    }
  }

  /**
   * Given a product, remove it from the customer's shopping cart.
   *
   * @param product the product to remove from the shopping cart
   */
  void removeFromShoppingCart(AbstractProduct product) {
    if (this.shoppingCart.contains(product) && product.getProductQuantity() > 0) {
      this.shoppingCart.remove(product);
      this.supermarket.addItemToInventory(product);
    } else {
      System.err.println("This product is not in the cart.");
    }
  }

  /**
   * After customer has added, do this method.
   *
   * @return a new receipt after fulfilling and processing order.
   */
  Receipt processOrder() {
    OnlineOrder customerOrder = new OnlineOrder(this.supermarket);
    List<AbstractProduct> newOrder = customerOrder.substituteProduct(this.getShoppingCart());
    this.setShoppingCart(newOrder);
    return customerOrder.printReceipt(this, this.getShoppingCart());
  }

  String getCustomerName() {
    return customerName;
  }

  int getCustomerAge() {
    return customerAge;
  }

  List<AbstractProduct> getShoppingCart() {
    return shoppingCart;
  }

  void setShoppingCart(List<AbstractProduct> shoppingCart) {
    this.shoppingCart = shoppingCart;
  }
}
