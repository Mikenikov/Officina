package Persistenza;

import MainClasses.*;
import Strategy.*;

import java.io.*;


public class GestoreFile {

    private String fileAuto = "auto.dat";
    private String fileProprietari = "proprietari.dat";
    private String filePrenotazioni = "prenotazioni.dat";
    private String fileStoricoPrenotazioni = "storicoprenotazioni.dat";

    public void salvaAuto(Officina officina) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(fileAuto))) {

            out.writeObject(officina.getAuto());

            System.out.println("File auto aggiornato.");

        } catch (IOException e) {
            System.out.println("Errore nel salvataggio delle auto.");
        }
    }
    public void caricaAuto(Officina officina) {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(fileAuto))) {

            Auto[] autoSalvate = (Auto[]) in.readObject();

            for (Auto auto : autoSalvate) {

                if (auto != null) {
                    officina.aggiungiAuto(auto);
                }
            }

            System.out.println("Auto caricate dal file.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    "Errore nella lettura del file auto."
            );
        }
    }

    public void salvaProprietari(Officina officina) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(fileProprietari))) {

            out.writeObject(officina.getProprietari());

            System.out.println("Proprietari salvati nel file.");

        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dei proprietari.");
        }
    }
    public void caricaProprietari(Officina officina) {

        File file = new File(fileProprietari);

        if (!file.exists()) {
            System.out.println("Nessun file proprietari trovato.");
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(fileProprietari))) {

            Proprietario[] proprietariSalvati =
                    (Proprietario[]) in.readObject();

            for (Proprietario proprietario : proprietariSalvati) {

                if (proprietario != null) {
                    officina.aggiungiProprietario(proprietario);
                }
            }

            System.out.println("Proprietari caricati dal file.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore nella lettura del file proprietari.");
        }
    }

    public void salvaPrenotazioni(Officina officina) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(filePrenotazioni))) {

            out.writeObject(officina.getPrenotazioni());

            System.out.println("File prenotazioni aggiornato.");

        } catch (IOException e) {

            System.out.println("Errore nel salvataggio delle prenotazioni.");

            e.printStackTrace();
        }
    }
    public void caricaPrenotazioni(Officina officina) {

        File file = new File(filePrenotazioni);

        if (!file.exists()) {
            System.out.println("Nessun file prenotazioni trovato.");
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(filePrenotazioni))) {

            Prenotazione[] prenotazioniSalvate =
                    (Prenotazione[]) in.readObject();

            for (Prenotazione prenotazione : prenotazioniSalvate) {

                if (prenotazione != null) {
                    officina.aggiungiPrenotazione(prenotazione);
                }
            }

            System.out.println("Prenotazioni caricate dal file.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore nella lettura del file prenotazioni.");
        }
    }

    // =========================================================
    // SALVA STORICO PRENOTAZIONI
    // =========================================================

    public void salvaStoricoPrenotazioni(
            Officina officina) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(
                                     fileStoricoPrenotazioni))) {

            out.writeObject(
                    officina.getStoricoPrenotazioni()
            );

            System.out.println(
                    "Storico prenotazioni aggiornato."
            );

        } catch (IOException e) {

            System.out.println(
                    "Errore nel salvataggio dello storico prenotazioni."
            );
        }
    }


     // =========================================================
     // CARICA STORICO PRENOTAZIONI
     // =========================================================

    public void caricaStoricoPrenotazioni(
            Officina officina) {

        File file =
                new File(fileStoricoPrenotazioni);

        if (!file.exists()) {

            System.out.println(
                    "Nessun file storico prenotazioni trovato."
            );

            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(
                                     fileStoricoPrenotazioni))) {

            Prenotazione[] storicoSalvato =
                    (Prenotazione[]) in.readObject();

            for (Prenotazione prenotazione :
                    storicoSalvato) {

                if (prenotazione != null) {

                    officina.aggiungiStoricoPrenotazione(
                            prenotazione
                    );
                }
            }

            System.out.println(
                    "Storico prenotazioni caricato."
            );

        } catch (IOException |
                 ClassNotFoundException e) {

            System.out.println(
                    "Errore nella lettura dello storico prenotazioni."
            );
        }
    }



}