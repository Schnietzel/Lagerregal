package lagerController;

import java.util.ArrayList;
import lagerModel.Lager;

public class Lagerverwaltung
{
	private static ArrayList<Lager> lager;
	
	public static void main(String[] args)
	{
		lager = new ArrayList<Lager>();
		
		initTestlager();
	}

	private static void initTestlager()
	{
		Lager lager = new Lager("Hannover-Misburg", 10, 10);
	}
}
