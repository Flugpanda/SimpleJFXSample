package de.sample.jfx.controller;

import de.sample.jfx.MainApp;
import de.sample.jfx.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * The controller for the PersonOverview
 * 
 * @author Bastian Bräunel
 *
 */
public class PersonOverviewController {

	// register all the elements of the table
	@FXML
	private TableView<Person> tblPersons;
	@FXML
	private TableColumn<Person, String> columnFirstName;
	@FXML
	private TableColumn<Person, String> columnLastName;

	// register all the lables
	@FXML
	private Label lblFirstNAme;
	@FXML
	private Label lblLastName;
	@FXML
	private Label lblBirthday;
	@FXML
	private Label lblStreet;
	@FXML
	private Label lblCity;
	@FXML
	private Label lblPostCode;

	// register all the buttons
	@FXML
	private Button btnNew;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * Default constructor. The constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		columnFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		columnLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		// reset all labels
		showPersonDetails(null);

		// register an jfx event lister for the table with the listed persons
		// so that it realizes if the user changed the selected person
		// an display the information of the newly selected person
		tblPersons.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		tblPersons.setItems(mainApp.getPersonData());
	}

	/**
	 * Display the details of the given Person
	 * 
	 * @param person
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			lblBirthday.setText(person.getBirthday().toString());
			lblCity.setText(person.getCity());
			lblFirstNAme.setText(person.getFirstName());
			lblLastName.setText(person.getLastName());
			lblPostCode.setText(Integer.toString(person.getPostalCode()));
			lblStreet.setText(person.getStreet());
		} else {
			lblBirthday.setText("");
			lblCity.setText("");
			lblFirstNAme.setText("");
			lblLastName.setText("");
			lblPostCode.setText("");
			lblStreet.setText("");
		}
	}

	/**
	 * Delete a person from the list
	 */
	@FXML
	private void handleDeletePerson() {
		// get the index of the currently selected person from the list
		int selectedIndex = tblPersons.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			// remove the person from the list
			tblPersons.getItems().remove(selectedIndex);
			// clear outputs
			showPersonDetails(null);
		} else {
	        // Nothing selected -> Show new dialogue
			// create dialogue
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");
	        // show dialogue
	        alert.showAndWait();
		}
	}
}
