import java.util.*;

// specifically, a morphism from a free monoid into a free group
public class Homomorphism {

	public static class Entry {
		public final char c;
		public final FreeGroupElement val;
		
		public Entry(char c, FreeGroupElement val) {
			if (val == null) {
				throw new RuntimeException("val is null");
			}
			this.c = c;
			this.val = val;
		}
	}
	
	private final List<Entry> entries;
	
	public Homomorphism(List<Entry> data) {
		if (data == null) {
			throw new IllegalArgumentException("data is null");
		}
		if (data.isEmpty()) {
			throw new IllegalArgumentException("data is empty");
		}
		final Set<Character> charsVisited = new HashSet<Character>();
		for (final Entry entry : data) {
			if (entry == null) {
				throw new IllegalArgumentException("found null entry");
			}
			if (entry.val == null) {
				throw new IllegalArgumentException("found null free group element");
			}
			if (charsVisited.contains(entry.c)) {
				throw new IllegalArgumentException("character " + entry.c + " mapped twice");
			}
		}
		this.entries = Collections.unmodifiableList(new ArrayList<Entry>(data));
	}
	
	public List<Entry> getEntries() {
		return this.entries;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final Entry entry : this.entries) {
			sb.append(entry.c + " : " + entry.val + "\n");
		}
		return sb.toString();
	}
}
