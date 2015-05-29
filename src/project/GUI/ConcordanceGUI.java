package project.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import project.Process;
import project.WordObject;
import project.con1.AVLTreeSet;

/**
 * GUI for utilizing the Concordance program
 * 
 * @author Alexander Sniffin, Julian Wyatt
 * @lastedited 3/19/15
 */
public class ConcordanceGUI extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	
	private Process input;
	private FileReader location;
	private File txtFile;
	private JFileChooser fc;
	private JMenuBar menu;
	private JMenu file, edit, help;
	private JMenuItem load1, load2, exit, mapSize, search, printAll, about;
	private JTextArea text;

	/**
	 * Sets up the different user interface options
	 * 
	 * @param title of client
	 * @param size of client
	 */
	public ConcordanceGUI(String title, Dimension size)
	{
		this.setTitle(title);
		this.setPreferredSize(size);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setJMenuBar(createMenu());
		
		fc = new JFileChooser();
        fc.addChoosableFileFilter(new Filter());
        fc.setAcceptAllFileFilterUsed(false);
		
		text = new JTextArea("Select a menu option to begin...\n");
		text.setEditable(false);
		text.setLineWrap(true);
		
		JScrollPane sp = new JScrollPane(text);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(sp, BorderLayout.CENTER);
		
		this.pack();
		this.setResizable(true);
		this.setVisible(true);
	}

	private JMenuBar createMenu() {
		menu = new JMenuBar();
		file = new JMenu("File");
		edit = new JMenu("Edit");
		help = new JMenu("Help");
		file.setMnemonic(KeyEvent.VK_F);
		edit.setMnemonic(KeyEvent.VK_E);
		help.setMnemonic(KeyEvent.VK_H);
		menu.add(file);
		menu.add(edit);
		menu.add(help);
		
		load1 = new JMenuItem("Load as Concor1", KeyEvent.VK_L);
		load2 = new JMenuItem("Load as Concor2", KeyEvent.VK_O);
		exit = new JMenuItem("Exit", KeyEvent.VK_X);
		mapSize = new JMenuItem("Directory Size", KeyEvent.VK_D);
		search = new JMenuItem("Search for Identifier", KeyEvent.VK_S);
		printAll = new JMenuItem("Print All", KeyEvent.VK_P);
		about = new JMenuItem("About", KeyEvent.VK_A);
		
		load1.setActionCommand("con1");
		load2.setActionCommand("con2");
		exit.setActionCommand("exit");
		mapSize.setActionCommand("size");
		search.setActionCommand("search");
		printAll.setActionCommand("print");
		about.setActionCommand("about");
		
		load1.addActionListener(this);
		load2.addActionListener(this);
		exit.addActionListener(this);
		mapSize.addActionListener(this);
		search.addActionListener(this);
		printAll.addActionListener(this);
		about.addActionListener(this);
		
		file.add(load1);
		file.add(load2);
		file.addSeparator();
		file.add(exit);
		
		edit.add(mapSize);
		edit.add(search);
		edit.add(printAll);
		
		help.add(about);
		return menu;
	}

	/**
	 * Handle all of the different menu actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println("Menu action: " + action);
		
		switch (action) {
			case "con1":
				try {
					location = new FileReader(getFile());
				} catch (FileNotFoundException e1) {
					System.err.println("File not found");
				}
				
				input = new Process(this, location, true);
				input.read();
				
				this.setTitle(this.getTitle() + " - Concordance 1: AVLTree");
				break;
				
			case "con2":
				try {
					location = new FileReader(getFile());
				} catch (FileNotFoundException e1) {
					System.err.println("File not found");
				}
				
				input = new Process(this, location, false);
				input.read();
				
				this.setTitle(this.getTitle() + " - Concordance 2: Hash");
				break;
				
			case "exit":
				System.exit(0);
				break;
				
			case "size":
				if (input == null)
					JOptionPane.showMessageDialog(this, "No values within Map Directorys!", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					if (input.getHashMap() != null)
						editTextArea("Size of HashMap directory is: " + input.getHashMap().getMap().size());
					if (input.getTreeMap() != null)
						editTextArea("Size of AVLTreeMap directory is: " + input.getTreeMap().size());
				}
				break;
				
			case "search":
				if (input == null)
					JOptionPane.showMessageDialog(this, "No values within Map Directorys!", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					String search = getSearchS();
					
					if (input.getHashMap() != null)
						searchHash(search);
					else if (input.getTreeMap() != null)
						searchTree(search);
				}
				break;
				
			case "print":
				try {
					PrintWriter outFile = new PrintWriter(new FileWriter(txtFile.getName() + "_output.txt"));
					
					outFile.println();
					outFile.println("/---------------- MAP DATA ----------------/"); 
					outFile.println();
					
					if (input.getHashMap() != null)
						printHash(outFile);
					else if (input.getTreeMap() != null)
						printTree(outFile);
					
					outFile.close();
					editTextArea("Print of the entire map has been outputed to " + txtFile.getName() + "_output.txt" + " successfully!");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
				
			case "about":
				JOptionPane.showMessageDialog(this, 
						"Concordance program, loads in .txt data into a map\n"
						+ "and stores each string with the number of times it\n"
						+ "occurs and which line number(s) it shows up at.\n\n"
						+ "Created by Alexander Sniffin, and Julian Wyatt", "About",
						JOptionPane.PLAIN_MESSAGE);
				break;
		}
	}
	
	/**
	 * Searches the HashMap with the inputed Identifier,
	 * and displays the occurrences and line numbers.
	 * 
	 * @param search inputed identifier
	 */
	public void searchHash(String search) {
		if (input.getHashMap().getMap().containsKey(search)) {
			String output = "Search sucessfully found for '" + search + "'. "
					+ "Occurances: " + input.getHashMap().getMap().get(search).getOccurrences() + ". Lines: [";
			Iterator<Integer> i = input.getHashMap().getMap().get(search).getSet().iterator();
			
			while (i.hasNext()) {
	            output += i.next();
	            if (i.hasNext())
	            	output += ", ";
	        }
			editTextArea(output + "]");
		} else {
			editTextArea("Search identifier not found!");
		}
	}
	
	/**
	 * Searches the AVLTreeMap with the inputed Identifier,
	 * and displays the occurrences and line numbers.
	 * 
	 * @param search inputed identifier
	 */
	public void searchTree(String search) {
		if (input.getTreeMap().get(search) != null) {
			String output = "Search sucessfully found for '" + search + "'. "
					+ "Occurances: " + input.getTreeMap().get(search).getOccurrences() + ". Lines: [";
			Iterator<Integer> i = input.getTreeMap().get(search).getSet().iterator();
			
			while (i.hasNext()) {
	            output += i.next();
	            if (i.hasNext())
	            	output += ", ";
	        }
			editTextArea(output + "]");
		} else {
			editTextArea("Search identifier not found!");
		}
	}
	
	/**
	 * Prints the entire Tree Map to an output file
	 * 
	 * @param outFile File to print too
	 */
	public void printTree(PrintWriter outFile) {
		Iterator<String> map = input.getTreeMap().entrySet().iterator();
		
		while (map.hasNext()) {
			String output = "";
			String id = map.next();
			output += "Identifier '" + id + "' found. Occurrences = " + input.getTreeMap().get(id).getOccurrences() + ". Lines = [ ";
			Iterator<Integer> i = input.getTreeMap().get(id).getSet().iterator();
			
			while (i.hasNext()) {
	            output += i.next();
	            if (i.hasNext())
	            	output += ", ";
	        }
			
			output += " ]";
			outFile.println(output);
			outFile.println();
		}
	}
	
	/**
	 * Prints the entire Hash Map to an output file
	 * 
	 * @param outFile File to print too
	 */
	public void printHash(PrintWriter outFile) {
		Iterator<Entry<String, WordObject<HashSet<Integer>>>> map = input.getHashMap().getMap().entrySet().iterator();
		
		while (map.hasNext()) {
			String output = "";
			String id = map.next().getKey();
			output += "Identifier '" + id + "' found. Occurrences = " + input.getHashMap().getMap().get(id).getOccurrences() + ". Lines = [ ";
			Iterator<Integer> i = input.getHashMap().getMap().get(id).getSet().iterator();
			
			while (i.hasNext()) {
	            output += i.next();
	            if (i.hasNext())
	            	output += ", ";
	        }
			
			output += " ]";
			outFile.println(output);
			outFile.println();
		}
	}
	
	public JMenuItem getLoad1() {
		return load1;
	}

	public JMenuItem getLoad2() {
		return load2;
	}

	/**
	 * Get file location
	 * 
	 * @return file location
	 */
	public File getFile() {
		int loadVal = fc.showOpenDialog(null);

        if (loadVal == JFileChooser.APPROVE_OPTION) {
        	editTextArea("File "+fc.getName(fc.getSelectedFile())+" loaded successfully!");
        	txtFile = fc.getSelectedFile();
            return txtFile;
        }
        return null;
	}
	
	/**
	 * Edit text for the JTextArea
	 * 
	 * @param s string of JTextArea
	 */
	public void editTextArea(String s) {
		text.setText(text.getText() + s + "\n");
	}
	
	/**
	 * Get search String
	 * 
	 * @return String to search
	 */
	private String getSearchS() {
        return JOptionPane.showInputDialog(this, "Enter a String to search for.","Search", JOptionPane.PLAIN_MESSAGE);
    }
	
	/**
	 * Filter so load file is only .txt
	 * 
	 * @author Alexander
	 */
	public class Filter extends FileFilter {

	    public boolean accept(File f) {
	    	
	        if (f.isDirectory())
	            return true;

	        String extension = Utils.getExtension(f);
	        if (extension != null)
	            if (extension.equals(Utils.txt))
	                return true;
	            else
	                return false;

	        return false;
	    }

	    public String getDescription() {
	        return "ASCII Text file (.txt)";
	    }
	}
	
	/**
	 * Checks the extension of the file
	 * 
	 * @author Alexander
	 */
	public static class Utils {
		
	    public final static String txt = "txt";
	 
	    /*
	     * Get the extension of a file.
	     */
	    public static String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');
	 
	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
	}
}
