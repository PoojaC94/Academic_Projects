package edu.neu.ccs.cs5004;


import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArgParser implements IArgParser {
    private static final String EMAIL = "--email";
    private static final String LETTER = "--letter";
    private static final String OUTPUT = "--output-dir";
    private static final String ETEMPLATE = "--email-template";
    private static final String INPUT ="--csv-file";
    private static final String LTEMPLATE = "--letter-template";

  public static List<Object> parseArgs(List<String> args) throws WrongExtensionType, IllegalArgumentException {

    if (!args.get(0).contains("--")) {
      throw new IllegalArgumentException("Argument is invalid");
    }

    boolean isEmail = false;
    boolean isLetter = false;
    String letterTemplateFileName = null;
    String emailTemplate = null;
    String outputPath = null;
    String input = null;

    List<Object> fieldList = new ArrayList<>();

    for (int i = 0; i < args.size(); i++) {
      int next = i + 1;
      String command = args.get(i);
      switch (command) {
        case (EMAIL): {
          isEmail = true;
          break;
        }
        case (LETTER): {
          isLetter = true;
          break;
        }
        case (OUTPUT): {
          if (hasFile(args.get(next))) {
            outputPath = args.get(next);
            i++;
            break;
          } else {
            throw new NullPointerException("file not found");
          }
        }
        case (ETEMPLATE): {
            //check if it contains .txt
            emailTemplate = checkExtension(args.get(next));
            i++;
            break;
        }
        case (INPUT): {
            input = checkExtensionCsv(args.get(next));
            i++;
            break;
        }
        case (LTEMPLATE): {
            //check if it contains .txt
            letterTemplateFileName = checkExtension(args.get(next));
            i++;
            break;
        }
        default:
          throw new IllegalArgumentException("Not valid argument");
      }
    }
    if (input == null || outputPath == null) {
            System.out.println("Error: Input or output is missing. --output-dir <path> accepts the "
                    + "name of a folder, all output is placed in this folder.\nFor input: "
                    + "--csv-file <path> accepts the"
                    + " name of the csv file to process\nExample: --email --email-template email-template"
                    + ".txt --output-dir emails --csv-file customer.csv\n");
            throw new IllegalArgumentException("You must specify input/output");
        }
        if (!isEmail && !isLetter) {
            System.out.println("Error: You must enter either --email or --letter prior to giving\n"
                    + "further input/output data.\nExample: --email --email-template email-template.txt"
                    + " --output-dir emails --csv-file customer.csv\n");
            throw new IllegalArgumentException("Enter either email or letter");
        } else if ((isEmail && emailTemplate == null)
                || (!isEmail && emailTemplate != null)) {
            System.out.println("Error: Make sure --email flag is entered and"
                    + " that --email-template was given. Example:\n"
                    + "       --email --email-template email-template.txt --output-dir\n"
                    + "emails --csv-file customer.csv\n");
            throw new IllegalArgumentException("Email / email template not found");
        } else if ((isLetter && letterTemplateFileName == null)
                || (!isLetter && letterTemplateFileName != null)) {
            System.out.println("Error: Make sure --letter flag and --letter-template are provided.\n"
                    + "Example: --letter --letter-template letter-template.txt --output-dir"
                    + "letters --csv-file customers.csv\n");
            throw new IllegalArgumentException("Letter / letter template not found");
        }

    fieldList.add(input);
    fieldList.add(outputPath);
    fieldList.add(isEmail);
    fieldList.add(isLetter);
    fieldList.add(emailTemplate);
    fieldList.add(letterTemplateFileName);

    return fieldList;
  }

  /**
     * Checks if argument exists.
     * @param nextArg input command
     * @return true if argument exists, false if not.
  */

  private static boolean hasFile(String nextArg) {
    if (nextArg != null && (!nextArg.contains("--"))) {
      return true;
    }
    return false;
  }

  /**
     * checks if the given file name is valid and has correct format(txt)
     * @param args string arguments given as input
     * @return the string if valid.
     * @throws WrongExtensionType if string is not valid.
  */

  private static String checkExtension(String args) throws WrongExtensionType {
      if (hasFile(args)) {
          if (!getFileExtension(args).equals("txt")) {
              throw new WrongExtensionType(String.format("Path %s is not a valid TXT file.", args));
          }
      } else {
          throw new NullPointerException("file not found");
      }

      return args;
  }

  /**
     * checks if the given file name is valid and has correct format(csv)
     * @param args string arguments given as input
     * @return the string if valid.
     * @throws WrongExtensionType if string is not valid.
  */

    private static String checkExtensionCsv(String args) throws WrongExtensionType {
        if (hasFile(args)) {
            if (!getFileExtension(args).equals("csv")) {
                throw new WrongExtensionType(String.format("Path %s is not a valid CSV file.", args));
            }
        } else {
            throw new NullPointerException("file not found");
        }

        return args;
    }

  /**
     * Checks if extension exists.
     * @param fileName file as input
     * @return the extension if valid.
     * @throws WrongExtensionType if file extension not valid.
  */

    private static String getFileExtension(String fileName) throws WrongExtensionType {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            throw new WrongExtensionType(
                    String.format("Path %s does not have a valid file extension.", fileName));
        }
        return (Optional.of(fileName.substring(lastIndex + 1)).get());
    }

}
