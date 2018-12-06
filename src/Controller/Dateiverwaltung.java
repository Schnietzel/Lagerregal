package Controller;

import java.io.File;
import java.util.ArrayList;
import Model.Lager;

public class Dateiverwaltung
{
	public static ArrayList<Lager> ladeZustand(File datei)
	{
		
	}
	
	public static void speicherZustand(File datei, ArrayList<Lager> lager)
	{
		StringBuilder csv = new StringBuilder();
		
		
	}
	
	private String getLagerRecursive(StringBuilder sb, ArrayList<Lager> lager, int counter, int depth)
	{
		if (lager.isEmpty())
		{
			String ret = 
		}
	}
}
