package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Meldung extends JFrame {

    Meldung(String text, String titel){
        //ist noch ugly
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        setTitle(titel);
        JLabel textLabel = new JLabel();
        textLabel.setText(text);
        rootPanel.add(textLabel);

        JButton button = new JButton();
        button.setText("Okay");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        rootPanel.add(button);

        setContentPane(rootPanel);
    }
    void open(){
        this.pack();
        this.setVisible(true);
        //Definitiv noch anpassen
        this.setMinimumSize(new Dimension(100, 100));
        this.validate();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        Meldung test = new Meldung("TestText","TITEL");
        test.open();
    }
}
