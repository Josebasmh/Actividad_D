package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Persona;

public class ActividadBController implements Initializable{

	@FXML
	private Button btnAgregar;
	
	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;
	
	@FXML
    private TableView<Persona> tblTabla;
	
	@FXML
	private TableColumn<Persona, String> tblApellidos;
	
	@FXML
	private TableColumn<Persona, Integer> tblEdad;
	
	@FXML
	private TableColumn<Persona, String> tblNombre;
	
	@FXML
	private TextField txtApellidos;
	
	@FXML
	private TextField txtEdad;
	
	@FXML
	private TextField txtNombre;
	
	// Variables de clase
	static ObservableList<Persona> listaPersonas;

	/*
	 * Método de inicialización
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaPersonas = FXCollections.observableArrayList();
		
		tblNombre.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombre"));
		tblApellidos.setCellValueFactory(new PropertyValueFactory<Persona, String>("apellidos"));
		tblEdad.setCellValueFactory(new PropertyValueFactory<Persona, Integer>("edad"));		
			
		
		tblTabla.setItems(listaPersonas);		
	}
		
	/*
	 * Método para abrir la ventana 'VentanaNuePer'
	 */
	@FXML
    void agregarPersona(ActionEvent event) {
		Stage arg0 = new Stage();
		arg0.setTitle("NUEVA PERSONA"); 
		FlowPane aux;
		try {
			aux = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/NuevaPersona.fxml"));
			Scene scene = new Scene(aux,600,300);
			arg0.setScene(scene);
			arg0.setMinHeight(300);
			arg0.setMinWidth(600);
			arg0.show();
		} catch (IOException e) {
			System.out.println("La ventana no se abrió correctamente.");
			e.printStackTrace();
		}
		
    }
	
	
	
	/*
	 * Metodo auxiliar para mostrar alertas de tipo error o confirmación
	 */
	static void ventanaAlerta(String tipoAlerta, String mensaje) {
		Alert alert = null;
		switch (tipoAlerta) {
			case ("E"):
				alert = new Alert(Alert.AlertType.ERROR);
				break;
			case ("C"):
				alert = new Alert(Alert.AlertType.CONFIRMATION);
		}
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
}