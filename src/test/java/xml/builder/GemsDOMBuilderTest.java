package test.java.xml.builder;

import main.java.xml.builder.GemBuilder;
import main.java.xml.builder.GemsDOMBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GemsDOMBuilderTest {
    GemBuilder gemBuilder;
    String url = "src/gems.xml";

    @BeforeClass
    public void setUp() {
        gemBuilder = new GemsDOMBuilder();
        gemBuilder.buildGemSet(url);
    }

    @Test
    public void testBuildGemSet() {
        int expected = 16;
        int actual = gemBuilder.getGems().size();
        Assert.assertEquals(actual, expected);
    }
}