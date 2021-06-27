package hmacdectiger;

public class JointObservation {
    Observation agent1;
    Observation agent2;

    public JointObservation(Observation agent1, Observation agent2) {
        this.agent1 = agent1;
        this.agent2 = agent2;
    }

    public JointObservation(int level, int observation1, int observation2) {
       agent1 = new Observation(level, 1, observation1);
       agent2 = new Observation(level, 2, observation2);
    }

    public Observation getAgent1() {
        return agent1;
    }

    public Observation getAgent2() {
        return agent2;
    }

    @Override
    public int hashCode() {
        return agent1.hashCode() * 10 + agent2.hashCode();
    }
}
