package hmacdectiger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MacroObservationFunction {
    private final static MacroObservationFunction instance = new MacroObservationFunction();

    private MacroObservationFunction() {}

    public static MacroObservationFunction getInstance() {
        return instance;
    }

    public Observation determineObservation(History history) {
        if (history.getLevel() == 0) {
            return determineNextObservation1(history);
        } else {
            return determineNextObservation2(history);
        }
    }

    private Observation determineNextObservation1(History history) {
        assert history.getLevel() == 0;

        if (history.end().is(Observation.HEARD_LEFT) || history.end().is(Observation.HEARD_RIGHT)) {
            return new Observation(1, history.getAgent(), history.end().getObservation());
        }

        int location = 2 * history.getAgent();
        int previousObservation = Observation.NORTH;
        for (int observation: movementObservations(history)) {
            if (previousObservation == Observation.EAST && observation == Observation.EAST) {
                location = Math.min(location + 1, 5);
            } else if (previousObservation == Observation.WEST && observation == Observation.WEST) {
                location = Math.max(location - 1, 1);
            }
            previousObservation = observation;
        }

        return new Observation(1, history.getAgent(), location);
    }

    private ArrayList<Integer> movementObservations(History history) {
        ArrayList<Integer> shortHistory = new ArrayList<>();
        history.getIterator().forEachRemaining((observation -> {
            if (observation.is(Observation.HEARD_LEFT) || observation.is(Observation.HEARD_RIGHT)) {
                shortHistory.clear();
            } else {
                shortHistory.add(observation.getObservation());
            }
        }));
        return shortHistory;
    }

    private Observation determineNextObservation2(History history) {
        assert history.getLevel() == 1;
        assert history.end().is(Observation.HEARD_LEFT) || history.end().is(Observation.HEARD_RIGHT);

        return new Observation(1, history.getAgent(), history.end().getObservation());
    }
}
