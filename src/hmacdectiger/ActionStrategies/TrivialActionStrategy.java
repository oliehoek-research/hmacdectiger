package hmacdectiger.ActionStrategies;

import hmacdectiger.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrivialActionStrategy implements ActionStrategy {
    @Override
    public void execute(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action) {
        Policy policy = new Policy(action.getLevel(), action.getAgent());
        policy.add(null);
        action.setPolicy(policy);

        TerminationCondition terminationCondition = new TerminationCondition(action.getLevel(), action.getAgent());
        // Always terminate if ground level
        terminationCondition.add(action.getLevel() == 0);
        action.setTerminationCondition(terminationCondition);
    }
}
