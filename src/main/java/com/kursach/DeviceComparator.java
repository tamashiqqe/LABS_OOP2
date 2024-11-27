package com.kursach;

import generated.Device;

import java.util.Comparator;

public class DeviceComparator implements Comparator<Device.Component> {
    @Override
    public int compare(Device.Component f1, Device.Component f2) {
        return f1.getName().compareTo(f2.getName());
    }
}
