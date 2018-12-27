package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Lager implements Serializable {
    private String name;					// Name des Lagers
    private int kapazitaet;					// Kapazität des Lagers
    private int bestand;					// Aktueller Bestand des Lagers
    private ArrayList<Lager> unterlager;	// Liste aller Unterlager

    private int ebene;						// Ebene des Lagers. 0 = Rootlager

    /**
     * Konstruktor, der nur den Namen des Lagers braucht. Restliche Members werden initialisiert.
     * @param name Name des Lagers
     */
    public Lager(String name) {
    	this.name = name;
    	this.kapazitaet = 0;
    	this.bestand = 0;
    	this.unterlager = new ArrayList<Lager>();
    }

    /**
     * Konstruktor für ein Lager ohne Unterlager
     * @param name Name des Lagers
     * @param kapazitaet Kapazität des Lagers
     * @param bestand Aktueller Bestand des Lagers
     */
    public Lager(String name, int kapazitaet, int bestand) {
        this.name = name;
        this.kapazitaet = kapazitaet;
        this.bestand = bestand;
        unterlager = new ArrayList<Lager>();
    }

    /**
     * Konstruktor für ein Lager mit Unterlagern
     * @param name Name des Lagers
     * @param unterlager ArrayList aller Unterlager
     */
    public Lager(String name, ArrayList<Lager> unterlager) {
        this.name = name;
        this.unterlager = unterlager;
        for (Lager lager : unterlager) {
            kapazitaet += lager.getKapazitaet();
            bestand += lager.getBestand();
        }

    }

    /**
     * Methode, um Unterlager zum Lager hinzuzufügen
     * @param lager Das Unterlager, das hinzugefügt werden soll
     */
    public void addUnterlager(Lager lager) {
    	unterlager.add(lager);
    }

    /**
     * Methode, um ein Unterlager zu entfernen
     * @param lager Das Unterlager, das entfernt werden soll.
     */
	public void removeUnterlager(Lager lager) {
		// TODO Auto-generated method stub
		this.unterlager.remove(lager);
	}

	/**
	 * Methode um die Liste der Unterlager zu erhalten
	 * @return ArrayList mit allen Unterlagern des Lagers
	 */
    public ArrayList<Lager> getUnterlager() {
        return unterlager;
    }

    /**
     * Methode, um Bestand hinzuzubuchen
     * @param anzahl Die Anzahl der Artikeln, die dem Lager zugebucht werden sollen
     */
    public void addBestand(int anzahl) {
        this.bestand += anzahl;
    }

    /**
     * Methode, um Bestand abzubuchen
     * @param anzahl Die Anzahl der Artikel, die aus dem Lager entfernt werden. Wenn anzahl=0 werden alle Artikel aus dem Lager entfernt
     * @return Die Anzahl der Artikel, die entfernt wurden.
     */
    public int removeBestand(int anzahl) {
        int ret = bestand;

        if (anzahl == 0) {
            bestand = 0;
            return ret;
        } else {
            bestand -= anzahl;
        }

        return anzahl;
    }

    /**
     * Methode, um den Bestand auf einen bestimmten Wert zu setzen.
     * Nur bei der aktualisierung der Bestände von Oberlagern benutzen!
     * @param bestand Der Bestand, der gesetzt werden soll
     */
    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    /**
     * Methode, um den aktuellen Bestand zu erhalten
     * @return Der aktuelle Bestand des Lagers
     */
    public int getBestand() {
        return bestand;
    }

    /**
     * Methode, um den Namen des Lagers zu setzen
     * @param name Der neue Name des Lagers
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode, um den aktuellen Namen zu erhalten
     * @return Der aktuelle Name des Lagers
     */
    public String getName() {
        return name;
    }

    /**
     * Methode, um die Kapazität des Lagers zu setzen
     * @param kap Die neue Kapazität des Lagers
     */
    public void setKapazitaet(int kap) {
        this.kapazitaet = kap;
    }

    /**
     * Methode, um die aktuelle Kapazität zu erhalten
     * @return Die aktuelle Kapazität des Lagers
     */
    public int getKapazitaet() {
        return kapazitaet;
    }

	/**
	 * Methode, um die aktuelle Ebene des Lagers in der Hierarchie zu setzen.
	 * @param ebene Ebene des Lagers
	 */
    public void setEbene(int ebene) {
        this.ebene = ebene;
    }
    
    /**
     * Überlädt die Object.toString()-Methode, um die Lager lesbar darzustellen.
     */
    @Override
    public String toString() {
        String ret = name + ", Bestand: " + bestand + "/" + kapazitaet;
        for (int i = 0; i < ebene; i++) {
            ret = "   " + ret;
        }
        return ret;
    }
}
