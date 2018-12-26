package Model;

public class Auslieferung extends Lieferung {
	/**
	 * Konstruktor, der lediglich den Konstruktor der Superklasse aufruft
	 * @param gesamtmenge Die Gesamtmenge der Lieferung
	 */
    public Auslieferung(int gesamtmenge) {
        super(gesamtmenge);
    }

    /**
     * Überlädt die Object.toString()-Methode, um die Auslieferung lesbar darzustellen.
     */
    @Override
    public String toString() {
        System.out.println("Auslieferung toString");
        return "Auslieferung vom " + super.getTimestamp() + " über " + super.getGesamtmenge() + " Artikel";
    }
}
