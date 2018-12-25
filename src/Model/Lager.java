package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Lager implements Serializable {
    private String name;
    private int kapazitaet;
    private int bestand;
    private ArrayList<Lager> unterlager;

    private int ebene;

    public Lager() {
        unterlager = new ArrayList<Lager>();
    }

    public Lager(String name, int kapazitaet, int bestand) {
        this.name = name;
        this.kapazitaet = kapazitaet;
        this.bestand = bestand;
        unterlager = new ArrayList<Lager>();
    }

    public Lager(String name, ArrayList<Lager> unterlager) {
        this.name = name;
        this.unterlager = unterlager;
        for (Lager lager : unterlager) {
            kapazitaet += lager.getKapazitaet();
            bestand += lager.getBestand();
        }

    }

    public void addUnterlager(Lager lager) {
        if (unterlager.isEmpty()) {
            unterlager.add(lager);
            lager.addBestand(this.bestand);

        } else {
            unterlager.add(lager);
        }
    }

    public void addBestand(int anzahl) {
        this.bestand += anzahl;
    }

    /**
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

    @Override
    public String toString() {
        String ret = name + ", Bestand: " + bestand + "/" + kapazitaet;
        for (int i = 0; i < ebene; i++) {
            ret = "   " + ret;
        }
        return ret;
    }

    public void setEbene(int ebene) {
        this.ebene = ebene;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public int getBestand() {
        return bestand;
    }

    public ArrayList<Lager> getUnterlager() {
        return unterlager;
    }

    public void setKapazitaet(int kap) {
        this.kapazitaet = kap;
    }

}
