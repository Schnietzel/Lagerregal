package View;

import Controller.GUITools;
import Controller.Lagerverwaltung;
import Model.Lager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class AnsichtTab extends JPanel{

    JList aList;

    JLabel aName;
    JTextField aTbName;
    JLabel aKapazitaet;
    JTextField aTbKapazitaet;
    JButton aSpeichern;

    AnsichtTab(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Panel f�r die Liste erstellen
        //Tabellenkram
        JPanel aListePanel = new JPanel(new BorderLayout());
        // TestLager Anzeigen lassen
        DefaultListModel<Lager> dlm = new DefaultListModel<>();
        GUITools gt = new GUITools();
        gt.getLagerRecursive(Lagerverwaltung.getLagerList(), dlm);
        //Liste an JList
        //AnsichtTab
        aList = new JList<>(dlm);
        aList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        JScrollPane aScrollPane = new JScrollPane(aList);
        //ScrollPane an Panel f�r Liste
        aListePanel.add(aScrollPane);

        //Panel f�r Bearbeitungsleiste erstellen
        //Bearbeitungszeile
        JPanel aBearbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        aName = new JLabel();
        aName.setText("Name:");
        aBearbPanel.add(aName);

        aTbName = new JTextField();
        //Initiales Disablen
        aTbName.setEnabled(false);
        aTbName.setPreferredSize(new Dimension(120,19));
        aBearbPanel.add(aTbName);

        aKapazitaet = new JLabel();
        aKapazitaet.setText("Kapazit�t:");
        aBearbPanel.add(aKapazitaet);

        aTbKapazitaet = new JTextField();
        //Initiales Disablen
        aTbKapazitaet.setEnabled(false);
        aTbKapazitaet.setPreferredSize(new Dimension(60,19));
        aBearbPanel.add(aTbKapazitaet);

        //Initiales Disablen
        aSpeichern = new JButton();
        aSpeichern.setEnabled(false);
        aSpeichern.setText("�nderungen �bernehmen");
        aBearbPanel.add(aSpeichern);
        //Panels in Ansicht
        this.add(aListePanel);
        this.add(aBearbPanel);
        this.createListener();
    }

    private void createListener(){
        //TODO: Lager-Ansicht: Problem, klick mal was an und halt maustaste gedr�ckt, dann auf ein anderes ziehen
        //- erster klick wird nur genommen...
        aList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Disabled bis das erste Lager ausgw�hlt wurde
                aTbName.setEnabled(true);
                aSpeichern.setEnabled(true);
                Lager selected = (Lager) aList.getSelectedValue();

                aTbName.setText(selected.getName());
                aTbKapazitaet.setText("" + selected.getKapazitaet());

                aTbKapazitaet.setEnabled(selected.getUnterlager().isEmpty());

            }
        });

        // Lager-�nderung speichern
        aSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lager selected = (Lager) aList.getSelectedValue();

                selected.setName(aTbName.getText());
                selected.setKapazitaet(Integer.parseInt(aTbKapazitaet.getText()));

                // TODO: Kapazit�ten aller Lager aktualisieren (Observer?)
                aList.updateUI();
            }
        });
    }

}
