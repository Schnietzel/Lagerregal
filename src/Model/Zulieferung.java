package Model;

import java.util.ArrayList;

public class Zulieferung extends Lieferung {
	/**
	 * Konstruktor, der lediglich den Konstruktor der Superklasse aufruft
	 * @param gesamtmenge Die Gesamtmenge der Lieferung
	 */
    public Zulieferung(int gesamtmenge) {
        super(gesamtmenge);
    }

    /**
     * Überlädt die Object.toString()-Methode, um die Zulieferung lesbar darzustellen.
     */
    @Override
    public String toString() {
        return "Zulieferung vom " + super.getTimestamp() + " über " + super.getGesamtmenge() + " Artikel";
    }
}
