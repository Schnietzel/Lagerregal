package View;

import Controller.ControllerSingleton;
import Controller.GUITools;
import Model.Lager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class LieferungTab extends JPanel {

    private JTextField lEingabeFeld;
    private int lEingabeInt;
    private JLabel lLieferung;
    private ObserverLabel lVerteilt;
    private JLabel lSlash;
    private JLabel lGesamt;
    private JRadioButton rbEinlieferung;
    private JRadioButton rbAuslieferung;
    private JButton lieferungDist;
    private ObservableButton lieferungConfirm;
    private ObserverListLagerVerwaltung lList;
    private JButton undoButton;
    private JButton redoButton;

    private boolean lieferungAktiv = false;


    LieferungTab() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //EingabePanel
        JPanel lEingabePanel = new JPanel();
        lEingabePanel.setLayout(new GridLayout());
        this.add(lEingabePanel);

        lEingabeFeld = new JTextField();
        lEingabeFeld.setToolTipText("Bitte Menge eintragen.");
        lEingabeFeld.setPreferredSize(new Dimension(100, 19));
        lEingabePanel.add(lEingabeFeld);

        //Panel f�r die Radiobuttons, damit sie untereinander sind
        JPanel lRBPanel = new JPanel();
        lRBPanel.setLayout(new BoxLayout(lRBPanel, BoxLayout.Y_AXIS));

        rbEinlieferung = new JRadioButton();
        rbEinlieferung.setText("Einlieferung");
        lRBPanel.add(rbEinlieferung);

        rbAuslieferung = new JRadioButton();
        rbAuslieferung.setText("Auslieferung");
        lRBPanel.add(rbAuslieferung);

        lEingabePanel.add(lRBPanel);
        //RadioButtons zur Gruppe hinzuf�gen
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

        lieferungConfirm = new ObservableButton();
        lieferungConfirm.getButton().setText("Lieferung best�tigen");
        //lieferungConfirm.setPreferredSize(new Dimension(150,30));
        lieferungConfirm.getButton().setEnabled(false);
        lieferungConfirm.addObserver(HistorieTab.hList);
        lButtonPanel.add(lieferungConfirm.getButton());

        lEingabePanel.add(lButtonPanel);

        //Panel f�r die Liste erstellen
        JPanel lListePanel = new JPanel(new BorderLayout());
        //Liste f�llen
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        GUITools gt = new GUITools();
        gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);

        //Liste an JList
        lList = new ObserverListLagerVerwaltung();
        ControllerSingleton.getLVInstance().addObserver(lList);
        lList.setModel(dlm);
        lList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        JScrollPane lScrollPane = new JScrollPane(lList);
        //ScrollPane an Panel f�r Liste
        lListePanel.add(lScrollPane);

        //Panel f�r die Bearbeitungsleiste,
        JPanel lBearbPanelMain = new JPanel(new GridLayout());
        JPanel lBearbPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel lBearbPanelRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        lLieferung = new JLabel();
        lLieferung.setText("Lieferung:");
        lBearbPanelLeft.add(lLieferung);

        lVerteilt = new ObserverLabel();
        lVerteilt.setText("0");
        lBearbPanelLeft.add(lVerteilt);

        lSlash = new JLabel();
        lSlash.setText("/");
        lBearbPanelLeft.add(lSlash);

        lGesamt = new JLabel();
        lGesamt.setText("0");
        lBearbPanelLeft.add(lGesamt);

        undoButton = new JButton();
        undoButton.setText("Undo");
        lBearbPanelRight.add(undoButton);

        redoButton = new JButton();
        redoButton.setText("Redo");
        lBearbPanelRight.add(redoButton);

        lBearbPanelMain.add(lBearbPanelLeft);
        lBearbPanelMain.add(lBearbPanelRight);

        this.add(lEingabePanel);
        this.add(lListePanel);
        this.add(lBearbPanelMain);
        this.createListener();
    }

    private void createListener() {
        // RadioButtons
        rbEinlieferung.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (rbEinlieferung.isSelected()) {
                    lLieferung.setText("Einlieferung");
                } else lLieferung.setText("Auslieferung");
            }
        });
        // Lieferung verteilen
        lieferungDist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUITools check = new GUITools();
                int errorCode = check.LieferungInput(lEingabeFeld.getText());
                switch (errorCode) {
                    case 0:
                        lEingabeInt = Integer.parseInt(lEingabeFeld.getText());
                        if (rbEinlieferung.isSelected()) {
                            System.out.println(ControllerSingleton.getBVInstance().getVerteilteMenge());
                            lTextEdit("Einlieferung", ControllerSingleton.getBVInstance().getVerteilteMenge(), lEingabeInt);
                        } else lTextEdit("Auslieferung", ControllerSingleton.getBVInstance().getVerteilteMenge(), lEingabeInt);

                        boolean gueltig = ControllerSingleton.getBVInstance().createLieferung(rbAuslieferung.isSelected(), lEingabeInt);

                        if (gueltig)
                            lGueltigeLieferungsZahl();
                        else
                            lUngueltigeZahl();
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "Die eingegebene Zahl muss zwischen 0 und 2.147.483.647 liegen.", "Fehlercode 1", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "Bitte nur Zahlen eingeben.", "Fehlercode 2", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Es ist ein unerwarteter Fehler aufgetreten.", "Fehlercode 3", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            }
        });

        lEingabeFeld.addKeyListener(new KeyListener() {

            String ziffer;

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub
                ziffer = lEingabeFeld.getText();
                lGesamt.setText(ziffer);
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
            }
        });

        // Lieferungen-Lager-Liste
        //Event: Doppelklick auf Item in der Liste, Item muss anschlie�end via Control bearbeitet werden.
        // Aufruf in der LierferungBest�tigen
        lList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // TODO: Buchung Handlen
                    Lager lager = lList.getSelectedValue();
                    if (ControllerSingleton.getBVInstance().istBearbeitet(lager)) {
                        JOptionPane.showMessageDialog(null, "Lager ist bereits bearbeitet! (Ggf. Undo benutzen)", "Fehler", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    if (!lager.getUnterlager().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Lager ist Oberlager und kann damit nicht zur Buchung verwendet werden!", "Fehler", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    BuchungDialog bd = new BuchungDialog(lager, rbAuslieferung.isSelected(), lEingabeInt);
                    bd.okButton.addObserver(lVerteilt);


//                    boolean gueltig = Buchungsverwaltung.createBuchung(rbAuslieferung.isSelected(), bd.getLager(), bd.getProzent());
//                    if (gueltig)
//                        lGueltigeBuchungsZahl();
//                    else
//                        lUngueltigeZahl();
                    // TODO: Handlen ob G�ltig oder nicht
                }
            }
        });

        // Lieferung Best�tigen
        lieferungConfirm.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Lieferung komplett verteilt? - Lieferung ausf�hren -sicher?
                boolean gueltig = ControllerSingleton.getBVInstance().verteileLieferung(rbAuslieferung.isSelected());
                if (gueltig) {
                    lGueltigeLieferung();
                    lieferungConfirm.setChanged();
                    lieferungConfirm.notifyObservers();
                    lVerteilt.setText("0");
                    lEingabeFeld.setText("");
                    lGesamt.setText("0");
                } else {
                    lUngueltigeLieferung();
                }
            }
        });
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
    }

    private void lTextEdit(String lieferung, int zahl, int gesamt) {
        lLieferung.setText(lieferung);
        lVerteilt.setText(String.valueOf(zahl));
        lGesamt.setText(String.valueOf(gesamt));
    }

    private void lGueltigeLieferungsZahl() {
        setLieferungAktiv(true);
    }

    private void lGueltigeBuchungsZahl() {
        // TODO: Irgendwie in Liste bemerkbar machen


    }

    private void lGueltigeLieferung() {
        setLieferungAktiv(false);
    }

    private void lUngueltigeZahl() {
        JOptionPane.showMessageDialog(null, "Lagermenge/Kapazit�t ist nicht ausreichend f�r diese Buchung", "Fehler", JOptionPane.INFORMATION_MESSAGE);
    }

    private void lUngueltigeLieferung() {
        JOptionPane.showMessageDialog(null, "Es sind noch Artikel nicht verbucht!", "Fehler", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setLieferungAktiv(boolean aktiv) {
        lieferungDist.setEnabled(!aktiv);
        lEingabeFeld.setEnabled(!aktiv);
        lList.setEnabled(aktiv);
        rbEinlieferung.setEnabled(!aktiv);
        rbAuslieferung.setEnabled(!aktiv);
        lieferungConfirm.getButton().setEnabled(aktiv);
    }


}
