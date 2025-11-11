package com.example.taller5preparcial;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PanelClientes extends VBox {

    private final ObservableList<Cliente> datosClientes;
    private TableView<Cliente> tablaClientes;

    // Campos del formulario para fácil acceso en la lógica
    private TextField txtDocumento;
    private TextField txtNombre;
    private TextField txtTelefono;
    private TextField txtCorreo;

    public PanelClientes(ObservableList<Cliente> datosClientes) {
        this.datosClientes = datosClientes;

        // Configuración del VBox principal
        this.setPadding(new Insets(10));
        this.setSpacing(20);

        // Construcción de los componentes
        HBox topContainer = crearFormularioYBotones();
        tablaClientes = crearTablaClientes();

        // Conectar la selección de la tabla al formulario
        conectarSeleccionTabla();

        VBox.setVgrow(tablaClientes, Priority.ALWAYS);

        this.getChildren().addAll(topContainer, tablaClientes);
    }

    /**
     * Crea el HBox que contiene el formulario (GridPane) y los botones (VBox).
     */
    private HBox crearFormularioYBotones() {
        HBox topContainer = new HBox(30);
        topContainer.setAlignment(Pos.TOP_LEFT);

        // --- 1. Formulario ---
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(10));

        // Inicialización de los campos de texto
        txtDocumento = new TextField();
        txtNombre = new TextField();
        txtTelefono = new TextField();
        txtCorreo = new TextField();

        formulario.add(new Label("Documento:"), 0, 0);
        formulario.add(txtDocumento, 1, 0);
        formulario.add(new Label("Nombre:"), 0, 1);
        formulario.add(txtNombre, 1, 1);
        formulario.add(new Label("# Teléfono:"), 0, 2);
        formulario.add(txtTelefono, 1, 2);
        formulario.add(new Label("Correo:"), 0, 3);
        formulario.add(txtCorreo, 1, 3);

        ColumnConstraints columnaCampo = new ColumnConstraints();
        columnaCampo.setHgrow(Priority.ALWAYS);
        formulario.getColumnConstraints().addAll(new ColumnConstraints(80), columnaCampo);

        // --- 2. Botones de Acción ---
        VBox botonesAccion = new VBox(10);
        botonesAccion.setAlignment(Pos.TOP_CENTER);

        Button btnGuardar = new Button("Guardar");
        Button btnActualizar = new Button("Actualizar");
        Button btnBorrar = new Button("Borrar");

        btnGuardar.setPrefWidth(100);
        btnActualizar.setPrefWidth(100);
        btnBorrar.setPrefWidth(100);

        botonesAccion.getChildren().addAll(btnGuardar, btnActualizar, btnBorrar);

        // --- 3. Implementación de la Lógica de Botones ---
        btnGuardar.setOnAction(e -> guardarCliente());
        btnActualizar.setOnAction(e -> actualizarCliente());
        btnBorrar.setOnAction(e -> borrarCliente());

        topContainer.getChildren().addAll(formulario, botonesAccion);
        return topContainer;
    }

    /**
     * Crea la TableView de clientes.
     */
    private TableView<Cliente> crearTablaClientes() {
        TableView<Cliente> tabla = new TableView<>();
        tabla.setItems(datosClientes);

        TableColumn<Cliente, String> colDocumento = new TableColumn<>("Documento");
        colDocumento.setCellValueFactory(cellData -> cellData.getValue().documentoProperty());

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("# Teléfono");
        colTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());

        TableColumn<Cliente, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(cellData -> cellData.getValue().correoProperty());

        tabla.getColumns().addAll(colDocumento, colNombre, colTelefono, colCorreo);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tabla;
    }

    /**
     * Conecta la selección de la tabla a los campos de texto del formulario.
     */
    private void conectarSeleccionTabla() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        // Carga los datos del cliente seleccionado en el formulario
                        txtDocumento.setText(newValue.getDocumento());
                        txtNombre.setText(newValue.getNombre());
                        txtTelefono.setText(newValue.getTelefono());
                        txtCorreo.setText(newValue.getCorreo());
                    } else {
                        // Limpia el formulario si no hay nada seleccionado
                        limpiarCampos();
                    }
                }
        );
    }

    /**
     * Implementa la lógica para guardar un nuevo cliente.
     */
    private void guardarCliente() {
        if (!txtDocumento.getText().isEmpty() && !txtNombre.getText().isEmpty()) {
            Cliente nuevoCliente = new Cliente(
                    txtDocumento.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText()
            );
            datosClientes.add(nuevoCliente);
            limpiarCampos();
        }
    }

    /**
     * Implementa la lógica para actualizar el cliente seleccionado.
     */
    private void actualizarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            // Actualiza las propiedades del objeto cliente
            clienteSeleccionado.setDocumento(txtDocumento.getText());
            clienteSeleccionado.setNombre(txtNombre.getText());
            clienteSeleccionado.setTelefono(txtTelefono.getText());
            clienteSeleccionado.setCorreo(txtCorreo.getText());

            // Refresca la tabla para que los cambios sean visibles
            tablaClientes.refresh();
            limpiarCampos();
        }
    }

    /**
     * Implementa la lógica para borrar el cliente seleccionado.
     */
    private void borrarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            datosClientes.remove(clienteSeleccionado);
            limpiarCampos();
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
    }
}