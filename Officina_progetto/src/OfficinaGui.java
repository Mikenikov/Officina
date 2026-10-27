
import javax.swing.*;
import java.awt.*;

import MainClasses.Officina;
import MainClasses.Proprietario;
import Persistenza.GestoreFile;
import MainClasses.Auto;
import MainClasses.Prenotazione;
import MainClasses.Ricevuta;
import MainClasses.Meccanico;
import Strategy.TipoIntervento;
import Strategy.Riparazione;
import Strategy.Rettifica;
import Strategy.AggiuntaComponenti;
import MainClasses.SMS;
import PagamentoStrategy.MetodoPagamento;
import PagamentoStrategy.Contanti;
import PagamentoStrategy.CartaDiCredito;
import PagamentoStrategy.Bancomat;
import Factory.*;
import ChainOfResponsibility.*;

public class OfficinaGui extends JFrame {

    private Officina officina;
    private GestoreFile gestoreFile;

    private CardLayout cardLayout;
    private JPanel pannelloPrincipale;


    private Proprietario proprietarioLoggato;


    private Meccanico meccanico1;
    private Meccanico meccanico2;
    private GestoreMeccanico gestoreMotore;
    private GestoreMeccanico gestoreElettronica;
    private Meccanico meccanicoLoggato;

    private JTextArea areaPrenotazioni;
    private JPanel pannelloPrenotazioniMeccanico;
    private JPanel pannelloRitiroPagamento;
    private JTextArea areaSMS;
    private JLabel labelCostoPagamento;
    private JTextArea areaStoricoPrenotazioni;

    private Prenotazione prenotazioneSelezionata;


    public OfficinaGui() {

        // =========================
        // OGGETTI PRINCIPALI
        // =========================

        officina = new Officina(50);
        gestoreFile = new GestoreFile();

        meccanico1 = new Meccanico(
                "Mario",
                "Rossi",
                2001,
                "Motore"
        );

        meccanico2 = new Meccanico(
                "Luigi",
                "Bianchi",
                2002,
                "Elettronica"
        );

        gestoreMotore = new MeccanicoMotore(meccanico1);
        gestoreElettronica = new MeccanicoElettronica(meccanico2);

        gestoreMotore.setSuccessivo(gestoreElettronica);

        // Carico i proprietari salvati
        gestoreFile.caricaProprietari(officina);
        gestoreFile.caricaAuto(officina);
        gestoreFile.caricaPrenotazioni(officina);
        gestoreFile.caricaStoricoPrenotazioni(officina);


        // =========================
        // FINESTRA
        // =========================

        setTitle("Officina progetto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);


        // =========================
        // CARD LAYOUT
        // =========================

        cardLayout = new CardLayout();

        pannelloPrincipale =
                new JPanel(cardLayout);


        // Creazione pagine per la Gui

        JPanel home =
                creaHome();

        JPanel registrazione =
                creaRegistrazione();

        JPanel login =
                creaLogin();

        JPanel areaProprietario =
                creaAreaProprietario();

        JPanel richiestaPrenotazione = creaRichiestaPrenotazione();

        JPanel miePrenotazioni =
                creaMiePrenotazioni();

        JPanel paginaSMS =
                creaPaginaSMS();



        JPanel loginMeccanico =
                creaLoginMeccanico();

        JPanel areaMeccanico =
                creaAreaMeccanico();

        JPanel completaIntervento =
                creaCompletaIntervento();

        JPanel prenotazioniMeccanico =
                creaPrenotazioniMeccanico();

        JPanel ritiroPagamento =
                creaRitiroPagamento();

        JPanel confermaPagamento =
                creaConfermaPagamento();

        JPanel storicoPrenotazioni =
                creaStoricoPrenotazioni();




        // aggiunta pagine

        pannelloPrincipale.add(
                home,
                "HOME"
        );

        pannelloPrincipale.add(
                registrazione,
                "REGISTRAZIONE"
        );

        pannelloPrincipale.add(
                login,
                "LOGIN"
        );

        pannelloPrincipale.add(
                areaProprietario,
                "AREA_PROPRIETARIO"
        );
        pannelloPrincipale.add(richiestaPrenotazione, "RICHIESTA_PRENOTAZIONE");


        pannelloPrincipale.add(
                miePrenotazioni,
                "MIE_PRENOTAZIONI"
        );

        pannelloPrincipale.add(
                paginaSMS,
                "SMS"
        );



        pannelloPrincipale.add(
                loginMeccanico,
                "LOGIN_MECCANICO"
        );

        pannelloPrincipale.add(
                areaMeccanico,
                "AREA_MECCANICO"
        );

        pannelloPrincipale.add(
                prenotazioniMeccanico,
                "PRENOTAZIONI_MECCANICO"
        );

        pannelloPrincipale.add(
                ritiroPagamento,
                "RITIRO_PAGAMENTO"
        );

        pannelloPrincipale.add(
                storicoPrenotazioni,
                "STORICO_PRENOTAZIONI"
        );

        pannelloPrincipale.add(
                confermaPagamento,
                "CONFERMA_PAGAMENTO"
        );

        pannelloPrincipale.add(
                completaIntervento,
                "COMPLETA_INTERVENTO"
        );


        add(pannelloPrincipale);


        // Qui abbiamo la schermata Home
        cardLayout.show(
                pannelloPrincipale,
                "HOME"
        );


        setVisible(true);
    }

    private void aggiornaMiePrenotazioni() {

        areaPrenotazioni.setText("");

        Prenotazione[] prenotazioni =
                officina.getPrenotazioni();

        int numero = 1;

        for (int i = 0;
             i < officina.getNumeroPrenotazioni();
             i++) {

            Prenotazione p =
                    prenotazioni[i];

            if (p != null &&
                    p.getProprietario()
                            .getNumeroTelefono()
                            .equals(
                                    proprietarioLoggato
                                            .getNumeroTelefono()
                            )) {

                Auto auto = p.getAuto();

                // =========================
                // DATI PRENOTAZIONE
                // =========================

                areaPrenotazioni.append(
                        "PRENOTAZIONE " + numero + "\n"
                );

                areaPrenotazioni.append(
                        "------------------------------\n"
                );

                areaPrenotazioni.append(
                        "Ricevuta: "
                                + p.getRicevuta().getId()
                                + "\n\n"
                );

                areaPrenotazioni.append(
                        "Marca: "
                                + auto.getMarca()
                                + "\n"
                );

                areaPrenotazioni.append(
                        "Tipo: "
                                + auto.getTipo()
                                + "\n"
                );

                areaPrenotazioni.append(
                        "Carburante: "
                                + auto.getCarburante()
                                + "\n"
                );

                areaPrenotazioni.append(
                        "Matricola: "
                                + auto.getMatricola()
                                + "\n"
                );

                areaPrenotazioni.append(
                        "Cilindrata: "
                                + auto.getCilindrata()
                                + "\n"
                );

                areaPrenotazioni.append(
                        "Tipo intervento: "
                                + p.getTipoIntervento().getDescrizione()
                                + "\n"
                );


                // =========================
                // STATO PRENOTAZIONE
                // =========================

                if (p.isRiparata()) {

                    areaPrenotazioni.append(
                            "\nStato: Auto pronta per il ritiro\n"
                    );

                } else {

                    areaPrenotazioni.append(
                            "\nStato: In attesa del meccanico\n"
                    );
                }

                areaPrenotazioni.append(
                        "------------------------------\n\n"
                );

                numero++;
            }
        }


        // =========================
        // NESSUNA PRENOTAZIONE
        // =========================

        if (numero == 1) {

            areaPrenotazioni.setText(
                    "Non hai ancora prenotazioni."
            );
        }

        areaPrenotazioni.setCaretPosition(0);
    }

    // =========================================================
    // HOME
    // =========================================================

    private JPanel creaHome() {

        JPanel pannello = new JPanel();

        pannello.setLayout(
                new GridLayout(5, 1, 10, 10)
        );


        JButton btnLogin =
                new JButton("Login Proprietario");

        JButton btnProprietario =
                new JButton("Registrazione Proprietario");

        JButton btnMeccanico =
                new JButton("Login Meccanico");


        // =========================================================
        // Login ,registrazione e login meccanico
        // =========================================================

        btnLogin.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "LOGIN"
            );

        });




        btnProprietario.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "REGISTRAZIONE"
            );

        });

        btnMeccanico.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "LOGIN_MECCANICO"
            );

        });


        pannello.add(btnLogin);

        pannello.add(btnProprietario);

        pannello.add(btnMeccanico);




        return pannello;
    }


    // =========================================================
    // METODO REGISTRAZIONE PROPRIETARIO
    // =========================================================

    private JPanel creaRegistrazione() {

        JPanel pannello =
                new JPanel(new BorderLayout());




        JLabel titolo =
                new JLabel(
                        "REGISTRAZIONE PROPRIETARIO"
                );

        titolo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        0,
                        30,
                        0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MODULO
        // =========================

        JPanel modulo =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        5,
                        10,
                        5,
                        10
                );


        // =========================
        // CAMPI DA COMPILARE(se lasciati vuoi="CAMPO OBBLIGATORIO")
        // =========================

        JTextField campoNome =
                new JTextField();

        JTextField campoCognome =
                new JTextField();

        JTextField campoTelefono =
                new JTextField();

        JTextField campoEmail =
                new JTextField();


        Dimension dimensioneCampo =
                new Dimension(
                        300,
                        35
                );


        campoNome.setPreferredSize(
                dimensioneCampo
        );

        campoCognome.setPreferredSize(
                dimensioneCampo
        );

        campoTelefono.setPreferredSize(
                dimensioneCampo
        );

        campoEmail.setPreferredSize(
                dimensioneCampo
        );




        JLabel erroreNome =
                new JLabel("");

        JLabel erroreCognome =
                new JLabel("");

        JLabel erroreTelefono =
                new JLabel("");

        JLabel erroreEmail =
                new JLabel("");


        erroreNome.setForeground(
                Color.RED
        );

        erroreCognome.setForeground(
                Color.RED
        );

        erroreTelefono.setForeground(
                Color.RED
        );

        erroreEmail.setForeground(
                Color.RED
        );




        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor =
                GridBagConstraints.WEST;

        modulo.add(
                new JLabel("Nome:"),
                gbc
        );


        gbc.gridx = 1;

        modulo.add(
                campoNome,
                gbc
        );




        gbc.gridx = 1;
        gbc.gridy = 1;

        modulo.add(
                erroreNome,
                gbc
        );



        gbc.gridx = 0;
        gbc.gridy = 2;

        modulo.add(
                new JLabel("Cognome:"),
                gbc
        );


        gbc.gridx = 1;

        modulo.add(
                campoCognome,
                gbc
        );




        gbc.gridx = 1;
        gbc.gridy = 3;

        modulo.add(
                erroreCognome,
                gbc
        );




        gbc.gridx = 0;
        gbc.gridy = 4;

        modulo.add(
                new JLabel("Telefono:"),
                gbc
        );


        gbc.gridx = 1;

        modulo.add(
                campoTelefono,
                gbc
        );




        gbc.gridx = 1;
        gbc.gridy = 5;

        modulo.add(
                erroreTelefono,
                gbc
        );




        gbc.gridx = 0;
        gbc.gridy = 6;

        modulo.add(
                new JLabel("Email:"),
                gbc
        );


        gbc.gridx = 1;

        modulo.add(
                campoEmail,
                gbc
        );




        gbc.gridx = 1;
        gbc.gridy = 7;

        modulo.add(
                erroreEmail,
                gbc
        );




        JButton btnIndietro =
                new JButton("Indietro");

        JButton btnRegistra =
                new JButton("Registra");


        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor =
                GridBagConstraints.CENTER;

        modulo.add(
                btnIndietro,
                gbc
        );


        gbc.gridx = 1;

        modulo.add(
                btnRegistra,
                gbc
        );


        pannello.add(
                modulo,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        btnIndietro.addActionListener(e -> {

            // Cancello eventuali errori
            erroreNome.setText("");
            erroreCognome.setText("");
            erroreTelefono.setText("");
            erroreEmail.setText("");

            cardLayout.show(
                    pannelloPrincipale,
                    "HOME"
            );

        });




        btnRegistra.addActionListener(e -> {

            // Cancello errori precedenti

            erroreNome.setText("");
            erroreCognome.setText("");
            erroreTelefono.setText("");
            erroreEmail.setText("");


            boolean errore = false;


            // Qui ci sono gli if per controllare nomi,cognomi e gli altri dati del proprietario

            if (
                    campoNome
                            .getText()
                            .trim()
                            .isEmpty()
            ) {

                erroreNome.setText(
                        "Campo obbligatorio"
                );

                errore = true;
            }




            if (
                    campoCognome
                            .getText()
                            .trim()
                            .isEmpty()
            ) {

                erroreCognome.setText(
                        "Campo obbligatorio"
                );

                errore = true;
            }




            if (
                    campoTelefono
                            .getText()
                            .trim()
                            .isEmpty()
            ) {

                erroreTelefono.setText(
                        "Campo obbligatorio"
                );

                errore = true;
            }




            if (
                    campoEmail
                            .getText()
                            .trim()
                            .isEmpty()
            ) {

                erroreEmail.setText(
                        "Campo obbligatorio"
                );

                errore = true;
            }


            // Se c'è almeno un errore non continuo


            if (errore) {
                return;
            }


            // =========================
            // CREAZIONE PROPRIETARIO
            // =========================

            String nome =
                    campoNome.getText().trim();

            String cognome =
                    campoCognome.getText().trim();

            String telefono =
                    campoTelefono.getText().trim();

            String email =
                    campoEmail.getText().trim();


            Proprietario proprietario =
                    new Proprietario(
                            nome,
                            cognome,
                            telefono,
                            email
                    );


            officina.aggiungiProprietario(
                    proprietario
            );


            gestoreFile.salvaProprietari(
                    officina
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Proprietario registrato."
            );


            // Qui svuotiamo i campo

            campoNome.setText("");
            campoCognome.setText("");
            campoTelefono.setText("");
            campoEmail.setText("");




            cardLayout.show(
                    pannelloPrincipale,
                    "HOME"
            );

        });


        return pannello;
    }


    // =========================================================
    // LOGIN PROPRIETARIO
    // =========================================================

    private JPanel creaLogin() {

        JPanel pannello =
                new JPanel(
                        new BorderLayout()
                );


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel(
                        "LOGIN PROPRIETARIO"
                );

        titolo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        0,
                        30,
                        0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MODULO
        // =========================

        JPanel modulo =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );


        // =========================
        // TELEFONO
        // =========================

        JTextField campoTelefono =
                new JTextField();

        campoTelefono.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );


        JLabel erroreTelefono =
                new JLabel("");

        erroreTelefono.setForeground(
                Color.RED
        );


        JButton btnIndietro =
                new JButton("Indietro");

        JButton btnLogin =
                new JButton("Login");




        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor =
                GridBagConstraints.WEST;

        modulo.add(
                new JLabel(
                        "Numero di telefono:"
                ),
                gbc
        );




        gbc.gridx = 1;

        modulo.add(
                campoTelefono,
                gbc
        );




        gbc.gridx = 1;
        gbc.gridy = 1;

        modulo.add(
                erroreTelefono,
                gbc
        );




        gbc.gridx = 0;
        gbc.gridy = 2;

        modulo.add(
                btnIndietro,
                gbc
        );




        gbc.gridx = 1;

        modulo.add(
                btnLogin,
                gbc
        );


        pannello.add(
                modulo,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        btnIndietro.addActionListener(e -> {

            campoTelefono.setText("");
            erroreTelefono.setText("");

            cardLayout.show(
                    pannelloPrincipale,
                    "HOME"
            );

        });


        // =========================
        // LOGIN
        // =========================

        btnLogin.addActionListener(e -> {

            erroreTelefono.setText("");


            String telefono =
                    campoTelefono
                            .getText()
                            .trim();




            if (telefono.isEmpty()) {

                erroreTelefono.setText(
                        "Campo obbligatorio"
                );

                return;
            }




            Proprietario proprietario =
                    officina.cercaProprietario(
                            telefono
                    );




            if (proprietario == null) {

                erroreTelefono.setText(
                        "Proprietario non trovato"
                );

                return;
            }


            // =========================
            // LOGIN RIUSCITO
            // =========================

            proprietarioLoggato =
                    proprietario;


            JOptionPane.showMessageDialog(
                    this,
                    "Benvenuto "
                            + proprietario.getNome()
                            + " "
                            + proprietario.getCognome()
            );


            campoTelefono.setText("");




            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_PROPRIETARIO"
            );

        });


        return pannello;
    }


    // =========================================================
    // AREA PROPRIETARIO
    // =========================================================

    private JPanel creaAreaProprietario() {

        JPanel pannello =
                new JPanel(
                        new BorderLayout()
                );


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel(
                        "AREA PROPRIETARIO"
                );

        titolo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        0,
                        30,
                        0
                )
        );


        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MENU
        // =========================

        JPanel menu =
                new JPanel();

        menu.setLayout(
                new GridLayout(
                        5,
                        1,
                        10,
                        10
                )
        );



        JButton btnPrenotazione =
                new JButton(
                        "Richiedi prenotazione"
                );

        JButton btnMiePrenotazioni =
                new JButton(
                        "Visualizza le mie prenotazioni"
                );

        JButton btnSMS =
                new JButton(
                        "Visualizza SMS"
                );



        JButton btnLogout =
                new JButton(
                        "Logout"
                );





        menu.add(btnPrenotazione);

        menu.add(btnMiePrenotazioni);

        menu.add(btnSMS);



        menu.add(btnLogout);



        pannello.add(
                menu,
                BorderLayout.CENTER
        );


        // =========================
        // VISUALIZZA AUTO
        // =========================




        // =========================
        // RICHIEDI PRENOTAZIONE
        // =========================

        btnPrenotazione.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "RICHIESTA_PRENOTAZIONE"
            );

        });


        // =========================
        // VISUALIZZA PRENOTAZIONI
        // =========================

        btnMiePrenotazioni.addActionListener(e -> {

            aggiornaMiePrenotazioni();

            cardLayout.show(
                    pannelloPrincipale,
                    "MIE_PRENOTAZIONI"
            );

        });

        // =========================
        // VISUALIZZA SMS
        // =========================

        btnSMS.addActionListener(e -> {

            aggiornaSMS();

            cardLayout.show(
                    pannelloPrincipale,
                    "SMS"
            );

        });




        // =========================
        // LOGOUT
        // =========================

        btnLogout.addActionListener(e -> {

            proprietarioLoggato = null;

            JOptionPane.showMessageDialog(
                    this,
                    "Logout effettuato."
            );

            cardLayout.show(
                    pannelloPrincipale,
                    "HOME"
            );

        });


        return pannello;
    }

    private JPanel creaMiePrenotazioni() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("LE MIE PRENOTAZIONI");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // AREA PRENOTAZIONI
        // =========================

        areaPrenotazioni =
                new JTextArea();

        areaPrenotazioni.setEditable(false);

        areaPrenotazioni.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );


        JScrollPane scrollPane =
                new JScrollPane(areaPrenotazioni);

        pannello.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // PULSANTE INDIETRO
        // =========================

        JButton btnIndietro =
                new JButton("Indietro");

        pannello.add(
                btnIndietro,
                BorderLayout.SOUTH
        );


        btnIndietro.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_PROPRIETARIO"
            );

        });


        return pannello;
    }

    private JPanel creaPaginaSMS() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("I MIEI SMS");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // AREA SMS
        // =========================

        areaSMS =
                new JTextArea();

        areaSMS.setEditable(false);

        areaSMS.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JScrollPane scrollPane =
                new JScrollPane(areaSMS);

        pannello.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        JButton btnIndietro =
                new JButton("Indietro");

        btnIndietro.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_PROPRIETARIO"
            );

        });

        pannello.add(
                btnIndietro,
                BorderLayout.SOUTH
        );


        return pannello;
    }


    private void aggiornaSMS() {

        areaSMS.setText("");

        SMS[] smsRicevuti =
                proprietarioLoggato.getSmsRicevuti();

        int numeroSMS =
                proprietarioLoggato.getNumeroSMS();


        if (numeroSMS == 0) {

            areaSMS.setText(
                    "Nessun SMS ricevuto."
            );

            return;
        }


        for (int i = 0; i < numeroSMS; i++) {

            SMS sms =
                    smsRicevuti[i];


            areaSMS.append(
                    "SMS " + (i + 1) + "\n"
            );

            areaSMS.append(
                    "------------------------------\n"
            );

            areaSMS.append(
                    "Riparazione avvenuta.\n\n"
            );

            areaSMS.append(
                    "Problema riscontrato: "
                            + sms.getProblema()
                            + "\n"
            );

            areaSMS.append(
                    "Esito della riparazione: "
                            + sms.getEsito()
                            + "\n"
            );

            areaSMS.append(
                    "Costo: "
                            + String.format("%.2f", sms.getCosto())
                            + " €\n"
            );

            areaSMS.append(
                    "------------------------------\n\n"
            );
        }


        areaSMS.setCaretPosition(0);
    }

    private JPanel creaRichiestaPrenotazione() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("RICHIEDI PRENOTAZIONE");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MODULO
        // =========================

        JPanel modulo =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 10, 8, 10);

        gbc.anchor =
                GridBagConstraints.WEST;


        // =========================
        // CAMPI
        // =========================

        JTextField campoMarca =
                new JTextField();

        JTextField campoProprietario =
                new JTextField();

        JTextField campoTipo =
                new JTextField();

        JTextField campoCarburante =
                new JTextField();

        JTextField campoMatricola =
                new JTextField();

        JTextField campoCilindrata =
                new JTextField();


        Dimension dimensioneCampo =
                new Dimension(300, 35);

        String[] specializzazioni = {
                "Seleziona...",
                "Motore",
                "Elettronica"
        };

        JComboBox<String> menuSpecializzazione =
                new JComboBox<>(specializzazioni);

        menuSpecializzazione.setPreferredSize(
                new Dimension(300, 35)
        );

        campoMarca.setPreferredSize(dimensioneCampo);
        campoProprietario.setPreferredSize(dimensioneCampo);
        campoTipo.setPreferredSize(dimensioneCampo);
        campoCarburante.setPreferredSize(dimensioneCampo);
        campoMatricola.setPreferredSize(dimensioneCampo);
        campoCilindrata.setPreferredSize(dimensioneCampo);


        // =========================
        // MENU TIPO INTERVENTO
        // =========================

        String[] tipiIntervento = {
                "Seleziona...",
                "Riparazione",
                "Rettifica",
                "Aggiunta componenti"
        };

        JComboBox<String> menuTipoIntervento =
                new JComboBox<>(tipiIntervento);

        menuTipoIntervento.setPreferredSize(
                new Dimension(300, 35)
        );



        campoProprietario.setEditable(false);


        // =========================
        // MARCA
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 0;

        modulo.add(
                new JLabel("Marca:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoMarca,
                gbc
        );


        // =========================
        // PROPRIETARIO
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 1;

        modulo.add(
                new JLabel("Proprietario:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoProprietario,
                gbc
        );


        // =========================
        // TIPO AUTO
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 2;

        modulo.add(
                new JLabel("Tipo:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoTipo,
                gbc
        );


        // =========================
        // CARBURANTE
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 3;

        modulo.add(
                new JLabel("Carburante:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoCarburante,
                gbc
        );


        // =========================
        // MATRICOLA
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 4;

        modulo.add(
                new JLabel("Matricola:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoMatricola,
                gbc
        );


        // =========================
        // CILINDRATA
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 5;

        modulo.add(
                new JLabel("Cilindrata:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoCilindrata,
                gbc
        );

        // =========================
        // SPECIALIZZAZIONE RICHIESTA
        // =========================

        gbc.gridx = 0;
        gbc.gridy++;

        modulo.add(
                new JLabel("Tipo problema:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                menuSpecializzazione,
                gbc
        );


        // =========================
        // TIPO INTERVENTO
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;

        modulo.add(
                new JLabel("Tipo intervento:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                menuTipoIntervento,
                gbc
        );


        // =========================
        // PULSANTI
        // =========================

        JButton btnIndietro =
                new JButton("Indietro");

        JButton btnRichiedi =
                new JButton("Richiedi prenotazione");



        gbc.gridy = 8;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        modulo.add(
                btnIndietro,
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                btnRichiedi,
                gbc
        );


        pannello.add(
                modulo,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        btnIndietro.addActionListener(e -> {

            campoMarca.setText("");
            campoProprietario.setText("");
            campoTipo.setText("");
            campoCarburante.setText("");
            campoMatricola.setText("");
            campoCilindrata.setText("");

            menuTipoIntervento.setSelectedIndex(0);

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_PROPRIETARIO"
            );
        });


        // =========================
        // RICHIEDI PRENOTAZIONE
        // =========================

        btnRichiedi.addActionListener(e -> {

            if (proprietarioLoggato == null) {
                return;
            }


            // =========================
            // LEGGO I CAMPI
            // =========================

            String marca =
                    campoMarca.getText().trim();

            String tipo =
                    campoTipo.getText().trim();

            String carburante =
                    campoCarburante.getText().trim();

            String matricola =
                    campoMatricola.getText().trim();

            String cilindrata =
                    campoCilindrata.getText().trim();

            String interventoSelezionato =
                    (String) menuTipoIntervento.getSelectedItem();

            String specializzazioneRichiesta =
                    (String) menuSpecializzazione.getSelectedItem();


            // =========================
            // CONTROLLO CAMPI VUOTI
            // =========================

            if (marca.isEmpty()
                    || tipo.isEmpty()
                    || carburante.isEmpty()
                    || matricola.isEmpty()
                    || cilindrata.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Compila tutti i campi."
                );

                return;
            }


            // =========================
            // CONTROLLO TIPO INTERVENTO
            // =========================

            if (menuTipoIntervento.getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleziona il tipo di intervento."
                );

                return;
            }

            if (menuSpecializzazione.getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleziona il tipo di problema."
                );

                return;
            }


            // =========================
            // CONTROLLO MATRICOLA
            // =========================

            int matricolaNumero;

            try {

                matricolaNumero =
                        Integer.parseInt(matricola);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "La matricola deve essere un numero."
                );

                return;
            }


            // =========================
            // CONTROLLO PRENOTAZIONE
            // =========================

            if (officina.matricolaGiaPresente(
                    matricolaNumero
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Esiste già una prenotazione con questa matricola."
                );

                return;
            }


            // =========================
            // CONTROLLO AUTO
            // =========================

            if (officina.cercaAuto(
                    matricolaNumero
            ) != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Esiste già un'auto con questa matricola."
                );

                return;
            }


            // =========================
            // CREO L'AUTO
            // =========================

            Autofactory factory;

            if (marca.equalsIgnoreCase("Fiat")) {

                factory = new Fiatfactory();

            } else if (marca.equalsIgnoreCase("BMW")) {

                factory = new BMWfactory();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Marca non disponibile."
                );

                return;
            }

            Auto nuovaAuto =
                    factory.creaauto(
                            proprietarioLoggato,
                            tipo,
                            carburante,
                            matricolaNumero,
                            cilindrata
                    );


            // =========================
            // AGGIUNGO AUTO
            // =========================

            officina.aggiungiAuto(
                    nuovaAuto
            );


            // =========================
            // CREO RICEVUTA
            // =========================

            Ricevuta ricevuta =
                    new Ricevuta(
                            proprietarioLoggato
                    );


            // =========================
            // CREO PRENOTAZIONE
            // =========================

            Prenotazione nuovaPrenotazione =
                    new Prenotazione(
                            ricevuta,
                            proprietarioLoggato,
                            nuovaAuto
                    );

            TipoIntervento tipoIntervento;

            switch (interventoSelezionato) {

                case "Riparazione":
                    tipoIntervento = new Riparazione();
                    break;

                case "Rettifica":
                    tipoIntervento = new Rettifica();
                    break;

                case "Aggiunta componenti":
                    tipoIntervento = new AggiuntaComponenti();
                    break;

                default:
                    return;
            }

            nuovaPrenotazione.setTipoIntervento(tipoIntervento);

            // =========================
            // ASSEGNAZIONE MECCANICO
            // CHAIN OF RESPONSIBILITY
            // =========================

            gestoreMotore.gestisciPrenotazione(
                    nuovaPrenotazione,
                    specializzazioneRichiesta
            );

            // =========================
            // AGGIUNGO PRENOTAZIONE
            // =========================

            officina.aggiungiPrenotazione(
                    nuovaPrenotazione
            );


            // =========================
            // SALVATAGGIO
            // =========================

            gestoreFile.salvaAuto(
                    officina
            );

            gestoreFile.salvaPrenotazioni(
                    officina
            );



            // =========================
            // CONFERMA
            // =========================

            JOptionPane.showMessageDialog(
                    this,
                    "Prenotazione richiesta correttamente.\n"
                            + "Tipo intervento: "
                            + interventoSelezionato
            );


            // =========================
            // SVUOTO I CAMPI
            // =========================

            campoMarca.setText("");
            campoProprietario.setText("");
            campoTipo.setText("");
            campoCarburante.setText("");
            campoMatricola.setText("");
            campoCilindrata.setText("");

            menuTipoIntervento.setSelectedIndex(0);


            // =========================
            // TORNO AREA PROPRIETARIO
            // =========================

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_PROPRIETARIO"
            );
        });


        return pannello;
    }


    // =========================================================
    // LOGIN MECCANICO
    // =========================================================

    // =========================================================
    // LOGIN MECCANICO
    // =========================================================

    private JPanel creaLoginMeccanico() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("LOGIN MECCANICO");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MODULO
        // =========================

        JPanel modulo =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);


        // =========================
        // MATRICOLA
        // =========================

        JTextField campoMatricola =
                new JTextField();

        campoMatricola.setPreferredSize(
                new Dimension(300, 35)
        );

        JLabel erroreMatricola =
                new JLabel("");

        erroreMatricola.setForeground(
                Color.RED
        );


        JButton btnIndietro =
                new JButton("Indietro");

        JButton btnLogin =
                new JButton("Login");




        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        modulo.add(
                new JLabel("Matricola:"),
                gbc
        );




        gbc.gridx = 1;

        modulo.add(
                campoMatricola,
                gbc
        );




        gbc.gridx = 1;
        gbc.gridy = 1;

        modulo.add(
                erroreMatricola,
                gbc
        );




        gbc.gridx = 0;
        gbc.gridy = 2;

        modulo.add(
                btnIndietro,
                gbc
        );




        gbc.gridx = 1;

        modulo.add(
                btnLogin,
                gbc
        );


        pannello.add(
                modulo,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        btnIndietro.addActionListener(e -> {

            campoMatricola.setText("");
            erroreMatricola.setText("");

            cardLayout.show(
                    pannelloPrincipale,
                    "HOME"
            );
        });


        // =========================
        // LOGIN
        // =========================

        btnLogin.addActionListener(e -> {

            erroreMatricola.setText("");

            String testoMatricola =
                    campoMatricola.getText().trim();




            if (testoMatricola.isEmpty()) {

                erroreMatricola.setText(
                        "Campo obbligatorio"
                );

                return;
            }


            int matricola;

            try {

                matricola =
                        Integer.parseInt(testoMatricola);

            } catch (NumberFormatException ex) {

                erroreMatricola.setText(
                        "La matricola deve essere un numero"
                );

                return;
            }


            // =========================
            // CERCO IL MECCANICO
            // =========================

            if (matricola == meccanico1.getMatricola()) {

                meccanicoLoggato = meccanico1;

            } else if (matricola == meccanico2.getMatricola()) {

                meccanicoLoggato = meccanico2;

            } else {

                erroreMatricola.setText(
                        "Meccanico non trovato"
                );

                return;
            }


            // =========================
            // LOGIN RIUSCITO
            // =========================

            JOptionPane.showMessageDialog(
                    this,
                    "Benvenuto "
                            + meccanicoLoggato.getNome()
                            + " "
                            + meccanicoLoggato.getCognome()
            );

            campoMatricola.setText("");

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_MECCANICO"
            );
        });


        return pannello;
    }

    // =========================================================
// AREA MECCANICO
// =========================================================

    private JPanel creaAreaMeccanico() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("AREA MECCANICO");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MENU
        // =========================

        JPanel menu =
                new JPanel(
                        new GridLayout(4, 1, 10, 10)
                );

        JButton btnPrenotazioni =
                new JButton("Visualizza prenotazioni");

        JButton btnRitiro =
                new JButton("Ritiro e pagamento");

        JButton btnStorico =
                new JButton("Storico prenotazioni");

        JButton btnLogout =
                new JButton("Logout");


        menu.add(btnPrenotazioni);
        menu.add(btnRitiro);
        menu.add(btnStorico);
        menu.add(btnLogout);

        pannello.add(
                menu,
                BorderLayout.CENTER
        );


        // =========================
        // VISUALIZZA PRENOTAZIONI
        // =========================

        btnPrenotazioni.addActionListener(e -> {

            aggiornaPrenotazioniMeccanico();

            cardLayout.show(
                    pannelloPrincipale,
                    "PRENOTAZIONI_MECCANICO"
            );
        });

        // =========================
        // RITIRO E PAGAMENTO
        // =========================

        btnRitiro.addActionListener(e -> {

            aggiornaRitiroPagamento();

            cardLayout.show(
                    pannelloPrincipale,
                    "RITIRO_PAGAMENTO"
            );

        });

        // =========================
        // STORICO PRENOTAZIONI
        // =========================

        btnStorico.addActionListener(e -> {

            aggiornaStoricoPrenotazioni();

            cardLayout.show(
                    pannelloPrincipale,
                    "STORICO_PRENOTAZIONI"
            );
        });


        // =========================
        // LOGOUT
        // =========================

        btnLogout.addActionListener(e -> {

            meccanicoLoggato = null;

            JOptionPane.showMessageDialog(
                    this,
                    "Logout effettuato."
            );

            cardLayout.show(
                    pannelloPrincipale,
                    "HOME"
            );
        });


        return pannello;
    }

    private JPanel creaStoricoPrenotazioni() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("STORICO PRENOTAZIONI");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // AREA STORICO
        // =========================

        areaStoricoPrenotazioni =
                new JTextArea();

        areaStoricoPrenotazioni.setEditable(false);

        areaStoricoPrenotazioni.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JScrollPane scrollPane =
                new JScrollPane(areaStoricoPrenotazioni);

        pannello.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        JButton btnIndietro =
                new JButton("Indietro");

        btnIndietro.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_MECCANICO"
            );
        });

        pannello.add(
                btnIndietro,
                BorderLayout.SOUTH
        );


        return pannello;
    }

    private void aggiornaStoricoPrenotazioni() {

        areaStoricoPrenotazioni.setText("");

        Prenotazione[] storico =
                officina.getStoricoPrenotazioni();

        int numeroStorico =
                officina.getNumeroStoricoPrenotazioni();

        if (numeroStorico == 0) {
            areaStoricoPrenotazioni.setText(
                    "Nessuna prenotazione presente nello storico."
            );
            return;
        }

        int numero = 1;

        for (int i = 0; i < numeroStorico; i++) {

            Prenotazione p = storico[i];

            if (p == null) {
                continue;
            }

            Auto auto = p.getAuto();
            Proprietario proprietario = p.getProprietario();

            areaStoricoPrenotazioni.append(
                    "PRENOTAZIONE " + numero + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "------------------------------\n"
            );

            areaStoricoPrenotazioni.append(
                    "Ricevuta: "
                            + p.getRicevuta().getId()
                            + "\n\n"
            );

            areaStoricoPrenotazioni.append(
                    "Proprietario: "
                            + proprietario.getNome()
                            + " "
                            + proprietario.getCognome()
                            + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Telefono: "
                            + proprietario.getNumeroTelefono()
                            + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Email: "
                            + proprietario.getEmail()
                            + "\n\n"
            );

            areaStoricoPrenotazioni.append(
                    "Marca: " + auto.getMarca() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Tipo: " + auto.getTipo() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Carburante: " + auto.getCarburante() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Matricola: " + auto.getMatricola() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Cilindrata: " + auto.getCilindrata() + "\n\n"
            );

            areaStoricoPrenotazioni.append(
                    "Tipo intervento: "
                            + p.getTipoIntervento().getDescrizione()
                            + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Problema: " + p.getProblema() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Esito: " + p.getEsito() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Pezzi: " + p.getPezzi() + "\n"
            );

            areaStoricoPrenotazioni.append(
                    "Costo: "
                            + String.format("%.2f", p.getCosto())
                            + " €\n"
            );

            areaStoricoPrenotazioni.append(
                    "------------------------------\n\n"
            );

            numero++;
        }

        areaStoricoPrenotazioni.setCaretPosition(0);
    }


    // =========================================================
    // PRENOTAZIONI MECCANICO
    // =========================================================

    private JPanel creaCompletaIntervento() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("COMPLETA INTERVENTO");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MODULO
        // =========================

        JPanel modulo =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.anchor =
                GridBagConstraints.WEST;


        // =========================
        // CAMPI
        // =========================

        JTextField campoProblema =
                new JTextField();

        JTextField campoPezzi =
                new JTextField();

        JTextField campoCosto =
                new JTextField();


        Dimension dimensioneCampo =
                new Dimension(300, 35);

        campoProblema.setPreferredSize(dimensioneCampo);
        campoPezzi.setPreferredSize(dimensioneCampo);
        campoCosto.setPreferredSize(dimensioneCampo);




        gbc.gridx = 0;
        gbc.gridy = 0;

        modulo.add(
                new JLabel("Problema riscontrato:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoProblema,
                gbc
        );




        gbc.gridx = 0;
        gbc.gridy = 1;

        modulo.add(
                new JLabel("Pezzi cambiati:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoPezzi,
                gbc
        );




        gbc.gridx = 0;
        gbc.gridy = 2;

        modulo.add(
                new JLabel("Costo intervento:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoCosto,
                gbc
        );




        JButton btnIndietro =
                new JButton("Indietro");

        JButton btnConferma =
                new JButton("Conferma intervento");


        gbc.gridy = 3;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        modulo.add(
                btnIndietro,
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                btnConferma,
                gbc
        );


        pannello.add(
                modulo,
                BorderLayout.CENTER
        );




        btnIndietro.addActionListener(e -> {

            campoProblema.setText("");
            campoPezzi.setText("");
            campoCosto.setText("");

            cardLayout.show(
                    pannelloPrincipale,
                    "PRENOTAZIONI_MECCANICO"
            );
        });


        // =========================
        // CONFERMA INTERVENTO
        // =========================

        btnConferma.addActionListener(e -> {

            if (prenotazioneSelezionata == null) {
                return;
            }


            String problema =
                    campoProblema.getText().trim();

            String pezzi =
                    campoPezzi.getText().trim();

            String costoTesto =
                    campoCosto.getText().trim();


            // La sezione costo e problema richiede la compilazione mentre pezzi può essere lasciato vuoto.

            if (problema.isEmpty()
                    || costoTesto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci il problema riscontrato e il costo."
                );

                return;
            }


            // =========================
            // CONTROLLO COSTO
            // =========================

            double costo;

            try {

                costo =
                        Double.parseDouble(
                                costoTesto.replace(",", ".")
                        );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Il costo deve essere un numero."
                );

                return;
            }


            if (costo < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Il costo non può essere negativo."
                );

                return;
            }



            if (pezzi.isEmpty()) {
                pezzi = "Nessun pezzo cambiato";
            }


            // =========================
            // COMPLETO INTERVENTO
            // =========================

            prenotazioneSelezionata.setProblema(
                    problema
            );

            System.out.println(
                    "PRIMA = " + officina.getNumeroPrenotazioni()
            );

            prenotazioneSelezionata
                    .getTipoIntervento()
                    .esegui(prenotazioneSelezionata);

            prenotazioneSelezionata.setRiparata(
                    true,
                    "Intervento completato",
                    pezzi,
                    costo
            );

            System.out.println(
                    "DOPO = " + officina.getNumeroPrenotazioni()
            );


            // =========================
            // SALVATAGGIO
            // =========================

            gestoreFile.salvaPrenotazioni(
                    officina
            );


            // =========================
            // CONFERMA
            // =========================

            JOptionPane.showMessageDialog(
                    this,
                    "Intervento completato correttamente.\n"
                            + "Costo: "
                            + String.format("%.2f", costo)
                            + " €"
            );


            // =========================
            // PULISCO I CAMPI
            // =========================

            campoProblema.setText("");
            campoPezzi.setText("");
            campoCosto.setText("");


            prenotazioneSelezionata = null;



            aggiornaPrenotazioniMeccanico();



            cardLayout.show(
                    pannelloPrincipale,
                    "PRENOTAZIONI_MECCANICO"
            );
        });


        return pannello;
    }

    private JPanel creaPrenotazioniMeccanico() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("PRENOTAZIONI OFFICINA");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // PANNELLO PRENOTAZIONI
        // =========================

        pannelloPrenotazioniMeccanico =
                new JPanel();

        pannelloPrenotazioniMeccanico.setLayout(
                new BoxLayout(
                        pannelloPrenotazioniMeccanico,
                        BoxLayout.Y_AXIS
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        pannelloPrenotazioniMeccanico
                );

        pannello.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        JButton btnIndietro =
                new JButton("Indietro");

        btnIndietro.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_MECCANICO"
            );
        });

        pannello.add(
                btnIndietro,
                BorderLayout.SOUTH
        );


        return pannello;
    }
    private void aggiornaPrenotazioniMeccanico() {

        pannelloPrenotazioniMeccanico.removeAll();

        Prenotazione[] prenotazioni =
                officina.getPrenotazioni();

        int numero = 1;

        for (int i = 0;
             i < officina.getNumeroPrenotazioni();
             i++) {

            Prenotazione p =
                    prenotazioni[i];

            if (p == null) {
                continue;
            }

            // =========================
            // FILTRO MECCANICO
            // =========================

            if (p.getMeccanico() == null) {
                continue;
            }

            if (p.getMeccanico().getMatricola()
                    != meccanicoLoggato.getMatricola()) {

                continue;
            }

            if (p.isRiparata()) {
                continue;
            }

            Auto auto =
                    p.getAuto();


            // =========================
            // SINGOLA PRENOTAZIONE
            // =========================

            JPanel pannelloPrenotazione =
                    new JPanel(new BorderLayout());

            pannelloPrenotazione.setBorder(
                    BorderFactory.createEmptyBorder(
                            15, 20, 15, 20
                    )
            );


            JTextArea informazioni =
                    new JTextArea();

            informazioni.setEditable(false);

            informazioni.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            16
                    )
            );


            informazioni.append(
                    "PRENOTAZIONE " + numero + "\n"
            );

            informazioni.append(
                    "------------------------------\n"
            );

            informazioni.append(
                    "Ricevuta: "
                            + p.getRicevuta().getId()
                            + "\n\n"
            );

            informazioni.append(
                    "Marca: "
                            + auto.getMarca()
                            + "\n"
            );

            informazioni.append(
                    "Tipo: "
                            + auto.getTipo()
                            + "\n"
            );

            informazioni.append(
                    "Carburante: "
                            + auto.getCarburante()
                            + "\n"
            );

            informazioni.append(
                    "Matricola: "
                            + auto.getMatricola()
                            + "\n"
            );

            informazioni.append(
                    "Cilindrata: "
                            + auto.getCilindrata()
                            + "\n\n"
            );

            informazioni.append(
                    "Tipo intervento: "
                            + p.getTipoIntervento().getDescrizione()
                            + "\n\n"
            );



            // =========================
            // STATO
            // =========================

            if (p.isRiparata()) {

                informazioni.append(
                        "Stato: Riparazione terminata\n"
                );

            } else if (p.getMeccanico() != null) {

                informazioni.append(
                        "Stato: In lavorazione\n"
                );

            } else {

                informazioni.append(
                        "Stato: In attesa del meccanico\n"
                );
            }


            // =========================
            // PULSANTE INTERVIENI
            // =========================
            final Prenotazione prenotazioneCorrente = p;

            JButton btnIntervieni =
                    new JButton("Intervieni");



            btnIntervieni.addActionListener(e -> {

                int scelta = JOptionPane.showConfirmDialog(
                        this,
                        "Operazione terminata?",
                        "Intervento",
                        JOptionPane.YES_NO_OPTION
                );

                if (scelta == JOptionPane.NO_OPTION
                        || scelta == JOptionPane.CLOSED_OPTION) {
                    return;
                }

                prenotazioneSelezionata = prenotazioneCorrente;;

                cardLayout.show(
                        pannelloPrincipale,
                        "COMPLETA_INTERVENTO"
                );
            });


            pannelloPrenotazione.add(
                    informazioni,
                    BorderLayout.CENTER
            );

            pannelloPrenotazione.add(
                    btnIntervieni,
                    BorderLayout.SOUTH
            );


            pannelloPrenotazioniMeccanico.add(
                    pannelloPrenotazione
            );


            numero++;
        }




        if (numero == 1) {

            JLabel nessunaPrenotazione =
                    new JLabel(
                            "Non ci sono prenotazioni."
                    );

            nessunaPrenotazione.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            pannelloPrenotazioniMeccanico.add(
                    nessunaPrenotazione
            );
        }


        pannelloPrenotazioniMeccanico.revalidate();
        pannelloPrenotazioniMeccanico.repaint();
    }

    private JPanel creaRitiroPagamento() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("RITIRO E PAGAMENTO");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // PANNELLO AUTO DA RITIRARE
        // =========================

        pannelloRitiroPagamento =
                new JPanel();

        pannelloRitiroPagamento.setLayout(
                new BoxLayout(
                        pannelloRitiroPagamento,
                        BoxLayout.Y_AXIS
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        pannelloRitiroPagamento
                );

        pannello.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // INDIETRO
        // =========================

        JButton btnIndietro =
                new JButton("Indietro");

        btnIndietro.addActionListener(e -> {

            cardLayout.show(
                    pannelloPrincipale,
                    "AREA_MECCANICO"
            );

        });

        pannello.add(
                btnIndietro,
                BorderLayout.SOUTH
        );


        return pannello;
    }

    private JPanel creaConfermaPagamento() {

        JPanel pannello =
                new JPanel(new BorderLayout());


        // =========================
        // TITOLO
        // =========================

        JLabel titolo =
                new JLabel("CONFERMA PAGAMENTO");

        titolo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titolo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titolo.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 0, 30, 0
                )
        );

        pannello.add(
                titolo,
                BorderLayout.NORTH
        );


        // =========================
        // MODULO
        // =========================

        JPanel modulo =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.anchor =
                GridBagConstraints.WEST;




        JTextField campoRicevuta =
                new JTextField();

        campoRicevuta.setPreferredSize(
                new Dimension(300, 35)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;

        modulo.add(
                new JLabel("ID ricevuta:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                campoRicevuta,
                gbc
        );




        labelCostoPagamento =
                new JLabel("0.00 €");

        gbc.gridx = 0;
        gbc.gridy = 1;

        modulo.add(
                new JLabel("Costo da pagare:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                labelCostoPagamento,
                gbc
        );


        String[] metodi = {
                "Seleziona...",
                "Contanti",
                "Carta di credito",
                "Bancomat"
        };

        JComboBox<String> menuPagamento =
                new JComboBox<>(metodi);

        menuPagamento.setPreferredSize(
                new Dimension(300, 35)
        );

        gbc.gridx = 0;
        gbc.gridy = 2;

        modulo.add(
                new JLabel("Metodo di pagamento:"),
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                menuPagamento,
                gbc
        );




        JButton btnIndietro =
                new JButton("Indietro");

        JButton btnConferma =
                new JButton("Conferma pagamento");

        gbc.gridy = 3;

        gbc.gridx = 0;
        gbc.anchor =
                GridBagConstraints.CENTER;

        modulo.add(
                btnIndietro,
                gbc
        );

        gbc.gridx = 1;

        modulo.add(
                btnConferma,
                gbc
        );


        pannello.add(
                modulo,
                BorderLayout.CENTER
        );




        btnIndietro.addActionListener(e -> {

            campoRicevuta.setText("");
            menuPagamento.setSelectedIndex(0);

            cardLayout.show(
                    pannelloPrincipale,
                    "RITIRO_PAGAMENTO"
            );

        });




        btnConferma.addActionListener(e -> {

            if (prenotazioneSelezionata == null) {
                return;
            }

            // =========================
            // CONTROLLO ID RICEVUTA
            // =========================

            String testoRicevuta =
                    campoRicevuta.getText().trim();

            if (testoRicevuta.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci l'ID della ricevuta."
                );

                return;
            }

            int idRicevuta;

            try {

                idRicevuta =
                        Integer.parseInt(testoRicevuta);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "L'ID della ricevuta deve essere un numero."
                );

                return;
            }

            // =========================
            // VERIFICA RICEVUTA
            // =========================

            if (!prenotazioneSelezionata.verificaRicevuta(idRicevuta)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ricevuta non valida."
                );

                return;
            }

            // =========================
            // CONTROLLO METODO PAGAMENTO
            // =========================

            if (menuPagamento.getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleziona un metodo di pagamento."
                );

                return;
            }

            String metodoSelezionato =
                    (String) menuPagamento.getSelectedItem();

            MetodoPagamento metodoPagamento;

            // =========================
            // STRATEGY
            // =========================

            switch (metodoSelezionato) {

                case "Contanti":
                    metodoPagamento = new Contanti();
                    break;

                case "Carta di credito":
                    metodoPagamento = new CartaDiCredito();
                    break;

                case "Bancomat":
                    metodoPagamento = new Bancomat();
                    break;

                default:
                    return;
            }

            // =========================
            // PAGAMENTO
            // =========================

            double importo =
                    prenotazioneSelezionata.getCosto();

            metodoPagamento.paga(
                    prenotazioneSelezionata,
                    importo
            );

            // =========================
            // MESSAGGIO CONFERMA
            // =========================

            JOptionPane.showMessageDialog(
                    this,
                    "Pagamento effettuato correttamente.\n"
                            + "Metodo: "
                            + metodoPagamento.getDescrizione()
                            + "\n"
                            + "Importo: "
                            + String.format("%.2f", importo)
                            + " €"
            );

            // =========================
            // RITIRO AUTO
            // =========================


            Auto autoRitirata =
                    prenotazioneSelezionata.getAuto();


// =========================
// AGGIUNGO ALLO STORICO
// =========================

            officina.aggiungiStoricoPrenotazione(
                    prenotazioneSelezionata
            );

            gestoreFile.salvaStoricoPrenotazioni(
                    officina
            );


                   // =========================
                   // RIMUOVO DALLE PRENOTAZIONI ATTIVE
                   // =========================

            officina.rimuoviPrenotazione(
                    prenotazioneSelezionata
            );


                       // =========================
                       // RIMUOVO AUTO DALL'OFFICINA
                       // =========================

            officina.rimuoviAuto(
                    autoRitirata
            );


           // =========================
           // SALVO PRENOTAZIONI E AUTO
           // =========================

            gestoreFile.salvaPrenotazioni(
                    officina
            );

            gestoreFile.salvaAuto(
                    officina
            );


          // =========================
          // PULIZIA
          // =========================

            prenotazioneSelezionata = null;

            campoRicevuta.setText("");
            menuPagamento.setSelectedIndex(0);


         // =========================
         // AGGIORNO RITIRO
         // =========================

            aggiornaRitiroPagamento();

            cardLayout.show(
                    pannelloPrincipale,
                    "RITIRO_PAGAMENTO"
            );

        });



        return pannello;
    }

    private void aggiornaRitiroPagamento() {

        pannelloRitiroPagamento.removeAll();

        Prenotazione[] prenotazioni =
                officina.getPrenotazioni();

        int numero = 1;


        for (int i = 0;
             i < officina.getNumeroPrenotazioni();
             i++) {

            Prenotazione p =
                    prenotazioni[i];

            if (p == null) {
                continue;
            }


            // Mostro SOLO le auto
            // con intervento terminato
            if (!p.isRiparata()) {
                continue;
            }


            Auto auto =
                    p.getAuto();


            // =========================
            // SINGOLA PRENOTAZIONE
            // =========================

            JPanel pannelloPrenotazione =
                    new JPanel(new BorderLayout());

            pannelloPrenotazione.setBorder(
                    BorderFactory.createEmptyBorder(
                            15, 20, 15, 20
                    )
            );


            JTextArea informazioni =
                    new JTextArea();

            informazioni.setEditable(false);

            informazioni.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            16
                    )
            );


            informazioni.append(
                    "PRENOTAZIONE " + numero + "\n"
            );

            informazioni.append(
                    "------------------------------\n"
            );


            // =========================
            // RICEVUTA
            // =========================

            informazioni.append(
                    "Ricevuta: "
                            + p.getRicevuta().getId()
                            + "\n\n"
            );


            // =========================
            // PROPRIETARIO
            // =========================

            informazioni.append(
                    "Proprietario: "
                            + p.getProprietario().getNome()
                            + " "
                            + p.getProprietario().getCognome()
                            + "\n\n"
            );


            // =========================
            // AUTO
            // =========================

            informazioni.append(
                    "Marca: "
                            + auto.getMarca()
                            + "\n"
            );

            informazioni.append(
                    "Tipo: "
                            + auto.getTipo()
                            + "\n"
            );

            informazioni.append(
                    "Carburante: "
                            + auto.getCarburante()
                            + "\n"
            );

            informazioni.append(
                    "Matricola: "
                            + auto.getMatricola()
                            + "\n"
            );

            informazioni.append(
                    "Cilindrata: "
                            + auto.getCilindrata()
                            + "\n\n"
            );


            // =========================
            // INTERVENTO
            // =========================

            informazioni.append(
                    "Tipo intervento: "
                            + p.getTipoIntervento().getDescrizione()
                            + "\n"
            );

            informazioni.append(
                    "Costo: "
                            + String.format(
                            "%.2f",
                            p.getCosto()
                    )
                            + " €\n\n"
            );


            informazioni.append(
                    "Stato: Auto pronta per il ritiro\n"
            );


            // =========================
            // CONFERMA PAGAMENTO
            // =========================

            final Prenotazione prenotazioneCorrente =
                    p;

            JButton btnPagamento =
                    new JButton("Conferma pagamento");


            btnPagamento.addActionListener(e -> {

                prenotazioneSelezionata =
                        prenotazioneCorrente;

                labelCostoPagamento.setText(
                        String.format(
                                "%.2f €",
                                prenotazioneSelezionata.getCosto()
                        )
                );

                cardLayout.show(
                        pannelloPrincipale,
                        "CONFERMA_PAGAMENTO"
                );

            });


            pannelloPrenotazione.add(
                    informazioni,
                    BorderLayout.CENTER
            );

            pannelloPrenotazione.add(
                    btnPagamento,
                    BorderLayout.SOUTH
            );


            pannelloRitiroPagamento.add(
                    pannelloPrenotazione
            );


            numero++;
        }


        // =========================
        // NESSUNA AUTO DA RITIRARE
        // =========================

        if (numero == 1) {

            JLabel nessunaAuto =
                    new JLabel(
                            "Non ci sono auto pronte per il ritiro."
                    );

            nessunaAuto.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            pannelloRitiroPagamento.add(
                    nessunaAuto
            );
        }


        pannelloRitiroPagamento.revalidate();
        pannelloRitiroPagamento.repaint();
    }

    // =========================================================
    // Infine il Main
    // =========================================================

    public static void main(String[] args) {

        new OfficinaGui();

    }
}

