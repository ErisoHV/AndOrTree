/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tree;

import java.util.LinkedList;

/**
 *
 * @author Erika
 */
public class Tree {
    private String name;
    private Tree leftChild;
    private Tree rightChild;
    
    /**
     * Empty constructor
     */
    public Tree(String name, Tree leftChild, Tree rightChild) {
        this.name = name;
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
     * @return the node name
     */
    public String getName() {
        return name;
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
     * Given the task name, return it type (And/Or)
     * @param taskname Task name
     * @return Type and / or
     */
    public Tree getNode(String nodename) {
        Tree aux;
        Tree node = null;
        if (name.equals(nodename)) {
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
     * @param nodeList node name list
     */
    public void preorder(LinkedList<String> nodeList) {
        Tree aux;
        nodeList.addLast(name);
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
     * @param nodeList node name list
     */
    public void inorder(LinkedList<String> nodeList) {		//Recorrido inorder del Arbol
        Tree aux;
        if (leftChild != null) {
            aux = leftChild;
            aux.inorder(nodeList);
            aux = aux.getRightChild();
            nodeList.addLast(name);
            while (aux != null) {
                aux.inorder(nodeList);
                aux = aux.getRightChild();
            }
        } else {
            nodeList.addLast(name);
        }
    }

    /**
     * Postorder route
     * @param nodeList node name list
     */
    public void postorder(LinkedList<String>  nodeList) {		//Recorrido prostorden del Arbol
        Tree aux;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.postorder(nodeList);
                aux = aux.getRightChild();
            }
        }
        nodeList.addLast(name);
    }
    
    
    /**
     * Get the count of the atomic task in the tree
     * @return atomic task count
     */
    public int getLeafCount() {		//Cuenta las tareas Atomica ejecutadas
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


}
