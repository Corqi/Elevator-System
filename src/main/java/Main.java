import elevator.Elevator;
import elevator.ElevatorSystem;
import elevator.SimpleElevatorSystem;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        Elevator el = new Elevator(0,5,0);
//        System.out.println(el.destFloor);
//        el.destFloor.add(1);
//        el.destFloor.remove(0);
//        System.out.println(el.destFloor);
        ElevatorSystem system = new SimpleElevatorSystem(1,1,10);

        system.pickup(5,true);
        for(int i=0; i < 7; i++){
            printStatus(system.status());
            system.step();
        }
        printStatus(system.status());
        system.chooseFloor(0,9);
        for(int i=0; i < 10; i++){
            printStatus(system.status());
            system.step();
        }

    }

    public static void printStatus(ArrayList<Elevator> elevators){
        for (Elevator elevator : elevators){
            System.out.println("Elevator no: " + elevator.getId() + " Floor: " + elevator.getCurrFloor() + " Capacity: "
                    + elevator.getCurrCapacity() + " Destinations: " + elevator.destFloor);
        }
    }
}
