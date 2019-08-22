package edu.neu.ccs.cs5004.supermarket;

import java.util.List;

/**
 * An interface that contains operations for an inventory.
 */
interface IInventory {

  /**
   * Adds an item to an inventory.
   *
   * @param product the type of product to add
   */
  void addItemToInventory(AbstractProduct product);

  /**
   * Removes a product from an inventory.
   *
   * @param product the type of product to remove
   * @throws ExceedsQuantityException if the product isn't in inventory
   */
  void removeItemFromInventory(AbstractProduct product) throws ExceedsQuantityException;

  /**
   * Calculates the total retail value of all items in am inventory.
   *
   * @param productList the list of products
   * @param <X>         a list of any type of abstract product
   * @return the total value of all the products
   */
  <X extends AbstractProduct> double getTotalRetailValue(List<X> productList);
}