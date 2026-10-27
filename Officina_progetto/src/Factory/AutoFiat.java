package Factory;
import MainClasses.Auto;
import MainClasses.Proprietario;

public class AutoFiat extends Auto {
    public AutoFiat (Proprietario proprietario, String tipo, String carburante, int matricola, String cilindrata){
        super(proprietario, "fiat", tipo, carburante, matricola, cilindrata );

    }
}