package hmacdectiger;

import java.util.ArrayList;
import java.util.Iterator;

public class StateIterator implements Iterable<State> {

    @Override
    public Iterator<State> iterator() {
        ArrayList<State> states = new ArrayList<>();
        for (int tigerlocation = 0; tigerlocation < 2; tigerlocation++) {
            for (int locationAgent0 = 0; locationAgent0 < 5; locationAgent0++) {
                for (int locationAgent1 = 0; locationAgent1 < 5; locationAgent1++) {
                    for (int rotation0 = 0; rotation0 < 4; rotation0++) {
                        for (int rotation1 = 0; rotation1 < 4; rotation1++) {
                            states.add(new State(tigerlocation, locationAgent0, locationAgent1, rotation0, rotation1));
                        }
                    }
                }
            }
        }

        return new Iterator<State>() {

            private ArrayList<State> arrayList = states;
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < states.size() && arrayList.get(currentIndex) != null;
            }

            @Override
            public State next() {
                return arrayList.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
