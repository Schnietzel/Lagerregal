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
   
    // 
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
    

    public GUI() {
        this.setContentPane(outerpanel);
        this.setTitle("LieferungsTool für Mertens v1337");
        ButtonGroup radioButtons = new ButtonGroup();
        radioButtons.add(rbAuslieferung);
        radioButtons.add(rbEinlieferung);
        //Standard: Einlieferung
        rbEinlieferung.setSelected(true);
        lieferungConfirm.setEnabled(false);
        try {
            setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } 
        //TestLager Anzeigen lassen
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        Lagerverwaltung.initTestlager();
        for(Lager l : Lagerverwaltung.getLagerList()) {
        	dlm.addElement(l);
        }
        aList = new JList<Lager>(dlm);
        aScrollPane.add(aList);
     
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(600, 500));
        this.validate();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createListener();
     
        
    }

    private void createListener() {
        //UNDO REDO
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //UNDO
            }
        });
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //REDO
            }
        });

        //BUTTONS
        lieferungConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Lieferung komplett verteilt? - Lieferung ausführen -sicher?
            	
            }
        });
        lieferungDist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Daten an Ceddi übergeben, Lieferung werden nun verteilbar (Buchungen)
                //Ceddis handling dann:
                //Wenn Zahl: Methode lGueltigeZahl aufrufen
                //Wenn keine Zahl: Methode lUngueltigeZahl Aufrufen.
            }
        });

        //LISTEN::::
        aList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList aList = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = aList.locationToIndex(e.getPoint());
                    listClick(index, 1);
                }
            }
        });
        //Event: Doppelklick auf Item in der Liste, Item muss anschließend via Control bearbeitet werden.
        // Aufruf in der LierferungBestätigen
        lList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList lList = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = lList.locationToIndex(e.getPoint());
                    listClick(index, 2);
                }
            }
        });
        hList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList hList = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = hList.locationToIndex(e.getPoint());
                    listClick(index, 3);
                }
            }
        });

        //RADIOBUTTONS
        rbEinlieferung.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (rbEinlieferung.isSelected()) {
                    lTextEdit("Einlieferung");
                } else lTextEdit("Auslieferung");
            }
        });
    }

    public static void main(String[] args) {
        GUI test = new GUI();
        
        
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        outerpanel = new JPanel();
        outerpanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        innerpanel = new JPanel();
        innerpanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        outerpanel.add(innerpanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabs = new JTabbedPane();
        innerpanel.add(tabs, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        ansicht = new JPanel();
        ansicht.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabs.addTab("Lageransicht", ansicht);
        aScrollPane = new JScrollPane();
        ansicht.add(aScrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        aList = new JList();
        aScrollPane.setViewportView(aList);
        lieferung = new JPanel();
        lieferung.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabs.addTab("Lieferung", lieferung);
        lEingabePanel = new JPanel();
        lEingabePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        lieferung.add(lEingabePanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        lEingabeFeld = new JTextField();
        lEingabePanel.add(lEingabeFeld, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lieferungDist = new JButton();
        this.$$$loadButtonText$$$(lieferungDist, ResourceBundle.getBundle("String").getString("lieferung.verteilen"));
        lEingabePanel.add(lieferungDist, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rbEinlieferung = new JRadioButton();
        this.$$$loadButtonText$$$(rbEinlieferung, ResourceBundle.getBundle("String").getString("einlieferung"));
        lEingabePanel.add(rbEinlieferung, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rbAuslieferung = new JRadioButton();
        this.$$$loadButtonText$$$(rbAuslieferung, ResourceBundle.getBundle("String").getString("auslieferung"));
        lEingabePanel.add(rbAuslieferung, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lieferungConfirm = new JButton();
        this.$$$loadButtonText$$$(lieferungConfirm, ResourceBundle.getBundle("String").getString("lieferung.bestatigen"));
        lEingabePanel.add(lieferungConfirm, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(106, 50), null, 0, false));
        lLabel = new JLabel();
        lLabel.setText("0/0 verteilt.");
        lEingabePanel.add(lLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lAnsichtPanel = new JPanel();
        lAnsichtPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        lieferung.add(lAnsichtPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lScrollPane = new JScrollPane();
        lAnsichtPanel.add(lScrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lList = new JList();
        lScrollPane.setViewportView(lList);
        historie = new JPanel();
        historie.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabs.addTab("Historie", historie);
        hScrollPane = new JScrollPane();
        historie.add(hScrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        hList = new JList();
        hScrollPane.setViewportView(hList);
        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        outerpanel.add(toolbarPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        undoButton = new JButton();
        undoButton.setText("Undo");
        toolbarPanel.add(undoButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(76, 50), null, 0, false));
        redoButton = new JButton();
        redoButton.setText("Redo");
        toolbarPanel.add(redoButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(75, 50), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        toolbarPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(14, 50), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        outerpanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, 1, null, null, null, 0, false));
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
