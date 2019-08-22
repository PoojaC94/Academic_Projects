package edu.neu.ccs.cs5004.supermarket;

import java.util.List;

/**
 * A class that represents a shopping cart object.
 */
interface IShoppingCart {

  /**
   * Given a supermarket inventory and a product, remove it from stock and add it to the shopping
   * cart.
   *
   * @param stock   the inventory list where the product will be fetched from.
   * @param product the product to add to the shopping cart.
   * @throws ExceedsQuantityException if there's no item in stock.
   */
  void addToShoppingCart(List<AbstractProduct> stock, AbstractProduct product)
      throws ExceedsQuantityException;

  /**
   * Given a supermarket product, remove it from the shopping cart.
   *
   * @param stock   the inventory list where the product will be fetched from.
   * @param product the product to remove from the shopping cart.
   */
  void removeFromShoppingCart(List<AbstractProduct> stock, AbstractProduct product);
}
