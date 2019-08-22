package edu.neu.ccs.cs5004.supermarket;

/**
 * A class that represents a shampoo household item.
 */
class Shampoo extends AbstractHouseholdItem {

  /**
   * A constructor for a generic household product.
   *
   * @param productName         the name of the product
   * @param productManufacturer the manufacturer of the product
   * @param productPrice        the price of the product
   * @param productQuantity     the quantity of the product
   * @param minimumAgeToBuy     the minimum age requirement to buy the product
   * @param numUnits            the number of units in a household product package
   */
  Shampoo(String productName, String productManufacturer, int productPrice,
      int productQuantity, int minimumAgeToBuy, int numUnits) {
    super(productName, productManufacturer, productPrice, productQuantity, minimumAgeToBuy,
        numUnits);
  }
}
