package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

public class UndirectedValuedGraph extends UndirectedGraph{

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public UndirectedValuedGraph(int[][] matrixVal) {
        super();

        this.order = matrixVal.length;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (final UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrixVal[n.getLabel()].length; j++) {
                final UndirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn,matrixVal[n.getLabel()][j]);
                    nn.getNeighbours().put(n,matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph
     */
    public void addEdge(UndirectedNode from, UndirectedNode to, int cost) {
        // Completed
        if (!this.isEdge(from, to)) {
            from.addNeigh(to, cost);
            from.addNeigh(to, cost);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append("(").append(sn).append(",").append(n.getNeighbours().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 15, false, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        UndirectedValuedGraph al = new UndirectedValuedGraph(matrixValued);
        System.out.println(al);
        // TODO: Complete
    }

}
