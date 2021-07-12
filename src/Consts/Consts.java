package Consts;

import java.awt.*;

public class Consts {

    //window lenght and breadth
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HIGHT = 1000;

    public static final int WINDOW_HEADER = 30;
    public static final double BORDER_WIDTH = 3.5;

    //will be used as lenght and breadth
    public static final int BALL_SIZE = 40;
    public static final int BALL_RADIUS = BALL_SIZE/2;


    //number of units in a row
    public static final int FULL_ROW_COUNT = WINDOW_WIDTH/BALL_SIZE;
    public static final int SEMI_ROW_COUNT = FULL_ROW_COUNT-1;
    public static final int ROW_DISTANCE = BALL_SIZE*2;
    public static final int MAX_ROW_PRESENT = ((WINDOW_HIGHT-WINDOW_HEADER)/BALL_SIZE)-3;

    //difficulty levels
    public static final int STARTING_ROW_EASY = 4;
    public static final int STARTING_ROW_MEDIUM = 7;
    public static final int STARTING_ROW_HARD = 10;

    //ball's speed
    public static final int BALLS_SPEED = 1;

    //the frames per second of the moving ball
    public static final int FPS = 1;

    //players magazin size
    public static final int PLAYER_MAGAZIN_SIZE = 5;

    //player starting point
    public static final Point STARTING_POINT = new Point(WINDOW_WIDTH/2 - BALL_RADIUS, WINDOW_HIGHT - BALL_SIZE);


}
