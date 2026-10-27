package State;
import MainClasses.Auto;
import Factory.*;
public class StatoEntrata implements StatoAuto {
    @Override

    public void entra(Auto auto) {
        System.out.println("L'auto è già in officina.");
    }

    @Override
    public void esci(Auto auto) {
        System.out.println("L'auto sta uscendo dall'officina.");
        auto.setstato(new StatoUscita());
    }
}