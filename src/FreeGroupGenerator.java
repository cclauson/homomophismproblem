
public class FreeGroupGenerator {
	private boolean isInverse;
	private char c;
	
	private FreeGroupGenerator(char c, boolean isInverse) {
		if (!Character.isAlphabetic(c)) {
			// enforce this for readability
			throw new IllegalArgumentException("c must be alphabetic");
		}
		this.c = c;
		this.isInverse = isInverse;
	}

	public static FreeGroupGenerator make(char c) {
		return new FreeGroupGenerator(c, false);
	}

	public static FreeGroupGenerator makeInverse(char c) {
		return new FreeGroupGenerator(c, true);
	}
	
	@Override
	public int hashCode() {
		int hashCode = Character.hashCode(c);
		if (isInverse) {
			hashCode = ~hashCode;
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return Character.toString(c) + (isInverse? "'" : "");
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof FreeGroupGenerator)) return false;
		final FreeGroupGenerator fgg = (FreeGroupGenerator) o;
		return this.isInverse == fgg.isInverse && this.c == fgg.c;
	}
	
	public FreeGroupGenerator inverse() {
		return new FreeGroupGenerator(c, !isInverse);
	}
	
	public boolean isInverseOf(FreeGroupGenerator fgg) {
		return this.c == fgg.c && this.isInverse != fgg.isInverse;
	}
	
	public boolean isInverse() {
		return this.isInverse;
	}
}

