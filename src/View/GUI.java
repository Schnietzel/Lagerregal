package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controller.*;
import Model.Lager;

import static javax.swing.UIManager.*;

public class GUI extends JFrame {
	private Lagerverwaltung lagerverwaltung;
	
    private JPanel outerpanel; 
    private JPanel innerpanel;
    
    
    private JTabbedPane tabs;
   
    private JPanel ansicht;
    private JList aList;
    private JScrollPane aScrollPane;
    private JPanel ansichtPanel;
    private JLabel aName;
    private JLabel aKapazitaet;
    private JTextField aTbName;
    private JTextField aTbKapazitaet;
    private JButton aSpeichern;
    
    private JPanel lieferung; 
    private JPanel lEingabePanel;
    private JPanel lAnsichtPanel;
    private JList lList; 
    private JScrollPane lScrollPane;
    private JTextField lEingabeFeld;
    private JButton lieferungDist;
    private JButton lieferungConfirm;
    private JLabel lLabel;
    private JButton undoButton;
    private JButton redoButton;
     
    private JPanel historie;
    private JList hList;  
    private JScrollPane hScrollPane;
    
   
    private JPanel toolbarPanel;
    private JRadioButton rbEinlieferung;
    private JRadioButton rbAuslieferung;

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

        createListener();
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
        
        initGUIAnsicht();
        initGUILieferung();
        initGUIHistorie();
        

        // TODO: Spacer(?)
//      final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
//      Spacer
//      toolbarPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(14, 50), null, 0, false));
//      final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
//      outerpanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, 1, null, null, null, 0, false));
    }
    
    private void initGUIAnsicht()
    {
    	ansicht = new JPanel();
        ansicht.setLayout(new GridLayout());
        tabs.addTab("Lageransicht", ansicht);
        
        // TODO: Scrollpane
        // TestLager Anzeigen lassen
        
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        getLagerRecursive(Lagerverwaltung.getLagerList(), dlm);
        
        
        aList = new JList<Lager>(dlm);
        ansicht.add(aList);
        
        ansichtPanel = new JPanel();
        ansicht.add(ansichtPanel);

        aName = new JLabel();
        aName.setText("Name:");
        ansicht.add(aName);
        
        aTbName = new JTextField();
        ansicht.add(aTbName);
        
        aKapazitaet = new JLabel();
        aKapazitaet.setText("Kapazität:");
        ansicht.add(aKapazitaet);
        
        aTbKapazitaet = new JTextField();
        ansicht.add(aTbKapazitaet);
        
        aSpeichern = new JButton();
        aSpeichern.setText("Speichern");
        ansicht.add(aSpeichern);
    }
    
    private void initGUILieferung()
    {
    	lieferung = new JPanel();
        lieferung.setLayout(new GridLayout());
        tabs.addTab("Lieferung", lieferung);
        
        lEingabePanel = new JPanel();
        lEingabePanel.setLayout(new GridLayout());
        lieferung.add(lEingabePanel);

        lEingabeFeld = new JTextField();
        lEingabePanel.add(lEingabeFeld);
        
        // TODO: LoadButtonText Ändern
        lieferungDist = new JButton();
        this.$$$loadButtonText$$$(lieferungDist, ResourceBundle.getBundle("String").getString("lieferung.verteilen"));
        lEingabePanel.add(lieferungDist);

        ButtonGroup radioButtons = new ButtonGroup();
        
        rbEinlieferung = new JRadioButton();
        this.$$$loadButtonText$$$(rbEinlieferung, ResourceBundle.getBundle("String").getString("einlieferung"));
        lEingabePanel.add(rbEinlieferung);
        radioButtons.add(rbEinlieferung);

        rbAuslieferung = new JRadioButton();
        this.$$$loadButtonText$$$(rbAuslieferung, ResourceBundle.getBundle("String").getString("auslieferung"));
        lEingabePanel.add(rbAuslieferung);
        radioButtons.add(rbAuslieferung);
        
        // Standard: Einlieferung
        rbEinlieferung.setSelected(true);
        
        lieferungConfirm = new JButton();
        this.$$$loadButtonText$$$(lieferungConfirm, ResourceBundle.getBundle("String").getString("lieferung.bestatigen"));
        lEingabePanel.add(lieferungConfirm);
        lieferungConfirm.setEnabled(false);

        lLabel = new JLabel();
        lLabel.setText("0/0 verteilt.");
        lEingabePanel.add(lLabel);

        lAnsichtPanel = new JPanel();
        lAnsichtPanel.setLayout(new GridLayout());
        lieferung.add(lAnsichtPanel);

        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new GridLayout());
        lieferung.add(toolbarPanel);

        undoButton = new JButton();
        undoButton.setText("Undo");
        toolbarPanel.add(undoButton);

        redoButton = new JButton();
        redoButton.setText("Redo");
        toolbarPanel.add(redoButton);
        
        // TODO: ScrollPanel
        DefaultListModel dlm = new DefaultListModel();
        getLagerRecursive(Lagerverwaltung.getLagerList(), dlm);
        lList = new JList<Lager>(dlm);
        lieferung.add(lList);
    }
    
    private void initGUIHistorie()
    {
    	historie = new JPanel();
        historie.setLayout(new GridLayout());
        tabs.addTab("Historie", historie);
        
        // TODO: Scrollpane
        hList = new JList();
        // TODO: Liste füllen
    }

    private void getLagerRecursive(ArrayList<Lager> lager, DefaultListModel<Lager> dlm)
	{
    	// TODO: Unterlager einrücken
        for(Lager l : lager) 
        {
        	dlm.addElement(l);
        	getLagerRecursive(l.getUnterlager(), dlm);
        }
		
	}

	private void createListener() 
    {
        // Undo
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: UNDO
            }
        });
        
        // Redo
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: REDO
            }
        });

        // Lager-Ansicht
        aList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Lager selected = (Lager) aList.getSelectedValue();
                
                aTbName.setText(selected.getName());
                aTbKapazitaet.setText("" + selected.getKapazitaet());
                
                aTbKapazitaet.setEnabled(selected.getUnterlager().isEmpty());
                
            }
        });
        
        // Lager-Änderung speichern
        aSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Lager selected = (Lager) aList.getSelectedValue();
                
                selected.setName(aTbName.getText());
                selected.setKapazitaet(Integer.parseInt(aTbKapazitaet.getText()));
                
                // TODO: Kapazitäten aller Lager aktualisieren (Observer?)
                aList.updateUI();
            }
        });

        
        // Lieferung verteilen
        lieferungDist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// TODO: Exception Handling
                int gesamtmenge = Integer.parseInt(lEingabeFeld.getText());
                boolean gueltig = Buchungsverwaltung.createLieferung(rbAuslieferung.isSelected(), gesamtmenge);
                if (gueltig) 
                	lGueltigeLieferungsZahl();
                else
                	lUngueltigeZahl();
            }
        });
        
        // Lieferungen-Lager-Liste
        //Event: Doppelklick auf Item in der Liste, Item muss anschließend via Control bearbeitet werden.
        // Aufruf in der LierferungBestätigen
        lList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) 
                {
                	// TODO: Buchung Handlen
                	Lager lager = (Lager) lList.getSelectedValue();
                	if (Buchungsverwaltung.istBearbeitet(lager))
                	{
                		JOptionPane.showMessageDialog(null, "Lager ist bereits bearbeitet! (Ggf. Undo benutzen)");
                		return;
                	}
                	if (!lager.getUnterlager().isEmpty())
                	{
                		JOptionPane.showMessageDialog(null, "Lager ist Oberlager und kann damit nicht zur Buchung verwendet werden!");
                		return;
                	}
                	
            		BuchungDialog bd = new BuchungDialog(lager);
            		boolean gueltig = Buchungsverwaltung.createBuchung(rbAuslieferung.isSelected(), bd.getLager(), bd.getProzent());
            		if (gueltig)
            			lGueltigeBuchungsZahl();
            		else
            			lUngueltigeZahl();
                	// TODO: Handlen ob Gültig oder nicht
                }
            }
        });

        // Lieferung Bestätigen
        lieferungConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//TODO: Lieferung komplett verteilt? - Lieferung ausführen -sicher?
            	boolean gueltig = Buchungsverwaltung.verteileLieferung(rbAuslieferung.isSelected());
            	if (gueltig)
            		lGueltigeLieferung();
            	else
            		lUngueltigeLieferung();
            }
        });
        
        // Historien-Liste
        hList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList hList = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = hList.locationToIndex(e.getPoint());
                }
            }
        });

        // RadioButtons
        rbEinlieferung.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (rbEinlieferung.isSelected()) {
                    lTextEdit("Einlieferung");
                } else lTextEdit("Auslieferung");
            }
        });
    }
    

    public void lTextEdit(String lieferung) {
        lLabel.setText(lieferung + ": " + "Zahl" + "/" + "Zahl" + " verteilt.");
        //TODO: infoText nach Buchung anpassen (muss nach jeder Buchung, jedem schließen des Dialogs, ausgeführt werden)
        //Schema des Texts:
        //Einlieferung: xxxx/xxxx verteilt.
        //Auslieferung: xxxx/xxxx verteilt.

    }
    
    public void lGueltigeLieferungsZahl() 
    {
    	setLieferungAktiv(true);    	
    }

    public void lGueltigeBuchungsZahl()
    {
    	// TODO: Irgendwie in Liste bemerkbar machen
    }
    
    public void lGueltigeLieferung()
    {
    	setLieferungAktiv(false);
    }
    
    public void lUngueltigeZahl() {
        JOptionPane.showMessageDialog(null, "Lagermenge/Kapazität ist nicht ausreichend für diese Buchung");
    }

    public void lUngueltigeLieferung()
    {
    	JOptionPane.showMessageDialog(null, "Es sind noch Artikel nicht verbucht!");
    }
    
    private void setLieferungAktiv(boolean aktiv)
    {
    	lieferungDist.setEnabled(!aktiv);
        lEingabeFeld.setEnabled(!aktiv);
        lList.setEnabled(aktiv);
        rbEinlieferung.setEnabled(!aktiv);
        rbAuslieferung.setEnabled(!aktiv);
        lieferungConfirm.setEnabled(aktiv);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return outerpanel;
    }
}
