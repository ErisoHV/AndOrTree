package main.java.com.erisohv.andortree.ui.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import main.java.com.erisohv.andortree.tree.AndOrTree;

/**
 *
 * @author ErisoHV
 */
public class TreeCanvas extends JPanel{
	
	private static final long serialVersionUID = -8487039720611456232L;
	
	private Integer nodesPosition = 1;
    private Integer area = 1;
    private static final Font FONT = new Font("Arial Bold", Font.BOLD, 12);
    private static final int NODE_DISTANCE = 35;
    
    private TreeStructureLoader treeStructure = new TreeStructureLoader();
    
    public TreeCanvas(TreeStructureLoader tree){
        setSize(2000, 2000);
        this.treeStructure = tree;
        this.setBackground(Color.LIGHT_GRAY);
        repaint();
    }
    
    private void drawRootExecutedTask(int i, Graphics g, int y){
    	g.setColor(Color.BLACK);
    	 if (treeStructure.getAndOrTree().isAndTask()) {
             g.fillOval(i * NODE_DISTANCE - 15, y * NODE_DISTANCE - 15, 31, 31);
             g.setColor(Color.BLACK);
             g.drawOval(i * NODE_DISTANCE - 15, y * NODE_DISTANCE - 15, 30, 30);
         } else {
             g.fillRect(i * NODE_DISTANCE - 18, y * NODE_DISTANCE - 13, 36, 26);
             g.setColor(Color.BLACK);
             g.drawRect(i * NODE_DISTANCE - 18, y * NODE_DISTANCE - 13, 36, 26);
         }
    	 g.setFont(FONT);
         g.setColor(Color.WHITE);
    	 g.drawString(treeStructure.getAndOrTree().getContent().toString(), 
                 i * NODE_DISTANCE - 11, y * NODE_DISTANCE + 5);
    }
    
    private void drawRootNotExecutedTask(int i, Graphics g, int y){
    	g.setColor(Color.WHITE);
    	if (treeStructure.getAndOrTree().isAndTask()) {
            g.fillOval(i * NODE_DISTANCE - 15, y * NODE_DISTANCE - 15, 31, 31);
            g.setColor(Color.BLACK);
            g.drawOval(i * NODE_DISTANCE - 15, y * NODE_DISTANCE - 15, 30, 30);
        } else {
            g.fillRect(i * NODE_DISTANCE - 18, y * NODE_DISTANCE - 13, 36, 26);
            g.setColor(Color.BLACK);
            g.drawRect(i * NODE_DISTANCE - 18, y * NODE_DISTANCE - 13, 36, 26);
        }
    	g.setFont(FONT);
        g.setColor(Color.BLACK);
        g.drawString(treeStructure.getAndOrTree().getContent().toString(), 
                i * NODE_DISTANCE - 11, y * NODE_DISTANCE + 5);
    }
  
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Integer y = 1;
        graphics.translate(this.getInsets().left, this.getInsets().top);
        int i = drawTree(graphics, treeStructure.getAndOrTree(), y, NODE_DISTANCE, FONT);
        nodesPosition = 1;
        if (treeStructure.getAndOrTree().isExecuted()) {
           drawRootExecutedTask(i, graphics, y);
        } else {
           drawRootNotExecutedTask(i, graphics, y);
        }
        setPreferredSize(new Dimension((area + 2) * NODE_DISTANCE, area * NODE_DISTANCE));
     }
    
    private void drawExecutedTask(LinkedList<AndOrTree> childs, LinkedList<Integer> childsCoordinates,
    		Graphics graphics, Font f, Integer tam, Integer y){
    	graphics.setColor(Color.BLACK);
        if (childs.getFirst().getLeftChild() == null) {
            graphics.fillOval((childsCoordinates.getFirst() * tam) - 10, (y + 2) * tam - 10, 21, 21);
            graphics.setFont(f);
            graphics.setColor(Color.BLACK);
            graphics.drawOval((childsCoordinates.getFirst() * tam) - 10,
                    (y + 2) * tam - 10, 20, 20);
            graphics.drawString(childs.getFirst().getContent().toString(),
                    (childsCoordinates.getFirst() * tam) - 11,
                    (y + 2) * tam + 25);
        } else {
            if (childs.getFirst().isAndTask()) {
                graphics.fillOval((childsCoordinates.getFirst() * tam) - 15, (y + 2) * tam - 15, 31, 31);
                graphics.setColor(Color.BLACK);
                graphics.drawOval((childsCoordinates.getFirst() * tam)
                        - 15, (y + 2) * tam - 15, 30, 30);
                graphics.setColor(Color.WHITE);
                graphics.setFont(f);
                graphics.drawString(childs.getFirst().getContent()
                        .toString(), 
                        (childsCoordinates.getFirst() * tam) 
                                - 11, (y + 2) * tam + 5);
            } else {
                graphics.fillRect((childsCoordinates.getFirst() 
                        * tam - 18), (y + 2) * tam - 13, 36, 26);
                graphics.setColor(Color.BLACK);
                graphics.drawRect((childsCoordinates.getFirst() 
                        * tam - 18), (y + 2) * tam - 13, 36, 26);
                graphics.setFont(f);
                graphics.setColor(Color.WHITE);
                graphics.drawString(childs.getFirst().getContent()
                        .toString(), 
                        (childsCoordinates.getFirst() * tam) - 11,
                        (y + 2) * tam + 5);
            }
        }
    }
    
    private void drawNotExecutedTask(LinkedList<AndOrTree> childs, LinkedList<Integer> childsCoordinates,
    		Graphics graphics, Font f, Integer tam, Integer y){
    	graphics.setColor(Color.WHITE);
        if (childs.getFirst().getLeftChild() == null) {
            graphics.fillOval((childsCoordinates.getFirst() * tam) - 10,
                    (y + 2) * tam - 10, 21, 21);
            graphics.setFont(f);
            graphics.setColor(Color.BLACK);
            graphics.drawOval((childsCoordinates.getFirst() * tam) - 10,
                    (y + 2) * tam - 10, 20, 20);
            graphics.drawString(childs.getFirst().getContent().toString(),
                    (childsCoordinates.getFirst() * tam) - 11,
                    (y + 2) * tam + 25);
        } else {
            if (childs.getFirst().isAndTask()) {
                graphics.fillOval((childsCoordinates.getFirst() * tam) - 15,
                        (y + 2) * tam - 15, 31, 31);
                graphics.setColor(Color.BLACK);
                graphics.drawOval((childsCoordinates.getFirst() * tam) - 15,
                        (y + 2) * tam - 15, 30, 30);
                graphics.setFont(f);
                graphics.setColor(Color.BLACK);
                graphics.drawString(childs.getFirst().getContent().toString(),
                        (childsCoordinates.getFirst() * tam) - 11,
                        (y + 2) * tam + 5);
            } else {
                graphics.fillRect((childsCoordinates.getFirst() * tam - 18),
                        (y + 2) * tam - 13, 36, 26);
                graphics.setColor(Color.BLACK);
                graphics.drawRect((childsCoordinates.getFirst() * tam - 18),
                        (y + 2) * tam - 13, 36, 26);
                graphics.setFont(f);
                graphics.setColor(Color.BLACK);
                graphics.drawString(childs.getFirst().getContent().toString(),
                        (childsCoordinates.getFirst() * tam) - 11, 
                        (y + 2) * tam + 5);
            }
        }
    }
    
    private Integer drawTree(Graphics graphics, AndOrTree a, Integer y, Integer tam, Font f) {
        LinkedList<Integer> childsCoordinates = new LinkedList<>();
        Integer position = nodesPosition;
        LinkedList<AndOrTree> childs = (LinkedList<AndOrTree>) a.getChildren();
        if (a.getLeftChild() != null) {
            while (!childs.isEmpty()) {
                childsCoordinates.addLast(drawTree(graphics, childs.getFirst(), y + 2, tam, f));
                childs.removeFirst();
                nodesPosition++;
            }
            nodesPosition--;
            childs = (LinkedList<AndOrTree>) a.getChildren();
            if ((childsCoordinates.size() % 2) != 0) {
                position = childsCoordinates.get((childsCoordinates.size() / 2));
            } else {
                position = (((childsCoordinates.getLast() - childsCoordinates.getFirst()) / 2)
                        + childsCoordinates.getFirst());
            }
            while (!childsCoordinates.isEmpty()) {
                graphics.setColor(Color.BLACK);
                graphics.drawLine(position * tam, y * tam, childsCoordinates.getFirst() * tam, (y + 2) * tam);
                if (childs.getFirst().isExecuted()) {
                	drawExecutedTask(childs, childsCoordinates, graphics, f, tam, y);
                } else {
                	drawNotExecutedTask(childs, childsCoordinates, graphics, f, tam, y);
                }
                childs.removeFirst();
                childsCoordinates.removeFirst();
            }
        }
        area = nodesPosition;
        return position;
    }
}
