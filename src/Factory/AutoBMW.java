package Factory;
import MainClasses.Auto;
import MainClasses.Proprietario;

public class AutoBMW extends Auto {
    public AutoBMW (Proprietario proprietario,  String tipo, String carburante, int matricola, String cilindrata){
        super(proprietario, "BMW",  tipo, carburante, matricola, cilindrata );

    }
}
