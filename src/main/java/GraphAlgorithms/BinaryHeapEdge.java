package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Collection.Triple;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * First node is the he leading node of the edge.
	 * Second node is trailing node of the edge.
	 * The integer is the weight of the edge.
	 */
	private List<Triple<UndirectedNode,UndirectedNode,Integer>> binh;

	public BinaryHeapEdge() {
		this.binh = new ArrayList<>();
	}

	public boolean isEmpty() {
		return binh.isEmpty();
	}

	/**
	 * Insert a new edge in the binary heap
	 *
	 * @param from one node of the edge
	 * @param to one node of the edge
	 * @param val the edge weight
	 */
	public void insert(UndirectedNode from, UndirectedNode to, int val) {
		// Completed
		Triple<UndirectedNode, UndirectedNode, Integer> element = new Triple<>(from, to, val);
		int i = this.binh.size();
		this.binh.add(i, element);

		int father = (i - 1) / 2;
		// percolate-up
		while ((father  >= 0 && this.binh.get(father).getThird() > element.getThird())) {
			this.swap(father, i);
			i = father;
			element = this.binh.get(i);
		}
	}


	/**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
	 *
	 * @return the edge with the minimal weight (root of the binary heap)
	 */
	public Triple<UndirectedNode,UndirectedNode,Integer> remove() {
		// Completed
		Triple<UndirectedNode, UndirectedNode, Integer> root = new Triple<>(this.binh.get(0).getFirst(), this.binh.get(0).getSecond(), this.binh.get(0).getThird());

		// On l’échange (swap) avec la
		// dernière et on l’enlève du tas
		int lastIndex = binh.size() - 1;
		int i = 0;
		this.swap(i, lastIndex);
		this.binh.get(lastIndex).setThird(Integer.MAX_VALUE);

		int bestChild = this.getBestChildPos(i);
		// percolate-down pour faire descendre récursivement la nouvelle valeur à la racine avec le fils de plus
		// petite valeur
		// System.out.println(this.binh.get(bestChild-1).getThird());
		while (bestChild != Integer.MAX_VALUE && this.binh.get(bestChild).getThird() < this.binh.get(i).getThird()) {
			swap(i, bestChild);
			i = bestChild;
			bestChild = this.getBestChildPos(i);
		}

		return root;

	}


	/**
	 * From an edge indexed by src, find the child having the least weight and return it
	 *
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
	private int getBestChildPos(int src) {
		int lastIndex = binh.size()-1;
		if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
			return Integer.MAX_VALUE;
		}
		int leftChildPos = 2 * src + 1;
		int rightChildPos = 2 * src + 2;

		if (rightChildPos >= lastIndex) {
			return leftChildPos;
		}

		if(this.binh.get(leftChildPos).getThird() < this.binh.get(rightChildPos).getThird()){
			return leftChildPos;
		}
		else {
			return rightChildPos;
		}
	}

	private boolean isLeaf(int src) {
		// Completed
		return 2 * src + 1 >= this.binh.size();
	}


	/**
	 * Swap two edges in the binary heap
	 *
	 * @param father an index of the list edges
	 * @param child an index of the list edges
	 */
	private void swap(int father, int child) {
		Triple<UndirectedNode,UndirectedNode,Integer> temp = new Triple<>(binh.get(father).getFirst(), binh.get(father).getSecond(), binh.get(father).getThird());
		binh.get(father).setTriple(binh.get(child));
		binh.get(child).setTriple(temp);
	}


	/**
	 * Create the string of the visualisation of a binary heap
	 *
	 * @return the string of the binary heap
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Triple<UndirectedNode,UndirectedNode,Integer> no: binh) {
			s.append(no).append(", ");
		}
		return s.toString();
	}


	private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}

	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 *
	 */
	public void lovelyPrinting(){
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1+(int)(Math.log(this.binh.size())/Math.log(2));
		int index=0;

		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index<binh.size()){
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// ------------------------------------
	// 					TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 *
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 *
	 */
	private boolean test() {
		return this.isEmpty() || testRec(0);
	}

	private boolean testRec(int root) {
		int lastIndex = binh.size()-1;
		if (isLeaf(root)) {
			return true;
		} else {
			int left = 2 * root + 1;
			int right = 2 * root + 2;
			if (right >= lastIndex) {
				return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left);
			} else {
				return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left)
						&& binh.get(right).getThird() >= binh.get(root).getThird() && testRec(right);
			}
		}
	}

	public static void main(String[] args) {
		BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
		System.out.println(jarjarBin.isEmpty()+"\n");
		int k = 10;
		int m = k;
		int min = 2;
		int max = 20;
		while (k > 0) {
			int rand = min + (int) (Math.random() * ((max - min) + 1));
			jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k+30), rand);
			k--;
		}
		// TODO: Complete

		System.out.println(jarjarBin.test());
	}

}
