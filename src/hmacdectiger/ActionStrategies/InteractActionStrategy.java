package hmacdectiger.ActionStrategies;

import hmacdectiger.Action;
import hmacdectiger.Observation;
import hmacdectiger.Policy;
import hmacdectiger.TerminationCondition;

import java.util.Map;

public class InteractActionStrategy implements ActionStrategy {

    @Override
    public void execute(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        Policy policy = getPolicy(actions, observations, action);
        action.setPolicy(policy);
        TerminationCondition terminationCondition = getTerminationCondition(observations, action);
        action.setTerminationCondition(terminationCondition);
    }

    private Policy getPolicy(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        Policy policy = new Policy(action.getLevel(), action.getAgent());
        policy.add(actions.get(Action.ROTATE));
        observations.values().forEach(observation -> {
            if (observation.is(Observation.NORTH)) {
                policy.add(actions.get(action.getAction()), observation);
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
            boolean reset = observation.is(Observation.HEARD_LEFT) || observation.is(Observation.HEARD_RIGHT);
            terminationCondition.add(reset, observation);
        });
        return terminationCondition;
    }
}
