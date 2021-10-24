package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExperimentFrame extends JFrame {

    private final ArrayList<JFrame> frames = new ArrayList<>();

    public ExperimentFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        /*
            Generate Frames
         */
        int seq = 1;
        for (GraphicsDevice gd : gs) {
            GraphicsConfiguration[] gc = gd.getConfigurations();

            for (int i = 0; i < gc.length; i++) {
                JFrame f = new JFrame(gd.getDefaultConfiguration());
                Canvas c = new Canvas(gc[i]);
                Rectangle gcBounds = gc[i].getBounds();
                int xoffs = gcBounds.x;
                int yoffs = gcBounds.y;
                f.getContentPane().add(c);
                f.setLocation((i * 50) + xoffs, (i * 60) + yoffs);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //f.setResizable(false);
                f.setLayout(new BorderLayout());
                f.setTitle("Monitor " + seq);
                f.setExtendedState(Frame.MAXIMIZED_BOTH);
                //f.setSize(600, 600);
                f.setVisible(true);
                frames.add(f);

            }
            seq++;
        }
    }


    public ArrayList<JFrame> getStartFrames() {
        return frames;
    }


}
