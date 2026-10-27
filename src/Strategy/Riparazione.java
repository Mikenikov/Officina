package Strategy;

import MainClasses.Prenotazione;

public class Riparazione implements TipoIntervento {

    @Override
    public void esegui(Prenotazione prenotazione) {

        if (prenotazione.getMeccanico() != null) {

            System.out.println("Esecuzione riparazione.");

        } else {

            System.out.println(
                    "Riparazione non eseguita: nessun meccanico assegnato."
            );
        }
    }

    @Override
    public String getDescrizione() {
        return "Riparazione";
    }
}