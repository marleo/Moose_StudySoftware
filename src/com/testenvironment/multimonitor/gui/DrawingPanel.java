package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Experiment;
import com.testenvironment.multimonitor.experiment.Trial;
import com.testenvironment.multimonitor.experiment.Trialblocks;
import com.testenvironment.multimonitor.logging.Logger;
import com.testenvironment.multimonitor.logging.MouseLogger;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

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
    private Trialblocks trialblock;
    private int errors;
    private Trial currentTrial;

    public DrawingPanel(Experiment experiment, ArrayList<JComponent> drawables) {
        this.drawables = drawables;
        this.experiment = experiment;
        testStart = false;
        testTime = 0;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.logger = Logger.getLogger();
        this.mouseLogger = MouseLogger.getMouseLogger();
        this.trialblock = Trialblocks.getTrialblocks();
        this.startColor = Config.STARTFIELD_COLOR;
        this.blockNumber = experiment.getBlock() + 1;
        this.trialNumber = experiment.getTrial() + 1;
        this.errors = 0;
        this.currentTrial = trialblock.getBlocks().get(blockNumber - 1).get(trialNumber - 1);

        if(trialNumber % 2 != 0) {
            this.startFrame = Experiment.getFrames().get(trialblock.getBlocks().get(blockNumber - 1).get(trialNumber - 1).getMonitorEnd() - 1);
            this.endFrame = Experiment.getFrames().get(trialblock.getBlocks().get(blockNumber - 1).get(trialNumber - 1).getMonitorStart() - 1);
        } else {
            this.endFrame = Experiment.getFrames().get(trialblock.getBlocks().get(blockNumber - 1).get(trialNumber - 1).getMonitorEnd() - 1);
            this.startFrame = Experiment.getFrames().get(trialblock.getBlocks().get(blockNumber - 1).get(trialNumber - 1).getMonitorStart() - 1);
        }
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

//        this.setBackground(Config.TESTBACKGROUND_COLOR);
        g2d.setColor(new Color(230, 255, 230)); //new
        g2d.fillRect(0,0,this.getWidth(),this.getHeight()); //new

        System.out.println("Drawing...");

        for (JComponent draw : drawables) {
            if (draw instanceof StartField) {
                int c = ((StartField) draw).getTlX();
                int d = ((StartField) draw).getTlY();
                int a = draw.getWidth();
                int b = draw.getHeight();

//                this.setBackground(new Color(230, 255, 230));
                g2d.setColor(new Color(230, 255, 230)); //new
                g2d.fillRect(0,0,this.getWidth(),this.getHeight());

                g2d.setColor(startColor);
                g2d.fillRect(c, d, a, b);
                g2d.setColor(Config.STARTFIELD_COLOR_TEXT);
                g2d.setFont(new Font(Config.FONT_STYLE, Font.PLAIN, Config.STARTFIELD_FONT_SIZE));
                g2d.drawString("Start", c + a / 4, d + (3 * b / 4));
            } else if (draw instanceof GoalCircle) {
//                this.setBackground(new Color(255, 230, 230));
                g2d.setColor(new Color(255, 230, 230)); //new
                g2d.fillRect(0,0,this.getWidth(),this.getHeight());

                g2d.setColor(Config.GOALCIRCLE_COLOR);
                g2d.fillOval(((GoalCircle) draw).getTlX(), ((GoalCircle) draw).getTlY(), ((GoalCircle) draw).getDiam(), ((GoalCircle) draw).getDiam());
            }
        }

        g2d.setColor(Config.INFOTEXT_COLOR);
        g2d.setFont(new Font(Config.FONT_STYLE, Font.PLAIN, Config.INFOTEXT_FONT_SIZE));
        g2d.drawString("Block: " + blockNumber + " | Trial: " + trialNumber, Config.INFOTEXT_X, Config.INFOTEXT_Y);
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
                    System.out.println("Test started..."); //TODO: REMOVE
                    if (!testStart) {
                        testStart = true;

                        startColor = Config.STARTFIELD_PRESSED_COLOR;
                        repaint();

                        setStartLogger(e, monitorName, dr);
                        logger.setStartWindowWidth(windowWidth);
                        logger.setStartWindowHeight(windowHeight);
                        logger.setStartMonitorHeight(monitorHeight);
                        logger.setStartMonitorWidth(monitorWidth);
                    }
                }
            } else if (dr instanceof GoalCircle) {
                isInGoal = ((GoalCircle) dr).isInside(e.getX(), e.getY());
                if (isInGoal) {
                    if (testStart) {
                        playSuccess();

                        setTargetLogger(e, monitorName, dr);
                        logger.setTargetWindowWidth(windowWidth);
                        logger.setTargetWindowHeight(windowHeight);
                        logger.setTargetMonitorWidth(monitorWidth);
                        logger.setTargetMonitorHeight(monitorHeight);
                        logger.setErrors(this.currentTrial.getAndResetErrors());
                        logger.generateLogString();

                        experiment.drawFrames(startFrame, endFrame);
                    }
                } else {
                    if(testStart) {
                        playError();

                        setTargetLogger(e, monitorName, dr);
                        this.currentTrial.setError();
                        trialblock.pushBackTrial(this.currentTrial);
                        experiment.drawFrames(startFrame, endFrame);
                    }
                }
            }
            setMouseLogger(e, currentFrame);
            mouseLogger.setMouseReleased(1);
            mouseLogger.generateLogString();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

        setMouseLogger(e, currentFrame);
        mouseLogger.setMousePressed(1);
        mouseLogger.generateLogString();
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

        setMouseLogger(e, currentFrame);
        mouseLogger.setMouseEntered(1);
        mouseLogger.generateLogString();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

        setMouseLogger(e, currentFrame);
        mouseLogger.setMouseExited(1);
        mouseLogger.generateLogString();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(testStart) {
            JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);

            setMouseLogger(e, currentFrame);
            mouseLogger.setMouseMoved(1);
            mouseLogger.generateLogString();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // not needed
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // not needed
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

    private void setStartLogger(MouseEvent e, String monitorName, JComponent dr) {
        testTime = System.currentTimeMillis();

        logger.setTrialStartTime(testTime);
        logger.setStartPosX(dr.getX());
        logger.setStartPosY(dr.getY());
        logger.setStartCenterX(((StartField) dr).getCenterX());
        logger.setStartCenterY(((StartField) dr).getCenterY());
        logger.setDistancePx(((StartField) dr).distanceToMid(e.getX(), e.getY()));
        logger.setStartPointPressedX(e.getX());
        logger.setStartPointPressedY(e.getY());
        logger.setStartMonitor(Integer.parseInt(monitorName.replaceAll("[^0-9]", "")));
        logger.setBlockNumber(blockNumber);
        logger.setTrialNumberShown(trialNumber);
        logger.setTrialNumberInSet(experiment.getTrialNumberInSet());
    }

    private void setTargetLogger(MouseEvent e, String monitorName, JComponent dr) {
        long testFin = System.currentTimeMillis();
        long testFinishedTime = System.currentTimeMillis() - testTime;

        logger.setTrialEndTime(testFin);
        logger.setTrialTime(testFinishedTime);
        logger.setTargetPosX(dr.getX());
        logger.setTargetPosY(dr.getY());
        logger.setTargetCenterX(((GoalCircle) dr).getCenterX());
        logger.setTargetCenterY(((GoalCircle) dr).getCenterX());
        logger.setDistancePx(((GoalCircle) dr).distanceToMid(e.getX(), e.getY()));
        logger.setTargetPointPressedX(e.getX());
        logger.setTargetPointPressedY(e.getY());
        logger.setTargetMonitor(Integer.parseInt(monitorName.replaceAll("[^0-9]", "")));
        logger.setBlockNumber(blockNumber);
        logger.setTrialNumberShown(trialNumber);
        logger.setTrialNumberInSet(experiment.getTrialNumberInSet());
    }

    private void setMouseLogger(MouseEvent e, JFrame currentFrame) {
        mouseLogger.setMonitorNr(Integer.parseInt(currentFrame.getTitle().replaceAll("[^0-9]", "")));
        mouseLogger.setWindowWidth(currentFrame.getWidth());
        mouseLogger.setWindowHeight(currentFrame.getHeight());
        mouseLogger.setMonitorWidth(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getWidth());
        mouseLogger.setMonitorHeight(currentFrame.getGraphicsConfiguration().getDevice().getDisplayMode().getHeight());
        mouseLogger.setMouseX(e.getX());
        mouseLogger.setMouseY(e.getY());
        mouseLogger.setBlockNumber(blockNumber);
        mouseLogger.setTrialNumber(trialNumber);
        mouseLogger.setTrialNumberInSet(experiment.getTrialNumberInSet());

    }
}
