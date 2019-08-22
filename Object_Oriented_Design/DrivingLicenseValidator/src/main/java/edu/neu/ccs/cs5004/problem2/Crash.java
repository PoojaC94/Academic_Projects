package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that stores all the different types of crashes.
 */
public class Crash {

  private static final LocalDate CURRENT_DATE = LocalDate.now();
  private HashMap<String, LocalDate> listOfCrashes;

  /**
   * A constructor for a new Vehicle Crash.
   */
  Crash() {
    this.listOfCrashes = new HashMap<>();
    this.listOfCrashes.put("fender-bender", null);
    this.listOfCrashes.put("crash with no bodily injuries", null);
    this.listOfCrashes.put("crash with bodily injuries", null);
  }

  /**
   * A method that adds a new Crash to the hash map of possible Crashes.
   *
   * @param crash       the type of Crash to add
   * @param dateOfCrash the date the Crash occurred, or null if it never occurred
   */
  void addCrash(String crash, LocalDate dateOfCrash) {
    this.listOfCrashes.put(crash.toLowerCase(), dateOfCrash);
  }

  /**
   * A method that removes a new Crash to the list of possible Crashes.
   *
   * @param crash the Crash to remove
   */
  void removeCrash(String crash) {
    this.listOfCrashes.remove(crash.toLowerCase());
  }

  HashMap<String, LocalDate> getListOfCrashes() {
    return listOfCrashes;
  }

  LocalDate getLocalDate() {
    LocalDate newDate = LocalDate.of(1950, 1, 1);
    if (this.listOfCrashes.entrySet().iterator().next().getValue() == null) {
      return newDate;
    }
    return this.listOfCrashes.entrySet().iterator().next().getValue();
  }

  /**
   * A method which returns the most recent crash.
   *
   * @return a local date
   */
  LocalDate mostRecentCrash() {
    return getLocalDate();
  }

  /**
   * A method that calculates the number of months since the most recent crash.
   *
   * @param localDate of the key crash
   * @return an integer of the number of months
   */
  int calculateRecentCrash(LocalDate localDate) {
    return Period.between(mostRecentCrash(), CURRENT_DATE).getMonths();
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (Map.Entry crash : this.listOfCrashes.entrySet()) {
      result.append(crash).append("\n");
    }

    return result.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Crash crash = (Crash) object;
    return Objects.equals(listOfCrashes, crash.listOfCrashes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listOfCrashes);
  }
}
