package project;

/**
 * Word Object class that holds the number of occurrences (n),
 * and the lines in which a string occurs at
 * 
 * @author Alexander
 */
public class WordObject<SetType> {

	/**
	 * N number of occurrences a string appears
	 */
	private int n;
	
	/**
	 * Lines in which the string appears (Stored as Tree or Hash Set)
	 */
	private SetType lines;
	
	/**
	 * Word Object
	 * 
	 * @param n
	 * @param lines
	 */
	public WordObject(int n, SetType lines) {
		this.n = n;
		this.lines = lines;
	}

	public int getOccurrences() {
		return n;
	}

	public void setOccurrences(int n) {
		this.n = n;
	}

	public SetType getSet() {
		return lines;
	}
	
}
