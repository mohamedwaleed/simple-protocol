package my.simple.protocol.graph;

public class Pair<T1 extends Comparable, T2 extends Comparable> implements Comparable<Pair<T1, T2>>{

    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        return this.first.equals(((Pair)obj).getFirst());
    }

    @Override
    public int compareTo(Pair<T1, T2> pair) {
        if(this == pair) return 0;

        if(this.first.compareTo(pair.getFirst()) > 0) return 1;
        if(this.first.compareTo(pair.getFirst()) < 0) return -1;

        if(this.first.compareTo(pair.getFirst()) == 0) {
            if(this.second.compareTo(pair.getSecond()) > 0) return 1;
            if(this.second.compareTo(pair.getSecond()) < 0) return -1;
        }
        return 0;
    }
}

