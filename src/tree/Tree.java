package tree;

import java.util.LinkedList;

/**
 * A simple Tree structure
 * @author ErisoHV
 */
public class Tree<T> {
    private T content;
    private Tree leftChild;
    private Tree rightChild;
    
    /**
     * Empty constructor
     * @param contentNode content of the node
     * @param leftChild left child of the node
     * @param rightChild right child of the node
     */
    public Tree(T contentNode, Tree leftChild, Tree rightChild) {
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
    public Tree getLeftChild() {		//Retorna el hijo izquierdo de una taskName
        return leftChild;
    }

    /**
     * 
     * @return the right child of the AndOrTree
     */
    public Tree getRightChild() {		//Retorna el hijo derecho de una taskName
        return rightChild;
    }
    
     /**
     * Set the left child of the AndOrTree
     * @param tree 
     */
    public void setLeftChild(Tree tree) {		//Modifica el hijo izq de una taskName
        leftChild = tree;
    }

    /**
     * Set the right child of the AndOrTree
     * @param tree 
     */
    public void setRightChild(Tree tree) {       //Modifica el hijo derech de una taskName
        rightChild = tree;
    }
    
    /**
     * 
     * @return return all the children of the AndOrTree
     */
    public LinkedList<Tree> getChildren() {
        LinkedList<Tree> laux = new LinkedList<>();
        Tree arb;
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
    public Tree getNode(T nodeContent) {
        Tree aux = null;
        Tree node = null;
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
    public void preorder(LinkedList<T> nodeList) {
        Tree aux = null;
        nodeList.addLast(content);
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
    public void inorder(LinkedList<T> nodeList) {		//Recorrido inorder del Arbol
        Tree aux = null;
        if (leftChild != null) {
            aux = leftChild;
            aux.inorder(nodeList);
            aux = aux.getRightChild();
            nodeList.addLast(content);
            while (aux != null) {
                aux.inorder(nodeList);
                aux = aux.getRightChild();
            }
        } else {
            nodeList.addLast(content);
        }
    }

    /**
     * Postorder route
     * @param nodeList node content list
     */
    public void postorder(LinkedList<T>  nodeList) {		//Recorrido prostorden del Arbol
        Tree aux = null;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.postorder(nodeList);
                aux = aux.getRightChild();
            }
        }
        nodeList.addLast(content);
    }
    
    
    /**
     * Get the count of the nodes in the tree
     * @return nodes count
     */
    public int getLeafCount() {
        int cont = 0;
        Tree aux;
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
    public void getLeafs(LinkedList<T>  nodeList) {
        Tree aux;
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
        Tree aux = null;
        boolean val = false;
        if (getLeftChild() != null) {
            aux = leftChild;
            val = false;
            while ((val == false) && (aux != null)) {
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
