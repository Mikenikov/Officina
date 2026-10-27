package MainClasses;

import java.io.Serializable;
import java.util.Random;

public class Ricevuta implements Serializable {

    private int id;
    private Proprietario proprietario;

    // La ricevuta viene generata tramite comando random
    public Ricevuta(Proprietario proprietario) {

        Random random = new Random();

        this.id = random.nextInt(9000) + 1000;
        this.proprietario = proprietario;
    }

    // RICEVUTA CARICATA DAL FILE
    public Ricevuta(int id, Proprietario proprietario) {

        this.id = id;
        this.proprietario = proprietario;
    }

    public int getId() {
        return id;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

}