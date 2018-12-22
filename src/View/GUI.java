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

    //AnsichtTab
    private JPanel ansicht;
    //Tabellenkram
    private JPanel aListePanel;
    private JScrollPane aScrollPane;
    private JList aList;
    //Bearbeitungszeile
    private JPanel aBearbPanel;
    private JLabel aName;
    private JTextField aTbName;
    private JLabel aKapazitaet;
    private JTextField aTbKapazitaet;
    private JButton aSpeichern;

    //LieferungTab
    private JPanel lieferung;
    //EingabePanel
    private JPanel lEingabePanel;
    private JTextField lEingabeFeld;
    private JLabel lLabel;
    private JRadioButton rbEinlieferung;
    private JRadioButton rbAuslieferung;
    private JButton lieferungDist;
    private JButton lieferungConfirm;
    private JPanel lListePanel;
    private JScrollPane lScrollPane;
    private JList lList;
    private JButton undoButton;
    private JButton redoButton;

    private JPanel historie;
    private JScrollPane hScrollPane;
    private JList hList;

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
        this.setPreferredSize(new Dimension(450,500));


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
    	ansicht.setLayout(new BoxLayout(ansicht, BoxLayout.Y_AXIS));
        tabs.addTab("Lageransicht", ansicht);

        //Panel für die Liste erstellen
        aListePanel = new JPanel(new BorderLayout());
        // TestLager Anzeigen lassen
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        getLagerRecursive(Lagerverwaltung.getLagerList(), dlm);
        //Liste an JList
        aList = new JList<Lager>(dlm);
        aList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        aScrollPane = new JScrollPane(aList);
        //ScrollPane an Panel für Liste
        aListePanel.add(aScrollPane);

        //Panel für Bearbeitungsleiste erstellen
        aBearbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        aName = new JLabel();
        aName.setText("Name:");
        aBearbPanel.add(aName);

        aTbName = new JTextField();
        //Initiales Disablen
        aTbName.setEnabled(false);
        aTbName.setPreferredSize(new Dimension(120,19));
        aBearbPanel.add(aTbName);

        aKapazitaet = new JLabel();
        aKapazitaet.setText("Kapazität:");
        aBearbPanel.add(aKapazitaet);

        aTbKapazitaet = new JTextField();
        //Initiales Disablen
        aTbKapazitaet.setEnabled(false);
        aTbKapazitaet.setPreferredSize(new Dimension(60,19));
        aBearbPanel.add(aTbKapazitaet);

        aSpeichern = new JButton();
        //Initiales Disablen
        aSpeichern.setEnabled(false);
        aSpeichern.setText("Speichern");
        aBearbPanel.add(aSpeichern);
        //Panels in Ansicht
        ansicht.add(aListePanel);
        ansicht.add(aBearbPanel);
    }
    
    private void initGUILieferung()
    {
    	lieferung = new JPanel();
    	lieferung.setLayout(new BoxLayout(lieferung, BoxLayout.Y_AXIS));
        tabs.addTab("Lieferung", lieferung);
        
        lEingabePanel = new JPanel();
        lEingabePanel.setLayout(new GridLayout());
        lieferung.add(lEingabePanel);

        lEingabeFeld = new JTextField();
        lEingabeFeld.setToolTipText("Bitte Menge eintragen.");
        lEingabeFeld.setPreferredSize(new Dimension(100, 19));
        lEingabePanel.add(lEingabeFeld);

        //Panel für die Radiobuttons, damit sie untereinander sind
        JPanel lRBPanel = new JPanel();
        lRBPanel.setLayout(new BoxLayout(lRBPanel, BoxLayout.Y_AXIS));

        rbEinlieferung = new JRadioButton();
        rbEinlieferung.setText("Einlieferung");
        lRBPanel.add(rbEinlieferung);

        rbAuslieferung = new JRadioButton();
        rbAuslieferung.setText("Auslieferung");
        lRBPanel.add(rbAuslieferung);

        lEingabePanel.add(lRBPanel);
        //RadioButtons zur Gruppe hinzufügen
        ButtonGroup radioButtons = new ButtonGroup();
        radioButtons.add(rbEinlieferung);
        radioButtons.add(rbAuslieferung);
        // Standard: Einlieferung
        rbEinlieferung.setSelected(true);

        JPanel lButtonPanel = new JPanel();
        lButtonPanel.setLayout(new BoxLayout(lButtonPanel, BoxLayout.Y_AXIS));

        lieferungDist = new JButton();
        lieferungDist.setText("Lieferung verteilen");
        //lieferungDist.setPreferredSize(new Dimension(200,30));
        lButtonPanel.add(lieferungDist);

        lieferungConfirm = new JButton();
        lieferungConfirm.setText("Lieferung bestätigen");
        //lieferungConfirm.setPreferredSize(new Dimension(150,30));
        lieferungConfirm.setEnabled(false);
        lButtonPanel.add(lieferungConfirm);

        lEingabePanel.add(lButtonPanel);

        //Panel für die Liste erstellen
        lListePanel = new JPanel(new BorderLayout());
        //Liste füllen
        DefaultListModel dlm = new DefaultListModel();
        getLagerRecursive(Lagerverwaltung.getLagerList(), dlm);

        //Liste an JList
        lList = new JList<Lager>(dlm);
        lList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        lScrollPane = new JScrollPane(lList);
        //ScrollPane an Panel für Liste
        lListePanel.add(lScrollPane);
        
        //Panel für die Bearbeitungsleiste,
        JPanel lBearbPanelMain = new JPanel(new GridLayout());
        JPanel lBearbPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel lBearbPanelRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        lLabel = new JLabel();
        lLabel.setText("0/0 verteilt.");
        lBearbPanelLeft.add(lLabel);

        undoButton = new JButton();
        undoButton.setText("Undo");
        lBearbPanelRight.add(undoButton);

        redoButton = new JButton();
        redoButton.setText("Redo");
        lBearbPanelRight.add(redoButton);

        lBearbPanelMain.add(lBearbPanelLeft);
        lBearbPanelMain.add(lBearbPanelRight);

        lieferung.add(lEingabePanel);
        lieferung.add(lListePanel);
        lieferung.add(lBearbPanelMain);
    }
    
    private void initGUIHistorie()
    {
    	historie = new JPanel(new BorderLayout());
        tabs.addTab("Historie", historie);

        hList = new JList();
        // TODO: Liste füllen

        //Liste an JList
        //hList = new JList<a>(b);
        hList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        hScrollPane = new JScrollPane(hList);
        //ScrollPane an Panel für Liste
        historie.add(hScrollPane);
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

        //TODO: Lager-Ansicht: Problem, klick mal was an und halt maustaste gedrückt, dann auf ein anderes ziehen
        //- erster klick wird nur genommen...
        aList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Disabled bis das erste Lager ausgwählt wurde
                aTbName.setEnabled(true);
                aSpeichern.setEnabled(true);
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
