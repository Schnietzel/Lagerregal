package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Lieferung implements Serializable
{
	private Date timestamp;
	private ArrayList<Buchung> buchungen;
	private int gesamtmenge;
	
	public Lieferung(int gesamtmenge)
	{
		this.gesamtmenge = gesamtmenge;
		this.buchungen = new ArrayList<Buchung>();
		timestamp = new Date();
	}
	
	public void addBuchung(Buchung buchung)
	{
		buchungen.add(buchung);
	}
	
	public String getTimestamp()
	{
		return timestamp.toString();
	}
	
	public int getGesamtmenge()
	{
		return gesamtmenge;
	}
	
	public ArrayList<Buchung> getBuchungen()
	{
		return buchungen;
	}
}
