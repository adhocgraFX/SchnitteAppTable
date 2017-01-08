package schnitte;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by Hock on 06.01.2017.
 * Package schnitte.
 */
public class schnitteHelpDialogController {

    @FXML
    private WebView webViewHelp;

    private Stage dialogStage;

    // Reference to the main application.
    private MainApp mainApp;

    public schnitteHelpDialogController() {

    }

    @FXML
    private void initialize() {

        WebEngine webEngine = webViewHelp.getEngine();
        URL urlHelp = getClass().getResource("help/index-1.html");

        webEngine.load(urlHelp.toExternalForm());
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
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        dialogStage.close();
    }

    /**
     * Opens the print dialog.
     */
    @FXML
    private void handlePrint() {
        KlausurOverviewController.print(webViewHelp);
    }
}
