package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import stack.LinkedStack;
import stack.Stack;

/**
 * <p>
 * The {@code BinarySearchTree} class represents a 
 * tree type hierarchical data structure, storing data
 * following a particular set of rules. It contains 
 * methods to insert new data, delete existing data,
 * find existing data, print the data using different 
 * traversal techniques and methods to find the uncle,
 * sibling and grandparent nodes of the input node.
 * 
 * @author Arnav Singhania
 *
 * @param <E> A generic placeholder for a object
 * 		      bounded by the {@code Comparable} interface.
 */
public class BinarySearchTree<E extends Comparable<E>> {
	
	protected BSTNode<E> root; /////change back to private
	
	/**
	 * Initializes an empty {@code BinarySearchTree} object.
	 */
	public BinarySearchTree() {}
	
	/**
	 * Initialize a {@code BinarySearchTree} object 
	 * using the values in the input array to populate the tree.
	 * 
	 * @param data An array containing objects bounded by the
	 * 			   {@code Comparable} interface.
	 * @throws IllegalArgumentException Throws an IllegalArgumentException	
	 * 									if the input key already exists in the tree.
	 */
	public BinarySearchTree(E[] data) throws IllegalArgumentException {
		
		for (int a = 0; a < data.length; a++) {
			try {
				this.insert(data[a]);
			} catch (IllegalArgumentException ex) {
				throw ex;
			}
		}
		
	}
	

	/**
	 * Adds a new value to the tree according to the rules of a Binary Search Tree.
	 * 
	 * @param key A generic object to be added.
	 * @throws IllegalArgumentException Throws an IllegalArgumentException	
	 * 									if the input key already exists in the tree.
	 */
	public void insert(E key) throws IllegalArgumentException {
		
		BSTNode<E> child = new BSTNode<E>(key);
		
		if (this.root == null) {
			this.root = child;
		} else {
			try {
				BSTNode<E> parent = insertionPoint(key);
				if (key.compareTo(parent.getData()) < 0) {
					parent.left = child;
					child.parent = parent;
				} else if (key.compareTo(parent.getData()) > 0) {
					parent.right = child;
					child.parent = parent;
				}
			} catch (IllegalArgumentException ex) {
				throw ex;
			}
			
		}

	}
	
	private BSTNode<E> insertionPoint(E key) throws IllegalArgumentException {
		
		BSTNode<E> current = root;
		BSTNode<E> parent = null;
		
		while (current!=null) {
			if (key == current.getData()) {
				throw new IllegalArgumentException();
			} else if (key.compareTo(current.getData()) < 0) {
				parent = current;
				current = current.left;
			} else if (key.compareTo(current.getData()) >0) {
				parent = current;
				current = current.right;
			}
		}
		
		return parent;
	}
	
	/**
	 * Takes a key and removes the node from the tree.
	 * 
	 * @param key A generic object to be removed.
	 */
	public void delete(E key) {

		delete(nodeToDelete(key));
	}
	
	
	private BSTNode<E> nodeToDelete(E key) {
		
		BSTNode<E> node = null;
		
		if (this.find(key)) {
			
			BSTNode<E> current = this.root;
			
			while (current!=null) {
				
				if (current.getData().compareTo(key) == 0) {
					node = current;
					break;
				} else if (current.getData().compareTo(key) < 0) {
					
					current = current.right;
					
				} else if (current.getData().compareTo(key) > 0) {
					
					current = current.left;
					
				}
				
			}
			
			
		} else {
			
			throw new IllegalArgumentException();
			
		}
		
		return node;
		
	}
	
	private void delete(BSTNode<E> node) {
		
		if (this.isLeaf(node)) {
			
			if (this.isLeftChild(node)) {
				node.parent.left = null;
			} else if (this.isRightChild(node)) {
				node.parent.right = null;
			} 
			
		} else if (this.numChildren(node) == 1) {
			BSTNode<E> child;
			if (node.left!=null) {child = node.left;}
			else {child = node.right;}
			
			if(this.isLeftChild(node)) {
				node.parent.left = child;
				child.parent = node.parent;
			} else if (this.isRightChild(node)) {
				node.parent.right = child;
				child.parent = node.parent;
			}
			
		} else if (this.numChildren(node) == 2) { 

			BSTNode<E> max = maxLeftSubTree(node);
			node.setData(max.getData());
			delete(max);
		}
		
		
	}
	
	private BSTNode<E> maxLeftSubTree(BSTNode<E> node) {
		
		BSTNode<E> max = node.left;
	
		BSTNode<E> current = node.left;

		while (current.right!=null) {
			
			max = current.right;
			current = current.right;
			
		}
		
		return max;
	}

	private int numChildren(BSTNode<E> node) {
		
		int count = 0;
		
		if (node.left!=null) count++;
		if (node.right!=null) count++;
		
		return count;
	}

	
	/**
	 * Looks for input object and returns true or false 
	 * depending on if the object is found in the tree or not.
	 * 
	 * @param key A generic object that needs to be looked up.
	 * @return A boolean value indicating whether the input object 
	 * 		   was found or not.
	 */
	public boolean find(E key) {
		
		BSTNode<E> current = root;
		
		while (current != null) {
			if (current.getData().equals(key)) {
				return true;
			} else if(key.compareTo(current.getData()) < 0) {
				current = current.left;
			} else if (key.compareTo(current.getData()) > 0) {
				current = current.right;
			}
		}
		return false;
	}

	/**
	 * Returns true or false if the tree is empty or not.
	 * 
	 * @return A boolean value indicating whether the tree is empty
	 * 		   or not.
	 */
	public boolean isEmpty() {
		return this.root == null;
	}


	/**
	 * Returns true or false if the node is a leaf or not.
	 * 
	 * @param node A generic {@code BSTNode} object.
	 * @return A boolean value indicating whether the input
	 * 		   node is a leaf or not.
	 */
	public boolean isLeaf(BSTNode<E> node) { 
		return (node.left == null && node.right == null);
	}
	
	/**
	 *  Returns true or false if the given node is a left child of its parent, or not.
	 *  
	 * @param node A generic {@code BSTNode} object.
	 * @return A boolean value indicating whether the input
	 * 		   node is a left child or not.
	 */
	public boolean isLeftChild(BSTNode<E> node) {
		if (this.root == node) {return false;}
		else {return node.parent.left == node;}
	}
	
	/**
	 *  Returns true or false if the given node is a right child of its parent, or not.
	 *  
	 * @param node A generic {@code BSTNode} object.
	 * @return A boolean value indicating whether the input
	 * 		   node is a right child or not.
	 */
	public boolean isRightChild(BSTNode<E> node) {
		if (this.root == node) {return false;}
		else {return node.parent.right == node;}
	}

	/**
	 * Returns the sibling node of the given node, 
	 * if they exists.
	 * 
	 * @param node A generic {@code BSTNode} object.
	 * @return The sibling node of type {@code BSTNode}
	 * 		   of the input node.
	 */
	public BSTNode<E> sibling(BSTNode<E> node) {
		if (node.parent == null) {
			return null;
		} else {
			if (this.isLeftChild(node)) {
				return node.parent.right;
			} else {
				return node.parent.left;
			}
		}
	}


	/**
	 * Returns the uncle node of the given node.
	 * 
	 * @param node A generic {@code BSTNode} object.
	 * @return The uncle node of type {@code BSTNode}
	 * 		   of the input node.
	 */
	public BSTNode<E> uncle(BSTNode<E> node) {
		if (node.parent == null || node.parent.parent == null) {
			return null;
		} else {
			return this.sibling(node.parent);
		}
	}
	
	/**
	 * Returns the grandparent node of the given node.
	 * 
	 * @param node A generic {@code BSTNode} object.
	 * @return The grandparent node of type {@code BSTNode}
	 * 		   of the input node.
	 */
	public BSTNode<E> grandparent(BSTNode<E> node) {
		if (node.parent == null || node.parent.parent == null) {
			return null;
		} else {
			return node.parent.parent;
		}
	}
	

	/**
	 * Returns an ArrayList of nodes generated using preorder traversal.
	 * 
	 * @return An {@code ArrayList} object of type {@code BSTNode}
	 * 		   containing nodes in preorder sequence.
	 */
	public ArrayList<BSTNode<E>> preorder() {
		
		ArrayList<BSTNode<E>> list = new ArrayList<>();
		
		if (this.isEmpty()) {
			throw new NullPointerException("Tree is empty");
		}
		
		Stack<BSTNode<E>> stack = new LinkedStack<BSTNode<E>>();
		stack.push(this.root);
		
		while(!stack.isEmpty()) {
			
			BSTNode<E> current = stack.pop();
			list.add(current);
			
			if(current.right!=null) {
				stack.push(current.right);
			}
			
			if(current.left!=null) {
				stack.push(current.left);
			}
			
		}
		return list;
	}
	
	/**
	 * Returns an ArrayList of nodes generated using inorder traversal.
	 * 
	 * @return An {@code ArrayList} object of type {@code BSTNode}
	 * 		   containing nodes in inorder sequence.
	 */
	public ArrayList<BSTNode<E>> inorder() {
		
		ArrayList<BSTNode<E>> list = new ArrayList<>();
		
		if (this.isEmpty()) {
			throw new NullPointerException("Tree is empty");
		}
		
		Stack<BSTNode<E>> stack = new LinkedStack<BSTNode<E>>();
		BSTNode<E> current = this.root;
		
		while(!stack.isEmpty()||current!=null) {
			
			if(current!=null) {
				stack.push(current);
				current = current.left;
			} else {
				current = stack.pop();
				list.add(current);
				current = current.right;
			}
			
		}
		return list;
	}
	
	/**
	 * Returns an ArrayList of nodes generated using postorder traversal.
	 * 
	 * @return An {@code ArrayList} object of type {@code BSTNode}
	 * 		   containing nodes in postorder sequence.
	 */
	public ArrayList<BSTNode<E>> postorder() {
		
		ArrayList<BSTNode<E>> list = new ArrayList<>();
		
		if (this.isEmpty()) {
			throw new NullPointerException("Tree is empty");
		}
		
		Stack<BSTNode<E>> stack1 = new LinkedStack<BSTNode<E>>();
		Stack<BSTNode<E>> stack2 = new LinkedStack<BSTNode<E>>();
		
		stack1.push(this.root);
		
		while(!stack1.isEmpty()) {
			
			BSTNode<E> current = stack1.pop();
			stack2.push(current);
			
			if(current.left!=null) {
				stack1.push(current.left);
			} 
			if(current.right!=null) {
				stack1.push(current.right);
			}
			
		}
		
		while (!stack2.isEmpty()) {
			BSTNode<E> current = stack2.pop();
			list.add(current);
		}
		
		return list;
	}
	
	/**
	 * Returns an ArrayList of nodes generated using breadthfirst traversal.
	 * 
	 * @return An {@code ArrayList} object of type {@code BSTNode}
	 * 		   containing nodes in breadthfirst order.
	 */
	public ArrayList<BSTNode<E>> breadthfirst() {
		
		ArrayList<BSTNode<E>> list = new ArrayList<>();
		
		Queue<BSTNode<E>> q = new LinkedList<>();
		
		q.add(this.root);
		
		while(!q.isEmpty()) {
			BSTNode<E> p = q.remove();
			list.add(p);
			
			if (p.left!= null) {q.add(p.left);}
			if(p.right!=null) {q.add(p.right);}
		}
		
		return list;
		
	}
	
	/**
	 * Displays the tree in the console.
	 */
	public void printTree() {

	    if (this.root.right != null) {
	        this.printTree(this.root.right, true, "");
	    }

	    printNodeValue(this.root);

	    if (this.root.left != null) {
	        this.printTree(this.root.left, false, "");
	    }
	}

	private void printTree(BSTNode<E> node, boolean isRight, String indent) {
	    if (node.right != null) {
	        printTree(node.right, true, indent + (isRight ? "        " : " |      "));
	    }

	    System.out.print(indent);

	    if (isRight) {
	        System.out.print(" /");
	    }
	    else {
	        System.out.print(" \\");
	    }
	    System.out.print("----- ");
	    printNodeValue(node);
	    if (node.left != null) {
	        printTree(node.left, false, indent + (isRight ? " |      " : "        "));
	    }
	}

	private void printNodeValue(BSTNode<E> node) {
	    if (node == null) {
	        System.out.print("<null>");
	    }
	    else {
	        System.out.print(node.getData());
	    }
	    System.out.println();
	}

}

