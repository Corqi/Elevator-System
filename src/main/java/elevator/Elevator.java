package elevator;

import java.util.ArrayList;


public class Elevator {
    private final int id;
    private final int capacity;
    private int currCapacity;
    private int currFloor;
    private boolean up;
    //true when doors are open, and false when closed
    private boolean isOpen;
    private final ArrayList<Integer> destFloors = new ArrayList<>();

    public Elevator(int id, int capacity, int currFloor) {
        this.id = id;

        //Prevents elevator from having no/negative capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;

        this.currCapacity = 0;
        this.currFloor = currFloor;

        this.up = true;
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

    public int getCurrFloor() {
        return currFloor;
    }

    public boolean getDirection() {
        return up;
    }

    public void setDirection(boolean up) {
        this.up = up;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isFree(){
        return this.destFloors.isEmpty();
    }

    public ArrayList<Integer> getDestFloors() {
        return destFloors;
    }

    public void addDestination(int destination){
        this.destFloors.add(destination);
    }

    public boolean hasDestination(int destination){
        return this.destFloors.contains(destination);
    }

    public void unloadPassengers(){
        //Let out all passengers at their destination
        this.destFloors.removeIf(i -> i == this.currFloor);

        //Subtract it from current capacity
        this.currCapacity = this.destFloors.size();
    }

    public void move(){
        int direction = this.destFloors.get(0) > this.currFloor ? 1 : -1;
        this.currFloor += direction;
    }
}
