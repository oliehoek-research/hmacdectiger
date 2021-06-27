package hmacdectiger;

public class State {

    public final static int TIGER_IS_LEFT = 0;
    public final static int TIGER_IS_RIGHT = 1;

    public final static int NORTH = 0;
    public final static int EAST = 1;
    public final static int SOUTH = 2;
    public final static int WEST = 3;

    private int tigerlocation;
    private int locationAgent1;
    private int locationAgent2;
    private int rotationAgent1;
    private int rotationAgent2;

    private int hashcode;


    public int getTigerLocation() {
        return tigerlocation;
    }

    public int getLocationAgent(int agent) {
        if (agent <= 1) {
            return locationAgent1;
        } else {
            return locationAgent2;
        }
    }

    public int getRotationAgent(int agent) {
        if (agent <= 1) {
            return rotationAgent1;
        } else {
            return rotationAgent2;
        }
    }


    public State(int tigerlocation, int location1, int location2, int rotation1, int rotation2) {
        this.tigerlocation = tigerlocation;
        this.locationAgent1 = location1;
        this.locationAgent2 = location2;
        this.rotationAgent1 = rotation1;
        this.rotationAgent2 = rotation2;

        hashcode = calculateHashCode();
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    private int calculateHashCode() {
        return tigerlocation * 10000 + locationAgent1 * 1000 + locationAgent2 * 100 + rotationAgent1 * 10 + rotationAgent2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State other = (State) obj;
            return this.hashCode() == other.hashCode();
        } else {
            return false;
        }
    }
}
