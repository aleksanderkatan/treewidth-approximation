package treewidth_approximation.logic.steiner;

import org.javatuples.Pair;
import treewidth_approximation.logic.graph.TAGraph;

import java.util.Map;
import java.util.Set;

public class SteinerInstance {
    private final TAGraph graph;
    private final Set<Integer> terminals;
    private final Map<Pair<Integer, Integer>, Integer> weights;

    public SteinerInstance(TAGraph graph, Set<Integer> terminals, Map<Pair<Integer, Integer>, Integer> weights) {
        this.graph = graph;
        this.terminals = terminals;
        this.weights = weights;
    }

    public TAGraph getGraph() { return graph; }

    public Set<Integer> getTerminals() { return terminals; }
    public boolean isTerminal(int v) { return terminals.contains(v); }

    public Integer getEdgeWeight(int u, int v) {
        Integer result;
        result = weights.get(new Pair<>(u, v));
        if (result != null)
            return result;
        result = weights.get(new Pair<>(v, u));
        if (result != null)
            return result;
        return 1;
    }
}
