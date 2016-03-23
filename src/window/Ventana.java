package window;


/**
 * @(#)Ventana.java
 *
 *
 * @autor: Carlos Mazariegos #19.564.966 Erika Hernandez #20.163.814
 *
 * @Seccion: 1
 *
 * @version 1.00
 */

//Esta Clase contiene las funciones y subclases necesarias
//para implementar la Ventana
//donde se realizaran las tareas asociadas al AndOrTree
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import andortree.AndOrTree;

public class Ventana extends JFrame { //Inicio class Ventana

    /**
     * ********************* Componentes de la Ventana *******************
     */
    JMenuBar menubar = new JMenuBar();
    JMenu menu1 = new JMenu("Archivo");
    JMenuItem abrir = new JMenuItem("Abrir");

    JButton contaratom = new JButton("Contar Atomica");
    JButton haceratom = new JButton("Hacer Atomica");
    JButton deshatom = new JButton("Deshacer Atomica");
    JButton minatom = new JButton("Minimo Atomico");
    JButton imprimir = new JButton("Imprimir Arbol");
    JButton reporte = new JButton("Imprimir Reporte");

    Box horizontal1 = Box.createHorizontalBox();
    Box horizontal2 = Box.createHorizontalBox();
    JTabbedPane tabs = new JTabbedPane(
            JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

    /**
     * ********************* Ventanas Secundarias *************************
     */
    JFileChooser Abrir = new JFileChooser(); //Para Abrir Archivo

    /**
     * ********************* Arbol         *************************
     */
    AndOrTree arbol = new AndOrTree();	//Arbol

    /**
     * *************** Constructor de la Ventana Principal ****************
     */
    Ventana() {
        super("Arbol AND - OR");		//Titulo de la ventana

        /**
         * *************** Agregando elementos al Menu ******************
         */
        menubar.add(menu1);
        menu1.add(abrir);
        setJMenuBar(menubar);

        /**
         * **************** Eventos del Menu de Opciones ****************
         */
        abrir.addActionListener(new abrir()); //Para Abrir un archivo

        contaratom.addActionListener(new contaratom());
        haceratom.addActionListener(new haceratom());
        deshatom.addActionListener(new deshatom());
        minatom.addActionListener(new minatom());
        imprimir.addActionListener(new imprimir());
        reporte.addActionListener(new reporte());

        /**
         * **************** Agregando Botones al Box *********************
         */
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(contaratom);
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(haceratom);
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(deshatom);
        horizontal1.add(Box.createHorizontalGlue());
        horizontal1.add(minatom);

        horizontal2.add(Box.createHorizontalGlue());
        horizontal2.add(imprimir);
        horizontal2.add(Box.createHorizontalGlue());
        horizontal2.add(reporte);
        horizontal2.add(Box.createHorizontalGlue());

        /**
         * *************** Agregando Box a la ventana ****************
         */
        tabs.addTab("Tareas Atomicas", horizontal1);
        tabs.addTab("Imprimir Resultados", horizontal2);
        this.add(tabs, BorderLayout.CENTER);

        /**
         * ************** atributos de la ventana *********************
         */
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(550, 350);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * ************************* Eventos ********************************
     */
	//Los siguientes son los eventos asociados a los botones del BOX
    /**
     * ***************** Operaciones del AndOrTree ******************
     */
    class contaratom implements ActionListener { //Inicio class contaratom

        public void actionPerformed(ActionEvent ae) {
            if (!arbol.isEmpty()) {    //Cuenta Tareas Atomicas ejecutadas
                JOptionPane.showMessageDialog(null,
                        "La catidad de tareas atomicas ejecutadas es: " + arbol.getAtomicCount(),
                        "Contar Atomicas", JOptionPane.INFORMATION_MESSAGE);
            } else {	//Mensaje de Error
                JOptionPane.showMessageDialog(null,
                        "Debe cargar un arbol antes de realizar una operacion",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//Fin class contaratom

    class haceratom implements ActionListener {	//Inicio class haceratom

        public void actionPerformed(ActionEvent ae) {
            if (!arbol.isEmpty()) {    //Ejecuta una tarea Atomica dada
                String tarea;
                tarea = new String();
                JOptionPane cuadro_dialogo = new JOptionPane();
                tarea
                        = cuadro_dialogo.showInputDialog("Introduzca el identificador de la tarea que desea realizar: ");
                if (!(tarea == null)) {
                    if (arbol.isAtomic(tarea)) {
                        arbol.doAtomicTask(tarea);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "La tarea seleccionada no es una tarea Atomica",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    cuadro_dialogo.setOptionType(JOptionPane.CLOSED_OPTION);
                }
            } else {	//Mensaje de Error
                JOptionPane.showMessageDialog(null,
                        "Debe cargar un arbol antes de realizar una operacion",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//Fin class haceratom

    class deshatom implements ActionListener { //Inicio class deshatom

        public void actionPerformed(ActionEvent ae) {
            if (!arbol.isEmpty()) { //Deshace una tarea Atomica dada
                Object tarea = new Object();
                JOptionPane cuadro_dialogo = new JOptionPane();
                tarea
                        = cuadro_dialogo.showInputDialog("Introduzca el identificador de la tarea que desea deshacer: ");
                if (!(tarea == null)) {
                    if (arbol.isAtomic(tarea)) {
                        arbol.undoAtomicTask(tarea);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "La tarea seleccionada no es una tarea Atomica",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    cuadro_dialogo.setOptionType(JOptionPane.CLOSED_OPTION);
                }
            } else {	//Mensaje de Error
                JOptionPane.showMessageDialog(null,
                        "Debe cargar un arbol antes de realizar una operacion",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//Fin class deshatom

    class minatom implements ActionListener {   //Inicio class minaton

        public void actionPerformed(ActionEvent ae) {
            if (!arbol.isEmpty()) {  //Muestra el minimo atomico
                JOptionPane.showMessageDialog(null,
                        "El minimo atomico es: " + arbol.getAtomicMinimun(),
                        "Minimo Atomico", JOptionPane.INFORMATION_MESSAGE);
            } else {	//Mensaje de Error
                JOptionPane.showMessageDialog(null,
                        "Debe cargar un arbol antes de realizar una operacion",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//Fin class deshatom

    class reporte implements ActionListener { //Inicio class reporte

        public void actionPerformed(ActionEvent ae) {
            if (!arbol.isEmpty()) {	//Imprime un reporte por archivo
                JOptionPane cuadro_dialogo = new JOptionPane();
                String nombre = JOptionPane.showInputDialog("Indique el nombre del Archivo de salida (con Extension): ");
                if (!(nombre == null)) {
                    try {
                        FileWriter fw = new FileWriter(nombre);
                        try (BufferedWriter salida = new BufferedWriter(new BufferedWriter(fw))) {
                            LinkedList<String> list = new LinkedList<>();
                            int i = 0;
                            arbol.preorder(list);
                            while (i < list.size()) {
                                salida.write(list.get(i));
                                salida.write(" ");
                                i++;
                            }
                            salida.write("\n");
                            i = 0;
                            while (i < list.size()) {
                                salida.write(list.get(i) + " " + arbol.getTaskType(list.get(i))
                                        + " " + arbol.isExecuted(list.get(i)) + "\n");
                                i++;
                            }
                        }
                    } catch (IOException ioException) {	//Mensaje de Error
                        JOptionPane.showMessageDialog(null, "No se pudo crear el archivo",
                                "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    cuadro_dialogo.setOptionType(JOptionPane.CLOSED_OPTION);
                }
            } else {								//Mensaje de Error
                JOptionPane.showMessageDialog(null, "Debe cargar un arbol antes de realizar una operacion",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//Fin class reporte

    //Evento Asociado Al boton Abrir del MenuBar
    class abrir implements ActionListener {    //Inicio class abrir

        public void actionPerformed(ActionEvent ae) {
            try {		 //Abrir Archivo
                StringTokenizer op;
                String linea, lin, raiz, tipo, e = new String("");
                LinkedList<String> l = new LinkedList<>(), 
                        lista = new LinkedList<>();
                int retval = Abrir.showOpenDialog(Ventana.this), tareas, act;
                File workingDirectory = new File(System.getProperty("user.dir"));
                Abrir.setCurrentDirectory(workingDirectory);
                if (retval == JFileChooser.APPROVE_OPTION) {
                    File name = Abrir.getSelectedFile();
                    BufferedReader entrada = new BufferedReader(new FileReader(name));
                    if (name.exists()) {
                        linea = entrada.readLine();
                        for (int i = 0; i < linea.length(); i++) {
                            lista.addLast(e.valueOf(linea.charAt(i)));
                        }

                        arbol.createAndOrTree(lista);
                        lin = entrada.readLine();
                        tareas = Integer.valueOf(lin).intValue();
                        for (int i = 0; i < tareas; i++) {
                            lin = entrada.readLine();
                            op = new StringTokenizer(lin);
                            raiz = op.nextToken();
                            tipo = op.nextToken();
                            act = Integer.valueOf(op.nextToken()).intValue();
                            if (act == 1) {
                                arbol.executeTask(raiz);
                            }
                            if (tipo.equals("and")) {
                                arbol.changeToAndTask(raiz);
                            } else {
                                arbol.changeToOrTask(raiz);
                            }
                        }
                        entrada.close();
                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog(null, "El archivo no existe",
                        "Error", JOptionPane.WARNING_MESSAGE);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "El archivo no existe",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//Fin class abrir

    /**
     * ******************** Clase Para Imprimir el arbol ****************
     */
    //Esta clase contiene todas las Subclases asociadas a la ceacion de la Nueva
    //Ventana necesaria para la impresion del AndOrTree, contiene, ademas, los even-
    //tos, funciones, y otros, asociados a la ventana
    class imprimir implements ActionListener {	//Inicio class imprimir

        JFrame aplicacion = new JFrame();	//Nueva Ventana donde se imprimira el arbol
        Dibujar dibujo = new Dibujar();	//Dibuja el Arbol
        JScrollPane barra = new JScrollPane(dibujo);	//Barra de la Ventana
        JButton actualizar = new JButton("Actualizar Arbol");	//Boton actualizar
        JLabel info = new JLabel("Pesione Actualizar Arbol para visualizar las tareas ejecutadas recientemente");

        public void actionPerformed(ActionEvent ae) {	//Crea la Nueva Ventana
            if (!arbol.isEmpty()) {
                aplicacion.setTitle("Imprimir Arbol");
                aplicacion.setSize(800, 550);

                barra.setPreferredSize(new Dimension(100, 100));

                actualizar.addActionListener(new actualizar());
                aplicacion.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
                aplicacion.add(actualizar, BorderLayout.NORTH);
                aplicacion.add(barra, BorderLayout.CENTER);
                aplicacion.add(info, BorderLayout.SOUTH);
                aplicacion.setVisible(true);
            } else {			//Imprime mensaje de Error, en caso de que no se ha cargado un arbol
                JOptionPane.showMessageDialog(null, "Debe cargar un arbol antes de realizar una operacion",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }

        // La siguiente Subclase dibuja el arbol en un JPanel
        public class Dibujar extends JPanel //Inicio class Dibujar
        {

            Integer x = new Integer(1), Area = new Integer(1);

            public void paintComponent(Graphics g) {
                Font f = new Font("Arial Bold", Font.BOLD, 12);
                Integer y = 1, cons = 35, i;
                this.setBackground(Color.LIGHT_GRAY);
                super.paintComponent(g);
                g.translate(this.getInsets().left, this.getInsets().top);
                barra.getHorizontalScrollBar().addAdjustmentListener(new Refrescar());
                barra.getVerticalScrollBar().addAdjustmentListener(new Refrescar());
                i = dibujarArbol(g, arbol, y = 1, cons, f);
                x = 1;
                if (arbol.isExecuted()) {
                    if (arbol.isAndTask()) {
                        g.setColor(Color.BLACK);
                        g.fillOval(i * cons - 15, y * cons - 15, 31, 31);
                        g.setColor(Color.BLACK);
                        g.drawOval(i * cons - 15, y * cons - 15, 30, 30);
                        g.setFont(f);
                        g.setColor(Color.WHITE);
                        g.drawString(arbol.getContent().toString(), i * cons - 11, y * cons + 5);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(i * cons - 18, y * cons - 13, 36, 26);
                        g.setColor(Color.BLACK);
                        g.drawRect(i * cons - 18, y * cons - 13, 36, 26);
                        g.setFont(f);
                        g.setColor(Color.WHITE);
                        g.drawString(arbol.getContent().toString(), i * cons - 11, y * cons + 5);
                    }
                } else {
                    if (arbol.isAndTask()) {
                        g.setColor(Color.WHITE);
                        g.fillOval(i * cons - 15, y * cons - 15, 31, 31);
                        g.setColor(Color.BLACK);
                        g.drawOval(i * cons - 15, y * cons - 15, 30, 30);
                        g.setFont(f);
                        g.setColor(Color.BLACK);
                        g.drawString(arbol.getContent().toString(), i * cons - 11, y * cons + 5);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(i * cons - 18, y * cons - 13, 36, 26);
                        g.setColor(Color.BLACK);
                        g.drawRect(i * cons - 18, y * cons - 13, 36, 26);
                        g.setColor(Color.BLACK);
                        g.setFont(f);
                        g.drawString(arbol.getContent().toString(), i * cons - 11, y * cons + 5);
                    }
                }
                this.setPreferredSize(new Dimension((Area + 2) * cons, Area * cons));
                setSize(2000, 2000);
            }

            public Integer dibujarArbol(Graphics g, AndOrTree a, Integer y, Integer tam, Font f) {
                LinkedList<AndOrTree> hijos = new LinkedList<AndOrTree>();
                LinkedList<Integer> coor_hijos = new LinkedList<Integer>();
                int xx = x;
                hijos = (LinkedList<AndOrTree>) a.getChildren();
                if (a.getLeftChild() != null) {
                    while (hijos.size() > 0) {
                        coor_hijos.addLast(dibujarArbol(g, hijos.getFirst(), y + 2, tam, f));
                        hijos.removeFirst();
                        x++;
                    }
                    x--;
                    hijos = new LinkedList<AndOrTree>();
                    hijos = a.getChildren();
                    if ((coor_hijos.size() % 2) != 0) {
                        xx = coor_hijos.get((coor_hijos.size() / 2));
                    } else {
                        xx = (((coor_hijos.getLast() - coor_hijos.getFirst()) / 2)
                                + coor_hijos.getFirst());
                    }
                    while (coor_hijos.size() > 0) {
                        g.setColor(Color.BLACK);
                        g.drawLine(xx * tam, y * tam, coor_hijos.getFirst() * tam, (y + 2) * tam);
                        if (hijos.getFirst().isExecuted()) {
                            if (hijos.getFirst().getLeftChild() == null) {
                                g.setColor(Color.BLACK);
                                g.fillOval((coor_hijos.getFirst() * tam) - 10, (y + 2) * tam - 10, 21, 21);
                                g.setFont(f);
                                g.setColor(Color.BLACK);
                                g.drawOval((coor_hijos.getFirst() * tam) - 10, (y + 2) * tam - 10, 20, 20);
                                g.drawString(hijos.getFirst().getContent().toString(), (coor_hijos.getFirst() * tam) - 11, (y + 2) * tam + 25);
                            } else {
                                if (hijos.getFirst().isAndTask()) {
                                    g.setColor(Color.BLACK);
                                    g.fillOval((coor_hijos.getFirst() * tam) - 15, (y + 2) * tam - 15, 31, 31);
                                    g.setColor(Color.BLACK);
                                    g.drawOval((coor_hijos.getFirst() * tam) - 15, (y + 2) * tam - 15, 30, 30);
                                    g.setColor(Color.WHITE);
                                    g.setFont(f);
                                    g.drawString(hijos.getFirst().getContent().toString(), (coor_hijos.getFirst() * tam) - 11, (y + 2) * tam + 5);
                                } else {
                                    g.setColor(Color.BLACK);
                                    g.fillRect((coor_hijos.getFirst() * tam - 18), (y + 2) * tam - 13, 36, 26);
                                    g.setColor(Color.BLACK);
                                    g.drawRect((coor_hijos.getFirst() * tam - 18), (y + 2) * tam - 13, 36, 26);
                                    g.setFont(f);
                                    g.setColor(Color.WHITE);
                                    g.drawString(hijos.getFirst().getContent().toString(), (coor_hijos.getFirst() * tam) - 11, (y + 2) * tam + 5);
                                }
                            }
                        } else {
                            if (hijos.getFirst().getLeftChild() == null) {
                                g.setColor(Color.WHITE);
                                g.fillOval((coor_hijos.getFirst() * tam) - 10, (y + 2) * tam - 10, 21, 21);
                                g.setFont(f);
                                g.setColor(Color.BLACK);
                                g.drawOval((coor_hijos.getFirst() * tam) - 10, (y + 2) * tam - 10, 20, 20);
                                g.drawString(hijos.getFirst().getContent().toString(), (coor_hijos.getFirst() * tam) - 11, (y + 2) * tam + 25);
                            } else {
                                if (hijos.getFirst().isAndTask()) {
                                    g.setColor(Color.WHITE);
                                    g.fillOval((coor_hijos.getFirst() * tam) - 15, (y + 2) * tam - 15, 31, 31);
                                    g.setColor(Color.BLACK);
                                    g.drawOval((coor_hijos.getFirst() * tam) - 15, (y + 2) * tam - 15, 30, 30);
                                    g.setFont(f);
                                    g.setColor(Color.BLACK);
                                    g.drawString(hijos.getFirst().getContent().toString(), (coor_hijos.getFirst() * tam) - 11, (y + 2) * tam + 5);
                                } else {
                                    g.setColor(Color.WHITE);
                                    g.fillRect((coor_hijos.getFirst() * tam - 18), (y + 2) * tam - 13, 36, 26);
                                    g.setColor(Color.BLACK);
                                    g.drawRect((coor_hijos.getFirst() * tam - 18), (y + 2) * tam - 13, 36, 26);
                                    g.setFont(f);
                                    g.setColor(Color.BLACK);
                                    g.drawString(hijos.getFirst().getContent().toString(), (coor_hijos.getFirst() * tam) - 11, (y + 2) * tam + 5);
                                }
                            }
                        }
                        hijos.removeFirst();
                        coor_hijos.removeFirst();
                    }
                }
                Area = x;
                return xx;
            }
        }//Fin class Dibujar

        //Redibuja el Arbol en caso de que muevan las barras
        public class Refrescar implements AdjustmentListener { //Inicio class Refrescar  

            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaint();
            }
        }//Fin class Refrescar

    //Actualiza el Arbol en caso de que se haya ejecutado o no alguna tarea
        //Presionando el boton actualizar
        class actualizar implements ActionListener { //Inicio class actualizar

            public void actionPerformed(ActionEvent ae) {
                aplicacion.repaint();
            }
        }//Fin class actualizar
    }//Fin class imprimir

    /**
     * **************************** MAIN ***************************
     */
    public static void main(String args[]) {
        Ventana ventana = new Ventana();
    }

}//Fin class Ventana
