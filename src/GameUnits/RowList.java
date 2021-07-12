package GameUnits;

import java.util.ArrayList;

public class RowList extends ArrayList<Ball> {
    /*
        this class will be used as an implementation of the array list with an additional boolean value that will
        be queried any time that the program needs to no if the currnt row is a full row ar is a semi full row
     */
    private boolean full;

    public RowList(boolean isFull){
        this.full = isFull;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}
