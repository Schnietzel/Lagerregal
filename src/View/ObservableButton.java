package View;

import java.util.Observable;

import javax.swing.JButton;

public class ObservableButton extends Observable {

    JButton button = new JButton();

    public JButton getButton() {
        return button;
    }

    public void setChanged() {
        super.setChanged();
    }

}
