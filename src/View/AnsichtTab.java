package View;

import Controller.ControllerSingleton;
import Controller.GUITools;
import Model.Lager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AnsichtTab extends JPanel {
    GUITools gt;
    JPanel aListePanel;
    ObserverListLagerVerwaltung aList;
    private int lastSelected;

    JLabel aName;
    JTextField aTbName;
    JLabel aKapazitaet;
    JTextField aTbKapazitaet;
    JButton aSpeichern;

    JPopupMenu popupMenu;
    JMenuItem menuItemAdd;
    JMenuItem menuItemDelete;

    DefaultListModel<Lager> dlm = new DefaultListModel<>();

    AnsichtTab() {
        gt = ControllerSingleton.getGTInstance();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //PopUpMenu für Liste erstellen
        popupMenu = new JPopupMenu();
        menuItemAdd = new JMenuItem("Lager hinzufügen...");
        menuItemDelete = new JMenuItem("Lager löschen...");
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemDelete);


        //Panel für die Liste erstellen
        //Tabellenkram
        aListePanel = new JPanel(new BorderLayout());
        // TestLager Anzeigen lassen

        gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
        //Liste an JList
        //AnsichtTab
        aList = new ObserverListLagerVerwaltung();
        ControllerSingleton.getLVInstance().addObserver(aList);
        aList.setModel(dlm);
        aList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        JScrollPane aScrollPane = new JScrollPane(aList);
        //ScrollPane an Panel für Liste
        aListePanel.add(aScrollPane);

        //Panel für Bearbeitungsleiste erstellen
        //Bearbeitungszeile
        JPanel aBearbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        aName = new JLabel();
        aName.setText("Name:");
        aBearbPanel.add(aName);

        aTbName = new JTextField();
        //Initiales Disablen
        aTbName.setEnabled(false);
        aTbName.setPreferredSize(new Dimension(120, 19));
        aBearbPanel.add(aTbName);

        aKapazitaet = new JLabel();
        aKapazitaet.setText("Kapazität:");
        aBearbPanel.add(aKapazitaet);

        aTbKapazitaet = new JTextField();
        //Initiales Disablen
        aTbKapazitaet.setEnabled(false);
        aTbKapazitaet.setPreferredSize(new Dimension(60, 19));
        aBearbPanel.add(aTbKapazitaet);

        //Initiales Disablen
        aSpeichern = new JButton();
        aSpeichern.setEnabled(false);
        aSpeichern.setText("Änderungen übernehmen");
        aBearbPanel.add(aSpeichern);

        //Panels in Ansicht
        this.add(aListePanel);
        this.add(aBearbPanel);
        this.createListener();
    }

    private void createListener() {
        //TODO: Lager-Ansicht: Problem, klick mal was an und halt maustaste gedrückt, dann auf ein anderes ziehen
        //- erster klick wird nur genommen...
        aList.addMouseListener(new MouseAdapter() {
                                   @Override
                                   public void mouseClicked(MouseEvent e) {
                                       //Linksklick
                                       if (e.getButton() == MouseEvent.BUTTON1) {
                                           if (aList.getSelectedIndex() != lastSelected) {
                                               //Disabled bis das erste Lager ausgwählt wurde
                                               aTbName.setEnabled(true);
                                               aSpeichern.setEnabled(true);
                                               Lager selected = aList.getSelectedValue();

                                               aTbName.setText(selected.getName());
                                               aTbKapazitaet.setText("" + selected.getKapazitaet());

                                               aTbKapazitaet.setEnabled(selected.getUnterlager().isEmpty());
                                               //Overwrite lastSelected
                                               lastSelected = aList.getSelectedIndex();
                                               System.out.println(lastSelected);
                                           } else {
                                               aList.clearSelection();
                                               lastSelected = -1;
                                           }
                                       }
                                       //Rechtsklick
                                       if (e.getButton() == MouseEvent.BUTTON3) {
                                           //Rechtsklick Dings wird aufgerufen
                                           //TODO: ausgwähltes Lager zwsichenspeichern oder so - aList.getSelectedIndex() oder lastSelected
                                           popupMenu.show(e.getComponent(), e.getX(), e.getY());
                                       }
                                   }

                               }
        );
        // Lager-Änderung speichern
        aSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lager selected = aList.getSelectedValue();

                selected.setName(aTbName.getText());
                selected.setKapazitaet(Integer.parseInt(aTbKapazitaet.getText()));

                // TODO: Kapazitäten aller Lager aktualisieren (Observer?)
                ControllerSingleton.getLVInstance().updateLager();
                aList.updateUI();
            }
        });

        menuItemAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub //Lager Hinzufügen
                Lager selected = aList.getSelectedValue();
                if (selected == null) {
                    ControllerSingleton.getLVInstance().addLager();
//                    dlm.clear();
//                    gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
//                    aList.setModel(dlm);
                } else {

                    ControllerSingleton.getLVInstance().addLager(selected);
//                    dlm.clear();
//                    gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
//                    aList.setModel(dlm);

                }
            }
        });

        menuItemDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub //Lager Löschen
            	Lager selected = aList.getSelectedValue();
            	if(selected==null) {
            		 JOptionPane.showMessageDialog(aListePanel, "Sie müssen Lager Auswählen, um es zu Löschen.");
            	}
            	else {
            		ControllerSingleton.getLVInstance().removeLager(selected);
            		dlm.clear();
            		gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
            		aList.setModel(dlm);
            	}

            }
        });
    }

}
