package hmacdectiger;

import java.util.*;
import java.util.stream.IntStream;

public class HMacDecTiger {

    public final static int AGENT_AMOUNT = 2;
    public final static int ABSTRACTION_LEVEL_AMOUNT = 2;
    private Agent agent1;
    private Agent agent2;

    private ArrayList<State> initialStates;

    public HMacDecTiger(Agent agent1, Agent agent2) {
        populateInitialStates();
        this.agent1 = agent1;
        this.agent2 = agent2;
    }

    public Agent getAgent(int agent) {
        if (agent == 1) {
            return agent1;
        } else {
            return agent2;
        }
    }

    private void populateInitialStates() {
        initialStates = new ArrayList<>();
        initialStates.add(new State(State.TIGER_IS_LEFT, 2, 4, State.NORTH, State.NORTH));
        initialStates.add(new State(State.TIGER_IS_RIGHT, 2, 4, State.NORTH, State.NORTH));
    }

    public double evaluate(int maxTopLevelSteps) {
        History[] initialHistory1 = new History[ABSTRACTION_LEVEL_AMOUNT + 2];
        History[] initialHistory2 = new History[ABSTRACTION_LEVEL_AMOUNT + 2];
        IntStream.range(0, ABSTRACTION_LEVEL_AMOUNT + 2).forEach(level -> {
            initialHistory1[level] = new History(level, 1);
            initialHistory2[level] = new History(level, 2);
        });

        return initialStates.stream().mapToDouble(state -> V(state, initialHistory1, initialHistory2, maxTopLevelSteps) / initialHistory1.length).sum();
    }

    private double V(State state, History[] historyStack1, History[] historyStack2, int maxTopLevelSteps) {
        Action[] actions1 = determineActions(historyStack1, agent1);
        Action[] actions2 = determineActions(historyStack2, agent2);
        double reward = Reward.getInstance().get(state, actions1[0], actions2[0]);

        Set<State> nextStates = NextStates.getInstance().determineNextStates(state, actions1[0], actions2[0]);
        for (State nextState: nextStates) {
            double stateProbability = 1.0 / nextStates.size();
            Map<JointObservation, Double> nextGroundObservations = ObservationProbabilityFunction.getInstance().determineNextObservations(nextState, actions1[0], actions2[0]);
            for (Map.Entry<JointObservation, Double> observationEntry : nextGroundObservations.entrySet()) {
                History[] newHistory1 = determineHistoryStack(historyStack1, actions1, observationEntry.getKey().getAgent1());
                History[] newHistory2 = determineHistoryStack(historyStack2, actions2, observationEntry.getKey().getAgent2());
                if (newHistory1[ABSTRACTION_LEVEL_AMOUNT].size() < maxTopLevelSteps
                        || newHistory2[ABSTRACTION_LEVEL_AMOUNT].size() < maxTopLevelSteps) {
                    reward += V(nextState, newHistory1, newHistory2, maxTopLevelSteps) * observationEntry.getValue() * stateProbability;
                }
            }
        }
        return reward;
    }

    private Action[] determineActions(History[] historyStack, Agent agent) {
        Action[] actions = new Action[ABSTRACTION_LEVEL_AMOUNT + 2];

        actions[ABSTRACTION_LEVEL_AMOUNT + 1] = agent.getAction(ABSTRACTION_LEVEL_AMOUNT + 1, Action.TOP);
        for (int level = ABSTRACTION_LEVEL_AMOUNT; level >= 0; level--) {
            actions[level] = actions[level + 1].getPolicy().getAction(historyStack[level]);
        }
        return actions;
    }

    private History[] determineHistoryStack(History[] historyStack, Action[] actionStack, Observation nextObservation) {
        History[] newHistoryStack = new History[historyStack.length];
        for (int i = 0; i < historyStack.length; i++) {
            newHistoryStack[i] = historyStack[i].clone();
        }
        newHistoryStack[0].append(nextObservation);

        // Append the histories of the abstraction levels
        for (int level = 1; level <= ABSTRACTION_LEVEL_AMOUNT; level++) {
            if (actionStack[level].getTerminationCondition().getTermination(newHistoryStack[level - 1])) {
                Observation newObservation = MacroObservationFunction.getInstance().determineObservation(newHistoryStack[level - 1]);
                newHistoryStack[level].append(newObservation);
            } else {
                break;
            }
        }
        return newHistoryStack;
    }

    public void updateTopPolicies(Policy policy1, Policy policy2) {
        agent1.modifyTopPolicy(policy1);
        agent2.modifyTopPolicy(policy2);
    }
}
