import elevator.Elevator;
import elevator.SimpleElevatorSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class SimpleElevatorSystemTest {
    private SimpleElevatorSystem elevatorSystem;

    @BeforeEach
    public void setUp(){
        this.elevatorSystem = new SimpleElevatorSystem(3, 5, 10);
    }

    @Test
    public void testInvalidFloorAmount(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleElevatorSystem(3, 5, 1));
    }

    @Test
    public void testInvalidElevatorAmount(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleElevatorSystem(0, 5, 10));
    }

    @Test
    public void testInvalidCapacity(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SimpleElevatorSystem(3, -1, 10));
    }

    @Test
    public void testStatus(){
        ArrayList<Elevator> status = this.elevatorSystem.status();
        Assertions.assertEquals(3, status.size());

        Assertions.assertTrue(status.get(0).isFree());
        Assertions.assertEquals(0, status.get(0).getId());
        Assertions.assertEquals(0, status.get(0).getCurrFloor());
        Assertions.assertEquals(5, status.get(0).getCapacity());

        Assertions.assertTrue(status.get(1).isFree());
        Assertions.assertEquals(1, status.get(1).getId());
        Assertions.assertEquals(0, status.get(1).getCurrFloor());
        Assertions.assertEquals(5, status.get(1).getCapacity());

        Assertions.assertTrue(status.get(2).isFree());
        Assertions.assertEquals(2, status.get(2).getId());
        Assertions.assertEquals(0, status.get(2).getCurrFloor());
        Assertions.assertEquals(5, status.get(2).getCapacity());
    }
}
