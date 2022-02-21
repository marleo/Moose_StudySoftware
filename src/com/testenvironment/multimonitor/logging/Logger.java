package com.testenvironment.multimonitor.logging;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Monitor;
import com.testenvironment.multimonitor.experiment.TrialBlocks;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Logger {

    private static Logger instance = null;
    private PrintWriter logFile;
    private final String testtype;

    private int participant;
    private int blockNumber;
    private int trialNumberShown;
    private int trialNumberInSet;
    private int distancePx;
    private String movementDirection;
    private int startMonitor;
    private int startMonitorWidth;
    private int startMonitorHeight;
    private int startWindowWidth;
    private int startWindowHeight;
    private int startPosX;
    private int startPosY;
    private int startCenterX;
    private int startCenterY;
    private int targetMonitor;
    private int targetMonitorWidth;
    private int targetMonitorHeight;
    private int targetWindowWidth;
    private int targetWindowHeight;
    private int targetPosX;
    private int targetPosY;
    private int targetCenterX;
    private int targetCenterY;
    private int startPointReleasedX;
    private int startPointReleasedY;
    private int targetPointReleasedX;
    private int targetPointReleasedY;
    private int targetPointPressedX;
    private int targetPointPressedY;
    private int rightSwipes;
    private int leftSwipes;
    private int upSwipes;
    private int downSwipes;
    private int errors;
    private long trialStartTime;
    private long trialEndTime;
    private long trialTime;
    private double pixelSize;
    private double distanceMM;
    private final boolean isTargetCircle;
    private int targetWidth;
    private int targetHeight;


    private Logger() {
        createLogFile();
        writeToLog(createHeaderString());
        this.participant = Config.USER_ID;
        this.trialNumberShown = 0;
        this.trialNumberInSet = 0;
        this.blockNumber = 0;
        this.distancePx = 0;
        this.movementDirection = "";
        this.testtype = Config.TESTTYPE;
        this.startMonitor = 0;
        this.startMonitorWidth = 0;
        this.startMonitorHeight = 0;
        this.startWindowWidth = 0;
        this.startWindowHeight = 0;
        this.startPosX = 0;
        this.startPosY = 0;
        this.startCenterX = 0;
        this.startCenterY = 0;
        this.targetMonitor = 0;
        this.targetMonitorWidth = 0;
        this.targetMonitorHeight = 0;
        this.targetWindowWidth = 0;
        this.targetWindowHeight = 0;
        this.targetPosX = 0;
        this.targetPosY = 0;
        this.targetCenterX = 0;
        this.targetCenterY = 0;
        this.isTargetCircle = Config.GOAL_IS_CIRCLE;
        this.targetWidth = 0;
        this.targetHeight = 0;
        this.startPointReleasedX = 0;
        this.startPointReleasedY = 0;
        this.targetPointReleasedX = 0;
        this.targetPointReleasedY = 0;
        this.targetPointPressedX = 0;
        this.targetPointPressedY = 0;
        this.leftSwipes = 0;
        this.rightSwipes = 0;
        this.upSwipes = 0;
        this.downSwipes = 0;
        this.errors = 0;
        this.trialStartTime = 0;
        this.trialEndTime = 0;
        this.trialTime = 0;
        this.pixelSize = 0;
        this.distanceMM = 0;
    }

    public static Logger getLogger() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void createLogFile() {
        try {
            while (new File(Config.LOG_PATH + Config.MOOSE_LOG + Config.USER_ID + ".txt").exists()) {
                Config.USER_ID++;
            }
            logFile = new PrintWriter(new FileWriter(Config.LOG_PATH + Config.MOOSE_LOG + Config.USER_ID + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createHeaderString() {
        return "participant" + ";" +
                "blockNumber" + ";" +
                "trialNumber" + ";" +
                "trialNumberInSet" + ";" +
                "movementDirection" + ";" +
                "testType" + ";" +
                "startMonitor" + ";" +
                "startMonitorWidth" + ";" +
                "startMonitorHeight" + ";" +
                "startWindowWidth" + ";" +
                "startWindowHeight" + ";" +
                "distancePx" + ";" +
                "distanceMM" + ";" +
                "startPosX" + ";" +
                "startPosY" + ";" +
                "startCenterX" + ";" +
                "startCenterY" + ";" +
                "targetMonitor" + ";" +
                "targetMonitorWidth" + ";" +
                "targetMonitorHeight" + ";" +
                "targetWindowWidth" + ";" +
                "targetWindowHeight" + ";" +
                "targetPosX" + ";" +
                "targetPosY" + ";" +
                "targetCenterX" + ";" +
                "targetCenterY" + ";" +
                "isTargetCircle" + ";" +
                "targetWidth" + ";" +
                "targetHeight" + ";" +
                "startPointReleasedX" + ";" +
                "startPointReleasedY" + ";" +
                "targetPointPressedX" + ";" +
                "targetPointPressedY" + ";" +
                "targetPointReleasedX" + ";" +
                "targetPointReleasedY" + ";" +
                "leftSwipes" + ";" +
                "rightSwipes" + ";" +
                "upSwipes" + ";" +
                "downSwipes" + ";" +
                "errors" + ";" +
                "trialStartTime" + ";" +
                "trialEndTime" + ";" +
                "trialTime";
    }

    public void generateLogString() {
        String logString = participant + ";" +
                blockNumber + ";" +
                trialNumberShown + ";" +
                trialNumberInSet + ";" +
                movementDirection + ";" +
                testtype + ";" +
                startMonitor + ";" +
                startMonitorWidth + ";" +
                startMonitorHeight + ";" +
                startWindowWidth + ";" +
                startWindowHeight + ";" +
                distancePx + ";" +
                distanceMM + ";" +
                startPosX + ";" +
                startPosY + ";" +
                startCenterX + ";" +
                startCenterY + ";" +
                targetMonitor + ";" +
                targetMonitorWidth + ";" +
                targetMonitorHeight + ";" +
                targetWindowWidth + ";" +
                targetWindowHeight + ";" +
                targetPosX + ";" +
                targetPosY + ";" +
                targetCenterX + ";" +
                targetCenterY + ";" +
                isTargetCircle + ";" +
                targetWidth + ";" +
                targetHeight + ";" +
                startPointReleasedX + ";" +
                startPointReleasedY + ";" +
                targetPointReleasedX + ";" +
                targetPointReleasedY + ";" +
                targetPointPressedX + ";" +
                targetPointPressedY + ";" +
                leftSwipes + ";" +
                rightSwipes + ";" +
                upSwipes + ";" +
                downSwipes + ";" +
                errors + ";" +
                trialStartTime + ";" +
                trialEndTime + ";" +
                trialTime;
        writeToLog(logString);
    }

    public void writeToLog(String log) {
        String fileName = Config.LOG_PATH + Config.MOOSE_LOG + Config.USER_ID + ".txt";
        log = log + "\n";
        Path path = Paths.get(fileName);

        try {
            Files.writeString(path, log, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endLog() {
        System.out.println("Logging ended");
        logFile.close();
    }

    public void setStartEndDistance() {
        boolean horizontal = Config.MONITOR_ORIENTATION;
        int start = startMonitor;
        int end = targetMonitor;
        int startBtnX = startCenterX;
        int endBtnX = targetCenterX;
        int startBtnY = startCenterY;
        int endBtnY = targetCenterY;
        boolean edited = false;
        ArrayList<Monitor> monitors = TrialBlocks.getTrialblocks().getMonitors();

        if(horizontal) {
            if(start > end) {
                int temp = start;
                start = end;
                end = temp;

                temp = startBtnX;
                startBtnX = endBtnX;
                endBtnX = temp;
                this.movementDirection = "LEFT";
                edited = true;
            }
            if(start < end) {
                if(!edited) {
                    this.movementDirection = "RIGHT";
                }
                int addX = 0;
                ArrayList<Integer> monitorsBetween = new ArrayList<>();
                if(start + 1 != end) {
                    for(int i = start + 1; i < end; i++) {
                        monitorsBetween.add(i);
                    }
                    for(int m : monitorsBetween) {
                        addX += monitors.get(m).getMonitorWidth();
                    }
                }
                int distX = monitors.get(start - 1).getMonitorWidth() - startBtnX + addX + endBtnX;

                int distY = startBtnY - endBtnY;
                setDistancePx(calculateEuclidianDistance(distX, distY));
                setDistanceMM(calculateEuclidianDistance(distX, distY) * pixelSize);

            }
        } else { //vertical
            if(end < start) {
                int temp = start;
                start = end;
                end = temp;

                temp = startBtnY;
                startBtnY = endBtnY;
                endBtnY = temp;

                this.movementDirection = "UP";
                edited = true;
            }
            if(start < end) {
                if(!edited) {
                    this.movementDirection = "DOWN";
                }
                int addY = 0;
                ArrayList<Integer> monitorsBetween = new ArrayList<>();
                if(start + 1 != end) {
                    for(int i = start + 1; i < end; i++) {
                        monitorsBetween.add(i);
                    }
                    for(int m : monitorsBetween) {
                        addY += monitors.get(m).getMonitorHeight();
                    }
                }
                int distY = monitors.get(start - 1).getMonitorHeight() - startBtnY + addY + endBtnY;
                int distX = targetCenterX - startCenterX;
                setDistancePx(calculateEuclidianDistance(distX, distY));
                setDistanceMM(calculateEuclidianDistance(distX, distY) * pixelSize);
            }
        }
    }

    public void setStartEndDistanceMM() {
        distanceMM = distancePx * pixelSize;
    }

    private int calculateEuclidianDistance(int x, int y) {
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void incRightSwipe() {
       this.rightSwipes++;
    }

    public void incLeftSwipe() {
        this.leftSwipes++;
    }

    public void incUpSwipes() {
        this.upSwipes++;
    }

    public void incDownSwipes() {
        this.downSwipes++;
    }

    public void resetRightSwipes() {
        this.rightSwipes = 0;
    }

    public void resetLeftSwipes() {
        this.leftSwipes = 0;
    }

    public void resetUpSwipes() {
        this.upSwipes = 0;
    }

    public void resetDownSwipes() {
        this.downSwipes = 0;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public void setDistanceMM(double distanceMM) {
        this.distanceMM = distanceMM;
    }

    public double getPixelSize() {
        return this.pixelSize;
    }

    public void setPixelSize(double pixelSize) {
        this.pixelSize = pixelSize;
    }

    public void setTrialNumberInSet(int trialNumberInSet) {
        this.trialNumberInSet = trialNumberInSet;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public void setTrialNumberShown(int trialNumberShown) {
        this.trialNumberShown = trialNumberShown;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getDistancePx() {
        return distancePx;
    }

    public void setDistancePx(int distancePx) {
        this.distancePx = distancePx;
    }

    public void setMovementDirection(String movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void setStartMonitor(int startMonitor) {
        this.startMonitor = startMonitor;
    }

    public void setStartPosX(int startPosX) {
        this.startPosX = startPosX;
    }

    public void setStartPosY(int startPosY) {
        this.startPosY = startPosY;
    }

    public void setStartCenterX(int startCenterX) {
        this.startCenterX = startCenterX;
    }

    public void setStartCenterY(int startCenterY) {
        this.startCenterY = startCenterY;
    }

    public void setTargetMonitor(int targetMonitor) {
        this.targetMonitor = targetMonitor;
    }

    public void setTargetPosX(int targetPosX) {
        this.targetPosX = targetPosX;
    }

    public void setTargetPosY(int targetPosY) {
        this.targetPosY = targetPosY;
    }

    public void setTargetCenterX(int targetCenterX) {
        this.targetCenterX = targetCenterX;
    }

    public void setTargetCenterY(int targetCenterY) {
        this.targetCenterY = targetCenterY;
    }

    public void setStartPointReleasedX(int startPointReleasedX) {
        this.startPointReleasedX = startPointReleasedX;
    }

    public void setStartPointReleasedY(int startPointReleasedY) {
        this.startPointReleasedY = startPointReleasedY;
    }

    public void setTargetPointReleasedX(int targetPointReleasedX) {
        this.targetPointReleasedX = targetPointReleasedX;
    }

    public void setTargetPointReleasedY(int targetPointReleasedY) {
        this.targetPointReleasedY = targetPointReleasedY;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void setTrialStartTime(long trialStartTime) {
        this.trialStartTime = trialStartTime;
    }

    public void setTrialEndTime(long trialEndTime) {
        this.trialEndTime = trialEndTime;
    }

    public void setTrialTime(long trialTime) {
        this.trialTime = trialTime;
    }

    public void setStartMonitorWidth(int startMonitorWidth) {
        this.startMonitorWidth = startMonitorWidth;
    }

    public void setStartMonitorHeight(int startMonitorHeight) {
        this.startMonitorHeight = startMonitorHeight;
    }

    public void setTargetMonitorWidth(int targetMonitorWidth) {
        this.targetMonitorWidth = targetMonitorWidth;
    }

    public void setTargetMonitorHeight(int targetMonitorHeight) {
        this.targetMonitorHeight = targetMonitorHeight;
    }

    public void setStartWindowWidth(int startWindowWidth) {
        this.startWindowWidth = startWindowWidth;
    }

    public void setStartWindowHeight(int startWindowHeight) {
        this.startWindowHeight = startWindowHeight;
    }

    public void setTargetWindowWidth(int targetWindowWidth) {
        this.targetWindowWidth = targetWindowWidth;
    }

    public void setTargetWindowHeight(int targetWindowHeight) {
        this.targetWindowHeight = targetWindowHeight;
    }

    public void setTargetPointPressedX(int targetPointPressedX) {
        this.targetPointPressedX = targetPointPressedX;
    }

    public void setTargetPointPressedY(int targetPointPressedY) {
        this.targetPointPressedY = targetPointPressedY;
    }


}
