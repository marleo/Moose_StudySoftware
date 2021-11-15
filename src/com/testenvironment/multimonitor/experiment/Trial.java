package com.testenvironment.multimonitor.experiment;

import java.awt.*;

public class Trial {

    private int monitorStart;
    private int monitorEnd;
    private Point start;
    private Point end;

    public Trial(int monitorStart, int monitorEnd, Point start, Point end) {
        this.monitorStart = monitorStart;
        this.monitorEnd = monitorEnd;
        this.start = start;
        this.end = end;
    }

    public int getMonitorStart() {
        return monitorStart;
    }

    public void setMonitorStart(int monitorStart) {
        this.monitorStart = monitorStart;
    }

    public int getMonitorEnd() {
        return monitorEnd;
    }

    public void setMonitorEnd(int monitorEnd) {
        this.monitorEnd = monitorEnd;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

}
