import java.util.*;

public class FreeGroupElement {
	
	private final List<FreeGroupGenerator> data;
	
	public FreeGroupElement(Iterable<FreeGroupGenerator> data) {
		if (data == null) {
			throw new IllegalArgumentException("data is null");
		}
		final List<FreeGroupGenerator> dataTmp = new ArrayList<FreeGroupGenerator>();
		for (final FreeGroupGenerator fgg : data) {
			if (fgg == null) {
				throw new IllegalArgumentException("found null free group element");
			}
			final FreeGroupGenerator fggLast = dataTmp.isEmpty() 
					? null : dataTmp.get(dataTmp.size() - 1);
			if (fggLast != null && fggLast.isInverseOf(fgg)) {
				dataTmp.remove(dataTmp.size() - 1);
			} else {
				dataTmp.add(fgg);
			}
		}
		this.data = Collections.unmodifiableList(dataTmp);
	}
	
	// returns unmodifiable list
	public List<FreeGroupGenerator> getNormalGeneratorSequence() {
		return data;
	}
	
	@Override
	public int hashCode() {
		return data.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof FreeGroupElement)) {
			return false;
		}
		final FreeGroupElement fge = (FreeGroupElement) o;
		return this.data.equals(fge.data);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (FreeGroupGenerator fgg : data) {
			sb.append(fgg.toString());
		}
		return sb.toString();
	}
}
