package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.gui.*;
import com.testenvironment.multimonitor.logging.Logger;
import com.testenvironment.multimonitor.logging.MouseLogger;
import com.testenvironment.multimonitor.moose.Server;

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
    private final TrialBlocks trialblocks;
    private final ArrayList<ArrayList<Constellation>> blocks;
    private int currentTrialNum;
    private int currentTrialInSet;
    private int currentBlock;


    public Experiment() {
        this.logger = Logger.getLogger();
        this.mouseLogger = MouseLogger.getMouseLogger();
        this.trialblocks = TrialBlocks.getTrialblocks();
        this.blocks = trialblocks.getBlocks();
        this.currentTrialNum = 0;
        this.currentBlock = 0;
        drawFrames();
    }

    public static void main(String[] args) {
        if(Config.TESTTYPE.equals("MOOSE")) {
            Server.get().start();
        }
        ExperimentFrame experimentFrame = new ExperimentFrame();
        frames = experimentFrame.getStartFrames();
        new Experiment();
    }

    public void drawFrames() {
        if(currentTrialNum >= blocks.get(currentBlock).size()) {
            if((currentBlock + 1) % Config.PAUSE_AFTER_BLOCKNR == 0
                    && !trialblocks.isResumeTrial()
                    && currentBlock + 1 != Config.BLOCKS) {
                trialblocks.setPauseTrial(true);
            }
        }
        if(!trialblocks.isPauseTrial()) {
            trialblocks.setResumeTrial(false);
            if (currentTrialNum >= blocks.get(currentBlock).size()) {
                currentBlock++;
                currentTrialNum = 0;
                if(Config.TRIALTYPE.equals("Random"))
                    trialblocks.resetTrialblock();
                playFinished();
            }

            if (currentBlock >= blocks.size() || currentBlock >= Config.BLOCKS)  {
                logger.endLog();
                mouseLogger.endLog();
                System.exit(0);
            }

            for (JFrame fr : frames) {
                fr.getContentPane().removeAll();
                fr.repaint();
            }

            int numStartFrame = blocks.get(currentBlock).get(currentTrialNum).getMonitorStart() - 1;
            int numGoalFrame = blocks.get(currentBlock).get(currentTrialNum).getMonitorEnd() - 1;

            JFrame startFrame = frames.get(numStartFrame);
            JFrame endFrame = frames.get(numGoalFrame);

            ArrayList<JComponent> drawables = new ArrayList<>();

            this.currentTrialInSet = this.blocks.get(currentBlock).get(currentTrialNum).getTrialNum();

            int xRect, yRect, xCirc, yCirc;

            xRect = (int) this.blocks.get(currentBlock).get(currentTrialNum).getStart().getX();
            yRect = (int) this.blocks.get(currentBlock).get(currentTrialNum).getStart().getY();
            xCirc = (int) this.blocks.get(currentBlock).get(currentTrialNum).getEnd().getX();
            yCirc = (int) this.blocks.get(currentBlock).get(currentTrialNum).getEnd().getY();

            //Add Startfield
            StartField startField = new StartField(xRect, yRect, Config.STARTFIELD_WIDTH, Config.STARTFIELD_HEIGHT);
            startField.setLocation(new Point(startField.getTlX(), startField.getTlY()));
            drawables.add(startField);

            DrawingPanel canvas = new DrawingPanel(this, drawables);

            canvas.setBounds(0, 0, startFrame.getWidth(), startFrame.getHeight());
            startFrame.getContentPane().add(canvas);
            startFrame.setVisible(true);

            drawables = new ArrayList<>();

            //Add GoalCircle
            GoalCircle goalCircle;
            GoalRect goalRect;
            Constellation constellation = this.blocks.get(currentBlock).get(currentTrialNum);

            if (Config.GOAL_IS_CIRCLE) {
                goalCircle = new GoalCircle(xCirc, yCirc, constellation.getGoalWidth());
                goalCircle.setLocation(new Point(goalCircle.getTlX(), goalCircle.getTlY()));
                drawables.add(goalCircle);
            } else {
                goalRect = new GoalRect(xCirc, yCirc, constellation.getGoalWidth(), constellation.getGoalHeight());
                goalRect.setLocation(new Point(goalRect.getTlX(), goalRect.getTlY()));
                drawables.add(goalRect);
            }

            DrawingPanel canvasSecond = new DrawingPanel(this, drawables);

            canvasSecond.setBounds(0, 0, endFrame.getWidth(), endFrame.getHeight());
            endFrame.getContentPane().add(canvasSecond);
            endFrame.setVisible(true);

            logger.resetDownSwipes();
            logger.resetLeftSwipes();
            logger.resetRightSwipes();
            logger.resetUpSwipes();
            logger.resetTapCount();
            logger.resetMonitorJumps();
            logger.resetLeftTaps();
            logger.resetRightTaps();
            logger.resetDownSlides();
            logger.resetUpSlides();

            currentTrialNum++;
        } else {
            for(JFrame fr : frames) {

                fr.getContentPane().removeAll();
                fr.repaint();

                PauseFrame pauseFrame = new PauseFrame(this);
                pauseFrame.setBounds(0, 0, fr.getWidth(), fr.getHeight());
                fr.getContentPane().add(pauseFrame);
                fr.setVisible(true);
            }
        }

    }

    private void playFinished() {
        AudioInputStream finishedIn;
        Clip clip;

        try {
            finishedIn = AudioSystem.getAudioInputStream(new File(Config.SOUND_FINISHED_PATH));
            clip = AudioSystem.getClip();
            clip.open(finishedIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBlock() {
        return this.currentBlock;
    }

    public int getTrial() {
        return this.currentTrialNum;
    }

    public int getTrialNumberInSet() { //Real trialnumber for logging
        return this.currentTrialInSet;
    }


}