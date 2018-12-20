package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.UIManager.*;

public class GUI extends JFrame {
    private JPanel outerpanel;
    private JTabbedPane tabs;
    private JButton undoButton;
    private JButton redoButton;
    private JList hList;
    private JPanel innerpanel;
    private JToolBar toolbar;
    private JPanel lEingabePanel;
    private JPanel lAnsichtPanel;
    private JList lList;
    private JScrollPane lScrollPane;
    private JScrollPane hScrollPane;
    private JButton lBestaetigen;
    private JTextField lEingabeFeld;
    private JList aList;
    private JScrollPane aScrollPane;
    private JPanel ansicht;
    private JPanel lieferung;
    private JPanel historie;
    private Object ListModel;

    public GUI(){
        this.setContentPane(outerpanel);
        try {
            setLookAndFeel (getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace ();
        }
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        lBestaetigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Daten an Ceddi übergeben
                //Wenn Zahl: Methode gueltigeZahl aufrufen
                //Wenn keine Zahl: Methode ungueltigeZahl Aufrufen.
            }
        });
        lList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() == 2 && !e.isConsumed()){
                    e.consume();
                    lListClick();
                }
            }
        });
    }

    public static void main(String[] args){
        GUI test = new GUI();
    }

    public void lGueltigeZahl(){
        lBestaetigen.setEnabled(false);
        lEingabeFeld.setEnabled(false);
        lList.setEnabled(true);
    }
    public void lungueltigeZahl(){
        LieferungPopup popup = new LieferungPopup();
    }
    public void lListClick(){
        LieferungBestaetigen lBest = new LieferungBestaetigen();
    }
}
