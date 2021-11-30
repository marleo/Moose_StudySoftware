package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.*;

public class Trialblocks {
    private static Trialblocks instance = null;
    private final Map<Integer, ArrayList<Point>> position;
    private final ArrayList<Trial> trials;
    private final ArrayList<ArrayList<Trial>> blocks;
    private int numMonitors;

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
     * @param monitorWidth  MonitorWidth
     * @param monitorHeight MonitorHeight
     */
    public void addMonitor(int monitorWidth, int monitorHeight, int insetSize) {
        this.numMonitors++; //start with monitor 1

        int rectWidth = Config.STARTFIELD_WIDTH;
        int rectHeight = Config.STARTFIELD_HEIGHT;
        int padding = Config.PADDING;
        ArrayList<Point> xyCoords = new ArrayList<>();

        // Field 3x3
        int xLeft = padding + rectWidth / 2;
        int xMid = monitorWidth / 2;
        int xRight = monitorWidth - rectWidth / 2 - padding;
        int yTop = rectHeight + insetSize + padding;
        int yMid = monitorHeight / 2 - insetSize;
        int yBottom = monitorHeight - rectHeight / 2 - insetSize - padding;

        // Field 5x5
        int xLeftMid = xMid / 2 + padding;
        int xRightMid = xMid + xMid / 2 - padding;
        int yTopMid = yMid / 2 + padding;
        int yBottomMid = yMid + yMid / 2 - padding;

        // 3x3 (Index 0 - 8)
        xyCoords.add(new Point(xLeft, yTop));       //Top Left
        xyCoords.add(new Point(xMid, yTop));        //Top Mid
        xyCoords.add(new Point(xRight, yTop));      //Top Right
        xyCoords.add(new Point(xLeft, yMid));       //Mid Left
        xyCoords.add(new Point(xMid, yMid));        //Mid Mid
        xyCoords.add(new Point(xRight, yMid));      //Mid Right
        xyCoords.add(new Point(xLeft, yBottom));    //Bottom Left
        xyCoords.add(new Point(xMid, yBottom));     //Bottom Mid
        xyCoords.add(new Point(xRight, yBottom));   //Bottom Right

        // 5x5 (Index 9 - 23)
        xyCoords.add(new Point(xLeftMid, yTop));
        xyCoords.add(new Point(xRightMid, yTop));
        xyCoords.add(new Point(xLeft, yTopMid));
        xyCoords.add(new Point(xLeftMid, yTopMid));
        xyCoords.add(new Point(xMid, yTopMid));
        xyCoords.add(new Point(xRightMid, yTopMid));
        xyCoords.add(new Point(xRight, yTopMid));
        xyCoords.add(new Point(xLeftMid, yMid));
        xyCoords.add(new Point(xRightMid, yMid));
        xyCoords.add(new Point(xLeft, yBottomMid));
        xyCoords.add(new Point(xLeftMid, yBottomMid));
        xyCoords.add(new Point(xMid, yBottomMid));
        xyCoords.add(new Point(xRightMid, yBottomMid));
        xyCoords.add(new Point(xRight, yBottomMid));
        xyCoords.add(new Point(xLeftMid, yBottom));
        xyCoords.add(new Point(xRightMid, yBottom));


        position.put(numMonitors, xyCoords);
    }

    public void generateTrials() {
        int trialNum = 0;
        for (int i = 1; i <= Config.MAX_MONITOR; i++) {
            for (int j = Config.MAX_MONITOR; j > 1; j--) {
                if (i != j) {
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

//                    trials.add(new Trial(i, j, position.get(i).get(9), position.get(i).get(10), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(11), position.get(i).get(13), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(16), position.get(i).get(17), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(19), position.get(i).get(21), trialNum++));
//                    trials.add(new Trial(i, j, position.get(i).get(22), position.get(i).get(23), trialNum++));

                }
            }
        }

        for (int i = 0; i < Config.BLOCKS; i++) {
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
     * For Debugging -> Prints map to console
     */
    public void printMonitorPositions() {
        System.out.println("---------------------------");
        position.forEach((key, value) -> System.out.println("Monitor " + key + ": " + Arrays.toString(value.toArray())));
        System.out.println("---------------------------");
    }

}
