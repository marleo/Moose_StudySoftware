package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Experiment;
import com.testenvironment.multimonitor.experiment.Logger;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
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
    private Color startColor;

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
        this.startColor = Config.STARTFIELD_COLOR;
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
        g2d.drawString("Test: " + experiment.getRemainingTests() + " of " + Config.NUM_TRIALS, Config.INFOTEXT_X, Config.INFOTEXT_Y);

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
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /*
        Check if Mouseclick is in GoalCircle or StartField
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        boolean isInStart, isInGoal;
        JFrame currentFrame = (JFrame) SwingUtilities.getRoot(this);
        String monitorName = currentFrame.getTitle();
        int monitorWidth = currentFrame.getWidth();
        int monitorHeight = currentFrame.getHeight();

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
                        logger.setStartMonitorWidth(monitorWidth);
                        logger.setStartMonitorHeight(monitorHeight);

//                      logger.writeToLog(e.paramString(), Config.MOUSE_LOG);
//                      logger.writeToLog("Start at: X: "
//                                        + dr.getX()
//                                        + " Y: "
//                                        + dr.getY()
//                                        + " on "
//                                        + monitorName + " "
//                                , Config.TIMING_LOG);
                    }
                }
            } else if (dr instanceof GoalCircle) {
                isInGoal = ((GoalCircle) dr).isInside(e.getX(), e.getY());
                if (isInGoal) {
                    if (testStart) {
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
                        logger.setTargetMonitorWidth(monitorWidth);
                        logger.setTargetMonitorHeight(monitorHeight);
                        logger.setHit(1);

                        System.out.println("Test finished in: "
                                + testFinishedTime
                                + "ms");
//                        logger.writeToLog(e.paramString(), Config.MOUSE_LOG);
                        experiment.drawFrames(startFrame, endFrame);
//                        logger.writeToLog("Goal at: X: "
//                                        + dr.getX()
//                                        + " Y: " + dr.getY()
//                                        + " on "
//                                        + monitorName + "\n"
//                                        + testFinishedTime
//                                        + "ms"
//                                        + "\n"
//                                , Config.TIMING_LOG);
                        logger.generateLogString();
                    }
                } else {
                    if(testStart) {
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
                        logger.setHit(0);

                        System.out.println("Test finished in: "
                                + testFinishedTime
                                + "ms");
//                        logger.writeToLog(e.paramString(), Config.MOUSE_LOG);
//                        logger.writeToLog("Goal at: X: "
//                                        + dr.getX()
//                                        + " Y: " + dr.getY()
//                                        + " on "
//                                        + monitorName + "\n"
//                                        + testFinishedTime
//                                        + "ms"
//                                        + "\n"
//                                , Config.TIMING_LOG);
                        logger.generateLogString();
                    }
                }
            }
            logger.writeToLog(e.paramString(), Config.MOUSE_LOG);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        logger.writeToLog(e.paramString(), Config.MOUSE_LOG);
    }
}
