package elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Simulation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    //True will make each step being visually drawn in console, False will output text based information
    private final boolean useDraw;
    //Time in between each step (in milliseconds)
    private final int pauseTime;
    //Frequency is number of steps in between every user batch generation
    private final int frequency;
    //FrequencyAmount is max number people that will generate each batch
    private final int frequencyAmount;

    //Steps in simulation
    private final int simulationSteps;
    //Simulation seed
    private final long seed;
    //Number of users generated during simulation
    private int users;
    //Combined waiting time of all users
    private int waitTime;

    //Number of people at each floor, for each direction
    private final int[] upRequests;
    private final int[] downRequests;
    //True if button on i'th floor is pressed, for each direction
    private final boolean[] upPressed;
    private final boolean[] downPressed;
    private final SimpleElevatorSystem system;

    public Simulation(int simulationSteps, long seed, int frequency, int frequencyAmount, int elevatorAmount, int capacity,
                      int floorsAmount, Boolean useDraw, int pauseTime) {
        this.useDraw = useDraw;
        if (pauseTime < 0){
            throw new IllegalArgumentException("PauseTime needs to be equal or greater than 0.");
        }
        this.pauseTime = pauseTime;
        if (simulationSteps < 1){
            throw new IllegalArgumentException("simulationSteps needs to be equal or greater than 0.");
        }
        this.simulationSteps = simulationSteps;
        if (frequency < 1){
            throw new IllegalArgumentException("Frequency needs to be greater than 0.");
        }
        this.seed = seed;
        this.users = 0;
        this.waitTime = 0;

        this.frequency = frequency;
        if (frequencyAmount < 1){
            throw new IllegalArgumentException("FrequencyAmount needs to be greater than 0.");
        }
        this.frequencyAmount = frequencyAmount;

        this.system = new SimpleElevatorSystem(elevatorAmount, capacity, floorsAmount);

        this.upRequests = new int[floorsAmount];
        this.downRequests = new int[floorsAmount];

        this.upPressed = new boolean[floorsAmount];
        this.downPressed = new boolean[floorsAmount];
    }

    public void run(){
        Random random = new Random(this.seed);

        for (int i = 0; i < this.simulationSteps; i++){
            //Start by generating users every frequency number of steps
            if (i % this.frequency == 0){
                int frequencyAmount = random.nextInt(this.frequencyAmount) + 1;
                //Adding generated users to combined number
                this.users += frequencyAmount;

                for (int j=0; j < frequencyAmount; j++){
                    //Generate user's current floor
                    int userFloor = random.nextInt(this.system.floorsAmount);

                    //Generate user direction
                    int randInt = random.nextInt(2);
                    boolean direction = randInt == 0;
                    //Special cases for edge floors
                    if (userFloor == this.system.floorsAmount - 1){
                        direction = false;
                    }
                    else if(userFloor == 0){
                        direction = true;
                    }

                    if (direction){
                        this.upRequests[userFloor] += 1;
                    }
                    else {
                        this.downRequests[userFloor] += 1;
                    }
                }
            }
            //Generate pickup requests for each floor
            for (int j = 0; j < system.floorsAmount; j++){
                if (this.upRequests[j] > 0 && !this.upPressed[j]){
                    system.pickup(j,true);
                    this.upPressed[j] = true;
                }
                if (this.downRequests[j] > 0 && !this.downPressed[j]){
                    system.pickup(j, false);
                    this.downPressed[j] = true;
                }

                //Add users (ones not in elevators) waitTime to combined amount
                this.waitTime += this.upRequests[j] + this.downRequests[j];
            }
            //Check if users can enter elevator
            for (Elevator elevator: this.system.status()){
                //Add users (ones in elevators) waitTime to combined amount
                this.waitTime += elevator.getCurrCapacity();

                //Elevator going up, has open doors and there are users at the current floor
                if (upRequests[elevator.getCurrFloor()] > 0 && elevator.getDirection() && elevator.isOpen()){
                   //Generate destination for each user that can fit
                    int people = Math.min(upRequests[elevator.getCurrFloor()],
                            elevator.getCapacity() - elevator.getCurrCapacity());
                    for (int j=0; j < people; j++){
                        int destFloor = random.nextInt(system.floorsAmount - 1 - elevator.getCurrFloor()) + 1 + elevator.getCurrFloor();
                        system.chooseFloor(elevator.getId(), destFloor);
                    }

                    //Subtract waiting line
                    upRequests[elevator.getCurrFloor()] -= people;

                    //Release button
                    upPressed[elevator.getCurrFloor()] = false;
                }
                //Elevator going down, has open doors and there are users at the current floor
                else if (downRequests[elevator.getCurrFloor()] > 0 && !elevator.getDirection() && elevator.isOpen()){
                    //Generate destination for each user that can fit
                    int people = Math.min(downRequests[elevator.getCurrFloor()],
                            elevator.getCapacity() - elevator.getCurrCapacity());
                    for (int j=0; j < people; j++){
                        int destFloor = random.nextInt(elevator.getCurrFloor());
                        system.chooseFloor(elevator.getId(), destFloor);
                    }

                    //Subtract waiting line
                    downRequests[elevator.getCurrFloor()] -= people;

                    //Release button
                    downPressed[elevator.getCurrFloor()] = false;
                }
            }
            //Show visual/text feedback
            if (useDraw){
                drawStatus(system);
            }
            else{
                printStatus(system.status());
            }
            //Simulate step
            system.step();
            //Wait for pauseTime
            try {
                Thread.sleep(this.pauseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            System.out.print(ANSI_GREEN + this.upRequests[i] + "\t");
            System.out.print(ANSI_RED + this.downRequests[i] + ANSI_RESET + "\t");
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
        System.out.println("=".repeat((system.status().size() + 2) * 4 - 3)  + "\n");
    }

    public float calculateAverageWaitTime(){
        return (float) this.waitTime / this.users;
    }
}
