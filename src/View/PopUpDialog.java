package View;

import javax.swing.*;

public class PopUpDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel infoText;

    PopUpDialog() {
        this.setTitle("Fehler");
        setContentPane(contentPane);
        setModal(true);

        buttonOK.addActionListener(e -> onOK());
        try {
            UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace ();
        }
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void popUp(){
        this.pack();
        this.setVisible(true);
    }
    public void setMeldung(String meldung){
        infoText.setText(meldung);
    }

    private void onOK() {
        // add your code here
        this.dispose();
    }

    public static void main(String[] args){
        PopUpDialog test = new PopUpDialog();
    }
}
