
public class Pair<S, T> {

	private final S s;
	private final T t;
	
	public Pair(S s, T t) {
		this.s = s;
		this.t = t;
	}
	
	public S getFirst() {
		return this.s;
	}
	
	public T getSecond() {
		return this.t;
	}
}
