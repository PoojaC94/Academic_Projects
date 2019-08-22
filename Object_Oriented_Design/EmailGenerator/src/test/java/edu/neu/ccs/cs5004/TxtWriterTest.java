package edu.neu.ccs.cs5004;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class TxtWriterTest {
    TxtWriter test = new TxtWriter();
    @Test
    public void writeTxt() {

        Path outputFilePath = Paths.get(
                "src/main/java/edu/neu/ccs/cs5004/SomeOutputFile.txt");
        String testOutputText = "This is some text that I want to write to a file.";

        test.writeTxt(testOutputText, outputFilePath);
    }

    @Test
    public void writeTxt1() throws IOException {

        Path outputFilePath = Paths.get(
            "N/A");
        String testOutputText = "This is some text that I want to write to a file.";

        test.writeTxt(testOutputText, outputFilePath);
    }
}