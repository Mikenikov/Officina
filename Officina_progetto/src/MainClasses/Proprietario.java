package MainClasses;
import java.io.Serializable;

public class Proprietario implements Serializable {

    private String nome;
    private String cognome;
    private String numeroTelefono;
    private String email;

    private SMS[] smsRicevuti;
    private int numeroSMS;


    public Proprietario(String nome, String cognome,
                        String numeroTelefono, String email) {

        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = numeroTelefono;
        this.email = email;

        smsRicevuti = new SMS[50];
        numeroSMS = 0;


    }



    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nome + " " + cognome;
    }

    public void riceviMessaggio(SMS sms) {

        if (numeroSMS < smsRicevuti.length) {
            smsRicevuti[numeroSMS] = sms;
            numeroSMS++;
        }
    }


    public SMS[] getSmsRicevuti() {
        return smsRicevuti;
    }

    public int getNumeroSMS() {
        return numeroSMS;
    }
}