package AdjacencyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;

public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

	private static final int _DEBUG =0;

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedGraph(){
		super();

		this.nodes = new ArrayList<>();
	}

    public DirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (final DirectedNode n: this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
                final DirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,0);
                    nn.getPreds().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public DirectedGraph(DirectedGraph g) {
        super();

        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();

        for(final DirectedNode n: g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (final DirectedNode n: g.getNodes()) {
            final DirectedNode nn = this.getNodes().get(n.getLabel());
            for (final DirectedNode sn: n.getSuccs().keySet()) {
                final DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn, 0);
                snn.getPreds().put(nn, 0);
            }
        }
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public boolean isArc(DirectedNode from, DirectedNode to) {
        // Completed
        return from.getSuccs().containsKey(to);// && to.getPreds().containsKey(from);
    }

    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
        // Completed
        // No need check `if (this.isArc(from, to))` since `remove` only removes a key if it is present
        from.getSuccs().remove(to);
        to.getPreds().remove(from);
    }

    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
        // Completed
        if (!this.isArc(from, to)) {
            from.addSucc(to, 0);
            from.addPred(to, 0);
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public DirectedNode makeNode(int label) {
        return new DirectedNode(label);
    }

    /**
     * @return the corresponding {@link DirectedNode} in the {@link #nodes} {@link List}
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];

        System.out.println("nodes: " + nodes);
        for (int i = 0; i < order; i++) {
            System.out.println("nodes.get(i).getSuccs(): " + nodes.get(i).getSuccs());
            for (final Map.Entry<DirectedNode, Integer> entry: nodes.get(i).getSuccs().entrySet()) {
                matrix[i][entry.getKey().getLabel()] = 1;
            }
        }

        return matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {
        // Completed
        return new DirectedGraph(GraphTools.invertMatrix(this.toAdjacencyMatrix()));
    }
    
    @Override
    public String toString(){
        final StringBuilder s = new StringBuilder();

        s.append("Successors:\n");
        for (final DirectedNode n: nodes) {
            s.append("  - ").append(n).append(" -> ");
            s.append(n.getSuccs().keySet().stream().map(Object::toString).collect(Collectors.joining(", "))).append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        final int[][] mat1 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        System.out.println("Matrix:");
        System.out.println(GraphTools.matrixToString(mat1, 1, 2));
        System.out.println("Matrix inverse:");
        System.out.println(GraphTools.matrixToString(GraphTools.invertMatrix(mat1), 1, 2));

        DirectedGraph dg1 = new DirectedGraph(mat1);
        System.out.println(dg1);

        // TODO: Complete
        System.out.println("Adjacency matrix:\n" + GraphTools.matrixToString(dg1.toAdjacencyMatrix(), 1, 2));
        System.out.println("Clone:\n" + new DirectedGraph(dg1));
        System.out.println("Inverse:\n" + dg1.computeInverse());
    }
}
