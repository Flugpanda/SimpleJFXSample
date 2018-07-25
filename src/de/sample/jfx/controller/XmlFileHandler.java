package de.sample.jfx.controller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.sample.jfx.model.PersonListWrapper;

/**
 * 
 * This class encapsulates all action for storing and loading files from the
 * system
 * 
 * @author Bastian Bräunel
 *
 */
public class XmlFileHandler {

	/**
	 * Save the given list of persons as xml file to the disk
	 * 
	 * @param file       the file that contains the file path
	 * @param personList the list with the persons
	 * @return true if successful and false if not
	 */
	public boolean savePersonToXmlFile(File file, PersonListWrapper personList) {
		if (file == null) {
			return false;
		}

		// Make sure it has the correct extension
		if (!file.getPath().endsWith(".xml")) {
			file = new File(file.getPath() + ".xml");
		}

		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Marshalling and saving XML to the file.
			m.marshal(personList, file);

			// return true after saving is done
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Load the list of persons from an xml file
	 * 
	 * @param file the file that contains the file path
	 * @return the lsit of persons
	 */
	public PersonListWrapper loadPersonsFromXML(File file) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			PersonListWrapper wrapper = (PersonListWrapper) jaxbUnmarshaller.unmarshal(file);
			return wrapper;
		} catch (Exception e) { // catches ANY exception
			System.err.println("Can't unmarshal the file: " + file.getAbsolutePath());
			e.printStackTrace();
		}

		return null;
	}
}
