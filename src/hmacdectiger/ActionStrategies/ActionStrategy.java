package hmacdectiger.ActionStrategies;

import hmacdectiger.Action;
import hmacdectiger.Observation;
import hmacdectiger.Policy;

import java.util.List;
import java.util.Map;

public interface ActionStrategy {
    void execute(Map<Integer, Action> actions, Map<Integer, Observation> observations, Action action);
}
