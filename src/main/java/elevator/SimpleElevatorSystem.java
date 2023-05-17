package elevator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class SimpleElevatorSystem implements ElevatorSystem{
    public final int floorsAmount;
    private final ArrayList<Elevator> elevators;
    private final ArrayList<Request> request = new ArrayList<>();

    public SimpleElevatorSystem(int elevatorAmount, int capacity, int floorsAmount) {
        //Check if there are enough floors for elevators to operate
        if (floorsAmount < 2){
            throw new IllegalArgumentException("FloorsAmount needs to be greater than 1.");
        }
        this.floorsAmount = floorsAmount;

        //Check if amount of elevators does not exceed 16 and is higher than 0
        if (elevatorAmount <= 0 || elevatorAmount > 16) {
            throw new IllegalArgumentException("The amount of elevators must range between 1 and 16.");
        }
        //Prevents elevator from having no/negative capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }

        this.elevators = IntStream.range(0, elevatorAmount)
                .mapToObj(i -> new Elevator(i, capacity, 0))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Direction with true value means going up, and false means going down
    @Override
    public void pickup(int floor, boolean direction) {
        if(!this.request.contains(new Request(floor, direction))){
            this.request.add(new Request(floor, direction));
        }
    }

    //Choose floor inside the elevator. It also acts as person going inside an elevator.
    @Override
    public void chooseFloor(int id, int destination){
        elevators.get(id).addDestination(destination);
        elevators.get(id).setCurrCapacity(elevators.get(id).getCurrCapacity() + 1);
    }

    @Override
    public void step() {
        //Check if elevator can take a new request
        Set<Elevator> freeElevators = this.elevators.stream()
                .filter(Elevator::isFree)
                .collect(Collectors.toSet());

        //Assign request to free elevators
        int assignAmount = Math.min(freeElevators.size(), this.request.size());
        for (int i=0; i < assignAmount; i++){
            int requestFloor = this.request.get(0).floor();

            Elevator nearestElevator = freeElevators.stream()
                    .min(Comparator.comparing(elevator -> Math.abs(elevator.getCurrFloor() - requestFloor)))
                    .get();
            nearestElevator.addDestination(this.request.get(0).floor());
            nearestElevator.setDirection(this.request.get(0).up());

            freeElevators.remove(nearestElevator);
            request.remove(0);
        }

        for (Elevator elevator : this.elevators) {
            //Close door
            elevator.setOpen(false);

            //Check if elevator should stop at destination floor
            if(elevator.hasDestination(elevator.getCurrFloor())){
                //Open door
                elevator.setOpen(true);

                elevator.unloadPassengers();
            }
            //In other cases, move elevator by 1 floor
            else if(!elevator.isFree()){
                elevator.move();
            }
        }
    }

    @Override
    public ArrayList<Elevator> status() {
        return elevators;
    }
}
