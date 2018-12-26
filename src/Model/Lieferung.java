package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public class Lieferung extends Observable implements Serializable {
    private Date timestamp;					// Eindeutiger Timestamp des Lagers
    private ArrayList<Buchung> buchungen;	// Liste aller Buchungen, die in der Lieferung vorhanden sind
    private int gesamtmenge;				// Gesamtemenge der Artkel in der Lieferung

    /**
     * Konstruktor der Lieferung
     * Initialisiert alle Member und setzt den Timestamp auf die aktuelle Zeit
     * @param gesamtmenge Gesamtmenge der Artikel in der Lieferung
     */
    public Lieferung(int gesamtmenge) {
        this.gesamtmenge = gesamtmenge;
        this.buchungen = new ArrayList<Buchung>();
        timestamp = new Date();
        this.setChanged();
        this.notifyObservers(this);
    }

    /**
     * Methode, um eine Buchung zur Lieferung hinzuzufügen
     * @param buchung Die Buchung, die zur Lieferung hinzugefügt werden soll
     */
    public void addBuchung(Buchung buchung) {
        buchungen.add(buchung);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Methode, die die Liste der Buchungen zurückgibt
     * @return Die Liste der Buchungen
     */
    public ArrayList<Buchung> getBuchungen() {
        return buchungen;
    }

    /**
     * Methode, die den eindeutigen Timestamp der Lieferung zurückgibt
     * @return Der Timestamp als String
     */
    public String getTimestamp() {
        return timestamp.toString();
    }

    /**
     * Methode, die die Gesamtmenge zurückgibt
     * @return Die Gesamtmenge der Lieferung
     */
    public int getGesamtmenge() {
        return gesamtmenge;
    }

    /**
     * Überlädt die Object.toString()-Methode, um die Lager lesbar darzustellen.
     */
    @Override
    public String toString() {
        return "Lieferung vom " + timestamp + " über " + gesamtmenge + " Artikel";
    }
}
