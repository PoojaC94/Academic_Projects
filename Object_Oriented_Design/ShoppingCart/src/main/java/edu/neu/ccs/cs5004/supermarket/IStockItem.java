package edu.neu.ccs.cs5004.supermarket;

/**
 * An interface that represents a product in stock.
 */
interface IStockItem {

  /**
   * A method that checks if there's enough of a product in stock to complete a purchase.
   *
   * @param numItemsToBuy number of products that a customer will buy
   * @return true if there's enough items to buy, false if there isn't
   */
  boolean isAvailable(int numItemsToBuy);

  /**
   * A method that reduces the quantity of product in stock.
   *
   * @param numItems decrease the stock of product by this much
   */
  void decreaseQuantity(int numItems);

  /**
   * A method that raises the quantity of product in stock.
   *
   * @param numItems increase the stock of product by this much
   */
  void increaseQuantity(int numItems);

  /**
   * A method that calculates the price of all of a single type of product in stock.
   *
   * @return the total price of a single type of product
   */
  double calculateTotalPrice();
}
