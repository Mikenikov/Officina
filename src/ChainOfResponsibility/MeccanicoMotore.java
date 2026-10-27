package ChainOfResponsibility;

import MainClasses.Meccanico;
import MainClasses.Prenotazione;

public class MeccanicoMotore extends GestoreMeccanico {

    private Meccanico meccanico;

    public MeccanicoMotore(Meccanico meccanico) {
        this.meccanico = meccanico;
    }

    @Override
    public boolean gestisciPrenotazione(
            Prenotazione prenotazione,
            String specializzazioneRichiesta) {

        if (specializzazioneRichiesta.equalsIgnoreCase("Motore")) {

            prenotazione.setMeccanico(meccanico);

            System.out.println(
                    "Prenotazione assegnata al meccanico "
                            + meccanico.getNome()
                            + " "
                            + meccanico.getCognome()
            );

            return true;
        }

        if (successivo != null) {
            return successivo.gestisciPrenotazione(
                    prenotazione,
                    specializzazioneRichiesta
            );
        }

        return false;
    }
}