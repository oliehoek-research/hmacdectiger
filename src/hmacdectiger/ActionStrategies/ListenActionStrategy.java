package hmacdectiger.ActionStrategies;

import hmacdectiger.Action;
import hmacdectiger.Observation;
import hmacdectiger.Policy;
import hmacdectiger.TerminationCondition;

import java.util.Map;

public class ListenActionStrategy implements ActionStrategy {
    int initialMove;

    @Override
    public void execute(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        if (action.getAgent() <= 1) {
            initialMove = Action.MOVE_RIGHT;
        } else {
            initialMove = Action.MOVE_LEFT;
        }
        Policy policy = getPolicy(actions, observations, action);
        action.setPolicy(policy);
        TerminationCondition terminationCondition = getTerminationCondition(observations, action);
        action.setTerminationCondition(terminationCondition);
    }

    private Policy getPolicy(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        Policy policy = new Policy(action.getLevel(), action.getAgent());
        policy.add(actions.get(initialMove));
        observations.values().forEach(observation -> {
            if (observation.getObservation() < 3) {
                policy.add(actions.get(Action.MOVE_RIGHT), observation);
            } else if (observation.is(3)){
                policy.add(actions.get(Action.LISTEN), observation);
            } else if (observation.getObservation() <= 5) {
                policy.add(actions.get(Action.MOVE_LEFT), observation);
            } else {
                policy.add(actions.get(initialMove), observation);
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
