package Factory;
import MainClasses.Auto;
import MainClasses.Proprietario;

public abstract class Autofactory {
    public abstract Auto creaauto(
            Proprietario proprietario, String tipo, String carburante, int matricola, String cilindrata
    );

}
