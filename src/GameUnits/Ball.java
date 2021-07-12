package GameUnits;

import Consts.Consts;

import java.awt.*;

public class Ball {
    private Color color;
    private boolean mark;
    private volatile boolean visable;

    private Point location;

    public Ball(Color color) {
        this.color = color;
    }

    public Ball(Color color, Point location) {
        this.color = color;
        this.location = location;
        this.mark = false;

    }

    public Color getColor() {
        return color;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean isVisable() {
        return visable;
    }

    public void setVisable(boolean visable) {
        this.visable = visable;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRow(){
        return location.y/(Consts.ROW_DISTANCE/2);
    }

    public int getCol(){
        return location.x/Consts.BALL_SIZE;
    }

    public void paintBall(Graphics graphics){
        if(this.isVisable()){
            graphics.setColor(this.getColor());
            graphics.fillOval(this.location.x, this.location.y, Consts.BALL_SIZE, Consts.BALL_SIZE);
        }
    }

    /**
        @param movingBall - the ball that is moving in the game.
        @param stationBall - the ball that is part of the wall of balls
        @return the distance from the moving ball the the station ball
     */
    public double getBallsDistance(Ball movingBall, Ball stationBall){
        double distanceX = movingBall.getLocation().x-stationBall.getLocation().x;
        double distanceY = movingBall.getLocation().y-stationBall.getLocation().y;
        return Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
    }
}










