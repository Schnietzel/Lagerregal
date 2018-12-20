package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LieferungPopup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea infotext;

    public LieferungPopup() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        try {
            UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace ();
        }
        infotext.setText("Ungültiger Wert, bitte geben sie nur Ganzzahlige Beträge zum Verteilen ein.");
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void onOK() {
        // add your code here
        this.dispose();
    }
}
