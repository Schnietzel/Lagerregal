package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import Model.Lager;
import Model.Lieferung;
import View.GUI;

public class Dateiverwaltung {
	/**
     * Serialisiert und speichert den Inhalt der �bergebenen Liste in die �bergebene Datei
     * @param datei Die Datei, in der die Historie gespeichert werden soll
     * @param lieferungen Die Liste der Lager
     */
    public void speicherLager(File datei, ArrayList<Lager> lager) {
    	try
    	{
	        FileOutputStream fos = new FileOutputStream(datei);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(lager);
	        oos.close();
    	}
    	catch (IOException e)
    	{
        	System.err.println("Die Datei scheint gesperrt zu sein!");
    	}
    	catch (Exception e)
    	{
        	System.err.println("Unerwarteter Fehler festgestellt!");
    	}
    }

    /**
     * L�dt die Serialisierte Lager-Liste aus der �bergebenen Datei
     * @param datei Die Datei mit der Serialisierten Liste
     * @return Die Liste mit den Lagern
     */
    public ArrayList<Lager> ladeLager(File datei) {
    	ArrayList<Lager> lager = new ArrayList<Lager>();
    	try
    	{
	        FileInputStream fis = new FileInputStream(datei);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        lager = (ArrayList<Lager>) ois.readObject();
	        ois.close();
    	}
    	catch (ClassNotFoundException e)
        {
        	System.err.println("Der Inhalt der Datei entspricht keiner Lager-Liste!");
        }
        catch (IOException e)
        {
        	System.err.println("Die Datei scheint nicht zu existieren oder ist gesperrt!");
        }
        catch (Exception e)
        {
        	System.err.println("Unerwarteter Fehler festgestellt!");
        }
    	
        return lager;
    }

    /**
     * �ffnet einen FileSaveDialog und speichert die �bergebenen Lager in die ausgew�hlte Datei
     * @param lager Die Lager, die exportiert werden
     */
    public void exportLager(ArrayList<Lager> lager) {
    	final JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showSaveDialog(GUI.getGUI());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File datei = fc.getSelectedFile();
            speicherLager(datei, lager);
        }
    }

    /**
     * �ffnet einen FileOpenDialog und L�dt die ausgew�hlte Datei
     * @return Die geladene Lagerliste
     */
    public ArrayList<Lager> importLager() {
    	ArrayList<Lager> ret = new ArrayList<Lager>();
    	final JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showOpenDialog(GUI.getGUI());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File datei = fc.getSelectedFile();
            ret = ladeLager(datei);
        }
        
        return ret;
    }

    /**
     * Serialisiert und speichert den Inhalt der �bergebenen Liste in die �bergebene Datei
     * @param datei Die Datei, in der die Historie gespeichert werden soll
     * @param lieferungen Die Liste der Lieferungen
     */
    public void speicherHistorie(File datei, ArrayList<Lieferung> lieferungen) {
    	try
    	{
	        FileOutputStream fos = new FileOutputStream(datei);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(lieferungen);
	        oos.close();
    	}
    	catch (IOException e)
    	{
        	System.err.println("Die Datei scheint gesperrt zu sein!");
    	}
    	catch (Exception e)
    	{
        	System.err.println("Unerwarteter Fehler festgestellt!");
    	}
    }

    /**
     * L�dt die Serialisierte Lieferungen-Historie aus der �bergebenen Datei
     * @param datei Die Datei mit der Serialisierten Historie
     * @return Die Liste mit den Lieferungen
     */
    public ArrayList<Lieferung> ladeHistorie(File datei) {
        ArrayList<Lieferung> lieferungen = new ArrayList<Lieferung>();
        try
        {
	        FileInputStream fis = new FileInputStream(datei);
	        ObjectInputStream ois = new ObjectInputStream(fis);
        	lieferungen = (ArrayList<Lieferung>) ois.readObject();
            ois.close();
        }
        catch (ClassNotFoundException e)
        {
        	System.err.println("Der Inhalt der Datei entspricht keiner Lieferungs-Historie!");
        }
        catch (IOException e)
        {
        	System.err.println("Die Datei scheint nicht zu existieren oder ist gesperrt!");
        }
        catch (Exception e)
        {
        	System.err.println("Unerwarteter Fehler festgestellt!");
        }
        
        return lieferungen;
    }

    /**
     * �ffnet einen FileSaveDialog und speichert die �bergebene Lieferung in die ausgew�hlte Datei
     * @param lieferungen Die Lieferungen, die exportiert werden
     */
    public void exportHistorie(ArrayList<Lieferung> lieferungen) {
    	final JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showSaveDialog(GUI.getGUI());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File datei = fc.getSelectedFile();
            speicherHistorie(datei, lieferungen);
        }
    }

    /**
     * �ffnet einen FileOpenDialog und L�dt die ausgew�hlte Datei
     * @return Die geladene Historie
     */
    public ArrayList<Lieferung> importHistorie() {
    	ArrayList<Lieferung> ret = new ArrayList<Lieferung>();
    	final JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showOpenDialog(GUI.getGUI());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File datei = fc.getSelectedFile();
            ret = ladeHistorie(datei);
        }
        
        return ret;
    }
}
