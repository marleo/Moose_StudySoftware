package com.testenvironment.multimonitor.experiment;

public class Monitor {
    int monitorWidth, monitorHeight;

    public Monitor(int monitorWidth, int monitorHeight) {
        this.monitorHeight = monitorHeight;
        this.monitorWidth = monitorWidth;
    }

    public int getMonitorWidth() {
        return monitorWidth;
    }

    public int getMonitorHeight() {
        return monitorHeight;
    }
}
