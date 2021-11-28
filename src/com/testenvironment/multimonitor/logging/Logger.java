package com.testenvironment.multimonitor.logging;

import com.testenvironment.multimonitor.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {

    private static Logger instance = null;
    private PrintWriter logFile;
    private String testtype;
    private boolean headerWritten;

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
    private int startPointPressedX;
    private int startPointPressedY;
    private int targetPointPressedX;
    private int targetPointPressedY;
    private int errors;
    private long trialStartTime;
    private long trialEndTime;
    private long trialTime;


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
        this.startPointPressedX = 0;
        this.startPointPressedY = 0;
        this.targetPointPressedX = 0;
        this.targetPointPressedY = 0;
        this.errors = 0;
        this.trialStartTime = 0;
        this.trialEndTime = 0;
        this.trialTime = 0;
    }

    public static Logger getLogger() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void createLogFile() {
        try {
            while(new File(Config.LOG_PATH + Config.MOOSE_LOG + Config.USER_ID + ".txt").exists()) {
                Config.USER_ID ++;
            }
            logFile = new PrintWriter(new FileWriter(Config.LOG_PATH + Config.MOOSE_LOG + Config.USER_ID + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createHeaderString() {
        return "participant" + ";" +
                "trialNumber" + ";" +
                "blockNumber" + ";" +
                "trialNumberInSet" + ";" +
                "distancePx" + ";" +
                "movementDirection" + ";" +
                "testType" + ";" +
                "startMonitor" + ";" +
                "startMonitorWidth" + ";" +
                "startMonitorHeight" + ";" +
                "startWindowWidth" + ";" +
                "startWindowHeight" + ";" +
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
                "startPointPressedX" + ";" +
                "startPointPressedY" + ";" +
                "targetPointPressedX" + ";" +
                "targetPointPressedY" + ";" +
                "errors" + ";" +
                "trialStartTime" + ";" +
                "trialEndTime" + ";" +
                "trialTime";
    }

    public void generateLogString(){

        if(this.targetMonitor > this.startMonitor) {
            this.movementDirection = "Right";
        } else {
            this.movementDirection = "Left";
        }

        String logString = participant + ";" +
                trialNumberShown + ";" +
                blockNumber + ";" +
                trialNumberInSet + ";" +
                distancePx + ";" +
                movementDirection + ";" +
                testtype + ";" +
                startMonitor + ";" +
                startMonitorWidth + ";" +
                startMonitorHeight + ";" +
                startWindowWidth + ";" +
                startWindowHeight + ";" +
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
                startPointPressedX + ";" +
                startPointPressedY + ";" +
                targetPointPressedX + ";" +
                targetPointPressedY + ";" +
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

    public void setTrialNumberInSet(int trialNumberInSet) {
        this.trialNumberInSet = trialNumberInSet;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public int getTrialNumberShown() {
        return trialNumberShown;
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

    public String getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(String movementDirection) {
        this.movementDirection = movementDirection;
    }

    public int getStartMonitor() {
        return startMonitor;
    }

    public void setStartMonitor(int startMonitor) {
        this.startMonitor = startMonitor;
    }

    public int getStartPosX() {
        return startPosX;
    }

    public void setStartPosX(int startPosX) {
        this.startPosX = startPosX;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public void setStartPosY(int startPosY) {
        this.startPosY = startPosY;
    }

    public int getStartCenterX() {
        return startCenterX;
    }

    public void setStartCenterX(int startCenterX) {
        this.startCenterX = startCenterX;
    }

    public int getStartCenterY() {
        return startCenterY;
    }

    public void setStartCenterY(int startCenterY) {
        this.startCenterY = startCenterY;
    }

    public int getTargetMonitor() {
        return targetMonitor;
    }

    public void setTargetMonitor(int targetMonitor) {
        this.targetMonitor = targetMonitor;
    }

    public int getTargetPosX() {
        return targetPosX;
    }

    public void setTargetPosX(int targetPosX) {
        this.targetPosX = targetPosX;
    }

    public int getTargetPosY() {
        return targetPosY;
    }

    public void setTargetPosY(int targetPosY) {
        this.targetPosY = targetPosY;
    }

    public int getTargetCenterX() {
        return targetCenterX;
    }

    public void setTargetCenterX(int targetCenterX) {
        this.targetCenterX = targetCenterX;
    }

    public int getTargetCenterY() {
        return targetCenterY;
    }

    public void setTargetCenterY(int targetCenterY) {
        this.targetCenterY = targetCenterY;
    }

    public int getStartPointPressedX() {
        return startPointPressedX;
    }

    public void setStartPointPressedX(int startPointPressedX) {
        this.startPointPressedX = startPointPressedX;
    }

    public int getStartPointPressedY() {
        return startPointPressedY;
    }

    public void setStartPointPressedY(int startPointPressedY) {
        this.startPointPressedY = startPointPressedY;
    }

    public int getTargetPointPressedX() {
        return targetPointPressedX;
    }

    public void setTargetPointPressedX(int targetPointPressedX) {
        this.targetPointPressedX = targetPointPressedX;
    }

    public int getTargetPointPressedY() {
        return targetPointPressedY;
    }

    public void setTargetPointPressedY(int targetPointPressedY) {
        this.targetPointPressedY = targetPointPressedY;
    }

    public int isHit() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public long getTrialStartTime() {
        return trialStartTime;
    }

    public void setTrialStartTime(long trialStartTime) {
        this.trialStartTime = trialStartTime;
    }

    public long getTrialEndTime() {
        return trialEndTime;
    }

    public void setTrialEndTime(long trialEndTime) {
        this.trialEndTime = trialEndTime;
    }

    public long getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(long trialTime) {
        this.trialTime = trialTime;
    }

    public int getStartMonitorWidth() {
        return startMonitorWidth;
    }

    public void setStartMonitorWidth(int startMonitorWidth) {
        this.startMonitorWidth = startMonitorWidth;
    }

    public int getStartMonitorHeight() {
        return startMonitorHeight;
    }

    public void setStartMonitorHeight(int startMonitorHeight) {
        this.startMonitorHeight = startMonitorHeight;
    }

    public int getTargetMonitorWidth() {
        return targetMonitorWidth;
    }

    public void setTargetMonitorWidth(int targetMonitorWidth) {
        this.targetMonitorWidth = targetMonitorWidth;
    }

    public int getTargetMonitorHeight() {
        return targetMonitorHeight;
    }

    public void setTargetMonitorHeight(int targetMonitorHeight) {
        this.targetMonitorHeight = targetMonitorHeight;
    }

    public int getStartWindowWidth() {
        return startWindowWidth;
    }

    public void setStartWindowWidth(int startWindowWidth) {
        this.startWindowWidth = startWindowWidth;
    }

    public int getStartWindowHeight() {
        return startWindowHeight;
    }

    public void setStartWindowHeight(int startWindowHeight) {
        this.startWindowHeight = startWindowHeight;
    }

    public int getTargetWindowWidth() {
        return targetWindowWidth;
    }

    public void setTargetWindowWidth(int targetWindowWidth) {
        this.targetWindowWidth = targetWindowWidth;
    }

    public int getTargetWindowHeight() {
        return targetWindowHeight;
    }

    public void setTargetWindowHeight(int targetWindowHeight) {
        this.targetWindowHeight = targetWindowHeight;
    }


}
