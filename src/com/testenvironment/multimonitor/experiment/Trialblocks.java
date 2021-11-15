package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.*;

public class Trialblocks {
    private static Trialblocks instance = null;

    private int numMonitors;
    private Map<Integer, ArrayList<Point>> position;
    private ArrayList<Trial> trials;
    private ArrayList<ArrayList<Trial>> blocks;

    private Trialblocks() {
        this.numMonitors = 0;
        this.position = new HashMap<>();
        this.trials = new ArrayList<>();
        this.blocks = new ArrayList<>();
    }

    public static Trialblocks getTrialblocks() {
        if (instance == null) {
            instance = new Trialblocks();
        }
        return instance;
    }

    /**
     * Add Monitor to Hashmap positions with all 9 possible fieldpositions
     *
     * @param monitorWidth MonitorWidth
     * @param monitorHeight MonitorHeight
     */
    public void addMonitor(int monitorWidth, int monitorHeight) {
        this.numMonitors++; //start with monitor 1

        int startFieldWidth = Config.STARTFIELD_WIDTH;
        int startFieldHeight = Config.STARTFIELD_HEIGHT;
        ArrayList<Point> xyCoords = new ArrayList<>();

        int xLeft = startFieldWidth;
        int xMid = monitorWidth / 2;
        int xRight = monitorWidth - startFieldWidth;
        int yTop = startFieldHeight;
        int yMid = monitorHeight / 2;
        int yBottom = monitorHeight - startFieldHeight;

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

    public void generateTrials() {
        int trialNum = 0;
        for(int i = 1; i <= Config.MAX_MONITOR; i++) {
            for(int j = Config.MAX_MONITOR; j > 1; j--) {
                if(i != j) {
                    //Same Positions
                    trials.add(new Trial(i, j, position.get(i).get(5), position.get(j).get(3), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(2), position.get(i).get(0), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(8), position.get(i).get(6), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(1), position.get(i).get(1), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(4), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(7), position.get(i).get(7), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(6), position.get(i).get(8), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(0), position.get(i).get(2), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(3), position.get(i).get(5), trialNum++));

                    //Diagonal
                    trials.add(new Trial(i, j, position.get(i).get(0), position.get(j).get(8), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(6), position.get(i).get(2), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(2), position.get(i).get(6), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(8), position.get(i).get(0), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(8), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(2), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(0), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(6), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(6), position.get(i).get(4), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(0), position.get(i).get(4), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(2), position.get(i).get(4), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(8), position.get(i).get(4), trialNum++));

                }
            }
        }

        for(int i = 0; i < Config.BLOCKS; i++) {
            Collections.shuffle(trials);
            blocks.add(trials);
        }

        System.out.println("Trials: " + trials.size());
    }

    public ArrayList<ArrayList<Trial>> getBlocks() {
        return blocks;
    }

    /**
     *  For Debugging -> Prints map to console
     */
    public void printMonitorPositions() {
        System.out.println("---------------------------");
        position.forEach((key, value) -> System.out.println("Monitor " + key + ": " + Arrays.toString(value.toArray())));
        System.out.println("---------------------------");
    }

}
