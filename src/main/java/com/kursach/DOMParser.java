package com.kursach;

import generated.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import generated.Device;
public class DOMParser {
    private static final Logger logger = LoggerFactory.getLogger(DOMParser.class);

    public Device parseXML(String xmlFilePath) {
        Device device = new Device();

        try {
            logger.info("DOM parsing started for Device XML");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            document.getDocumentElement().normalize();

            NodeList componentList = document.getElementsByTagName("Component");
            for (int i = 0; i < componentList.getLength(); i++) {
                Element componentElement = (Element) componentList.item(i);
                Device.Component component = new Device.Component();

                // Set attributes
                component.setName(componentElement.getAttribute("Name"));
                component.setOrigin(componentElement.getAttribute("Origin"));
                component.setPrice(new BigDecimal(componentElement.getAttribute("Price")));
                component.setCritical(Boolean.parseBoolean(componentElement.getAttribute("Critical")));

                // Parse Type element
                Element typeElement = (Element) componentElement.getElementsByTagName("Type").item(0);
                Device.Component.Type type = new Device.Component.Type();

                type.setPeripheral(PeripheralType.fromValue(typeElement.getElementsByTagName("Peripheral").item(0).getTextContent().trim()));
                type.setEnergyConsumption(Integer.parseInt(typeElement.getElementsByTagName("EnergyConsumption").item(0).getTextContent().trim()));
                type.setCooling(CoolingType.fromValue(typeElement.getElementsByTagName("Cooling").item(0).getTextContent().trim()));
                type.setGroup(typeElement.getElementsByTagName("Group").item(0).getTextContent().trim());

                // Parse Ports
                Device.Component.Type.Ports ports = new Device.Component.Type.Ports();
                List<PortType> portList = new ArrayList<>();
                NodeList portNodes = typeElement.getElementsByTagName("Port");
                for (int j = 0; j < portNodes.getLength(); j++) {
                    portList.add(PortType.fromValue(portNodes.item(j).getTextContent().trim()));
                }
                ports.getPort().addAll(portList);
                type.setPorts(ports);

                component.setType(type);
                device.getComponent().add(component);
            }

            logger.info("DOM parsing completed successfully");
        } catch (Exception e) {
            logger.error("Error while parsing XML: {}", e.getMessage());
            e.printStackTrace();
        }

        return device;
    }
}
