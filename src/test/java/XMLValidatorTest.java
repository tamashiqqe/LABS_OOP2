import com.kursach.XMLValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XMLValidatorTest {
    XMLValidator validator = new XMLValidator();

    @Test
    void testValidXML() {
        String xsdPath = "xsd/device.xsd";
        String xmlPath = "device.xml";

        assertTrue(validator.validateXMLSchema(xsdPath, xmlPath));
    }

    @Test
    void testInvalidXML() {
        String xsdPath = "xsd/device.xsd";
        String xmlPath = "invalid_device.xml";

        assertFalse(validator.validateXMLSchema(xsdPath, xmlPath));
    }
}