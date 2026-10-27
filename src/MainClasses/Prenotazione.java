package MainClasses;

import java.util.Scanner;
import PagamentoStrategy.*;
import Strategy.*;

public class Prenotazione implements java.io.Serializable {

    private Ricevuta ricevuta;
    private Proprietario proprietario;
    private Auto auto;

    // Strategy
    private TipoIntervento tipoIntervento;


    private String problema;

    //  Chain of Responsibility
    private Meccanico meccanico;

    // Informazioni sull'intervento terminato
    private boolean riparazione;
    private String esito;
    private String pezzi;
    private double costo;


    // ==================================================
    // COSTRUTTORE
    // ==================================================

    public Prenotazione(
            Ricevuta ricevuta,
            Proprietario proprietario,
            Auto auto,
            TipoIntervento tipoIntervento,
            String problema) {

        this.ricevuta = ricevuta;
        this.proprietario = proprietario;
        this.auto = auto;
        this.tipoIntervento = tipoIntervento;
        this.problema = problema;

        this.meccanico = null;

        this.riparazione = false;
        this.esito = null;
        this.pezzi = null;
        this.costo = 0;
    }

    // ==================================================
// COSTRUTTORE PRENOTAZIONE INIZIALE
// ==================================================

    public Prenotazione(
            Ricevuta ricevuta,
            Proprietario proprietario,
            Auto auto) {

        this.ricevuta = ricevuta;
        this.proprietario = proprietario;
        this.auto = auto;

        // Verranno inseriti successivamente dal meccanico
        this.tipoIntervento = null;
        this.problema = null;
        this.meccanico = null;

        this.riparazione = false;
        this.esito = null;
        this.pezzi = null;
        this.costo = 0;
    }


    // ==================================================
    // GETTER
    // ==================================================

    public Ricevuta getRicevuta() {
        return ricevuta;
    }


    public Proprietario getProprietario() {
        return proprietario;
    }


    public Auto getAuto() {
        return auto;
    }


    public TipoIntervento getTipoIntervento() {
        return tipoIntervento;
    }


    public String getProblema() {
        return problema;
    }


    public Meccanico getMeccanico() {
        return meccanico;
    }


    public boolean isRiparata() {
        return riparazione;
    }


    public String getEsito() {
        return esito;
    }


    public String getPezzi() {
        return pezzi;
    }


    public double getCosto() {
        return costo;
    }


    // ==================================================
    // MECCANICO
    // ==================================================

    public void setMeccanico(Meccanico meccanico) {
        this.meccanico = meccanico;
    }

    public void setTipoIntervento(TipoIntervento tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }


    // ==================================================
    // VERIFICA RICEVUTA
    // ==================================================

    public boolean verificaRicevuta(int idRicevuta) {

        return ricevuta.getId() == idRicevuta;
    }


    // ==================================================
    // COMPLETAMENTO INTERVENTO
    // ==================================================

    public void setRiparata(
            boolean riparata,
            String esito,
            String pezzi,
            double costo) {

        this.riparazione = riparata;
        this.esito = esito;
        this.pezzi = pezzi;
        this.costo = costo;


        if (riparata) {

            // l'auto passa automaticamente in uscita

            auto.esci();



            SMS sms = new SMS(
                    problema,
                    esito,
                    pezzi,
                    costo
            );

            sms.invia(proprietario);
        }
    }


}

