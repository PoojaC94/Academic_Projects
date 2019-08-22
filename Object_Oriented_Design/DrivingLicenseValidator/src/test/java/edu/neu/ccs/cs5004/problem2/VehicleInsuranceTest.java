package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VehicleInsuranceTest {

  private VehicleInsurance sampleVehicleInsurance;
  private Name person2;

  @Before
  public void setUp() {
    Name vehicleOwner = new Name("Jesus", "Christ");
    Name person1 = new Name("Barack", "Obama");
    person2 = new Name("Donald", "Trump");
    List<Name> coveredPeople = new ArrayList<>(Arrays.asList(vehicleOwner, person1));
    LocalDate sampleInsuranceExpiration = LocalDate.of(2024, 2, 14);

    sampleVehicleInsurance = new VehicleInsurance(vehicleOwner, coveredPeople,
        sampleInsuranceExpiration);
  }

  @Test
  public void addToPolicy() {
    // Before adding Donald Trump to the list of covered people:
    Assert.assertEquals(sampleVehicleInsurance.getCoveredPeople().size(), 2);
    System.out.println(sampleVehicleInsurance.getCoveredPeople());

    sampleVehicleInsurance.addToPolicy(person2);

    // After adding Donald Trump to the list of covered people:
    Assert.assertEquals(sampleVehicleInsurance.getCoveredPeople().size(), 3);
    System.out.println(sampleVehicleInsurance.getCoveredPeople());
  }

  @Test
  public void getVehicleOwner() {
    Assert.assertEquals(sampleVehicleInsurance.getVehicleOwner().toString(), "Jesus Christ");
  }

  @Test
  public void getCoveredPeople() {
    Assert.assertEquals(sampleVehicleInsurance.getCoveredPeople().size(), 2);
  }

  @Test
  public void getInsuranceExpiration() {
    Assert.assertEquals(sampleVehicleInsurance.getInsuranceExpiration(),
        LocalDate.of(2024, Month.FEBRUARY, 14));
  }
}