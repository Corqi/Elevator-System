package elevator;
import java.util.ArrayList;

public interface ElevatorSystem {
    void pickup(int floor, int direction);

    void update(int id, int currFloor, int destFloor);

    void step();

    ArrayList<Elevator> status();
}
