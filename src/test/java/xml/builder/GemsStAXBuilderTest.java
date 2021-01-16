package test.java.xml.builder;

import main.java.xml.builder.GemBuilder;
import main.java.xml.builder.GemsStAXBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GemsStAXBuilderTest {
    GemBuilder gemBuilder;
    String url = "src/gems.xml";

    @BeforeClass
    public void setUp() {
        gemBuilder = new GemsStAXBuilder();
        gemBuilder.buildGemSet(url);
    }

    @Test
    public void testBuildGemSet() {
        int expected = 14;
        int actual = gemBuilder.getGems().size();
        Assert.assertEquals(actual, expected);
    }
}