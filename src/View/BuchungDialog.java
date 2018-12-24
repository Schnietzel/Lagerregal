package View;

import Controller.Buchungsverwaltung;
import Controller.ControllerSingleton;
import Controller.GUITools;
import Model.Lager;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class BuchungDialog extends JDialog implements Observer {
	
    private JPanel rootPanel;
    public ObservableButton okButton;
    private JButton cancelButton;
    private JSlider prozentSlider;
    private JTextField prozentTextField;
    private JLabel lagerLabel;
    private JLabel verteiltLabel;
    private boolean supsupressSliderChange = false;
    
    private Lager lager;
    private boolean auslieferung;
    private Double prozent;
    private int zuVerteilen;
    private int bereitsVerteilt;

    BuchungDialog(Lager info, boolean lieferung, int zuVerteilen)
    {
    	this.lager = info;
    	this.prozent = 0.0;
    	this.bereitsVerteilt = ControllerSingleton.getBVInstance().getVerteilteMenge();
    	this.zuVerteilen = zuVerteilen;
    	initGUI();
    	
        setContentPane(rootPanel);
        this.setTitle("Buchung durchführen");
        this.lagerLabel.setText("Ausgewähltes Lager: " + lager.getName());
        this.verteiltLabel.setText(this.bereitsVerteilt + "/" + zuVerteilen + " verteilt.");
        this.auslieferung = lieferung;
        this.pack();
        this.setVisible(true);
        this.setPreferredSize(new Dimension(250,200));
        this.setResizable(false);
        this.validate();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        createListener();
    }

    private void initGUI()
    {
    	
    	rootPanel = new JPanel();
    	rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        //labelPanel
    	JPanel labelPanelMain = new JPanel();
    	JPanel labelPanelA = new JPanel();
    	JPanel labelPanelB = new JPanel();

    	labelPanelMain.setLayout(new BoxLayout(labelPanelMain,BoxLayout.Y_AXIS));
    	labelPanelA.setLayout(new FlowLayout(FlowLayout.LEFT));
    	labelPanelB.setLayout(new FlowLayout(FlowLayout.LEFT));

        lagerLabel = new JLabel();
        labelPanelA.add(lagerLabel);

        verteiltLabel = new JLabel();
        labelPanelB.add(verteiltLabel);

        labelPanelMain.add(labelPanelA);
        labelPanelMain.add(labelPanelB);
        rootPanel.add(labelPanelMain);
        //inputPanel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        prozentSlider = new JSlider();
        prozentSlider.setPreferredSize(new Dimension(150,19));
        prozentSlider.setValue(0);
        inputPanel.add(prozentSlider);

        prozentTextField = new JTextField();
        prozentTextField.setPreferredSize(new Dimension(100,19));
        prozentTextField.setText("0");
        inputPanel.add(prozentTextField);


        rootPanel.add(inputPanel);
        //buttonPanel
    	JPanel buttonPanelMain = new JPanel(new GridLayout());
    	JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    	
    	cancelButton = new JButton();
        cancelButton.setText("Abbrechen");
    	buttonPanelLeft.add(cancelButton);
      
    	okButton = new ObservableButton();
    	okButton.getButton().setText("Bestätigen");
    	buttonPanelRight.add(okButton.getButton());

        buttonPanelMain.add(buttonPanelLeft);
        buttonPanelMain.add(buttonPanelRight);
        rootPanel.add(buttonPanelMain, BorderLayout.PAGE_END);
    }
    
    private void createListener() {
        okButton.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        prozentSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	int tmp;
                if (!supsupressSliderChange) {
                    prozentTextField.setText(String.valueOf(prozentSlider.getValue()));
                    prozent = Double.valueOf(prozentTextField.getText());
                } else supsupressSliderChange = false;
                tmp = (int) (zuVerteilen * (prozent/100));
                verteiltLabel.setText(bereitsVerteilt+tmp + "/" + zuVerteilen + " verteilt.");
            }
        });
        prozentTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prozentTextFieldAction();
            }
        });
    }

    private void onOK() {
        //TODO: Wert an Control übergeben, Lager "disablen"
    	
        //prozent ist der wert (werte von 0,00-100,00)
    	ControllerSingleton.getBVInstance().createBuchung(auslieferung, lager, prozent);
    	okButton.setChanged();
    	okButton.notifyObservers();
    	
    	

        dispose();
    }

    private void onCancel() {
        //Fenster schließen
        dispose();
    }

    private void changeLabel(Double percent) {
        //TODO: muss noch kram getan werden
        //double max = Double.parseDouble(this.lagerLabel.getText().replaceAll("/.", ""));
        //double allocated = max / (percent/100);
        //System.out.println(max);
        //System.out.println(percent);
        //this.lagerLabel.setText(this.lagerLabel.getText().replaceAll("^\\d*[.,]?\\d*[^/]",Double.toString(allocated)));
    }

    private void prozentTextFieldAction() {
        //PopUpDialog popUp = new PopUpDialog();
        GUITools check = new GUITools();
        prozentSlider.setValue(Integer.parseInt(prozentTextField.getText()));
        int errorCode = check.BuchungInput(prozentTextField.getText());
//        //TODO: Fehlerhandling ggf. auslagern
        switch (errorCode) {
            case 0:
                supsupressSliderChange = true;
                prozentSlider.setValue(check.iValue);
                prozent = check.dValue;
                System.out.println(prozent);
                break;
//            case 1:
//                popUp.setMeldung("Nur Zahlenbereiche von 0-100.");
//                popUp.popUp();
//                break;
//            case 2:
//                popUp.setMeldung("Bitte nur Zahlen eingeben.");
//                popUp.popUp();
//                break;
//            default:
//                popUp.setMeldung("Ein unerwarteter Fehler ist aufgetreten.");
//                popUp.setTitle("Mertens, das sollte nicht so sein!");
//                popUp.popUp();
//                break;
        }
    }

    public Lager getLager()
    {
    	return lager;
    }
    
    public double getProzent()
    {
    	return prozent;
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		 
		
	}
}
