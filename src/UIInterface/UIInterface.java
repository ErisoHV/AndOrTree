package UIInterface;

import UIHelper.TreeStructureLoader;
import UIHelper.TreeWindow;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class UIInterface extends JFrame {

    JMenuBar menubar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenuItem openOption = new JMenuItem("Open");

    JButton countAtomicButton = new JButton("Atomic count");
    JButton doAtomicButton = new JButton("Do Atomic task");
    JButton undoAtomicButton = new JButton("Undo Atomic task");
    JButton minAtomicButton = new JButton("Atomic minimum");
    JButton showTree = new JButton("Show tree");
    JButton treeReportButton = new JButton("Print tasks report");

    Box horizontal1 = Box.createHorizontalBox();
    Box horizontal2 = Box.createHorizontalBox();
    JTabbedPane tabs = new JTabbedPane(
            JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

    TreeStructureLoader treeStructure = new TreeStructureLoader();
    
    TreeWindow treeWindow = new TreeWindow(treeStructure);
    //Messages
    private final String FILE_ERROR = "The file does not exist";
    private final String EMPTY_TREE = "You must load a tree before "
            + "performing an operation";
    private final String ATOMIC_COUNT = "The number of atomic tasks executed is: {0}";
    private final String ATOMIC_TASK = "The selected task is not an atomic task";
    private final String DO_TASK = "Enter the ID of the task to perform:";
    private final String UNDO_TASK = "Enter the ID of the task that you want to undo:";
    private final String ATOMIC_MINIMUM = "The atomic minimum is: {0}";
    private final String REPORT = "Please indicate the name of the File output:";
    private final String REPORT_ERROR = "Could not create the file: {0}";
    
     public UIInterface() {
        super("AND - OR Tree");		//Titulo de la ventana
        menubar.add(menu1);
        menu1.add(openOption);
        setJMenuBar(menubar);

        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(countAtomicButton);
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(doAtomicButton);
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(undoAtomicButton);
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(minAtomicButton);

        horizontal2.add(Box.createHorizontalGlue());
        horizontal2.add(showTree);
        horizontal2.add(Box.createHorizontalGlue());
        horizontal2.add(treeReportButton);
        horizontal2.add(Box.createHorizontalGlue());

        tabs.addTab("Atomic Tasks", horizontal1);
        tabs.addTab("Results", horizontal2);
        this.add(tabs, BorderLayout.CENTER);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(550, 350);
        setVisible(true);
        setLocationRelativeTo(null);
        init();
    }

     private void init(){
         //Actions
        openOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openActions();
            }
        });

        countAtomicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countAtomicActions();
            }
        });

        doAtomicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAtomicTaskActions();
            }
        });

        undoAtomicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               UndoAtomicTaskActions();
            }
        });
        
        minAtomicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAtomicMinimumActions();
            }
        });
        
        showTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTreeActions();
            }
        });
        treeReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              reportTreeActions();
            }
        });
     }

     private void showTreeActions(){
         if (checkTree()){
             treeWindow.setTreeStructure(treeStructure);
             if (!treeWindow.isShowing())
                treeWindow.showWindow();
             else 
                treeWindow.refreshTreeWindow();
         }
     }
     
    private void openActions(){
         JFileChooser open = new JFileChooser("Open File");
         File workingDirectory = new File(System.getProperty("user.dir"));
         open.setCurrentDirectory(workingDirectory);
         
         int retval = open.showOpenDialog(null);
         if (retval == JFileChooser.APPROVE_OPTION) {
             try {
                 File name = open.getSelectedFile();
                 BufferedReader input = new BufferedReader(new FileReader(name));
                 if (name.exists()) {
                     treeStructure.readTree(input);
                     
                     showTreeActions();
                     
                     input.close();
                 }
                 
             } catch (FileNotFoundException ex) {
                 JOptionPane.showMessageDialog(null, FILE_ERROR,
                            "Error", JOptionPane.ERROR_MESSAGE);
                 Logger.getLogger(UIInterface.class.getName()).log(Level.SEVERE, null, ex);
             }  catch (IOException ex) {
                     JOptionPane.showMessageDialog(null, FILE_ERROR,
                            "Error", JOptionPane.ERROR_MESSAGE);
                     Logger.getLogger(UIInterface.class.getName()).log(Level.SEVERE, null, ex);
                 }
         }
    }
    
    private void countAtomicActions(){
        if (checkTree()){
          JOptionPane.showMessageDialog(null, 
                  MessageFormat.format(ATOMIC_COUNT, 
                          treeStructure.getAndOrTree().getLeafCount()));
        }
    }
    
    private void doAtomicTaskActions(){
        if (checkTree()) {
            String task = JOptionPane.showInputDialog(DO_TASK);
            if (task != null && !task.isEmpty() && checkIsLeaf(task)) {
               treeStructure.getAndOrTree().doAtomicTask(task);
               treeWindow.refreshTreeWindow();
            }
        }
    }
    
     public void UndoAtomicTaskActions() {
         if (checkTree()) {
             String task = JOptionPane.showInputDialog(UNDO_TASK);
             if (task != null && !task.isEmpty() && checkIsLeaf(task)) {
                 treeStructure.getAndOrTree().undoAtomicTask(task);
                 treeWindow.refreshTreeWindow();
             }
         } 
    }
     
    private void getAtomicMinimumActions() {
        if (checkTree()) {
            JOptionPane.showMessageDialog(null,
                    MessageFormat.format(ATOMIC_MINIMUM, 
                            treeStructure.getAndOrTree().getAtomicMinimum()),
                    "Atomic Minimum", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private boolean reportTreeActions(){
       if (checkTree()) {
            String name = JOptionPane.showInputDialog(REPORT);
            if (name != null && !name.isEmpty()) {
                try {
                    FileWriter fw = new FileWriter(name);
                    try (BufferedWriter output
                            = new BufferedWriter(new BufferedWriter(fw))) {
                        LinkedList<String> list = new LinkedList<>();
                        int i = 0;
                        treeStructure.getAndOrTree().preorder(list);
                        while (i < list.size()) {
                            output.write(list.get(i));
                            output.write(" ");
                            i++;
                        }
                        output.write("\n");
                        i = 0;
                        while (i < list.size()) {
                            output.write(list.get(i) + " "
                                    + treeStructure.getAndOrTree()
                                            .getTaskType(list.get(i))
                                    + " " + treeStructure.getAndOrTree()
                                            .isExecuted(list.get(i)) + "\n");
                            i++;
                        }
                    }
                    return true;
                } catch (IOException ioException) {	//Mensaje de Error
                    JOptionPane.showMessageDialog(null, MessageFormat
                            .format(REPORT_ERROR, name),
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
       return false;
    }

    private boolean checkTree(){
        if (!treeStructure.getAndOrTree().isEmpty()) {
            return true;
        } 
         JOptionPane.showMessageDialog(null, EMPTY_TREE, "Error", 
                 JOptionPane.ERROR_MESSAGE);
         return false;
    }
    
    private boolean checkIsLeaf(String task){
        if (treeStructure.getAndOrTree().isLeaf(task)) {
            return true;
        }
        JOptionPane.showMessageDialog(null, ATOMIC_TASK,
                    "Error", JOptionPane.ERROR_MESSAGE);
        return false;
        
    }
}
