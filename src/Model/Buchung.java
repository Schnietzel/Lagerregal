package Model;

public class Buchung 
{
	private Lager ziellager;
	private int prozent;
	
	public Buchung(Lager ziellager, int prozent)
	{
		this.ziellager = ziellager;
		this.prozent = prozent;
	}
}
