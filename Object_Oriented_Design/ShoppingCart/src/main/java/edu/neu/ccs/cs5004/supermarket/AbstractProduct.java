package edu.neu.ccs.cs5004.supermarket;

import java.util.Objects;

/**
 * A class that represents a generic product sold at a supermarket.
 */

public abstract class AbstractProduct implements IStockItem {

  private String productName;
  private String productManufacturer;
  private double productPrice;
  private int productQuantity;
  private int minimumAgeToBuy;

  /**
   * A constructor for a generic supermarket product.
   *
   * @param productName         the name of the product
   * @param productManufacturer the manufacturer of the product
   * @param productPrice        the price of the product
   * @param productQuantity     the quantity of the product
   * @param minimumAgeToBuy     the minimum age requirement to buy the product
   */
  AbstractProduct(String productName, String productManufacturer, int productPrice,
      int productQuantity, int minimumAgeToBuy) {
    this.productName = productName;
    this.productManufacturer = productManufacturer;
    this.productPrice = productPrice;
    this.productQuantity = productQuantity;
    this.minimumAgeToBuy = minimumAgeToBuy;
  }

  /**
   * A method that checks if there's enough of a product in stock to complete a purchase.
   *
   * @param numItemsToBuy number of products that a customer will buy
   * @return true if there's enough items to buy, false if there isn't
   */
  @Override
  public boolean isAvailable(int numItemsToBuy) {
    return this.getProductQuantity() >= numItemsToBuy;
  }

  /**
   * A method that reduces the quantity of product in stock.
   *
   * @param numItems decrease the stock of product by this much.
   */
  @Override
  public void decreaseQuantity(int numItems) {
    if (this.isAvailable(numItems)) {
      this.setProductQuantity(this.getProductQuantity() - numItems);
    }
  }

  /**
   * A method that raises the quantity of product in stock.
   *
   * @param numItems increase the stock of product by this much.
   */
  @Override
  public void increaseQuantity(int numItems) {
    if (this.isAvailable(numItems)) {
      this.setProductQuantity(this.getProductQuantity() + numItems);
    }
  }

  /**
   * A method that calculates the price of a all of a single type of product in stock.
   *
   * @return the total price of a single type of product.
   */
  @Override
  public double calculateTotalPrice() {
    return getProductQuantity() * getProductPrice();
  }

  public double getProductPrice() {
    return productPrice;
  }

  public int getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }

  public int getMinimumAgeToBuy() {
    return minimumAgeToBuy;
  }

  public void setMinimumAgeToBuy(int minimumAgeToBuy) {
    this.minimumAgeToBuy = minimumAgeToBuy;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    AbstractProduct that = (AbstractProduct) object;
    return Double.compare(that.productPrice, productPrice) == 0
        && productQuantity == that.productQuantity && minimumAgeToBuy == that.minimumAgeToBuy
        && Objects.equals(productName, that.productName)
        && Objects.equals(productManufacturer, that.productManufacturer);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(productName, productManufacturer, productPrice, productQuantity, minimumAgeToBuy);
  }

  @Override
  public String toString() {
    return "AbstractProduct{"
        + "productName='" + productName + '\''
        + ", productManufacturer='"
        + productManufacturer
        + '\''
        + ", productPrice="
        + productPrice
        + ", productQuantity="
        + productQuantity
        + ", minimumAgeToBuy="
        + minimumAgeToBuy
        + '}';
  }
}