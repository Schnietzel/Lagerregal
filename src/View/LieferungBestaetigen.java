package View;

import Model.Lieferung;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.event.*;

public class LieferungBestaetigen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSlider slider;
    private JTextField input;
    private JLabel infoText;

    public LieferungBestaetigen() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //Textwert auf Sliderwert setzen
                    input.setText(String.valueOf(slider.getValue()));
            }
        });
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Eingabe in int Parsen, "," zu "."
                String eingabe = input.getText();
                String replaced = eingabe.replace(",",".");
                int value = (int) Double.parseDouble(replaced);

                slider.setValue(value);
            }
        });
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
        this.pack();
        this.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        //Wert an Control übergeben, Lager "disablen"
        dispose();
    }

    private void onCancel() {
        //Fenster schließen
        dispose();
    }

    public static void main(String[] args) {
        LieferungBestaetigen dialog = new LieferungBestaetigen();
    }
}
