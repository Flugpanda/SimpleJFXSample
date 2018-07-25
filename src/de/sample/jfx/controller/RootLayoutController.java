package de.sample.jfx.controller;

import java.io.File;

import de.sample.jfx.MainApp;
import de.sample.jfx.model.Person;
import de.sample.jfx.model.PersonListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

/**
 * The controller for the RootLayout
 * 
 * @author Bastian Bräunel
 *
 */
public class RootLayoutController {
	// register all menu items
	@FXML
	private MenuItem mItemNew;
	@FXML
	private MenuItem mItemOpen;
	@FXML
	private MenuItem mItemSave;
	@FXML
	private MenuItem mItemSaveAs;
	@FXML
	private MenuItem mItemClose;

	// reference to the main app
	private MainApp mainApp;

	/**
	 * Default constructor
	 */
	public RootLayoutController() {
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp The reference to the main JFX App
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		init();
	}

	/**
	 * Initializes the data
	 */
	private void init() {
		// Try to load last opened person file.
		File file = mainApp.getPersonFilePath();
		if (file != null) {
			loadData(file);
		}
		changeSaveButtonState();
	}

	@FXML
	public void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null);

		changeSaveButtonState();
	}

	/**
	 * save the current list of the persons to the current path of the xml file
	 */
	@FXML
	public void handleSave() {

		saveData(mainApp.getPersonFilePath(), mainApp.getPersonData());
		changeSaveButtonState();
	}

	/**
	 * Save the current list of the persons to the current path of the xml file
	 */
	@FXML
	public void handleSaveAs() {

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			saveData(file, mainApp.getPersonData());
			mainApp.setPersonFilePath(file);
		}

		// update the state of the save button
		changeSaveButtonState();
	}

	/**
	 * Load a list of persons from an xml file
	 */
	@FXML
	private void handleOpen() {
		// Create a new FileChooser dialogue
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		loadData(file);
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

	/**
	 * This method encapsulates the saving of the file
	 * 
	 * @param file The file with the path where the document shall be saved
	 * @param list The list with the persons to save
	 */
	private void saveData(File file, ObservableList<Person> list) {

		if (list.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("List is empty");
			alert.setContentText("The list is empty, nothing to be saved!");

			alert.showAndWait();

			return;
		}

		// create the wrapper object
		PersonListWrapper wrapper = new PersonListWrapper();
		// get the xml handler to work with the files
		XmlFileHandler fileHandler = mainApp.getXmlFileHandler();

		try {
			wrapper.setPersons(list);

			// save the file and check the result
			// if the saving is not successful throw an alert
			if (!fileHandler.savePersonToXmlFile(file, wrapper)) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Could not save data");
				alert.setContentText("Could not save data in file:\n" + file.getPath());

				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param file
	 */
	private void loadData(File file) {
		// if the file was successfully loaded, replace the list of persons inside the
		// main app
		if (file != null) {
			PersonListWrapper wrapper = mainApp.getXmlFileHandler().loadPersonsFromXML(file);

			if (wrapper != null && wrapper.getPersons() != null){
				mainApp.getPersonData().clear();
				mainApp.getPersonData().addAll(wrapper.getPersons());
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("File not vaild");
				alert.setContentText("The content from the file was not valid for this application \n\n" + "file:\n"
						+ file.getPath());

				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data in file:\n" + file.getPath());

			alert.showAndWait();
		}

		// update the state of the save button
		changeSaveButtonState();
	}

	/**
	 * Flip the menu item save if there is nothing to save
	 */
	private void changeSaveButtonState() {
		if (mainApp.getPersonFilePath() == null || mainApp.getPersonData().isEmpty()) {
			mItemSave.setDisable(true);
		} else {
			mItemSave.setDisable(false);
		}
	}
}
