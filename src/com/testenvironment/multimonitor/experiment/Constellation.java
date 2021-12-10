package com.testenvironment.multimonitor.experiment;

import java.awt.*;

public class Constellation {

    private int monitorStart;
    private int monitorEnd;
    private int errors;
    private int goalWidth;
    private int goalHeight;
    private final int trialNum;
    private Point start;
    private Point end;


    public Constellation(int monitorStart, int monitorEnd, Point start, Point end, int trialNum, int goalWidth, int goalHeight) {
        this.monitorStart = monitorStart;
        this.monitorEnd = monitorEnd;
        this.start = start;
        this.end = end;
        this.trialNum = trialNum;
        this.errors = 0;
        this.goalHeight = goalHeight;
        this.goalWidth = goalWidth;
    }

    public int getTrialNum() {
        return this.trialNum;
    }

    public void setError() {
        this.errors++;
    }

    public int getAndResetErrors() {
        int returnVal = this.errors;
        this.errors = 0;
        return returnVal;
    }

    public int getGoalWidth() {
        return goalWidth;
    }
    public int getGoalHeight() {
        return goalHeight;
    }
    public int getMonitorStart() {
        return monitorStart;
    }
    public int getMonitorEnd() {
        return monitorEnd;
    }
    public Point getStart() {
        return start;
    }
    public Point getEnd() {
        return end;
    }

}
