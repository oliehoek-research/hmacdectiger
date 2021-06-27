package hmacdectiger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Agent {
    private Map<Integer, Map<Integer, Action>> actions;
    private Map<Integer, Map<Integer, Observation>> observations;

    public Agent(Map<Integer, Map<Integer, Action>> actions, Map<Integer, Map<Integer, Observation>> observations) {
        this.actions = actions;
        this.observations = observations;
    }

    public Map<Integer, Action> getActions(int level) {
        return actions.get(level);
    }
    public Map<Integer, Observation> getObservations(int level) {
        return observations.get(level);
    }

    public Action getAction(int level, int action) {
        return actions.get(level).get(action);
    }

    public Observation getObservation(int level, int observation) {
        return observations.get(level).get(observation);
    }

    public void modifyTopPolicy(Policy policy) {
        actions.get(actions.size() - 1).get(Action.TOP).setPolicy(policy);
    }
}
