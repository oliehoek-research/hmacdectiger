package hmacdectiger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class History {
    private int level;
    private int agent;
    private List<Observation> observationHistory;

    public History(int level, int agent) {
        this(level, agent, new ArrayList<>());
    }

    public History(int level, int agent, List<Observation> observationHistory) {
        this.level = level;
        this.agent = agent;
        this.observationHistory = observationHistory;
    }

    public Observation get(int timestep) {
        return observationHistory.get(timestep - 1);
    }

    public int getAgent() {
        return agent;
    }

    public Observation end() {
        return observationHistory.get(observationHistory.size() - 1);
    }

    public int getLevel() {
        return level;
    }

    public int size() {
        return observationHistory.size();
    }

    public History append(Observation observation) {
        observationHistory.add(observation);
        return this;
    }

    public History appendAndClone(Observation observation) {
        return clone().append(observation);
    }

    public History clone() {
        List<Observation> newObservationHistory = new ArrayList<>();
        newObservationHistory.addAll(observationHistory);
        return new History(level, agent, newObservationHistory);
    }

    public History last(int last) {
        if (observationHistory.size() <= last) {
            return this;
        } else {
            return new History(level, agent, observationHistory.subList(observationHistory.size() - last, observationHistory.size()));
        }
    }

    public Iterator<Observation> getIterator() {
        return observationHistory.iterator();
    }

    @Override
    public int hashCode() {
        int multiplier = 10;
        int hashcode = 0;

        for (Observation observation: observationHistory) {
            hashcode += observation.getObservation() * multiplier;
            multiplier *= 10;
        }

        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof History && ((History) obj).hashCode() == hashCode();
    }
}
