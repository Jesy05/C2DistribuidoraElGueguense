package ni.edu.uam.c2distribuidoraelgueguense.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;

/**
 * Utilidades para mostrar cuadros de diálogo de forma sencilla.
 */
public class Alertas {

    public static void info(String titulo, String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    public static void error(String titulo, String mensaje) {
        mostrar(Alert.AlertType.ERROR, titulo, mensaje);
    }

    public static void errores(String titulo, List<String> errores) {
        error(titulo, String.join("\n", errores));
    }

    public static boolean confirmar(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        return a.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}