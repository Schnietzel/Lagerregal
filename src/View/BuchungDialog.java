package View;

import Controller.EingabeCheck;

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
    private Double percentage;

    BuchungDialog(String info) {
        setContentPane(contentPane);
        this.setTitle("Buchung durchf�hren");
        this.infoText.setText(info);
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

    private void onOK() {
        //TODO: Wert an Control �bergeben, Lager "disablen"
        //percentage ist der wert (werte von 0,00-100,00)
        dispose();
    }

    private void onCancel() {
        //Fenster schlie�en
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
        PopUpDialog popUp = new PopUpDialog();
        EingabeCheck check = new EingabeCheck();
        int errorCode = check.BuchungInput(input.getText());
        //TODO: Fehlerhandling ggf. auslagern
        switch (errorCode) {
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

//    {
//// GUI initializer generated by IntelliJ IDEA GUI Designer
//// >>> IMPORTANT!! <<<
//// DO NOT EDIT OR ADD ANY CODE HERE!
//        $$$setupUI$$$();
//    }
//
//    /**
//     * Method generated by IntelliJ IDEA GUI Designer
//     * >>> IMPORTANT!! <<<
//     * DO NOT edit this method OR call it in your code!
//     *
//     * @noinspection ALL
//     */
//    private void $$$setupUI$$$() {
//        contentPane = new JPanel();
//        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
//        final JPanel panel1 = new JPanel();
//        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
//        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
//        final JPanel panel2 = new JPanel();
//        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
//        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        buttonCancel = new JButton();
//        this.$$$loadButtonText$$$(buttonCancel, ResourceBundle.getBundle("String").getString("abbrechen"));
//        panel2.add(buttonCancel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        final Spacer spacer1 = new Spacer();
//        panel2.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
//        buttonOK = new JButton();
//        this.$$$loadButtonText$$$(buttonOK, ResourceBundle.getBundle("String").getString("ok"));
//        panel2.add(buttonOK, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        final JPanel panel3 = new JPanel();
//        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
//        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        slider = new JSlider();
//        panel3.add(slider, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        input = new JTextField();
//        panel3.add(input, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
//        infoText = new JLabel();
//        infoText.setText("");
//        panel3.add(infoText, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//    }
//
//    /**
//     * @noinspection ALL
//     */
//    private void $$$loadButtonText$$$(AbstractButton component, String text) {
//        StringBuffer result = new StringBuffer();
//        boolean haveMnemonic = false;
//        char mnemonic = '\0';
//        int mnemonicIndex = -1;
//        for (int i = 0; i < text.length(); i++) {
//            if (text.charAt(i) == '&') {
//                i++;
//                if (i == text.length()) break;
//                if (!haveMnemonic && text.charAt(i) != '&') {
//                    haveMnemonic = true;
//                    mnemonic = text.charAt(i);
//                    mnemonicIndex = result.length();
//                }
//            }
//            result.append(text.charAt(i));
//        }
//        component.setText(result.toString());
//        if (haveMnemonic) {
//            component.setMnemonic(mnemonic);
//            component.setDisplayedMnemonicIndex(mnemonicIndex);
//        }
//    }
//
//    /**
//     * @noinspection ALL
//     */
//    public JComponent $$$getRootComponent$$$() {
//        return contentPane;
//    }
}