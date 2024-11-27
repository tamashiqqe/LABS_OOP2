package com.kursach;

import com.kursach.DeviceComparator;
import generated.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StAXParser {
    private static final Logger logger = LoggerFactory.getLogger(StAXParser.class);
    private List<Device.Component> components = new ArrayList<>();

    public List<Device.Component> parseXML(String xmlFilePath) {
        try {
            logger.info("StAX parsing started");
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(xmlFilePath));
            Device.Component component = null;
            Device.Component.Type componentType = null;
            Device.Component.Type.Ports ports = null;
            StringBuilder data = new StringBuilder();

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("Component")) {
                        component = new Device.Component();
                        component.setName(startElement.getAttributeByName(new QName("Name")).getValue());
                        component.setOrigin(startElement.getAttributeByName(new QName("Origin")).getValue());
                        component.setPrice(new BigDecimal(startElement.getAttributeByName(new QName("Price")).getValue()));
                        component.setCritical(Boolean.parseBoolean(startElement.getAttributeByName(new QName("Critical")).getValue()));
                    } else if (qName.equalsIgnoreCase("Type")) {
                        componentType = new Device.Component.Type();
                    } else if (qName.equalsIgnoreCase("Ports")) {
                        ports = new Device.Component.Type.Ports();
                    } else if (qName.equalsIgnoreCase("Port")) {
                        String portType = event.asStartElement().getAttributeByName(new QName("Port")).getValue();
                        if (ports != null) {
                            ports.getPort().add(PortType.valueOf(portType.toUpperCase()));
                        }
                    }
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String qName = endElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("Peripheral")) {
                        String peripheral = data.toString().trim();
                        componentType.setPeripheral(PeripheralType.valueOf(peripheral.toUpperCase()));
                    } else if (qName.equalsIgnoreCase("EnergyConsumption")) {
                        componentType.setEnergyConsumption(Integer.parseInt(data.toString().trim()));
                    } else if (qName.equalsIgnoreCase("Cooling")) {
                        componentType.setCooling(CoolingType.valueOf(data.toString().trim().toUpperCase()));
                    } else if (qName.equalsIgnoreCase("Group")) {
                        componentType.setGroup(data.toString().trim());
                    } else if (qName.equalsIgnoreCase("Ports")) {
                        componentType.setPorts(ports);
                    } else if (qName.equalsIgnoreCase("Type")) {
                        component.setType(componentType);
                    } else if (qName.equalsIgnoreCase("Component")) {
                        components.add(component);
                    }

                    data.setLength(0); // Clear StringBuilder after each element
                } else if (event.isCharacters()) {
                    data.append(event.asCharacters().getData());
                }
            }

            logger.info("StAX parsing completed successfully");
        } catch (Exception e) {
            logger.error("StAX parsing failed: {}", e.getMessage());
            e.printStackTrace();
        }

        Collections.sort(components, new DeviceComparator()); // Sort components if comparator available
        return components;
    }
}
