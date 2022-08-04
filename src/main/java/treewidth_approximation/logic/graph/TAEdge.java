package treewidth_approximation.logic.graph;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TAEdge implements Comparable<TAEdge> {
    private final int u;
    private final int v;

    public TAEdge(int u, int v) {
        this.u = min(u, v);
        this.v = max(u, v);
    }

    public int getFirst() { return u; }
    public int getSecond() { return v; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TAEdge edge = (TAEdge) o;
        return u == edge.u && v == edge.v;
    }

    @Override
    public int compareTo(TAEdge o) {
        if (getFirst() != o.getFirst()) {
            return getFirst() - o.getFirst();
        }
        return getSecond() - o.getSecond();
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v);
    }
}
