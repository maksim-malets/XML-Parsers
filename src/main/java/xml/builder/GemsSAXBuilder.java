package main.java.xml.builder;

import main.java.xml.handler.GemErrorHandler;
import main.java.xml.handler.GemHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class GemsSAXBuilder extends GemBuilder {
    private GemHandler gemHandler;
    private XMLReader reader;

    private static Logger logger = LogManager.getLogger();

    public GemsSAXBuilder() {
        gemHandler = new GemHandler();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            SAXParser parser = parserFactory.newSAXParser();
            reader = parser.getXMLReader();
            reader.setContentHandler(gemHandler);
        } catch (SAXException | ParserConfigurationException ex) {
            logger.log(Level.ERROR, "Problem with SAXParser", ex);
        }
    }

    @Override
    public void buildGemSet(String fileName) {
        try {
            reader.parse(fileName);
        } catch (IOException | SAXException ex) {
            logger.log(Level.ERROR, "Problem with XMLReader", ex);
        }
        gems = gemHandler.getGems();
        logger.log(Level.INFO, "SAXParsing is successful");
    }
}
