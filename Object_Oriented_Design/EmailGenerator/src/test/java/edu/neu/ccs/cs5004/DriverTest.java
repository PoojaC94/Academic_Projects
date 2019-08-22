package edu.neu.ccs.cs5004;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DriverTest {

    private String template;
    private HashMap<String, String> testCustomer;
    private String output;
    private Path outputPath;
    Driver driver;

    @Before
    public void setUp() throws Exception {
        template = "src/main/java/edu/neu/ccs/cs5004/lettertemplate.txt";
        output = "src/main/java/edu/neu/ccs/cs5004/testFile/";
        outputPath = Paths.get(output);
        testCustomer = new HashMap();
        testCustomer.put("first_name", "Benny");
        testCustomer.put("last_name", "Thomas");
        testCustomer.put("company_name", "ABC Associates");
        testCustomer.put("address", "15, Baker St");
        testCustomer.put("county", "Snohomish");
        testCustomer.put("city", "New London");
        testCustomer.put("state", "WA");
        testCustomer.put("zip", "900001");
        testCustomer.put("email", "benthom@abca.com");
        Driver driver = new Driver();
    }

    @Test(expected = Exception.class)
    public void main() throws IOException, WrongExtensionType, NullPointerException {
        driver.main(null);

    }

    @Test(expected = Exception.class)
    public void processTemplate() throws NullPointerException {
        driver.processTemplate(template, null, outputPath);
    }

    @Test
    public void processTemplatepass() {
        driver.processTemplate(template, testCustomer, outputPath);
    }





}