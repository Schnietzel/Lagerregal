package Controller;

public class EingabeCheck {

    public Double dValue;
    public int iValue;

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
}
