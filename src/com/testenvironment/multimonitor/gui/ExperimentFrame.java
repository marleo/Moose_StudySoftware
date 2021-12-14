package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.TrialBlocks;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExperimentFrame extends JFrame {

    private final ArrayList<JFrame> frames = new ArrayList<>();

    public ExperimentFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        TrialBlocks trialblocks = TrialBlocks.getTrialblocks();

        /*
            Generate Frames
         */
        int seq = 1;
        for (GraphicsDevice gd : gs) {
            GraphicsConfiguration[] gc = gd.getConfigurations();

            JFrame f = new JFrame(gd.getDefaultConfiguration());
            Canvas c = new Canvas(gc[0]);
            Rectangle gcBounds = gc[0].getBounds();
            int xoffs = gcBounds.x;
            int yoffs = gcBounds.y;
            f.getContentPane().add(c);
            f.setLocation(xoffs, yoffs);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //f.setResizable(false);
            f.setLayout(new BorderLayout());
            f.setTitle("Monitor " + seq);
            f.setSize(600, 600);
            if(Config.FULLSCREEN) {
                f.setExtendedState(Frame.MAXIMIZED_BOTH); //Comment this out for 600 x 600 Windows instead of Fullscreen
            }
            f.setVisible(true);
            frames.add(f);
            trialblocks.addMonitor(f.getWidth(), f.getHeight(), f.getInsets().top);
            seq++;
        }
        trialblocks.generateTrials();
    }


    public ArrayList<JFrame> getStartFrames() {
        return frames;
    }


}
