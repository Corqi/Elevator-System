import elevator.ManualSimulation;
import elevator.Simulation;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;

        while (true){
            System.out.println("Choose an option:");
            System.out.println("1 - Create automatic simulation");
            System.out.println("2 - Create manual simulation");
            System.out.println("3 - Exit program");
            System.out.print("Your choice: ");

            if (scanner.hasNextInt()){
                input = scanner.nextInt();

                if (input == 1){
                    System.out.print("\n\n");

                    //Get simulation parameters from user input
                    int simulationSteps;
                    while (true) {
                        try {
                            System.out.print("Enter simulation steps (minimum 1): ");
                            simulationSteps = scanner.nextInt();
                            scanner.nextLine();

                            if (simulationSteps >= 1) {
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

                    long seed;
                    while (true) {
                        try {
                            System.out.print("Enter seed (0 for randomized): ");
                            seed = scanner.nextLong();
                            scanner.nextLine();

                            if (seed == 0) {
                                seed = System.currentTimeMillis();
                                break;
                            }
                            break;
                        }
                        catch (InputMismatchException e){
                            System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            //Clear invalid input
                            scanner.nextLine();
                        }
                    }

                    int frequency;
                    while (true) {
                        try {
                            System.out.println("Frequency is number of steps in between every user batch generation");
                            System.out.print("Enter frequency (minimum 1): ");
                            frequency = scanner.nextInt();
                            scanner.nextLine();

                            if (frequency >= 1) {
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

                    int frequencyAmount;
                    while (true) {
                        try {
                            System.out.println("Frequency amount is max number of people that will generate each batch");
                            System.out.print("Enter frequency amount (minimum 1): ");
                            frequencyAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (frequencyAmount >= 1) {
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

                    int elevatorAmount;
                    while (true) {
                        try {
                            System.out.print("Enter elevators amount (1-16): ");
                            elevatorAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (elevatorAmount >= 1 && elevatorAmount <= 16) {
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

                    int capacity;
                    while (true) {
                        try {
                            System.out.print("Enter elevator maximum capacity (minimum 1): ");
                            capacity = scanner.nextInt();
                            scanner.nextLine();

                            if (capacity >= 1) {
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

                    int floorsAmount;
                    while (true) {
                        try {
                            System.out.print("Enter amount of floors (minimum 2): ");
                            floorsAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (floorsAmount >= 2) {
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

                    boolean useDraw;
                    while (true) {
                        try {
                            System.out.println("Drawing mode is visually drawn in console, otherwise information is text-based");
                            System.out.print("Use drawing mode? (true/false): ");
                            useDraw = scanner.nextBoolean();
                            scanner.nextLine();

                            break;
                        }
                        catch (InputMismatchException e){
                            System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            //Clear invalid input
                            scanner.nextLine();
                        }
                    }

                    int pauseTime;
                    while (true) {
                        try {
                            System.out.println("Time in between each step (in milliseconds)");
                            System.out.print("Enter pause time (minimum 0): ");
                            pauseTime = scanner.nextInt();
                            scanner.nextLine();

                            if (pauseTime >= 0) {
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
                    System.out.print("\n\n");

                    Simulation simulation = new Simulation(simulationSteps, seed, frequency, frequencyAmount, elevatorAmount, capacity, floorsAmount, useDraw, pauseTime);

                    simulation.run();

                    System.out.println("Average wait time (waiting for elevator + going to destination): " + simulation.calculateAverageWaitTime() + " steps");

                }
                else if (input == 2){
                    System.out.print("\n\n");

                    //Get simulation parameters from user input
                    int elevatorAmount;
                    while (true) {
                        try {
                            System.out.print("Enter elevators amount (1-16): ");
                            elevatorAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (elevatorAmount >= 1 && elevatorAmount <= 16) {
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

                    int capacity;
                    while (true) {
                        try {
                            System.out.print("Enter elevator maximum capacity (minimum 1): ");
                            capacity = scanner.nextInt();
                            scanner.nextLine();

                            if (capacity >= 1) {
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

                    int floorsAmount;
                    while (true) {
                        try {
                            System.out.print("Enter amount of floors (minimum 2): ");
                            floorsAmount = scanner.nextInt();
                            scanner.nextLine();

                            if (floorsAmount >= 2) {
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

                    boolean useDraw;
                    while (true) {
                        try {
                            System.out.println("Drawing mode is visually drawn in console, otherwise information is text-based");
                            System.out.print("Use drawing mode? (true/false): ");
                            useDraw = scanner.nextBoolean();
                            scanner.nextLine();

                            break;
                        }
                        catch (InputMismatchException e){
                            System.out.println(ANSI_RED + "Invalid input, please try again.\n" + ANSI_RESET);
                            //Clear invalid input
                            scanner.nextLine();
                        }
                    }
                    System.out.print("\n\n");

                    ManualSimulation manualSimulation = new ManualSimulation(elevatorAmount, capacity, floorsAmount, useDraw);

                    manualSimulation.run();

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

            System.out.print("\n\n");
        }

        scanner.close();
    }
}
