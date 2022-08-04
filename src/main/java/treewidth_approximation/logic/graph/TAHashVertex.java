package treewidth_approximation.logic.graph;

import java.util.HashSet;
import java.util.Set;

public class TAHashVertex implements TAVertex {
    private final Set<TAVertex> neighbours;
    private final Set<Integer> neighboursIds;
    private final int id;

    public TAHashVertex(int id) {
        this.id = id;
        neighbours = new HashSet<>();
        neighboursIds = new HashSet<>();
    }

    @Override
    public int getId() {
        return id;
    }

//    @Override
//    public void setId(int id) {
//        // they remember old id
//        for (TAVertex neighbour : neighbours) {
//            neighbour.removeNeighbour(this);
//        }
//        this.id = id;
//        for (TAVertex neighbour : neighbours) {
//            neighbour.addNeighbour(this);
//        }
//    }

    @Override
    public Set<TAVertex> getNeighbours() {
        return neighbours;
    }

    @Override
    public Set<Integer> getNeighboursIds() {
        return neighboursIds;
    }

    @Override
    public void addNeighbour(TAVertex neighbour) {
        neighbours.add(neighbour);
        neighboursIds.add(neighbour.getId());
    }

    @Override
    public void removeNeighbour(TAVertex neighbour) {
        neighbours.remove(neighbour);
        neighboursIds.remove(neighbour.getId());
    }
}
