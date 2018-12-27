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
    private JMenuItem miLagerImport;
    private JMenuItem miLagerExport;
    private JMenuItem miHistorieImport;
    private JMenuItem miHistorieExport;
    private JMenuItem miVirus;
    private JMenu menuEdit;
    private JMenuItem miUndo;
    private JMenuItem miRedo;

    private AnsichtTab aTab;
    private LieferungTab lTab;
    private HistorieTab hTab;
    
    private static GUI gui;
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
        lv = ControllerSingleton.getLVInstance();
        dv = ControllerSingleton.getDVInstance();
        bv = ControllerSingleton.getBVInstance();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        gui = new GUI();

    }

    public static GUI getGUI()
    {
    	return gui;
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

        aTab = new AnsichtTab();
        lTab = new LieferungTab();
        hTab = new HistorieTab();

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
        
        miLagerImport = new JMenuItem("Lagerdatei importieren",
                KeyEvent.VK_O);
        miLagerImport.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        miLagerImport.getAccessibleContext().setAccessibleDescription(
                "Bestehende Lagerdatei Öffnen");
        miLagerImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lv.setLagerListe(dv.importLager());
                lTab.updateUI();
                // TODO: Updaten
            }
        });
        menuDatei.add(miLagerImport);

        miLagerExport = new JMenuItem("Lagerdatei exportieren",
                KeyEvent.VK_S);
        miLagerExport.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        miLagerExport.getAccessibleContext().setAccessibleDescription(
                "Aktuelles Lagerdatei speichern");
        miLagerExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dv.exportLager(lv.getLagerList());
            }
        });
        menuDatei.add(miLagerExport);
        menuDatei.addSeparator();

        miHistorieImport = new JMenuItem("Historiendatei importieren");
        miHistorieImport.getAccessibleContext().setAccessibleDescription(
                "Bestehende Historiendatei importieren");
        miHistorieImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bv.setHistorie(dv.importHistorie());
                // TODO: Updaten???
            }
        });
        menuDatei.add(miHistorieImport);

        miHistorieExport = new JMenuItem("Historiendatei exportieren");
        miHistorieExport.getAccessibleContext().setAccessibleDescription(
                "Aktuelle Historie exportieren");
        miHistorieExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dv.exportHistorie(bv.getHistorie());
            }
        });
        menuDatei.add(miHistorieExport);
        menuDatei.addSeparator();

        miVirus = new JMenuItem("Trust me, I'm a dolphin!", KeyEvent.VK_V);
        miVirus.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        miVirus.getAccessibleContext().setAccessibleDescription("Hier passiert schon nichts.");
        miVirus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Hier was lustiges einfallen lassen!
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
    public void windowClosing(WindowEvent e) {
        lv.close();
        bv.close();
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }
}