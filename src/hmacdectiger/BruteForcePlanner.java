package hmacdectiger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

public class BruteForcePlanner{
    private int maxPolicies;
    private int topLevelHorizon;
    private HMacDecTiger model;

    public BruteForcePlanner(int topLevelHorizon, HMacDecTiger model) {
        this.model = model;
        this.topLevelHorizon = topLevelHorizon;
        int A = model.getAgent(1).getActions(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT).size();
        int O = model.getAgent(1).getObservations(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT).size();
        int n = HMacDecTiger.AGENT_AMOUNT;
        int h = topLevelHorizon;
        maxPolicies = (int) Math.pow(A,((n * (Math.pow(O,h) - 1))/(O - 1)));
    }

    public Solution solve() {
        double highestReward = -10000000;
        Policy[] bestPolicies = null;
        for (int i = 0; i < maxPolicies; i++) {
            Policy[] policies = getPolicies(i);
            model.updateTopPolicies(policies[0], policies[1]);
            double reward = model.evaluate(topLevelHorizon);
            if (reward > highestReward) {
                highestReward = reward;
                bestPolicies = policies;
            }
            showProgress(i);
        }
        System.out.println();
//////        Policy[] policies = getPolicies(4601364); // optimal
////        Policy[] policies = getPolicies(4782968); // always listen
//////        policies = getPolicies(3233439);
//        model.updateTopPolicies(policies[0], policies[1]);
//        double reward = model.evaluate(topLevelHorizon);
//        if (reward > highestReward) {
//            highestReward = reward;
//            bestPolicies = policies;
//        }
        return new Solution(bestPolicies, highestReward);
    }

    private void showProgress(int i) {
        if (i % 10000 == 0) {
            System.out.printf("\rProgress: %d/%d\t~%.2f%%", i, maxPolicies, (( (double) i / (double) maxPolicies) * 100));
        }
    }

    private Policy[] getPolicies(int i) {
        Iterator<Integer> actionIterator = signatureToActionIterator(i);
        Policy[] newPolicies = new Policy[2];
        newPolicies[0] = new Policy(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT + 1, 1);
        newPolicies[1] = new Policy(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT + 1, 2);

        for (int agent = 1; agent <= HMacDecTiger.AGENT_AMOUNT; agent++) {
            newPolicies[agent - 1].add(model.getAgent(agent).getAction(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT, actionIterator.next()));
            for (int o1 = Observation.HEARD_LEFT; o1 <= Observation.HEARD_RIGHT; o1++) {
                Observation observation1 = model.getAgent(agent).getObservation(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT, o1);
                newPolicies[agent - 1].add(model.getAgent(agent).getAction(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT, actionIterator.next()), observation1);
                for (int o2 = Observation.HEARD_LEFT; o2 <= Observation.HEARD_RIGHT; o2++) {
                    Observation observation2 = model.getAgent(agent).getObservation(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT, o2);
                    newPolicies[agent - 1].add(model.getAgent(agent).getAction(HMacDecTiger.ABSTRACTION_LEVEL_AMOUNT, actionIterator.next()), observation1, observation2);
                }
            }
        }
        return newPolicies;
    }

    private Iterator<Integer> signatureToActionIterator(int i) {
        String actions = Integer.toString(i, 3);
        if (actions.length() < 14) {
            char[] zeros = new char[14 - actions.length()];
            Arrays.fill(zeros, '0');
            actions = String.valueOf(zeros) + actions;
        }
        return actions.chars().map(value -> value - '0').iterator();
    }


}
