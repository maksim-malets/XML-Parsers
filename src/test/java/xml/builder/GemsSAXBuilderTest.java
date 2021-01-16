package test.java.xml.builder;

import main.java.xml.builder.GemBuilder;
import main.java.xml.builder.GemsSAXBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class GemsSAXBuilderTest {
    GemBuilder gemBuilder;
    String url = "src/gems.xml";

    @BeforeClass
    public void setUp() {
        gemBuilder = new GemsSAXBuilder();
        gemBuilder.buildGemSet(url);
    }

    @Test
    public void testBuildGemSet() {
        int expected = 16;
        int actual = gemBuilder.getGems().size();
        Assert.assertEquals(actual, expected);
    }
}