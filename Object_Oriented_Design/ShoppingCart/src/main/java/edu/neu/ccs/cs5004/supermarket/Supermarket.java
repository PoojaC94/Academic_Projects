package edu.neu.ccs.cs5004.supermarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class that represents a supermarket's inventory.
 */
public class Supermarket implements IInventory {

  private List<AbstractGroceryItem> groceryStock;
  private List<AbstractHouseholdItem> householdStock;

  /**
   * A constructor for a new supermarket inventory.
   *
   * @param groceryStock   the list of grocery stock items
   * @param householdStock the list of household stock items
   */
  Supermarket(List<AbstractGroceryItem> groceryStock,
      List<AbstractHouseholdItem> householdStock) {
    this.groceryStock = groceryStock;
    this.householdStock = householdStock;
  }

  /**
   * A method that prints the simple name of every item in a supermarket stock list.
   *
   * @param anInventory a supermarket inventory list
   * @param <X>         any abstract product in the supermarket
   * @return a list of simple class names of the products
   */
  static <X extends AbstractProduct> List<String> printStock(List<X> anInventory) {
    List<String> newList = new ArrayList<>();
    for (AbstractProduct product : anInventory) {
      newList.add(product.getClass().getSimpleName());
    }
    return newList;
  }

  /**
   * Adds an item to the supermarket's inventory.
   *
   * @param product the type of product to add
   */
  @Override
  public void addItemToInventory(AbstractProduct product) {
    if (product instanceof AbstractGroceryItem) {
      this.groceryStock.add((AbstractGroceryItem) product);
    } else if (product instanceof AbstractHouseholdItem) {
      this.householdStock.add((AbstractHouseholdItem) product);
    }
  }

  /**
   * Removes an item from the supermarket's inventory.
   *
   * @param product the type of product to remove
   */
  @Override
  public void removeItemFromInventory(AbstractProduct product) throws ExceedsQuantityException {
    if (!getQuantityInventory(product)) {
      throw new ExceedsQuantityException();
    } else {
      if (product instanceof AbstractGroceryItem) {
        this.groceryStock.remove(product);
      } else if (product instanceof AbstractHouseholdItem) {
        this.householdStock.remove(product);
      }
    }
  }

  /**
   * Calculates the total retail value of all products in a supermarket inventory.
   *
   * @param productList the list of products in an inventory
   * @return the total value of all the products
   */
  @Override
  public <X extends AbstractProduct> double getTotalRetailValue(List<X> productList) {
    double totalValue = 0;

    for (AbstractProduct product : productList) {
      totalValue += product.calculateTotalPrice();
    }
    return totalValue;
  }

  List<AbstractGroceryItem> getGroceryStock() {
    return groceryStock;
  }

  List<AbstractHouseholdItem> getHouseholdStock() {
    return householdStock;
  }

  List<AbstractProduct> getAllStock() {
    return Stream.of(getGroceryStock(), getHouseholdStock()).flatMap(Collection::stream).collect(
        Collectors.toList());
  }

  /**
   * A helper method that prevents too many items from being removed from the inventory because we
   * cannot have negative amount of items in this universe.
   *
   * @param product the product which is an instance of grocery or household
   * @return boolean true if the inventory size is greater than 0, false otherwise
   */
  Boolean getQuantityInventory(AbstractProduct product) {
    if (product instanceof AbstractGroceryItem) {
      return (this.groceryStock.size() > 0);
    } else if (product instanceof AbstractHouseholdItem) {
      return (this.householdStock.size() > 0);
    }
    return false;
  }
}
