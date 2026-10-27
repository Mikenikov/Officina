package MainClasses;

import java.util.Objects;

public class Officina {

    private Auto[] auto;
    private Proprietario[] proprietari;
    private Prenotazione[] prenotazioni;

    // Storico delle prenotazioni concluse e pagate
    private Prenotazione[] storicoPrenotazioni;

    private int numeroAuto;
    private int numeroProprietari;
    private int numeroPrenotazioni;
    private int numeroStoricoPrenotazioni;


    public Officina(int capacita) {

        auto = new Auto[capacita];
        proprietari = new Proprietario[capacita];
        prenotazioni = new Prenotazione[capacita];

        storicoPrenotazioni =
                new Prenotazione[capacita];

        numeroAuto = 0;
        numeroProprietari = 0;
        numeroPrenotazioni = 0;
        numeroStoricoPrenotazioni = 0;
    }


    // =========================================================
    // GETTER
    // =========================================================

    public Auto[] getAuto() {
        return auto;
    }



    public Proprietario[] getProprietari() {
        return proprietari;
    }

    public Prenotazione[] getPrenotazioni() {
        return prenotazioni;
    }

    public int getNumeroPrenotazioni() {
        return numeroPrenotazioni;
    }

    public Prenotazione[] getStoricoPrenotazioni() {
        return storicoPrenotazioni;
    }

    public int getNumeroStoricoPrenotazioni() {
        return numeroStoricoPrenotazioni;
    }


    // =========================================================
    // AUTO
    // =========================================================

    public void aggiungiAuto(Auto nuovaAuto) {

        if (cercaAuto(nuovaAuto.getMatricola()) != null) {

            System.out.println(
                    "L'auto con matricola "
                            + nuovaAuto.getMatricola()
                            + " è già presente nell'officina."
            );

            return;
        }

        if (numeroAuto < auto.length) {

            auto[numeroAuto] = nuovaAuto;
            numeroAuto++;

            System.out.println(
                    "Auto aggiunta all'officina."
            );

        } else {

            System.out.println(
                    "L'officina è piena."
            );
        }
    }


    public void rimuoviAuto(Auto autoDaRimuovere) {

        for (int i = 0; i < numeroAuto; i++) {

            if (auto[i] == autoDaRimuovere) {

                for (int j = i;
                     j < numeroAuto - 1;
                     j++) {

                    auto[j] = auto[j + 1];
                }

                auto[numeroAuto - 1] = null;

                numeroAuto--;

                System.out.println(
                        "Auto rimossa dall'officina."
                );

                return;
            }
        }

        System.out.println(
                "Auto non trovata."
        );
    }




    public Auto cercaAuto(int matricola) {

        for (int i = 0; i < numeroAuto; i++) {

            if (auto[i].getMatricola()
                    == matricola) {

                return auto[i];
            }
        }

        return null;
    }


    // =========================================================
    // PRENOTAZIONI
    // =========================================================

    public void aggiungiPrenotazione(
            Prenotazione prenotazione) {

        if (numeroPrenotazioni
                < prenotazioni.length) {

            prenotazioni[numeroPrenotazioni] =
                    prenotazione;

            numeroPrenotazioni++;

            prenotazione.getAuto().entra();

        } else {

            System.out.println(
                    "Numero massimo di prenotazioni raggiunto."
            );
        }
    }





    public void rimuoviPrenotazione(
            Prenotazione prenotazioneDaRimuovere) {

        for (int i = 0;
             i < numeroPrenotazioni;
             i++) {

            if (prenotazioni[i]
                    == prenotazioneDaRimuovere) {

                for (int j = i;
                     j < numeroPrenotazioni - 1;
                     j++) {

                    prenotazioni[j] =
                            prenotazioni[j + 1];
                }

                prenotazioni[
                        numeroPrenotazioni - 1
                        ] = null;

                numeroPrenotazioni--;

                System.out.println(
                        "Prenotazione rimossa."
                );

                return;
            }
        }

        System.out.println(
                "Prenotazione non trovata."
        );
    }


    // =========================================================
    // STORICO PRENOTAZIONI
    // =========================================================

    public void aggiungiStoricoPrenotazione(
            Prenotazione prenotazione) {

        if (numeroStoricoPrenotazioni
                < storicoPrenotazioni.length) {

            storicoPrenotazioni[
                    numeroStoricoPrenotazioni
                    ] = prenotazione;

            numeroStoricoPrenotazioni++;

            System.out.println(
                    "Prenotazione aggiunta allo storico."
            );

        } else {

            System.out.println(
                    "Storico prenotazioni pieno."
            );
        }
    }


    // =========================================================
    // PROPRIETARI
    // =========================================================

    public void aggiungiProprietario(
            Proprietario nuovoProprietario) {

        if (cercaProprietario(
                nuovoProprietario
                        .getNumeroTelefono()
        ) != null) {

            System.out.println(
                    "Il proprietario con numero di telefono "
                            + nuovoProprietario
                            .getNumeroTelefono()
                            + " è già presente."
            );

            return;
        }

        if (numeroProprietari
                < proprietari.length) {

            proprietari[numeroProprietari] =
                    nuovoProprietario;

            numeroProprietari++;

            System.out.println(
                    "Proprietario aggiunto."
            );

        } else {

            System.out.println(
                    "Non c'è spazio per altri proprietari."
            );
        }
    }


    public Proprietario cercaProprietario(
            String numeroTelefono) {

        for (int i = 0;
             i < numeroProprietari;
             i++) {

            if (Objects.equals(
                    proprietari[i]
                            .getNumeroTelefono(),
                    numeroTelefono
            )) {

                return proprietari[i];
            }
        }

        return null;
    }


    // =========================================================
    // STAMPE
    // =========================================================



    public boolean matricolaGiaPresente(
            int matricola) {

        for (int i = 0;
             i < numeroPrenotazioni;
             i++) {

            if (prenotazioni[i] != null
                    && prenotazioni[i]
                    .getAuto() != null
                    && prenotazioni[i]
                    .getAuto()
                    .getMatricola()
                    == matricola) {

                return true;
            }
        }

        return false;
    }


}