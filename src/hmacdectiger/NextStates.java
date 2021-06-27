package hmacdectiger;

import java.util.HashSet;
import java.util.Set;

public class NextStates {
    private final static NextStates instance = new NextStates();

    private NextStates() {}

    public static NextStates getInstance() {
        return instance;
    }

    public Set<State> determineNextStates(State origin, Action action1, Action action2) {
        Set<State> destinations = new HashSet<>();
        boolean agent1Pulls = action1.is(Action.PULL) && (origin.getLocationAgent(1) == 1 || origin.getLocationAgent(1) == 5) && origin.getRotationAgent(1) == State.NORTH;
        boolean agent2Pulls = action2.is(Action.PULL) && (origin.getLocationAgent(2) == 1 || origin.getLocationAgent(2) == 5) && origin.getRotationAgent(2) == State.NORTH;
        boolean agent1Listens = action1.is(Action.LISTEN) && (origin.getLocationAgent(1) == 3) && origin.getRotationAgent(1) == State.NORTH;
        boolean agent2Listens = action2.is(Action.LISTEN) && (origin.getLocationAgent(2) == 3) && origin.getRotationAgent(2) == State.NORTH;

        if ((agent1Pulls && agent2Pulls) || (agent1Pulls && agent2Listens) || (agent1Listens && agent2Pulls)) {
            destinations.add(new State(State.TIGER_IS_LEFT, 2, 4, State.NORTH, State.NORTH));
            destinations.add(new State(State.TIGER_IS_RIGHT, 2, 4, State.NORTH, State.NORTH));
        } else if (agent1Listens && agent2Listens) {
            destinations.add(new State(origin.getTigerLocation(), 2, 4, State.NORTH, State.NORTH));
        } else {
            destinations.add(new State(origin.getTigerLocation(),
                    location(origin, action1),
                    location(origin, action2),
                    rotation(origin, action1),
                    rotation(origin, action2)));
        }
        return destinations;
    }

    private int rotation(State origin, Action action) {
        int rotation = origin.getRotationAgent(action.getAgent());
        if (action.is(Action.ROTATE)) {
            return (rotation + 1) % 4;
        } else {
            return rotation;
        }
    }

    private int location(State origin, Action action) {
        int location = origin.getLocationAgent(action.getAgent());
        int rotation = origin.getRotationAgent(action.getAgent());
        if (action.is(Action.FORWARD) && rotation == State.WEST) {
            return Math.max(1, location - 1);
        } else if (action.is(Action.FORWARD) && rotation == State.EAST) {
            return Math.min(5, location + 1);
        } else {
            return location;
        }
    }
}
