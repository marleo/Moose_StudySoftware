package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class TrialBlocks {
    private static TrialBlocks instance = null;
    private final Map<Integer, ArrayList<Point>> position;
    private final ArrayList<Constellation> trials;
    private final ArrayList<ArrayList<Constellation>> blocks;
    private final ArrayList<Monitor> monitors;
    private int numMonitors;
    private int trialNum;
    private boolean pauseTrial;
    private boolean resumeTrial;

    private TrialBlocks() {
        this.numMonitors = 0;
        this.position = new HashMap<>();
        this.trials = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.monitors = new ArrayList<>();
        this.trialNum = 0;
        this.pauseTrial = false;
        this.resumeTrial = false;
    }

    public static TrialBlocks getTrialblocks() {
        if (instance == null) {
            instance = new TrialBlocks();
        }
        return instance;
    }

    /**
     * Creates Zones for given Monitor and puts them into position Map
     *
     * @param monitorWidth - monitor width in px
     * @param monitorHeight - monitor height in px
     * @param insetSize - Size of Window insets
     */
    public void addMonitor(int monitorWidth, int monitorHeight, int insetSize) {
        monitors.add(new Monitor(monitorWidth, monitorHeight));
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

        int xCoord = leftEdge + ((rightEdge - leftEdge) / (monitorCols)) / 2;
        int xCoord_cpy = xCoord;
        int yCoord = topEdge + ((bottomEdge - topEdge) / (monitorRows)) / 2;

        int xStep = (rightEdge - leftEdge) / (monitorCols);
        int yStep = (bottomEdge - topEdge) / (monitorRows);

        xyCoords.add(new Point(xCoord, yCoord));

        for (int i = 1; i < monitorRows; i++) {
            for (int j = 1; j < monitorCols; j++) {
                xCoord += xStep;
                xyCoords.add(new Point(xCoord, yCoord));
            }
            yCoord += yStep;
            xCoord = xCoord_cpy;
            xyCoords.add(new Point(xCoord, yCoord));
        }
        for (int j = 1; j < monitorCols; j++) {
            xCoord += xStep;
            xyCoords.add(new Point(xCoord, yCoord));
        }

        position.put(numMonitors, xyCoords);
    }

    /**
     * Adds new Trial to the trial ArrayList
     *
     * @param monitorStart - startmonitor
     * @param monitorGoal - endmonitor
     * @param posStart - position in monitorzones (starting with 0 top left and going right)
     * @param posGoal - goal position in monitorzones
     * @param goalWidth - width of the goalcircle / -rectangle in px
     * @param goalHeight - height of the goalcircle / -rectangle in px
     */
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

    /**
     * Here you can add more Trials.
     * The trialorder for the following blocks gets generated below.
     */
    public void generateTrials() {
        if(!Config.RECIPROCAL) {
            addTrial(1, 3, 5, 12, 10, 10);
            addTrial(3, 1, 24, 14, 10, 10);
            addTrial(1, 2, 24, 14, 10, 10);
            addTrial(2, 1, 24, 14, 10, 10);
            addTrial(1, 3, 24, 14, 10, 10);
            addTrial(3, 2, 24, 14, 10, 10);
            addTrial(2, 3, 24, 14, 10, 10);
            addTrial(3, 1, 24, 14, 10, 10);

            blocks.add(trials);

            generateBlocks();

        } else {
            addReciproceTrials();
        }
    }

    /**
     * Adds a set of predefined Reciproce Trials
     *
     */
    private void addReciproceTrials() {
        addTrial(2,3,2,7,10,10);
        addTrial(3,2,1,4,10,10);
        addTrial(2,3,8,6,10,10);
        addTrial(3,2,8,3,10,10);
        addTrial(2,3,1,8,10,10);
        addTrial(3,2,5,7,10,10);
        addTrial(2,3,12,10,10,10);
        addTrial(3,2,5,16,10,10);
        addTrial(2,3,15,20,10,10);
        addTrial(3,2,17,19,10,10);
        addTrial(2,3,11,16,10,10);
        addTrial(3,2,10,18,10,10);

        ArrayList<Constellation> trialCopy = new ArrayList<>(trials);
        blocks.add(trialCopy);

        Collections.reverse(trials);

        trialCopy = new ArrayList<>(trials);
        blocks.add(trialCopy);
        trials.clear();

        addTrial(1,3,2,15,10,10);
        addTrial(3,1,1,7,10,10);
        addTrial(1,3,3,5,10,10);
        addTrial(3,1,2,16,10,10);
        addTrial(1,3,8,12,10,10);
        addTrial(3,1,12,10,10,10);
        addTrial(1,3,14,15,10,10);
        addTrial(3,1,17,3,10,10);
        addTrial(1,3,4,22,10,10);
        addTrial(3,1,16,13,10,10);
        addTrial(1,3,20,24,10,10);
        addTrial(3,1,22,1,10,10);

        trialCopy = new ArrayList<>(trials);
        blocks.add(trialCopy);

        Collections.reverse(trials);

        trialCopy = new ArrayList<>(trials);
        blocks.add(trialCopy);
        trials.clear();
    }

    private void generateBlocks() {
        int startMonitor = 2;
        int startTrial = 0;

        for (int i = 1; i < Config.BLOCKS; i++) {
            ArrayList<Constellation> nextBlock = new ArrayList<>();
            ArrayList<ArrayList<Constellation>> seperateTrials = new ArrayList<>();

            // Get different startMonitors
            ArrayList<Integer> maxStart = new ArrayList<>();
            for (Constellation t : trials) {
                if (!maxStart.contains(t.getMonitorStart())) {
                    maxStart.add(t.getMonitorStart());
                }
            }

            //Seperate different startMonitors in seperate Lists
            for (int j = 1; j <= numMonitors; j++) {
                int finalX = j;
                ArrayList<Constellation> filteredTrials = trials.stream()
                        .filter(n -> n.getMonitorStart() == finalX)
                        .collect(Collectors.toCollection(ArrayList::new));
                Collections.shuffle(filteredTrials);
                seperateTrials.add(filteredTrials);
            }

            int constIndex = 0;

            if(startMonitor > numMonitors) {
                startMonitor = 1;
                startTrial++;
            }

            Constellation nextConst;
            if(startTrial >= seperateTrials.get(startMonitor - 1).size()) {
                nextConst = seperateTrials.get(startMonitor - 1).get(seperateTrials.get(startMonitor - 1).size() - 1);
            } else {
                nextConst = seperateTrials.get(startMonitor - 1).get(startTrial);
            }
            seperateTrials.get(startMonitor - 1).remove(nextConst);

            startMonitor++;

            while (nextConst != null) {
                nextBlock.add(nextConst);
                constIndex++;
                nextConst = getNextConstellation(seperateTrials, nextConst.getMonitorEnd(), constIndex);
            }

            for (ArrayList<Constellation> constellations : seperateTrials) {
                nextBlock.addAll(constellations);
            }

            blocks.add(nextBlock);
        }
        //Sort blocks
        blocks.sort(Comparator.comparingInt(o -> o.get(0).getMonitorStart()));

        //TODO: delete Debug
        for(int i = 0; i < blocks.size(); i++) {
            System.out.println("Blocknr.: " + i);
            for(Constellation c : blocks.get(i)) {
                System.out.println(c.getMonitorStart() + " - " + c.getMonitorEnd() + " | Trialnumber:" + c.getTrialNum());
            }
            System.out.println();
        }
    }

    /**
     * Tries to find a Constellation, where Startmonitor == previous Endmonitor.
     * Otherwise returns null.
     *
     * @param constellations - Array containing constellations arrays splitted by startmonitor
     * @param endMonitor - Previous Endmonitor
     */
    public Constellation getNextConstellation(ArrayList<ArrayList<Constellation>> constellations, int endMonitor, int constIndex) {
        Constellation nextConst = null;

        for (ArrayList<Constellation> constellation : constellations) {
            for (Constellation c : constellation) {
                if (c.getMonitorStart() == endMonitor) {
                    nextConst = c;
                    for (ArrayList<Constellation> block : blocks) {
                        if (!(block.get(constIndex - 1) == nextConst)) {
                            constellation.remove(c);
                            return nextConst;
                        }
                    }
                }
            }
            if(nextConst != null) {
                constellation.remove(nextConst);
                return nextConst;
            }
        }
        return null;
    }

    public ArrayList<ArrayList<Constellation>> getBlocks() {
        return blocks;
    }

    /**
     * Add errortrial back to trials.
     * @param trial - Constellation to push back
     */
    public void pushBackTrial(Constellation trial, int currentBlock) {
        //trials.add(trial);
        System.out.println("-------");
        System.out.println("Failed at trial " + trial.getTrialNum());

        Constellation trialToSwitch = null;

        StringBuilder oldOrder = new StringBuilder();
        for(Constellation c : blocks.get(currentBlock - 1)) {
            oldOrder.append(" ").append(c.getTrialNum());
            if(c.getMonitorStart() == trial.getMonitorStart() && c.getMonitorEnd() == trial.getMonitorEnd()) {
                trialToSwitch = c;
                int indexToSwitch = blocks.get(currentBlock - 1).indexOf(c);
                blocks.get(currentBlock - 1).set(indexToSwitch, trial);
            }
        }
        System.out.println("Old Order: " + oldOrder);

        blocks.get(currentBlock - 1).add(Objects.requireNonNullElse(trialToSwitch, trial));

        StringBuilder newOrder = new StringBuilder();
        for(Constellation c : blocks.get(currentBlock - 1)) {
            newOrder.append(" ").append(c.getTrialNum());
        }
        System.out.println("New Order: " + newOrder);
        System.out.println("-------");
    }

    /**
     * remove duplicates (errors duplicate the trials)
     */
    public void resetTrialblock() {
        ArrayList<Constellation> removedErrors = new ArrayList<>();
        for(int i = 0; i < trialNum; i++) {
            removedErrors.add(trials.get(i));
        }
        trials.clear();
        trials.addAll(removedErrors);

        for(ArrayList<Constellation> constellations : blocks) {
            ArrayList<Constellation> remErrors = new ArrayList<>();

            for(int i = 0; i < trialNum; i++) {
                remErrors.add(constellations.get(i));
            }

            constellations.clear();
            constellations.addAll(remErrors);
        }
    }

    public ArrayList<Monitor> getMonitors() {
        return monitors;
    }

    public void setPauseTrial(boolean pauseTrial) {
        this.pauseTrial = pauseTrial;
    }

    public boolean isPauseTrial() {
        return this.pauseTrial;
    }

    public boolean isResumeTrial() {
        return resumeTrial;
    }

    public void setResumeTrial(boolean resumeTrial) {
        this.resumeTrial = resumeTrial;
    }
}
