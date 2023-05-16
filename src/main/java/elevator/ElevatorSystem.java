package elevator;
import java.util.ArrayList;

public interface ElevatorSystem {
    void pickup(int floor, boolean direction);

    void chooseFloor(int id, int destination);

    void step();

    ArrayList<Elevator> status();
}
