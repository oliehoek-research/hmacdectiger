package hmacdectiger;

public class Start {
    public static void main(String[] args) {
        AgentFactory agentFactory = new AgentFactory();
        Agent agent1 = agentFactory.getAgent(1);
        Agent agent2 = agentFactory.getAgent(2);
        HMacDecTiger model = new HMacDecTiger(agent1, agent2);
        BruteForcePlanner planner = new BruteForcePlanner(3, model);
        System.out.println(planner.solve());
    }
}
