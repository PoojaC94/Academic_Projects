package edu.neu.ccs.cs5004;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

public class TextReplacerTest {

    private StringBuilder oldText = new StringBuilder();
    private HashMap<String, String> hashMap = new HashMap<>();
    private TextReplacer test = new TextReplacer();

    @Test
    public void replaceText() {

        oldText.append("Dear [[first_name]] [[last_name]],");
        hashMap.put("first_name", "James");
        hashMap.put("last_name", "Butt");

        // Test the old text (from the original template).
        Assert.assertEquals(oldText.toString(), "Dear [[first_name]] [[last_name]],");

        // Make the replacement.
        String newText = test.replaceText(oldText, hashMap);

        // Test that the old text was replaced with the information from the customer HashMap.
        Assert.assertEquals(newText, "Dear James Butt,");

    }

    @Test
    public void replaceTextFail() {
        oldText.append("Dear [[first_name]],");
        hashMap.put("first_name", "James");
        hashMap.put("last_name", "Butt");

        // Test the old text (from the original template).
        Assert.assertEquals(oldText.toString(), "Dear [[first_name]],");

        // Make the replacement.
        String newText = test.replaceText(oldText, hashMap);

        // Test that the old text was replaced with the information from the customer HashMap.
        Assert.assertNotEquals(newText, "Dear James Butt,");
    }

    @Test
    public void replaceTextFailSecond() {
        oldText.append("Dear {{first_name}},");
        hashMap.put("first_name", "James");
        hashMap.put("last_name", "Butt");

        // Test the old text (from the original template).
        Assert.assertNotEquals(oldText.toString(), "Dear [[first_name]],");

        // Make the replacement.
        String newText = test.replaceText(oldText, hashMap);

        // Test that the old text was replaced with the information from the customer HashMap.
        Assert.assertNotEquals(newText, "Dear James Butt,");
    }
}