package edu.neu.ccs.cs5004.problem2;

/**
 * An interface that validates a Driver's information against a set of rules.
 */
public interface IValidator {

  Boolean checkAge();

  Boolean checkName();

  Boolean checkBirthdate();

  Boolean checkCountry();

  Boolean checkLicenseIssueDate();

  Boolean checkLicenseExpirationDate();

  Boolean checkVehicleAge();

  Boolean checkOwner();

  Boolean checkVehicleInsurance();

  Boolean isInsuranceExpired();

  Boolean checkDriverHistory();

  Boolean checkVehicleHistory();


}
