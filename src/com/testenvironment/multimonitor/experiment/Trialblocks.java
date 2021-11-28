package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.*;

public class Trialblocks {
    private static Trialblocks instance = null;

    private int numMonitors;
    private final Map<Integer, ArrayList<Point>> position;
    private final ArrayList<Trial> trials;
    private final ArrayList<ArrayList<Trial>> blocks;

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
    public void addMonitor(int monitorWidth, int monitorHeight, int insetSize) {
        this.numMonitors++; //start with monitor 1

        int rectWidth = Config.STARTFIELD_WIDTH;
        int rectHeight = Config.STARTFIELD_HEIGHT;
        int padding = Config.PADDING;
        ArrayList<Point> xyCoords = new ArrayList<>();

        int xLeft = padding + rectWidth / 2;
        int xMid = monitorWidth / 2;
        int xRight = monitorWidth - rectWidth / 2 - padding;
        int yTop = rectHeight + insetSize + padding;
        int yMid = monitorHeight / 2 - insetSize;
        int yBottom = monitorHeight - rectHeight / 2 - insetSize - padding;

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
//                    trials.add(new Trial(i, j, position.get(i).get(5), position.get(j).get(3), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(2), position.get(i).get(0), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(8), position.get(i).get(6), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(1), position.get(i).get(1), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(4), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(7), position.get(i).get(7), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(6), position.get(i).get(8), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(0), position.get(i).get(2), trialNum++));
                    trials.add(new Trial(i, j, position.get(i).get(3), position.get(i).get(5), trialNum++));

                    //Diagonal
//                    trials.add(new Trial(i, j, position.get(i).get(0), position.get(j).get(8), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(6), position.get(i).get(2), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(2), position.get(i).get(6), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(8), position.get(i).get(0), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(8), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(2), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(0), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(4), position.get(i).get(6), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(6), position.get(i).get(4), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(0), position.get(i).get(4), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(2), position.get(i).get(4), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(8), position.get(i).get(4), trialNum++));

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

    public void pushBackTrial(Trial trial) {
        trials.add(trial);
    }

    public void resetTrialblock() {
        Set<Trial> set = new HashSet<>(trials);
        trials.clear();
        trials.addAll(set);
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
