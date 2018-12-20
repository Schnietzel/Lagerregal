package Model;

import java.util.ArrayList;

public class LieferungFactory
{
	public static Zulieferung CreateZulieferung(int menge)
	{
		return new Zulieferung(menge);
	}
	
	public static Auslieferung CreateAuslieferung(int menge)
	{
		return new Auslieferung(menge);
	}
}
