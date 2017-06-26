import java.util.Arrays;
import java.util.function.Consumer;

public class TestMain {
	public static void main(String[] args) {
		final FreeGroupGenerator x = FreeGroupGenerator.make('x');
		final FreeGroupGenerator xInverse = x.inverse();
		
		// final FreeGroupElement imagea = new FreeGroupElement(Arrays.asList(x));
		// final FreeGroupElement imageb = new FreeGroupElement(Arrays.asList(xInverse, xInverse));

		final FreeGroupElement imagea = new FreeGroupElement(Arrays.asList(x, x));
		final FreeGroupElement imageb = new FreeGroupElement(Arrays.asList(xInverse, xInverse, xInverse));

		final Homomorphism morphism = new Homomorphism(Arrays.asList(
			new Homomorphism.Entry('b', imageb),
			new Homomorphism.Entry('a', imagea)
		));
		System.out.println(morphism);
		System.out.println();
		WeightedDigraph<String, StringFggPair> graph = FunctionUtil.mAutomatonForMorphism(morphism);
		System.out.println();
		System.out.println(graph);
		System.out.println();
		
		System.out.println("non inverse -> inverse:");
		FunctionUtil.iterateCompatibleEdges(graph,
				new Consumer<Pair<WeightedDigraph.Edge<String, StringFggPair>, WeightedDigraph.Edge<String, StringFggPair>>>() {
					@Override
					public void accept(Pair<WeightedDigraph.Edge<String, StringFggPair>, WeightedDigraph.Edge<String, StringFggPair>> t) {
						final WeightedDigraph.Edge<String, StringFggPair> edge = t.getFirst();
						final WeightedDigraph.Edge<String, StringFggPair> edgeInverse = t.getSecond();
						String str = edge.src + ":" + edgeInverse.dst + " -> " + edge.weight.string + "(" + edge.dst + ":" + edgeInverse.src + ")" + edgeInverse.weight.string;
						System.out.println(str);
					}
				}
			);

		System.out.println("inverse -> non inverse:");
		FunctionUtil.iterateCompatibleEdges(graph,
				new Consumer<Pair<WeightedDigraph.Edge<String, StringFggPair>, WeightedDigraph.Edge<String, StringFggPair>>>() {
					@Override
					public void accept(Pair<WeightedDigraph.Edge<String, StringFggPair>, WeightedDigraph.Edge<String, StringFggPair>> t) {
						final WeightedDigraph.Edge<String, StringFggPair> edge = t.getFirst();
						final WeightedDigraph.Edge<String, StringFggPair> edgeInverse = t.getSecond();
						String str = edgeInverse.src + ":" + edge.dst + " -> " + edgeInverse.weight.string + "(" + edgeInverse.dst + ":" + edge.src + ")" + edge.weight.string;
						System.out.println(str);
					}
				}
			);

	}
}
