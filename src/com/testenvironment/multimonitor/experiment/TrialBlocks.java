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
        if(Objects.equals(Config.TRIALTYPE, "Random")) {
//            addTrial(1, 3, 1, 2, 10, 10);
//            addTrial(3, 1, 3, 4, 10, 10);
//            addTrial(1, 2, 5, 6, 10, 10);
//            addTrial(2, 1, 7, 8, 10, 10);
//            addTrial(1, 3, 9, 10, 10, 10);
//            addTrial(3, 2, 11, 12, 10, 10);
//            addTrial(2, 3, 13, 14, 10, 10);
//            addTrial(3, 1, 15, 16, 10, 10);

            addTrial(1, 2, 24, 14, 38, 38);
            addTrial(2, 1, 24, 14, 38, 38);

            blocks.add(trials);

            generateBlocks();
            Collections.shuffle(blocks);

        } else if(Objects.equals(Config.TRIALTYPE, "Reciprocal")){
            addReciproceTrials();
        } else if(Objects.equals(Config.TRIALTYPE, "Hardcoded")) {
            Config.PAUSE_AFTER_BLOCKNR = 1;
            Config.MONITOR_ZONES = new int[]{4, 6};
            addHardcodedTrials();
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

    private void addHardcodedTrials() {
        int goalHeight = 38;
        int goalWidth = 38;

        switch (Config.TRIALSEQUENCE) {
            case "Sequence72_Alternative_1" -> {
                addTrial(1, 2, 7, 1, goalWidth, goalHeight);
                addTrial(2, 3, 3, 9, goalWidth, goalHeight);
                addTrial(3, 2, 11, 9, goalWidth, goalHeight);
                addTrial(2, 1, 8, 13, goalWidth, goalHeight);
                addTrial(1, 2, 12, 15, goalWidth, goalHeight);
                addTrial(2, 1, 20, 15, goalWidth, goalHeight);
                addTrial(1, 3, 21, 14, goalWidth, goalHeight);
                addTrial(3, 2, 21, 15, goalWidth, goalHeight);
                addTrial(2, 3, 14, 7, goalWidth, goalHeight);
                addTrial(3, 1, 6, 4, goalWidth, goalHeight);
                addTrial(1, 2, 3, 11, goalWidth, goalHeight);
                addTrial(2, 3, 10, 17, goalWidth, goalHeight);
                addTrial(3, 1, 11, 0, goalWidth, goalHeight);
                addTrial(1, 3, 6, 16, goalWidth, goalHeight);
                addTrial(3, 2, 23, 13, goalWidth, goalHeight);
                addTrial(2, 1, 7, 1, goalWidth, goalHeight);
                addTrial(1, 2, 2, 11, goalWidth, goalHeight);
                addTrial(2, 3, 17, 19, goalWidth, goalHeight);

                ArrayList<Constellation> trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();

                addTrial(3, 1, 13, 18, goalWidth, goalHeight);
                addTrial(1, 2, 12, 19, goalWidth, goalHeight);
                addTrial(2, 1, 21, 7, goalWidth, goalHeight);
                addTrial(1, 3, 0, 8, goalWidth, goalHeight);
                addTrial(3, 2, 7, 5, goalWidth, goalHeight);
                addTrial(2, 1, 4, 9, goalWidth, goalHeight);
                addTrial(1, 3, 7, 18, goalWidth, goalHeight);
                addTrial(3, 1, 6, 12, goalWidth, goalHeight);
                addTrial(1, 3, 14, 2, goalWidth, goalHeight);
                addTrial(3, 1, 9, 8, goalWidth, goalHeight);
                addTrial(1, 3, 10, 6, goalWidth, goalHeight);
                addTrial(3, 1, 8, 2, goalWidth, goalHeight);
                addTrial(1, 2, 4, 7, goalWidth, goalHeight);
                addTrial(2, 1, 12, 19, goalWidth, goalHeight);
                addTrial(1, 2, 13, 15, goalWidth, goalHeight);
                addTrial(2, 3, 17, 11, goalWidth, goalHeight);
                addTrial(3, 2, 4, 1, goalWidth, goalHeight);
                addTrial(2, 3, 0, 5, goalWidth, goalHeight);

                trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();

                addTrial(3, 1, 17, 16, goalWidth, goalHeight);
                addTrial(1, 3, 11, 0, goalWidth, goalHeight);
                addTrial(3, 1, 2, 5, goalWidth, goalHeight);
                addTrial(1, 3, 3, 10, goalWidth, goalHeight);
                addTrial(3, 2, 15, 23, goalWidth, goalHeight);
                addTrial(2, 3, 21, 13, goalWidth, goalHeight);
                addTrial(3, 2, 14, 17, goalWidth, goalHeight);
                addTrial(2, 1, 11, 17, goalWidth, goalHeight);
                addTrial(1, 3, 23, 22, goalWidth, goalHeight);
                addTrial(3, 1, 23, 20, goalWidth, goalHeight);
                addTrial(1, 3, 18, 12, goalWidth, goalHeight);
                addTrial(3, 2, 18, 23, goalWidth, goalHeight);
                addTrial(2, 1, 16, 23, goalWidth, goalHeight);
                addTrial(1, 3, 17, 20, goalWidth, goalHeight);
                addTrial(3, 1, 22, 18, goalWidth, goalHeight);
                addTrial(1, 3, 20, 18, goalWidth, goalHeight);
                addTrial(3, 1, 12, 8, goalWidth, goalHeight);
                addTrial(1, 2, 6, 3, goalWidth, goalHeight);

                trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();

                addTrial(2, 3, 5, 3, goalWidth, goalHeight);
                addTrial(3, 1, 1, 10, goalWidth, goalHeight);
                addTrial(1, 3, 22, 16, goalWidth, goalHeight);
                addTrial(3, 1, 10, 22, goalWidth, goalHeight);
                addTrial(1, 2, 16, 9, goalWidth, goalHeight);
                addTrial(2, 3, 7, 23, goalWidth, goalHeight);
                addTrial(3, 1, 22, 14, goalWidth, goalHeight);
                addTrial(1, 3, 15, 0, goalWidth, goalHeight);
                addTrial(3, 1, 2, 4, goalWidth, goalHeight);
                addTrial(1, 3, 16, 20, goalWidth, goalHeight);
                addTrial(3, 1, 8, 6, goalWidth, goalHeight);
                addTrial(1, 3, 1, 4, goalWidth, goalHeight);
                addTrial(3, 1, 3, 10, goalWidth, goalHeight);
                addTrial(1, 2, 8, 3, goalWidth, goalHeight);
                addTrial(2, 1, 1, 3, goalWidth, goalHeight);
                addTrial(1, 3, 1, 14, goalWidth, goalHeight);
                addTrial(3, 1, 19, 14, goalWidth, goalHeight);
                addTrial(1, 3, 20, 10, goalWidth, goalHeight);

                trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();
                Config.BLOCKS = blocks.size();
            }
            case "Sequence72_Alternative_2" -> {
                addTrial(3, 2, 11, 23, goalWidth, goalHeight);
                addTrial(2, 1, 17, 4, goalWidth, goalHeight);
                addTrial(1, 3, 2, 5, goalWidth, goalHeight);
                addTrial(2, 1, 4, 1, goalWidth, goalHeight);
                addTrial(1, 2, 6, 21, goalWidth, goalHeight);
                addTrial(2, 1, 23, 15, goalWidth, goalHeight);
                addTrial(1, 2, 13, 18, goalWidth, goalHeight);
                addTrial(2, 3, 12, 4, goalWidth, goalHeight);
                addTrial(3, 1, 5, 20, goalWidth, goalHeight);
                addTrial(1, 3, 14, 12, goalWidth, goalHeight);
                addTrial(3, 1, 18, 16, goalWidth, goalHeight);
                addTrial(1, 3, 11, 15, goalWidth, goalHeight);
                addTrial(3, 1, 17, 13, goalWidth, goalHeight);
                addTrial(1, 3, 12, 6, goalWidth, goalHeight);
                addTrial(3, 2, 7, 9, goalWidth, goalHeight);
                addTrial(2, 3, 10, 1, goalWidth, goalHeight);
                addTrial(3, 2, 2, 5, goalWidth, goalHeight);
                addTrial(2, 3, 3, 21, goalWidth, goalHeight);

                ArrayList<Constellation> trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();

                addTrial(3, 2, 19, 20, goalWidth, goalHeight);
                addTrial(2, 1, 13, 17, goalWidth, goalHeight);
                addTrial(1, 3, 16, 22, goalWidth, goalHeight);
                addTrial(3, 2, 16, 12, goalWidth, goalHeight);
                addTrial(2, 3, 7, 8, goalWidth, goalHeight);
                addTrial(3, 1, 9, 8, goalWidth, goalHeight);
                addTrial(1, 2, 9, 2, goalWidth, goalHeight);
                addTrial(2, 3, 8, 21, goalWidth, goalHeight);
                addTrial(3, 1, 20, 21, goalWidth, goalHeight);
                addTrial(1, 2, 23, 19, goalWidth, goalHeight);
                addTrial(2, 1, 18, 14, goalWidth, goalHeight);
                addTrial(1, 2, 19, 1, goalWidth, goalHeight);
                addTrial(2, 1, 2, 19, goalWidth, goalHeight);
                addTrial(1, 2, 14, 10, goalWidth, goalHeight);
                addTrial(2, 1, 15, 11, goalWidth, goalHeight);
                addTrial(1, 2, 5, 0, goalWidth, goalHeight);
                addTrial(2, 1, 2, 10, goalWidth, goalHeight);
                addTrial(1, 3, 9, 6, goalWidth, goalHeight);

                trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();

                addTrial(3, 2, 0, 3, goalWidth, goalHeight);
                addTrial(2, 3, 5, 11, goalWidth, goalHeight);
                addTrial(3, 1, 10, 22, goalWidth, goalHeight);
                addTrial(1, 2, 20, 17, goalWidth, goalHeight);
                addTrial(2, 1, 22, 2, goalWidth, goalHeight);
                addTrial(1, 3, 4, 3, goalWidth, goalHeight);
                addTrial(3, 1, 4, 3, goalWidth, goalHeight);
                addTrial(1, 2, 5, 22, goalWidth, goalHeight);
                addTrial(2, 1, 15, 22, goalWidth, goalHeight);
                addTrial(1, 3, 23, 14, goalWidth, goalHeight);
                addTrial(3, 1, 15, 0, goalWidth, goalHeight);
                addTrial(1, 3, 6, 2, goalWidth, goalHeight);
                addTrial(3, 2, 1, 7, goalWidth, goalHeight);
                addTrial(2, 1, 9, 21, goalWidth, goalHeight);
                addTrial(1, 3, 22, 18, goalWidth, goalHeight);
                addTrial(3, 1, 20, 18, goalWidth, goalHeight);
                addTrial(1, 3, 20, 8, goalWidth, goalHeight);
                addTrial(3, 1, 7, 4, goalWidth, goalHeight);

                trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();

                addTrial(1, 3, 10, 20, goalWidth, goalHeight);
                addTrial(3, 1, 18, 7, goalWidth, goalHeight);
                addTrial(1, 3, 0, 7, goalWidth, goalHeight);
                addTrial(3, 1, 14, 2, goalWidth, goalHeight);
                addTrial(1, 2, 0, 11, goalWidth, goalHeight);
                addTrial(2, 3, 16, 11, goalWidth, goalHeight);
                addTrial(3, 1, 10, 23, goalWidth, goalHeight);
                addTrial(1, 3, 21, 22, goalWidth, goalHeight);
                addTrial(3, 1, 23, 18, goalWidth, goalHeight);
                addTrial(1, 3, 19, 23, goalWidth, goalHeight);
                addTrial(3, 1, 22, 7, goalWidth, goalHeight);
                addTrial(1, 3, 1, 15, goalWidth, goalHeight);
                addTrial(3, 1, 13, 6, goalWidth, goalHeight);
                addTrial(1, 3, 8, 13, goalWidth, goalHeight);
                addTrial(3, 1, 12, 17, goalWidth, goalHeight);
                addTrial(1, 3, 15, 13, goalWidth, goalHeight);
                addTrial(3, 1, 6, 12, goalWidth, goalHeight);
                addTrial(1, 3, 13, 16, goalWidth, goalHeight);

                trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();
                Config.BLOCKS = blocks.size();
            }
            case "Sequence18_1_MonitorJump" -> {
                System.out.println("Here-.-.-.-.-.-.-.-.-");
                addTrial(1, 2, 2, 3, goalWidth, goalHeight);
                addTrial(2, 1, 8, 2, goalWidth, goalHeight);
                addTrial(1, 2, 14, 17, goalWidth, goalHeight);
                addTrial(2, 1, 22, 14, goalWidth, goalHeight);
                addTrial(1, 2, 4, 1, goalWidth, goalHeight);
                addTrial(2, 1, 6, 4, goalWidth, goalHeight);
                addTrial(1, 2, 10, 11, goalWidth, goalHeight);
                addTrial(2, 1, 16, 10, goalWidth, goalHeight);
                addTrial(1, 2, 6, 9, goalWidth, goalHeight);
                addTrial(2, 1, 14, 6, goalWidth, goalHeight);
                addTrial(1, 2, 12, 13, goalWidth, goalHeight);
                addTrial(2, 1, 18, 12, goalWidth, goalHeight);
                addTrial(1, 2, 8, 7, goalWidth, goalHeight);
                addTrial(2, 1, 12, 8, goalWidth, goalHeight);
                addTrial(1, 2, 0, 5, goalWidth, goalHeight);
                addTrial(2, 1, 10, 0, goalWidth, goalHeight);
                addTrial(1, 2, 16, 15, goalWidth, goalHeight);
                addTrial(2, 1, 20, 16, goalWidth, goalHeight);

                ArrayList<Constellation> trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();
                Config.BLOCKS = blocks.size();
            }
            case "Sequence18_2_MonitorJump" -> {
                addTrial(1, 3, 9, 10, goalWidth, goalHeight);
                addTrial(3, 1, 5, 9, goalWidth, goalHeight);
                addTrial(1, 3, 7, 8, goalWidth, goalHeight);
                addTrial(3, 1, 3, 7, goalWidth, goalHeight);
                addTrial(1, 3, 13, 12, goalWidth, goalHeight);
                addTrial(3, 1, 7, 13, goalWidth, goalHeight);
                addTrial(1, 3, 17, 16, goalWidth, goalHeight);
                addTrial(3, 1, 11, 17, goalWidth, goalHeight);
                addTrial(1, 3, 23, 20, goalWidth, goalHeight);
                addTrial(3, 1, 15, 23, goalWidth, goalHeight);
                addTrial(1, 3, 15, 14, goalWidth, goalHeight);
                addTrial(3, 1, 9, 15, goalWidth, goalHeight);
                addTrial(1, 3, 19, 22, goalWidth, goalHeight);
                addTrial(3, 1, 17, 19, goalWidth, goalHeight);
                addTrial(1, 3, 11, 6, goalWidth, goalHeight);
                addTrial(3, 1, 1, 11, goalWidth, goalHeight);
                addTrial(1, 3, 21, 18, goalWidth, goalHeight);
                addTrial(3, 1, 13, 21, goalWidth, goalHeight);

                ArrayList<Constellation> trialCopy = new ArrayList<>(trials);
                blocks.add(trialCopy);
                trials.clear();
                Config.BLOCKS = blocks.size();
            }
        }
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
            for (int j = 1; j <= maxStart.size(); j++) {
                int finalX = j;
                ArrayList<Constellation> filteredTrials = trials.stream()
                        .filter(n -> n.getMonitorStart() == finalX)
                        .collect(Collectors.toCollection(ArrayList::new));
                if(Config.IS_SEEDED) {
                    Collections.shuffle(filteredTrials, new Random(Config.SEED));
                } else {
                    Collections.shuffle(filteredTrials);
                }
                seperateTrials.add(filteredTrials);
            }

            int constIndex = 0;

            if(startMonitor > maxStart.size()) {
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
        //blocks.sort(Comparator.comparingInt(o -> o.get(0).getMonitorStart()));

        //TODO: delete Debug
        for(int i = 0; i < blocks.size(); i++) {
            System.out.println("Blocknr.: " + i);
            //StringBuilder trialorder = new StringBuilder();
            for(Constellation c : blocks.get(i)) {
                System.out.println(c.getMonitorStart() + " - " + c.getMonitorEnd() + " | Trialnumber:" + c.getTrialNum());
                //trialorder.append(c.getTrialNum())           ;
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

        if(Config.ERROR_TO_END) {
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
        } else {
            blocks.get(currentBlock - 1).add(trial.getTrialNum(), trial); //repeat trial immediately
        }
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
