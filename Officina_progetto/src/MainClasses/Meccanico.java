package MainClasses;

import java.io.Serializable;

public class Meccanico implements Serializable {

    private String nome;
    private String cognome;
    private int matricola;
    private String specializzazione;

    public Meccanico(
            String nome,
            String cognome,
            int matricola,
            String specializzazione) {

        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.specializzazione = specializzazione;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getMatricola() {
        return matricola;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }
}