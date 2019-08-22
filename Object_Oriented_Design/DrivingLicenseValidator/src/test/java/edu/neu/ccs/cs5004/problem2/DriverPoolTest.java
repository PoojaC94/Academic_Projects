package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DriverPoolTest {

  private DriverPool driverPool;
  private Driver sampleDriver1;
  private Driver sampleDriver2;

  @Before
  public void setUp() throws Exception {

    Name sampleName1 = new Name("Jane", "Doe");
    Name sampleName2 = new Name("John", "Doe");
    Address sampleAddress = new Address(101, "Strawberry Street",
        "Houston", "TX", 54321);
    LocalDate sampleBirthdate1 = LocalDate.of(1990, 5, 21);
    LocalDate sampleBirthdate2 = LocalDate.of(2005, 1, 16);
    Locale sampleCountry = new Locale("", "United States");
    LocalDate sampleIssueDate = LocalDate.of(2010, 4, 20);
    LocalDate sampleExpirationDate = LocalDate.of(2020, 4, 20);
    Vehicle sampleVehicle = new Vehicle("Ford", "Focus",
        2015, sampleName1);
    MovingViolation sampleMovingViolation = new MovingViolation();
    NonmovingViolation sampleNonmovingViolation = new NonmovingViolation();
    DriverHistory sampleDriverHistory = new DriverHistory(sampleMovingViolation,
        sampleNonmovingViolation);
    Crash sampleCrash = new Crash();
    VehicleHistory sampleVehicleHistory = new VehicleHistory(sampleMovingViolation, sampleCrash);
    License sampleLicense1 = new License(sampleName1, sampleAddress, 1234567890,
        sampleBirthdate1, sampleCountry, sampleIssueDate, sampleExpirationDate);
    License sampleLicense2 = new License(sampleName2, sampleAddress, 1234567890,
        sampleBirthdate2, sampleCountry, sampleIssueDate, sampleExpirationDate);
    Name vehicleOwner = new Name("Jesus", "Christ");
    Name person1 = new Name("Barack", "Obama");
    List<Name> coveredPeople = new ArrayList<>(Arrays.asList(vehicleOwner, person1));
    LocalDate sampleInsuranceExpiration = LocalDate.of(2024, 2, 14);
    VehicleInsurance sampleVehicleInsurance = new VehicleInsurance(vehicleOwner, coveredPeople,
        sampleInsuranceExpiration);
    VehicleInsurance sampleVehicleInsurance2 = new VehicleInsurance(sampleName1, coveredPeople,
        sampleInsuranceExpiration);

    Application sampleApplicant = new Application(sampleName1, sampleBirthdate1, sampleLicense1,
        sampleVehicle, sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance2);
    Validator validator = new Validator(sampleApplicant);
    sampleDriver1 = new Driver(sampleName1, sampleBirthdate1, sampleLicense1, sampleVehicle,
        sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance);
    sampleDriver2 = new Driver(sampleName2, sampleBirthdate2, sampleLicense2, sampleVehicle,
        sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance);

    driverPool = new DriverPool(validator);
  }

  @Test
  public void addDriver1() {
    Assert.assertEquals(driverPool.getAcceptedDrivers().size(), 0);  // Empty to start.
    driverPool.addDriver(sampleDriver1);
    Assert.assertEquals(driverPool.getAcceptedDrivers().size(), 1);  // Now has 1.
  }

  @Test
  public void addDriver2() {
    Assert.assertEquals(driverPool.getAcceptedDrivers().size(), 0);  // Empty to start.
    driverPool.addDriver(sampleDriver1);
    Assert.assertEquals(driverPool.getAcceptedDrivers().size(), 1);  // Now has 1.
    driverPool.addDriver(sampleDriver2);
  }

  @Test
  public void removeDriver() {
  }

  @Test
  public void getAcceptedDrivers() {
  }
}