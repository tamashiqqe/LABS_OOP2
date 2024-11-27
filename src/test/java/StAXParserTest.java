

import com.kursach.StAXParser;
import generated.Device;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StAXParserTest {

    @Test
    void testParseXML() {
        // Arrange
        StAXParser parser = new StAXParser();
        String xmlFilePath = "src/test/resources/test_device.xml";

        // Act
        List<Device.Component> components = parser.parseXML(xmlFilePath);

        // Assert
        assertNotNull(components);
        assertEquals(2, components.size());

        // Validate first component
        Device.Component component1 = components.get(0);
        assertEquals("Keyboard", component1.getName());
        assertEquals("USA", component1.getOrigin());
        assertEquals(new BigDecimal("50.00"), component1.getPrice());
        assertTrue(component1.isCritical());
        assertNotNull(component1.getType());
        assertEquals("true", component1.getType().getPeripheral().toString());
        assertEquals(5, component1.getType().getEnergyConsumption());
        assertEquals("passive", component1.getType().getCooling().toString());
        assertEquals("Input", component1.getType().getGroup());
        assertEquals(2, component1.getType().getPorts().getPort().size());
        assertTrue(component1.getType().getPorts().getPort().contains("USB"));
        assertTrue(component1.getType().getPorts().getPort().contains("PS2"));

        // Validate second component
        Device.Component component2 = components.get(1);
        assertEquals("Mouse", component2.getName());
        assertEquals("China", component2.getOrigin());
        assertEquals(new BigDecimal("20.00"), component2.getPrice());
        assertFalse(component2.isCritical());
        assertNotNull(component2.getType());
        assertEquals("true", component2.getType().getPeripheral().toString());
        assertEquals(2, component2.getType().getEnergyConsumption());
        assertEquals("none", component2.getType().getCooling().toString());
        assertEquals("Input", component2.getType().getGroup());
        assertEquals(1, component2.getType().getPorts().getPort().size());
        assertTrue(component2.getType().getPorts().getPort().contains("USB"));
    }
}
