package hmacdectiger;

import java.util.HashMap;
import java.util.Map;

public class ObservationProbabilityFunction {
    private final static ObservationProbabilityFunction instance = new ObservationProbabilityFunction();

    private ObservationProbabilityFunction() {}

    public static ObservationProbabilityFunction getInstance() {
        return instance;
    }

    public Map<JointObservation, Double> determineNextObservations(State destination, Action action1, Action action2) {
        Map<JointObservation, Double> output = new HashMap<>();
        if (determineEnvironmentReset(destination, action1, action2)) {
            if (action1.is(Action.LISTEN) && action2.is(Action.LISTEN)) {
                output.put(new JointObservation(0, Observation.HEARD_LEFT, Observation.HEARD_RIGHT), 0.85 * 0.15);
                output.put(new JointObservation(0, Observation.HEARD_RIGHT, Observation.HEARD_LEFT), 0.85 * 0.15);
                if (destination.getTigerLocation() == State.TIGER_IS_LEFT) {
                    output.put(new JointObservation(0, Observation.HEARD_LEFT, Observation.HEARD_LEFT), 0.85*0.85);
                    output.put(new JointObservation(0, Observation.HEARD_RIGHT, Observation.HEARD_RIGHT), 0.15*0.15);
                } else { // Tiger is right
                    output.put(new JointObservation(0, Observation.HEARD_LEFT, Observation.HEARD_LEFT), 0.15*0.15);
                    output.put(new JointObservation(0, Observation.HEARD_RIGHT, Observation.HEARD_RIGHT), 0.85*0.85);
                }
            } else {
                output.put(new JointObservation(0, Observation.HEARD_LEFT, Observation.HEARD_LEFT), 0.25);
                output.put(new JointObservation(0, Observation.HEARD_LEFT, Observation.HEARD_RIGHT), 0.25);
                output.put(new JointObservation(0, Observation.HEARD_RIGHT, Observation.HEARD_LEFT), 0.25);
                output.put(new JointObservation(0, Observation.HEARD_RIGHT, Observation.HEARD_RIGHT), 0.25);
            }
        } else {
            output.put(new JointObservation(0, destination.getRotationAgent(1), destination.getRotationAgent(2)), 1.0);
        }
        return output;
    }

    private boolean determineEnvironmentReset(State destination, Action action1, Action action2) {
        return destination.getRotationAgent(1) == State.NORTH && destination.getRotationAgent(2) == State.NORTH &&
                destination.getLocationAgent(1) == 2 && destination.getLocationAgent(2) == 4 &&
                (action1.is(Action.LISTEN) || action1.is(Action.PULL)) &&
                (action2.is(Action.LISTEN) || action2.is(Action.PULL));
    }
}
