package hmacdectiger;

public class Action {

    // Ground level
    public static final int ROTATE = 0;
    public static final int FORWARD = 1;
    public static final int LISTEN = 2;
    public static final int PULL = 3;

    // Abstraction level 1
    public static final int MOVE_LEFT = 0;
    public static final int MOVE_RIGHT = 1;
//    public static final int LISTEN = 2;
//    public static final int PULL = 3;

    // Abstraction level 2
    public static final int OPEN_LEFT = 0;
    public static final int OPEN_RIGHT = 1;
//    public static final int LISTEN = 2;

    // Abstraction level 3
    public static final int TOP = 0;

    private int agent;
    private int level;
    private int action;
    private Policy policy;
    private TerminationCondition terminationCondition;

    public int getAgent() {
        return agent;
    }

    public int getLevel() {
        return level;
    }

    public int getAction() {
        return action;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public void setTerminationCondition(TerminationCondition terminationCondition) {
        this.terminationCondition = terminationCondition;
    }

    public TerminationCondition getTerminationCondition() {
        return terminationCondition;
    }

    public Action(int level, int action, int agent) {
        this.level = level;
        this.action = action;
        this.agent = agent;
    }

    public boolean is(int action) {
        return this.getAction() == action;
    }

    @Override
    public int hashCode() {
        return action;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Action) {
            Action other = (Action) obj;
            return this.hashCode() == other.hashCode();
        } else {
            return false;
        }
    }
}
