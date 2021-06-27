package hmacdectiger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TerminationCondition {
    private Map<History, Boolean> termination;
    private int maxHorizon;
    private int level;
    private int agent;

    public TerminationCondition(int level, int agent) {
        termination = new HashMap<>();
        maxHorizon = 0;
        this.agent = agent;
        this.level = level;
    }

    public int getHorizon() {
        return maxHorizon;
    }

    public void add(History history, boolean terminates) {
        assert history.getLevel() == level;
        maxHorizon = Math.max(maxHorizon, history.size());
        termination.put(history, terminates);
    }


    public void add(boolean terminates, Observation... observations) {
        History history = new History(level - 1, agent, Arrays.asList(observations));
        add(history, terminates);
    }

    public boolean getTermination(History history) {
        return termination.get(history.last(maxHorizon));
    }
}
