package project.con1;

import java.util.Iterator;

/**
 * Set interface class
 * 
 * @author Alexander
 *
 * @param <V>
 */
public interface SetInterface<V> {

	/**
	 * Adds a value into the set, if it's a duplicate
	 * it does nothing.
	 * 
	 * @param value Value to add
	 */
	public void add(V value);
	
	/**
	 * Iterate over all the values in the set
	 * 
	 * @return Iterator object
	 */
	public Iterator<V> iterator();
	
}
