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
import javafx.scene.input.MouseEvent;
import model.Persona;

public class ActividadBController implements Initializable{

	@FXML
	private Button btnAgregar;
	
    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;
	
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
	private ObservableList<Persona> listaPersonas;
	private String camposNulos = "";

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
	 */
	@FXML
    void agregarPersona(ActionEvent event) {
		camposNulos = "";
		try {
			// Controlar que los parametros se insertan correctamente
			comprobarValores();
			// Crear persona
			String nombre= txtNombre.getText();
			String apellidos= txtApellidos.getText();
			Integer edad= Integer.parseInt(txtEdad.getText());
			Persona p = new Persona(nombre, apellidos, edad);
			// Insertar persona, controlando que no exista
			if (listaPersonas.contains(p)== false) {
				listaPersonas.add(p);
				ventanaAlerta("I", "Persona añadida correctamente");
				eliminarValores();
			}else{
				ventanaAlerta("E", "La persona ya existe");
			}	
		}catch(NullPointerException e){
			ventanaAlerta("E", camposNulos);
		}catch(NumberFormatException e) {
			ventanaAlerta("E", "El valor de edad debe ser un número entero mayor que cero");
		}
		
    }
	
	/*
	 * Metodo para eliminar persona de tabla.
	 */
	@FXML
    void eliminarPersona(ActionEvent event) {
		String sNombreEliminado = tblTabla.getSelectionModel().getSelectedItem().getNombre();
		String sApellidosEliminado = tblTabla.getSelectionModel().getSelectedItem().getApellidos();
		Integer nEdadEliminado = tblTabla.getSelectionModel().getSelectedItem().getEdad();
		listaPersonas.remove(new Persona(sNombreEliminado, sApellidosEliminado, nEdadEliminado));
		ventanaAlerta("I","Persona eliminada correctamente");
		eliminarValores();
    }

	/*
	 * Método para Modificar persona selecionada de la tabla
	 */
    @FXML
    void modificarPersona(ActionEvent event) {
    	camposNulos="";
    	try {
    		// Comprobar que los datos introducidos son correctos
    		comprobarValores();
    		// Crear persona para comprobar que no esxiste
    		Persona p = new Persona(txtNombre.getText(), txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));
    		if (!listaPersonas.contains(p)) {
        		// Modificar persona
    			String sNombreEliminado = tblTabla.getSelectionModel().getSelectedItem().getNombre();
    			String sApellidosEliminado = tblTabla.getSelectionModel().getSelectedItem().getApellidos();
    			Integer nEdadEliminado = tblTabla.getSelectionModel().getSelectedItem().getEdad();
    			listaPersonas.remove(new Persona(sNombreEliminado, sApellidosEliminado, nEdadEliminado));
    			listaPersonas.add(p);
    			ventanaAlerta("I", "Persona modificada correctamente");
    			eliminarValores();
    		}else {
    			ventanaAlerta("E", "Persona existente");
    		}
    		
    	}catch(NullPointerException e){
    		ventanaAlerta("E", camposNulos);
    	}catch(NumberFormatException e) {
			ventanaAlerta("E", "El valor de edad debe ser un número entero mayor que cero");
		}
    	
    }

    /*
     * Metodo para cargar una persona en los campos de texto
     */
    @FXML
    void seleccionarPersona(MouseEvent event) {

    	try {
    		txtNombre.setText(tblTabla.getSelectionModel().getSelectedItem().getNombre());
        	txtApellidos.setText(tblTabla.getSelectionModel().getSelectedItem().getApellidos());
        	txtEdad.setText(tblTabla.getSelectionModel().getSelectedItem().getEdad().toString());
    	}catch(NullPointerException e) {
    		ventanaAlerta("E", "Seleccione una persona para cargar. Si la tabla no contiene ninguna, agregue una nueva.");
    	}
    	
    }
	
	/*
	 * METODOS AUXILIARES
	 */
    
    // Metodo para mostrar alertas de tipo error o información
	void ventanaAlerta(String tipoAlerta, String mensaje) {
		Alert alert = null;
		switch (tipoAlerta) {
			case ("E"):
				alert = new Alert(Alert.AlertType.ERROR);
				break;
			case ("I"):
				alert = new Alert(Alert.AlertType.INFORMATION);
		}
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
	
	// Metodo para comprobar que los valores de los cuadro de texto son correctos.
	// Se controla que los campos no pueden ser nulos y que el campo edad sea un número mayor que 1.
	void comprobarValores() {
		if (txtNombre.getText().equals("")) {camposNulos = "El campo nombre es obligatorio\n";}
		if (txtApellidos.getText().equals("")) {camposNulos += "El campo apellidos es obligatorio\n";}
		if (txtEdad.getText().isEmpty()) {camposNulos += "El campo apellidos es obligatorio";}
		if (camposNulos!="") {throw new NullPointerException();}
		if (Integer.parseInt(txtEdad.getText().toString()) < 1) {throw new NumberFormatException();}
	}
	
	// Metodo para vaciar campos cada vez que insertamos, modificamos o eliminamos
	void eliminarValores() {
		txtNombre.clear();
		txtApellidos.clear();
		txtEdad.clear();
	}
}

	

