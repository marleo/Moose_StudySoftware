package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Experiment;
import com.testenvironment.multimonitor.experiment.Logger;
import com.testenvironment.multimonitor.experiment.MouseLogger;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel implements MouseInputListener {

    private static boolean testStart;
    private static long testTime;
    private final ArrayList<JComponent> drawables;
    private final Experiment experiment;
    private final JFrame startFrame;
    private final JFrame endFrame;
    private final Logger logger;
    private final MouseLogger mouseLogger;
    private Color startColor;
    private int blockNumber;
    private int trialNumber;

    public DrawingPanel(Experiment experiment, ArrayList<JComponent> drawables) {
        JFrame endFrame1;
        this.drawables = drawables;
        this.experiment = experiment;
        this.startFrame = Experiment.getFrames().get(new Random().nextInt(Experiment.getFrames().size()));
        endFrame1 = Experiment.getFrames().get(new Random().nextInt(Experiment.getFrames().size()));
        while (endFrame1 == this.startFrame) {
            endFrame1 = Experiment.getFrames().get(new Random().nextInt(Experiment.getFrames().size()));
        }
        this.endFrame = endFrame1;
        testStart = false;
        testTime = 0;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.logger = Logger.getLogger();
        this.mouseLogger = MouseLogger.getMouseLogger();
        this.startColor = Config.STARTFIELD_COLOR;
        this.blockNumber = experiment.getBlock() + 1;
        this.trialNumber = experiment.getTrial() + 1;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getHeight(), getParent().getWidth());
    }


    /*
        Draw StartField and GoalCircle to frames
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.setBackground(Config.TESTBACKGROUND_COLOR);

        g2d.setColor(Config.INFOTEXT_COLOR);
        g2d.setFont(new Font(Config.FONT_STYLE, Font.PLAIN, Config.INFOTEXT_FONT_SIZE));
        g2d.drawString("Block: " + blockNumber + " | Trial: " + trialNumber, Config.INFOTEXT_X, Config.INFOTEXT_Y);

        for (JComponent draw : drawables) {
            if (draw instanceof StartField) {
                int c = ((StartField) draw).getTlX();
                int d = ((StartField) draw).getTlY();
                int a = draw.getWidth();
                int b = draw.getHeight();

                this.setBackground(new Color(230, 255, 230));

                g2d.setColor(startColor);
                g2d.fillRect(c, d, a, b);
                g2d.setColor(Config.STARTFIELD_COLOR_TEXT);
                g2d.setFont(new Font(Config.FONT_STYLE, Font.PLAIN, Config.STARTFIELD_FONT_SIZE));
                g2d.drawString("Start", c + a / 4, d + (3 * b / 4));
            } else if (draw instanceof GoalCircle) {
                this.setBackground(new Color(255, 230, 230));
                g2d.setColor(Config.GOALCIRCLE_COLOR);
                g2d.fillOval(((GoalCircle) draw).getTlX(), ((GoalCircle) draw).getTlY(), ((GoalCircle) draw).getDiam(), ((GoalCircle) draw).getDiam());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

        mouseLogger.setMonitorNr(Integer.parseInt(currentFrame.getTitle().replaceAll("[^0-9]", "")));
        mouseLogger.setWindowWidth(currentFrame.getWidth());
        mouseLogger.setWindowHeight(currentFrame.getHeight());
        mouseLogger.setMonitorWidth(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth());
        mouseLogger.setMonitorHeight(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight());
        mouseLogger.setMouseX(e.getX());
        mouseLogger.setMouseY(e.getY());
        mouseLogger.setMousePressed(1);
        mouseLogger.setBlockNumber(blockNumber);
        mouseLogger.setTrialNumber(trialNumber);

        mouseLogger.generateLogString();
    }

    /*
        Check if Mouseclick is in GoalCircle or StartField
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        boolean isInStart, isInGoal;
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);
        String monitorName = currentFrame.getTitle();
        int windowWidth = currentFrame.getWidth();
        int windowHeight = currentFrame.getHeight();
        int monitorWidth = currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth(); //Get Monitorwidth where currentFrame is placed
        int monitorHeight = currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight(); //Get Monitorheight where currentFrame is placed

        for (JComponent dr : drawables) {
            if (dr instanceof StartField) {
                isInStart = ((StartField) dr).isInside(e.getX(), e.getY());
                if (isInStart) {
                    System.out.println("In Start"); //TODO: REMOVE
                    if (!testStart) {
                        testTime = System.currentTimeMillis();
                        testStart = true;

                        startColor = Config.STARTFIELD_PRESSED_COLOR;

                        logger.setTrialStartTime(testTime);
                        logger.setStartPosX(dr.getX());
                        logger.setStartPosY(dr.getY());
                        logger.setStartCenterX(((StartField) dr).getCenterX());
                        logger.setStartCenterY(((StartField) dr).getCenterY());
                        logger.setStartPointPressedX(e.getX());
                        logger.setStartPointPressedY(e.getY());
                        logger.setStartMonitor(Integer.parseInt(monitorName.replaceAll("[^0-9]", "")));
                        logger.setStartWindowWidth(windowWidth);
                        logger.setStartWindowHeight(windowHeight);
                        logger.setStartMonitorHeight(monitorHeight);
                        logger.setStartMonitorWidth(monitorWidth);
                        logger.setBlockNumber(blockNumber);
                        logger.setTrialNumber(trialNumber);
                    }
                }
            } else if (dr instanceof GoalCircle) {
                isInGoal = ((GoalCircle) dr).isInside(e.getX(), e.getY());
                if (isInGoal) {
                    if (testStart) {
                        playSuccess();

                        long testFin = System.currentTimeMillis();
                        long testFinishedTime = System.currentTimeMillis() - testTime;

                        logger.setTrialEndTime(testFin);
                        logger.setTrialTime(testFinishedTime);
                        logger.setTargetPosX(dr.getX());
                        logger.setTargetPosY(dr.getY());
                        logger.setTargetCenterX(((GoalCircle) dr).getCenterX());
                        logger.setTargetCenterY(((GoalCircle) dr).getCenterX());
                        logger.setStartPointPressedX(e.getX());
                        logger.setStartPointPressedY(e.getY());
                        logger.setTargetMonitor(Integer.parseInt(monitorName.replaceAll("[^0-9]", "")));
                        logger.setTargetWindowWidth(windowWidth);
                        logger.setTargetWindowHeight(windowHeight);
                        logger.setTargetMonitorWidth(monitorWidth);
                        logger.setTargetMonitorHeight(monitorHeight);
                        logger.setBlockNumber(blockNumber);
                        logger.setTrialNumber(trialNumber);
                        logger.setHit(1);

                        System.out.println("Test finished in: "
                                + testFinishedTime
                                + "ms");
                        experiment.drawFrames(startFrame, endFrame);
                        logger.generateLogString();
                    }
                } else {
                    if(testStart) {
                        playError();

                        long testFin = System.currentTimeMillis();
                        long testFinishedTime = System.currentTimeMillis() - testTime;

                        logger.setTrialEndTime(testFin);
                        logger.setTrialTime(testFinishedTime);
                        logger.setTargetPosX(dr.getX());
                        logger.setTargetPosY(dr.getY());
                        logger.setTargetCenterX(((GoalCircle) dr).getCenterX());
                        logger.setTargetCenterY(((GoalCircle) dr).getCenterX());
                        logger.setTargetPointPressedX(e.getX());
                        logger.setTargetPointPressedY(e.getY());
                        logger.setTargetMonitor(Integer.parseInt(monitorName.replaceAll("[^0-9]", "")));
                        logger.setBlockNumber(blockNumber);
                        logger.setTrialNumber(trialNumber);
                        logger.setHit(0);

                        System.out.println("Test finished in: "
                                + testFinishedTime
                                + "ms");
                        logger.generateLogString();
                    }
                }
            }
            mouseLogger.setMonitorNr(Integer.parseInt(monitorName.replaceAll("[^0-9]", "")));
            mouseLogger.setMonitorWidth(monitorWidth);
            mouseLogger.setMonitorHeight(monitorHeight);
            mouseLogger.setMouseX(e.getX());
            mouseLogger.setMouseY(e.getY());
            mouseLogger.setMouseReleased(1);
            mouseLogger.setBlockNumber(blockNumber);
            mouseLogger.setTrialNumber(trialNumber);

            mouseLogger.generateLogString();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

        mouseLogger.setMonitorNr(Integer.parseInt(currentFrame.getTitle().replaceAll("[^0-9]", "")));
        mouseLogger.setWindowWidth(currentFrame.getWidth());
        mouseLogger.setWindowHeight(currentFrame.getHeight());
        mouseLogger.setMonitorWidth(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth());
        mouseLogger.setMonitorHeight(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight());
        mouseLogger.setMouseX(e.getX());
        mouseLogger.setMouseY(e.getY());
        mouseLogger.setMouseEntered(1);
        mouseLogger.setBlockNumber(blockNumber);
        mouseLogger.setTrialNumber(trialNumber);

        mouseLogger.generateLogString();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

        mouseLogger.setMonitorNr(Integer.parseInt(currentFrame.getTitle().replaceAll("[^0-9]", "")));
        mouseLogger.setWindowWidth(currentFrame.getWidth());
        mouseLogger.setWindowHeight(currentFrame.getHeight());
        mouseLogger.setMonitorWidth(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth());
        mouseLogger.setMonitorHeight(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight());
        mouseLogger.setMouseX(e.getX());
        mouseLogger.setMouseY(e.getY());
        mouseLogger.setMouseExited(1);
        mouseLogger.setBlockNumber(blockNumber);
        mouseLogger.setTrialNumber(trialNumber);

        mouseLogger.generateLogString();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(testStart) {
            JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

            mouseLogger.setMonitorNr(Integer.parseInt(currentFrame.getTitle().replaceAll("[^0-9]", "")));
            mouseLogger.setWindowWidth(currentFrame.getWidth());
            mouseLogger.setWindowHeight(currentFrame.getHeight());
            mouseLogger.setMonitorWidth(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth());
            mouseLogger.setMonitorHeight(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight());
            mouseLogger.setMouseX(e.getX());
            mouseLogger.setMouseY(e.getY());
            mouseLogger.setMouseMoved(1);
            mouseLogger.setBlockNumber(blockNumber);
            mouseLogger.setTrialNumber(trialNumber);

            mouseLogger.generateLogString();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    private void playSuccess() {
        AudioInputStream successIn;
        Clip clip;

        try {
            successIn = AudioSystem.getAudioInputStream(new File(Config.SOUND_SUCCESS_PATH));
            clip = AudioSystem.getClip();
            clip.open(successIn);
            clip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void playError() {
        AudioInputStream errorIn;
        Clip clip;

        try {
            errorIn = AudioSystem.getAudioInputStream(new File(Config.SOUND_ERROR_PATH));
            clip = AudioSystem.getClip();
            clip.open(errorIn);
            clip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
