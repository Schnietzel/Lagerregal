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
     * �berl�dt die Object.toString()-Methode, um die Auslieferung lesbar darzustellen.
     */
    @Override
    public String toString() {
        System.out.println("Auslieferung toString");
        return "Auslieferung vom " + super.getTimestamp() + " �ber " + super.getGesamtmenge() + " Artikel";
    }
}
