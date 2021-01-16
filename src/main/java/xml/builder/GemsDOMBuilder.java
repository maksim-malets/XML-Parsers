package main.java.xml.builder;

import main.java.xml.entity.Gem;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

public class GemsDOMBuilder extends GemBuilder {
    private DocumentBuilder documentBuilder;

    private static Logger logger = LogManager.getLogger();

    public GemsDOMBuilder() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            logger.log(Level.ERROR, "Problem with DocumentBuilder", ex);
        }
    }

    public void buildGemSet(String filename) {
        Document document;
        try {
            document = documentBuilder.parse(filename);
            Element root = document.getDocumentElement();
            NodeList gemList = root.getElementsByTagName("gem");
            for (int i = 0; i < gemList.getLength(); i++) {
                Element gemElement = (Element) gemList.item(i);
                Gem gem = null;
                try {
                    gem = buildGem(gemElement);
                } catch (ParseException ex) {
                    logger.log(Level.ERROR, "Problem while building gem", ex);
                }
                gems.add(gem);
            }
            logger.log(Level.INFO, "DOMParsing is successful");
        } catch (IOException | SAXException ex) {
            logger.log(Level.ERROR, "Problem while building gemSet", ex);
        }
    }

    private Gem buildGem(Element gemElement) throws ParseException {
        Gem gem = new Gem();
        gem.setName(gemElement.getAttribute("name"));
        gem.setDate(gemElement.getAttribute("date"));
        gem.setPreciousness(gemElement.getAttribute("preciousness"));
        gem.setOrigin(getElementTextContent(gemElement, "origin"));
        gem.setValue(Double.parseDouble(getElementTextContent(gemElement, "value")));

        Gem.Parameters parameters = new Gem.Parameters();
        Element parametersElement = (Element) gemElement.getElementsByTagName("parameters").item(0);
        parameters.setColor(getElementTextContent(parametersElement, "color"));
        parameters.setGemcutter(Integer.parseInt(getElementTextContent(parametersElement, "gemcutter")));
        parameters.setTransparency(Integer.parseInt(getElementTextContent(parametersElement, "transparency")));
        gem.setParameters(parameters);
        return gem;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}
