package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.gui.DrawingPanel;
import com.testenvironment.multimonitor.gui.ExperimentFrame;
import com.testenvironment.multimonitor.gui.GoalCircle;
import com.testenvironment.multimonitor.gui.StartField;
import com.testenvironment.multimonitor.logging.Logger;
import com.testenvironment.multimonitor.logging.MouseLogger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Experiment extends JPanel {

    private static ArrayList<JFrame> frames;
    private final Logger logger;
    private final MouseLogger mouseLogger;
    private final Trialblocks trialblocks;
    private ArrayList<ArrayList<Trial>> blocks;
    private int currentTrial;
    private int currentTrialInSet;
    private int currentBlock;


    public Experiment(ArrayList<JFrame> frames) {
        this.logger = Logger.getLogger();
        this.mouseLogger = MouseLogger.getMouseLogger();
        this.trialblocks = Trialblocks.getTrialblocks();
        this.blocks = trialblocks.getBlocks();
        this.currentTrial = 0;
        this.currentBlock = 0;

        int startFrame = blocks.get(0).get(0).getMonitorStart() - 1;
        int endFrame = blocks.get(0).get(0).getMonitorEnd() - 1;

        System.out.println("StartFrame: " + startFrame);
        System.out.println("EndFrame: " + endFrame);
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

        if(currentTrial >= blocks.get(currentBlock).size()) {
            currentBlock++;
            currentTrial = 0;
            playFinished();
        }

        if(currentBlock >= blocks.size()) {
            logger.endLog();
            mouseLogger.endLog();
            System.exit(0);
        }

        startFrame.getContentPane().removeAll();
        startFrame.repaint();
        endFrame.getContentPane().removeAll();
        endFrame.repaint();

        ArrayList<JComponent> drawables = new ArrayList<>();

        this.currentTrialInSet = this.blocks.get(currentBlock).get(currentTrial).getTrialNum();

        int xRect = (int) this.blocks.get(currentBlock).get(currentTrial).getStart().getX();  //generator.nextInt(endFrame.getWidth() - 100) + 50;
        int yRect = (int) this.blocks.get(currentBlock).get(currentTrial).getStart().getY();  //generator.nextInt(endFrame.getHeight() - 100) + 50;

        int xCirc = (int) this.blocks.get(currentBlock).get(currentTrial).getEnd().getX();    //generator.nextInt(endFrame.getWidth() - 100) + 10;
        int yCirc = (int) this.blocks.get(currentBlock).get(currentTrial).getEnd().getY();    //generator.nextInt(endFrame.getHeight() - 100) + 10;

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

        currentTrial++;
    }


    public int getBlock() {
        return this.currentBlock;
    }

    public int getTrial() {
        return this.currentTrial;
    }

    public int getTrialNumberInSet() { //Real trialnumber for logging
        return this.currentTrialInSet;
    }

    private void playFinished() {
        AudioInputStream finishedIn;
        Clip clip;

        try {
            finishedIn = AudioSystem.getAudioInputStream(new File(Config.SOUND_FINISHED_PATH));
            clip = AudioSystem.getClip();
            clip.open(finishedIn);
            clip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}