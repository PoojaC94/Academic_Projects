package edu.neu.ccs.cs5004;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;


public class TxtReaderTest {

    @Test
    public void readTxt1() {
        Path emailTemplatePath = Paths.get(
            "src/main/java/edu/neu/ccs/cs5004/emailtemplate.txt");

        Assert.assertNotNull(TxtReader.readTxt(emailTemplatePath));

        // Check that the first line was read and stored.
        Assert.assertTrue(
            TxtReader.readTxt(emailTemplatePath).toString().contains("From:insuranceCompany@ic.com"));

        // Check that the last line was read and stored.
        Assert.assertTrue(
            TxtReader.readTxt(emailTemplatePath).toString().contains("Insurance Company CEO"));
    }

    @Test
    public void readTxt2() {
        Path emailTemplatePath = Paths.get(
            "src\\main\\java\\edu\\neu\\ccs\\cs5004\\NonExistantFile.txt");

        // The file doesn't exist, so there's no text to read, so there's no text to store.
        StringBuilder testString = TxtReader.readTxt(emailTemplatePath);
        Assert.assertEquals(testString.length(), 0);
    }
}