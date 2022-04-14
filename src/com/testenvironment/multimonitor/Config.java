package com.testenvironment.multimonitor;

import java.awt.*;

public class Config {

    /**
     * Logging
     */
    public static int USER_ID = 1; //increments + 1 everytime the program gets opened
    public static int BLOCKS = 15; //Number of TrialBlocks | IF 0, generates all possible combinations for the given trials
    public static int PAUSE_AFTER_BLOCKNR = 2;
    public static int[] MONITOR_ZONES = {5, 5}; //Rows, Cols
    public static boolean MONITOR_ORIENTATION = true; // true = horizontal; false = vertical
    public static String LOG_PATH = "logs/"; //Directory, where logs get stored (has to be created beforehand)
    public static String MOUSE_LOG = "events_";
    public static String MOOSE_LOG = "moose_";
    public static String TESTTYPE = "MOOSE";
    public static Boolean RECIPROCAL = false;

    /**
     * Testpanel
     */
    public static Color START_BACKGROUNDCOLOR = new Color(230, 255, 230);
    public static Color GOAL_BACKGROUNDCOLOR = new Color(255, 230, 230);
    public static Color PAUSE_BACKGROUNDCOLOR = new Color(5, 17, 73);
    public static int PADDING = 15;
    public static boolean FULLSCREEN = true;
    public static int CURSOR_ANIM_LENGTH = 250; //in ms
    public static String[] CURSOR_FILEPATHS = {
            "assets/8x8.png",
            "assets/12x12.png",
            "assets/16x16.png",
            "assets/24x24.png",
            "assets/32x32.png"
    };

    /**
     * Startbutton
     */
    public static Color STARTFIELD_COLOR = new Color(8, 161, 54);
    public static Color STARTFIELD_PRESSED_COLOR = new Color(4, 90, 29);
    public static Color STARTFIELD_COLOR_TEXT = Color.WHITE;
    public static int STARTFIELD_FONT_SIZE = 12;
    public static int STARTFIELD_HEIGHT = 20;
    public static int STARTFIELD_WIDTH = 50;

    /**
     * Text in upper left corner
     */
    public static int INFOTEXT_X = 20;
    public static int INFOTEXT_Y = 30;
    public static Color INFOTEXT_COLOR = Color.BLACK;
    public static int INFOTEXT_FONT_SIZE = 18;
    public static Color PAUSETEXT_COLOR = Color.white;
    public static int PAUSETEXT_FONT_SIZE = 22;
    public static String PAUSETEXT = "Trial Paused";
    public static final String CONTINUESTRING = "Press anywhere to continue";
    public static int PAUSETIME = 5;

    /**
     * General Fontstyle
     */
    public static String FONT_STYLE = "Sans-Serif";

    /**
     * Goal-Circle
     */
    public static Color GOALCIRCLE_COLOR = new Color(208, 33, 33);
    public static boolean GOAL_IS_CIRCLE = true;
    //Height and Width is set per Test in Trialblocks.java

    /**
     * Ressources
     */
    public static String SOUND_ERROR_PATH = "sounds/error.wav";
    public static String SOUND_SUCCESS_PATH = "sounds/success.wav";
    public static String SOUND_FINISHED_PATH = "sounds/finished.wav";
}
