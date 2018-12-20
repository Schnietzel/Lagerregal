package Model;

public class Buchung 
{
	private Lager ziellager;
	private double prozent;
	
	public Buchung(Lager ziellager, double prozent)
	{
		this.ziellager = ziellager;
		this.prozent = prozent;
	}
	
	
	public Lager getZiellager()
	{
		return ziellager;
	}

	public void setZiellager(Lager ziellager)
	{
		this.ziellager = ziellager;
	}

	public double getProzent()
	{
		return prozent;
	}

	public void setProzent(double prozent)
	{
		this.prozent = prozent;
	}

}
