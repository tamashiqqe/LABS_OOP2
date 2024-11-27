package com.kursach;

import generated.Device;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeviceComparatorTest {

    @Test
    void testCompare() {
        // Prepare Device components
        Device.Component component1 = new Device.Component();
        component1.setName("Keyboard");

        Device.Component component2 = new Device.Component();
        component2.setName("Mouse");

        Device.Component component3 = new Device.Component();
        component3.setName("Monitor");

        // Create a list and add components
        List<Device.Component> components = new java.util.ArrayList<>(List.of(component1, component2, component3));

        // Sort the list using the comparator
        components.sort(new DeviceComparator());

        // Validate the sorting order by comparing names
        assertEquals("Keyboard", components.get(0).getName());
        assertEquals("Mouse", components.get(2).getName());
        assertEquals("Monitor", components.get(1).getName());
    }
}
