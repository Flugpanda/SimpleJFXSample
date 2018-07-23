package de.sample.jfx.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	// using  JavaFX properties for all the fields
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty street;
    private IntegerProperty postalCode;
    private StringProperty city;
    private ObjectProperty<LocalDate> birthday;  

    /**
     * Basic constructor, that will take the persons name and fills the rest of the fields with some default values
     * 
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("Foo street");
        this.postalCode = new SimpleIntegerProperty(0000);
        this.city = new SimpleStringProperty("Bar city");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1990, 1, 1));
    }

	public StringProperty getFirstName() {
		return firstName;
	}

	public void setFirstName(StringProperty firstName) {
		this.firstName = firstName;
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public void setLastName(StringProperty lastName) {
		this.lastName = lastName;
	}

	public StringProperty getStreet() {
		return street;
	}

	public void setStreet(StringProperty street) {
		this.street = street;
	}

	public IntegerProperty getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(IntegerProperty postalCode) {
		this.postalCode = postalCode;
	}

	public StringProperty getCity() {
		return city;
	}

	public void setCity(StringProperty city) {
		this.city = city;
	}

	public ObjectProperty<LocalDate> getBirthday() {
		return birthday;
	}

	public void setBirthday(ObjectProperty<LocalDate> birthday) {
		this.birthday = birthday;
	}
}
