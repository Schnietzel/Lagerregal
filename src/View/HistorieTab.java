package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class HistorieTab extends JPanel{

    private JList hList;

    HistorieTab(){
        this.setLayout(new BorderLayout());

        hList = new JList();
        // TODO: Liste füllen

        //Liste an JList
        //hList = new JList<a>(b);
        hList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JList an Scrollpane
        JScrollPane hScrollPane = new JScrollPane(hList);
        //ScrollPane an Panel für Liste
        this.add(hScrollPane);
    }

    void createListener(){
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
    }
}
