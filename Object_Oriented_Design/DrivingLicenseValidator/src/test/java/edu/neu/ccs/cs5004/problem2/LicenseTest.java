package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LicenseTest {

  private License sampleLicense1;
  private License sampleLicense2;
  private License sampleLicense3;

  @Before
  public void setUp() throws Exception {
    Name sampleName1 = new Name("Jane", "Doe");
    Name sampleName2 = new Name("John", "Doe");
    Address sampleAddress = new Address(101, "Strawberry Street",
        "Houston", "TX", 54321);
    LocalDate sampleBirthdate = LocalDate.of(1990, 5, 21);
    Locale sampleCountry = new Locale("", "US");
    LocalDate sampleIssueDate = LocalDate.of(2010, 4, 20);
    LocalDate sampleExpirationDate = LocalDate.of(2020, 4, 20);

    sampleLicense1 = new License(sampleName1, sampleAddress, 1234567890, sampleBirthdate,
        sampleCountry, sampleIssueDate, sampleExpirationDate);
    sampleLicense2 = new License(sampleName1, sampleAddress, 1234567890, sampleBirthdate,
        sampleCountry, sampleIssueDate, sampleExpirationDate);
    sampleLicense3 = new License(sampleName2, sampleAddress, 1234567890, sampleBirthdate,
        sampleCountry, sampleIssueDate, sampleExpirationDate);
  }

  @Test
  public void getDriverName() {
    Assert.assertEquals(sampleLicense1.getDriverName().toString(), "Jane Doe");
  }

  @Test
  public void getDriverAddress() {
    Assert.assertEquals(sampleLicense1.getDriverAddress().toString(),
        "101 Strawberry Street, Houston, TX, 54321");
  }

  @Test
  public void getLicenseNumber() {
    Assert.assertEquals(sampleLicense1.getLicenseNumber(), 1234567890);
  }

  @Test
  public void getBirthdate() {
    Assert.assertEquals(sampleLicense1.getBirthdate(), LocalDate.of(1990, Month.MAY,
        21));
  }

  @Test
  public void getCountry() {
    Assert.assertEquals(sampleLicense1.getCountry().getDisplayCountry(), "United States");
  }

  @Test
  public void getIssueDate() {
    Assert.assertEquals(sampleLicense1.getIssueDate(), LocalDate.of(2010, Month.APRIL,
        20));
  }

  @Test
  public void getExpirationDate() {
    Assert.assertEquals(sampleLicense1.getExpirationDate(), LocalDate.of(2020, Month.APRIL,
        20));
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleLicense1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleLicense1.equals(sampleLicense2));
  }

  @Test
  public void equalsToTest2() {
    Assert.assertFalse(sampleLicense1.equals(sampleLicense3));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleLicense1.hashCode());
  }
}