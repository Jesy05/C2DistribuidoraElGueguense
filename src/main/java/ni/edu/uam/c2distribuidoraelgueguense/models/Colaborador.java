package ni.edu.uam.c2distribuidoraelgueguense.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos de un colaborador de la Distribuidora El Güegüense.
 * Se guarda temporalmente en una ObservableList (sin base de datos).
 */
public class Colaborador {

    private String nombres;
    private String apellidos;
    private String usuario;
    private String passwordTemporal;
    private String cargo;
    private String area;
    private LocalDate fechaContratacion;
    private String tipoContrato;
    private List<String> beneficios;

    public Colaborador() {
        this.beneficios = new ArrayList<>();
    }

    public Colaborador(String nombres, String apellidos, String usuario, String passwordTemporal,
                       String cargo, String area, LocalDate fechaContratacion,
                       String tipoContrato, List<String> beneficios) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.passwordTemporal = passwordTemporal;
        this.cargo = cargo;
        this.area = area;
        this.fechaContratacion = fechaContratacion;
        this.tipoContrato = tipoContrato;
        this.beneficios = beneficios == null ? new ArrayList<>() : beneficios;
    }

    // ---- Propiedades derivadas usadas por el TableView ----

    public String getNombreCompleto() {
        return (nombres == null ? "" : nombres) + " " + (apellidos == null ? "" : apellidos);
    }

    public String getBeneficiosTexto() {
        return beneficios == null || beneficios.isEmpty() ? "" : String.join(", ", beneficios);
    }

    // ---- Getters y setters ----

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPasswordTemporal() { return passwordTemporal; }
    public void setPasswordTemporal(String passwordTemporal) { this.passwordTemporal = passwordTemporal; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public List<String> getBeneficios() { return beneficios; }
    public void setBeneficios(List<String> beneficios) { this.beneficios = beneficios; }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
}
