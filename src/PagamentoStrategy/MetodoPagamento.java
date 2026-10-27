package PagamentoStrategy;

import MainClasses.Prenotazione;

public interface MetodoPagamento {
     void paga(Prenotazione prenotazione, double importo) ;


    String getDescrizione();


}
