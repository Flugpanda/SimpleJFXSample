package de.sample.jfx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class contains the base model of the Person
 * 
 * @author Bastian Bräunel
 *
 */
public class Person {

	// The data of the model
	// using JavaFX properties for all the fields
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private StringProperty postalCode;
	private StringProperty city;
	private StringProperty birthday;

	/**
	 * default constructor for marshalling with JABX
	 */
	private Person() {
		this.firstName = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty("");

		// Some initial dummy data, just for convenient testing.
		this.street = new SimpleStringProperty("");
		this.postalCode = new SimpleStringProperty("");
		this.city = new SimpleStringProperty("");
		this.birthday = new SimpleStringProperty("");
	}

	/**
	 * Basic constructor, that will take the persons name and fills the rest of the
	 * fields with some default values
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);

		// Some initial dummy data, just for convenient testing.
		this.street = new SimpleStringProperty("Foo street");
		this.postalCode = new SimpleStringProperty("0000");
		this.city = new SimpleStringProperty("Bar city");
		this.birthday = new SimpleStringProperty("1970-01-01");
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public String getStreet() {
		return street.get();
	}

	public void setStreet(String street) {
		this.street.set(street);
	}

	public StringProperty streetProperty() {
		return street;
	}

	public String getPostalCode() {
		return postalCode.get();
	}

	public void setPostalCode(String postalCode) {
		this.postalCode.set(postalCode);
	}

	public StringProperty postalCodeProperty() {
		return postalCode;
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public StringProperty cityProperty() {
		return city;
	}

	public StringProperty birthdayProperty() {
		return birthday;
	}
	
	public String getBirthday() {
		return birthday.get();
	}
	
	public void setBirthday(String birthday) {
		this.birthday.set(birthday);
	}
}
