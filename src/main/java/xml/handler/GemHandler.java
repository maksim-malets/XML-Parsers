package main.java.xml.handler;

import main.java.xml.entity.Gem;
import main.java.xml.entity.GemEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class GemHandler extends DefaultHandler {
    private Set<Gem> gems;
    private Gem current = null;
    private GemEnum currentEnum = null;
    private EnumSet<GemEnum> withText;
    private Gem.Parameters parameters = null;

    private static Logger logger = LogManager.getLogger();

    public GemHandler() {
        gems = new HashSet<Gem>();
        withText = EnumSet.range(GemEnum.GEMS, GemEnum.TRANSPARENCY);
    }

    public Set<Gem> getGems() {
        return gems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if ("gem".equals(localName)) {
            current = new Gem();
            parameters = new Gem.Parameters();
            current.setName(attrs.getValue(0));
            current.setDate(attrs.getValue(1));
            if (attrs.getLength() == 3) {
                current.setPreciousness(attrs.getValue(2));
            }
        } else {
            GemEnum temp = GemEnum.valueOf(localName.toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("gem".equals(localName)) {
            current.setParameters(parameters);
            gems.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).strip();
        if (currentEnum != null) {
            switch (currentEnum) {
                case ORIGIN:
                    current.setOrigin(s);
                    break;
                case VALUE:
                    current.setValue(Double.parseDouble(s));
                    break;
                case COLOR:
                    parameters.setColor(s);
                    break;
                case GEMCUTTER:
                    parameters.setGemcutter(Integer.parseInt(s));
                    break;
                case TRANSPARENCY:
                    parameters.setTransparency(Integer.parseInt(s));
                    break;
                case PARAMETERS:
                case GEMS:
                    break;
                default:
                    logger.log(Level.ERROR, "Unknown element in XML file");
                    throw new EnumConstantNotPresentException(currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }
}
