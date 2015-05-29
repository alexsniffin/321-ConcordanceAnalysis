package project;

import java.awt.Dimension;

import project.GUI.ConcordanceGUI;

/**
 * Initiates the GUI
 * 
 * @author Alexander Sniffin, Julian Wyatt
 * @lastedited 3/19/15
 */
public class Driver {
	
	private static ConcordanceGUI gui;

	/**
	 * Initiates and creates the GUI
	 * @param args command line arguements
	 */
	public static void main(String[] args)
	{
	
		gui = new ConcordanceGUI("Concordance Project", new Dimension(500, 400));
		
	}

}
