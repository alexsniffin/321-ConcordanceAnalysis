package project.con1;

import java.util.Iterator;

/**
 * AVL Tree Set class for extracting and implementing identifies
 *
 * @author Alexander Sniffin, Julian Wyatt
 */
public class AVLTreeSet<AnyType> extends AVLTree<AnyType> implements SetInterface<AnyType> {
	
	public AVLTreeSet() {
		root = null;
	}
	
	public final Iterator<AnyType> iterator() {
		return new AVLTreeIterator<AnyType>(root);
	}

	@Override
	public void add(AnyType value) {
		if (!contains(value))
			insert(value);
	}
	
}

