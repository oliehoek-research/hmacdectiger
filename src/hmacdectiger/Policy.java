package hmacdectiger;

import java.util.*;

public class Policy {
    private Map<History, Action> policy;
    private int maxHorizon;
    private int level;
    private int agent;

    public Policy(int level, int agent) {
        this.level = level;
        this.agent = agent;
        policy = new HashMap<>();
    }

    public int getHorizon() {
        return maxHorizon;
    }

    public void add(History history, Action action) {
        maxHorizon = Math.max(maxHorizon, history.size());
        policy.put(history, action);
    }

    public void add(Action action, Observation... observations) {
        History history = new History(level - 1, agent, Arrays.asList(observations));
        add(history, action);
    }

    public Action getAction(History history) {
        History check = history.last(maxHorizon);
        return policy.get(history.last(maxHorizon));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        policy.forEach((key, value) -> {
            builder.append(key.hashCode()).setLength(builder.length() - 1);
            builder.append("-").append(value.getAction()).append("\n");
        });
        return builder.toString();
    }
}
