package tree;

/**
 * <p>
 * The {@code BSTNode} represents a generic 
 * data structure bounded representing a single
 * node in the {@code BinarySearchTree} object 
 * that can contain objects bounded by the 
 * {@code Comparable} interface. It contains
 * pointers to left, right and the parent
 * nodes and methods to set and get the data
 * stored in the {@code} BSTNode object.
 * 
 * @author Arnav Singhania
 *
 * @param <E> A generic placeholder for a object
 * 		      bounded by the {@code Comparable} interface.
 */
public class BSTNode<E extends Comparable<E>> {

	/**
	 * {@code BSTNode} object that
	 * points to the parent.
	 */
	protected BSTNode<E> parent; 
	/**
	 * {@code BSTNode} object that
	 * points to the left node.
	 */
	protected BSTNode<E> left;
	/**
	 * {@code BSTNode} object that
	 * points to the right node.
	 */
	protected BSTNode<E> right;
	private E data;
	
	/**
	 * Instantiates a {@code BSTNode} object
	 * setting its {@code data} variable to the
	 * input data.
	 * 
	 * @param data A generic value to be stored
	 * 			   in the {@code BSTNode} object.
	 */
	public BSTNode(E data) {
		this.data = data;
	}
	
	/**
	 * Finds and returns the data stored
	 * in the {@code BSTNode} object.
	 * 
	 * @return The data stored
	 * 		   in the {@code BSTNode} object.
	 */
	public E getData() {
		return data;
	}
	
	/**
	 * Sets the data in the {@code BSTNode}
	 * object to the input data.
	 * 
	 * @param data A generic value to be stored
	 * 			   in the {@code BSTNode} object.
	 */
	public void setData(E data) {
		this.data = data;
	}
	
}
