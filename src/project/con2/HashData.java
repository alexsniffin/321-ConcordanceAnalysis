package project.con2;

import java.util.HashMap;
import java.util.HashSet;

import project.WordObject;

/**
 * Includes a HashMap for extracting 
 * and implementation of concordance 2
 * 
 * @author Alexander Sniffin, Julian Wyatt
 */
public class HashData {

	/**
	 * Map data for Identifiers and WordObjects
	 */
	private HashMap<String, WordObject<HashSet<Integer>>> map;
	
	/**
	 * Identifier: String
	 * Occurrences and lines: WordObject<Integer> (Lines: Integer)
	 */
	public HashData() {
		map = new HashMap<String, WordObject<HashSet<Integer>>>();
	}

	/**
	 * Get the Concordance 2 Map Data
	 * 
	 * @return pointer for map
	 */
	public HashMap<String, WordObject<HashSet<Integer>>> getMap() {
		return map;
	}
	
}
