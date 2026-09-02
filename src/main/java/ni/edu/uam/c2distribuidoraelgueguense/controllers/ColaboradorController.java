package ni.edu.uam.c2distribuidoraelgueguense.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.c2distribuidoraelgueguense.models.Colaborador;
import ni.edu.uam.c2distribuidoraelgueguense.utils.Alertas;
import ni.edu.uam.c2distribuidoraelgueguense.utils.Validador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorController {

    // ---- Controles del formulario ----
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbCargo;
    @FXML private ListView<String> lstAreas;
    @FXML private DatePicker dpFecha;
    @FXML private ToggleGroup grupoContrato;
    @FXML private RadioButton rbPermanente;
    @FXML private RadioButton rbTemporal;
    @FXML private RadioButton rbServicios;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkTransporte;
    @FXML private CheckBox chkAlimentacion;
    @FXML private CheckBox chkCapacitacion;
    @FXML private ImageView imgLogo;

    // ---- Tabla ----
    @FXML private TableView<Colaborador> tabla;
    @FXML private TableColumn<Colaborador, String> colNombre;
    @FXML private TableColumn<Colaborador, String> colCargo;
    @FXML private TableColumn<Colaborador, String> colArea;
    @FXML private TableColumn<Colaborador, LocalDate> colFecha;
    @FXML private TableColumn<Colaborador, String> colContrato;
    @FXML private TableColumn<Colaborador, String> colBeneficios;

    private final ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbCargo.setItems(FXCollections.observableArrayList(
                "Gerente", "Supervisor", "Vendedor", "Cajero", "Bodeguero", "Contador", "Conductor"));

        lstAreas.setItems(FXCollections.observableArrayList(
                "Administración", "Ventas", "Bodega", "Contabilidad", "Logística", "Recursos Humanos"));
        lstAreas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colContrato.setCellValueFactory(new PropertyValueFactory<>("tipoContrato"));
        colBeneficios.setCellValueFactory(new PropertyValueFactory<>("beneficiosTexto"));
        tabla.setItems(colaboradores);

        // ContextMenu sobre la tabla: Editar y Eliminar
        ContextMenu menu = new ContextMenu();
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
        miEditar.setOnAction(e -> cargarSeleccionado());
        miEliminar.setOnAction(e -> eliminarSeleccionado());
        menu.getItems().addAll(miEditar, miEliminar);
        tabla.setContextMenu(menu);

        cargarLogo();
    }

    private void cargarLogo() {
        try {
            URL url = getClass().getResource("/ni/edu/uam/c2distribuidoraelgueguense/images/logo.png");
            if (url != null) {
                imgLogo.setImage(new Image(url.toExternalForm()));
            }
        } catch (Exception ignored) {
            // Si no hay imagen disponible, el ImageView queda vacío.
        }
    }

    // ================= ActionEvent =================

    @FXML
    private void onGuardar() {
        List<String> errores = validarFormulario();
        if (!errores.isEmpty()) {
            Alertas.errores("Datos incompletos", errores);
            return;
        }
        colaboradores.add(construirDesdeFormulario());
        Alertas.info("Guardado", "Colaborador registrado correctamente.");
        limpiarFormulario();
    }

    @FXML
    private void onActualizar() {
        Colaborador sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Sin selección", "Seleccione un colaborador en la tabla para actualizar.");
            return;
        }
        List<String> errores = validarFormulario();
        if (!errores.isEmpty()) {
            Alertas.errores("Datos incompletos", errores);
            return;
        }
        colaboradores.set(colaboradores.indexOf(sel), construirDesdeFormulario());
        tabla.getSelectionModel().clearSelection();
        Alertas.info("Actualizado", "Colaborador actualizado correctamente.");
        limpiarFormulario();
    }

    @FXML
    private void onLimpiar() {
        limpiarFormulario();
    }

    @FXML
    private void onEliminar() {
        eliminarSeleccionado();
    }

    // ================= MouseEvent =================

    @FXML
    private void onTablaClick(MouseEvent e) {
        if (e.getClickCount() == 2) {
            cargarSeleccionado();
        }
    }

    // ================= KeyEvent =================

    @FXML
    private void onFormKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            onGuardar();
        } else if (e.getCode() == KeyCode.ESCAPE) {
            limpiarFormulario();
        }
    }

    // ================= MenuBar =================

    @FXML
    private void onNuevo() {
        tabla.getSelectionModel().clearSelection();
        limpiarFormulario();
    }

    @FXML
    private void onSalir() {
        if (Alertas.confirmar("Salir", "¿Desea cerrar la aplicación?")) {
            Platform.exit();
        }
    }

    @FXML
    private void onAcercaDe() {
        Alertas.info("Acerca de",
                "Distribuidora El Güegüense\n" +
                        "Sistema de Registro de Colaboradores\n\n" +
                        "Programación Orientada a Objetos\n" +
                        "JavaFX + Scene Builder - 2026");
    }

    // ================= Lógica de apoyo =================

    private List<String> validarFormulario() {
        return Validador.validar(
                txtNombres.getText(),
                txtApellidos.getText(),
                txtUsuario.getText(),
                txtPassword.getText(),
                cmbCargo.getValue(),
                lstAreas.getSelectionModel().getSelectedItem(),
                dpFecha.getValue(),
                tipoContratoSeleccionado(),
                !beneficiosSeleccionados().isEmpty());
    }

    private Colaborador construirDesdeFormulario() {
        return new Colaborador(
                txtNombres.getText().trim(),
                txtApellidos.getText().trim(),
                txtUsuario.getText().trim(),
                txtPassword.getText(),
                cmbCargo.getValue(),
                lstAreas.getSelectionModel().getSelectedItem(),
                dpFecha.getValue(),
                tipoContratoSeleccionado(),
                beneficiosSeleccionados());
    }

    private String tipoContratoSeleccionado() {
        Toggle t = grupoContrato.getSelectedToggle();
        return t == null ? null : ((RadioButton) t).getText();
    }

    private List<String> beneficiosSeleccionados() {
        List<String> b = new ArrayList<>();
        if (chkSeguro.isSelected())       b.add("Seguro médico");
        if (chkTransporte.isSelected())   b.add("Transporte");
        if (chkAlimentacion.isSelected()) b.add("Alimentación");
        if (chkCapacitacion.isSelected()) b.add("Capacitación");
        return b;
    }

    private void cargarSeleccionado() {
        Colaborador c = tabla.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alertas.error("Sin selección", "Seleccione un colaborador de la tabla.");
            return;
        }
        txtNombres.setText(c.getNombres());
        txtApellidos.setText(c.getApellidos());
        txtUsuario.setText(c.getUsuario());
        txtPassword.setText(c.getPasswordTemporal());
        cmbCargo.setValue(c.getCargo());
        lstAreas.getSelectionModel().select(c.getArea());
        dpFecha.setValue(c.getFechaContratacion());
        seleccionarContrato(c.getTipoContrato());

        List<String> ben = c.getBeneficios() == null ? new ArrayList<>() : c.getBeneficios();
        chkSeguro.setSelected(ben.contains("Seguro médico"));
        chkTransporte.setSelected(ben.contains("Transporte"));
        chkAlimentacion.setSelected(ben.contains("Alimentación"));
        chkCapacitacion.setSelected(ben.contains("Capacitación"));
    }

    private void seleccionarContrato(String tipo) {
        grupoContrato.selectToggle(null);
        if (tipo == null) return;
        for (Toggle t : grupoContrato.getToggles()) {
            if (((RadioButton) t).getText().equals(tipo)) {
                grupoContrato.selectToggle(t);
                break;
            }
        }
    }

    private void eliminarSeleccionado() {
        Colaborador c = tabla.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alertas.error("Sin selección", "Seleccione un colaborador para eliminar.");
            return;
        }
        if (Alertas.confirmar("Confirmar eliminación", "¿Eliminar a " + c.getNombreCompleto() + "?")) {
            colaboradores.remove(c);
            limpiarFormulario();
        }
    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        txtUsuario.clear();
        txtPassword.clear();
        cmbCargo.setValue(null);
        cmbCargo.getSelectionModel().clearSelection();
        lstAreas.getSelectionModel().clearSelection();
        dpFecha.setValue(null);
        grupoContrato.selectToggle(null);
        chkSeguro.setSelected(false);
        chkTransporte.setSelected(false);
        chkAlimentacion.setSelected(false);
        chkCapacitacion.setSelected(false);
        txtNombres.requestFocus();
    }
}