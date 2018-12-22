package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import Controller.*;
import Model.Lager;

import static javax.swing.UIManager.*;

public class GUI extends JFrame {
	private Lagerverwaltung lagerverwaltung;
	
    private JPanel outerpanel; 
    private JButton undoButton;
    private JButton redoButton;
    private JPanel innerpanel;
    private JTabbedPane tabs;
   
    private JPanel ansicht;
    private JList aList;
    
    private JScrollPane aScrollPane;
    
    private JPanel lieferung; 
    private JPanel lEingabePanel;
    private JPanel lAnsichtPanel;
    private JList lList; 
    private JScrollPane lScrollPane;
    private JTextField lEingabeFeld;
    private JButton lieferungDist;
    private JButton lieferungConfirm;
    private JLabel lLabel;
     
    private JPanel historie;
    private JList hList;  
    private JScrollPane hScrollPane;
    
   
    private JPanel toolbarPanel;
    private JRadioButton rbEinlieferung;
    private JRadioButton rbAuslieferung;


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
    {
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	Lagerverwaltung.initTestlager();
    	
        GUI test = new GUI();    
    }

    public GUI() {
    	// TODO: GridLayout schön machen
    	outerpanel = new JPanel();
        outerpanel.setLayout(new GridLayout());
        
        innerpanel = new JPanel();
        innerpanel.setLayout(new GridLayout());
        outerpanel.add(innerpanel);
        
        tabs = new JTabbedPane();
        innerpanel.add(tabs);
        
        ansicht = new JPanel();
        ansicht.setLayout(new GridLayout());
        tabs.addTab("Lageransicht", ansicht);
        
        // TODO: Scrollpane
        // TestLager Anzeigen lassen
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        for(Lager l : Lagerverwaltung.getLagerList()) 
        {
        	// TODO: Rekursiv
        	dlm.addElement(l);
        }
        
        aList = new JList<Lager>(dlm);
        ansicht.add(aList);
        

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
      
        // TODO: ScrollPanel
        lList = new JList();
        // TODO: Liste füllen

        historie = new JPanel();
        historie.setLayout(new GridLayout());
        tabs.addTab("Historie", historie);
        
        // TODO: Scrollpane
        hList = new JList();
        // TODO: Liste füllen
        
        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new GridLayout());
        outerpanel.add(toolbarPanel);

        undoButton = new JButton();
        undoButton.setText("Undo");
        toolbarPanel.add(undoButton);

        redoButton = new JButton();
        redoButton.setText("Redo");
        toolbarPanel.add(redoButton);

        // TODO: Spacer(?)
//        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
//        Spacer
//      toolbarPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(14, 50), null, 0, false));
//      final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
//      outerpanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, 1, null, null, null, 0, false));

        
        this.setContentPane(outerpanel);
        this.setTitle("LieferungsTool für Mertens v1337");
        this.pack();
      	this.setVisible(true);
      	this.setMinimumSize(new Dimension(600, 500));
      	this.validate();
      	this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        createListener();
      	

   
        
        
    }
    

    private void createListener() {
//        //UNDO REDO
//        undoButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //UNDO
//            }
//        });
//        redoButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //REDO
//            }
//        });
//
//        //BUTTONS
//        lieferungConfirm.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //TODO: Lieferung komplett verteilt? - Lieferung ausführen -sicher?
//            	
//            }
//        });
//        lieferungDist.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //TODO: Daten an Ceddi übergeben, Lieferung werden nun verteilbar (Buchungen)
//                //Ceddis handling dann:
//                //Wenn Zahl: Methode lGueltigeZahl aufrufen
//                //Wenn keine Zahl: Methode lUngueltigeZahl Aufrufen.
//            }
//        });

        //LISTEN::::
        aList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JList aList = (JList) e.getSource();
//                if (e.getClickCount() == 2) {
//                    int index = aList.locationToIndex(e.getPoint());
//                    listClick(index, 1);
//                }
            	JOptionPane.showMessageDialog(null, "Test");
            }
        });
        //Event: Doppelklick auf Item in der Liste, Item muss anschließend via Control bearbeitet werden.
//        // Aufruf in der LierferungBestätigen
//        lList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JList lList = (JList) e.getSource();
//                if (e.getClickCount() == 2) {
//                    int index = lList.locationToIndex(e.getPoint());
//                    listClick(index, 2);
//                }
//            }
//        });
//        hList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JList hList = (JList) e.getSource();
//                if (e.getClickCount() == 2) {
//                    int index = hList.locationToIndex(e.getPoint());
//                    listClick(index, 3);
//                }
//            }
//        });
//
//        //RADIOBUTTONS
//        rbEinlieferung.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                if (rbEinlieferung.isSelected()) {
//                    lTextEdit("Einlieferung");
//                } else lTextEdit("Auslieferung");
//            }
//        });
    }
    

    public void lTextEdit(String lieferung) {
        lLabel.setText(lieferung + ": " + "Zahl" + "/" + "Zahl" + " verteilt.");
        //TODO: infoText nach Buchung anpassen (muss nach jeder Buchung, jedem schließen des Dialogs, ausgeführt werden)
        //Schema des Texts:
        //Einlieferung: xxxx/xxxx verteilt.
        //Auslieferung: xxxx/xxxx verteilt.

    }

    public void lGueltigeZahl() {
        //müsste undo-able sein!!
        lieferungDist.setEnabled(false);
        lEingabeFeld.setEnabled(false);
        lList.setEnabled(true);
        rbEinlieferung.setEnabled(false);
        rbAuslieferung.setEnabled(false);
        lieferungConfirm.setEnabled(true);
    }

    public void lUngueltigeZahl() {
        PopUpDialog popup = new PopUpDialog();
        popup.setMeldung("Ungueltige Zahl.");
        popup.popUp();
    }

    private void listClick(int index, int art) {
        switch (art) {
            case 1:
                //TODO: Umbenennen des Lagers
                break;
            case 2:
                //TODO: mit dem Index muss dann (nach klick auf OK) das angeklickte Lager gelocked werden?
                BuchungDialog lBest = new BuchungDialog("GUI");
                break;
            case 3:
                //TODO: Was auch immer beim Klicken in der Historie gemacht wird...
                break;
            default:
                PopUpDialog popUp = new PopUpDialog();
                popUp.setMeldung("Nicht unterstützte Liste, sollte eigentlich nicht vorkommen!");
                popUp.setTitle("Mertens!!");
                popUp.popUp();
                break;
        }
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
