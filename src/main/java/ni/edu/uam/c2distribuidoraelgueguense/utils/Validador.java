package ni.edu.uam.c2distribuidoraelgueguense.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Validaciones del formulario de registro de colaboradores.
 * Devuelve la lista de errores encontrados (vacía si todo es correcto).
 */
public class Validador {

    public static List<String> validar(String nombres,
                                       String apellidos,
                                       String usuario,
                                       String password,
                                       String cargo,
                                       String area,
                                       LocalDate fechaContratacion,
                                       String tipoContrato,
                                       boolean alMenosUnBeneficio) {

        List<String> errores = new ArrayList<>();

        if (vacio(nombres))   errores.add("Los nombres no pueden quedar vacíos.");
        if (vacio(apellidos)) errores.add("Los apellidos no pueden quedar vacíos.");

        if (vacio(usuario)) {
            errores.add("El usuario no puede quedar vacío.");
        } else if (usuario.trim().length() < 5) {
            errores.add("El usuario debe tener al menos 5 caracteres.");
        }

        if (vacio(password)) {
            errores.add("La contraseña temporal no puede quedar vacía.");
        } else if (password.length() < 8) {
            errores.add("La contraseña debe tener al menos 8 caracteres.");
        }

        if (vacio(cargo)) errores.add("Debe seleccionar un cargo.");
        if (vacio(area))  errores.add("Debe seleccionar un área de trabajo.");

        if (fechaContratacion == null) {
            errores.add("Debe seleccionar la fecha de contratación.");
        } else if (fechaContratacion.isAfter(LocalDate.now())) {
            errores.add("La fecha de contratación no puede ser posterior a la fecha actual.");
        }

        if (vacio(tipoContrato)) errores.add("Debe seleccionar el tipo de contrato.");
        if (!alMenosUnBeneficio) errores.add("Debe seleccionar al menos un beneficio.");

        return errores;
    }

    private static boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}