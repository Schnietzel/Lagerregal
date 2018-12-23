package Controller;

import Model.Buchung;
import Model.Lager;
import Model.Lieferung;

import javax.swing.*;
import java.util.ArrayList;

public class GUITools {

    public Double dValue;
    public int iValue;
    //Klasse mit tollem Zeug für die GUI - Quasi ein Werkzeugkasten

    public boolean BuchungOK(String eingabe){
        //Eingabe verarbeiten
        return true;
    }

    public int BuchungInput(String eingabe){
        //return-Werte als error-Codes zu werten. anderes Handling je nach Wert
        //REgex matcht Zahlen mit ,/. dazwischen, sofern diese zusammenhängen.
        if(eingabe.matches("^\\d*[,.]?\\d*$")) {
            String replaced = eingabe.replace(",",".");
            this.dValue = Double.parseDouble(replaced);
            this.iValue = (int) Double.parseDouble(replaced);
            if(this.iValue <= 100 && iValue >= 0) return 0;
            else return 1;
        } else return 2;

    }

    public void getLagerRecursive(ArrayList<Lager> lager, DefaultListModel<Lager> dlm)
    {
        for(Lager l : lager)
        {
            dlm.addElement(l);
            getLagerRecursive(l.getUnterlager(), dlm);
        }

    }

	public void getLieferung(ArrayList<Lieferung> historie, DefaultListModel dlm) 
	{
		for (Lieferung l : historie)
        {
        	dlm.addElement(l.toString());
        	for (Buchung b : l.getBuchungen())
        	{
        		dlm.addElement(b.toString());
        	}
        }
	}
}
