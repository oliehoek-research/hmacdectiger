package hmacdectiger;

import hmacdectiger.ActionStrategies.ActionStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActionFactory {
    Map<Integer, Map<Integer, Observation>> observations;
    Map<Integer, Map<Integer, Action>> actions;
    Map<Integer, Map<Action, ActionStrategy>> actionStrategies;

    public ActionFactory(Map<Integer, Map<Integer, Observation>> observations) {
        this.observations = observations;
        actions = new HashMap<>();
        actions.put(0, new LinkedHashMap<>());
        actions.put(1, new LinkedHashMap<>());
        actions.put(2, new LinkedHashMap<>());
        actions.put(3, new LinkedHashMap<>());
        actionStrategies = new HashMap<>();
        actionStrategies.put(0, new LinkedHashMap<>());
        actionStrategies.put(1, new LinkedHashMap<>());
        actionStrategies.put(2, new LinkedHashMap<>());
        actionStrategies.put(3, new LinkedHashMap<>());
    }

    public void add(int level, int action, int agent, ActionStrategy strategy) {
        Action actionObject = new Action(level, action, agent);
        actions.get(level).put(action, actionObject);
        actionStrategies.get(level).put(actionObject, strategy);
    }

    public Map<Integer, Map<Integer, Action>> execute() {
        actionStrategies.values().forEach(actionStrategiesMap -> actionStrategiesMap.forEach((action, strategy) -> {
            strategy.execute(actions.get(action.getLevel() - 1), observations.get(action.getLevel() - 1), action);
        }));
        return actions;
    }
}
