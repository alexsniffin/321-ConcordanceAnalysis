package project.con1;

import java.util.Iterator;

import project.con1.AVLTree.AvlNode;


/**
 * AVL Tree Set iterator
 * 
 * @author Alexander Sniffin
 */
final class AVLTreeIterator<AnyType> implements Iterator<AnyType>
{
    private AvlNode<AnyType> root;
    private AvlNode<AnyType> last;
    private AvlNode<AnyType> next = null;
    private boolean initial = true;


    public AVLTreeIterator( AvlNode<AnyType> root )
    {
        this.root = root;
        find_next();
    }


    @Override
    public boolean hasNext()
    {
        return next != null || ( initial && root != null );
    }


    @Override
    public AnyType next()
    {
        AnyType value = next == null ? null : next.element;
        find_next();
        return value;
    }


    public void find_next()
    {
        if ( next == null )
        {
            if ( root == null || !initial )
                return;
            initial = false;
            next = root;
            while ( next.left != null ) {
            	last = next;
                next = next.left;
                next.parent = last;
            }
        }
        else
        {
            if ( next.right != null )
            {
            	last = next;
                next = next.right;
                next.parent = last;
                while ( next.left != null ) {
                	last = next;
                    next = next.left;
                    next.parent = last;
                }
            }
            else
            {
                AvlNode<AnyType> parent = next.parent;

                while ( parent != null && parent.left != next )
                {
                    next = parent;
                    parent = next.parent;
                }

                next = parent;
            }
        }
    }


    @Override
    public void remove(){}
}
