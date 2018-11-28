import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start is null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> vertexList = graph.getAdjList();
        if (!vertexList.containsKey(start)) {
            throw new IllegalArgumentException(
                "start is not contained in the graph");
        }
        List<Vertex<T>> output = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> traversed = new HashSet<>(); 
        
        queue.add(start);
        traversed.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            output.add(v);
            for (VertexDistance<T> t : vertexList.get(v)) {
                if (!traversed.contains(t.getVertex())) {
                    queue.add(t.getVertex());
                    traversed.add(t.getVertex());
                }
            }
        }
        
        return output;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start is null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> vertexList = graph.getAdjList();
        if (!vertexList.containsKey(start)) {
            throw new IllegalArgumentException(
                "start is not contained in the graph");
        }
        List<Vertex<T>> output = new ArrayList<>();
        Set<Vertex<T>> traversed = new HashSet<>();
        
        depthFirstSearchHelper(start, traversed, output, vertexList);
        
        return output;
    }

    /**
     * helper method of depth-first-search, recursively 
     * calls itself and explores
     * the children of current node before moving on to neighbouring nodes
     * @param <T> generic type of the data
     * @param start -the starting node
     * @param traversed -the traversed node
     * @param output -the list of nodes traversed
     * @param vertexList -the list of available vertices
     */
    private static <T> void depthFirstSearchHelper(Vertex<T> start,
        Set<Vertex<T>> traversed, List<Vertex<T>> output,
        Map<Vertex<T>, List<VertexDistance<T>>> vertexList) {
        output.add(start);
        traversed.add(start);
        for (VertexDistance<T> v : vertexList.get(start)) {
            if (!traversed.contains(v.getVertex())) {
                depthFirstSearchHelper(v.getVertex(),
                    traversed, output, vertexList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start is null!");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null!");
        }
        //vertexList stores a list of adjacent vertices
        Map<Vertex<T>, List<VertexDistance<T>>> vertexList = graph.getAdjList();
        Map<Vertex<T>, Integer> output = new HashMap<>();
        //queue stores a list of sorted vertices yet to be visited
        Queue<VertexDistance<T>> queue = new PriorityQueue<>();
        if (!vertexList.containsKey(start)) {
            throw new IllegalArgumentException(
                "start is not contained in the graph");
        }
        for (Vertex<T> v : vertexList.keySet()) {
            if (v.equals(start)) {
                output.put(v, 0);
            } else {
                //assign "infinity" to all other vertices
                output.put(v, Integer.MAX_VALUE);
            }
        }
        //starting at initial vertex
        queue.add(new VertexDistance<>(start, 0));
        while (!queue.isEmpty()) {
            VertexDistance<T> removed = queue.remove();
            for (VertexDistance<T> v : vertexList.get(removed.getVertex())) {
                if (output.get(v.getVertex())
                    > removed.getDistance() + v.getDistance()) {
                    output.put(v.getVertex(),
                        removed.getDistance() + v.getDistance());
                    queue.add(new VertexDistance<T>(v.getVertex(),
                        removed.getDistance() + v.getDistance()));
                }
            }
        }
        return output;
    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph is null!");
        }
        Set<Edge<T>> output = new HashSet<>();
        //edge is the sorted queue which stores the edge length
        Queue<Edge<T>> edge = new PriorityQueue<>(graph.getEdges());
        //vertexList is the set that stores all vertices
        DisjointSet<Vertex<T>> vertexList = new DisjointSet<Vertex<T>>(
            graph.getAdjList().keySet());
        while (!edge.isEmpty()) {
            Edge<T> e = edge.remove();
            Vertex<T> u = vertexList.find(e.getU());
            Vertex<T> v = vertexList.find(e.getV());
            //if e,u,v do not form a cycle
            if (!u.equals(v)) {
                vertexList.union(u, v);
                output.add(e);
                output.add(new Edge<T>(e.getV(), e.getU(),
                        e.getWeight()));
            }
        }
        int s = graph.getAdjList().keySet().size();
        //if output covers all existing vertices
        if (output.size() == 2 * (s - 1))   {
            return output;
        } else  {
            //return null if there is no valid MST
            return null;
        }
    }
}
