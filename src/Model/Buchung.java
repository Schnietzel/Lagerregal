package Model;

import java.io.Serializable;

public class Buchung implements Serializable {
    private Lager ziellager;
    private double prozent;

    public Buchung(Lager ziellager, double prozent) {
        this.ziellager = ziellager;
        this.prozent = prozent;
    }


    public Lager getZiellager() {
        return ziellager;
    }

    public void setZiellager(Lager ziellager) {
        this.ziellager = ziellager;
    }

    public double getProzent() {
        return prozent;
    }

    public void setProzent(double prozent) {
        this.prozent = prozent;
    }

    @Override
    public String toString() {
        return "   " + ziellager.getName() + ": " + prozent + "%";
    }
}
