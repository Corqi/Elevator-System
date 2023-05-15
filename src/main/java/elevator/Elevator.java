package elevator;

import java.util.ArrayList;

public class Elevator {
    private final int id;
    private final int capacity;
    private int currCapacity;
    private int currFloor;
    //true is up request, and false is down request
    private boolean direction;
    //true when doors are open, and false when closed
    private boolean isOpen;
    public ArrayList<Integer> destFloor = new ArrayList<>();

    public Elevator(int id, int capacity, int currFloor) {
        this.id = id;

        //Prevents elevator from having no/negative capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;

        this.currCapacity = 0;
        this.currFloor = currFloor;

        this.direction = true;
        this.isOpen = false;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrCapacity() {
        return currCapacity;
    }

    public void setCurrCapacity(int currCapacity) {
        //Prevents elevator from exceeding max or having less than 0 capacity
        if (this.capacity < currCapacity || currCapacity < 0) {
            throw new IllegalArgumentException("Current capacity cannot exceed maximum capacity and must be at least 0.");
        }
        this.currCapacity = currCapacity;
    }

    public void setCurrFloor(int currFloor) {
        this.currFloor = currFloor;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
