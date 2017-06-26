// right hand side of linear rewrite rule
// T is node type
public class LinearRHS<T> {
	public final String stringInitial;
	public final T nodeStart;
	public final T nodeEnd;
	public final String stringFinal;
	
	public LinearRHS(String stringInitial, T nodeStart, T nodeEnd, String stringFinal) {
		this.stringInitial = stringInitial;
		this.nodeStart = nodeStart;
		this.nodeEnd = nodeEnd;
		this.stringFinal = stringFinal;
	}
}
