package View;

import Controller.ControllerSingleton;
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

class AnsichtTab extends JPanel {
    GUITools gt;
    JList aList;
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

        //PopUpMenu f�r Liste erstellen
        popupMenu = new JPopupMenu();
        menuItemAdd = new JMenuItem("Lager hinzuf�gen...");
        menuItemDelete = new JMenuItem("Lager l�schen...");
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemDelete);


        //Panel f�r die Liste erstellen
        //Tabellenkram
        JPanel aListePanel = new JPanel(new BorderLayout());
        // TestLager Anzeigen lassen
      
        gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
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
        aTbName.setPreferredSize(new Dimension(120, 19));
        aBearbPanel.add(aTbName);

        aKapazitaet = new JLabel();
        aKapazitaet.setText("Kapazit�t:");
        aBearbPanel.add(aKapazitaet);

        aTbKapazitaet = new JTextField();
        //Initiales Disablen
        aTbKapazitaet.setEnabled(false);
        aTbKapazitaet.setPreferredSize(new Dimension(60, 19));
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

    private void createListener() {
        //MenuItem Listener
        menuItemAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Unterlager hinzuf�gen.
                System.out.println("HIER WIRD HINZUGEF�GT");
            }
        });
        menuItemDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: LAger l�schbar? ausgw�hltes Lager l�schen
                System.out.println("HIER WIRD GEL�SCHT");
            }
        });
        //TODO: Lager-Ansicht: Problem, klick mal was an und halt maustaste gedr�ckt, dann auf ein anderes ziehen
        //- erster klick wird nur genommen...
        aList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Linksklick
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if(aList.getSelectedIndex() != lastSelected){
                        //Disabled bis das erste Lager ausgw�hlt wurde
                        aTbName.setEnabled(true);
                        aSpeichern.setEnabled(true);
                        Lager selected = (Lager) aList.getSelectedValue();

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
                if(e.getButton() == MouseEvent.BUTTON3) {
                    //Rechtsklick Dings wird aufgerufen
                    //TODO: ausgw�hltes Lager zwsichenspeichern oder so
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

        }
        );
        // Lager-�nderung speichern
        aSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lager selected = (Lager) aList.getSelectedValue();

                selected.setName(aTbName.getText());
                selected.setKapazitaet(Integer.parseInt(aTbKapazitaet.getText()));

                // TODO: Kapazit�ten aller Lager aktualisieren (Observer?)
                ControllerSingleton.getLVInstance().updateLager();
                aList.updateUI();
            }
        });
        
        menuItemAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub //Lager Hinzuf�gen
				Lager selected = (Lager) aList.getSelectedValue();
				if(selected == null) {				
					ControllerSingleton.getLVInstance().addLager();
					dlm = null;
					gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
					aList.setModel(dlm);
					}
				else {
					
				ControllerSingleton.getLVInstance().addLager(selected);
				dlm = null;
				gt.getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
				aList.setModel(dlm);
				
				}
			}});
        
        menuItemDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub //Lager L�schen
				
			}
		});
    }

}
