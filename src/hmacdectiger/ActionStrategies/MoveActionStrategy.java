package hmacdectiger.ActionStrategies;

import hmacdectiger.*;

import java.util.Map;

public class MoveActionStrategy implements ActionStrategy {
    private int moveDirection;

    @Override
    public void execute(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        if (action.is(Action.MOVE_LEFT)) {
            this.moveDirection = Observation.WEST;
        } else { // Move right
            this.moveDirection = Observation.EAST;
        }

        Policy policy = getPolicy(actions, observations, action);
        action.setPolicy(policy);
        TerminationCondition terminationCondition = getTerminationCondition(observations, action);
        action.setTerminationCondition(terminationCondition);

    }

    private Policy getPolicy(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        Policy policy = new Policy(action.getLevel(), action.getAgent());
        policy.add(actions.get(Action.ROTATE));
        observations.values().forEach(observation -> {
            if (observation.is(moveDirection)) {
                policy.add(actions.get(Action.FORWARD), observation);
            } else {
                policy.add(actions.get(Action.ROTATE), observation);
            }
        });
        return policy;
    }

    private TerminationCondition getTerminationCondition(Map<Integer, Observation> observations, Action action) {
        TerminationCondition terminationCondition = new TerminationCondition(action.getLevel(), action.getAgent());
        terminationCondition.add(false);

        observations.values().forEach(observation -> {
            terminationCondition.add(false, observation);
            observations.values().forEach(observation2 -> {
                boolean agentMoved = observation.is(moveDirection) && observation2.is(moveDirection);
                terminationCondition.add(agentMoved, observation, observation2);
            });
        });
        return terminationCondition;
    }
}
