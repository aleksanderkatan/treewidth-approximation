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
    private final Map<TAEdge, Double> weights;
    private final Set<TAEdge> selected;

    public SteinerInstance(TAGraph graph, Set<Integer> terminals, Map<TAEdge, Double> weights) {
        this.graph = graph;
        this.terminals = terminals;
        this.weights = weights;
        this.selected = new HashSet<>();
    }

    public TAGraph getGraph() { return graph; }

    public Set<Integer> getTerminals() { return terminals; }
    public boolean isTerminal(int v) { return terminals.contains(v); }

    public Double getEdgeWeight(TAEdge edge) {
        int u = edge.getFirst();
        int v = edge.getSecond();
        if (!graph.hasEdge(u, v)) {
            return 0.0;
        }
        Double result;
        result = weights.get(new TAEdge(u, v));
        if (result != null)
            return result;
        return 1.0;
    }

    public void setSelected(Set<TAEdge> selected) {
        this.selected.clear();
        this.selected.addAll(selected);
    }

    public Set<TAEdge> getSelected() { return selected; }
}
