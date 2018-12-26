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
    private Lagerverwaltung lv;
    private Dateiverwaltung dv;

    private Stack<Buchung> buchungenUndo;
    private Stack<Buchung> buchungenRedo;

    private Stack<Lieferung> lieferungenUndo;
    private Stack<Lieferung> lieferungenRedo;
    private ArrayList<Lieferung> historie;

    private Lieferung aktuelleLieferung;

    public Lieferung getAktuelleLieferung() {
        return aktuelleLieferung;
    }

    public void setAktuelleLieferung(Lieferung aktuelleLieferung) {
        this.aktuelleLieferung = aktuelleLieferung;
    }

    private int restMenge;
    int verteilteMenge;

    public Buchungsverwaltung() {
        lv = ControllerSingleton.getLVInstance();
        dv = ControllerSingleton.getDVInstance();

        buchungenUndo = new Stack<Buchung>();
        buchungenRedo = new Stack<Buchung>();

        lieferungenUndo = new Stack<Lieferung>();
        lieferungenRedo = new Stack<Lieferung>();

        ladeHistorie();
    }

    private void ladeHistorie() {
        // TODO: Datei festlegen, Exceptions und so
        try {
            String datei = "C:\\Test\\bla.historie";
            File file = new File(datei);
            historie = dv.ladeHistorie(file);
        } catch (Exception e) {
            e.printStackTrace();
            historie = new ArrayList<Lieferung>();
        }
    }

    public void close() {
        // TODO: Datei festlegen, Exceptions und so
        try {
            String datei = "C:\\Test\\bla.historie";
            File file = new File(datei);
            dv.speicherHistorie(file, historie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Lieferung> getHistorie() {
        return historie;
    }

    public boolean createZulieferung(int gesamtmenge) {
        if (gesamtmenge > lv.getFreieGesamtlagerkapazität()) {
            return false;
        }

        aktuelleLieferung = LieferungFactory.CreateZulieferung(gesamtmenge);
        restMenge = gesamtmenge;
        return true;
    }

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

    public boolean createAuslieferung(int gesamtmenge) {
        if (gesamtmenge > lv.getGesamtbestand()) {
            return false;
        }

        aktuelleLieferung = LieferungFactory.CreateAuslieferung(gesamtmenge);
        restMenge = gesamtmenge;
        return true;
    }

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

    public void undoBuchung() {
        buchungenRedo.push(buchungenUndo.pop());
        // ausgrauen rückgängig machen
    }

    public void redoBuchung() {
        buchungenUndo.push(buchungenRedo.pop());
        // ausgrauen wiederherstellen
    }

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

    private int getEinzelmenge(int Gesamtmenge, double prozent) {
        return (int) (((double) Gesamtmenge / 100.0 * prozent) + 0.5);
    }

    public double getSchrittweite(int Gesamtmenge) {
        return 100.0 / (double) Gesamtmenge;
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

    public boolean createLieferung(boolean Auslieferung, int gesamtmenge) {
        verteilteMenge = 0;
        if (Auslieferung)
            return createAuslieferung(gesamtmenge);
        else
            return createZulieferung(gesamtmenge);

    }

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

    public boolean istBearbeitet(Lager lager) {
        return buchungenUndo.contains(lager);
    }

    public boolean verteileLieferung(boolean auslieferung) {
        if (restMenge > 0)
            return false;
        if (auslieferung)
            verteileAuslieferung();
        else
            verteileZulieferung();
        return true;
    }


}
