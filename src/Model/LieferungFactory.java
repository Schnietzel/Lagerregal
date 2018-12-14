package Model;

public class LieferungFactory
{
	public Zulieferung CreateZulieferung(int menge)
	{
		return new Zulieferung(menge);
	}
	
	public Auslieferung CreateAuslieferung(int menge)
	{
		return new Auslieferung(menge);
	}
}
