package treewidth_approximation;

import prefuse.*;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import treewidth_approximation.logic.graph.GraphConverter;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.view.GraphShower;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        TAGraph g = provider.getRandom(15, 3.0/15);
        Set<Integer> W = provider.getRandomVertexSubset(g, 4).stream().map(TAVertex::getId).collect(Collectors.toSet());

        GraphShower.showGraph(g, List.of(W), "graph");
    }


}