package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;

import javax.swing.*;
import java.awt.*;

public class StartField extends JComponent {

    int width, height;
    int tlX, tlY; //Top Left x, y
    int centerX, centerY;
    Color color;

    public StartField(int centerX, int centerY, int width, int height) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerY = centerY;
        this.tlX = centerX - width / 2;
        this.tlY = centerY - height / 2;
        this.color = Config.STARTFIELD_COLOR;
    }

    public boolean isInside(int x, int y) { //TODO: DOES NOT MATCH
        return x >= this.getTlX() && x <= this.getTlX() + this.getWidth() &&
                y >= this.getTlY() && y <= this.getTlY() + this.getHeight();
    }

    public int distanceToMid(int x, int y) {
        return (int) Math.sqrt(Math.pow(x - this.centerX, 2) + Math.pow(y - this.centerY, 2));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }

    /*
       Getter's & Setter's
    */

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
