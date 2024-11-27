

import com.kursach.DeviceSAXParser;
import generated.Device;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeviceSAXParserTest {

    @Test
    void testParseXML() {
        // Arrange
        DeviceSAXParser saxParser = new DeviceSAXParser();
        String xmlFilePath = "src/test/resources/test_device.xml";

        // Act
        List<Device.Component> components = saxParser.parseXML(xmlFilePath);

        // Assert
        assertNotNull(components);
        assertEquals(2, components.size());

        // Validate the first component
        Device.Component component1 = components.get(0);
        assertEquals("Monitor", component1.getName());
        assertEquals("Japan", component1.getOrigin());
        assertEquals(new BigDecimal("200.00"), component1.getPrice());
        assertTrue(component1.isCritical());
        assertNotNull(component1.getType());
        assertFalse(component1.getType().getPeripheral());
        assertEquals(50, component1.getType().getEnergyConsumption());
        assertEquals("active", component1.getType().getCooling().toString());
        assertEquals("Display", component1.getType().getGroup());
        assertEquals(2, component1.getType().getPorts().getPort().size());
        assertTrue(component1.getType().getPorts().getPort().contains("HDMI"));
        assertTrue(component1.getType().getPorts().getPort().contains("VGA"));

        // Validate the second component
        Device.Component component2 = components.get(1);
        assertEquals("Speaker", component2.getName());
        assertEquals("Germany", component2.getOrigin());
        assertEquals(new BigDecimal("150.00"), component2.getPrice());
        assertFalse(component2.isCritical());
        assertNotNull(component2.getType());
        assertTrue(component2.getType().getPeripheral());
        assertEquals(10, component2.getType().getEnergyConsumption());
        assertEquals("none", component2.getType().getCooling().toString());
        assertEquals("Audio", component2.getType().getGroup());
        assertEquals(2, component2.getType().getPorts().getPort().size());
        assertTrue(component2.getType().getPorts().getPort().contains("AUX"));
        assertTrue(component2.getType().getPorts().getPort().contains("Bluetooth"));
    }
}
