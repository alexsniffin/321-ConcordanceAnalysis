package project.con1;

import project.WordObject;

/**
 * AVL Tree Map class for extracting and implementing identifies
 * 
 * @author Alexander Sniffin, Julian Wyatt
 * @lastedited 3/19/15
 */
public class AVLTreeMap<Key extends Comparable<? super Key>, AnyType> 
extends AVLTree<AnyType> 
implements MapInterface<Key, AnyType>
{
	private int totalKeys;
	private AVLTreeSet<Key> set;
	
	/**
	 * Sets root to null and totalKeys to 0
	 */
	public AVLTreeMap()
	{
		super();
		totalKeys = 0;
		set = new AVLTreeSet<Key>();
	}

	@Override
	public void put(Key key, WordObject<AVLTreeSet<Integer>> wordData) {
		if(key == null || wordData == null) {
			throw new IllegalArgumentException();
		}
		insert(key, wordData);
	}

	@Override
	public AnyType get(Key key) {
		return get((AvlMapNode)root, key);
	}

	/**
	 * Gets the node from the tree depending on the key
	 * 
	 * @param t node element
	 * @param key Key to find
	 * @return pointer to the node
	 */
	private AnyType get(AvlMapNode t, Key key) {
	    if(t == null) {
	      return null;
	    }

	    int compare = key.compareTo(t.key);
	    
	    if (compare == 0)
	      return t.element;
	    else if (compare < 0)
	      return get((AvlMapNode)t.left, key);
	    else
	      return get((AvlMapNode)t.right, key);
	}
	
	/**
	 * Inserts into the tree map, duplicates are ignored
	 * 
	 * @param key Key value
	 * @param value The type of object being stored
	 */
	public void insert(Key key, WordObject<AVLTreeSet<Integer>> value) {
		root = insert(key, value, (AvlMapNode) root);
		set.add(key);
	}
	
	/**
	 * Inserts and checks whether a key already exists
	 * 
	 * @param key
	 * @param value
	 * @param t
	 * @return
	 */
	private AvlMapNode insert(Key key, WordObject<AVLTreeSet<Integer>> value, AvlMapNode t) {
		if (t == null) {
		    totalKeys++;
			return new AvlMapNode(key, (AnyType) value, null, null);
		}
		
		int compareResult = key.compareTo(t.key);
		 
		if (compareResult < 0)
		    t.left = insert(key, value, (AvlMapNode) t.left);
		else if (compareResult > 0)
		    t.right = insert(key, value, (AvlMapNode) t.right);
		
		return (AvlMapNode) balance(t);
	}

	@Override
	public int size() {
		return totalKeys;
	}
	
	public AVLTreeSet<Key> entrySet() {
		return set;
	}
	
	/**
	 * Map Node class for map objects within the tree
	 * 
	 * @author Alexander
	 * @lastedited 3/19/15
	 */
	private class AvlMapNode extends AvlNode<AnyType> {

		protected Key key;
		
		public AvlMapNode(Key key, AnyType theElement) {
			super(theElement);
			this.key = key;
		}
		
		public AvlMapNode(Key key, AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
			super(theElement, lt, rt);
			this.key = key;
		}
	}
}
