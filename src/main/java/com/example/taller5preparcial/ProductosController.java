package com.example.taller5preparcial;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ProductosController {

    // Elementos inyectados desde FXML
    @FXML private VBox productosPanel;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Number> colPrecio; // Usar Number para DoubleProperty

    private ObservableList<Producto> datosProductos;

    @FXML
    public void initialize() {
        // 1. Conectar las columnas de la tabla con las propiedades del modelo Producto
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty());

        // 2. Aplicar formato para no mostrar decimales en precios enteros
        colPrecio.setCellFactory(tc -> new TableCell<Producto, Number>() {
            @Override
            protected void updateItem(Number precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty || precio == null) {
                    setText(null);
                } else {
                    double valor = precio.doubleValue();

                    // Comprueba si el valor es un entero (ej: 1000.0)
                    if (valor == Math.floor(valor)) {
                        // Muestra el valor sin decimales (ej: 1000)
                        setText(String.format("%.0f", valor));
                    } else {
                        // Muestra el valor con decimales si los tiene (ej: 1000.50)
                        setText(String.valueOf(valor));
                    }
                }
            }
        });

        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 3. Conectar la selección de la tabla al formulario
        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> mostrarDetallesProducto(newValue)
        );
    }

    // Método llamado desde DashboardController para inicializar los datos
    public void initData(ObservableList<Producto> datos) {
        this.datosProductos = datos;
        tablaProductos.setItems(datosProductos);
    }

    private void mostrarDetallesProducto(Producto producto) {
        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            // Usar String.valueOf para mostrar el double en el TextField
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
        } else {
            limpiarCampos();
        }
    }

    // --- LÓGICA DE ACCIÓN ---

    @FXML
    private void guardarProducto() {
        try {
            String nombre = txtNombre.getText();
            // Intenta convertir el texto del precio a un número
            double precio = Double.parseDouble(txtPrecio.getText());

            if (!nombre.isEmpty()) {
                Producto nuevoProducto = new Producto(nombre, precio);
                datosProductos.add(nuevoProducto);
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            // Maneja el error si el usuario introduce texto en lugar de un número en el precio
            System.err.println("Error: El precio debe ser un número válido.");
        }
    }

    @FXML
    private void actualizarProducto() {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            try {
                productoSeleccionado.setNombre(txtNombre.getText());
                productoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));

                tablaProductos.refresh();
                limpiarCampos();
            } catch (NumberFormatException e) {
                System.err.println("Error: El precio debe ser un número válido para actualizar.");
            }
        }
    }

    @FXML
    private void borrarProducto() {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            datosProductos.remove(productoSeleccionado);
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtPrecio.clear();
    }
}