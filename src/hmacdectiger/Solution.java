package hmacdectiger;

import java.util.Arrays;

public class Solution {
    private Policy[] topLevelJointPolicy;
    private double reward;

    public Solution(Policy[] topLevelJointPolicy, double reward) {
        this.topLevelJointPolicy = topLevelJointPolicy;
        this.reward = reward;
    }

    public Policy[] getTopLevelJointPolicy() {
        return topLevelJointPolicy;
    }

    public double getReward() {
        return reward;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Reward: " + reward + "\n");
        Arrays.stream(topLevelJointPolicy).forEach(policy -> builder.append(policy.toString()));
        return builder.toString();
    }
}
