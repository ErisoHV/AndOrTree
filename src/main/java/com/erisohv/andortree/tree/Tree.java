package main.java.com.erisohv.andortree.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple Tree structure
 * @author ErisoHV
 */
public class Tree<T> {
    private T content;
    private Tree leftChild;
    private Tree rightChild;
    
    public static final String INORDER = "INORDER";
    public static final String PREORDER = "PREORDER";
    public static final String POSTORDER = "POSTORDER";
    
    /**
     * Empty constructor
     * @param contentNode content of the node
     * @param leftChild left child of the node
     * @param rightChild right child of the node
     */
    public Tree(T contentNode, Tree<T> leftChild, Tree<T> rightChild) {
        this.content = contentNode;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    
    /**
     * 
     * @return True if the Tree is empty
     */
    public boolean isEmpty() {		       
        return leftChild == null && rightChild == null;
    }
    
    /**
     * 
     * @return the node content
     */
    public T getContent() {
        return content;
    }
    
    /**
     * 
     * 
     * @param name
     */
    public void setContent(T name) {
        this.content = name;
    }
    
    /**
     * 
     * @return the left child of the AndOrTree
     */
    public Tree<T> getLeftChild() {
        return leftChild;
    }

    /**
     * 
     * @return the right child of the AndOrTree
     */
    public Tree<T> getRightChild() {
        return rightChild;
    }
    
     /**
     * Set the left child of the AndOrTree
     * @param tree 
     */
    public void setLeftChild(Tree<T> tree) {
        leftChild = tree;
    }

    /**
     * Set the right child of the AndOrTree
     * @param tree 
     */
    public void setRightChild(Tree tree) {
        rightChild = tree;
    }
    
    /**
     * 
     * @return return all the children of the AndOrTree
     */
    public List<Tree> getChildren() {
        LinkedList<Tree> laux = new LinkedList<>();
        Tree<T> arb;
        if (leftChild != null) {
            arb = leftChild;
            while (arb != null) {
                laux.addLast(arb);
                arb = arb.getRightChild();
            }
        }
        return laux;
    }
    
    /**
     * Return a node given the node content
     * @param nodeContent Node content
     * @return A Node
     */
    public Tree<T> getNode(T nodeContent) {
        Tree<T> aux = null;
        Tree<T> node = null;
        if (content.equals(nodeContent)) {
            return this;
        } else {
            if (leftChild != null) {
                aux = leftChild;
                while ((aux != null) && node == null) {
                    node = this;
                    aux = aux.getRightChild();
                }
            }
            return node;
        }
    }
    
    /**
     * Preorder route
     * @param nodeList node content list
     */
    public void preorder(LinkedList<Tree> nodeList) {
        Tree<T> aux = null;
        nodeList.addLast(this);
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.preorder(nodeList);
                aux = aux.getRightChild();
            }
        }
    }
    
   /**
     * Inorder route
     * @param nodeList node content list
     */
    public void inorder(LinkedList<Tree> nodeList) {
        Tree<T> aux = null;
        if (leftChild != null) {
            aux = leftChild;
            aux.inorder(nodeList);
            aux = aux.getRightChild();
            nodeList.addLast(this);
            while (aux != null) {
                aux.inorder(nodeList);
                aux = aux.getRightChild();
            }
        } else {
            nodeList.addLast(this);
        }
    }

    /**
     * Postorder route
     * @param nodeList node content list
     */
    public void postorder(LinkedList<Tree>  nodeList) {
        Tree<T> aux = null;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.postorder(nodeList);
                aux = aux.getRightChild();
            }
        }
        nodeList.addLast(this);
    }
    
    /**
     * Get the count of the nodes in the tree
     * @return nodes count
     */
    public int getLeafCount() {
        int cont = 0;
        Tree<T> aux;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                cont = cont + aux.getLeafCount();
                aux = aux.getRightChild();
            }
        } else {
            cont = 1;
        }
        return cont;
    }
    
    /**
     * Get the tree leafs
     * @param nodeList leafs list
     * @return leafs
     */
    public void getLeafs(List<T>  nodeList) {
        Tree<T> aux;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.getLeafs(nodeList);
                aux = aux.getRightChild();
            }
        } else {
            nodeList.add(content);
        }
    }
    
    
    /**
     * Return a true if the node is a Leaf
     * @param nodeContent Node content to compare
     * @return True if is a Leaf
     */
    public boolean isLeaf(T nodeContent) {
        Tree<T> aux = null;
        boolean val = false;
        if (getLeftChild() != null) {
            aux = leftChild;
            while (!val && (aux != null)) {
                val = aux.isLeaf(nodeContent);
                aux = aux.getRightChild();
            }
            return val;
        } else {
            if (getContent().equals(nodeContent)) {
                return true;
            }
        }
        return val;
    }

}
