import elevator.Simulation;


public class Main {
    public static void main(String[] args) {
        Simulation sim = new Simulation(100, 10, 10, 5,
                2, 10, true, 1000);
        sim.run();
    }
}
