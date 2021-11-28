package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

public class UndirectedGraph extends AbstractListGraph<UndirectedNode> implements IUndirectedGraph {

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public UndirectedGraph() {
		 this.nodes = new ArrayList<>();
	}
	
	public UndirectedGraph(List<UndirectedNode> nodes) {
        super(nodes);
        for (UndirectedNode i : nodes) {
            this.m += i.getNbNeigh();
        }
    }

    public UndirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(this.makeNode(i));
        }
        for (UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrix[n.getLabel()].length; j++) {
            	UndirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn,0);
                    nn.getNeighbours().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public UndirectedGraph(UndirectedGraph g) {
        super();
        this.order = g.getNbNodes();
        this.m = g.getNbEdges();
        this.nodes = new ArrayList<>();
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (UndirectedNode n : g.getNodes()) {
        	UndirectedNode nn = this.getNodes().get(n.getLabel());
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
            	UndirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getNeighbours().put(snn,0);
                snn.getNeighbours().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbEdges() {
        return this.m;
    }

    @Override
    public boolean isEdge(UndirectedNode from, UndirectedNode to) {
//      return getNodeOfList(x).getNeighbours().containsKey(getNodeOfList(y));
        // Completed
        return from.getNeighbours().containsKey(to);// && to.getPreds().containsKey(from);
    }

    @Override
    public void removeEdge(UndirectedNode from, UndirectedNode to) {
        // Completed
        // No need to check `if (this.isEdge(from, to))` since `remove` only removes a key if it is present
        this.getNodeOfList(from).getNeighbours().remove(to);
        this.getNodeOfList(to).getNeighbours().remove(from);
    }

    @Override
    public void addEdge(UndirectedNode from, UndirectedNode to) {
        if (!isEdge(from, to)) {
            final UndirectedNode fromInThisGraph = this.getNodeOfList(from);
            final UndirectedNode toInThisGraph = this.getNodeOfList(to);
            fromInThisGraph.addNeigh(toInThisGraph, 0);
            toInThisGraph.addNeigh(fromInThisGraph, 0);
        }
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends UndirectedNode
     */
    @Override
    public UndirectedNode makeNode(int label) {
        return new UndirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }
    
    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        final int[][] matrix = new int[order][order];

        // Completed
        for (final UndirectedNode node: this.getNodes()) {
            for (final Map.Entry<UndirectedNode, Integer> entry: node.getNeighbours().entrySet()) {
                matrix[node.getLabel()][entry.getKey().getLabel()] = entry.getValue();
            }
        }

        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        final int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        final UndirectedGraph al = new UndirectedGraph(mat);
        System.out.println(al);
        System.out.println("2–5? " + al.isEdge(new UndirectedNode(2), new UndirectedNode(5)));

        // TODO: Complete
        GraphTools.afficherMatrix(al.toAdjacencyMatrix());
    }

}
