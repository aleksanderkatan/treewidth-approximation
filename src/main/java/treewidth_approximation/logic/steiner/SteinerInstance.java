package treewidth_approximation.logic.steiner;

import org.javatuples.Pair;
import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SteinerInstance {
    private final TAGraph graph;
    private final Set<Integer> terminals;
    private final Map<TAEdge, Integer> weights;
    private final Set<TAEdge> selected;

    public SteinerInstance(TAGraph graph, Set<Integer> terminals, Map<TAEdge, Integer> weights) {
        this.graph = graph;
        this.terminals = terminals;
        this.weights = weights;
        this.selected = new HashSet<>();
    }

    public TAGraph getGraph() { return graph; }

    public Set<Integer> getTerminals() { return terminals; }
    public boolean isTerminal(int v) { return terminals.contains(v); }

    public Integer getEdgeWeight(int u, int v) {
        Integer result;
        if (!graph.hasEdge(u, v)) {
            return 0;
        }
        result = weights.get(new TAEdge(u, v));
        if (result != null)
            return result;
        return 1;
    }

    public void setSelected(Set<TAEdge> selected) {
        this.selected.clear();
        this.selected.addAll(selected);
    }

    public Set<TAEdge> getSelected() { return selected; }
}
