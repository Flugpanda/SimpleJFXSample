package de.sample.jfx.controller;

import de.sample.jfx.MainApp;
import de.sample.jfx.model.Person;
import javafx.fxml.FXML;
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
     * Default constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        columnFirstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        columnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
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
}
