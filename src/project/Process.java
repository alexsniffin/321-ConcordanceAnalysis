package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import project.WordObject;
import project.GUI.ConcordanceGUI;
import project.con1.AVLTreeMap;
import project.con1.AVLTreeSet;
import project.con2.HashData;

/**
 * Reads in the file and determines which concordance to use
 * 
 * @author Alexander Sniffin, Julian Wyatt
 * @lastedited 3/19/15
 */
public class Process
{

	/**
	 * Tree Map pointer
	 */
	private AVLTreeMap<String, WordObject<AVLTreeSet<Integer>>> treeMap;
	
	/**
	 * HashData pointer
	 */
	private HashData hashData;
	
	/**
	 * GUI pointer
	 */
	private ConcordanceGUI gui;
	
	/**
	 * Filer reader object
	 */
	private FileReader file;
	
	/**
	 * true: concordance 1
	 * false: concordance 2
	 */
	private boolean type;
	
	/**
	 * Start and end long values to keep track of input time
	 */
	private long start, end;
	
	public Process(ConcordanceGUI gui, FileReader file, boolean type) {
		this.gui = gui;
		this.file = file;
		this.type = type;
		
		if (type)
			treeMap = new AVLTreeMap<String, WordObject<AVLTreeSet<Integer>>>();
		else if (!type)
			hashData = new HashData();
	}
	
	/**
	 * The read method reads the input files, parses each line
	 * into tokens, and then inserts each token(as an identifier/key) 
	 * into a map. The type of data structure is dependent on the type
	 * of variable. If the same token already exists, it will increase
	 * the occurrence and add the line to a set. The time it takes
	 * to perform this method is recorded inorder to compare the
	 * two different data structures complexity.
	 */
	public void read() {
		try (BufferedReader b = new BufferedReader(file)) {
			start = System.nanoTime();
			String line;
			int lineNum = 0;
			while ((line = b.readLine()) != null)
			{
				//Read and split each line by anything that isn't a letter or number
				String[] tokens = line.trim().split("\\P{Alpha}+");
				
				for (int i = 0; i < tokens.length; i++) {
					if (!tokens[i].equals(""))
						//Concordance 1
						if (type) {
							if (treeMap.get(tokens[i]) == null) {
								WordObject<AVLTreeSet<Integer>> wordData = new WordObject<AVLTreeSet<Integer>>(1, new AVLTreeSet<Integer>());
								wordData.getSet().add(lineNum);
								treeMap.put(tokens[i], wordData);
							} else {
								treeMap.get(tokens[i]).setOccurrences(treeMap.get(tokens[i]).getOccurrences() + 1);
								treeMap.get(tokens[i]).getSet().add(lineNum);
							}
						//Concordance 2
						} else if (!type) {
							if (!hashData.getMap().containsKey(tokens[i])) {
								WordObject<HashSet<Integer>> wordData = new WordObject<HashSet<Integer>>(1, new HashSet<Integer>());
								wordData.getSet().add(lineNum);
								hashData.getMap().put(tokens[i], wordData);
							} else {
								hashData.getMap().get(tokens[i]).setOccurrences(hashData.getMap().get(tokens[i]).getOccurrences() + 1);
								hashData.getMap().get(tokens[i]).getSet().add(lineNum);
							}
						}
				}
				lineNum++;
			}
			end = System.nanoTime();
			gui.editTextArea("Finished reading file successfully!");
			gui.editTextArea("All identifiers added into map in " + ((double) (end - start) / 1000000000) + " seconds.");
			
			//Disable menu options
			gui.getLoad1().setEnabled(false);
			gui.getLoad2().setEnabled(false);
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public AVLTreeMap<String, WordObject<AVLTreeSet<Integer>>> getTreeMap() {
		return treeMap;
	}


	public HashData getHashMap() {
		return hashData;
	}

}
