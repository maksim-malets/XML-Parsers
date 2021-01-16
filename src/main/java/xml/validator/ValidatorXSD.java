package main.java.xml.validator;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorXSD {
    private static Logger logger = LogManager.getLogger();

    public void validateXSD() {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String fileName = "src/gems.xml";
        String schemaName = "src/gems.xsd";
        SchemaFactory schemaFactory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        try {
            Schema schema = schemaFactory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            validator.validate(source);
            logger.log(Level.INFO, "Successful validation");
        } catch (SAXException | IOException ex) {
            logger.log(Level.ERROR, "Unsuccessful validation", ex);
        }
    }
}
