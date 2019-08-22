package edu.neu.ccs.cs5004.problem1;

public class ReleaseYear {

  private int year;

  public ReleaseYear(int year) {
    this.year = year;

  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

//  /**
//   * A method which takes an integer of length 2 or 4 and returns a correct
//   * 4 digit string of the year.
//   * @param year                    an integer of the year
//   * @return                        a correctly formatted year
//   */
//  static String stringToDate(int year){
//    int now = 20;
//    int fullYear = 4;
//    if (String.valueOf(year).length() == fullYear) {
//      return String.valueOf(year);
//    }
//    String yearFormat = "20" + year;
//    if(year > now) {
//      yearFormat = "19" + year;
//    }
//    return yearFormat;
//  }

}
