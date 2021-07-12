package Player;

import Consts.Consts;
import GameUnits.Ball;
import GameUnits.BallFactory;
import GameUnits.MovingBall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Ball> ballMagazin;
    private final BallFactory ballFactory = BallFactory.getFactory();
    private MovingBall playerMovingBall;

    public Player() {
        this.ballMagazin = loadMagazin();
    }

    private List<Ball> loadMagazin(){
        List<Ball> playersMagazin = new ArrayList<>();
        for(int i = 0; i< Consts.PLAYER_MAGAZIN_SIZE; ++i){//loading the players magazine with balls
            Ball newBall = ballFactory.creatBall();
            newBall.setVisable(true);
            playersMagazin.add(newBall);
        }
        return playersMagazin;
    }

    /**
     * @param mousePoint * the currnt location of the courser on the panel
     */
    public void fire(Point mousePoint){
        //get the status of the moving ball
        boolean currntlyMoving = !(this.playerMovingBall == null);
        currntlyMoving = (currntlyMoving && this.playerMovingBall.isMoving());

        //if there is no moving ball
        if(!currntlyMoving){
            playerMovingBall = ballFactory.creatMovingBall(ballMagazin.remove(0));//invoking the ball factory to create a moving ball
            playerMovingBall.lounchBall(mousePoint);//FIRE
            //loading a new ball to the magazine
            Ball newBall = ballFactory.creatBall();
            newBall.setVisable(true);
            ballMagazin.add(newBall);
        }
    }

    public void displayMagazin(Graphics graphics){
        //display the loaded ball
        this.ballMagazin.get(0).setLocation(Consts.STARTING_POINT);
        this.ballMagazin.get(0).paintBall(graphics);

        //display the rest of the ball magazin
        for(int i=1; i<ballMagazin.size(); ++i){//displaying the magazin the the right locations
            Point magazinDisplay = new Point(40+(Consts.BALL_SIZE*i-1), Consts.WINDOW_HIGHT - Consts.BALL_SIZE);
            this.ballMagazin.get(i).setLocation(magazinDisplay);
            this.ballMagazin.get(i).paintBall(graphics);
        }
    }



    public MovingBall getPlayerMovingBall() {
        return playerMovingBall;
    }
}