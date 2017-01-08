package schnitte;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.util.Properties;


/**
 * Created by Hock on 05.01.2017.
 * Package schnitte.
 */
public class KlausurOptionsDialogController {

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private TextField K1;
    @FXML
    private TextField K2;
    @FXML
    private TextField K3;
    @FXML
    private TextField K4;
    @FXML
    private TextField K5;
    @FXML
    private TextField K6;
    @FXML
    private TextField K7;
    @FXML
    private TextField K8;

    @FXML
    private TextField S1;
    @FXML
    private TextField S2;
    @FXML
    private TextField S3;
    @FXML
    private TextField S4;
    @FXML
    private TextField S5;
    @FXML
    private TextField S6;
    @FXML
    private TextField S7;
    @FXML
    private TextField S8;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // todo catch exceptions evtl anpassen

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the properties value
            K1.setText(prop.getProperty("k1"));
            K2.setText(prop.getProperty("k2"));
            K3.setText(prop.getProperty("k3"));
            K4.setText(prop.getProperty("k4"));
            K5.setText(prop.getProperty("k5"));
            K6.setText(prop.getProperty("k6"));
            K7.setText(prop.getProperty("k7"));
            K8.setText(prop.getProperty("k8"));

            S1.setText(prop.getProperty("s1"));
            S2.setText(prop.getProperty("s2"));
            S3.setText(prop.getProperty("s3"));
            S4.setText(prop.getProperty("s4"));
            S5.setText(prop.getProperty("s5"));
            S6.setText(prop.getProperty("s6"));
            S7.setText(prop.getProperty("s7"));
            S8.setText(prop.getProperty("s8"));

            System.out.println(prop.getProperty("k1"));
            System.out.println(prop.getProperty("k2"));
            System.out.println(prop.getProperty("s1"));
            System.out.println(prop.getProperty("s2"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     * setter
     */
    @FXML
    private void handleOk() {

        // todo catch exceptions evtl anpassen

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("k1", K1.getText());
            prop.setProperty("k2", K2.getText());
            prop.setProperty("k3", K3.getText());
            prop.setProperty("k4", K4.getText());
            prop.setProperty("k5", K5.getText());
            prop.setProperty("k6", K6.getText());
            prop.setProperty("k7", K7.getText());
            prop.setProperty("k8", K8.getText());

            prop.setProperty("s1", S1.getText());
            prop.setProperty("s2", S2.getText());
            prop.setProperty("s3", S3.getText());
            prop.setProperty("s4", S4.getText());
            prop.setProperty("s5", S5.getText());
            prop.setProperty("s6", S6.getText());
            prop.setProperty("s7", S7.getText());
            prop.setProperty("s8", S8.getText());

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop.getProperty("k1"));
            System.out.println(prop.getProperty("k2"));
            System.out.println(prop.getProperty("s1"));
            System.out.println(prop.getProperty("s2"));

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        okClicked = true;
        dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}