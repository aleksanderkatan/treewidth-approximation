package treewidth_approximation.logic.graph;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TAEdge {
    private final int u;
    private final int v;

    public TAEdge(int u, int v) {
        this.u = min(u, v);
        this.v = max(u, v);
    }

    public int getFirst() { return u; }
    public int getSecond() { return v; }

    @Override
    public int hashCode() {
        return Objects.hash(u, v);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof TAEdge))
            return false;

        TAEdge c = (TAEdge) obj;

        return (getFirst() == c.getFirst()) && (getSecond() == c.getSecond());
    }
}
