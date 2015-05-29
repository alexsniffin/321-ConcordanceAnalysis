package project.con1;

import project.WordObject;

/**
 * Interface for the Avl Tree Map
 * 
 * @author Alexander
 *
 * @param <K> Key
 * @param <V> Value
 */
public interface MapInterface<K, V> {

	/**
	 * Inserts a value into the tree map
	 * 
	 * @param key The key in which the node can be found with
	 * @param value The value of the node
	 */
	public void put(K key, WordObject<AVLTreeSet<Integer>> value);
	
	/**
	 * Gets the value of the node
	 * 
	 * @param key The key in which the node is connected with
	 */
	public V get(K key);
	
	/**
	 * Checks if the map is empty or not
	 * 
	 * @return true if empty
	 */
	public boolean isEmpty();
	
	/**
	 * The number of keys in the tree map
	 * 
	 * @return Total keys
	 */
	public int size();
	
}
