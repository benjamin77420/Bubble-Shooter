package MainGame;

import Consts.Consts;
import GameUnits.Ball;
import GameUnits.BallFactory;
import GameUnits.StartingWall;
import Player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScene extends JPanel {

    public Player player = new Player();
    private final StartingWall startingWall = new StartingWall();


    public Point point = new Point();
    public static void main(String[] args) {
        GameScene main = new GameScene();
    }

    public GameScene(){
        this.init();//create the panel
        startingWall.buildWall();//build the starting wall of balls
        new Thread(()->{
            while (true){
                if(player.getPlayerMovingBall() != null && player.getPlayerMovingBall().isVisable()){//the ball exists and is moving
                    checkBallProximity();//chcek if the ball is close hitting a stationery ball
                }
                repaint();
            }
        }).start();
    }

    public void init() {
        this.setVisible(true);
        this.setSize(Consts.WINDOW_WIDTH,Consts.WINDOW_HIGHT);
        this.setLayout(null);
    }

    public void checkBallProximity(){
        //the X and Y of the moving ball
        int movingBallX = player.getPlayerMovingBall().getLocation().x;
        int movingBallY = player.getPlayerMovingBall().getLocation().y;
        //the balls currnt row position
        int row = (movingBallY - Consts.BALL_RADIUS)/((Consts.ROW_DISTANCE/2)-5);
        int col;

        if(row < Consts.MAX_ROW_PRESENT){//it is locaited in the rows of balls
            if(startingWall.getBallRows().get(row).isFull()){//the currnt row is a full row
                col = (movingBallX)/(Consts.BALL_SIZE);
            }else{//the currnt row is a semi full row
                col = (movingBallX-(Consts.BALL_RADIUS+1))/(Consts.BALL_SIZE);
            }

            if(row == 0){//the ball has hit the most upper border
                fixBallPosition(row,col);
            }

            ArrayList<Ball> surroundingBalls = startingWall.getSurroundingBalls(row, col);

            for(Ball ball : surroundingBalls){//checking if there is a interaction with the surrounding balls
                if(ball.isVisable() && ball.getBallsDistance(player.getPlayerMovingBall(), ball) <= 4+Consts.BALL_SIZE){//the two balls are in close proximity
                    fixBallPosition(row,col);
                    break;
                }
            }
        }
    }

    /**
     * @param row - the index that the ball is located at in the balls rows
     * @param col - the index of the ball in the row it is locaited at
     */
    public void fixBallPosition(int row, int col){
        //immobilizing the moving ball and vanishing it
        player.getPlayerMovingBall().setMoving(false);
        player.getPlayerMovingBall().setVisable(false);
        Point newPoint;

        if(startingWall.getBallRows().get(row).get(col).isVisable()){//the location that was set for the ne wball is already taken
            col+=1;//setting the location of the new ball one unit to the right
        }

        newPoint = startingWall.getBallRows().get(row).get(col).getLocation();//getting he point of the new location
        Ball newBall = new Ball(player.getPlayerMovingBall().getColor(), newPoint);//creating a new ball that will be fitted in the new location
        newBall.setVisable(true);//making the new ball visible
        startingWall.getBallRows().get(row).set(col, newBall);//setting a new ball in the impact location

        startingWall.removeColorStrik(row, col);//removing a color stike
        startingWall.removeFloatingBalls();//removing floating balls
    }


    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawLine(Consts.WINDOW_WIDTH /2, Consts.WINDOW_HIGHT - Consts.BALL_RADIUS, (int)point.getX(), (int)point.getY());
        player.displayMagazin(g);
        if(!(player.getPlayerMovingBall() == null)){
            player.getPlayerMovingBall().paint(g);
        }
        startingWall.paint(g);
    }
}
