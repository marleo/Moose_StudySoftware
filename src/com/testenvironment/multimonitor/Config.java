package com.testenvironment.multimonitor;

import java.awt.*;

public class Config {

    /**
     * Logging
     */
    public static int USER_ID = 1;
    public static int MAX_MONITOR = 2;
    public static int BLOCKS = 4;
    public static String LOG_PATH = "logs/";
    public static String MOUSE_LOG = "events_";
    public static String MOOSE_LOG = "moose_";
    public static String TESTTYPE = "NO_MOOSE";
    public static String SOUND_ERROR_PATH = "sounds/error.wav";
    public static String SOUND_SUCCESS_PATH = "sounds/success.wav";
    public static String SOUND_FINISHED_PATH = "sounds/finished.wav";

    /**
     *  Testpanel
     */
    public static Color TESTBACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static int PADDING = 15;

    /**
     *  Startbutton
     */
    public static Color STARTFIELD_COLOR = new Color(8, 161, 54);
    public static Color STARTFIELD_PRESSED_COLOR = new Color(4, 90, 29);
    public static Color STARTFIELD_COLOR_TEXT = Color.WHITE;
    public static int STARTFIELD_FONT_SIZE = 12;
    public static int STARTFIELD_HEIGHT = 20;
    public static int STARTFIELD_WIDTH = 50;

    /**
     *  Text in upper left corner
     */
    public static int INFOTEXT_X = 20;
    public static int INFOTEXT_Y = 30;
    public static Color INFOTEXT_COLOR = Color.BLACK;
    public static int INFOTEXT_FONT_SIZE = 18;

    /**
     *  General Fontstyle
     */
    public static String FONT_STYLE = "Sans-Serif";

    /**
     *  Goal-Circle
     */
    public static Color GOALCIRCLE_COLOR = new Color(208, 33, 33);
    public static Color GOALCIRCLE_PRESSED_COLOR = new Color(118, 18, 18);
    public static int GOALCIRCLE_RAD = 15; //Default value
    public static int[] GOALCIRCLE_RADS = {15, 20, 25, 10}; //value per block
}
