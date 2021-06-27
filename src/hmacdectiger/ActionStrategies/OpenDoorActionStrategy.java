package hmacdectiger.ActionStrategies;

import hmacdectiger.Action;
import hmacdectiger.Observation;
import hmacdectiger.Policy;
import hmacdectiger.TerminationCondition;

import java.util.Map;

public class OpenDoorActionStrategy implements ActionStrategy {
    private int doorLocation;
    private int moveDirection;

    @Override
    public void execute(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        if (action.is(Action.OPEN_LEFT)) {
            doorLocation = 1;
            moveDirection = Action.MOVE_LEFT;
        } else { // Open Right
            doorLocation = 5;
            moveDirection = Action.MOVE_RIGHT;
        }
        Policy policy = getPolicy(actions, observations, action);
        action.setPolicy(policy);
        TerminationCondition terminationCondition = getTerminationCondition(observations, action);
        action.setTerminationCondition(terminationCondition);

    }

    private Policy getPolicy(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        Policy policy = new Policy(action.getLevel(), action.getAgent());
        policy.add(actions.get(moveDirection));
        observations.values().forEach(observation -> {
            if (observation.is(doorLocation)) {
                policy.add(actions.get(Action.PULL), observation);
            } else {
                policy.add(actions.get(moveDirection), observation);
            }
        });
        return policy;
    }

    private TerminationCondition getTerminationCondition(Map<Integer, Observation> observations, Action action) {
        TerminationCondition terminationCondition = new TerminationCondition(action.getLevel(), action.getAgent());
        terminationCondition.add(false);

        observations.values().forEach(observation -> {
            boolean reset = observation.is(Observation.HEARD_LEFT) || observation.is(Observation.HEARD_RIGHT);
            terminationCondition.add(reset, observation);
        });
        return terminationCondition;
    }
}
