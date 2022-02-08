package com.testenvironment.multimonitor.moose;

import com.testenvironment.multimonitor.experiment.Monitor;
import com.testenvironment.multimonitor.experiment.TrialBlocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Robot {
    private static Robot instance = null;

    public static Robot getRobot() {
        if(instance == null) {
            instance = new Robot();
        }
        return instance;
    }

    public void moveRobot(String swipeDirection) throws AWTException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        int currentScreenIndex = Arrays.asList(gs).indexOf(MouseInfo.getPointerInfo().getDevice());
        GraphicsDevice currentScreen = gs[currentScreenIndex];
        GraphicsDevice nextScreen = null;
        int prevScreenWidths = 0;
        int prevScreenHeights = 0;

        switch (swipeDirection) {
            case "swipeRight" -> {
                if (currentScreenIndex + 1 < gs.length) {
                    nextScreen = gs[currentScreenIndex + 1];
                    for (GraphicsDevice g : gs) {
                        if (g == nextScreen)
                            break;
                        prevScreenWidths += g.getDisplayMode().getWidth();
                    }
                }
                if (nextScreen != null) {
                    fixedMouseMoveHorizontal(nextScreen, prevScreenWidths);
                }
            }
            case "swipeLeft" -> {
                if (currentScreenIndex - 1 >= 0) {
                    nextScreen = gs[currentScreenIndex - 1];
                    for (GraphicsDevice g : gs) {
                        if (g == nextScreen)
                            break;
                        prevScreenWidths += g.getDisplayMode().getWidth();
                    }
                }
                if (nextScreen != null) {
                    fixedMouseMoveHorizontal(nextScreen, prevScreenWidths);
                }
            }
            case "swipeUp" -> {
                if (currentScreenIndex + 1 < gs.length) {
                    nextScreen = gs[currentScreenIndex + 1];
                    for (GraphicsDevice g : gs) {
                        if (g == nextScreen)
                            break;
                        prevScreenHeights += g.getDisplayMode().getHeight();
                    }
                    if (nextScreen != null) {
                        fixedMouseMoveVertical(nextScreen, prevScreenHeights);
                    }
                }
            }
            case "swipeDown" -> {
                if (currentScreenIndex - 1 >= 0) {
                    nextScreen = gs[currentScreenIndex - 1];
                    for (GraphicsDevice g : gs) {
                        if (g == nextScreen)
                            break;
                        prevScreenHeights += g.getDisplayMode().getHeight();
                    }
                    if (nextScreen != null) {
                        fixedMouseMoveVertical(nextScreen, prevScreenHeights);
                    }
                }
            }
        }
    }

    private void fixedMouseMoveVertical(GraphicsDevice nextScreen, int prevScreenHeights) throws AWTException {
        java.awt.Robot robot = new java.awt.Robot(nextScreen);

        int width = nextScreen.getDisplayMode().getWidth();
        int height = nextScreen.getDisplayMode().getHeight();

        int x = width/2;
        int y = -prevScreenHeights + height/2;

        for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != x || //First move it to the correct screen
                MouseInfo.getPointerInfo().getLocation().getY() != y) &&
                count < 100; count++) {
            robot.mouseMove(x, y);
        }
        x = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getWidth() / 2; //Then put x to mid of that monitor

        fixCurrentMonitorRes(robot, x, y); //Adjust for current Monitors Width
    }

    private void fixedMouseMoveHorizontal(GraphicsDevice nextScreen, int prevScreenWidths) throws AWTException {
        java.awt.Robot robot = new java.awt.Robot(nextScreen);

        int width = nextScreen.getDisplayMode().getWidth();
        int height = nextScreen.getDisplayMode().getHeight();

        int maxHeight = 0;
        ArrayList<Monitor> monitors = TrialBlocks.getTrialblocks().getMonitors();

        for(Monitor m : monitors) { //Find monitor with max height
            if(m.getMonitorHeight() < maxHeight) {
                maxHeight = m.getMonitorHeight();
            }
        }

        int x = prevScreenWidths + width/2;
        int y = height/2 + Math.abs(maxHeight - nextScreen.getDisplayMode().getHeight());

        for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != x || //First move it to the correct screen
                MouseInfo.getPointerInfo().getLocation().getY() != y) &&
                count < 100; count++) {
            robot.mouseMove(x, y);
        }
        y = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getHeight() / 2; //Then put y to mid of that monitor

        fixCurrentMonitorRes(robot, x, y); //Adjust for current Monitors Height
    }

    private void fixCurrentMonitorRes(java.awt.Robot robot, int x, int y) { //Workaround because AWTRobot is broken ATM
        for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != x ||
                MouseInfo.getPointerInfo().getLocation().getY() != y) &&
                count < 100; count++) {
            robot.mouseMove(x, y);
        }

        int currentPosX = MouseInfo.getPointerInfo().getLocation().x;
        int currentPosY = MouseInfo.getPointerInfo().getLocation().y;

        System.out.println("X: " + currentPosX + " | Y: " + currentPosY);
    }
}
