package my.simple.protocol.graph;

public class Pair<T1, T2> {

    private T1 first;
    private T2 Second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.Second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return Second;
    }
}
