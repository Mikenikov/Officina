package State;
import MainClasses.*;

import java.io.Serializable;

public interface StatoAuto extends Serializable {


    void entra(Auto auto);

    void esci(Auto auto);
}
