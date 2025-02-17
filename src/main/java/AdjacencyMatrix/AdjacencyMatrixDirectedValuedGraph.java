package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	private  int[][] matrixCosts;	// The graph with Costs

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super();
		this.order = mat.length;
		this.matrix = new int[this.order][this.order];
		this.matrixCosts = new int[this.order][this.order];
		for(int i =0;i<this.order;i++){
			for(int j=0;j<this.order;j++){
				int val = mat[i][j];
				int cost = matrixVal[i][j]; 
				this.matrix[i][j] = val;				
				this.matrixCosts[i][j] = cost; 
				this.m += val;					
			}
		}
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------
	
	/**
	 * @return the matrix with costs of the graph
 	 */
	public int[][] getMatrixCosts() {
		return matrixCosts;
	}

	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	/**
     * removes the arc (from,to) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		super.removeArc(from, to);
		// Completed
		matrixCosts[from.getLabel()][to.getLabel()] = 0;
	}

	/**
     * adds the arc (from,to,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
	public void addArc(DirectedNode from, DirectedNode to, int cost ) {
		super.addArc(from,to);
		// Completed
		// FIXME: We don't keep the initial cost as the doc comment says
		matrix[from.getLabel()][to.getLabel()] = cost;
	}

	public String toString() {
		return "Matrix of Costs:\n" + GraphTools.matrixToString(this.matrixCosts, 2, 2);
	}

	public static void main(String[] args) {
		final int[][] matrix = GraphTools.generateGraphData(10, 30, false, false, false, 100001);
		final int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);

		final AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);

		// TODO: Complete
	}

}
