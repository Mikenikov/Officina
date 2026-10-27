package PagamentoStrategy;

import MainClasses.Prenotazione;

public class CartaDiCredito implements MetodoPagamento {

    @Override
    public void paga(Prenotazione prenotazione, double importo) {

        if (importo == prenotazione.getCosto()) {

            System.out.println(
                    "Pagamento effettuato con carta di credito: "
                            + importo + " euro."
            );

        } else {

            System.out.println(
                    "Importo non corretto. Il costo è di "
                            + prenotazione.getCosto() + " euro."
            );
        }
    }

    @Override
    public String getDescrizione() {
        return "Carta Di Credito";
    }
}