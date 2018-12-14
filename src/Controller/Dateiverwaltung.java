package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import Model.Lager;
import Model.Lieferung;

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
	
	public static ArrayList<Lager> ladeZustand(File datei) throws IOException, ClassNotFoundException
	{
		//TODO: Lade-Dialog
		FileInputStream fis = new FileInputStream(datei);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Lager> lager = (ArrayList<Lager>) ois.readObject();
		ois.close();
		return lager;
	}
	
	public static void speicherHistorie(File datei, ArrayList<Lieferung> lieferungen) throws IOException
	{
		//TODO: Speicher-Dialog
		FileOutputStream fos = new FileOutputStream(datei);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(lieferungen);
		oos.close();
	}
	
	public static ArrayList<Lieferung> ladeHistorie(File datei) throws IOException, ClassNotFoundException
	{
		//TODO: Lade-Dialog
		FileInputStream fis = new FileInputStream(datei);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Lieferung> lieferungen = (ArrayList<Lieferung>) ois.readObject();
		ois.close();
		return lieferungen;
	}
}
