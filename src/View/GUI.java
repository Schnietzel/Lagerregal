package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.UIManager.*;

public class GUI extends JFrame {
    private JPanel outerpanel;
    private JTabbedPane tabs;
    private JButton undoButton;
    private JButton redoButton;
    private JList hList;
    private JPanel innerpanel;
    private JPanel lEingabePanel;
    private JPanel lAnsichtPanel;
    private JList lList;
    private JScrollPane lScrollPane;
    private JScrollPane hScrollPane;
    private JButton lieferungDist;
    private JTextField lEingabeFeld;
    private JList aList;
    private JScrollPane aScrollPane;
    private JPanel ansicht;
    private JPanel lieferung;
    private JPanel historie;
    private JButton lieferungConfirm;
    private JPanel toolbarPanel;
    private JRadioButton rbEinlieferung;
    private JRadioButton rbAuslieferung;
    private JLabel lLabel;

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
                JList aList = (JList)e.getSource();
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
                JList lList = (JList)e.getSource();
                if (e.getClickCount() == 2) {
                    int index = lList.locationToIndex(e.getPoint());
                    listClick(index, 2);
                }
            }
        });
        hList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList hList = (JList)e.getSource();
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
                if(rbEinlieferung.isSelected()){
                    lTextEdit("Einlieferung");
                } else lTextEdit("Auslieferung");
            }
        });
    }

    public static void main(String[] args) {
        GUI test = new GUI();
    }

    public void lTextEdit(String lieferung){
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
        switch (art){
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
}
