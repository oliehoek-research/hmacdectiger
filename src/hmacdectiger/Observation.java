package hmacdectiger;

public class Observation {

    public final static int NORTH = 0;
    public final static int EAST = 1;
    public final static int SOUTH = 2;
    public final static int WEST = 3;

    public final static int HEARD_LEFT = 8;
    public final static int HEARD_RIGHT = 9;

    private int level;
    private int agent;
    private int observation;

    public int getLevel() {
        return level;
    }

    public int getAgent() {
        return agent;
    }

    public int getObservation() {
        return observation;
    }

    public Observation(int level, int agent, int observation) {
        this.level = level;
        this.agent = agent;
        this.observation = observation;
    }

    public boolean is(int observation) {
        return this.observation == observation;
    }

    @Override
    public int hashCode() {
        return observation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Observation) {
            Observation other = (Observation) obj;
            return this.hashCode() == other.hashCode();
        } else {
            return false;
        }
    }
}
