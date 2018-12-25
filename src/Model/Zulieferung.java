package Model;

import java.util.ArrayList;

public class Zulieferung extends Lieferung {
    public Zulieferung(int gesamtmenge) {
        super(gesamtmenge);
    }

    @Override
    public String toString() {
        return "Zulieferung vom " + super.getTimestamp() + " über " + super.getGesamtmenge() + " Artikel";
    }
}
