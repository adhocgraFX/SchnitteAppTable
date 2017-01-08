package schnitte;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by Hock on 19.10.2016.
 * Package schnitte.
 */

public class MainApp extends Application {

    // welcher modus / welcher tab / noten oder punkte / neu oder bearbeiten
    public static boolean dialogTypeNew;
    public static String dialogTypeModus;
    public static String tabModus;
    public static boolean editFlag = false;

    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * The data as an observable list of Klausuren.
     */
    private ObservableList<Klausur> klausurData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        klausurData.add(new Klausur("Probe"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the data as an observable list of Klausuren.
     *
     * @return
     */
    public ObservableList<Klausur> getKlausurData() {
        return klausurData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SchnitteApp");

        // Set the application icon
        this.primaryStage.getIcons().add(new Image(this.getClass().getResource("icons/cut.png").toString()));
        initRootLayout();

        showKlausurOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 800, 600);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened Klausur file.
        File file = getKlausurFilePath();
        if (file != null) {
            loadKlausurDataFromFile(file);
        }
    }

    /**
     * Shows the klausur overview inside the root layout.
     */
    public void showKlausurOverview() {
        try {
            // Load klausur overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("KlausurOverview.fxml"));
            BorderPane klausurOverview = (BorderPane) loader.load();

            // Set klausur overview into the center of root layout.
            rootLayout.setCenter(klausurOverview);

            // Give the controller access to the main app.
            KlausurOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showKlausurCompareDialog(Klausur klausur) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("KlausurCompareDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Arbeit vergleichen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(true);
            dialogStage.initOwner(primaryStage);

            // Set the application icon.
            dialogStage.getIcons().add(new Image(this.getClass().getResource("icons/stat.png").toString()));
            Scene scene = new Scene(page, 800, 600);
            dialogStage.setScene(scene);

            // Set the klausur into the controller.
            KlausurCompareDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // chart initialisieren
            controller.setInitialChart(klausur);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to edit details for the specified klausur. If the user
     * clicks OK, the changes are saved into the provided klausur object and true
     * is returned.
     *
     * @param klausur the klausur object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showKlausurEditDialog(Klausur klausur) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("KlausurEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage
            System.out.println("dialogTypeNew: " + dialogTypeNew);
            Stage dialogStage = new Stage();
            if (dialogTypeNew == true) {
                dialogStage.setTitle("Neue Prüfung");
            } else {
                dialogStage.setTitle("Prüfung bearbeiten");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);

            // Set the application icon.
            if (dialogTypeNew == true) {
                dialogStage.getIcons().add(new Image(this.getClass().getResource("icons/page.png").toString()));
            } else {
                dialogStage.getIcons().add(new Image(this.getClass().getResource("icons/pencil.png").toString()));
            }
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the klausur into the controller.
            KlausurEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // klausur daten
            controller.setKlausur(klausur);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to edit details for the specified preferences. If the user
     * clicks OK, the changes are saved into the provided preferences object and true
     * is returned.
     *
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showOptionsEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("KlausurOptionsDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Einstellungen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);

            // Set the application icon.
            dialogStage.getIcons().add(new Image(this.getClass().getResource("icons/cog.png").toString()));

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the controller.
            KlausurOptionsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog to show help informations.
     */
    public void showHelpDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("SchnitteHelp.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Hilfe");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(true);
            dialogStage.initOwner(primaryStage);

            // Set the application icon.
            dialogStage.getIcons().add(new Image(this.getClass().getResource("icons/help.png").toString()));

            Scene scene = new Scene(page, 800, 600);
            dialogStage.setScene(scene);

            // Set the controller.
            schnitteHelpDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the klausur file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getKlausurFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setKlausurFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("SchnitteApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("SchnitteApp");
        }
    }

    /**
     * Loads klausur data from the specified file. The current klausur data will
     * be replaced.
     *
     * @param file
     */
    public void loadKlausurDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(KlausurListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            KlausurListWrapper wrapper = (KlausurListWrapper) um.unmarshal(file);

            klausurData.clear();
            klausurData.addAll(wrapper.getKlausuren());

            // Save the file path to the registry.
            setKlausurFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Saves the current klausur data to the specified file.
     *
     * @param file
     */
    public void saveKlausurDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(KlausurListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our klausur data.
            KlausurListWrapper wrapper = new KlausurListWrapper();
            wrapper.setKlausuren(klausurData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setKlausurFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}
