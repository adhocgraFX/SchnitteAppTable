package schnitte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.util.Arrays;

/**
 * Created by Hock on 08.12.2016.
 * Package schnitte.
 */

public class KlausurCompareDialogController {

    @FXML
    private TableView<Klausur> klausurTable;
    @FXML
    private TableColumn<Klausur, String> klausurColumn;

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    @FXML
    private BarChart<String, Number> punkteChart = new BarChart<String, Number>(xAxis, yAxis);
    private XYChart.Series series = new XYChart.Series();

    private ObservableList<String> pNames = FXCollections.observableArrayList();
    private ObservableList<Integer> pErgs = FXCollections.observableArrayList();

    private ObservableList<String> nNames = FXCollections.observableArrayList();
    private ObservableList<Integer> nErgs = FXCollections.observableArrayList();

    private Stage dialogStage;
    private Klausur klausur;
    private boolean okClicked = false;

    String cNew;
    String cOld;

    // Reference to the main application.
    private MainApp mainApp;

    public KlausurCompareDialogController() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        klausurTable.setItems(mainApp.getKlausurData());
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // Initialize the klausur table with the two columns.
        klausurColumn.setCellValueFactory(cellData -> cellData.getValue().klausurProperty());

        // Listen for selection changes and show the klausur details when changed.
        klausurTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> compareCharts(newValue));

        // init chart
        series.setName(null);
        // Create array for punkte chart
        pNames.addAll(Arrays.asList("P0", "P1", "P2", "P3", "P4", "P5", "P6", "P7",
                "P8", "P9", "P10", "P11", "P12", "P13", "P14", "P15"));
        // Create array for noten chart
        nNames.addAll(Arrays.asList("N1", "N2", "N3", "N4", "N5", "N6"));

        // Assign the pname as categories for the horizontal axis.
        xAxis.setCategories(pNames);
        // Assign the nName as categories for the horizontal axis.
        xAxis.setCategories(nNames);
    }

    public void setInitialChart(Klausur klausur) {
        if (klausur != null) {
            if(klausur.getModus().equals("punkteModus")) {
                setNewPunkteChart(klausur);

            } else if(klausur.getModus().equals("notenModus")) {
                setNewNotenChart(klausur);
            }
            cOld = klausur.getModus();
        }
    }

    private void compareCharts(Klausur klausur) {

        if (klausur != null) {

            // nur passende Prüfungen vergleichen, sonst reset chart
            cNew = klausur.getModus();
            System.out.println("cNew: " + cNew + " | cOld: " + cOld);

            if (klausur.getModus().equals("punkteModus")) {

                if (cOld.equals(cNew)) {
                    setNewPunkteChart(klausur);
                } else {
                    resetChartData();
                    setNewPunkteChart(klausur);
                }

            } else if (klausur.getModus().equals("notenModus")) {

                if (cOld.equals(cNew)) {
                    setNewNotenChart(klausur);
                } else {
                    resetChartData();
                    setNewNotenChart(klausur);
                }
            }
        }
        cOld = cNew;
    }

    /**
     *
     * @param klausur
     */
    private void setNewPunkteChart(Klausur klausur){
        this.klausur = klausur;

        XYChart.Series newPSeries = new XYChart.Series();

        // fill chart with klausur data
        newPSeries.setName(klausur.getKlausur());

        // Create new array for punkte chart data
        pErgs.addAll(Arrays.asList(klausur.getP0(), klausur.getP1(), klausur.getP2(), klausur.getP3(),
                klausur.getP4(), klausur.getP5(), klausur.getP6(), klausur.getP7(),
                klausur.getP8(), klausur.getP9(), klausur.getP10(), klausur.getP11(),
                klausur.getP12(), klausur.getP13(), klausur.getP14(), klausur.getP15()));

        // fill chart with klausur data
        for (int i = 0; i <= 15; i++) {
            newPSeries.getData().add(new XYChart.Data(pNames.get(i), pErgs.get(i)));
        }

        punkteChart.getData().addAll(newPSeries);

        // clear array
        pErgs.clear();
    }

    /**
     *
     * @param klausur
     */
    private void setNewNotenChart(Klausur klausur){
        this.klausur = klausur;

        XYChart.Series newNSeries = new XYChart.Series();

        // fill chart with klausur data
        newNSeries.setName(klausur.getKlausur());

        // Create new array for punkte chart data
        nErgs.addAll(Arrays.asList(klausur.getN1(), klausur.getN2(), klausur.getN3(),
                klausur.getN4(), klausur.getN5(), klausur.getN6()));

        // fill chart with klausur data
        for (int i = 0; i <= 5; i++) {
            newNSeries.getData().add(new XYChart.Data(nNames.get(i), nErgs.get(i)));
        }

        punkteChart.getData().addAll(newNSeries);

        // clear array
        pErgs.clear();
    }

    public void resetChartData() {
        punkteChart.getData().clear();
        punkteChart.layout();
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
        okClicked = true;
        dialogStage.close();
    }

    /**
     * Called when the user clicks reset.
     */
    @FXML
    private void handleReset() {
        resetChartData();
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
     * Opens the print dialog.
     */
    @FXML
    private void handlePrint() {
        KlausurOverviewController.print(punkteChart);
    }
}