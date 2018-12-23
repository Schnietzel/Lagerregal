package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;


import Controller.*;
import Model.Lager;
import Model.Lieferung;

import static javax.swing.UIManager.*;

public class GUI extends JFrame implements WindowListener {
    private static Lagerverwaltung lv;
    private static Dateiverwaltung dv;
    private static Buchungsverwaltung bv;

    private JPanel rootPanel;
    private JTabbedPane tabs;
    private JMenuBar menuBar;
    private JMenu menuDatei;
    private JMenuItem miNeu;
    private JMenuItem miOeffnen;
    private JMenuItem miSpeichern;
    private JMenuItem miVirus;
    private JMenu menuEdit;
    private JMenuItem miUndo;
    private JMenuItem miRedo;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
        lv = ControllerSingleton.getLVInstance();
        dv = ControllerSingleton.getDVInstance();
        bv = ControllerSingleton.getBVInstance();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        GUI gui = new GUI();
    }

    public GUI() {
        initGUI();

        this.setContentPane(rootPanel);
        this.setTitle("LieferungsTool für Mertens v1337");
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(600, 500));
        this.validate();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        // TODO: GridLayout schön machen
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());

        this.setJMenuBar(createMenubar());

        tabs = new JTabbedPane();
        rootPanel.add(tabs);
        
        AnsichtTab aTab = new AnsichtTab();
        LieferungTab lTab = new LieferungTab();
        HistorieTab hTab = new HistorieTab();
        
        tabs.addTab("Lageransicht", aTab);
        tabs.addTab("Lieferung", lTab);
        tabs.addTab("Historie", hTab);
        
        this.setPreferredSize(new Dimension(450, 500));
        
        addWindowListener(this);
    }

    private JMenuBar createMenubar() {
        menuBar = new JMenuBar();
        menuDatei = new JMenu("Datei");
        menuDatei.setMnemonic(KeyEvent.VK_D);
        menuDatei.getAccessibleContext().setAccessibleDescription("Dateiverwaltung");
        menuBar.add(menuDatei);

        miNeu = new JMenuItem("Neue Lagerdatei",
                KeyEvent.VK_N);
        miNeu.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        miNeu.getAccessibleContext().setAccessibleDescription(
                "Neue Lagerdatei anlegen");
        miNeu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Bei STRG+N wird neu angelet!!
                System.out.println("ANGELEGT");
            }
        });
        menuDatei.add(miNeu);
        menuDatei.addSeparator();

        miOeffnen = new JMenuItem("Lagerdatei Öffnen",
                KeyEvent.VK_O);
        miOeffnen.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        miOeffnen.getAccessibleContext().setAccessibleDescription(
                "Bestehende Lagerdatei Öffnen");
        miOeffnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Bei STRG+O wird geöffnet!!
                System.out.println("GEOEFFNET");
            }
        });
        menuDatei.add(miOeffnen);

        miSpeichern = new JMenuItem("Lagerdatei Speichern",
                KeyEvent.VK_S);
        miSpeichern.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        miSpeichern.getAccessibleContext().setAccessibleDescription(
                "Aktuelles Lagerdatei speichern");
        miSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Bei STRG+S wird gespeichert!!
                System.out.println("GESPEICHERT");
            }
        });
        menuDatei.add(miSpeichern);
        menuDatei.addSeparator();

        miVirus = new JMenuItem("Trust me, I'm a dolphin!", KeyEvent.VK_V);
        miVirus.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        miVirus.getAccessibleContext().setAccessibleDescription("Hier passiert schon nichts.");
        miVirus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hier was lustiges einfallen lassen!
                System.out.println("Virus wird installiert...");
            }
        });
        menuDatei.add(miVirus);

        //Zweites Menu
        menuEdit = new JMenu("Ändern");
        menuEdit.setMnemonic(KeyEvent.VK_A);
        menuEdit.getAccessibleContext().setAccessibleDescription("Undo und Redo");
        menuBar.add(menuEdit);

        miUndo = new JMenuItem("Rückgangig machen", KeyEvent.VK_Z);
        miUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        miUndo.getAccessibleContext().setAccessibleDescription("Letzte Aktion Rückgängig machen.");
        miUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hier UNDO: Muss in Lieferung Tab was anderes Machen, als in Ansicht Tab
                System.out.println("UNDO");
            }
        });
        menuEdit.add(miUndo);

        miRedo = new JMenuItem("Wiederherstellen", KeyEvent.VK_Y);
        miRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        miRedo.getAccessibleContext().setAccessibleDescription("Letzte undo Aktion wird rückgängig gemacht.");
        miRedo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hier REDO: Muss in Lieferung Tab was anderes Machen, als in Ansicht Tab
                System.out.println("REDO");
            }
        });
        menuEdit.add(miRedo);

        //TODO:
        //Bug, wenn man menubar offen hat und liste anklickt, gibts nen Nullpointer
        return menuBar;
    }

    @Override
    public void windowClosing(WindowEvent e) 
    {
    	lv.close();
    	bv.close();
    }

	@Override
	public void windowActivated(WindowEvent arg0) { }

	@Override
	public void windowClosed(WindowEvent arg0) { }

	@Override
	public void windowDeactivated(WindowEvent arg0) { }

	@Override
	public void windowDeiconified(WindowEvent arg0) { }

	@Override
	public void windowIconified(WindowEvent arg0) { }

	@Override
	public void windowOpened(WindowEvent arg0) { }
}