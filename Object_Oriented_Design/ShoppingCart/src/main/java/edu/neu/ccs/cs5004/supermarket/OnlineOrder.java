package edu.neu.ccs.cs5004.supermarket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A class that handles properties of and states for an online order.
 */
public class OnlineOrder implements ICheckoutOrder, IShoppingCart {

  private Supermarket supermarket;
  private List<AbstractProduct> unavailableItems = new ArrayList<>();
  private List<AbstractProduct> availableItems = new ArrayList<>();
  private List<AbstractProduct> removedItems = new ArrayList<>();

  /**
   * A constructor for a new online order.
   *
   * @param supermarket the supermarket that will place the order
   */
  OnlineOrder(Supermarket supermarket) {
    this.supermarket = supermarket;
  }

  /**
   * Given a customer's age, remove any items from their shopping cart if they don't meet the age
   * requirement for purchasing the item.
   *
   * @param customerAge the age of the customer
   * @param product     the products in the customer's shopping card
   * @return a new shopping cart with any offending items removed
   */
  @Override
  public boolean checkAge(int customerAge, AbstractProduct product) {
    return product.getMinimumAgeToBuy() > customerAge;
  }

  public Supermarket getSupermarket() {
    return supermarket;
  }

  public void setSupermarket(Supermarket supermarket) {
    this.supermarket = supermarket;
  }

  /**
   * A method that takes in a customer's shopping cart and a supermarket's inventory and returns a
   * new shopping cart with items substituted.
   *
   * @param shoppingCart the customer's shopping cart.
   * @return a new shopping cart for the customer with any missing items substituted.
   */

  @Override
  public List<AbstractProduct> substituteProduct(List<AbstractProduct> shoppingCart) {
    List<AbstractProduct> sub = new ArrayList<>(shoppingCart);
    for (AbstractProduct item : sub) {
      System.out.println(item.getClass());
      if (!checkAvailability(item)) {
        System.out.println("Item not available");
        List<AbstractProduct> checkList = this.supermarket.getAllStock();
        for (AbstractProduct product : checkList) {
          System.out.println(product.getClass());
          if (item.getClass() == product.getClass()) {
            System.out.println("Class is same");
            if (product.getProductQuantity() > 0
                && product.getProductPrice() <= item.getProductPrice()) {
              System.out.println("price and quantity match");
              System.out.println(product.getProductQuantity());

              if (product instanceof AbstractGroceryItem && checkWeight((AbstractGroceryItem) item,
                  (AbstractGroceryItem) product)) {

                unavailableItems.add(item);
                shoppingCart.remove(item);
                shoppingCart.add(product);
                break;
              } else if (product instanceof AbstractHouseholdItem && checkUnit(
                  (AbstractHouseholdItem) item,
                  (AbstractHouseholdItem) product)) {
                unavailableItems.add(item);
                shoppingCart.remove(item);
                shoppingCart.add(product);
                break;
              } else {
                System.out.println("Price and quantity don't match");
                unavailableItems.add(item);
                shoppingCart.remove(item);
              }
            } else {
              System.out.println(product.getProductQuantity());
              System.out.println(item.getProductQuantity());
              System.out.println("remove item");
              unavailableItems.add(item);
              shoppingCart.remove(item);
              break;
            }
          }
        }
      }
    }
    return shoppingCart;
  }

  /**
   * A method that checks the weight of original and substitute grocery products and returns true if
   * substitute item weighs more or equal to original.
   *
   * @param item       original item.
   * @param substitute item as substitute.
   * @return true if weight is greater or equal, false if otherwise.
   */

  boolean checkWeight(AbstractGroceryItem item, AbstractGroceryItem substitute) {
    return substitute.getProductWeight() >= item.getProductWeight();
  }

  /**
   * A method that checks the weight of original and substitute household products and returns true
   * if substitute item has more or equal number of units than original.
   *
   * @param item       original item.
   * @param substitute substitute item.
   * @return true if number of units is greater or equal, false otherwise.
   */

  boolean checkUnit(AbstractHouseholdItem item, AbstractHouseholdItem substitute) {
    return substitute.getNumUnits() >= item.getNumUnits();
  }

  /**
   * A method that updates the inventory by giving shopping cart as input.
   *
   * @param shoppingCart the customer's shopping cart.
   */
  @Override
  public void updateInventory(List<AbstractProduct> shoppingCart) {
    for (AbstractProduct item : shoppingCart) {
      if (checkAvailability(item)) {
        if (item instanceof AbstractGroceryItem) {
          this.supermarket.getGroceryStock().remove(item);
          item.setProductQuantity(item.getProductQuantity() - 1);
        }
        if (item instanceof AbstractHouseholdItem) {
          this.supermarket.getHouseholdStock().remove(item);
          item.setProductQuantity(item.getProductQuantity() - 1);
        }
      }
    }
  }

  /**
   * Check availability of product in inventory.
   *
   * @param inputProduct given product.
   * @return true if available, false otherwise.
   */
  @Override
  public boolean checkAvailability(AbstractProduct inputProduct) {
    Set<AbstractProduct> temp = new HashSet<>(
        this.supermarket.getAllStock());  // Remove duplicates from stock list
    return temp.contains(inputProduct) && inputProduct.getProductQuantity() > 0;
  }

  /**
   * A method that prints the receipt out for a customer's online order.
   *
   * @param customer     the customer who will get the receipt
   * @param shoppingCart the customer's shopping cart
   * @return a receipt containing the available, unavailable, removed items and price.
   */
  @Override
  public Receipt printReceipt(Customer customer, List<AbstractProduct> shoppingCart) {
    availableItems.addAll(shoppingCart);
    for (AbstractProduct item : shoppingCart) {
      if (checkAge(customer.getCustomerAge(), item)) {
        removedItems.add(item);
        availableItems.remove(item);
      }
    }
    updateInventory(availableItems);

    Receipt customerReceipt = new Receipt(this.supermarket.getTotalRetailValue(availableItems),
        this.getAvailableItems(),
        this.getRemovedItems(), this.getUnavailableItems());
    List<AbstractProduct> newList = new ArrayList<>();
    customer.setShoppingCart(newList);
    return customerReceipt;
  }


  /**
   * Given a product, check if that type of product in stock and has a quantity greater than zero.
   * If it does, add it to the customer's cart. If it doesn't, print an error message.
   *
   * @param product the product to add to the shopping cart.
   */
  @Override
  public void addToShoppingCart(List<AbstractProduct> shoppingCart, AbstractProduct product) {
    Set<AbstractProduct> temp = new HashSet<>(
        this.supermarket.getAllStock());  // Remove duplicates from stock list
    if (temp.contains(product) && product.getProductQuantity() > 0) {
      shoppingCart.add(product);
    } else {
      System.err.println("This product is not in stock.");
    }
  }

  /**
   * Given a supermarket product, remove it from the customer's shopping cart.
   *
   * @param product the product to remove from the shopping cart.
   */
  @Override
  public void removeFromShoppingCart(List<AbstractProduct> shoppingCart, AbstractProduct product) {
    if (shoppingCart.contains(product) && product.getProductQuantity() > 0) {
      shoppingCart.remove(product);
      this.supermarket.addItemToInventory(product);
    } else {
      System.err.println("This product is not in the cart.");
    }
  }

  /**
   * Return list of out of stock items.
   *
   * @return a list of AbstractProduct.
   */
  private List<AbstractProduct> getUnavailableItems() {
    return unavailableItems;
  }

  /**
   * Returns a list of available items.
   *
   * @return a list of abstractProduct.
   */
  private List<AbstractProduct> getAvailableItems() {
    return availableItems;
  }

  /**
   * Returns list of removed items.
   *
   * @return a list of abstractProducts.
   */
  private List<AbstractProduct> getRemovedItems() {
    return removedItems;
  }
}
