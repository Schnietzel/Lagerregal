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

	private static int restMenge;
	
	public static void initBuchungsverwaltung()
	{
		buchungenUndo = new Stack<Buchung>();
		buchungenRedo = new Stack<Buchung>();
		
		// TODO: lieferungen-Historie aus speicher laden
		lieferungenUndo = new Stack<Lieferung>();
		lieferungenRedo = new Stack<Lieferung>();
	}
	
	public static boolean createZulieferung(int gesamtmenge)
	{
		if (gesamtmenge > Lagerverwaltung.getFreieGesamtlagerkapazität())
		{
			return false;
		}
		
		aktuelleLieferung = LieferungFactory.CreateZulieferung(gesamtmenge);
		restMenge = gesamtmenge;
		return true;
	}
	
	public static boolean createZubuchung(Lager lager, double prozent)
	{
		int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), prozent);
		if (menge > lager.getKapazitaet() || menge > restMenge)
		{
			return false;
		}
		
		Buchung buchung = new Buchung(lager, prozent);
		buchungenUndo.push(buchung);
		restMenge -= menge;
		return true;
	}
	
	public static void verteileZulieferung()
	{
		int size = buchungenUndo.size();
		for (int i = 0; i < size; i++)
		{
		
			Buchung buchung = buchungenUndo.pop();
			aktuelleLieferung.addBuchung(buchung);
		}
		
		System.out.println("Buchungsliste der Aktuelle Lieferung:"+ aktuelleLieferung.getBuchungen().size()+" Elemente");
		for (Buchung buchung : aktuelleLieferung.getBuchungen())
		{
			int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), buchung.getProzent());
			buchung.getZiellager().addBestand(menge);
		}
		
		lieferungenUndo.push(aktuelleLieferung);
	}
	
	public static boolean createAuslieferung(int gesamtmenge)
	{
		if (gesamtmenge > Lagerverwaltung.getGesamtbestand())
		{
			return false;
		}
		
		aktuelleLieferung = new Lieferung(gesamtmenge);
		restMenge = gesamtmenge;
		return true;
	}
	
	public static boolean createAbbuchung(Lager lager, double prozent)
	{
		int menge = getEinzelmenge(aktuelleLieferung.getGesamtmenge(), prozent);
		if (menge > lager.getBestand() || menge > restMenge)
		{
			return false;
		}
		
		Buchung buchung = new Buchung(lager, prozent);
		buchungenUndo.push(buchung);
		restMenge -= menge;
		return true;
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
		return (int) (((double) Gesamtmenge / 100.0 * prozent) + 0.5);
	}
	
	public static double getSchrittweite(int Gesamtmenge)
	{
		return 100.0 / (double)Gesamtmenge;
	}
	
	public static Stack<Buchung> getBuchungenUndoStack() { return buchungenUndo; }
	public static Stack<Buchung> getBuchungenRedoStack() { return buchungenRedo; }
	public static Stack<Lieferung> getLieferungenUndoStack() { return lieferungenUndo; }
	public static Stack<Lieferung> getLieferungenRedoStack() { return lieferungenRedo; }

	public static boolean createLieferung(boolean Auslieferung, int gesamtmenge)
	{
		if (Auslieferung)
			return createAuslieferung(gesamtmenge);
		else
			return createZulieferung(gesamtmenge);
	}

	public static boolean createBuchung(boolean auslieferung, Lager lager, double prozent)
	{
		System.out.println(prozent);
		if (auslieferung)
			return createAbbuchung(lager, prozent);
		else
			return createZubuchung(lager, prozent);
	}

	public static int getRestMenge()
	{
		return restMenge;
	}
	
	public static boolean istBearbeitet(Lager lager)
	{
		return buchungenUndo.contains(lager);
	}

	public static boolean verteileLieferung(boolean auslieferung)
	{
		if (restMenge > 0)
			return false;
		if (auslieferung)
			verteileAuslieferung();
		else
			verteileZulieferung();
		return true;
	}
}
