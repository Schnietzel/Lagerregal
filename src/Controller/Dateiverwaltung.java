package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import Model.Lager;

public class Dateiverwaltung
{	
	public static void speicherZustand(File datei, ArrayList<Lager> lager) throws IOException
	{
		//TODO: Speicher-Dialog
		FileOutputStream fos = new FileOutputStream(datei);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(lager);
		oos.close();
	}
	
	public ArrayList<Lager> ladeZustand(File datei) throws IOException, ClassNotFoundException
	{
		//TODO: Lade-Dialog
		FileInputStream fis = new FileInputStream(datei);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Lager> lager = (ArrayList<Lager>) ois.readObject();
		ois.close();
		return lager;
	}
}
