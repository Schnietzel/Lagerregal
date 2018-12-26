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
    private ArrayList<Lager> lager = new ArrayList<Lager>();

    public Lagerverwaltung() {
        ladeLager();
        updateLager();
    }

    private void ladeLager() {
        // TODO: Datei festlegen, Exceptions und so
        try {
            String datei = "C:\\Test\\bla.lager";
            File file = new File(datei);
            lager = ControllerSingleton.getDVInstance().ladeLager(file);
        } catch (Exception e) {
            e.printStackTrace();
            lager = new ArrayList<Lager>();
            initTestlager();
        }
    }

    public void close() {
        // TODO: Datei festlegen, Exceptions und so
        try {
            String datei = "C:\\Test\\bla.lager";
            File file = new File(datei);
            ControllerSingleton.getDVInstance().speicherLager(file, lager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Lager> getLagerList() {
        return lager;
    }

    public int getFreieGesamtlagerkapazität() {
        int kap = 0;
        for (Lager l : lager) {
            kap += (l.getKapazitaet() - l.getBestand());
        }
        return kap;
    }

    public int getGesamtbestand() {
        int bestand = 0;
        for (Lager l : lager) {
            bestand += (l.getBestand());
        }
        return bestand;
    }

    public void updateLager() {
        for (Lager l : lager) {
            updateLagerRec(l, 0);
        }
    }
    
    public void addLager() { //Lager auf Oberster Hierachie-Ebene hinzufügen
    	System.out.println("Lager hinzugefügt");
    	lager.add(new Lager("Neues Lager"));
    }
    
    public void addLager(Lager l) { //Lager auf unter anderem Lager hinzufügen
    	Lager found = null;
    	int tmpBestand = 0;
    	System.out.println(l.getName());
    	found = searchForName(l.getName(), this.lager, found);
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
    			parent.removerUnterlager(l);
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

    private void updateLagerRec(Lager l, int depth) {
        l.setEbene(depth);
        depth++;
        for (Lager sub : l.getUnterlager()) {
            updateLagerRec(sub, depth);
        }

        if (!l.getUnterlager().isEmpty()) {
            int bestand = 0;
            int kap = 0;
            for (Lager sub : l.getUnterlager()) {
                bestand += sub.getBestand();
                kap += sub.getKapazitaet();
            }
            l.setBestand(bestand);
            l.setKapazitaet(kap);
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

    public void initTestlager() {
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
