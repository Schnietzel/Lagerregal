package Controller;

import java.util.ArrayList;
import java.util.Stack;
import Model.Auslieferung;
import Model.Buchung;
import Model.Lager;
import Model.Lieferung;
import Model.LieferungFactory;
import Model.Zulieferung;

public class Buchungsverwaltung
{
	private static Stack<Buchung> buchungenUndo;
	private static Stack<Buchung> buchungenRedo;
	
	private static Stack<Lieferung> lieferungenUndo;
	private static Stack<Lieferung> lieferungenRedo;
	
	private static Lieferung aktuelleLieferung;
	
	public static void createZulieferung(int gesamtmenge)
	{
		if (gesamtmenge > Lagerverwaltung.getFreieGesamtlagerkapazität())
		{
			// UngueltigeZahl aufrufen
			return;
		}
		
		aktuelleLieferung = LieferungFactory.CreateZulieferung(gesamtmenge);
		// GueltigeZahl aufrufen
	}
	
	public static void createZubuchung(Lager lager, double prozent)
	{
		int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), prozent);
		if (menge > lager.getKapazitaet())
		{
			// UngueltigeZahl aufrufen
			return;
		}
		
		Buchung buchung = new Buchung(lager, prozent);
		buchungenUndo.push(buchung);
		// GueltigeZahl aufrufen
	}
	
	public static void verteileZulieferung()
	{
		for (int i = 0; i < buchungenUndo.size(); i++)
		{
			Buchung buchung = buchungenUndo.pop();
			aktuelleLieferung.addBuchung(buchung);
		}
		
		for (Buchung buchung : aktuelleLieferung.getBuchungen())
		{
			int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), buchung.getProzent());
			buchung.getZiellager().addBestand(menge);
		}
		
		lieferungenUndo.push(aktuelleLieferung);
	}
	
	public static void createAuslieferung(int gesamtmenge)
	{
		if (gesamtmenge > Lagerverwaltung.getGesamtbestand())
		{
			// UngueltigeZahl aufrufen
			return;
		}
		
		aktuelleLieferung = new Lieferung(gesamtmenge);
		// GueltigeZahl aufrufen
	}
	
	public static void createAbbuchung(Lager lager, double prozent)
	{
		int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), prozent);
		if (menge > lager.getBestand())
		{
			// UngueltigeZahl aufrufen
			return;
		}
		
		Buchung buchung = new Buchung(lager, prozent);
		buchungenUndo.push(buchung);
		// GueltigeZahl aufrufen
	}
	
	public static void verteileAuslieferung()
	{
		for (int i = 0; i < buchungenUndo.size(); i++)
		{
			Buchung buchung = buchungenUndo.pop();
			aktuelleLieferung.addBuchung(buchung);
		}
		
		for (Buchung buchung : aktuelleLieferung.getBuchungen())
		{
			int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), buchung.getProzent());
			buchung.getZiellager().removeBestand(menge);
		}
		
		lieferungenUndo.push(aktuelleLieferung);
	}
	
	public static void undoBuchung()
	{
		buchungenRedo.push(buchungenUndo.pop());
		// ausgrauen rückgängig machen
	}
	
	public static void redoBuchung()
	{
		buchungenUndo.push(buchungenRedo.pop());
		// ausgrauen wiederherstellen
	}
	
	public static void undoLieferung()
	{
		aktuelleLieferung = lieferungenUndo.pop();
		if (aktuelleLieferung.getClass().equals(Auslieferung.class))
		{
			verteileZulieferung();
		}
		else
		{
			verteileAuslieferung();
		}
		lieferungenRedo.push(aktuelleLieferung);
	}
	
	public static void redoLieferung()
	{
		aktuelleLieferung = lieferungenRedo.pop();
		if (aktuelleLieferung.getClass().equals(Auslieferung.class))
		{
			verteileZulieferung();
		}
		else
		{
			verteileAuslieferung();
		}
		lieferungenUndo.push(aktuelleLieferung);
	}
	
	private static int getEinzelmenge(int Gesamtmenge, double prozent)
	{
		return (int) ((double) Gesamtmenge / 100.0 * prozent);
	}
	
	public static double getSchrittweite(int Gesamtmenge)
	{
		return 100.0 / (double)Gesamtmenge;
	}
	
	public static Stack<Buchung> getBuchungenUndoStack() { return buchungenUndo; }
	public static Stack<Buchung> getBuchungenRedoStack() { return buchungenRedo; }
	public static Stack<Lieferung> getLieferungenUndoStack() { return lieferungenUndo; }
	public static Stack<Lieferung> getLieferungenRedoStack() { return lieferungenRedo; }
}
