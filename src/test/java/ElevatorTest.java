import elevator.Elevator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class ElevatorTest {
    private Elevator elevator;

    @BeforeEach
    public void setUp(){
        this.elevator = new Elevator(0, 5, 0);
    }

    @Test
    public void testAddDestination(){
        this.elevator.addDestination(5);
        ArrayList<Integer> expectedDestinations = new ArrayList<>();
        expectedDestinations.add(5);
        Assertions.assertEquals(expectedDestinations, this.elevator.getDestFloors());
    }

    @Test
    public void testUnloadPassengers(){
        this.elevator.addDestination(5);
        this.elevator.addDestination(0);
        this.elevator.unloadPassengers();
        ArrayList<Integer> expectedDestinations = new ArrayList<>();
        expectedDestinations.add(5);
        Assertions.assertEquals(expectedDestinations, this.elevator.getDestFloors());
        Assertions.assertEquals(1, this.elevator.getCurrCapacity());
    }

    @Test
    public void testMove(){
        this.elevator.addDestination(5);
        this.elevator.move();
        Assertions.assertEquals(1, this.elevator.getCurrFloor());
        this.elevator.move();
        this.elevator.move();
        Assertions.assertEquals(3, this.elevator.getCurrFloor());
    }

    @Test
    public void testInvalidCapacity(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Elevator(0, 0, 0));
    }

    @Test
    public void testInvalidCurrCapacity(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                this.elevator.setCurrCapacity(6));
    }
}
