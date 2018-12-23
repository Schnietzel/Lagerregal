package View;

import Controller.Buchungsverwaltung;
import Controller.GUITools;
import Model.Lager;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class BuchungDialog extends JDialog {
	
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSlider slider;
    private JTextField input;
    private JLabel infoText;
    private boolean supressSliderChange = false;
    
    private Lager lager;
    private boolean auslieferung;
    private Double percentage;

    BuchungDialog(Lager info, boolean lieferung) 
    {
    	this.lager = info;
    	this.percentage = 0.0;
    	
    	initGUI();
    	
        setContentPane(contentPane);
        this.setTitle("Buchung durchführen");
        this.infoText.setText(lager.getName());
        this.auslieferung = lieferung;
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(new Dimension(450, 200));
        this.validate();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        createListener();
    }

    private void initGUI()
    {
    	// TODO: loadButtonText entfernen
    	
    	contentPane = new JPanel();
    	contentPane.setLayout(new GridLayout());
      
    	final JPanel panel1 = new JPanel();
    	panel1.setLayout(new GridLayout());
    	contentPane.add(panel1);
    	
    	final JPanel panel2 = new JPanel();
    	panel2.setLayout(new GridLayout());
    	panel1.add(panel2);
    	
    	buttonCancel = new JButton();
    	this.$$$loadButtonText$$$(buttonCancel, ResourceBundle.getBundle("String").getString("abbrechen"));
    	panel2.add(buttonCancel);
      
    	// TODO: Spacer(?)
    	// final Spacer spacer1 = new Spacer();
    	// panel2.add(spacer1);
      
    	buttonOK = new JButton();
    	this.$$$loadButtonText$$$(buttonOK, ResourceBundle.getBundle("String").getString("ok"));
    	panel2.add(buttonOK);

    	final JPanel panel3 = new JPanel();
    	panel3.setLayout(new GridLayout());
    	contentPane.add(panel3);

    	slider = new JSlider();
    	panel3.add(slider);
    	
    	input = new JTextField();
    	panel3.add(input);

    	infoText = new JLabel();
    	panel3.add(infoText);
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
                    percentage = Double.valueOf(input.getText());
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

    private void onOK() {
        //TODO: Wert an Control übergeben, Lager "disablen"
        //percentage ist der wert (werte von 0,00-100,00)
    	System.out.println(percentage);
    	Buchungsverwaltung.createBuchung(auslieferung, lager, percentage);
        dispose();
    }

    private void onCancel() {
        //Fenster schließen
        dispose();
    }

    private void changeLabel(Double percent) {
        //TODO: muss noch kram getan werden
        //double max = Double.parseDouble(this.infoText.getText().replaceAll("/.", ""));
        //double allocated = max / (percent/100);
        //System.out.println(max);
        //System.out.println(percent);
        //this.infoText.setText(this.infoText.getText().replaceAll("^\\d*[.,]?\\d*[^/]",Double.toString(allocated)));
    }

    private void inputAction() {
        //PopUpDialog popUp = new PopUpDialog();
        GUITools check = new GUITools();
        slider.setValue(Integer.parseInt(input.getText()));
        int errorCode = check.BuchungInput(input.getText());
//        //TODO: Fehlerhandling ggf. auslagern
//        switch (errorCode) {
//            case 0:
//                supressSliderChange = true;
//                slider.setValue(check.iValue);
//                percentage = check.dValue;
//                //changeLabel(check.dValue);
//                break;
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
//        }
    }

    public Lager getLager()
    {
    	return lager;
    }
    
    public double getProzent()
    {
    	return percentage;
    }
    
    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
