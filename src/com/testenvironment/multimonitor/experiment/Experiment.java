package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.gui.DrawingPanel;
import com.testenvironment.multimonitor.gui.ExperimentFrame;
import com.testenvironment.multimonitor.gui.GoalCircle;
import com.testenvironment.multimonitor.gui.StartField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Experiment extends JPanel {

    private static ArrayList<JFrame> frames;
    private final Random generator;
    private final int numTrials;
    private int count;
    private final Logger logger;
    private final MouseLogger mouseLogger;

    public Experiment(ArrayList<JFrame> frames) {
        this.generator = new Random(100);
        this.numTrials = Config.NUM_TRIALS;
        this.count = 0;
        this.logger = Logger.getLogger();
        this.mouseLogger = MouseLogger.getMouseLogger();
        int startFrame = generator.nextInt(frames.size());
        int endFrame = generator.nextInt(frames.size());
        while (endFrame == startFrame) {
            endFrame = generator.nextInt(frames.size());
        }
        drawFrames(frames.get(startFrame), frames.get(endFrame));
    }

    public static void main(String[] args) {
        ExperimentFrame experimentFrame = new ExperimentFrame();
        frames = experimentFrame.getStartFrames();
        new Experiment(frames);
    }

    public static ArrayList<JFrame> getFrames() {
        return frames;
    }

    public void drawFrames(JFrame startFrame, JFrame endFrame) {
        count++;
        if (count >= numTrials) {
            logger.endLog();
            mouseLogger.endLog();
            System.exit(0);
        }
        startFrame.getContentPane().removeAll();
        startFrame.repaint();
        endFrame.getContentPane().removeAll();
        endFrame.repaint();

        ArrayList<JComponent> drawables = new ArrayList<>();

        int xRect = generator.nextInt(endFrame.getWidth() - 100) + 50;
        int yRect = generator.nextInt(endFrame.getHeight() - 100) + 50;

        int xCirc = generator.nextInt(endFrame.getWidth() - 100) + 10;
        int yCirc = generator.nextInt(endFrame.getHeight() - 100) + 10;

        //Add Startfield
        StartField startField = new StartField(xRect, yRect, Config.STARTFIELD_WIDTH, Config.STARTFIELD_HEIGHT);
        startField.setLocation(new Point(startField.getTlX(), startField.getTlY()));
        drawables.add(startField);
        DrawingPanel canvas = new DrawingPanel(this, drawables);
        canvas.setBounds(0, 0, startFrame.getWidth(), startFrame.getHeight());

        startFrame.getContentPane().add(canvas);

        drawables = new ArrayList<>();

        //Add GoalCircle
        GoalCircle goalCircle = new GoalCircle(xCirc, yCirc, Config.GOALCIRCLE_RAD);
        goalCircle.setLocation(new Point(goalCircle.getCenterX(), goalCircle.getCenterY()));
        drawables.add(goalCircle);
        DrawingPanel canvasSecond = new DrawingPanel(this, drawables);
        canvasSecond.setBounds(0, 0, endFrame.getWidth(), endFrame.getHeight());

        endFrame.getContentPane().add(canvasSecond);
    }

    public int getRemainingTests() {
        return count;
    }
}