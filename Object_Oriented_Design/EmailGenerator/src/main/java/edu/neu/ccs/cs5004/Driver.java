package edu.neu.ccs.cs5004;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Driver {

  public static void main(String[] args) throws WrongExtensionType, IOException {
    StringBuilder emailTemplateText, letterTemplateText;
    List<Object> argList = ArgParser.parseArgs(Arrays.asList(args));

    System.out.println(argList);
    Path csvPath = Paths.get(argList.get(0).toString());

    // Parse the table of insurance company members (.csv file) and store the data in a list.
    List<HashMap<String, String>> listOfCustomers = CsvParser.readCsv(csvPath.toString());

    Path output = Paths.get(argList.get(1).toString());

    // If the output directory for the emails doesn't exist, first create it.
    if (!Files.isDirectory(output)) {
      Files.createDirectory(output);
      System.out.printf("Created directory %s.\n", output.getParent());
    }

    // Perform text replacement and writing of files for every customer listed in the CSV file.
    for (HashMap<String, String> customer : listOfCustomers) {
      boolean emailExists = (boolean) argList.get(2);
      if (emailExists) {
        String emailTemplate = argList.get(4).toString();
        if (emailTemplate != null) {
          processTemplate(emailTemplate, customer, output);
        }
      }

      boolean letterExists = (boolean) argList.get(3);
      if (letterExists) {
        String letterTemplate = argList.get(5).toString();
        if (letterTemplate != null) {
          processTemplate(letterTemplate, customer, output);
        }
      }
    }
  }

  public static void processTemplate(String template, HashMap<String, String> customer, Path output) {
    Path templatePath = Paths.get(template);

    // Read the email template (.txt file) and store the text.
    StringBuilder templateText = TxtReader.readTxt(templatePath);

    // Perform text replacement on the email template based on the customer's information.
    String replacedText = TextReplacer.replaceText(templateText, customer);

    // Define a path to a new output text file for this customer.
    String outputPath = String.format("%s/%s_%s.txt", output, customer.get("first_name"), customer.get("last_name"));
    TxtWriter.writeTxt(replacedText, Paths.get(outputPath));

    System.out.printf("Wrote file %s.\n", outputPath);
  }
}
