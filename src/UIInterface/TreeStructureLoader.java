package UIInterface;

import andortree.AndOrTree;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErisoHV
 */
public class TreeStructureLoader{
    private final LinkedList<String> treeStructure = new LinkedList<>();
    private final AndOrTree tree = new AndOrTree();
    
    public void readTree(BufferedReader input) {
        try {
            readTreeStructure(input);
            readTasksStructure(input);
        } catch (IOException ex) {
            Logger.getLogger(TreeStructureLoader.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
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
        int tasks = Integer.valueOf(line).intValue();
        for (int i = 0; i < tasks; i++) {
            line = input.readLine();
            StringTokenizer operation = new StringTokenizer(line);
            String root = operation.nextToken();
            String type = operation.nextToken();
            int actual = Integer.valueOf(operation.nextToken()).intValue();
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
    
    public LinkedList<String> getTreeStructure(){
        return treeStructure;
    }
    
    public AndOrTree getAndOrTree(){
        return tree;
    }
    
    
}
