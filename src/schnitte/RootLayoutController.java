package schnitte;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

import static schnitte.MainApp.editFlag;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * <p>
 * Created by Hock on 23.10.2016.
 */

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getKlausurData().clear();
        mainApp.setKlausurFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select data to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadKlausurDataFromFile(file);
        }
    }

    /**
     * Saves the file to the schnitte file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File schnitteFile = mainApp.getKlausurFilePath();
        if (schnitteFile != null) {
            mainApp.saveKlausurDataToFile(schnitteFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveKlausurDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SchnitteApp");

        // Get the Stage.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("icons/cut.png").toString()));

        alert.setHeaderText("Info");
        alert.setContentText("Johannes Hock \n" +
                "www.adhocgrafx.de \n" +
                "info@adhocgrafx.de");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {

        if (!editFlag) {
            System.exit(0);

        } else if (editFlag) {

            File tmpFile = mainApp.getKlausurFilePath();
            String tmpName = tmpFile.getName();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("SchnitteApp - " + tmpName);
            alert.setHeaderText("Die Datei " + tmpName + " wurde verändert!");
            alert.setContentText("Änderungen speichern?");

            ButtonType buttonTypeJa = new ButtonType("Ja");
            ButtonType buttonTypeNein = new ButtonType("Nein");
            ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeJa, buttonTypeNein, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeJa) {
                // ... user chose OK
                File personFile = mainApp.getKlausurFilePath();
                if (personFile != null) {
                    mainApp.saveKlausurDataToFile(personFile);
                } else {
                    handleSaveAs();
                }
                System.out.println("file saved ... user chose OK / editFlag: " + editFlag);
                System.exit(0);

            } else if (result.get() == buttonTypeNein) {
                // ... user chose No
                System.out.println("file not saved ... user chose No ... and exit / editFlag: " + editFlag);
                System.exit(0);

            } else if (result.get() == buttonTypeCancel) {
                // ... user chose CANCEL or closed the dialog
                System.out.println("user chose Cancel / editFlag: " + editFlag);
            }
        }
    }

    @FXML
    private void handleOptions() {
        mainApp.showOptionsEditDialog();
    }

    @FXML
    private void handleHelp() {
        mainApp.showHelpDialog();
    }
}