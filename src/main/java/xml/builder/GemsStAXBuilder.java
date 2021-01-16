package main.java.xml.builder;

import main.java.xml.entity.Gem;
import main.java.xml.entity.GemEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GemsStAXBuilder extends GemBuilder {
    private XMLInputFactory inputFactory;

    private static Logger logger = LogManager.getLogger();

    public GemsStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildGemSet(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;

        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {

                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (GemEnum.valueOf(name.toUpperCase()) == GemEnum.GEM) {
                        Gem gem = buildGem(reader);
                        gems.add(gem);
                    }
                }
            }
            logger.log(Level.INFO, "StAXParsing is successful");
        } catch (XMLStreamException | FileNotFoundException ex) {
            logger.log(Level.ERROR, "Problem while opening file", ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                logger.log(Level.ERROR, "Problem while closing inputStream", ex);
            }
        }
    }

    private Gem buildGem(XMLStreamReader reader) throws XMLStreamException {
        if (reader.getAttributeValue(null, "preciousness") == null) {
            return null;
        }
        Gem gem = new Gem();
        gem.setName(reader.getAttributeValue(null, "name"));
        gem.setDate(reader.getAttributeValue(null, "date"));
        gem.setPreciousness(reader.getAttributeValue(null, "preciousness"));

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemEnum.valueOf(name.toUpperCase())) {
                        case ORIGIN:
                            gem.setOrigin(getXMLText(reader));
                            break;
                        case VALUE:
                            name = getXMLText(reader);
                            gem.setValue(Double.parseDouble(name));
                            break;
                        case PARAMETERS:
                            gem.setParameters(getXMLParameters(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (GemEnum.valueOf(name.toUpperCase()) == GemEnum.GEM) {
                        return gem;
                    }
                    break;
            }
        }
        logger.log(Level.ERROR, "Unknown element in tag Gem");
        throw new XMLStreamException("Unknown element in tag Gem");
    }

    private Gem.Parameters getXMLParameters(XMLStreamReader reader) throws XMLStreamException {
        Gem.Parameters parameters = new Gem.Parameters();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemEnum.valueOf(name.toUpperCase())) {
                        case COLOR:
                            parameters.setColor(getXMLText(reader));
                            break;
                        case GEMCUTTER:
                            name = getXMLText(reader);
                            parameters.setGemcutter(Integer.parseInt(name));
                            break;
                        case TRANSPARENCY:
                            name = getXMLText(reader);
                            parameters.setTransparency(Integer.parseInt(name));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (GemEnum.valueOf(name.toUpperCase()) == GemEnum.PARAMETERS) {
                        return parameters;
                    }
                    break;
            }
        }
        logger.log(Level.ERROR, "Unknown element in tag Parameters");
        throw new XMLStreamException("Unknown element in tag Parameters");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
