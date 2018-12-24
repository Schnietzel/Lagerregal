package Model;

import java.util.ArrayList;

public class Auslieferung extends Lieferung
{
	public Auslieferung(int gesamtmenge)
	{
		super(gesamtmenge);
	}
	
	@Override
	public String toString()
	{
		System.out.println("Auslieferung toString");
		return "Auslieferung vom " + super.getTimestamp() + " über " + super.getGesamtmenge() + " Artikel";
	}
}
