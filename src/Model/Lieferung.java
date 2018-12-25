package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public class Lieferung extends Observable implements Serializable {
    private Date timestamp;
    private ArrayList<Buchung> buchungen;
    private int gesamtmenge;

    public Lieferung(int gesamtmenge) {
        this.gesamtmenge = gesamtmenge;
        this.buchungen = new ArrayList<Buchung>();
        timestamp = new Date();
        this.setChanged();
        this.notifyObservers(this);
    }

    public void addBuchung(Buchung buchung) {
        buchungen.add(buchung);
        this.setChanged();
        this.notifyObservers();
    }

    public String getTimestamp() {
        return timestamp.toString();
    }

    public int getGesamtmenge() {
        return gesamtmenge;
    }

    public ArrayList<Buchung> getBuchungen() {
        return buchungen;
    }

    @Override
    public String toString() {
        return "Lieferung vom " + timestamp + " über " + gesamtmenge + " Artikel";
    }
}
