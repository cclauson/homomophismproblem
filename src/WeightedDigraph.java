

import java.util.*;

// S is type of node
// T is type of edge weight
public class WeightedDigraph<S, T> {
	
	public static class Edge<S, T> {
		public final S src;
		public final S dst;
		public final T weight;
		
		public Edge(S src, S dst, T weight) {
			this.src = src;
			this.dst = dst;
			this.weight = weight;
		}
	}
	
	private final Map<NodePair<S>, T> weights;
	private final Set<S> nodes;
	private final HashMultiMap<S, S> forwardReferences;
	private final HashMultiMap<S, S> backwardReferences;
	
	public WeightedDigraph() {
		this.weights = new HashMap<NodePair<S>, T>();
		this.nodes = new HashSet<S>();
		this.forwardReferences = new HashMultiMap<S, S>();
		this.backwardReferences = new HashMultiMap<S, S>();
	}
	
	private class NodePair<S> {
		public final S src;
		public final S dst;
		
		public NodePair(S src, S dst) {
			this.src = src;
			this.dst = dst;
		}
		
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof NodePair)) return false;
			final NodePair<?> nodePair2 = (NodePair<?>) o;
			return this.src.equals(nodePair2.src) &&
					this.dst.equals(nodePair2.dst);
		}
		
		public int hashCode() {
			return 31 * src.hashCode() ^ 53 * src.hashCode();
		}
	}
	
	public void addNode(S node) {
		this.nodes.add(node);
	}
	
	// if edge already exists, then just change the weight
	public void addEdgeWithWeightOrSetWeight(S nodeSrc, S nodeDst, T weight) {
		if (!nodes.contains(nodeSrc))
			throw new IllegalArgumentException(nodeSrc + " is not a node in the graph");
		if (!nodes.contains(nodeDst))
			throw new IllegalArgumentException(nodeDst + " is not a node in the graph");
		
		forwardReferences.put(nodeSrc, nodeDst);
		backwardReferences.put(nodeDst, nodeSrc);
		final NodePair<S> nodepair = new NodePair<S>(nodeSrc, nodeDst);
		weights.put(nodepair, weight);
	}
	
	public Iterable<S> successors(S node) {
		return this.forwardReferences.get(node);
	}

	public Iterable<S> predecessors(S node) {
		return this.backwardReferences.get(node);
	}
	
	public boolean hasEdge(S nodeSrc, S nodeDst) {
		return weights.containsKey(new NodePair<S>(nodeSrc, nodeDst));
	}
	
	public Set<S> nodeSet() {
		return Collections.unmodifiableSet(nodes);
	}
	
	public List<Edge<S, T>> outEdgesForNode(S node) {
		final Set<S> dstNodes = this.forwardReferences.get(node);
		final List<Edge<S, T>> edges = new ArrayList<Edge<S, T>>();
		for (S dstNode : dstNodes) {
			edges.add(new Edge<S, T>(node, dstNode, weights.get(new NodePair<S>(node, dstNode))));
		}
		return edges;
	}

	public List<Edge<S, T>> inEdgesForNode(S node) {
		final Set<S> srcNodes = this.backwardReferences.get(node);
		final List<Edge<S, T>> edges = new ArrayList<Edge<S, T>>();
		for (S srcNode : srcNodes) {
			edges.add(new Edge<S, T>(srcNode, node, weights.get(new NodePair<S>(srcNode, node))));
		}
		return edges;
	}
	
	public List<Edge<S, T>> getEdges() {
		final List<Edge<S, T>> edges = new ArrayList<Edge<S, T>>();
		for (final Map.Entry<NodePair<S>, T> entry : this.weights.entrySet()) {
			final NodePair<S> nodePair = entry.getKey();
			edges.add(new Edge<S, T>(nodePair.src, nodePair.dst, entry.getValue()));
		}
		return edges;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (S node : nodes) {
			sb.append(node.toString() + "\n");
		}
		for (Map.Entry<NodePair<S>, T> entry: this.weights.entrySet()) {
			NodePair<S> key = entry.getKey();
			sb.append(key.src + " -> " + key.dst + ", " + entry.getValue() + "\n");
		}
		return sb.toString();
	}
}
