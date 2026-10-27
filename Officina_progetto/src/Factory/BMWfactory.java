package Factory;
import MainClasses.Auto;
import MainClasses.Proprietario;

public class BMWfactory extends Autofactory {
    @Override
    public Auto creaauto(Proprietario proprietario,  String tipo, String carburante, int matricola, String cilindrata){
        return new AutoBMW(proprietario,  tipo, carburante, matricola, cilindrata);
    }

}
