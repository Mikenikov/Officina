package Strategy;
import MainClasses.*;
public class AggiuntaComponenti implements TipoIntervento {

    @Override
    public void esegui(Prenotazione prenotazione) {
        if (prenotazione.getMeccanico() != null) {

            System.out.println("Esecuzione aggiunta componenti.");

        } else {

            System.out.println(
                    "Aggiunta componenti non eseguita: nessun meccanico assegnato."
            );
        }
    }

    @Override
    public String getDescrizione() {
        return "Aggiunta componenti";
    }
}
