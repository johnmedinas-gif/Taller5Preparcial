package com.example.taller5preparcial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane mainBorderPane;

    // Botones inyectados desde DashboardView.fxml
    @FXML
    private Button btnClientes;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnVentas; // Botón de Ventas

    // Listas de datos que persisten durante la vida de la aplicación
    private final ObservableList<Cliente> datosClientes = FXCollections.observableArrayList();
    private final ObservableList<Producto> datosProductos = FXCollections.observableArrayList();
    private final ObservableList<Venta> datosVentas = FXCollections.observableArrayList(); // Nueva lista para Ventas

    @FXML
    public void initialize() {
        // Conexión de los botones a sus métodos de carga de panel
        btnClientes.setOnAction(event -> cargarPanelClientes());
        btnProductos.setOnAction(event -> cargarPanelProductos());
        btnVentas.setOnAction(event -> cargarPanelVentas());
    }

    // -----------------------------------------------------------------------

    private void cargarPanelClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientesView.fxml"));
            Pane clientesView = loader.load();

            ClientesController clientesController = loader.getController();
            // Pasa la lista de Clientes al controlador
            clientesController.initData(datosClientes);

            mainBorderPane.setCenter(clientesView);

        } catch (IOException e) {
            System.err.println("Error cargando ClientesView.fxml.");
            e.printStackTrace();
        }
    }

    private void cargarPanelProductos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductosView.fxml"));
            Pane productosView = loader.load();

            ProductosController productosController = loader.getController();
            // Pasa la lista de Productos al controlador
            productosController.initData(datosProductos);

            mainBorderPane.setCenter(productosView);

        } catch (IOException e) {
            System.err.println("Error cargando ProductosView.fxml.");
            e.printStackTrace();
        }
    }

    private void cargarPanelVentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentasView.fxml"));
            Pane ventasView = loader.load();

            VentasController ventasController = loader.getController();
            // Pasa las TRES listas necesarias al controlador de Ventas
            ventasController.initData(datosClientes, datosProductos, datosVentas);

            mainBorderPane.setCenter(ventasView);

        } catch (IOException e) {
            System.err.println("Error cargando VentasView.fxml.");
            e.printStackTrace();
        }
    }
}