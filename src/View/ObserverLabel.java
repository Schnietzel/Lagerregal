package View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import Controller.ControllerSingleton;

public class ObserverLabel extends JLabel implements Observer{

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		this.setText(String.valueOf(ControllerSingleton.getBVInstance().getVerteilteMenge()));
		
	}
	
	

}
