package com.kursach;

import generated.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceSAXParser extends DefaultHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeviceSAXParser.class);
    private List<Device.Component> components = new ArrayList<>();
    private Device.Component currentComponent;
    private Device.Component.Type currentType;
    private StringBuilder data;

    public List<Device.Component> parseXML(String xmlFilePath) {
        try {
            logger.info("SAX parsing started");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(xmlFilePath), this);
            logger.info("SAX parsing completed successfully");
        } catch (Exception e) {
            logger.error("SAX parsing failed: {}", e.getMessage());
            e.printStackTrace();
        }
        return components;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data = new StringBuilder();

        switch (qName.toLowerCase()) {
            case "component":
                currentComponent = new Device.Component();
                currentComponent.setName(attributes.getValue("Name"));
                currentComponent.setOrigin(attributes.getValue("Origin"));
                currentComponent.setPrice(new BigDecimal(attributes.getValue("Price")));
                currentComponent.setCritical(Boolean.parseBoolean(attributes.getValue("Critical")));
                break;
            case "type":
                currentType = new Device.Component.Type();
                break;
            case "ports":
                currentType.setPorts(new Device.Component.Type.Ports());
                break;
            case "port":
                break;
            case "peripheral":
                break;
            case "energyconsumption":
                break;
            case "cooling":
                break;
            case "group":
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName.toLowerCase()) {
            case "component":
                components.add(currentComponent);
                break;
            case "type":
                currentComponent.setType(currentType);
                break;
            case "peripheral":
                currentType.setPeripheral(PeripheralType.valueOf(data.toString().trim().toUpperCase()));
                break;
            case "energyconsumption":
                currentType.setEnergyConsumption(Integer.parseInt(data.toString().trim()));
                break;
            case "cooling":
                currentType.setCooling(CoolingType.valueOf(data.toString().trim().toUpperCase()));
                break;
            case "group":
                currentType.setGroup(data.toString().trim());
                break;
            case "port":
                currentType.getPorts().getPort().add(PortType.valueOf(data.toString().trim().toUpperCase()));
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
