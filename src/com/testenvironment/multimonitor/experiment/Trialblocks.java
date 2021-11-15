package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Trialblocks {
    private static Trialblocks instance = null;

    private int numMonitors;
    private Map<Integer, ArrayList<Point>> position;

    private Trialblocks() {
        this.numMonitors = 0;
        this.position = new HashMap<>();
    }

    public static Trialblocks getTrialblocks() {
        if (instance == null) {
            instance = new Trialblocks();
        }
        return instance;
    }

    public void addMonitor(int windowWidth, int windowHeight) {
        this.numMonitors++; //start with monitor 1

        int startFieldWidth = Config.STARTFIELD_WIDTH;
        int startFieldHeight = Config.STARTFIELD_HEIGHT;
        ArrayList<Point> xyCoords = new ArrayList<>();

        /**
         *  Calculate different positions on that monitor
         */

        int xLeft = startFieldWidth;
        int xMid = windowWidth / 2;
        int xRight = windowWidth - startFieldWidth;
        int yTop = startFieldHeight;
        int yMid = windowHeight / 2;
        int yBottom = windowHeight - startFieldHeight;

        xyCoords.add(new Point(xLeft, yTop));       //Top Left
        xyCoords.add(new Point(xMid, yTop));        //Top Mid
        xyCoords.add(new Point(xRight, yTop));      //Top Right
        xyCoords.add(new Point(xLeft, yMid));       //Mid Left
        xyCoords.add(new Point(xMid, yMid));        //Mid Mid
        xyCoords.add(new Point(xRight, yMid));      //Mid Right
        xyCoords.add(new Point(xLeft, yBottom));    //Bottom Left
        xyCoords.add(new Point(xMid, yBottom));     //Bottom Mid
        xyCoords.add(new Point(xRight, yBottom));   //Bottom Right

        position.put(numMonitors, xyCoords);
    }

    public void printMonitorPositions() {
        System.out.println("---------------------------");
        position.forEach((key, value) -> System.out.println("Monitor " + key + ": " + Arrays.toString(value.toArray())));
        System.out.println("---------------------------");
    }

}
