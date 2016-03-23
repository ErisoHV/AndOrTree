package andortree;

import java.util.LinkedList;
import tree.Tree;
/**
 * ADN-OR tree is an n-ary tree used to represent knowledge about 
 * groups of tasks to be executed to achieve some goal.<br/>
 * AND-OR tree has two types of nodes: the AND nodes and OR nodes.<br/>
 * The tasks that are not composed of subtasks are known as atomic tasks.<br/>
 * An atomic task can be in two states: executed or unexecuted. Meanwhile, 
 * the status of a non-atomic task depends on the state of their children: 
 * if it is an AND node is executed if all child nodes are executed.
 * OR node is executed if at least one of their children has been done.
 * @author ErisoHV
 */
public class AndOrTree extends Tree{
    
    private boolean andTask;
    private boolean orTask;
    private boolean isExecuted;

    /**
     * Empty constructor
     */
    public AndOrTree(){
        super (null, null, null);
        andTask = false;
        orTask = false;
        isExecuted = false;
    }

    /**
     * 
     * @return True if the AndOrTree is empty
     */
    @Override
    public boolean isEmpty() {		       
        return !andTask && !orTask;
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
        return isExecuted;
    }

    /**
     * Change a AndOrTree node type to And given the task name
     * @param taskname The name of the task to change to And
     */
    public void changeToAndTask(String taskname) {		//Dado el nombre de una taskName, se busca y se cambia a and
        AndOrTree aux;
        if (getContent().equals(taskname)) {
            andTask = true;
            orTask = false;
        }
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            while (aux != null) {
                aux.changeToAndTask(taskname);
                aux = (AndOrTree) aux.getRightChild();
            }
        }

    }

    /**
     * Change a AndOrTree node type to Or given the task name
     * @param taskname The name of the task to change to Or
     */
    public void changeToOrTask(Object taskname) {
        AndOrTree aux;
        if (getContent().toString().equals(taskname)) {
            andTask = false;
            orTask = true;
        }
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            while (aux != null) {
                aux.changeToOrTask(taskname);
                aux = (AndOrTree) aux.getRightChild();
            }
        }

    }
    
    /**
     * Execute a AndOrTree node given the task name
     * @param taskname the name of the task to be executed
     */
    public void executeTask(Object taskname) {
        AndOrTree aux;
        if (getContent().toString().equals(taskname)) {
            isExecuted = true;
        }
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            while (aux != null) {
                aux.executeTask(taskname);
                aux = (AndOrTree) aux.getRightChild();
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
        setContent(new String());
        treeStructure.remove();
        b = treeStructure.getFirst();
        treeStructure.remove();
        while ((!b.equals(",")) && (!b.equals(")"))) {
            setContent(getContent() + b);
            b = treeStructure.getFirst();
            treeStructure.remove();
        }
        andTask = false;
        orTask = false;
        isExecuted = false;
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
            setLeftChild(inic);
            aux = (AndOrTree) getLeftChild();
            while (laux.size() > 0) {
                inic = laux.getFirst();
                laux.removeFirst();
                aux.setRightChild(inic);
                aux = (AndOrTree) aux.getRightChild();
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
        if (getContent().toString().equals(taskname)) {
            if (andTask) {
                return "and";
            } else {
                return "or";
            }
        } else {
            if (getLeftChild() != null) {
                aux = (AndOrTree) getLeftChild();
                while ((aux != null) && tipo.equals(" ")) {
                    tipo = aux.getTaskType(taskname);
                    aux = (AndOrTree) aux.getRightChild();
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
        if (getContent().toString().equals(taskname)) {
            return isExecuted;
        } else {
            if (getLeftChild() != null) {
                aux = (AndOrTree) getLeftChild();
                while ((aux != null)) {
                    e = aux.isExecuted(taskname);
                    aux = (AndOrTree) aux.getRightChild();
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
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            val = false;
            while ((val == false) && (aux != null)) {
                val = aux.isAtomic(taskname);
                aux = (AndOrTree) aux.getRightChild();
            }
            return val;
        } else {
            if (getContent().toString().equals(taskname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the count of the atomic task in the tree
     * @return atomic task count
     */
    public int getAtomicCount() {		//Cuenta las tareas Atomica ejecutadas
        int cont = 0;
        AndOrTree aux;
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            while (aux != null) {
                cont = cont + aux.getAtomicCount();
                aux = (AndOrTree) aux.getRightChild();
            }
        } else if (isExecuted) {
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
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            if (andTask) {
                val = true;
                while (aux != null) {
                    val = aux.doAtomicTask(taskname) && val;
                    aux = (AndOrTree) aux.getRightChild();
                }
                isExecuted = val;
            } else {
                val = false;
                while (aux != null) {
                    val = aux.doAtomicTask(taskname) || val;
                    aux = (AndOrTree) aux.getRightChild();
                }
                isExecuted = val;
            }
        } else {
            if (getContent().toString().equals(taskname)) {
                isExecuted = true;
            }

        }
        return isExecuted;
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
        if (getLeftChild() != null) {
            aux = (AndOrTree) getLeftChild();
            if (andTask) {
                val = true;
                while (aux != null) {
                    val = aux.undoAtomicTask(taskname) && val;
                    aux = (AndOrTree) aux.getRightChild();
                }
                isExecuted = val;
            } else {
                val = false;
                while (aux != null) {
                    val = aux.undoAtomicTask(taskname) || val;
                    aux = (AndOrTree) aux.getRightChild();
                }
                isExecuted = val;
            }
        } else {
            if (getContent().toString().equals(taskname)) {
                isExecuted = false;
            }
        }
        return isExecuted;
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
        aux = (AndOrTree) getLeftChild();
        if (andTask || orTask) {
            if (!isExecuted) {
                if (aux != null) {
                    if (andTask) {
                        while (aux != null) {
                            cont1 = cont1 + aux.getAtomicMinimun();
                            aux = (AndOrTree) aux.getRightChild();
                        }
                    } else {
                        cont1 = 5000;
                        while (aux != null) {
                            cont2 = aux.getAtomicMinimun();
                            if (cont1 > cont2) {
                                cont1 = cont2;
                            }
                            aux = (AndOrTree) aux.getRightChild();
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
