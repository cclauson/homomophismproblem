import java.util.*;
import java.util.function.Consumer;

public class FunctionUtil {
	// can't instantiate
	private FunctionUtil() {}
	

	/*
	public static <T> WeightedDigraph<T, LinearRHS<T>> findLinearRewriteRules(WeightedDigraph<T, StringFggPair> graphIn) {
		
		final WeightedDigraph<T, LinearRHS<T>> graphOut = new WeightedDigraph<T, LinearRHS<T>>();
		
		for (final T node : graphIn.nodeSet()) {
			graphOut.addNode(node);
		}
		
		final HashMultiMap<FreeGroupGenerator, WeightedDigraph.Edge<T, StringFggPair>> edgesByFggs =
				new HashMultiMap<FreeGroupGenerator, WeightedDigraph.Edge<T, StringFggPair>>();
		final Set<FreeGroupGenerator> nonInverseGenerators = new HashSet<FreeGroupGenerator>();
		
		for (final WeightedDigraph.Edge<T, StringFggPair> edge : graphIn.getEdges()) {
			final FreeGroupGenerator fgg = edge.weight.fgg;
			edgesByFggs.put(fgg, edge);
			if (!fgg.isInverse())
				nonInverseGenerators.add(fgg);
		}
		
		for (final FreeGroupGenerator fgg : nonInverseGenerators) {
			final FreeGroupGenerator fggInverse = fgg.inverse();
			final Set<WeightedDigraph.Edge<T, StringFggPair>> edges = edgesByFggs.get(fgg);
			final Set<WeightedDigraph.Edge<T, StringFggPair>> edgesInverse = edgesByFggs.get(fggInverse);
			for (WeightedDigraph.Edge<T, StringFggPair> edge : edges) {
				for (WeightedDigraph.Edge<T, StringFggPair> edgeInverse : edgesInverse) {
					// TODO: Code here
				}
			}
		}
		
		// TODO: Finish this
		return graphOut;
		
		iterateCompatibleEdges(graphIn,
			new Consumer<Pair<WeightedDigraph.Edge<T, StringFggPair>, WeightedDigraph.Edge<T, StringFggPair>>>() {
				@Override
				public void accept(Pair<WeightedDigraph.Edge<T, StringFggPair>, WeightedDigraph.Edge<T, StringFggPair>> t) {
					// TODO Auto-generated method stub
					
				}
			}
		);
		
	}
		*/

	public static <T> void iterateCompatibleEdges(WeightedDigraph<T, StringFggPair> graphIn,
			Consumer<Pair<WeightedDigraph.Edge<T, StringFggPair>,
			WeightedDigraph.Edge<T, StringFggPair>>> edgePairHandler) {
		
		final WeightedDigraph<T, LinearRHS<T>> graphOut = new WeightedDigraph<T, LinearRHS<T>>();
		
		for (final T node : graphIn.nodeSet()) {
			graphOut.addNode(node);
		}
		
		final HashMultiMap<FreeGroupGenerator, WeightedDigraph.Edge<T, StringFggPair>> edgesByFggs =
				new HashMultiMap<FreeGroupGenerator, WeightedDigraph.Edge<T, StringFggPair>>();
		final Set<FreeGroupGenerator> nonInverseGenerators = new HashSet<FreeGroupGenerator>();
		
		for (final WeightedDigraph.Edge<T, StringFggPair> edge : graphIn.getEdges()) {
			final FreeGroupGenerator fgg = edge.weight.fgg;
			edgesByFggs.put(fgg, edge);
			if (!fgg.isInverse())
				nonInverseGenerators.add(fgg);
		}
		
		for (final FreeGroupGenerator fgg : nonInverseGenerators) {
			final FreeGroupGenerator fggInverse = fgg.inverse();
			final Set<WeightedDigraph.Edge<T, StringFggPair>> edges = edgesByFggs.get(fgg);
			final Set<WeightedDigraph.Edge<T, StringFggPair>> edgesInverse = edgesByFggs.get(fggInverse);
			for (final WeightedDigraph.Edge<T, StringFggPair> edge : edges) {
				for (final WeightedDigraph.Edge<T, StringFggPair> edgeInverse : edgesInverse) {
					edgePairHandler.accept(new Pair<WeightedDigraph.Edge<T, StringFggPair>,
							WeightedDigraph.Edge<T, StringFggPair>>(edge, edgeInverse));
				}
			}
		}
	}

	public static WeightedDigraph<String, StringFggPair> mAutomatonForMorphism(Homomorphism morphism) {
		final String nodeStart = "q0";
		int inodeCur = 1;
		final WeightedDigraph<String, StringFggPair> graph = new WeightedDigraph<String, StringFggPair>();
		graph.addNode(nodeStart);
		for (final Homomorphism.Entry entry : morphism.getEntries()) {
			final List<FreeGroupGenerator> fggs = entry.val.getNormalGeneratorSequence();
			// let's not handle mapping to the identity for now
			if (fggs.isEmpty()) {
				throw new RuntimeException("morphisms that map to identity currently not handled");
			}
			String nodeLast = nodeStart;
			for (int i = 0; i < fggs.size() - 1; ++i) {
				String nodeNew = "q" + inodeCur;
				++inodeCur;
				graph.addNode(nodeNew);
				graph.addEdgeWithWeightOrSetWeight(nodeLast, nodeNew, new StringFggPair("", fggs.get(i)));
				nodeLast = nodeNew;
			}
			graph.addEdgeWithWeightOrSetWeight(nodeLast, nodeStart,
					new StringFggPair(Character.toString(entry.c), fggs.get(fggs.size() - 1)));
		}
		return graph;
	}
}
