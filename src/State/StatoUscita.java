package State;
import MainClasses.Auto;
import Factory.*;
public class StatoUscita implements StatoAuto {

    @Override
    public void entra(Auto auto) {
        System.out.println("L'auto sta entrando nell'officina.");
        auto.setstato(new StatoEntrata());
    }

    @Override
    public void esci(Auto auto) {
        System.out.println("L'auto è già fuori dall'officina.");
    }
}
