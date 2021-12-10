package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Trialblocks {
    private static Trialblocks instance = null;
    private final Map<Integer, ArrayList<Point>> position;
    private final ArrayList<Constellation> trials;
    private final ArrayList<ArrayList<Constellation>> blocks;
    private int numMonitors;
    private int trialNum;

    private Trialblocks() {
        this.numMonitors = 0;
        this.position = new HashMap<>();
        this.trials = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.trialNum = 0;
    }

    public static Trialblocks getTrialblocks() {
        if (instance == null) {
            instance = new Trialblocks();
        }
        return instance;
    }

    public void addMonitor(int monitorWidth, int monitorHeight, int insetSize) {
        this.numMonitors++; //start with monitor 1

        int monitorRows = Config.MONITOR_ZONES[0];
        int monitorCols = Config.MONITOR_ZONES[1];

        int rectWidth = Config.STARTFIELD_WIDTH;
        int rectHeight = Config.STARTFIELD_HEIGHT;
        int padding = Config.PADDING;
        ArrayList<Point> xyCoords = new ArrayList<>();

        int leftEdge = padding + rectWidth / 2;
        int rightEdge = monitorWidth - rectWidth / 2 - padding;
        int topEdge = rectHeight + insetSize + padding;
        int bottomEdge = monitorHeight - rectHeight / 2 - insetSize - padding;

        int xCoord = leftEdge;
        int yCoord = topEdge;

        xyCoords.add(new Point(xCoord, yCoord));

        for(int i = 1; i < monitorRows; i++) {
            for(int j = 1; j < monitorCols; j++) {
                int xStep = (rightEdge - leftEdge) / (monitorCols - 1);
                xCoord += xStep;
                xyCoords.add(new Point(xCoord, yCoord));
            }
            int yStep = (bottomEdge - topEdge) / (monitorRows - 1);
            yCoord += yStep;
            xCoord = leftEdge;
            xyCoords.add(new Point(xCoord, yCoord));
        }

        position.put(numMonitors, xyCoords);
    }

    public void addTrial(int monitorStart, int monitorGoal, int posStart, int posGoal, int goalWidth, int goalHeight) {

        trials.add(new Constellation(
                monitorStart,
                monitorGoal,
                position.get(monitorStart).get(posStart),
                position.get(monitorGoal).get(posGoal),
                trialNum++,
                goalWidth,
                goalHeight
        ));
    }

    public void generateTrials() {
        addTrial(3, 1, 0, 0, 10, 25);
        addTrial(1, 2, 1, 1, 10, 25);
        addTrial(2, 3, 2, 2, 10, 25);
        addTrial(3, 1, 3, 3, 10, 25);
        addTrial(1, 3, 4, 4, 10, 25);
        addTrial(3, 2, 5, 5, 10, 25);
        addTrial(2, 1, 4, 4, 10, 20);


        for (int i = 0; i < Config.BLOCKS; i++) {
            //Collections.shuffle(trials);
            ArrayList<Constellation> nextBlock = new ArrayList<>();
            ArrayList<ArrayList<Constellation>> seperateTrials = new ArrayList<>();

            // Get different startMonitors
            ArrayList<Integer> maxStart = new ArrayList<>();
            for(Constellation t : trials) {
                if(!maxStart.contains(t.getMonitorStart())) {
                    maxStart.add(t.getMonitorStart());
                }
            }

            //Seperate different startMonitors in seperate Lists
            for(int j = 1; j <= maxStart.size(); j++) {
                int finalX = j;
                ArrayList<Constellation> filteredTrials= trials.stream()
                        .filter(n -> n.getMonitorStart() == finalX).collect(Collectors.toCollection(ArrayList::new));
                Collections.shuffle(filteredTrials);
                seperateTrials.add(filteredTrials);
            }

            //Stitch together new Block but keep order endMonitor = new StartMonitor
            for(ArrayList<Constellation> constellations : seperateTrials) {
                for(int x = 0; x < constellations.size(); x++) {
                    nextBlock.add(constellations.get(x));
                    boolean found = false;

                    for(ArrayList<Constellation> constellations1 : seperateTrials) {
                        for(Constellation constellation : constellations1) {
                            if(constellation.getMonitorStart() == constellations.get(x).getMonitorEnd()) {
                                nextBlock.add(constellation);
                                constellations1.remove(constellation);
                                found = true;
                                break;
                            }
                        }
                        if(found) break;
                    }
                    constellations.remove(constellations.get(x));
                }
            }
            System.out.println("New Trialblock: ");
            for(Constellation constellation : nextBlock) {
                System.out.println(constellation.getMonitorStart() + " - " + constellation.getMonitorEnd() + ": " + constellation.getTrialNum());
            }
            blocks.add(trials);
        }

        System.out.println("Trials: " + trials.size());
    }

    public ArrayList<ArrayList<Constellation>> getBlocks() {
        return blocks;
    }

    public void pushBackTrial(Constellation trial) {
        trials.add(trial);
    }

    public void resetTrialblock() {
        Set<Constellation> set = new HashSet<>(trials);
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
