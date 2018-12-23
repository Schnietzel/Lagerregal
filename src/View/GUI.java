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
import java.util.concurrent.Flow;

import Controller.*;
import Model.Lager;
import Model.Lieferung;

import static javax.swing.UIManager.*;

public class GUI extends JFrame {
	private Lagerverwaltung lagerverwaltung;
	
    private JPanel outerpanel; 
    private JPanel innerpanel;
    private JTabbedPane tabs;


    private boolean lieferungAktiv = false;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException
    {
    	Lagerverwaltung.initLagerverwaltung();
    	Buchungsverwaltung.initBuchungsverwaltung();

    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

    	GUI gui = new GUI();
    }

    public GUI() {
    	initGUI();

    	this.setContentPane(outerpanel);
        this.setTitle("LieferungsTool für Mertens v1337");
        this.pack();
      	this.setVisible(true);
      	this.setMinimumSize(new Dimension(600, 500));
      	this.validate();
      	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI()
    {
    	// TODO: GridLayout schön machen
    	outerpanel = new JPanel();
        outerpanel.setLayout(new GridLayout());

        innerpanel = new JPanel();
        innerpanel.setLayout(new GridLayout());
        outerpanel.add(innerpanel);

        tabs = new JTabbedPane();
        innerpanel.add(tabs);

        tabs.addTab("Lageransicht", new AnsichtTab());
        tabs.addTab("Lieferung", new LieferungTab());
        tabs.addTab("Historie", new HistorieTab());
        this.setPreferredSize(new Dimension(450,500));
    }

}
