package Model;

import java.util.ArrayList;

public class LieferungFactory {
	/**
	 * Erstellt eine neue Zulieferung
	 * @param menge Die Menge der Artikel in der Lieferung
	 * @return Eine neu erzeugte und initialisierte Zulieferun
	 */
    public static Zulieferung CreateZulieferung(int menge) {
        return new Zulieferung(menge);
    }

    /**
     * Erstellt eine neue Auslieferung
     * @param menge Die Menge der Artikel in der Lieferung
     * @return Eine neu erzeugte und initialisierte Auslieferung
     */
    public static Auslieferung CreateAuslieferung(int menge) {
        return new Auslieferung(menge);
    }
}
