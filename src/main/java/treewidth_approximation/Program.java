package treewidth_approximation;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAHashGraph;
import treewidth_approximation.logic.misc.serialization.StringScanner;
import treewidth_approximation.logic.misc.serialization.StringWriter;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.SteinerSolver;
import treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.NiceTreeDecomposition;
import treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.NiceTreeDecompositionGenerator;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.PrefuseGraphShower;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        // read problem instance from file provided as first argument
        String inputPath = args[0];
        String outputPath = args[1];
        String instanceString = new Scanner(new File(inputPath)).useDelimiter("\\Z").next();
        SteinerInstance instance = StringScanner.scanSteinerInstance(instanceString, TAHashGraph::new);

        // assert graph is a single connected component
        assert instance.getGraph().splitIntoConnectedComponents(false).size() == 1;

        for (int i = 1; i <= 5; i++) {
            // attempt to find tree-decomposition of width 4*i+3
            TreeDecompositionFinder.Result r = TreeDecompositionFinder
                    .find(instance.getGraph(), i);

            if (!r.successful) {
                // tree-width greater than i, try again with bigger i
                // r.setWithoutSeparator - set without balanced separator of order i+1
                continue;
            }
            TreeDecomposition decomposition = r.decomposition;
            // verify, that resulting decomposition is in fact correct
            assert TreeDecompositionVerifier.verify(decomposition, instance.getGraph(), i * 4 + 3, true);

            // generate niceDecomposition from decomposition and solve
            NiceTreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, instance);
            SteinerSolver.solve(instance, niceDecomposition);

            // show result
            PrefuseGraphShower.showTreeDecomposition(niceDecomposition, "Extended nice decomposition");
            PrefuseGraphShower.showSteinerInstance(instance, "Solved Steiner Instance");

            // save result
            List<TAEdge> resultEdges = new ArrayList<>(instance.getSelected());
            FileWriter output = new FileWriter(outputPath);
            output.write(StringWriter.writeEdgeList(resultEdges));
            output.close();
            break;
        }
    }
}
