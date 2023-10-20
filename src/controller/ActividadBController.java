package controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Persona;

public class ActividadBController implements Initializable{

	@FXML
	private Button btnAgregar;
	
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
	
	private ObservableList<Persona> listaPersonas;

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
	 * Método para agregar personas a la tabla.
	 * Se controla que los campos no pueden ser nulos y que el campo edad sea un número mayor que 1.
	 */
	@FXML
    void agregarPersona(ActionEvent event) {
		String camposNulos = "";
		try {
			// Controlar que los parametros se insertan correctamente
			if (txtNombre.getText().equals("")) {camposNulos = "El campo nombre es obligatorio\n";}
			if (txtApellidos.getText().equals("")) {camposNulos += "El campo apellidos es obligatorio\n";}
			if (txtEdad.getText().isEmpty()) {camposNulos += "El campo apellidos es obligatorio";}
			if (camposNulos!="") {throw new NullPointerException();}
			if (Integer.parseInt(txtEdad.getText().toString()) < 1) {throw new NumberFormatException();}
			// Crear persona
			String nombre= txtNombre.getText();
			String apellidos= txtApellidos.getText();
			Integer edad= Integer.parseInt(txtEdad.getText());
			Persona p = new Persona(nombre, apellidos, edad);
			// Insertar persona, controlando que no exista
			if (listaPersonas.contains(p)== false) {
				listaPersonas.add(p);
				ventanaAlerta("C", "Persona añadida correctamente");
			}else{
				ventanaAlerta("E", "La persona ya existe");
			}	
		}catch(NullPointerException e){
			ventanaAlerta("E", camposNulos);
		}catch(NumberFormatException e) {
			ventanaAlerta("E", "El valor de edad debe ser un número mayor que cero");
		}
		
    }
	
	/*
	 * Metodo auxiliar para mostrar alertas de tipo error o confirmación
	 */
	void ventanaAlerta(String tipoAlerta, String mensaje) {
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