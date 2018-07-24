package de.sample.jfx.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.sample.jfx.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Bastian Bräunel
 *
 */
public class PersonUpdateController {
	// register the TextFilds
	@FXML
	private TextField tfFirstName;
	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfStreet;
	@FXML
	private TextField tfCity;
	@FXML
	private TextField tfPostCode;
	@FXML
	private TextField tfBirthday;

	// register the buttons
	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;

	private Stage dialogStage;
	private Person person;
	private Boolean saved = false;

	// Pattern to check the format of the entered birthday
	private Pattern datePatern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");

	/**
	 * Default constructor
	 */
	public PersonUpdateController() {
	}

	/**
	 * Gets called through jfx live cycle
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person The person to edit
	 */
	public void setPerson(Person person) {
		this.person = person;
		showPersonDetails();
	}

	/**
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return
	 */
	public boolean wasSaved() {
		return saved;
	}

	/**
	 * Check all the inputs and throw a message if they are not valid
	 * 
	 * @return true if valid, false of not
	 */
	private boolean isInputValid() {

		String errorMessage = "";

		if (tfFirstName.getText() == null || tfFirstName.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (tfLastName.getText() == null || tfLastName.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if (tfStreet.getText() == null || tfStreet.getText().length() == 0) {
			errorMessage += "No valid street!\n";
		}

		if (tfPostCode.getText() == null || tfPostCode.getText().length() == 0) {
			errorMessage += "No valid postal code!\n";
		}

		if (tfCity.getText() == null || tfCity.getText().length() == 0) {
			errorMessage += "No valid city!\n";
		}

		if (tfBirthday.getText() == null || tfBirthday.getText().length() == 0) {
			errorMessage += "No valid birthday!\n";
		}
		// prepare matcher for checking the birthday with the regex
		Matcher m = datePatern.matcher(tfBirthday.getText());

		// check date input for valid form
		if (!m.matches()) {
			errorMessage += "No valid birthday. Use the format yyyy-mm-dd!\n";
		}

		// if no error message was generated then there is no failure
		// and all the input was valid
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Called when the user clicks save.
	 */
	@FXML
	private void handleUpdatePerson() {
		if (isInputValid()) {
			person.setFirstName(tfFirstName.getText());
			person.setLastName(tfLastName.getText());
			person.setStreet(tfStreet.getText());
			person.setPostalCode(tfPostCode.getText());
			person.setCity(tfCity.getText());
			person.setBirthday(LocalDate.parse(tfBirthday.getText(), DateTimeFormatter.ISO_LOCAL_DATE));

			saved = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Display the detailed informations from a given person, or clear all fields if
	 * passed null
	 * 
	 * @param person
	 */
	private void showPersonDetails() {
		tfFirstName.setText(person.getFirstName());
		tfLastName.setText(person.getLastName());
		tfStreet.setText(person.getStreet());
		tfCity.setText(person.getCity());
		tfPostCode.setText(person.getPostalCode());
		tfBirthday.setText(person.getBirthday().toString());
	}
}
