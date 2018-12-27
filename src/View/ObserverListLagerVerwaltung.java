package View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

import Controller.ControllerSingleton;
import Model.Lager;

public class ObserverListLagerVerwaltung  extends JList<Lager> implements Observer {
	DefaultListModel<Lager> dlm = new DefaultListModel<>();
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		dlm.clear();
        ControllerSingleton.getGTInstance().getLagerRecursive(ControllerSingleton.getLVInstance().getLagerList(), dlm);
        this.setModel(dlm);
	}

}
