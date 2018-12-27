package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import Model.Auslieferung;
import Model.Buchung;
import Model.Lager;
import Model.Lieferung;
import Model.LieferungFactory;
import Model.Zulieferung;

public class Buchungsverwaltung extends Observable {
    private Lagerverwaltung lv;					// Verweis auf die Instanz der Lagerverwaltung
    private Dateiverwaltung dv;					// Verweis auf die Instanz der Dateiverwaltung

    private Stack<Buchung> buchungenUndo;		// Undo-Stack für Buchungen
    private Stack<Buchung> buchungenRedo;		// Redo-Stack für Buchungen

    private Stack<Lieferung> lieferungenUndo;	// Undo-Stack für Gesamte Lieferungen
    private Stack<Lieferung> lieferungenRedo;	// Redo-Stack für Gesamte Lieferungen
    private ArrayList<Lieferung> historie;		// Liste der gesamten Lieferungen

    private Lieferung aktuelleLieferung;		// Aktuell bearbeitete Lieferung

    private int restMenge;						// Restmenge, die noch weiter verbucht werden muss
    int verteilteMenge;							// Menge, die bereits verbucht ist

    /**
     * Konstruktor, der alles initialisiert
     */
    public Buchungsverwaltung() {
        lv = ControllerSingleton.getLVInstance();
        dv = ControllerSingleton.getDVInstance();

        buchungenUndo = new Stack<Buchung>();
        buchungenRedo = new Stack<Buchung>();

        lieferungenUndo = new Stack<Lieferung>();
        lieferungenRedo = new Stack<Lieferung>();

        ladeHistorie();
    }

    /**
     * Lädt die Historiendatei. 
     * Falls sie nicht existiert, wird eine neue, leere Liste initialisiert.
     */
    private void ladeHistorie() {
        File file = new File(".historie");
        if (file.exists())
        	historie = dv.ladeHistorie(file);
        else
            historie = new ArrayList<Lieferung>();
    }

    /**
     * Speichert die Historiendatei. Wird aufgerufen beim Schließen des Programms
     */
    public void close() {
        File file = new File(".historie");
        dv.speicherHistorie(file, historie);
    }

    /**
     * Gibt die aktuelle Historie zurück
     * @return Die aktuelle Historie
     */
    public ArrayList<Lieferung> getHistorie() {
        return historie;
    }

    /**
     * Eröffnet eine neue Zulieferung
     * @param gesamtmenge Die Menge, die für die Lieferung verbucht werden sollen
     * @return true, wenn die Lieferung eröffnet wurde, false, wenn die Gesamte Lagerkapazität nicht ausreicht
     */
    public boolean createZulieferung(int gesamtmenge) {
        if (gesamtmenge > lv.getFreieGesamtlagerkapazität()) {
            return false;
        }

        aktuelleLieferung = LieferungFactory.CreateZulieferung(gesamtmenge);
        restMenge = gesamtmenge;
        return true;
    }

    /**
     * Fügt eine neue Zubuchung zur aktuellen Lieferung hinzu
     * @param lager Das Lager, zu welchem Artikel gebucht werden sollen
     * @param prozent Der Prozentsatz der Gesamtmenge, die verbucht werden soll
     * @return true, wenn die Buchung erstellt wurde, false, wenn die Lagerkapazität nicht ausreicht oder die restmenge nicht ausreicht
     */
    public boolean createZubuchung(Lager lager, double prozent) {
        int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), prozent);
        if ((menge+lager.getBestand()) > lager.getKapazitaet() || menge > restMenge) {
            return false;
        }

        Buchung buchung = new Buchung(lager, prozent);
        buchungenUndo.push(buchung);
        restMenge -= menge;
        verteilteMenge += menge;
        return true;
    }

    /**
     * Schließt die aktuelle Lieferung ab und verbucht alle Buchungen
     */
    public void verteileZulieferung() {
        int size = buchungenUndo.size();
        for (int i = 0; i < size; i++) {

            Buchung buchung = buchungenUndo.pop();
            aktuelleLieferung.addBuchung(buchung);
        }

        System.out.println("Buchungsliste der Aktuelle Lieferung:" + aktuelleLieferung.getBuchungen().size() + " Elemente");
        for (Buchung buchung : aktuelleLieferung.getBuchungen()) {
            int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), buchung.getProzent());
            ControllerSingleton.getLVInstance().addLagerBestand(buchung.getZiellager(), menge);
        }

        lieferungenUndo.push(aktuelleLieferung);
        historie.add(aktuelleLieferung);
    }

    /**
     * Eröffnet eine neue Auslieferung
     * @param gesamtmenge Die Menge, die für die Lieferung verbucht werden sollen
     * @return true, wenn die Lieferung eröffnet wurde, false, wenn der Gesamte Lagerbestand nicht ausreicht
     */
    public boolean createAuslieferung(int gesamtmenge) {
        if (gesamtmenge > lv.getGesamtbestand()) {
            return false;
        }

        aktuelleLieferung = LieferungFactory.CreateAuslieferung(gesamtmenge);
        restMenge = gesamtmenge;
        return true;
    }

    /**
     * Fügt eine neue Abbuchung zur aktuellen Lieferung hinzu
     * @param lager Das Lager, zu welchem Artikel gebucht werden sollen
     * @param prozent Der Prozentsatz der Gesamtmenge, die verbucht werden soll
     * @return true, wenn die Buchung erstellt wurde, false, wenn der Lagerbestand nicht ausreicht oder die restmenge nicht ausreicht
     */
    public boolean createAbbuchung(Lager lager, double prozent) {
        int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), prozent);
        System.out.println("Menge der Abbuchung: "+menge);
        if (menge > lager.getBestand() || menge > restMenge) {
            return false;
        }

        Buchung buchung = new Buchung(lager, prozent);
        buchungenUndo.push(buchung);
        restMenge -= menge;
        verteilteMenge += menge;
        //hier muss die verteilte menge geupdatet werden

        return true;
    }

    /**
     * Schließt die aktuelle Lieferung ab und verbucht alle Buchungen
     */
    public void verteileAuslieferung() {
    	int size = buchungenUndo.size();
        for (int i = 0; i < size; i++) {
            Buchung buchung = buchungenUndo.pop();
            aktuelleLieferung.addBuchung(buchung);
        }

        for (Buchung buchung : aktuelleLieferung.getBuchungen()) {
            int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), buchung.getProzent());
            ControllerSingleton.getLVInstance().removeLagerBestand(buchung.getZiellager(), menge);
        }

        lieferungenUndo.push(aktuelleLieferung);
        historie.add(aktuelleLieferung);
    }

    /**
     * Setzt letzte Buchung zurück
     */
    public void undoBuchung() {
        buchungenRedo.push(buchungenUndo.pop());
        // ausgrauen rückgängig machen
    }

    /**
     * Wiederholt letzte Buchung
     */
    public void redoBuchung() {
        buchungenUndo.push(buchungenRedo.pop());
        // ausgrauen wiederherstellen
    }

    /**
     * Setzt letzte Lieferung zurück
     */
    public void undoLieferung() {
        aktuelleLieferung = lieferungenUndo.pop();
        if (aktuelleLieferung.getClass().equals(Auslieferung.class)) {
            verteileZulieferung();
        } else {
            verteileAuslieferung();
        }
        lieferungenRedo.push(aktuelleLieferung);
        historie.remove(aktuelleLieferung);
    }

    /**
     * Wiederholt letzte Lieferung
     */
    public void redoLieferung() {
        aktuelleLieferung = lieferungenRedo.pop();
        if (aktuelleLieferung.getClass().equals(Auslieferung.class)) {
            verteileZulieferung();
        } else {
            verteileAuslieferung();
        }
        lieferungenUndo.push(aktuelleLieferung);
        historie.add(aktuelleLieferung);
    }

    /**
     * Gibt aktuelle Lieferung zurück
     * @return Die aktuelle Lieferung
     */
    public Lieferung getAktuelleLieferung() {
        return aktuelleLieferung;
    }

    /**
     * Setzt die aktuelle Lieferung neu
     * @param aktuelleLieferung die neue Lieferung
     */
    public void setAktuelleLieferung(Lieferung aktuelleLieferung) {
        this.aktuelleLieferung = aktuelleLieferung;
    }

    /**
     * Berechnet die Einzelmenge mithilfe der Gesamtmenge und des Prozentsatzes
     * @param Gesamtmenge Gesamtwert
     * @param prozent Prozentsatz
     * @return Prozentwert
     */
    private int getEinzelmenge(int Gesamtmenge, double prozent) {
        return (int) (((double) Gesamtmenge / 100.0 * prozent) + 0.5);
    }

    /**
     * Berechnet die Schrittweite des Sliders
     * @param gesamtmenge Gesamtmenge
     * @return Die berechnete Schrittweite
     */
    public double getSchrittweite(int gesamtmenge) {
        return 100.0 / (double) gesamtmenge;
    }

    public Stack<Buchung> getBuchungenUndoStack() {
        return buchungenUndo;
    }

    public Stack<Buchung> getBuchungenRedoStack() {
        return buchungenRedo;
    }

    public Stack<Lieferung> getLieferungenUndoStack() {
        return lieferungenUndo;
    }

    public Stack<Lieferung> getLieferungenRedoStack() {
        return lieferungenRedo;
    }

    /**
     * Erstellt eine neue Lieferung, abhängig vom Radiobutton
     * @param auslieferung true, wenn Auslieferung, false, wenn Zulieferung
     * @param gesamtmenge Die Gesamtmenge der Lieferung
     * @return True, wenn Lieferung erfolgreich eröffnet, false, wenn nicht
     */
    public boolean createLieferung(boolean auslieferung, int gesamtmenge) {
        verteilteMenge = 0;
        if (auslieferung)
            return createAuslieferung(gesamtmenge);
        else
            return createZulieferung(gesamtmenge);

    }

    /**
     * Erstellt eine neue Buchung, abhängig vom Radiobutton
     * @param auslieferung true, wenn Auslieferung, false, wenn Zulieferung
     * @param lager Das Lager der Buchung
     * @param prozent Der Prozentsatz der Buchung
     * @return true, wenn Buchung erfolgreich erstellt, false wenn nicht
     */
    public boolean createBuchung(boolean auslieferung, Lager lager, double prozent) {
        System.out.println(prozent);
        if (auslieferung)
            return createAbbuchung(lager, prozent);
        else
            return createZubuchung(lager, prozent);
    }

    public int getRestMenge() {
        return restMenge;
    }

    public int getVerteilteMenge() {
        return verteilteMenge;
    }

    /**
     * Gibt zurück, ob zu dem gewählten Lager bereits eine Buchung in der aktuellen Lieferung existiert
     * @param lager
     * @return True, wenn Buchung existiert, false, wenn nicht
     */
    public boolean istBearbeitet(Lager lager) {
        return buchungenUndo.contains(lager);
    }

    /**
     * Erstellt eine neue Verteilung, abhängig vom Radiobutton
     * @param auslieferung True, wenn Auslieferung, false, wenn Zulieferung
     * @return true, wenn Verteilung erfolgreich, False, wenn noch Restartikel vorhanden sind
     */
    public boolean verteileLieferung(boolean auslieferung) {
        if (restMenge > 0)
            return false;
        if (auslieferung)
            verteileAuslieferung();
        else
            verteileZulieferung();
        return true;
    }

    public void setHistorie(ArrayList<Lieferung> historie)
    {
    	this.historie = historie;
    }
}
