package hmacdectiger;

import hmacdectiger.ActionStrategies.*;

import java.util.*;

public class AgentFactory {
    public Agent getAgent(int agent) {
        Map<Integer, Map<Integer, Observation>> observations = createObservations(agent);
        return new Agent(createActions(agent, observations), observations);
    }

    private Map<Integer, Map<Integer, Observation>> createObservations(int agent) {
        Map<Integer, Map<Integer, Observation>> observations = new HashMap<>();
        observations.put(0, new LinkedHashMap<>());
        observations.get(0).put(Observation.NORTH, new Observation(0, agent, Observation.NORTH));
        observations.get(0).put(Observation.EAST, new Observation(0, agent, Observation.EAST));
        observations.get(0).put(Observation.SOUTH, new Observation(0, agent, Observation.SOUTH));
        observations.get(0).put(Observation.WEST, new Observation(0, agent, Observation.WEST));
        observations.get(0).put(Observation.HEARD_LEFT, new Observation(0, agent, Observation.HEARD_LEFT));
        observations.get(0).put(Observation.HEARD_RIGHT, new Observation(0, agent, Observation.HEARD_RIGHT));

        observations.put(1, new LinkedHashMap<>());
        for (int location = 1; location <= 5; location++) {
            observations.get(1).put(location, new Observation(1, agent, location));
        }
        observations.get(1).put(Observation.HEARD_LEFT, new Observation(1, agent, Observation.HEARD_LEFT));
        observations.get(1).put(Observation.HEARD_RIGHT, new Observation(1, agent, Observation.HEARD_RIGHT));

        observations.put(2, new LinkedHashMap<>());
        observations.get(2).put(Observation.HEARD_LEFT, new Observation(2, agent, Observation.HEARD_LEFT));
        observations.get(2).put(Observation.HEARD_RIGHT, new Observation(2, agent, Observation.HEARD_RIGHT));

        observations.put(3, new LinkedHashMap<>());
        return observations;
    }

    private Map<Integer, Map<Integer, Action>> createActions(int agent, Map<Integer, Map<Integer, Observation>> observations) {

        ActionFactory factory = new ActionFactory(observations);

        factory.add(0, Action.ROTATE, agent, new TrivialActionStrategy());
        factory.add(0, Action.FORWARD, agent, new TrivialActionStrategy());
        factory.add(0, Action.LISTEN, agent, new TrivialActionStrategy());
        factory.add(0, Action.PULL, agent, new TrivialActionStrategy());

        factory.add(1, Action.MOVE_LEFT, agent, new MoveActionStrategy());
        factory.add(1, Action.MOVE_RIGHT, agent, new MoveActionStrategy());
        factory.add(1, Action.LISTEN, agent, new InteractActionStrategy());
        factory.add(1, Action.PULL, agent, new InteractActionStrategy());

        factory.add(2, Action.OPEN_LEFT, agent, new OpenDoorActionStrategy());
        factory.add(2, Action.OPEN_RIGHT, agent, new OpenDoorActionStrategy());
        factory.add(2, Action.LISTEN, agent, new ListenActionStrategy());

        factory.add(3, Action.TOP, agent, new TrivialActionStrategy());

        return factory.execute();
    }
}
