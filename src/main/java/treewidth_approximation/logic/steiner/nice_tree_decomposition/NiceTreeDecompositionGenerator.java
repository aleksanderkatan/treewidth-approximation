package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import org.javatuples.Pair;
import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.*;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;
import treewidth_approximation.logic.tree_decomposition.DecompositionNodeImpl;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionImpl;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NiceTreeDecompositionGenerator {

    public static NiceTreeDecomposition generate(TreeDecomposition decomposition, SteinerInstance steiner) {
        TreeDecomposition copy = decomposition.copy();

        // make it so there is a right amount of nodes
        collapse(copy.getRoot());

        // make it so no node has more than 2 children and joins are correct
        addJoins(copy.getRoot());

        // make it so every leaf and root are empty
        DecompositionNode newRoot = new DecompositionNodeImpl(new HashSet<>());
        newRoot.addChild(copy.getRoot());
        copy = new TreeDecompositionImpl(newRoot);
        addLeaves(copy.getRoot());

        // make it so every node differs by at most one vertex from its parent
        addIntroducesAndForgets(copy.getRoot());

        // generate nice tree decomposition
        NiceDecompositionNode niceRoot = generateNice(copy.getRoot(), steiner);

        // terminal to be added to every decomposition node
        int U = steiner.getTerminals().iterator().next();
        addTerminal(niceRoot, U);

        // fill the inducedSubgraph field in every node
        fillInducedSubgraph(niceRoot);

        return new NiceTreeDecomposition(niceRoot);
    }

    private static void collapse(DecompositionNode node) {
        for (DecompositionNode child : new ArrayList<>(node.getChildren())) {
            collapse(child);

            Set<Integer> nodeBag = node.getVertices();
            Set<Integer> childBag = child.getVertices();

            if (nodeBag.containsAll(childBag) || childBag.containsAll(nodeBag)) {
                node.getVertices().addAll(childBag);
                node.removeChild(child);
                for (DecompositionNode grandChild : child.getChildren()) {
                    node.addChild(grandChild);
                }
            }
        }
    }

    private static void addJoins(DecompositionNode node) {
        List<DecompositionNode> children = new ArrayList<>(node.getChildren());
        for (DecompositionNode child : children) {
            addJoins(child);
        }

        if (children.size() >= 2) {
            DecompositionNode current = node;
            for (DecompositionNode child : children) {
                node.removeChild(child);
            }

            for (int i = 0; i < children.size() - 1; i++) {
                DecompositionNode left = new DecompositionNodeImpl(node.getVertices());
                DecompositionNode right = new DecompositionNodeImpl(node.getVertices());
                left.addChild(children.get(i));
                current.addChild(left);
                current.addChild(right);
                current = right;
            }
            current.addChild(children.get(children.size() - 1));
        }
    }

    private static void addLeaves(DecompositionNode node) {
        if (node.getChildren().size() == 0) {
            node.addChild(new DecompositionNodeImpl(new HashSet<>()));
        } else {
            for (DecompositionNode child : node.getChildren()) {
                addLeaves(child);
            }
        }
    }

    private static void addIntroducesAndForgets(DecompositionNode node) {
        if (node.getChildren().size() > 1) {
            // join
            for (DecompositionNode child : node.getChildren()) {
                addIntroducesAndForgets(child);
            }
        }
        if (node.getChildren().size() == 1) {
            DecompositionNode child = node.getChildren().iterator().next();
            addIntroducesAndForgets(child);
            node.removeChild(child);
            Set<Integer> toIntroduce = new HashSet<>(node.getVertices());
            toIntroduce.removeAll(child.getVertices());
            Set<Integer> toForget = new HashSet<>(child.getVertices());
            toForget.removeAll(node.getVertices());

            for (Integer v : toIntroduce) {
                Set<Integer> newBag = new HashSet<>(node.getVertices());
                newBag.remove(v);
                DecompositionNode newNode = new DecompositionNodeImpl(newBag);
                node.addChild(newNode);
                node = newNode;
            }

            for (Integer v : toForget) {
                Set<Integer> newBag = new HashSet<>(node.getVertices());
                newBag.add(v);
                DecompositionNode newNode = new DecompositionNodeImpl(newBag);
                node.addChild(newNode);
                node = newNode;
            }

            for (DecompositionNode grandChild : child.getChildren()) {
                node.addChild(grandChild);
            }
        }
    }

    private static NiceDecompositionNode generateNice(DecompositionNode node, SteinerInstance steiner) {
        NiceDecompositionNode result = null;
        if (node.getChildren().size() == 0) {
            // leaf
            result = new LeafNode();
        }
        if (node.getChildren().size() == 1) {
            DecompositionNode child = node.getChildren().iterator().next();
            NiceDecompositionNode niceChild = generateNice(child, steiner);

            Set<Integer> nodeBag = node.getVertices();
            Set<Integer> childBag = child.getVertices();

            Set<Integer> onlyInNode = new HashSet<>(nodeBag);
            onlyInNode.removeAll(childBag);

            Set<Integer> onlyInChild = new HashSet<>(childBag);
            onlyInChild.removeAll(nodeBag);


            if (onlyInChild.isEmpty()) {
                // introduce
                int introduced = onlyInNode.iterator().next();
                result = new IntroduceNode(node.getVertices(), introduced);
                result.addChild(niceChild);
            } else {
                // forget
                int forgotten = onlyInChild.iterator().next();
                result = new ForgetNode(node.getVertices(), forgotten);

//                // introduce all the edges before forgetting
                NiceDecompositionNode current = result;
                for (int vertex : nodeBag) {
                    if (steiner.getGraph().hasEdge(forgotten, vertex)) {
                        NiceDecompositionNode temp = new IntroduceEdgeNode(nodeBag, new TAEdge(forgotten, vertex));
                        current.addChild(temp);
                        current = temp;
                    }
                }
                current.addChild(niceChild);
            }
        }
        if (node.getChildren().size() == 2) {
            // join
            List<DecompositionNode> children = new ArrayList<>(node.getChildren());
            NiceDecompositionNode niceChild0 = generateNice(children.get(0), steiner);
            NiceDecompositionNode niceChild1 = generateNice(children.get(1), steiner);

            result = new JoinNode(node.getVertices());
            result.addChild(niceChild0);
            result.addChild(niceChild1);
        }
        return result;
    }

    private static void addTerminal(DecompositionNode node, int U) {
        for (DecompositionNode child : node.getChildren()) {
            addTerminal(child, U);
        }
        node.getVertices().add(U);
    }

    private static void fillInducedSubgraph(NiceDecompositionNode node) {
        for (DecompositionNode child : node.getChildren()) {
            fillInducedSubgraph((NiceDecompositionNode)child);
        }
        if (node.getChildren().size() == 0) {
            node.updateSubgraph(null);
        } else {
            node.updateSubgraph(((NiceDecompositionNode)node.getChildren().iterator().next()).getSubgraph());
        }
    }
}