package View;

import Controller.EingabeCheck;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuchungDialog extends JDialog{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSlider slider;
    private JTextField input;
    private JLabel infoText;
    private boolean supressSliderChange = false;
    private Double percentage;

    BuchungDialog(String info) {
        setContentPane(contentPane);
        this.setTitle("Buchung durchf�hren");
        this.infoText.setText(info);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(450, 200));
        this.validate();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        createListener();
    }

    private void createListener() {
        buttonOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});
        buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});
        slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			    if (!supressSliderChange) {
			        input.setText(String.valueOf(slider.getValue()));
			    } else supressSliderChange = false;
			}
		});
        input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputAction();
			}
		});
    }

    public static void main(String[] args) {
        //mein Test
        BuchungDialog dialog = new BuchungDialog("000/1000 verteilt.");
    }

    private void onOK() {
        //TODO: Wert an Control �bergeben, Lager "disablen"
        //percentage ist der wert (werte von 0,00-100,00)
        dispose();
    }

    private void onCancel() {
        //Fenster schlie�en
        dispose();
    }

    private void changeLabel(Double percent){
        //TODO: muss noch kram getan werden
        //double max = Double.parseDouble(this.infoText.getText().replaceAll("/.", ""));
        //double allocated = max / (percent/100);
        //System.out.println(max);
        //System.out.println(percent);
        //this.infoText.setText(this.infoText.getText().replaceAll("^\\d*[.,]?\\d*[^/]",Double.toString(allocated)));
    }

    private void inputAction(){
        PopUpDialog popUp = new PopUpDialog();
        EingabeCheck check = new EingabeCheck();
        int errorCode = check.BuchungInput(input.getText());
        //TODO: Fehlerhandling ggf. auslagern
        switch (errorCode){
            case 0:
                supressSliderChange = true;
                slider.setValue(check.iValue);
                percentage = check.dValue;
                //changeLabel(check.dValue);
                break;
            case 1:
                popUp.setMeldung("Nur Zahlenbereiche von 0-100.");
                popUp.popUp();
                break;
            case 2:
                popUp.setMeldung("Bitte nur Zahlen eingeben.");
                popUp.popUp();
                break;
            default:
                popUp.setMeldung("Ein unerwarteter Fehler ist aufgetreten.");
                popUp.setTitle("Mertens, das sollte nicht so sein!");
                popUp.popUp();
                break;
        }
    }
}
