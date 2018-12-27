package View;

import javax.swing.*;

import Controller.ControllerSingleton;
import Controller.GUITools;
import Model.Buchung;
import Model.Lieferung;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class HistorieTab extends JPanel {
    private GUITools gt;

    static ObserverListHistorie hList = new ObserverListHistorie();

    HistorieTab() {
        gt = ControllerSingleton.getGTInstance();
        this.setLayout(new BorderLayout());

        DefaultListModel<String> dlm = new DefaultListModel<String>();
        //gt.getLieferung(ControllerSingleton.getBVInstance().getHistorie(), dlm);


        hList.setModel(dlm);


        //JList an Scrollpane
        JScrollPane hScrollPane = new JScrollPane(hList);
        //ScrollPane an Panel für Liste
        this.add(hScrollPane);
    }

    void createListener() {
        // Historien-Liste
        hList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<String> hList = (JList<String>) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = hList.locationToIndex(e.getPoint());
                }
            }
        });
    }
}
