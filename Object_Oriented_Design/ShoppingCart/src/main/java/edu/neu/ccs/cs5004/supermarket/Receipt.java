package edu.neu.ccs.cs5004.supermarket;

import java.util.List;
import java.util.Objects;

/**
 * Receipt after an order is processed.
 */

public class Receipt {

  private double totalPrice;
  private List availableProducts;
  private List removedProducts;
  private List unavailableProducts;

  /**
   * @param totalPrice          total price calculated.
   * @param availableProducts   list of products that are available and processed
   * @param removedProducts     list of products that are removed from the shopping cart.
   * @param unavailableProducts list of products that are out of stock.
   */

  Receipt(double totalPrice, List availableProducts,
      List removedProducts,
      List unavailableProducts) {

    this.totalPrice = totalPrice;
    this.availableProducts = availableProducts;
    this.removedProducts = removedProducts;
    this.unavailableProducts = unavailableProducts;
  }

  double getTotalPrice() {
    return totalPrice;
  }

  List getReceivedProducts() {
    return availableProducts;
  }

  List getRemovedProducts() {
    return removedProducts;
  }

  List getUnavailableProducts() {
    return unavailableProducts;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Receipt receipt = (Receipt) object;
    return Double.compare(receipt.totalPrice, totalPrice) == 0
        && Objects.equals(availableProducts, receipt.availableProducts)
        && Objects.equals(removedProducts, receipt.removedProducts)
        && Objects.equals(unavailableProducts, receipt.unavailableProducts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalPrice, availableProducts, removedProducts, unavailableProducts);
  }

  @Override
  public String toString() {
    return "Receipt{"
        + "totalPrice="
        + totalPrice
        + ", availableProducts="
        + availableProducts
        + ", removedProducts="
        + removedProducts
        + ", unavailableProducts="
        + unavailableProducts
        + '}';
  }
}
