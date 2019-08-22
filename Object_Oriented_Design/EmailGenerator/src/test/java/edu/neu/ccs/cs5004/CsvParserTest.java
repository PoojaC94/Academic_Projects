package edu.neu.ccs.cs5004;


import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class CsvParserTest {
    CsvParser test = new CsvParser();

    @Test
    public void readCsv() throws FileNotFoundException {
        String filePath = "src/insurance_company_members .csv";

        Assert.assertEquals(501, test.readCsv(filePath).size());
    }

    @Test
    public void failedReadCsv() throws FileNotFoundException {
        String filePath = "src/main/java/edu/neu/ccs/cs5004/NonExistantFile.csv";
        test.readCsv(filePath);
    }
}