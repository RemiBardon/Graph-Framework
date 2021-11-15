package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixDirectedGraph extends AbstractMatrixGraph<DirectedNode> implements IDirectedGraph {

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedGraph() {
		super();
	}

	public AdjacencyMatrixDirectedGraph(int[][] M) {
		this.order = M.length;
		this.matrix = new int[this.order][this.order];
		for(int i = 0; i<this.order; i++){
			for(int j = 0; j<this.order; j++){
				this.matrix[i][j] = M[i][j];
				this.m += M[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(IDirectedGraph g) {
		this.order = g.getNbNodes();
		this.m = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbArcs() {
		return this.m;
	}

	public List<Integer> getSuccessors(DirectedNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix[x.getLabel()].length;i++){
			if(this.matrix[x.getLabel()][i]>0){
				v.add(i);
			}
		}
		return v;
	}

	public List<Integer> getPredecessors(DirectedNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix.length;i++){
			if(this.matrix[i][x.getLabel()]>0){
				v.add(i);
			}
		}
		return v;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isArc(DirectedNode from, DirectedNode to) {
		// Completed
		return matrix[from.getLabel()][to.getLabel()] > 0;
	}

	/**
	 * removes the arc (from,to) if there exists at least one between these nodes in the graph.
	 */
	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		// Completed
		if (isArc(from, to)) {
			matrix[from.getLabel()][to.getLabel()]--;
		}
	}

	/**
	 * Adds the arc (from,to). we allow multiple graph.
	 */
	@Override
	public void addArc(DirectedNode from, DirectedNode to) {
		// Completed
		matrix[from.getLabel()][to.getLabel()]++;
	}


	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	@Override
	public IDirectedGraph computeInverse() {
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(this.matrix);
		for(int i=0; i<this.order; i++) {
			for(int j=i+1; j<this.order; j++) {
				int tmp = am.matrix[i][j];
				am.matrix[i][j] = am.matrix[j][i];
				am.matrix[j][i] = tmp;
			}
		}
		// Completed
		return am;
	}

	@Override
	public String toString(){
		return "Adjacency Matrix:\n" + GraphTools.matrixToString(this.matrix, 1, 2);
	}

	public static void main(String[] args) {
		final int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		final AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);

		final List<Integer> t = am.getSuccessors(new DirectedNode(1));
		final String successors = t.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println("Successors of 1: " + successors);

		final List<Integer> t2 = am.getPredecessors(new DirectedNode(2));
		final String predecessors = t2.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println("Predecessors of 2: " + predecessors);

		// TODO: Complete
	}

}
