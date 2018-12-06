package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Model.Lager;

public class Lagerverwaltung
{
	private static ArrayList<Lager> lager;
	
	public static void main(String[] args) throws IOException
	{
		lager = new ArrayList<Lager>();
		
		initTestlager();
		
		String datei = "C:\\Test\\test.lager";
		File file = new File(datei);
		Dateiverwaltung.speicherZustand(file, lager);
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
