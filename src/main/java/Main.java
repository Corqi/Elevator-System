import elevator.Simulation;


public class Main {
    public static void main(String[] args) {
//        long seed = System.currentTimeMillis();
        long seed = 10;
        Simulation sim = new Simulation(15, seed,100, 10, 5,
                2, 10, true, 1000);
        sim.run();

        System.out.println("Average wait time: " + sim.calculateAverageWaitTime() + " steps");
    }
}
