package treewidth_approximation.logic.prefuse;

import prefuse.data.Graph;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.HashSet;

public class SteinerInstanceConverter {
    public static Graph convert(SteinerInstance instance) {
        return GraphConverter.convert(instance.getGraph(), instance.getTerminals(), new HashSet<>(), instance.getSelected());
    }
}
