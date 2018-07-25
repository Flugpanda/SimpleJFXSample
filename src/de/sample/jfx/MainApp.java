package de.sample.jfx;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import de.sample.jfx.controller.PersonOverviewController;
import de.sample.jfx.controller.PersonUpdateController;
import de.sample.jfx.controller.RootLayoutController;
import de.sample.jfx.controller.XmlFileHandler;
import de.sample.jfx.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class will be used to start up the JFX GUI
 * 
 * @author Bastian Bräunel
 *
 */
public class MainApp extends Application {

	// the primary stage
    private Stage primaryStage;
    // the layout for the primary stage
    private BorderPane rootLayout;
    // Working with JavaFX view classes that need to be informed about any changes made to the list of persons.
    // Therefore a special observable JFX collection is used
    private ObservableList<Person> personData;
    // XML handler for dealing with files
    private XmlFileHandler fileHandler;

    /**
     * main method to start jfx application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Custom init to initialize some data, that is not related to the view 
     */
    @Override
    public void init() throws Exception {
    	super.init();
    	personData = FXCollections.observableArrayList();
    	fileHandler = new XmlFileHandler();
//    	bootstrapData();
    }
    
    /**
     * This method is called automatically, through the jfx-live cycle model
     * launch => init => start => stop
     * 
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Simple JFX Adress App");

        // custom init of the layout of the primary stage
        initRootLayout();

        // load custom view
        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonUpdateView.fxml"));
            AnchorPane personUpdateView = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(personUpdateView);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonUpdateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.wasSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     *  Add some sample data
     */
    private void bootstrapData() {
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    /**
     * Returns the main stage.
     * 
     * @return	the primary stage of the app
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Return the list with the stored persons
     * 
     * @return	the obersable list with the persons
     */
	public ObservableList<Person> getPersonData() {
		return personData;
	}
	
	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	/**
	 * Returns the file handler
	 * 
	 * @return
	 */
	public XmlFileHandler getXmlFileHandler() {
		return fileHandler;
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setPersonFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
	    }
	}

}
