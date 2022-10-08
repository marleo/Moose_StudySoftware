package com.testenvironment.multimonitor;

import java.awt.*;

public class Config {

    /**
     * Logging
     */
    public static boolean DEBUG = true;
    public static int USER_ID = 100; //increments + 100 everytime the program gets opened
    public static int BLOCKS = 3; //Number of TrialBlocks | IF 0, generates all possible combinations for the given trials
    public static int PAUSE_AFTER_BLOCKNR = 1;
    public static int[] MONITOR_ZONES = {5, 5}; //Rows, Cols
    public static boolean MONITOR_ORIENTATION = true; // true = horizontal; false = vertical
    public static String LOG_PATH = "logs/"; //Directory, where logs get stored (has to be created beforehand)
    public static String EVENTS_LOG = "Events";
    public static String TRIALS_LOG = "Trials";
    public static String TESTTYPE = "MOOSE";
    public static String TRIALTYPE = "Hardcoded"; // Options: Random, Reciprocal, Hardcoded
    public static String TRIALSEQUENCE = "Sequence72_Alternative_1"; //Options: Sequence72_Alternative_1, Sequence72_Alternative_2,
                                             //Sequence18_1_MonitorJump, Sequence18_2_MonitorJump
    public static Boolean IS_SEEDED = false; //true, if every run should generate the same order of blocks
    public static Boolean ERROR_TO_END = false; // if true, trial gets pushed to end of block, else it's the next one.
    public static long SEED = 135495419; //seed, if IS_SEEDED is true
    public static boolean JUMPTOMID = false; // if true, the mouse jumps to the middle of the monitor

    public static boolean ANIMATION_RUNNING = false;

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
    public static int STARTFIELD_FONT_SIZE = 14;
    public static int STARTFIELD_HEIGHT = 76;
    public static int STARTFIELD_WIDTH = 76;

    /**
     * Text in upper left corner
     */
    public static int INFOTEXT_X = 20;
    public static int INFOTEXT_Y = 30;
    public static Color INFOTEXT_COLOR = Color.BLACK;
    public static int INFOTEXT_FONT_SIZE = 18;
    public static Color PAUSETEXT_COLOR = Color.white;
    public static int PAUSETEXT_FONT_SIZE = 22;
    public static String PAUSETEXT = "Trials pausiert";
    public static final String CONTINUESTRING = "Klicke irgendwo, um fortzufahren";
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
