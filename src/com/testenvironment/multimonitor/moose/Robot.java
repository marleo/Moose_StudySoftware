package com.testenvironment.multimonitor.moose;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Monitor;
import com.testenvironment.multimonitor.experiment.TrialBlocks;
import com.testenvironment.multimonitor.logging.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Robot {
    private static Robot instance = null;
    private Logger logger = Logger.getLogger();

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
        GraphicsDevice nextScreen = null;
        int prevScreenWidths = 0;

        System.out.println("####### moveRobot");

        switch (swipeDirection) {
            case "swipeRight", "tapRight", "slideUp" -> {
                System.out.println("####### JUMP RIGHT");
                    switch (swipeDirection) {
                        case "swipeRight" -> logger.incRightSwipe();
                        case "tapRight" -> logger.incRightTap();
                        case "slideUp" -> logger.incUpSlide();
                    }
                if (currentScreenIndex + 1 < gs.length) {
                    nextScreen = gs[currentScreenIndex + 1];
                    for (GraphicsDevice g : gs) {
                        if (g == nextScreen)
                            break;
                        prevScreenWidths += g.getDisplayMode().getWidth();
                    }
                }
                if (nextScreen != null) {
                    if(Config.JUMPTOMID) {
                        fixedMouseMoveHorizontal(nextScreen, prevScreenWidths);
                    } else {
                        int currentMouseX = MouseInfo.getPointerInfo().getLocation().x;
                        int currentMouseY = MouseInfo.getPointerInfo().getLocation().y;

                        java.awt.Robot robot = new java.awt.Robot(nextScreen);

                        fixCurrentMonitorRes(robot, nextScreen.getDisplayMode().getWidth() + currentMouseX, currentMouseY);
                    }
                    logger.incMonitorJumps();
                    drawCustomMouse();
                    incrementSwipeCount();
                }
            }
            case "swipeLeft", "tapLeft", "slideDown" -> {
                System.out.println("####### JUMP LEFT");

                switch (swipeDirection) {
                        case "swipeLeft" -> logger.incLeftSwipe();
                        case "tapLeft" -> logger.incLeftTap();
                        case "slideDown" -> logger.incDownSlide();
                    }
                if (currentScreenIndex - 1 >= 0) {
                    nextScreen = gs[currentScreenIndex - 1];
                    for (GraphicsDevice g : gs) {
                        if (g == nextScreen)
                            break;
                        prevScreenWidths += g.getDisplayMode().getWidth();
                    }
                }
                if (nextScreen != null) {
                    if(Config.JUMPTOMID) {
                        fixedMouseMoveHorizontal(nextScreen, prevScreenWidths);
                    } else {
                        int currentMouseX = MouseInfo.getPointerInfo().getLocation().x;
                        int currentMouseY = MouseInfo.getPointerInfo().getLocation().y;

                        java.awt.Robot robot = new java.awt.Robot(nextScreen);
                        fixCurrentMonitorRes(robot, currentMouseX - nextScreen.getDisplayMode().getWidth(), currentMouseY);
                    }
                    //fixedMouseMoveHorizontal(nextScreen, prevScreenWidths);
                    logger.incMonitorJumps();
                    drawCustomMouse();
                    incrementSwipeCount();
                }
            }
            case "tap" -> {
                java.awt.Robot robot = new java.awt.Robot();
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
            if(m.getMonitorHeight() > maxHeight) {
                maxHeight = m.getMonitorHeight();
            }
        }

        int x = prevScreenWidths + width/2;
        int y = height/2 + Math.abs(maxHeight - nextScreen.getDisplayMode().getHeight());

        System.out.println("*********** X : " + x + " ****************");
        robot.mouseMove(x, maxHeight);

        for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != x || //First move it to the correct screen
                MouseInfo.getPointerInfo().getLocation().getY() != y) &&
                count < 100; count++) {
            robot.mouseMove(x, y);
        }
        //y = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getHeight() / 2; //Then put y to mid of that monitor

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

    /**
     *  Has to run on its own thread to avoid clicks not detecting while animating
     */
    private void drawCustomMouse() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        ArrayList<Image> cursorImg = new ArrayList<>();

        for(String path : Config.CURSOR_FILEPATHS) {
            cursorImg.add(toolkit.getImage(path));
        }

        if(!Config.ANIMATION_RUNNING) {
            SwingUtilities.invokeLater(() -> {
                Config.ANIMATION_RUNNING = true;
                changeCursor(cursorImg);
                Collections.reverse(cursorImg);
                changeCursor(cursorImg);

                for(Frame frame : Frame.getFrames()) {
                    frame.setCursor(Cursor.getDefaultCursor());
                }
                Config.ANIMATION_RUNNING = false;
            });
        }
    }

    private void changeCursor(ArrayList<Image> cursorImg) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for(Image img : cursorImg) {
            Cursor cursor = toolkit.createCustomCursor(img, new Point(16, 16), "pulse");
            for(Frame frame : Frame.getFrames()) {
                frame.setCursor(cursor);
            }
            try {
                Thread.sleep(Config.CURSOR_ANIM_LENGTH / cursorImg.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void incrementSwipeCount() {
        TrialBlocks trialBlocks = TrialBlocks.getTrialblocks();
    }
}
