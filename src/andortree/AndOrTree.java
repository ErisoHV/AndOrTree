package andortree;
/**
 * @author ErisoHV
 * @version 1.0
 */
import java.util.LinkedList;

public class AndOrTree{
    private String taskName;
    private boolean andTask;
    private boolean orTask;
    private boolean isEjecuted;
    private AndOrTree leftChild;
    private AndOrTree rightChild;

    /**
     * Empty constructor
     */
    public AndOrTree() {
        andTask = false;
        orTask = false;
        isEjecuted = false;
        leftChild = null;
        rightChild = null;
    }

    /**
     * 
     * @return True if the AndOrTree is empty
     */
    public boolean isEmpty() {		       
        return !andTask && !orTask;
    }

    /**
     * 
     * @return 
     */
    public String getTaskName() {
        return taskName;
    }
    
    /**
     * 
     * @return True if the AndOrTree node is a <strong>And Task</strong>
     */
    public boolean isAndTask() {
        return andTask;
    }
    
    /**
     * 
     * @return True if the AndOrTree node is a <strong>Or Task</strong>
     */
    public boolean isOrTask() {
        return orTask;
    }
    
    /**
     * 
     * @return True if the AndOrTree node is <strong>already executed</strong>
     */
    public boolean isExecuted() {
        return isEjecuted;
    }

    /**
     * 
     * @return the left child of the AndOrTree
     */
    public AndOrTree getLeftChild() {		//Retorna el hijo izquierdo de una taskName
        return leftChild;
    }

    /**
     * 
     * @return the right child of the AndOrTree
     */
    public AndOrTree getRightChild() {		//Retorna el hijo derecho de una taskName
        return rightChild;
    }
    
    /**
     * Set the left child of the AndOrTree
     * @param tree 
     */
    public void setLeftChild(AndOrTree tree) {		//Modifica el hijo izq de una taskName
        leftChild = tree;
    }

    /**
     * Set the right child of the AndOrTree
     * @param tree 
     */
    public void setRightChild(AndOrTree tree) {       //Modifica el hijo derech de una taskName
        rightChild = tree;
    }

    /**
     * 
     * @return return all the children of the AndOrTree
     */
    public LinkedList<AndOrTree> getChildren() {
        LinkedList<AndOrTree> laux = new LinkedList<>();
        AndOrTree arb;
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
     * Change a AndOrTree node type to And given the task name
     * @param taskname The name of the task to change to And
     */
    public void changeToAndTask(String taskname) {		//Dado el nombre de una taskName, se busca y se cambia a and
        AndOrTree aux;
        if (taskName.equals(taskname)) {
            andTask = true;
            orTask = false;
        }
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.changeToAndTask(taskname);
                aux = aux.getRightChild();
            }
        }

    }

    /**
     * Change a AndOrTree node type to Or given the task name
     * @param taskname The name of the task to change to Or
     */
    public void changeToOrTask(String taskname) {		//Dado el nombre de una taskName, se busca y se cambia a or
        int cont = 0;
        AndOrTree aux;
        if (taskName.equals(taskname)) {
            andTask = false;
            orTask = true;
        }
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.changeToOrTask(taskname);
                aux = aux.getRightChild();
            }
        }

    }
    
    /**
     * Execute a AndOrTree node given the task name
     * @param taskname the name of the task to be executed
     */
    public void executeTask(String taskname) {
        AndOrTree aux;
        if (taskName.equals(taskname)) {
            isEjecuted = true;
        }
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.executeTask(taskname);
                aux = aux.getRightChild();
            }
        }
    }
    
    /**
     * creates a AndOrTree through a levels route by parenthetical notation<br/>
     * <strong>Parenthetical notation example: </strong>
     * (T1,(T2,(T5),(T6,(T9),(T10),(T11),(T12))),(T3,(T7),(T8)),(T4))<br/>
     * The tree is drawn from left to right recursively<br/>    
     * @param treeStructure Parenthetical notation 
     */
    public void createAndOrTree(LinkedList<String> treeStructure) {		
        String b;
        AndOrTree aux;
        AndOrTree inic;
        LinkedList<AndOrTree> laux = new LinkedList<>();
        taskName = new String();
        treeStructure.remove();
        b = treeStructure.getFirst();
        treeStructure.remove();
        while ((!b.equals(",")) && (!b.equals(")"))) {
            taskName = taskName + b;
            b = treeStructure.getFirst();
            treeStructure.remove();
        }
        andTask = false;
        orTask = false;
        isEjecuted = false;
        if (b.equals(",")) {
            while (!b.equals(")")) {
                aux = new AndOrTree();
                aux.createAndOrTree(treeStructure);
                laux.addLast(aux);
                b = treeStructure.getFirst();
                treeStructure.remove();
            }
            inic = laux.getFirst();
            laux.removeFirst();
            leftChild = inic;
            aux = leftChild;
            while (laux.size() > 0) {
                inic = laux.getFirst();
                laux.removeFirst();
                aux.setRightChild(inic);
                aux = aux.getRightChild();
            }
        }
    }
    
    /**
     * Given the task name, return it type (And/Or)
     * @param taskname Task name
     * @return Type and / or
     */
    public String getTaskType(String taskname) {
        AndOrTree aux;
        String tipo = "";
        if (taskName.equals(taskname)) {
            if (andTask) {
                return "and";
            } else {
                return "or";
            }
        } else {
            if (leftChild != null) {
                aux = leftChild;
                while ((aux != null) && tipo.equals(" ")) {
                    tipo = aux.getTaskType(taskname);
                    aux = aux.getRightChild();
                }
            }
            return tipo;
        }
    }

    /**
     * Given the task name, return true if the node is already executed
     * making a recursively route
     * @param taskname Task name of the node
     * @return  return true if the node is already executed
     */
    public boolean isExecuted(String taskname) {
        AndOrTree aux;
        boolean e = false;
        if (taskName.equals(taskname)) {
            return isEjecuted;
        } else {
            if (leftChild != null) {
                aux = leftChild;
                while ((aux != null)) {
                    e = aux.isExecuted(taskname);
                    aux = aux.getRightChild();
                }
            }
            return e;
        }
    }

    /**
     * Return true if the node is a Atomic task (a leaf)
     * @param taskname Task name
     * @return true if the node is a leaf 
     */
    public boolean isAtomic(Object taskname) {
        AndOrTree aux;
        boolean val;
        if (leftChild != null) {
            aux = leftChild;
            val = false;
            while ((val == false) && (aux != null)) {
                val = aux.isAtomic(taskname);
                aux = aux.getRightChild();
            }
            return val;
        } else {
            if (taskName.equals(taskname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Preorder route
     * @param nodeList node name list
     */
    public void preorder(LinkedList<String> nodeList) {
        AndOrTree aux;
        nodeList.addLast(taskName);
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
        AndOrTree aux;
        if (leftChild != null) {
            aux = leftChild;
            aux.inorder(nodeList);
            aux = aux.getRightChild();
            nodeList.addLast(taskName);
            while (aux != null) {
                aux.inorder(nodeList);
                aux = aux.getRightChild();
            }
        } else {
            nodeList.addLast(taskName);
        }
    }

    /**
     * Postorder route
     * @param nodeList node name list
     */
    public void postorder(LinkedList<String>  nodeList) {		//Recorrido prostorden del Arbol
        AndOrTree aux;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                aux.postorder(nodeList);
                aux = aux.getRightChild();
            }
        }
        nodeList.addLast(taskName);
    }

    /**
     * Get the count of the atomic task in the tree
     * @return atomic task count
     */
    public int getAtomicCount() {		//Cuenta las tareas Atomica ejecutadas
        int cont = 0;
        AndOrTree aux;
        if (leftChild != null) {
            aux = leftChild;
            while (aux != null) {
                cont = cont + aux.getAtomicCount();
                aux = aux.getRightChild();
            }
        } else if (isEjecuted) {
            cont = 1;
        }
        return cont;
    }

    /**
     * Execute a atomic task, given it name. Updated
     * the entire tree
     * @param taskname task name
     * @return 
     */
    public boolean doAtomicTask(String taskname) {	//ejecuta una taskName Atomica
        AndOrTree aux;
        boolean val;
        if (leftChild != null) {
            aux = leftChild;
            if (andTask) {
                val = true;
                while (aux != null) {
                    val = aux.doAtomicTask(taskname) && val;
                    aux = aux.getRightChild();
                }
                isEjecuted = val;
            } else {
                val = false;
                while (aux != null) {
                    val = aux.doAtomicTask(taskname) || val;
                    aux = aux.getRightChild();
                }
                isEjecuted = val;
            }
        } else {
            if (taskName.equals(taskname)) {
                isEjecuted = true;
            }

        }
        return isEjecuted;
    }

    /**
     * Undo a atomic task (isExecute=false), given it name. Updated
     * the entire tree
     * @param taskname the task name
     * @return 
     */
    public boolean undoAtomicTask(Object taskname) {	   //deshace una taskName Atomica isExecuted
        AndOrTree aux;
        boolean val;
        if (leftChild != null) {
            aux = leftChild;
            if (andTask) {
                val = true;
                while (aux != null) {
                    val = aux.undoAtomicTask(taskname) && val;
                    aux = aux.getRightChild();
                }
                isEjecuted = val;
            } else {
                val = false;
                while (aux != null) {
                    val = aux.undoAtomicTask(taskname) || val;
                    aux = aux.getRightChild();
                }
                isEjecuted = val;
            }
        } else {
            if (taskName.equals(taskname)) {
                isEjecuted = false;
            }
        }
        return isEjecuted;
    }
    
    /**
     * Calculates and returns the minimum number of atomic tasks 
     * that must be done from the current situation to have 
     * completed the overall task
     * @return number of atomic tasks
     */

    public int getAtomicMinimun() {		//Cuenta la cantidad de tareas Atomicas que faltan para que se ejecute la raiz
        int cont1 = 0, cont2 = 0;
        AndOrTree aux;
        aux = leftChild;
        if (andTask || orTask) {
            if (!isEjecuted) {
                if (aux != null) {
                    if (andTask) {
                        while (aux != null) {
                            cont1 = cont1 + aux.getAtomicMinimun();
                            aux = aux.getRightChild();
                        }
                    } else {
                        cont1 = 5000;
                        while (aux != null) {
                            cont2 = aux.getAtomicMinimun();
                            if (cont1 > cont2) {
                                cont1 = cont2;
                            }
                            aux = aux.getRightChild();
                        }
                    }
                } else {
                    cont1++;
                }
            }
        }
        return cont1;
    }

}
