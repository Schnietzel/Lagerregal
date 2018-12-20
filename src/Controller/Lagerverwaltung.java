package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Model.Lager;
import Model.Lieferung;

public class Lagerverwaltung
{
	private static ArrayList<Lager> lager;
	private static ArrayList<Lieferung> lieferungen;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		onStartup();
		
		lager = new ArrayList<Lager>();
		
		initTestlager();
		
		String datei = "C:\\Test\\test.lager";
		File file = new File(datei);
		Dateiverwaltung.speicherZustand(file, lager);
	}
	
	private static void onStartup() throws ClassNotFoundException, IOException
	{
		// TODO: Datei festlegen, Exceptions und so
		String datei = "C:\\Test\\bla.historie";
		File file = new File(datei);
		lieferungen = Dateiverwaltung.ladeHistorie(file);
	}

	private static void onShutdown() throws IOException
	{
		// TODO: Datei festlegen, Exceptions und so
		String datei = "C:\\Test\\bla.historie";
		File file = new File(datei);
		Dateiverwaltung.speicherHistorie(file, lieferungen);
	}
	
	public static void changeName(Lager lager, String neuerName)
	{
		lager.setName(neuerName);
	}
	
	public static ArrayList<Lager> getLagerList()
	{
		return lager;
	}
	
	public static int getFreieGesamtlagerkapazität()
	{
		int kap = 0;
		for (Lager l : lager)
		{
			kap += (l.getKapazitaet()-l.getBestand());
		}
		return kap;
	}
	
	public static int getGesamtbestand()
	{
		int bestand = 0;
		for (Lager l : lager)
		{
			bestand += (l.getBestand());
		}
		return bestand;
	}
	
	private static void initTestlager()
	{
		ArrayList<Lager> lNds = new ArrayList<Lager>();
		ArrayList<Lager> lDeutschland = new ArrayList<Lager>();
		ArrayList<Lager> lFrankreich = new ArrayList<Lager>();
		ArrayList<Lager> lEuropa = new ArrayList<Lager>();
		
		Lager Hannover = new Lager("Hannover", 10, 10);
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
