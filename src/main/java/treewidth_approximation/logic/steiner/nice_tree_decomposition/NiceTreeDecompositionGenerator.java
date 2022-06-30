package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.ForgetNode;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.IntroduceNode;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.JoinNode;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.LeafNode;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;
import treewidth_approximation.logic.tree_decomposition.DecompositionNodeImpl;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionImpl;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NiceTreeDecompositionGenerator {

    public static TreeDecomposition generate(TreeDecomposition decomposition, SteinerInstance instance) {
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
        NiceDecompositionNode niceRoot = generateNice(copy.getRoot());
        return new NiceTreeDecomposition(niceRoot);


        // terminal to be added to every decomposition node
//        int U = instance.getTerminals().iterator().next();




//        NiceTreeDecomposition result = new NiceTreeDecomposition(generate(emptyRoot.));
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

            for (int i = 0; i < children.size()-1; i++) {
                DecompositionNode left = new DecompositionNodeImpl(node.getVertices());
                DecompositionNode right = new DecompositionNodeImpl(node.getVertices());
                left.addChild(children.get(i));
                current.addChild(left);
                current.addChild(right);
                current = right;
            }
            current.addChild(children.get(children.size()-1));
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

    private static NiceDecompositionNode generateNice(DecompositionNode node) {
        NiceDecompositionNode result = null;
        if (node.getChildren().size() == 0) {
            // leaf
            result = new LeafNode();
        }
        if (node.getChildren().size() == 1) {
            DecompositionNode child = node.getChildren().iterator().next();
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
            } else {
                // forget
                int forgotten = onlyInChild.iterator().next();
                result = new ForgetNode(node.getVertices(), forgotten);
            }

            NiceDecompositionNode niceChild = generateNice(child);
            result.addChild(niceChild);
        }
        if (node.getChildren().size() == 2) {
            // join
            List<DecompositionNode> children = new ArrayList<>(node.getChildren());
            NiceDecompositionNode niceChild0 = generateNice(children.get(0));
            NiceDecompositionNode niceChild1 = generateNice(children.get(1));

            result = new JoinNode(node.getVertices());
            result.addChild(niceChild0);
            result.addChild(niceChild1);
        }
        return result;
    }


}
