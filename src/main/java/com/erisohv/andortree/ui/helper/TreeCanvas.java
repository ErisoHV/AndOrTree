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
    private Integer x = 1;
    private Integer area = 1;
    private Font FONT = new Font("Arial Bold", Font.BOLD, 12);
    private Integer nodeDistance = 35;
    
    TreeStructureLoader treeStructure = new TreeStructureLoader();
    
    public TreeCanvas(TreeStructureLoader tree){
        setSize(2000, 2000);
        treeStructure = tree;
        this.setBackground(Color.LIGHT_GRAY);
        repaint();
    }
  
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Integer y = 1;
        g.translate(this.getInsets().left, this.getInsets().top);
        Integer i = dibujarArbol(g, treeStructure.getAndOrTree(), y = 1, nodeDistance, FONT);
        x = 1;
        if (treeStructure.getAndOrTree().isExecuted()) {
            if (treeStructure.getAndOrTree().isAndTask()) {
                g.setColor(Color.BLACK);
                g.fillOval(i * nodeDistance - 15, y * nodeDistance - 15, 31, 31);
                g.setColor(Color.BLACK);
                g.drawOval(i * nodeDistance - 15, y * nodeDistance - 15, 30, 30);
                g.setFont(FONT);
                g.setColor(Color.WHITE);
                g.drawString(treeStructure.getAndOrTree().getContent().toString(), 
                        i * nodeDistance - 11, y * nodeDistance + 5);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(i * nodeDistance - 18, y * nodeDistance - 13, 36, 26);
                g.setColor(Color.BLACK);
                g.drawRect(i * nodeDistance - 18, y * nodeDistance - 13, 36, 26);
                g.setFont(FONT);
                g.setColor(Color.WHITE);
                g.drawString(treeStructure.getAndOrTree().getContent().toString(), 
                        i * nodeDistance - 11, y * nodeDistance + 5);
            }
        } else {
            if (treeStructure.getAndOrTree().isAndTask()) {
                g.setColor(Color.WHITE);
                g.fillOval(i * nodeDistance - 15, y * nodeDistance - 15, 31, 31);
                g.setColor(Color.BLACK);
                g.drawOval(i * nodeDistance - 15, y * nodeDistance - 15, 30, 30);
                g.setFont(FONT);
                g.setColor(Color.BLACK);
                g.drawString(treeStructure.getAndOrTree().getContent().toString(), 
                        i * nodeDistance - 11, y * nodeDistance + 5);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(i * nodeDistance - 18, y * nodeDistance - 13, 36, 26);
                g.setColor(Color.BLACK);
                g.drawRect(i * nodeDistance - 18, y * nodeDistance - 13, 36, 26);
                g.setColor(Color.BLACK);
                g.setFont(FONT);
                g.drawString(treeStructure.getAndOrTree().getContent().toString(), 
                        i * nodeDistance - 11, y * nodeDistance + 5);
            }
        }
        setPreferredSize(new Dimension((area + 2) * nodeDistance, 
                area * nodeDistance));
     }
    
    private Integer dibujarArbol(Graphics g, AndOrTree a, Integer y, Integer tam, Font f) {
                LinkedList<Integer> childsCoordinates = new LinkedList<>();
                Integer position = x;
                LinkedList<AndOrTree> childs = 
                        (LinkedList<AndOrTree>) a.getChildren();
                if (a.getLeftChild() != null) {
                    while (childs.size() > 0) {
                        childsCoordinates.addLast(dibujarArbol(g, 
                                childs.getFirst(), y + 2, tam, f));
                        childs.removeFirst();
                        x++;
                    }
                    x--;
                    childs = a.getChildren();
                    if ((childsCoordinates.size() % 2) != 0) {
                        position = childsCoordinates
                                .get((childsCoordinates.size() / 2));
                    } else {
                        position = (((childsCoordinates.getLast() 
                                - childsCoordinates.getFirst()) / 2)
                                + childsCoordinates.getFirst());
                    }
                    while (childsCoordinates.size() > 0) {
                        g.setColor(Color.BLACK);
                        g.drawLine(position * tam, y * tam, 
                                childsCoordinates.getFirst() * tam, (y + 2) * tam);
                        if (childs.getFirst().isExecuted()) {
                            if (childs.getFirst().getLeftChild() == null) {
                                g.setColor(Color.BLACK);
                                g.fillOval((childsCoordinates.getFirst() * tam) - 10,
                                        (y + 2) * tam - 10, 21, 21);
                                g.setFont(f);
                                g.setColor(Color.BLACK);
                                g.drawOval((childsCoordinates.getFirst() * tam) - 10,
                                        (y + 2) * tam - 10, 20, 20);
                                g.drawString(childs.getFirst().getContent().toString(),
                                        (childsCoordinates.getFirst() * tam) - 11,
                                        (y + 2) * tam + 25);
                            } else {
                                if (childs.getFirst().isAndTask()) {
                                    g.setColor(Color.BLACK);
                                    g.fillOval((childsCoordinates.getFirst() * tam)
                                            - 15, (y + 2) * tam - 15, 31, 31);
                                    g.setColor(Color.BLACK);
                                    g.drawOval((childsCoordinates.getFirst() * tam)
                                            - 15, (y + 2) * tam - 15, 30, 30);
                                    g.setColor(Color.WHITE);
                                    g.setFont(f);
                                    g.drawString(childs.getFirst().getContent()
                                            .toString(), 
                                            (childsCoordinates.getFirst() * tam) 
                                                    - 11, (y + 2) * tam + 5);
                                } else {
                                    g.setColor(Color.BLACK);
                                    g.fillRect((childsCoordinates.getFirst() 
                                            * tam - 18), (y + 2) * tam - 13, 36, 26);
                                    g.setColor(Color.BLACK);
                                    g.drawRect((childsCoordinates.getFirst() 
                                            * tam - 18), (y + 2) * tam - 13, 36, 26);
                                    g.setFont(f);
                                    g.setColor(Color.WHITE);
                                    g.drawString(childs.getFirst().getContent()
                                            .toString(), 
                                            (childsCoordinates.getFirst() * tam) - 11,
                                            (y + 2) * tam + 5);
                                }
                            }
                        } else {
                            if (childs.getFirst().getLeftChild() == null) {
                                g.setColor(Color.WHITE);
                                g.fillOval((childsCoordinates.getFirst() * tam) - 10,
                                        (y + 2) * tam - 10, 21, 21);
                                g.setFont(f);
                                g.setColor(Color.BLACK);
                                g.drawOval((childsCoordinates.getFirst() * tam) - 10,
                                        (y + 2) * tam - 10, 20, 20);
                                g.drawString(childs.getFirst().getContent().toString(),
                                        (childsCoordinates.getFirst() * tam) - 11,
                                        (y + 2) * tam + 25);
                            } else {
                                if (childs.getFirst().isAndTask()) {
                                    g.setColor(Color.WHITE);
                                    g.fillOval((childsCoordinates.getFirst() * tam) - 15,
                                            (y + 2) * tam - 15, 31, 31);
                                    g.setColor(Color.BLACK);
                                    g.drawOval((childsCoordinates.getFirst() * tam) - 15,
                                            (y + 2) * tam - 15, 30, 30);
                                    g.setFont(f);
                                    g.setColor(Color.BLACK);
                                    g.drawString(childs.getFirst().getContent().toString(),
                                            (childsCoordinates.getFirst() * tam) - 11,
                                            (y + 2) * tam + 5);
                                } else {
                                    g.setColor(Color.WHITE);
                                    g.fillRect((childsCoordinates.getFirst() * tam - 18),
                                            (y + 2) * tam - 13, 36, 26);
                                    g.setColor(Color.BLACK);
                                    g.drawRect((childsCoordinates.getFirst() * tam - 18),
                                            (y + 2) * tam - 13, 36, 26);
                                    g.setFont(f);
                                    g.setColor(Color.BLACK);
                                    g.drawString(childs.getFirst().getContent().toString(),
                                            (childsCoordinates.getFirst() * tam) - 11, 
                                            (y + 2) * tam + 5);
                                }
                            }
                        }
                        childs.removeFirst();
                        childsCoordinates.removeFirst();
                    }
                }
                area = x;
                return position;
            }
}
