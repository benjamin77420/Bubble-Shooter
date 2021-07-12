package GameUnits;

import Consts.Consts;
import Consts.Directions;

import java.awt.*;


public class MovingBall extends Ball {

    private volatile Directions ballDirection;
    private volatile boolean isMoving;

    /**
        @param ball - a ball that his properties will be used to create a moving ball
        with the same properties.
     */
    public MovingBall(Ball ball) {
        super(ball.getColor(), Consts.STARTING_POINT);
        this.ballDirection = Directions.STAND_STILL;
        this.setVisable(true);

        this.isMoving = true;
    }

    /**
        @param direction - the point that the cursors is locaited on.
     */
    public void lounchBall(Point direction){
        if(getBallDirection() != Directions.STAND_STILL)//the ball is no longer standing still
            return;

        synchronized(this.ballDirection){//creating a synchronization point for all the threads
            if(getBallDirection() != Directions.STAND_STILL)//if the thread is not the first thread entering the critical section
                return;

            new Thread(() ->{
                //currnt x and y locations on the panel
                float currntX = this.getLocation().x;
                float currntY = this.getLocation().y;

                float offsetX = direction.x - Consts.WINDOW_WIDTH/2;
                float offsetY = direction.y - Consts.WINDOW_HIGHT;

                double distance = Math.sqrt(Math.pow((double)offsetX, 2) + Math.pow((double)offsetY, 2));

                //setting the steps that will be taken on the X and Y axis values for the
                double stepX =((offsetX/distance*Consts.BALLS_SPEED));
                double stepY = ((offsetY/distance*Consts.BALLS_SPEED));

                //setting the direction of the ball's movement
                if(stepX > 0){
                    setBallDirection(Directions.UP_RIGHT);
                }else {
                    setBallDirection(Directions.UP_LEFT);
                }

                while (isMoving){
                    if(currntX + stepX < 0){//next step will hit the left border
                        currntX = (int) (currntX + stepX);//converting the floating value to a int, will be usuali zero
                        stepX = -stepX;//negating the value of the next step so it will look like the ball is bouncing
                    }
                    else if(currntX+stepX > Consts.WINDOW_WIDTH - Consts.BALL_SIZE){//next step will hit the right border
                        currntX = (int) ((Consts.WINDOW_WIDTH - Consts.BALL_SIZE) * 2 -(currntX+stepX));//mirroring the impact location and to simulate as it is hitting the left side border
                        stepX = -stepX;//negating the value of the next step so it will look like the ball is bouncing
                    }
                    else{//no border was hit
                        currntX += stepX;
                    }

                    currntY += stepY;

                    this.setLocation(new Point((int)currntX, (int)currntY));//updating the location of the ball

                    try {
                        Thread.sleep(Consts.FPS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void paint(Graphics graphics){
        if(this.isVisable()){
            graphics.setColor(getColor());
            graphics.fillOval(this.getLocation().x, this.getLocation().y, Consts.BALL_SIZE, Consts.BALL_SIZE);
        }
    }

    public Directions getBallDirection() {
        return ballDirection;
    }

    public void setBallDirection(Directions ballDirection) {
        this.ballDirection = ballDirection;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }


}
