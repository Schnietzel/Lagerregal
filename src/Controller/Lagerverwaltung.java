package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Model.Lager;
import Model.Lieferung;
import View.GUI;

public class Lagerverwaltung {
    private Dateiverwaltung dv;			// Singleton-Instanz der Dateiverwaltung
    private ArrayList<Lager> lager;		// Die Liste der Lager auf der Obersten Ebene
    
    /**
     * Konstruktor, der alles wichtige initialisiert
     */
    public Lagerverwaltung() {
    	dv = ControllerSingleton.getDVInstance();
    	
        ladeLager();
        updateLager();
    }

    /**
     * Lädt die bestehende Lager-Datei.
     * Falls keine gefunden wird, wird das Testlager initialisiert
     */
    private void ladeLager() {
        File file = new File(".lager");
        if (file.exists())
        	lager = dv.ladeLager(file);
        else
            initTestlager();
    }

    /**
     * Speichert die aktuelle Lagerkonfiguration in einer Datei
     * Wird automatisch bei Programmende aufgerufen
     */
    public void close() {
        File file = new File(".lager");
        dv.speicherLager(file, lager);
    }

    /**
     * Getter für die aktuelle Lagerliste
     * @return Die Liste der Oberlager
     */
    public ArrayList<Lager> getLagerList() {
        return lager;
    }

    /**
     * Berechnet die Freie Lagerkapazität
     * @return Die Freie Lagerkapazität
     */
    public int getFreieGesamtlagerkapazität() {
        int kap = 0;
        for (Lager l : lager) {
            kap += (l.getKapazitaet() - l.getBestand());
        }
        return kap;
    }

    /**
     * Berechnet den Gesamtbestand
     * @return Der Gesamtbestand
     */
    public int getGesamtbestand() {
        int bestand = 0;
        for (Lager l : lager) {
            bestand += (l.getBestand());
        }
        return bestand;
    }

    /**
     * Führt für alle Oberlager das Rekursive Lagerupdate mit Ebene 0 durch
     */
    public void updateLager() {
        for (Lager l : lager) {
            updateLagerRec(l, 0);
        }
    }
    
    /**
     * Fügt ein Lager auf der obersten Hierarchie-Ebene hinzu
     */
    public void addLager() { 
    	System.out.println("Lager hinzugefügt");
    	lager.add(new Lager("Neues Lager"));
    }
    
    /**
     * Fügt ein Lager auf einer unteren Hierarchie-Ebene hinzu
     * @param lager Das Lager, unter dem das neue Lager hinzugefügt wird
     */
    public void addLager(Lager lager) {
    	Lager found = null;
    	int tmpBestand = 0;
    	System.out.println(lager.getName());
    	found = searchForName(lager.getName(), this.lager, found);
    	System.out.println("Found = "+ found);
    	
    	if(found.getUnterlager().isEmpty()&&found.getBestand()>0) {
    		tmpBestand=found.getBestand();
    	}
    	
    	found.addUnterlager(new Lager("neues Lager", tmpBestand, tmpBestand));
    	updateLager();
    	for(Lager tmp : found.getUnterlager()) {
    		System.out.println(tmp.getName());
    	}
    	
    	//searchForName(l.getName(), this.lager).addUnterlager(new Lager("Neues Lager"));    
    }
    
    public boolean removeLager(Lager l) {
    	Lager parent = null;
    	int tmpKapazität = l.getKapazitaet();
    	int tmpBestand = l.getBestand();
    	boolean removed = false;
    	if(l.getUnterlager().isEmpty()||l.getBestand()==0) {
    		if(!this.lager.contains(l)) {
    			parent = searchForParent(l, this.lager , parent);
    			parent.setBestand(parent.getBestand()-tmpBestand);
    			parent.setKapazitaet(parent.getKapazitaet()-tmpKapazität);
    			parent.removeUnterlager(l);
    			removed = true;
    		}
    		else {
    		
    			this.lager.remove(l);
    			removed = true;
 
    		}
    	}
    	
    	updateLager();
    	return removed;
    }

    /**
     * Aktualisiert rekursiv die Ebene der Lager sowie den Bestand und die Kapazität
     * @param lager das aktuelle Lager, welches aktualisiert wird
     * @param depth die aktuelle Rekursionsiefe
     */
    private void updateLagerRec(Lager lager, int depth) {
        lager.setEbene(depth);
        depth++;
        for (Lager sub : lager.getUnterlager()) {
            updateLagerRec(sub, depth);
        }

        if (!lager.getUnterlager().isEmpty()) {
            int bestand = 0;
            int kap = 0;
            for (Lager sub : lager.getUnterlager()) {
                bestand += sub.getBestand();
                kap += sub.getKapazitaet();
            }
            lager.setBestand(bestand);
            lager.setKapazitaet(kap);
        }
    }
    
    public Lager searchForName(String name, ArrayList<Lager> lagerList, Lager result) {
    	
	    	
    	for(Lager l : lagerList) 
    	{
    		System.out.println(l.getName());
    		if(l.getName() == name) {
    			System.out.println("gefunden");
    			
    			result = l;
    		}
    		else if(!l.getUnterlager().isEmpty()&&l.getName()!=name&&result==null) {
    			
    			result = searchForName(name, l.getUnterlager(), result);
    		}
    		
    	}
    	
    return result;
    }
    
    public Lager searchForParent(Lager searched, ArrayList<Lager> lagerList, Lager result) {
    	for(Lager l : lagerList) 
    	{
    		
    		if(l.getUnterlager().contains(searched)) {
    			System.out.println("gefunden");
    			
    			result = l;
    		}
    		else if(!l.getUnterlager().isEmpty()&&!l.getUnterlager().contains(searched)&&result==null) {
    			
    			result = searchForParent(searched, l.getUnterlager(), result);
    		}
    		
    	}
    	
    return result;
    	
    }
    
    public void setLagerListe(ArrayList<Lager> lager)
    {
    	this.lager = lager;
    }

    /**
     * Initialisiert die Lagerliste mit Testlagern
     */
    public void initTestlager() {
    	lager = new ArrayList<Lager>();
        ArrayList<Lager> lNds = new ArrayList<Lager>();
        ArrayList<Lager> lDeutschland = new ArrayList<Lager>();
        ArrayList<Lager> lFrankreich = new ArrayList<Lager>();
        ArrayList<Lager> lEuropa = new ArrayList<Lager>();

        Lager Hannover = new Lager("Hannover", 10, 0);
        Lager Nienburg = new Lager("Nienburg", 10, 10);
        lNds.add(Hannover);
        lNds.add(Nienburg);

        Lager Niedersachsen = new Lager("Niedersachsen", lNds);
        Lager NRW = new Lager("NRW", 10, 10);
        Lager Bremen = new Lager("Bremen", 10, 10);
        Lager Hessen = new Lager("Hessen", 10, 10);
        lDeutschland.add(Niedersachsen);
        lDeutschland.add(NRW);
        lDeutschland.add(Bremen);
        lDeutschland.add(Hessen);

        Lager Deutschland = new Lager("Deutschland", lDeutschland);

        Lager Paris = new Lager("Paris", 10, 10);
        Lager Orleans = new Lager("Orleans", 10, 10);
        lFrankreich.add(Paris);
        lFrankreich.add(Orleans);


        Lager Frankreich = new Lager("Frankreich", lFrankreich);
        Lager England = new Lager("England", 10, 10);
        lEuropa.add(Frankreich);
        lEuropa.add(England);

        Lager Europa = new Lager("Europa", lEuropa);

        lager.add(Deutschland);
        lager.add(Europa);
    }
}
