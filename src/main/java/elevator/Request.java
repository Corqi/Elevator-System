package elevator;

public class Request {
    public int floor;
    //true is up request, and false is down request
    public boolean direction;

    public Request(int floor, boolean direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Request)) {
            return false;
        }
        Request other = (Request) obj;
        return this.floor == other.floor && this.direction == other.direction;
    }
}
