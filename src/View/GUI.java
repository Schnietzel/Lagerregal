package View;

import mdlaf.MaterialLookAndFeel;

import javax.swing.*;

public class GUI extends JFrame {
    private JPanel outerpanel;
    private JTabbedPane tabs;
    private JTree tree1;
    private JButton undoButton;
    private JButton redoButton;
    private JList list1;
    private JPanel innerpanel;
    private JToolBar toolbar;

    public GUI(){
        this.setContentPane(outerpanel);
        try {
            UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace ();
        }
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
