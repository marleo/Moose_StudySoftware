package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;

import javax.swing.*;
import java.awt.*;

public class GoalCircle extends JComponent {

    int radius;
    int diam; //Diameter
    int tlX, tlY; //Top left x, y
    int centerX, centerY;

    Color color;

    public GoalCircle(int centerX, int centerY, int radius) {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.tlX = centerX - radius;
        this.tlY = centerY - radius;
        this.diam = radius * 2;
        this.color = Config.GOALCIRCLE_COLOR;
    }

    public boolean isInside(int x, int y) {
        double distanceToCenter = Math.sqrt(Math.pow(x - this.centerX, 2) + Math.pow(y - this.centerY, 2));
        return distanceToCenter <= this.radius;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.diam, this.diam);
    }

    /*
        Getter's & Setter's
     */

    public int getRadius() {
        return radius;
    }

    public int getDiam() {
        return diam;
    }

    public int getTlX() {
        return tlX;
    }

    public int getTlY() {
        return tlY;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

}
