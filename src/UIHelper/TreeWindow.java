package UIHelper;

import UIInterface.TreeStructureLoader;
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
public class TreeWindow extends JFrame{
    JButton refresh = new JButton("Refresh Tree");	//Boton refresh
    JLabel info = new JLabel("Press \"Refresh Tree\" to view the "
            + "tasks performed recently");
    TreeCanvas canvas;
    JScrollPane scroll;	//Barra de la Ventana

    public TreeWindow(TreeStructureLoader tree){
        setTitle("Print tree");
        setSize(800, 550);
        canvas = new TreeCanvas(tree);
        scroll = new JScrollPane(canvas);
        scroll.setPreferredSize(new Dimension(100, 100));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        init();
    }
    
    private void init(){
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               repaint();
            }
        });
        scroll.getHorizontalScrollBar().addAdjustmentListener(new RefreshScroll());
        scroll.getVerticalScrollBar().addAdjustmentListener(new RefreshScroll());
        add(refresh, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(info, BorderLayout.SOUTH);
    }
    
    public void showWindow(){
        setVisible(true);
    }
    
    public void setTreeStructure(TreeStructureLoader tree){
        canvas = new TreeCanvas(tree);
    }
    
    public void refreshTreeWindow(){
        repaint();
    }
    
    class RefreshWindow implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
    }
    
    //Redibuja el Arbol en caso de que muevan las barras
    public class RefreshScroll implements AdjustmentListener{ //Inicio class RefreshScroll  
       @Override
       public void adjustmentValueChanged(AdjustmentEvent e){
              repaint();
       }
    }//Fin class RefreshScroll
    
}
