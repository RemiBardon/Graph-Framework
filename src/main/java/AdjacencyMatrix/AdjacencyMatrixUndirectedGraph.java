package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.UndirectedNode;
import Abstraction.IUndirectedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixUndirectedGraph extends AbstractMatrixGraph<UndirectedNode> implements IUndirectedGraph {
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 
	
	public AdjacencyMatrixUndirectedGraph() {
		super();
	}
	
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		this.order=mat.length;
		this.matrix = new int[this.order][this.order];
		for(int i = 0; i<this.order; i++){
			for(int j = i; j<this.order; j++){
				this.matrix[i][j] = mat[i][j];
				this.matrix[j][i] = mat[i][j];
				this.m += mat[i][j];
			}
		}	
	}
	
	public AdjacencyMatrixUndirectedGraph(IUndirectedGraph g) {
		this.order = g.getNbNodes(); 				
		this.m = g.getNbEdges(); 				
		this.matrix = g.toAdjacencyMatrix(); 
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbEdges() {
		return this.m;
	}

	public List<Integer> getNeighbours(AbstractNode x) {
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i<matrix[x.getLabel()].length; i++){
			if(matrix[x.getLabel()][i]>0){
				l.add(i);
			}
		}
		return l;
	}
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isEdge(UndirectedNode x, UndirectedNode y) {
		// Completed
		return matrix[x.getLabel()][y.getLabel()] > 0 && matrix[y.getLabel()][x.getLabel()] > 0;
	}
	
	/**
     * removes the edge (x,y) if there exists at least one between these nodes in the graph.
     */
	@Override
	public void removeEdge(UndirectedNode x, UndirectedNode y) {
		// Completed
		if (isEdge(x, y)) {
			matrix[x.getLabel()][y.getLabel()]--;
			matrix[y.getLabel()][x.getLabel()]--;
		}
	}

	/**
     * adds the edge (x,y), we allow the multi-graph.
     */
	@Override
	public void addEdge(UndirectedNode x, UndirectedNode y) {
		// Completed
		matrix[x.getLabel()][y.getLabel()]++;
		matrix[y.getLabel()][x.getLabel()]++;
	}

	/**
     * @return the adjacency matrix representation int[][] of the graph
     */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	@Override
	public String toString() {
		return "Adjacency Matrix:\n" + GraphTools.matrixToString(this.matrix, 1, 2);
	}

	public static void main(String[] args) {
		final int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		System.out.println("Matrix:");
		GraphTools.afficherMatrix(mat2);

		final AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);
		System.out.println("N = "+am.getNbNodes()+ ", M = "+am.getNbEdges());

		final List<Integer> t2 = am.getNeighbours(new UndirectedNode(2));
		final String neighbors = t2.stream().map(Object::toString).collect(Collectors.joining(", "));
		System.out.println("\nNeighbors of 2: " + neighbors);

		System.out.println("2–5? " + am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));

		System.out.println("\nAdding 3 edges between 2 and 5…");
		for(int i = 0; i<3; i++)
			am.addEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println(am);

		System.out.println("\nRemoving 1 edge between 2 and 5…");
		am.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
		System.out.println(am);

		// TODO: Complete
	}

}
