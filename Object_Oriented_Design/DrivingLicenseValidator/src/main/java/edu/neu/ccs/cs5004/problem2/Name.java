package edu.neu.ccs.cs5004.problem2;

import java.util.Objects;

/**
 * A simple class that represents the name of a Driver.
 */
class Name {

  private String firstName;
  private String lastName;

  /**
   * A constructor for a new name.
   *
   * @param firstName the first name of the driver
   * @param lastName  the last name of the driver
   */
  Name(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  String getFirstName() {
    return firstName;
  }

  String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return String.format("%s %s", firstName, lastName);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Name name = (Name) object;
    return Objects.equals(firstName, name.firstName)
        && Objects.equals(lastName, name.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }
}
