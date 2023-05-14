import elevator.Elevator;

public class Main {
    public static void main(String[] args) {
        Elevator el = new Elevator(0,5,0);
        System.out.println(el.destFloor);
        el.destFloor.add(1);
        el.destFloor.remove(0);
        System.out.println(el.destFloor);
    }
}
