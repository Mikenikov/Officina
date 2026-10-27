package Strategy;
import MainClasses.*;
public class Rettifica implements TipoIntervento {

    @Override
    public void esegui(Prenotazione prenotazione) {
        if (prenotazione.getMeccanico() != null) {

            System.out.println("Esecuzione rettifica.");

        } else {

            System.out.println(
                    "Rettifica non eseguita: nessun meccanico assegnato."
            );
        }
    }

    @Override
    public String getDescrizione() {
        return "Rettifica";
    }
}