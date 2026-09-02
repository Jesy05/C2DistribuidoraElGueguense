package ni.edu.uam.c2distribuidoraelgueguense;

import javafx.application.Application;

/**
 * Punto de entrada alterno (sin heredar de Application) para ejecutar
 * la aplicacion desde IntelliJ sin configuracion de modulos.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
