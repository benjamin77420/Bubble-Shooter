package Consts;

public enum Directions {
    //directions enum

    STAND_STILL(0),
    UP_LEFT(1),
    UP_RIGHT(2);

    private int direction;

    Directions(int direction){
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
