package elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;


public class ManualSimulation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    //True will make each step being visually drawn in console, False will output text based information
    private final boolean useDraw;

    //Destinations of people at each floor, for each direction
    private final ArrayList<Integer>[] upRequests;
    private final ArrayList<Integer>[] downRequests;
    //True if button on i'th floor is pressed, for each direction
    private final boolean[] upPressed;
    private final boolean[] downPressed;

    private final SimpleElevatorSystem system;

    public ManualSimulation(int elevatorAmount, int capacity, int floorsAmount, Boolean useDraw) {
        this.useDraw = useDraw;

        if (floorsAmount < 2){
            throw new IllegalArgumentException("FloorsAmount needs to be greater than 1.");
        }
        this.system = new SimpleElevatorSystem(elevatorAmount, capacity, floorsAmount);

        this.upRequests = Stream.generate(ArrayList<Integer>::new)
                .limit(floorsAmount)
                .toArray(ArrayList[]::new);
        this.downRequests = Stream.generate(ArrayList<Integer>::new)
                .limit(floorsAmount)
                .toArray(ArrayList[]::new);

        this.upPressed = new boolean[floorsAmount];
        this.downPressed = new boolean[floorsAmount];
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        int input;

        while (true){
            System.out.print("\n\n");
            //Generate pickup requests for each floor
            for (int j = 0; j < system.floorsAmount; j++){
                if (this.upRequests[j].size() > 0 && !this.upPressed[j]){
                    system.pickup(j,true);
                    this.upPressed[j] = true;
                }
                if (this.downRequests[j].size() > 0 && !this.downPressed[j]){
                    system.pickup(j, false);
                    this.downPressed[j] = true;
                }
            }
            //Check if users can enter elevator
            for (Elevator elevator: this.system.status()){
                //Elevator going up, has open doors and there are users at the current floor
                if (upRequests[elevator.getCurrFloor()].size() > 0 && elevator.getDirection() && elevator.isOpen()){
                    //Generate destination for each user that can fit
                    int people = Math.min(upRequests[elevator.getCurrFloor()].size(),
                            elevator.getCapacity() - elevator.getCurrCapacity());
                    for (int j=0; j < people; j++){
                        int destFloor = this.upRequests[elevator.getCurrFloor()].get(0);
                        system.chooseFloor(elevator.getId(), destFloor);

                        //Subtract waiting line
                        this.upRequests[elevator.getCurrFloor()].remove(0);
                    }

                    //Release button
                    upPressed[elevator.getCurrFloor()] = false;
                }
                //Elevator going down, has open doors and there are users at the current floor
                else if (downRequests[elevator.getCurrFloor()].size() > 0 && !elevator.getDirection() && elevator.isOpen()){
                    //Generate destination for each user that can fit
                    int people = Math.min(downRequests[elevator.getCurrFloor()].size(),
                            elevator.getCapacity() - elevator.getCurrCapacity());
                    for (int j=0; j < people; j++){
                        int destFloor = this.downRequests[elevator.getCurrFloor()].get(0);
                        system.chooseFloor(elevator.getId(), destFloor);

                        //Subtract waiting line
                        this.downRequests[elevator.getCurrFloor()].remove(0);
                    }

                    //Release button
                    downPressed[elevator.getCurrFloor()] = false;
                }
            }

            if (this.useDraw){
                drawStatus(this.system);
            }
            else {
                printStatus(this.system.status());
            }
            System.out.println("Choose an option:");
            System.out.println("1 - Make simulation step");
            System.out.println("2 - Add person");
            System.out.println("3 - Exit simulation");
            System.out.print("Your choice: ");

            if (scanner.hasNextInt()) {
                input = scanner.nextInt();

                if (input == 1) {
                    this.system.step();
                }
                else if (input == 2){
                    System.out.print("\n\n");

                    //Get person parameters from user input
                    int initialFloor;
                    while (true) {
                        try {
                            System.out.print("Enter initial floor of person (0-" + (system.floorsAmount - 1) + "): ");
                            initialFloor = scanner.nextInt();
                            scanner.nextLine();

                            if (initialFloor >= 0 && initialFloor < system.floorsAmount) {
                                break;
                            } else {
                                System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            }
                        }
                        catch (InputMismatchException e){
                            System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            //Clear invalid input
                            scanner.nextLine();
                        }
                    }

                    int destination;
                    while (true) {
                        try {
                            System.out.print("Enter destination floor of person (0-" + (system.floorsAmount - 1)
                                    + " and must be different floor than initial floor): ");
                            destination = scanner.nextInt();
                            scanner.nextLine();

                            if (destination >= 0 && destination < system.floorsAmount && destination != initialFloor) {
                                break;
                            } else {
                                System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            }
                        }
                        catch (InputMismatchException e){
                            System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            //Clear invalid input
                            scanner.nextLine();
                        }
                    }

                    boolean up = (destination - initialFloor) > 0;

                    if (up){
                        this.upRequests[initialFloor].add(destination);
                    }
                    else {
                        this.downRequests[initialFloor].add(destination);
                    }
                }
                else if (input == 3){
                    break;
                }
                else{
                    System.out.println(ANSI_RED + "Invalid input. Please try again." + ANSI_RESET);
                }
            }
            else{
                System.out.println(ANSI_RED + "Invalid input. Please try again." + ANSI_RESET);
                scanner.nextLine();
            }


        }
    }

    public void printStatus(ArrayList<Elevator> elevators){
        System.out.println("Uprequests: " + ANSI_GREEN + Arrays.toString(this.upRequests) + ANSI_RESET);
        System.out.println("Downrequests: " + ANSI_RED + Arrays.toString(this.downRequests) + ANSI_RESET);
        for (Elevator elevator : elevators){
            System.out.println("Elevator no: " + elevator.getId() + " Floor: " + elevator.getCurrFloor() + " Capacity: "
                    + elevator.getCurrCapacity() + " Destinations: " + elevator.getDestFloors() + " Doors open: "
                    + elevator.isOpen());
        }
        System.out.println();
    }

    public void drawStatus(SimpleElevatorSystem system){
        for (int i=system.floorsAmount-1; i >= 0; i--){
            System.out.print(ANSI_GREEN + this.upRequests[i].size() + "\t");
            System.out.print(ANSI_RED + this.downRequests[i].size() + ANSI_RESET + "\t");
            for (Elevator elevator: system.status()){
                if (elevator.getCurrFloor() == i){
                    if (elevator.getDirection()){
                        System.out.print(ANSI_GREEN);
                    }
                    else {
                        System.out.print(ANSI_RED);
                    }
                    System.out.print(elevator.getCurrCapacity() + ANSI_RESET + "\t");
                }
                else if (elevator.getDestFloors().contains(i)){
                    System.out.print("X" + "\t");
                }
                else {
                    System.out.print("|" + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("=".repeat((system.status().size() + 2) * 4 - 3));
    }
}
