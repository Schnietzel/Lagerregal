package Model;

import java.io.Serializable;

public class Buchung implements Serializable {
    private Lager ziellager;		// Das Lager, welches von der Buchung betroffen ist
    private double prozent;			// Der Prozentsatz der Gesamtmenge der Lieferung

    /**
     * Der Konstruktor der Buchung
     * @param ziellager Das Lager, welches von der Buchung betroffen ist
     * @param prozent Der Prozentsatz der Gesamtmenge der Lieferung
     */
    public Buchung(Lager ziellager, double prozent) {
        this.ziellager = ziellager;
        this.prozent = prozent;
    }

    /**
     * Gibt das Ziellager zur�ck
     * @return Das Lager, welches von der Buchung betroffen ist
     */
    public Lager getZiellager() {
        return ziellager;
    }

    /**
     * Gibt den Prozentsatz zur�ck
     * @return Der Prozentsatz der Gesamtmenge der Lieferung
     */
    public double getProzent() {
        return prozent;
    }
    
    /**
     * �berl�dt die Object.toString()-Methode, um die Buchung lesbar darzustellen.
     */
    @Override
    public String toString() {
        return "   " + ziellager.getName() + ": " + prozent + "%";
    }
}
