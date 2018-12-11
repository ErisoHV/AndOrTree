package main.java.com.erisohv.andortree.ui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.erisohv.andortree.tree.Tree;
import main.java.com.erisohv.andortree.ui.helper.TreeStructureLoader;
import main.java.com.erisohv.andortree.ui.helper.TreeWindow;

public class UIInterface {
	
	private JFrame window;
	private JMenuItem openOption = new JMenuItem("Open");
    private JButton countAtomicButton = new JButton("Atomic count");
    private JButton doAtomicButton = new JButton("Do Atomic task");
    private JButton undoAtomicButton = new JButton("Undo Atomic task");
    private JButton minAtomicButton = new JButton("Atomic minimum");
    private JButton showTree = new JButton("Show tree");
    private JButton treeReportButton = new JButton("Print tasks report");

    private Box horizontal1 = Box.createHorizontalBox();
    private Box horizontal2 = Box.createHorizontalBox();
    private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

    private TreeStructureLoader treeStructure = new TreeStructureLoader();
    
    private TreeWindow treeWindow = new TreeWindow(treeStructure);
    //Messages
    private static final String FILE_ERROR = "The file does not exist";
    private static final String EMPTY_TREE = "You must load a tree before performing an operation";
    private static final String ATOMIC_COUNT = "The number of atomic tasks executed is: {0}";
    private static final String ATOMIC_TASK = "The selected task is not an atomic task";
    private static final String DO_TASK = "Select the ID of the task to perform:";
    private static final String UNDO_TASK = "Enter the ID of the task that you want to undo:";
    private static final String ATOMIC_MINIMUM = "The atomic minimum is: {0}";
    private static final String REPORT = "Please select the tree traversal:";
    private static final String REPORT_ERROR = "Could not create the file: {0}";
    private static final String REPORT_OK = "The file: {0} was created successfully";
    
    private static final Logger logger = LogManager.getLogger(UIInterface.class);
    
     public UIInterface() {
        window = new JFrame("AND - OR Tree");
        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        
        menubar.add(menu1);
        menu1.add(openOption);
        window.setJMenuBar(menubar);     

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
        window.add(tabs, BorderLayout.CENTER);
        
        enableOperations(false);
        
        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setSize(550, 350);
       
        window.setLocationRelativeTo(null);
        initListeners();
    }
     
    public void open(){
    	if (window != null)
    		window.setVisible(true);
    }

     private void initListeners(){
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
               undoAtomicTaskActions();
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

     private void enableOperations(boolean enable){
        countAtomicButton.setEnabled(enable);
        doAtomicButton.setEnabled(enable);
        undoAtomicButton.setEnabled(enable);
        minAtomicButton.setEnabled(enable);
        showTree.setEnabled(enable);
        treeReportButton.setEnabled(enable);
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
        	 File name = open.getSelectedFile();
             try (BufferedReader input = new BufferedReader(new FileReader(name))) {
                 if (name.exists() && treeStructure.readTree(input)) {
                     showTreeActions();
                     enableOperations(true);
                 }
             } catch (IOException ex) {
            	 showErrorDialog(FILE_ERROR);
                 logger.error("UIInterface -> openActions", ex);
             } 
         }
    }
    
    private void countAtomicActions(){
        if (checkTree()){
          showInfoDialog(MessageFormat.format(ATOMIC_COUNT, treeStructure.getAndOrTree().getLeafCount()), "");
        }
    }
    
    private void doAtomicTaskActions(){
        if (checkTree()) {
            String task = showOptionPane(DO_TASK, treeStructure.getAtomicTasks().toArray());
            if (task != null && !task.isEmpty() && checkIsLeaf(task)) {
               treeStructure.doUndoAtomicTask(task, true);
               treeWindow.refreshTreeWindow();
            }
        }
    }
    
     public void undoAtomicTaskActions() {
         if (checkTree()) {
             String task = showOptionPane(UNDO_TASK, treeStructure.getAtomicTasks().toArray());
             if (task != null && !task.isEmpty() && checkIsLeaf(task)) {
                 treeStructure.doUndoAtomicTask(task, false);
                 treeWindow.refreshTreeWindow();
             }
         } 
    }
     
    private void getAtomicMinimumActions() {
        if (checkTree()) {
        	showInfoDialog(MessageFormat.format(ATOMIC_MINIMUM, treeStructure.getAtomicMinimum()), "Atomic Minimum");
        }
    }
    
    private boolean reportTreeActions(){
       if (checkTree()) {
            String traversal = showOptionPane(REPORT, 
                    new Object[]{Tree.INORDER,
                    Tree.POSTORDER, 
                    Tree.PREORDER});
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    
            String name = traversal + "_" + dateFormat.format(new Date()) + ".out";
            if (!name.isEmpty()) {
                try {
                    FileWriter fw = new FileWriter(name);
                    try (BufferedWriter output
                            = new BufferedWriter(new BufferedWriter(fw))) {
                        StringBuilder out = treeStructure.reportTree(traversal);
                        output.write(out.toString());
                        showInfoDialog(MessageFormat.format(REPORT_OK, name), "");
                        return true;
                    }
                } catch (IOException ioException) {
                    logger.error("reportTreeActions", ioException);
                    showWarningDialog(MessageFormat.format(REPORT_ERROR, name));
                }
            }
        }
       return false;
    }

    private boolean checkTree(){ 
        if (!treeStructure.getAndOrTree().isEmpty()) {
            return true;
        } 
        showErrorDialog(EMPTY_TREE);
        return false;
    }
    
    private boolean checkIsLeaf(String task){
        if (treeStructure.getAndOrTree().isLeaf(task)) {
            return true;
        }
        showErrorDialog(ATOMIC_TASK);
        return false;
        
    }
    
    private String showOptionPane(String msg, Object[] options){
        JFrame frame = new JFrame();
        return (String) JOptionPane.showInputDialog(frame, msg, "", JOptionPane.QUESTION_MESSAGE, null, 
                options, options[0]);
    }
    
    private void showErrorDialog(String message){
    	JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showWarningDialog(String message){
    	JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private void showInfoDialog(String message, String title){
    	 JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
}
