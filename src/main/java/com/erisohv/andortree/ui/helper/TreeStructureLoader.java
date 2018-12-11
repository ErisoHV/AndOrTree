package main.java.com.erisohv.andortree.ui.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.erisohv.andortree.tree.AndOrTree;
import main.java.com.erisohv.andortree.tree.Tree;

/**
 *
 * @author ErisoHV
 */
public class TreeStructureLoader{
    private LinkedList<String> treeStructure = new LinkedList<>();
    private AndOrTree tree = new AndOrTree();
    private LinkedList<String> atomicTasks = new LinkedList<>();
    private int atomicMinimun;
    
    private static final Logger logger = LogManager.getLogger(TreeStructureLoader.class);
    
    public boolean readTree(BufferedReader input) {
        treeStructure = new LinkedList<>();
        tree = new AndOrTree();
        atomicTasks = new LinkedList<>();
        atomicMinimun = 0;
        try {
            readTreeStructure(input);
            readTasksStructure(input);
            fillTreeTasksList();
            atomicMinimun = tree.getAtomicMinimum();
            
            return true;
            
        } catch (IOException ex) {
        	logger.error("", ex);
        }
        return false;
    }
    
    
    private void readTreeStructure(BufferedReader input) throws IOException{
        String line = input.readLine();
        for (int i = 0; i < line.length(); i++) {
            treeStructure.addLast(String.valueOf(line.charAt(i)));
        }
        tree.createAndOrTree(treeStructure);
    }
    
    private void readTasksStructure(BufferedReader input) throws IOException{
        String line = input.readLine();
        int tasks = Integer.parseInt(line);
        for (int i = 0; i < tasks; i++) {
            line = input.readLine();
            StringTokenizer operation = new StringTokenizer(line);
            String root = operation.nextToken();
            String type = operation.nextToken();
            int actual = Integer.parseInt(operation.nextToken());
            if (actual == 1) {
                tree.executeTask(root);
            }
            if (type.equals(AndOrTree.AND)) {
                tree.changeToAndTask(root);
            } else {
                tree.changeToOrTask(root);
            }
        }
    }
    
    private void fillTreeTasksList(){
       tree.getLeafs(atomicTasks);
    }
    
    public List<String> getTreeStructure(){
        return treeStructure;
    }
    
    public AndOrTree getAndOrTree(){
        return tree;
    }
    
    public List<String> getAtomicTasks(){
        return atomicTasks;
    }
    
    public int getAtomicMinimum(){
        return atomicMinimun;
    }
    
    public void doUndoAtomicTask(String task, boolean isExec){
        tree.setExecuteValue(task, isExec);
        //refresk the atomic minimum
        atomicMinimun = tree.getAtomicMinimum();
    }
    
    public StringBuilder reportTree(String traversal){
       StringBuilder out = new StringBuilder();
       LinkedList<AndOrTree> pre = new LinkedList<>();
       
       switch(traversal){
           case Tree.INORDER: tree.inorder(pre); break;
               case Tree.POSTORDER: tree.postorder(pre); break;
                   case Tree.PREORDER: tree.preorder(pre); break;
           default: break;
       }
       if (!pre.isEmpty()){
           //Nodes List
            for (AndOrTree i : pre){
                out.append(i.getContent()).append(" ");
            }

            //Tasks status
            out.append("\n");
            //Nodes List
            for (AndOrTree i : pre){
                out.append(i.getTaskType()).append(" ")
                        .append(i.isExecuted()).append("\n");
            }
       }
       return out;
    }
}
