
public class StringFggPair {
	
	public String string;
	public FreeGroupGenerator fgg;
	
	public StringFggPair(String string, FreeGroupGenerator fgg) {
		if (string == null) {
			throw new IllegalArgumentException("string is null");
		}
		if (fgg == null) {
			throw new IllegalArgumentException("fgg is null");
		}
		this.string = string;
		this.fgg = fgg;
	}
	
	@Override
	public String toString() {
		return "(" + (string.isEmpty() ? "[empty string]" : string) + ", " + fgg + ")";
	}
}
