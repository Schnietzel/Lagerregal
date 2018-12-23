package View;

import Controller.Buchungsverwaltung;
import Controller.GUITools;
import Controller.Lagerverwaltung;
import Model.Lager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class LieferungTab extends JPanel {

    private JTextField lEingabeFeld;
    private JLabel lLabel;
    private JRadioButton rbEinlieferung;
    private JRadioButton rbAuslieferung;
    private JButton lieferungDist;
    private JButton lieferungConfirm;
    private JList<Lager> lList;
    private JButton undoButton;
    private JButton redoButton;

    private boolean lieferungAktiv = false;

    LieferungTab(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //EingabePanel
        JPanel lEingabePanel = new JPanel();
        lEingabePanel.setLayout(new GridLayout());
        this.add(lEingabePanel);

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
        JPanel lListePanel = new JPanel(new BorderLayout());
        //Liste füllen
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        GUITools gt = new GUITools();
        gt.getLagerRecursive(Lagerverwaltung.getLagerList(), dlm);

        //Liste an JList
        lList = new JList<>(dlm);
        lList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        JScrollPane lScrollPane = new JScrollPane(lList);
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

        this.add(lEingabePanel);
        this.add(lListePanel);
        this.add(lBearbPanelMain);
        this.createListener();
    }

    private void createListener(){
        // RadioButtons
        rbEinlieferung.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (rbEinlieferung.isSelected()) {
                    lTextEdit("Einlieferung");
                } else lTextEdit("Auslieferung");
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
                    Lager lager = lList.getSelectedValue();
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

    private void lTextEdit(String lieferung) {
        lLabel.setText(lieferung + ": " + "Zahl" + "/" + "Zahl" + " verteilt.");
        //TODO: infoText nach Buchung anpassen (muss nach jeder Buchung, jedem schließen des Dialogs, ausgeführt werden)
        //Schema des Texts:
        //Einlieferung: xxxx/xxxx verteilt.
        //Auslieferung: xxxx/xxxx verteilt.

    }
    private void lGueltigeLieferungsZahl()
    {
        setLieferungAktiv(true);
    }

    private void lGueltigeBuchungsZahl()
    {
        // TODO: Irgendwie in Liste bemerkbar machen
    }

    private void lGueltigeLieferung()
    {
        setLieferungAktiv(false);
    }

    private void lUngueltigeZahl() {
        JOptionPane.showMessageDialog(null, "Lagermenge/Kapazität ist nicht ausreichend für diese Buchung");
    }

    private void lUngueltigeLieferung()
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

}
