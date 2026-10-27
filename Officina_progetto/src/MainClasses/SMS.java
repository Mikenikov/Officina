package MainClasses;

import java.io.Serializable;

public class SMS implements Serializable {

    private String problema;
    private String esito;

    private double costo;

    public SMS(String problema, String esito, String pezzi, double costo) {

        this.problema = problema;
        this.esito = esito;
        this.costo = costo;
    }

    public void invia(Proprietario proprietario) {

        proprietario.riceviMessaggio(this);

        System.out.println(
                "SMS inviato a "
                        + proprietario.getNome()
                        + " "
                        + proprietario.getCognome()
        );
    }


    public String getProblema() {
        return problema;
    }

    public String getEsito() {
        return esito;
    }

    public double getCosto() {
        return costo;
    }
}