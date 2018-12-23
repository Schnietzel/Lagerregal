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
	public void speicherLager(File datei, ArrayList<Lager> lager) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(datei);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(lager);
		oos.close();
	}
	
	public ArrayList<Lager> ladeLager(File datei) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(datei);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Lager> lager = (ArrayList<Lager>) ois.readObject();
		ois.close();
		return lager;
	}
	
	public void exportLager(ArrayList<Lager> lager) throws IOException
	{
		//TODO: Speicher-Dialog --> speicherLager
	}
	
	public ArrayList<Lager> importLager()
	{
		//TODO: Lade-Dialog --> ladeLager
		return null;
	}
	
	public void speicherHistorie(File datei, ArrayList<Lieferung> lieferungen) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(datei);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(lieferungen);
		oos.close();
	}
	
	public ArrayList<Lieferung> ladeHistorie(File datei) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(datei);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Lieferung> lieferungen = (ArrayList<Lieferung>) ois.readObject();
		ois.close();
		return lieferungen;
	}
	
	public void exportHistorie(ArrayList<Lieferung> lieferungen)
	{
		// TODO: Speicher-Dialog --> speicherHistorie
	}
	
	public ArrayList<Lieferung> importHistorie()
	{
		//TODO: Lade-Dialog --> ladeHistorie
		return null;
	}
}
