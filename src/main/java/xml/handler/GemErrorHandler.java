package main.java.xml.handler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class GemErrorHandler implements ErrorHandler {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        logger.log(Level.WARN, "Warning ", exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        logger.log(Level.ERROR, "Error ", exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        logger.log(Level.FATAL, "Fatal ", exception);
    }

}
