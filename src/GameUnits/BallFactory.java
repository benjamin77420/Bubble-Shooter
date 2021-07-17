package GameUnits;

import Consts.Consts;

import java.awt.*;
import java.util.Random;

public class BallFactory implements Factory{
    /*
        implementing the single tone design pattern
     */
    private static BallFactory instance;
    private static Object holder = new Object();
    private Random rand = new Random();

    private BallFactory(){}

    public static BallFactory getFactory(){
        BallFactory result = instance;
        if(result == null){
            synchronized (holder){
                result = instance;
                if(result == null){
                    result = instance = new BallFactory();
                }
            }
        }
        return result;
    }

    @Override
    public Ball creatBall() {
        return new Ball(getRandomColor());
    }

    @Override
    public MovingBall creatMovingBall(Ball ball) {
        return new MovingBall(ball);
    }

    /*
        creating a ball with a random color
     */
    public Color getRandomColor(){
        int random = rand.nextInt(Consts.STARTING_ROW_MEDIUM);

        switch (random){
            case 0:
                return Color.RED;
            case 1:
                return Color.blue;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.PINK;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.MAGENTA;
            default:
                throw new IllegalArgumentException("Unknown random number was given for color selection process");
        }
    }
}
