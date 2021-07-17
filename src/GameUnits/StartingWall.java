package GameUnits;

import Consts.Consts;

import java.awt.*;
import java.util.ArrayList;

public class StartingWall {
    private ArrayList<RowList> ballRows;
    private final BallFactory factory;

    public StartingWall() {
        this.ballRows = new ArrayList<RowList>();
        this.factory = BallFactory.getFactory();
    }

    public void buildWall(){
        for(int i=0 ; i<Consts.MAX_ROW_PRESENT; ++i){
            RowList row = new RowList((i%2==0 ? true : false));//setting of the currnt row needs to be a full row or a semi row
            ballRows.add(row);

            for(int j=0; j<(row.isFull() ? Consts.FULL_ROW_COUNT : Consts.SEMI_ROW_COUNT); ++j){//filling the row with all balls
                Ball newBall = factory.creatBall();//invoking the ball factory for a new ball
                newBall.setLocation(new Point(//setting the ball location while regarding its index in the row and the nature of the row itself
                        row.isFull() ?
                                j*Consts.BALL_SIZE : j*Consts.BALL_SIZE + Consts.BALL_RADIUS,
                        row.isFull() ?
                                (i/2)*Consts.ROW_DISTANCE + Consts.WINDOW_HEADER:
                                (i/2)*Consts.ROW_DISTANCE+Consts.ROW_DISTANCE/2 + Consts.WINDOW_HEADER)
                );
                if(i<Consts.STARTING_ROW_MEDIUM){//the row needs to be visible, changeable by altering the difficulty variable
                    newBall.setVisable(true);
                }
                row.add(newBall);
            }

        }
    }

    /**
     * adds a row to the wall of balls
     */
    public void addRow(){
        this.ballRows.remove(Consts.MAX_ROW_PRESENT-1);// makeing room for a new row to be added.
        for(RowList row: this.ballRows){
            for(Ball ball: row){//moving all the balls one row down by changeing the Y position value.
                ball.setLocation(new Point(ball.getLocation().x, ball.getLocation().y+Consts.BALL_SIZE));
            }
        }

        RowList newRow = new RowList(!this.ballRows.get(0).isFull());//creating the new row, setting its property based on the first row.

        for(int i=0; i<(newRow.isFull()? Consts.FULL_ROW_COUNT: Consts.SEMI_ROW_COUNT); ++i){
            Ball newBall = this.factory.creatBall();
            newBall.setLocation(//setting its location based on the property of the row.
                    new Point(newRow.isFull()?
                            i*Consts.BALL_SIZE:
                            i*Consts.BALL_SIZE+Consts.BALL_RADIUS,Consts.WINDOW_HEADER
                    ));
            newBall.setVisable(true);
            newRow.add(newBall);
        }
        this.ballRows.add(0,newRow);
    }

    /**
     * @param row - the index that the ball is located at in the balls rows
     * @param col - the index of the ball in the row it is locaited at
     * @return all the balles that are visable and are interacting with the ball in the row and col location
     */
    public ArrayList<Ball> getSurroundingBalls(int row, int col){
        ArrayList<Ball> surroundingBalls = new ArrayList<Ball>();
        //get left ball
        if (col>0) surroundingBalls.add(ballRows.get(row).get(col-1));
        //get right ball
        if (col < (ballRows.get(row).isFull() ? Consts.FULL_ROW_COUNT : Consts.SEMI_ROW_COUNT)-1){
            surroundingBalls.add(ballRows.get(row).get(col+1));
        }
        //get upper left
        if (ballRows.get(row).isFull() && col > 0 && row > 0){
            surroundingBalls.add(ballRows.get(row-1).get(col-1));
        }
        if (!ballRows.get(row).isFull() && row > 0){
            surroundingBalls.add(ballRows.get(row-1).get(col));
        }
        //get upper right
        if (ballRows.get(row).isFull() && col < Consts.FULL_ROW_COUNT-1 && row > 0){
            surroundingBalls.add(ballRows.get(row-1).get(col));
        }
        if (!ballRows.get(row).isFull() && row > 0){
            surroundingBalls.add(ballRows.get(row-1).get(col	+1));
        }
        //get lower left
        if (ballRows.get(row).isFull() && col > 0 && row < Consts.MAX_ROW_PRESENT-1){
            surroundingBalls.add(ballRows.get(row+1).get(col-1));
        }
        if (!ballRows.get(row).isFull() && row < Consts.MAX_ROW_PRESENT-1){
            surroundingBalls.add(ballRows.get(row+1).get(col));
        }
        //get lower right
        if (ballRows.get(row).isFull() && col < Consts.FULL_ROW_COUNT-1 && row < Consts.MAX_ROW_PRESENT-1){
            surroundingBalls.add(ballRows.get(row+1).get(col));
        }
        if (!ballRows.get(row).isFull() && row < Consts.MAX_ROW_PRESENT-1){
            surroundingBalls.add(ballRows.get(row+1).get(col+1));
        }

        return surroundingBalls;
    }

    public ArrayList<RowList> getBallRows() {
        return ballRows;
    }

    public void paint(Graphics graphics){
        for(RowList row : ballRows){
            for(Ball ball : row){
                ball.paintBall(graphics);
            }
        }
    }

    /**
     * @param row -the index that the ball is located at in the balls rows
     * @param col -the index of the ball in the row it is locaited at
     */
    public boolean removeColorStrik(int row, int col){
        markColor(row, col);
        boolean isSuccessfulShots = false;
        int markedBalls = countMarked();

        if(markedBalls > 2){
            removeMarked();
            isSuccessfulShots = true;
        }
        modifyAllMark(false);
        return isSuccessfulShots;
    }

    /**
        mark all the balls that have the same color as the one
        that was fired
        @param row - the row index of the fired ball landing location
        @param col - the col index of the fired ball landing location
     */
    public void markColor(int row, int col){
        this.ballRows.get(row).get(col).setMark(true);
        for(Ball ball: getSurroundingBalls(row,col)){
            if(ball.isVisable() && !ball.isMark()){
                if(ball.getColor().equals(this.ballRows.get(row).get(col).getColor())){
                    markColor(ball.getRow(), ball.getCol());
                }
            }
        }
    }

    /*
        removes all the balls that are floating with no connection to the balls rows
     */
    public void removeFloatingBalls(){
        modifyAllMark(true);
        for(Ball ball: this.ballRows.get(0)){
            if(ball.isVisable()){
                unMarkNotFloating(ball.getRow(), ball.getCol());
            }
        }

        removeMarked();
    }

    /**
     *@param row - the row index of the fired ball landing location
     *@param col - the col index of the fired ball landing location
     */
    public void unMarkNotFloating(int row, int col){
        this.ballRows.get(row).get(col).setMark(false);
        for(Ball ball: getSurroundingBalls(row,col)){
            if(ball.isVisable() && ball.isMark()){
                unMarkNotFloating(ball.getRow(), ball.getCol());
            }
        }
    }

    //scans the rows for marked balls and returns the amount of maked balls found
    public int countMarked(){
        int markedCaunter = 0;
        for(RowList row: this.ballRows){
            for(Ball ball: row){
                if(ball.isVisable() && ball.isMark()){
                    markedCaunter++;
                }
            }
        }
        return markedCaunter;
    }

    //removing the marked balls
    public int removeMarked(){
        int removedAmount = 0;
        for(RowList row: this.ballRows){
            for(Ball ball: row){
                if(ball.isVisable() && ball.isMark()){
                    ball.setVisable(false);
                    removedAmount++;
                }
            }
        }

        return removedAmount;
    }

    public void modifyAllMark(boolean markStatus){
        for(RowList row: this.ballRows){
            for(Ball ball: row){
                ball.setMark(markStatus);
            }
        }
    }

}
