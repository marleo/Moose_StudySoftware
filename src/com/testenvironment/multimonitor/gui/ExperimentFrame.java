package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Trialblocks;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExperimentFrame extends JFrame {

    private final ArrayList<JFrame> frames = new ArrayList<>();

    public ExperimentFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        Trialblocks trialblocks = Trialblocks.getTrialblocks();

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
            //f.setExtendedState(Frame.MAXIMIZED_BOTH);
            f.setSize(600, 600);
            f.setVisible(true);
            frames.add(f);
            trialblocks.addMonitor(f.getWidth(), f.getHeight(), f.getInsets().top);

            //trialblocks.addMonitor(gd.getDefaultConfiguration().getDevice().getDisplayMode().getWidth(),
            //        gd.getDefaultConfiguration().getDevice().getDisplayMode().getHeight());
            seq++;
        }
        trialblocks.printMonitorPositions();
        trialblocks.generateTrials();
    }


    public ArrayList<JFrame> getStartFrames() {
        return frames;
    }


}
