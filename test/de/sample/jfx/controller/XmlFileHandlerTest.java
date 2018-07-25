/**
 * 
 */
package de.sample.jfx.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.sample.jfx.model.Person;
import de.sample.jfx.model.PersonListWrapper;

/**
 * @author Bastian Bräunel
 *
 */
class XmlFileHandlerTest {

	private XmlFileHandler fileHandler = new XmlFileHandler();
	
	private Person p1;
	private Person p2;
	private PersonListWrapper wrapper;
	
	private final String TEST_DATE = "1990-10-13";
	private final String TEST_STREET = "Drofstrasse 4";
	
	private File testFile = new File("/home/santa/Dokumente/test_file.xml");
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		p1 = new Person("Alice", "Gray");
		p1.setCity("Werdau");
		p1.setPostalCode("3847");
		p1.setStreet("Bergstrasse 23");
		p1.setBirthday(TEST_DATE);
		
		p2 = new Person("Bob", "Beier");
		p2.setCity("Desau");
		p2.setPostalCode("1234");
		p2.setStreet(TEST_STREET);
		p2.setBirthday("1965-04-01");
		
		List<Person> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		
		wrapper = new PersonListWrapper();
		wrapper.setPersons(list);
	}

	@Test
	void testSave() {
		assertTrue(fileHandler.savePersonToXmlFile(testFile, wrapper));
	}
	
	@Test
	void testLoad() {
		PersonListWrapper loadedPersons = fileHandler.loadPersonsFromXML(testFile);
		
		assertNotNull(loadedPersons.getPersons());

		assertEquals(wrapper.getPersons().size(), loadedPersons.getPersons().size());
		
		assertNotNull(loadedPersons.getPersons().get(0).getFirstName());
		
		assertEquals(TEST_DATE, loadedPersons.getPersons().get(0).getBirthday());
		assertEquals(TEST_STREET, loadedPersons.getPersons().get(1).getStreet());
	}

}
