package MainClasses;
import State.*;
import java.io.Serializable;
public class Auto implements Serializable {

    private Proprietario proprietario;
    private String tipo;
    private String marca;
    private String carburante;
    private int matricola;
    private String cilindrata;

    private StatoAuto stato;

    public Auto (Proprietario proprietario, String marca, String tipo, String carburante, int matricola, String cilindrata){
        this.marca=marca;
        this.proprietario=proprietario;
        this.tipo=tipo;
        this.carburante=carburante;
        this.matricola=matricola;
        this.cilindrata=cilindrata;

        this.stato=new StatoUscita();
    }

    public void setstato(StatoAuto stato){

        this.stato=stato;
    }

    public Proprietario getProprietario() {

        return proprietario;
    }

    public int getMatricola(){

        return matricola;
    }

    public void entra(){

        stato.entra(this);
    }

    public void esci (){

        stato.esci(this);
    }

    public String getMarca() {
        return marca;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCarburante() {
        return carburante;
    }

    public String getCilindrata() {
        return cilindrata;
    }



}
