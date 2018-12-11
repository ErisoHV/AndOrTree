package main.java.com.erisohv.andortree.ui.helper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Erika
 */
public class TreeWindow {
	private JFrame window;
    JButton refresh = new JButton("Refresh Tree");
    JLabel info = new JLabel("Press \"Refresh Tree\" to view the tasks performed recently");
    TreeCanvas canvas;
    JScrollPane scroll;

    public TreeWindow(TreeStructureLoader tree){
    	window = new JFrame("Print tree");
        window.setSize(800, 550);
        canvas = new TreeCanvas(tree);
        scroll = new JScrollPane(canvas);
        scroll.setPreferredSize(new Dimension(100, 100));
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        init();
    }
    
    private void init(){
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               window.repaint();
            }
        });
        scroll.getHorizontalScrollBar().addAdjustmentListener(new RefreshScroll());
        scroll.getVerticalScrollBar().addAdjustmentListener(new RefreshScroll());
        window.add(refresh, BorderLayout.NORTH);
        window.add(scroll, BorderLayout.CENTER);
        window.add(info, BorderLayout.SOUTH);
    }
    
    public void showWindow(){
    	window.setVisible(true);
    }
    
    public void setTreeStructure(TreeStructureLoader tree){
        canvas = new TreeCanvas(tree);
    }
    
    public void refreshTreeWindow(){
    	window.repaint();
    }
    
    public boolean isShowing(){
    	return window.isShowing();
    }
    
    class RefreshWindow implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent ae) {
        	window.repaint();
        }
    }

    public class RefreshScroll implements AdjustmentListener{
       @Override
       public void adjustmentValueChanged(AdjustmentEvent e){
    	   window.repaint();
       }
    }
    
}
