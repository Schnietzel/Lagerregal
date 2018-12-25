package View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Controller.ControllerSingleton;
import Model.Buchung;
import Model.Lieferung;

public class ObserverJList extends JList implements Observer {


    @Override
    public void update(Observable arg0, Object arg1) {
        // TODO Auto-generated method stub
        ArrayList<Lieferung> historie = ControllerSingleton.getBVInstance().getHistorie();
        DefaultListModel dlm = new DefaultListModel();
        for (Lieferung l : historie) {
            dlm.addElement(l.toString());
            for (Buchung b : l.getBuchungen()) {
                dlm.addElement(b.toString());
            }
        }
        this.setModel(dlm);
    }

}
