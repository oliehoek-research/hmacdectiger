package hmacdectiger;

public class Reward {
    private final static Reward instance = new Reward();

    private Reward() {}

    public static Reward getInstance() {
        return instance;
    }

    public int get(State state, Action action1, Action action2) {
        boolean tigerLeft = state.getTigerLocation() == State.TIGER_IS_LEFT;
        boolean tigerRight = state.getTigerLocation() == State.TIGER_IS_RIGHT;
        boolean listens1 = state.getLocationAgent(1) == 3 && action1.getAction() == Action.LISTEN && state.getRotationAgent(1) == State.NORTH;
        boolean listens2 = state.getLocationAgent(2) == 3 && action2.getAction() == Action.LISTEN && state.getRotationAgent(2) == State.NORTH;
        boolean openLeft1 = state.getLocationAgent(1) == 1 && action1.getAction() == Action.PULL && state.getRotationAgent(1) == State.NORTH;
        boolean openLeft2 = state.getLocationAgent(2) == 1 && action2.getAction() == Action.PULL && state.getRotationAgent(2) == State.NORTH;
        boolean openRight1 = state.getLocationAgent(1) == 5 && action1.getAction() == Action.PULL && state.getRotationAgent(1) == State.NORTH;
        boolean openRight2 = state.getLocationAgent(2) == 5 && action2.getAction() == Action.PULL && state.getRotationAgent(2) == State.NORTH;

        if ((tigerLeft && openLeft1 && listens2) ||
                (tigerRight && openRight1 && listens2) ||
                (tigerLeft && listens1 && openLeft2) ||
                (tigerRight && listens1 && openRight2)) {
            return -101;
        }

        if ((openLeft1 && openRight2) || (openRight1 && openLeft2)) {
            return -100;
        }

        if ((tigerLeft && openLeft1 && openLeft2) ||
                (tigerRight && openRight1 && openRight2)) {
            return -50;
        }

        if (listens1 && listens2) {
            return -2;
        }

        if ((tigerRight && openLeft1 && listens2) ||
                (tigerLeft && openRight1 && listens2) ||
                (tigerRight && listens1 && openLeft2) ||
                (tigerLeft && listens1 && openRight2)) {
            return 9;
        }

        if ((tigerLeft && openRight1 && openRight2) || (tigerRight && openLeft1 && openLeft2)) {
            return 20;
        }

        return 0;
    }
}
