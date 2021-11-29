package GraphAlgorithms;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import Collection.Triple;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class GraphTools {

	private static int _DEBBUG =0;

	public GraphTools() {
	
	}

	/**
	 * 
	 * @param n the number of vertices
	 * @param multi at true if we want a multi-graph
	 * @param s at true if the graph is symmetric
	 * @param c at true if the graph is connected
	 * @param seed the unique seed giving a unique random graph
	 * @return the generated matrix
	 */ 
	public static int[][] generateGraphData(int n, boolean multi, boolean s, boolean c, int seed){
		if(_DEBBUG>0){
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateGraphData en aléatoire complet>>");
		}

		Random rand = new Random(seed);
		int m = (rand.nextInt(n)+1)*(n-1)/2;
		if(_DEBBUG>0){System.out.println("m = "+m);}
		int[][] matrix = new int[n][n];
		if(c){
			List<Integer> vis = new ArrayList<>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while(vis.size()<n ){
				if(!vis.contains(from)){
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);				
					if(s) {
						matrix[dest][from] = 1;
					}
					matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);				
			}
			m -= n-1;
		}

		while(m>0){
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			if(_DEBBUG>0){
				System.out.println("i = "+i);
				System.out.println("j = "+j);
			}
			if(!multi){
				if(i!=j && matrix[i][j]!=1 ){
					if(s) {
						matrix[j][i] = 1;
					}
					matrix[i][j] = 1;
					m--;
				}
			}
			else{
				if(matrix[i][j]==0 ){
					int val = ( i!=j ? ( m<3 ? m : rand.nextInt(3) +1) : 1);
					if(_DEBBUG>0){
						System.out.println("Pour multi, val = "+val);
					}
					if(s) {
						matrix[j][i] = val;
					}
					matrix[i][j] = val;
					m -= val;
				}
			}
		}
		return matrix;
	}

	/**
	 * 
	 * @param n the number of vertices
	 * @param m the number of edges
	 * @param multi at true if we want a multi-graph
	 * @param s at true if the graph is symmetric
	 * @param c at true if the graph is connexted
	 * @param seed the unique seed giving a unique random graph
	 * @return the generated matrix
	 */ 
	public static int[][] generateGraphData(int n, int m, boolean multi, boolean s, boolean c, int seed){
		if(_DEBBUG>0){
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateGraphData >>");
		}
		int[][] matrix = new int[n][n];
		Random rand = new Random(seed);
		if(c){
			List<Integer> vis = new ArrayList<>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while(vis.size() < n){
				if(!vis.contains(from)){
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);				
					if(s) {
						matrix[dest][from] = 1;
					}
					matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);				
			}
			m -= n-1;
		}

		while(m>0){
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			if(_DEBBUG>0){
				System.out.println("i = "+i);
				System.out.println("j = "+j);
			}
			if(!multi){
				if(i!=j && matrix[i][j]!=1 ){
					if(s) {
						matrix[j][i] = 1;
					}
					matrix[i][j] = 1;
					m--;
				}
			}
			else{
				if(matrix[i][j]==0 ){
					int val = ( i!=j ? ( m<3 ? m : rand.nextInt(3) +1) : 1);
					if(_DEBBUG>0){
						System.out.println("Pour multi, val = "+val);
					}
					if(s) {
						matrix[j][i] = val;
					}
					matrix[i][j] = val;
					m -= val;
				}
			}
		}
		return matrix;
	}

	/**
	 * 
	 * @param n, the number of vertices
	 * @param multi, at true if we want a multi-graph
	 * @param s, at true if the graph is symmetric
	 * @param c, at true if the graph is connexted
	 * @param neg, at true if the graph has negative weights 
	 * @param seed, the unique seed giving a unique random graph
	 */
	public static int[][] generateValuedGraphData(int n, int m, boolean multi, boolean s, boolean c, boolean neg, int seed){
		if(_DEBBUG>0){
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateValuedGraphData >>");
		}

		int[][] mat = generateGraphData(n, m, multi, s, c, seed);
		int [][] matValued = new int[mat.length][mat.length];
		Random rand = new Random(seed);
		int valNeg = 0;
		if(neg) {
			valNeg = -6;
		}

		for(int i = 0; i<mat.length; i++){
			for(int j = 0; j<mat[0].length; j++){
				if(mat[i][j]>0){
					int val = rand.nextInt(15) + 1 + valNeg;
					matValued[i][j] = val;
					if(s) {
						matValued[j][i] = val;
					}
				}
			}
		}

		return matValued;
	}

	public static int[][] generateValuedGraphData(int n, boolean multi, boolean s, boolean c, boolean neg, int seed) {
		return generateValuedGraphData(n, n, multi, s, c, neg, seed);
	}

		/**
         * @param m a matrix
         */
	public static void afficherMatrix(final int[][] m) {
		System.out.println(matrixToString(m, 1, 0));
	}

	/**
	 * @param m a matrix
	 */
	public static int[][] invertMatrix(final int[][] m) {
		int[][] inverse = new int[m.length][m.length];

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				inverse[j][i] = m[i][j];
			}
		}

		return inverse;
	}

	/**
	 * @param m A matrix.
	 * @param digits The number of digits per number
	 *               (e.g. {@code 1} will show {@code "-5␣0␣5␣10"},
	 *               {@code 2} will show {@code "-5␣␣0␣␣5␣10"}
	 *               and {@code 3} will show {@code "␣-5␣␣␣0␣␣␣5␣␣10"}).
	 * @param indent The number of leading spaces on each line.
	 */
	public static String matrixToString(final int[][] m, final int digits, final int indent) {
		final String format = "%" + digits + "d";
		return Arrays.stream(m)
			.map(row -> Arrays.stream(row)
				// Format numbers to take the desired number of digits
				.mapToObj(c -> String.format(format, c))
				// Join columns with spaces
				.collect(Collectors.joining(" "))
			)
			// Indent
			.map(row -> String.join("", Collections.nCopies(indent, " ")) + row)
			// Join rows with `\n`s
			.collect(Collectors.joining("\n"));
	}

	/**
	 * @param mat, a matrix
	 * @return the symmetrical matrix 
	 */
	public static int[][] matrixSym(int[][] mat){
		for(int i = 0; i<mat.length; i++){
			for(int j = 0; j<mat[i].length; j++){
				if(mat[i][j] == 1) {
					mat[j][i] = 1;
				}
				mat[i][j] = 0;
			}
		}
		return mat;
	}

	public static <Node extends AbstractNode, T> void bfs(
		final Node start,
		final Function<Node, T> function,
		final Function<Node, Set<Node>> nextNodes
	) {
		Set<Node> visited = new LinkedHashSet<>();
		visited.add(start);

		Queue<Node> toVisit = new LinkedList<>();
		toVisit.add(start);

		Node actual;
		while ((actual = toVisit.poll()) != null) {
			function.apply(actual);

			for (final Node next: nextNodes.apply(actual)) {
				if (!visited.contains(next)) {
					toVisit.add(next);
					visited.add(next);
				}
			}
		}
	}

	public static <T> void bfs(final UndirectedNode start, final Function<UndirectedNode, T> function) {
		bfs(start, function, node -> node.getNeighbours().keySet());
	}

	public static List<UndirectedNode> bfs(final UndirectedNode start) {
		final ArrayList<UndirectedNode> stack = new ArrayList<>();
		bfs(start, stack::add);
		return stack;
	}

	public static <T> void bfs(final DirectedNode start, final Function<DirectedNode, T> function) {
		bfs(start, function, node -> node.getSuccs().keySet());
	}

	public static List<DirectedNode> bfs(final DirectedNode start) {
		final ArrayList<DirectedNode> visitOrder = new ArrayList<>();
		bfs(start, visitOrder::add);
		return visitOrder;
	}

	public static <Node extends AbstractNode, T> void dfs(
		final Node start,
		final Function<Node, T> function,
		final Function<Node, Set<Node>> nextNodes
	) {
		final Set<Node> marked = new HashSet<>();
		final Stack<Node> toVisit = new Stack<>();
		toVisit.push(start);

		Node actual;
		while (!toVisit.empty()) {
			actual = toVisit.pop();
			if (marked.contains(actual)) { continue; }
			marked.add(actual);

			for (final Node next: nextNodes.apply(actual)) {
				if (!marked.contains(next)) {
					toVisit.push(next);
				}
			}

			function.apply(actual);
		}
	}

	public static <T> void dfs(final UndirectedNode start, final Function<UndirectedNode, T> function) {
		dfs(start, function, node -> node.getNeighbours().keySet());
	}

	public static List<UndirectedNode> dfs(final UndirectedNode start) {
		final ArrayList<UndirectedNode> stack = new ArrayList<>();
		dfs(start, stack::add);
		return stack;
	}

	public static <T> void dfs(final DirectedNode start, final Function<DirectedNode, T> function) {
		dfs(start, function, node -> node.getSuccs().keySet());
	}

	public static List<DirectedNode> dfs(final DirectedNode start) {
		final ArrayList<DirectedNode> visitOrder = new ArrayList<>();
		dfs(start, visitOrder::add);
		return visitOrder;
	}

	public static void explorerSommet(final UndirectedNode s, final Set<UndirectedNode> a) {
		a.add(s);
		for (final UndirectedNode t: s.getNeighbours().keySet()) {
			if (!a.contains(t)) {
				explorerSommet(t, a);
			}
		}
	}

	public static <S, T> void explorerSommet(
		final UndirectedNode s,
		final Set<UndirectedNode> atteint,
		final Function<UndirectedNode, S> before,
		final Function<UndirectedNode, T> after
	) {
		atteint.add(s);
		for (final UndirectedNode t: s.getNeighbours().keySet()) {
			if (!atteint.contains(t)) {
				before.apply(t);
				explorerSommet(t, atteint, before, after);
				after.apply(t);
			}
		}
	}

	public static List<UndirectedNode> prim(UndirectedGraph g,UndirectedNode start){
		//Démarrer avec un arbre initial A ayant un seul sommet
		List<UndirectedNode> exploree = new ArrayList<>();
		exploree.add(start);


		BinaryHeapEdge tasBinaire = new BinaryHeapEdge();

		// Tant que l’arbre n’est pas couvrant, ajouter le sommet
		// voisin le plus proche ne créant pas de cycle.
		UndirectedNode sommmetActuel = start;
		for (int i = 1; i < g.getNbNodes(); i++) {
			// Ajout des voisins du sommmetActuel
			for (Map.Entry<UndirectedNode, Integer> neighbour : sommmetActuel.getNeighbours().entrySet()) {
				tasBinaire.insert(sommmetActuel, neighbour.getKey(), neighbour.getValue());
			}

			// On prend l'arête de poids minimal
			Triple<UndirectedNode, UndirectedNode, Integer> currentEdge = tasBinaire.remove();
			// gestion des cycles
			while (exploree.contains(currentEdge.getSecond())) {
				currentEdge = tasBinaire.remove();
			}

			// passage au sommet suivant
			sommmetActuel = currentEdge.getSecond();
			exploree.add(sommmetActuel);
		}

		return exploree;
	}

	public static int[] dijkstra(int s, int[][] cout) {
		// Initialisation
		final int n = cout.length;
		// Tableau des sommets atteints
		boolean[] b = new boolean[n];
		Arrays.fill(b, false);
//		System.out.println("B=" + Arrays.toString(b));
		// Tableau des coûts
		int[] v = new int[n];
		Arrays.fill(v, Integer.MAX_VALUE);
		v[s] = 0;
//		System.out.println("V=" + Arrays.toString(v));
		// Tableau des prédécesseurs
		int[] p = new int[n];
		Arrays.fill(p, 0);
		p[s] = 1;
//		System.out.println("P=" + Arrays.toString(p));

		// Choix du sommet de départ
		int x = s;
		int visited = 1;
		// Tant qu’il reste un sommet non atteint
		while (visited < n) {
			// Recherche du sommet x non atteint de coût minimal
			int min = Integer.MAX_VALUE;
			for (int y=0; y<n; y++) {
				if (!b[y] && v[y]<min) {
					x = y;
					min = v[y];
				}
			}

			// Mise à jour des successeurs non fixés de x
			if (min < Integer.MAX_VALUE) {
				b[x] = true;
				for (int y=0; y<n; y++) {
					if ((!b[y]) && (cout[x][y] >= 0) && (cout[x][y] < v[y]) && (v[x]+cout[x][y] < v[y])) {
						v[y] = v[x]+cout[x][y];
						p[y] = x;
					}
				}
			}

			visited++;
		}

		return v;
	}

	public static int[] bellman(final int s, final DirectedGraph G) {
		// Initialisation
		final int n = G.getNbNodes();
		int[] dist = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[s] = 0;
//		System.out.println("dist=" + Arrays.toString(dist));

		/*
		for (int k = 1; k < n; k++) {
			final int[] distkmoins1 = Arrays.copyOf(dist, n);
			for (DirectedNode v: G.getNodes().stream().filter(node -> node.getLabel() != s).collect(Collectors.toList())) {
				int d = distkmoins1[v.getLabel()];
				for (Map.Entry<DirectedNode, Integer> entry: v.getPreds().entrySet()) {
					DirectedNode u = entry.getKey();
					Integer distance = entry.getValue();
					if (distkmoins1[u.getLabel()] == Integer.MAX_VALUE) {
//						d = min(d, distance);
					} else {
						d = min(d, abs(distkmoins1[u.getLabel()] + distance));
					}
				}

				dist[v.getLabel()] = d;
			}
		}
		*/

		for (int i = 1; i < n; ++i) {
			DirectedNode v = G.getNodes().get(i);
			for (final Map.Entry<DirectedNode, Integer> entry: v.getPreds().entrySet()) {
				final DirectedNode u = entry.getKey();
				final int w = entry.getValue();
				if (dist[u.getLabel()] != Integer.MAX_VALUE && dist[u.getLabel()] + w < dist[v.getLabel()])
					dist[v.getLabel()] = dist[u.getLabel()] + w;
			}
		}

		// Détection des cycles
		DirectedNode v = G.getNodes().get(n - 1);
		for (Map.Entry<DirectedNode, Integer> entry: v.getPreds().entrySet()) {
			DirectedNode u = entry.getKey();
			int w = entry.getValue();
			if (dist[u.getLabel()] != Integer.MAX_VALUE && dist[u.getLabel()] + w < dist[v.getLabel()]) {
				System.out.println("⚠️ Cycle");
				return dist;
			}
		}

		return dist;
	}


	public static void explorerGraphe(final UndirectedGraph g) {
		Set<UndirectedNode> atteint = new HashSet<>();
		for (final UndirectedNode s: g.getNodes()) {
			if (!atteint.contains(s)) {
				explorerSommet(s, atteint);
			}
		}
	}

	public static void explorerGrapheAvecOrdre(
		final List<UndirectedNode> nodes,
		final LinkedHashMap<UndirectedNode, Integer> debuts,
		final LinkedHashMap<UndirectedNode, Integer> fins
	) {
		Set<UndirectedNode> atteint = new HashSet<>();

		AtomicInteger compteur = new AtomicInteger();
		for (final UndirectedNode s: nodes) {
			if (!atteint.contains(s)) {
				explorerSommet(
					s,
					atteint,
					s2 -> {
						//System.out.println("Before " + s2);
						compteur.getAndIncrement();
						return debuts.put(s2, compteur.get());
					},
					s2 -> {
						//System.out.println("After " + s2);
						compteur.getAndIncrement();
						return fins.put(s2, compteur.get());
					}
				);
			}
		}
	}

	public static void explorerGrapheAvecOrdre(
			final UndirectedGraph g,
			final LinkedHashMap<UndirectedNode, Integer> debuts,
			final LinkedHashMap<UndirectedNode, Integer> fins
	) {
		explorerGrapheAvecOrdre(g.getNodes(), debuts, fins);
	}

	public static void main(String[] args) {
		final int[][] mat = generateGraphData(10, 20, false, false, false, 100001);
		System.out.println("Matrix 1:");
		afficherMatrix(mat);


		final int[][] mat2 = generateGraphData(10, 20, false, false, false, 100002);
		System.out.println("\nMatrix 2:");
		afficherMatrix(mat2);


		final int[][] mat3 = generateGraphData(10, 20, false, true, true, 100003);
		System.out.println("\nMatrix 3:");
		afficherMatrix(mat3);

		final int[][] matVal = generateValuedGraphData(10, false, false, true, true, 100007);
		System.out.println("\nValued matrix:");
		afficherMatrix(matVal);


		final int[][] mat4 = generateGraphData(10, 30, false, false, true, 100_008);
		System.out.println("\nMatrix 4:");
		afficherMatrix(mat4);

		final DirectedGraph g4 = new DirectedGraph(mat4);
		System.out.println("\nGraph 4:");
		System.out.println(g4);
		System.out.println("Nodes: " + g4.getNodes().stream().map(Object::toString).collect(Collectors.joining(", ")));

		final DirectedNode node3 = g4.getNodes().get(3);
		System.out.println("DFS (3): " + dfs(node3).stream().map(Object::toString).collect(Collectors.joining(", ")));
		System.out.println("BFS (3): " + bfs(node3).stream().map(Object::toString).collect(Collectors.joining(", ")));

		final DirectedNode node7 = g4.getNodes().get(7);
		System.out.println("DFS (7): " + dfs(node7).stream().map(Object::toString).collect(Collectors.joining(", ")));
		System.out.println("BFS (7): " + bfs(node7).stream().map(Object::toString).collect(Collectors.joining(", ")));


		final int[][] mat5 = generateGraphData(10, 20, false, true, true, 100_010);
		final UndirectedGraph g5 = new UndirectedValuedGraph(mat5);
		System.out.println("\nGraph 5:");
		System.out.println(g5);
		System.out.println("Nodes: " + g5.getNodes().stream().map(Object::toString).collect(Collectors.joining(", ")));

		System.out.println(matrixToString(mat5, 2, 0));
		System.out.println("DFS (0): " + dfs(g5.getNodes().get(0)).stream().map(Object::toString).collect(Collectors.joining(", ")));
		System.out.println("BFS (0): " + bfs(g5.getNodes().get(0)).stream().map(Object::toString).collect(Collectors.joining(", ")));
		final LinkedHashMap<UndirectedNode, Integer> debuts = new LinkedHashMap<>();
		final LinkedHashMap<UndirectedNode, Integer> fins = new LinkedHashMap<>();
		explorerGrapheAvecOrdre(g5, debuts, fins);
		System.out.println("\nDebuts: " + debuts);
		System.out.println("Fins: " + fins);


		final int[][] mat6 = generateValuedGraphData(5, false, true, true, false, 100_010);
		final UndirectedGraph g6 = new UndirectedValuedGraph(mat6);
		System.out.println("\nGraph 6: " + g6);
		System.out.println("Nodes: " + g6.getNodes().stream().map(Object::toString).collect(Collectors.joining(", ")));
		System.out.println(matrixToString(mat6, 2, 0));
//		System.out.println(g6.getNodes().get(0).getWeight());
		System.out.println("PRIM: " + prim(g6, g6.getNodes().get(0)));


//		int[][] matrix = generateGraphData(10, 15, false, true, false, 100001);

		final int[][] mat7 = generateValuedGraphData(10, 20, false, false, false, false, 100_008);
		System.out.println("\nMatrix 7:");
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (mat7[x][y] == 0) {
					mat7[x][y] = Integer.MAX_VALUE;
				}
			}
		}
		System.out.println(matrixToString(mat7, 10, 2));
		System.out.println("DIJKSTRA (0): " + Arrays.toString(dijkstra(0, mat7)));
		System.out.println("DIJKSTRA (4): " + Arrays.toString(dijkstra(4, mat7)));

		final int[][] mat8 = generateValuedGraphData(10, 40, false, false, false, false, 100_008);
		/*for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (mat8[x][y] == 0) {
					mat8[x][y] = Integer.MAX_VALUE;
				}
			}
		}*/
		System.out.println("\nMatrix 8:");
		System.out.println(matrixToString(mat8, 2, 2));
		final DirectedGraph g8 = new DirectedValuedGraph(mat8);
		System.out.println("\nGraph 8:");
		System.out.println(g8);
		System.out.println("BELLMAN (0): " + Arrays.toString(bellman(0, g8)));
		System.out.println("BELLMAN (4): " + Arrays.toString(bellman(4, g8)));
//		System.out.println(abs(Integer.MIN_VALUE) == Integer.MIN_VALUE);
//		System.out.println(abs(Integer.MIN_VALUE) * -1);
	}

}
