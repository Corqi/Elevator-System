package elevator;

import java.util.ArrayList;

public class SimpleElevatorSystem implements ElevatorSystem{
    public final int floorsAmount;
    private final ArrayList<Elevator> elevators = new ArrayList<>();

//    //1 if button on floor is pressed 0 when it's not
//    private Boolean[] upRequest;
//    private Boolean[] downRequest;

    private final ArrayList<Request> request = new ArrayList<>();

    public SimpleElevatorSystem(int elevatorAmount, int capacity, int floorsAmount) {
        this.floorsAmount = floorsAmount;
//        this.upRequest = new Boolean[floorsAmount];
//        this.downRequest = new Boolean[floorsAmount];

        //Check if amount of elevators does not exceed 16 and is higher than 0
        if (elevatorAmount <= 0 || elevatorAmount > 16) {
            throw new IllegalArgumentException("The amount of elevators must range between 1 and 16.");
        }
        //Prevents elevator from having no/negative capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        for (int i=0; i < elevatorAmount; i++){
            this.elevators.add(new Elevator(i, capacity, 0));
        }
    }

    //Direction with true value means going up, and false means going down
    public void pickup(int floor, boolean direction) {
//        if (direction == true){
//            this.upRequest[floor] = true;
//        }
//        else{
//            this.downRequest[floor] = true;
//        }
        if(!this.request.contains(new Request(floor, direction))){
            this.request.add(new Request(floor, direction));
        }
    }

    //Choose floor inside the elevator. It also acts as person going inside an elevator.
    public void chooseFloor(int id, int destination){
        elevators.get(id).destFloor.add(destination);
        elevators.get(id).setCurrCapacity(elevators.get(id).getCurrCapacity() + 1);
    }

    public void update(int id, int currFloor, ArrayList<Integer> destFloor) {
        elevators.get(id).setCurrFloor(currFloor);
        elevators.get(id).destFloor = destFloor;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            //Close door
            elevator.setOpen(false);
            //Check if elevator can take a new request
            if (elevator.destFloor.isEmpty()) {
                if (!this.request.isEmpty()) {
                    elevator.destFloor.add(this.request.get(0).floor);
                    elevator.setDirection(this.request.get(0).direction);
                    request.remove(0);
                }
            }
            //Check if elevator should stop at destination floor
            else if(elevator.destFloor.contains(elevator.getCurrFloor())){
                //Open door
                elevator.setOpen(true);
                //Let out all passengers at their destination
                elevator.destFloor.removeIf(i -> i == elevator.getCurrFloor());

                //Subtract it from current capacity
                elevator.setCurrCapacity(elevator.destFloor.size());
            }
            //In other cases, move elevator by 1 floor
            else{
                int direction = elevator.destFloor.get(0) > elevator.getCurrFloor() ? 1 : -1;
                elevator.setCurrFloor(elevator.getCurrFloor() + direction);
            }
        }
    }

    public ArrayList<Elevator> status() {
        return elevators;
    }
}
