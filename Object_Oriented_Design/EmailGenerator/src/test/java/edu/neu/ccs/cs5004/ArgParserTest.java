package edu.neu.ccs.cs5004;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ArgParserTest {
    ArgParser testArg = new ArgParser();

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidArgParsing() throws WrongExtensionType {
        List<String> args = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            args.add("");
        }
        testArg.parseArgs(args);
    }

    @Test
    public void testParser() throws WrongExtensionType {
        String[] args = {"--email", "--email-template", "email-template.txt",
                "--output-dir", "emails", "--csv-file", "insurance_company_members.csv"};
        List<String> argsList = Arrays.asList(args);
        List<String> stringList = new ArrayList<>();
        stringList.add("insurance_company_members.csv");
        stringList.add("emails");
        stringList.add("true");
        stringList.add("false");
        stringList.add("email-template.txt");
        stringList.add("null");

        Assert.assertEquals(stringList.toString(), testArg.parseArgs(argsList).toString());
    }
    @Test
    public void testParser2() throws WrongExtensionType {
        String[] args = {"--letter", "--letter-template", "lettertemplate.txt",
                "--output-dir", "letters", "--csv-file", "insurance_company_members.csv"};
        List<String> argsList2 = Arrays.asList(args);
        List<String> stringList2 = new ArrayList<>();
        stringList2.add("insurance_company_members.csv");
        stringList2.add("emails");
        stringList2.add("true");
        stringList2.add("false");
        stringList2.add("email-template.txt");
        stringList2.add("null");

        Assert.assertNotEquals(stringList2.toString(), testArg.parseArgs(argsList2).toString());

    }

    @Test(expected = Exception.class)
    public void testParser3() throws NullPointerException, WrongExtensionType {
        String[] args = {"--letter", "--letter-template",
            "--output-dir", "letters", "--csv-file", "insurance_company_members.csv"};
        List<String> argsList2 = Arrays.asList(args);
        List<String> stringList2 = new ArrayList<>();
        stringList2.add("insurance_company_members.csv");
        stringList2.add("emails");
        stringList2.add("true");
        stringList2.add("false");
        stringList2.add("email-template.txt");
        stringList2.add("null");

        Assert.assertNotEquals(stringList2.toString(), testArg.parseArgs(argsList2).toString());

    }

  @Test(expected = Exception.class)
  public void testParser4() throws NullPointerException, WrongExtensionType {
    String[] args = {"--letter", "--letter-template",
        "--output-dir", "letters", "--csv-file", "insurance_company_members.txt"};
    List<String> argsList2 = Arrays.asList(args);
    List<String> stringList2 = new ArrayList<>();
    stringList2.add("insurance_company_members.csv");
    stringList2.add("emails");
    stringList2.add("true");
    stringList2.add("false");
    stringList2.add("email-template.txt");
    stringList2.add("null");

    Assert.assertNotEquals(stringList2.toString(), testArg.parseArgs(argsList2).toString());

  }

  @Test(expected = Exception.class)
  public void testParser5() throws NullPointerException, WrongExtensionType {
    String[] args = {"--letter", "--letter-template", "lettertemplate.csv",
        "--output-dir", "letters", "--csv-file", "insurance_company_members.txt"};
    List<String> argsList2 = Arrays.asList(args);
    List<String> stringList2 = new ArrayList<>();
    stringList2.add("insurance_company_members.csv");
    stringList2.add("emails");
    stringList2.add("true");
    stringList2.add("false");
    stringList2.add("email-template.txt");
    stringList2.add("null");

    Assert.assertNotEquals(stringList2.toString(), testArg.parseArgs(argsList2).toString());

  }
    
  @Test(expected = Exception.class)
  public void testParser6() throws NullPointerException, WrongExtensionType {
   String[] args = {"--letter", "--csv-file", "--letter-template", "lettertemplate.txt",
                };
   List<String> argsList2 = Arrays.asList(args);
   List<String> stringList2 = new ArrayList<>();
   stringList2.add("insurance_company_members.csv");
   stringList2.add("emails");
   stringList2.add("true");
   stringList2.add("false");
   stringList2.add("email-template.txt");
   stringList2.add("null");
   Assert.assertNotEquals(stringList2.toString(), testArg.parseArgs(argsList2).toString());

    }
    
    @Test(expected = Exception.class)
    public void testParser7() throws NullPointerException, WrongExtensionType {
        String[] args = {"--ryan", "--csv-file", "--letter-template", "lettertemplate.txt",
        };
        List<String> argsList2 = Arrays.asList(args);
        List<String> stringList2 = new ArrayList<>();
        stringList2.add("insurance_company_members.csv");
        stringList2.add("emails");
        stringList2.add("true");
        stringList2.add("false");
        stringList2.add("email-template.txt");
        stringList2.add("null");
        Assert.assertNotEquals(stringList2.toString(), testArg.parseArgs(argsList2).toString());

    }

    private static boolean hasFile(String nextArg) {
        return nextArg != null && (!nextArg.contains("--"));
    }

    private static String getFileExtension(String fileName) throws WrongExtensionType {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            throw new WrongExtensionType(
                    String.format("Path %s does not have a valid file extension.", fileName));
        }
        return (Optional.of(fileName.substring(lastIndex + 1)).get());
    }

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void TestForException() throws WrongExtensionType {
        thrown.expect(WrongExtensionType.class);
        String[] args = {"--letter", "--letter-template", "lettertemplate.txt",
                "--output-dir", "letters", "--csv-file", "insurance_company_members.csv"};
        List<String> argsList3 = Arrays.asList(args);
        List<String> stringList3 = new ArrayList<>();
        stringList3.add("insurance_company_members.wow");
        stringList3.add("emails");
        stringList3.add("true");
        stringList3.add("false");
        stringList3.add("email-template.txt");
        stringList3.add("null");
        checkExtensionCsv(stringList3.get(0));
    }
}
