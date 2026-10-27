package ChainOfResponsibility;

import MainClasses.Prenotazione;
import java.io.Serializable;

public abstract class GestoreMeccanico implements Serializable {

    protected GestoreMeccanico successivo;

    public void setSuccessivo(GestoreMeccanico successivo) {
        this.successivo = successivo;
    }

    public abstract boolean gestisciPrenotazione(
            Prenotazione prenotazione,
            String specializzazioneRichiesta
    );
}