package Strategy;

import MainClasses.Prenotazione;

import java.io.Serializable;

public interface TipoIntervento  extends Serializable {

    void esegui(Prenotazione prenotazione);

    String getDescrizione();
}