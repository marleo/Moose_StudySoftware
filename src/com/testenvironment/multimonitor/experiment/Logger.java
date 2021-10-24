package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

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

    private int participant;
    private int trialNumber;
    private int blockNumber;
    private int distancePx;
    private String movementDirection;
    private int startMonitor;
    private int startMonitorWidth;
    private int startMonitorHeight;
    private int startPosX;
    private int startPosY;
    private int startCenterX;
    private int startCenterY;
    private int targetMonitor;
    private int targetMonitorWidth;
    private int targetMonitorHeight;
    private int targetPosX;
    private int targetPosY;
    private int targetCenterX;
    private int targetCenterY;
    private int startPointPressedX;
    private int startPointPressedY;
    private int targetPointPressedX;
    private int targetPointPressedY;
    private int hit;
    private long trialStartTime;
    private long trialEndTime;
    private long trialTime;


    private Logger() {
        createLogFile(Config.LOG_NAME);
        writeToLog(createHeaderString(), Config.LOG_NAME);
        this.participant = Config.USER_ID;
        this.trialNumber = 0;
        this.blockNumber = 0;
        this.distancePx = 0;
        this.movementDirection = "";
        this.testtype = Config.TESTTYPE;
        this.startMonitor = 0;
        this.startMonitorWidth = 0;
        this.startMonitorHeight = 0;
        this.startPosX = 0;
        this.startPosY = 0;
        this.startCenterX = 0;
        this.startCenterY = 0;
        this.targetMonitor = 0;
        this.targetMonitorWidth = 0;
        this.targetMonitorHeight = 0;
        this.targetPosX = 0;
        this.targetPosY = 0;
        this.targetCenterX = 0;
        this.targetCenterY = 0;
        this.startPointPressedX = 0;
        this.startPointPressedY = 0;
        this.targetPointPressedX = 0;
        this.targetPointPressedY = 0;
        this.hit = 0;
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

    public void createLogFile(String logName) {
        try {
            logFile = new PrintWriter(new FileWriter(Config.LOG_PATH + logName + Config.USER_ID + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void logMouseEvent(MouseEvent e) {
//        if(logFile != null) {
//            logFile.println(e.paramString());
//        }
//    }

    private String createHeaderString() {
        return "participant" + ";" +
                "trialNumber" + ";" +
                "blockNumber" + ";" +
                "distancePx" + ";" +
                "movementDirection" + ";" +
                "testtype" + ";" +
                "startMonitor" + ";" +
                "startMonitorWidth" + ";" +
                "startMonitorHeight" + ";" +
                "startPosX" + ";" +
                "startPosY" + ";" +
                "startCenterX" + ";" +
                "startCenterY" + ";" +
                "targetMonitor" + ";" +
                "targetMonitorWidth" + ";" +
                "targetMonitorHeight" + ";" +
                "targetPosX" + ";" +
                "targetPosY" + ";" +
                "targetCenterX" + ";" +
                "targetCenterY" + ";" +
                "startPointPressedX" + ";" +
                "startPointPressedY" + ";" +
                "targetPointPressedX" + ";" +
                "targetPointPressedY" + ";" +
                "hit" + ";" +
                "trialStartTime" + ";" +
                "trialEndTime" + ";" +
                "trialTime";
    }

    public void generateLogString(){
        String logString = participant + ";" +
                trialNumber + ";" +
                blockNumber + ";" +
                distancePx + ";" +
                movementDirection + ";" +
                testtype + ";" +
                startMonitor + ";" +
                startMonitorWidth + ";" +
                startMonitorHeight + ";" +
                startPosX + ";" +
                startPosY + ";" +
                startCenterX + ";" +
                startCenterY + ";" +
                targetMonitor + ";" +
                targetMonitorWidth + ";" +
                targetMonitorHeight + ";" +
                targetPosX + ";" +
                targetPosY + ";" +
                targetCenterX + ";" +
                targetCenterY + ";" +
                startPointPressedX + ";" +
                startPointPressedY + ";" +
                targetPointPressedX + ";" +
                targetPointPressedY + ";" +
                hit + ";" +
                trialStartTime + ";" +
                trialEndTime + ";" +
                trialTime;
        writeToLog(logString, Config.LOG_NAME);
    }

    public void writeToLog(String log, String logName) {
        String fileName = Config.LOG_PATH + logName + Config.USER_ID + ".txt";
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

    public int getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(int trialNumber) {
        this.trialNumber = trialNumber;
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
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
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


}
