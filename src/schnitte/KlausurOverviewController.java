package schnitte;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static schnitte.MainApp.*;
import static schnitte.MainApp.editFlag;

/**
 * Created by Hock on 19.10.2016.
 * Package schnitte.
 */

public class KlausurOverviewController {

    @FXML
    private TableView<Klausur> klausurTable;
    @FXML
    private AnchorPane klausurDetailsPane;
    @FXML
    private TableColumn<Klausur, String> klausurColumn;
    @FXML
    private TableColumn<Klausur, String> kursColumn;
    @FXML
    private TableColumn<Klausur, String> semesterColumn;
    @FXML
    private TableColumn<Klausur, String> infoColumn;
    @FXML
    private TableColumn<Klausur, LocalDate> datumColumn;
    @FXML
    private TableColumn<Klausur, Integer> anzahlColumn;
    @FXML
    private TableColumn<Klausur, Double> schnittPunkteColumn;
    @FXML
    private TableColumn<Klausur, Double> schnittNotenColumn;
    @FXML
    private TableColumn<Klausur, Double> prozentUngenuegendColumn;

    @FXML
    private Label klausurLabel;
    @FXML
    private Label kursLabel;
    @FXML
    private Label semesterLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Label datumLabel;
    @FXML
    private Label anzahlLabel;
    @FXML
    private Label schnittPunkteLabel;
    @FXML
    private Label schnittNotenLabel;
    @FXML
    private Label prozentUngenuegendLabel;

    @FXML
    private Label klausurLabel2;
    @FXML
    private Label schnittPunkteLabel2;
    @FXML
    private Label schnittNotenLabel2;
    @FXML
    private Label prozentUngenuegendLabel2;

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    @FXML
    private BarChart<String, Number> punkteChart = new BarChart<String, Number>(xAxis, yAxis);
    private XYChart.Series series = new XYChart.Series();

    private ObservableList<String> pNames = FXCollections.observableArrayList();
    private ObservableList<Integer> pErgs = FXCollections.observableArrayList();

    private ObservableList<String> nNames = FXCollections.observableArrayList();
    private ObservableList<Integer> nErgs = FXCollections.observableArrayList();


    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public KlausurOverviewController() {

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
        kursColumn.setCellValueFactory(cellData -> cellData.getValue().kursProperty());
        semesterColumn.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());
        datumColumn.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
        anzahlColumn.setCellValueFactory(cellData -> cellData.getValue().anzahlProperty().asObject());
        schnittPunkteColumn.setCellValueFactory(cellData -> cellData.getValue().schnittPunkteProperty().asObject());
        schnittNotenColumn.setCellValueFactory(cellData -> cellData.getValue().schnittNotenProperty().asObject());
        prozentUngenuegendColumn.setCellValueFactory(cellData -> cellData.getValue().prozentUngenuegendProperty().asObject());
        infoColumn.setCellValueFactory(cellData -> cellData.getValue().infoProperty());

        // Clear klausur details.
        showKlausurDetails(null);

        // Listen for selection changes and show the klausur details when changed.
        klausurTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showKlausurDetails(newValue));

        // init chart
        series.setName(null);

        // Create array for punkte chart
        pNames.addAll(Arrays.asList("P0", "P1", "P2", "P3", "P4", "P5", "P6", "P7",
                "P8", "P9", "P10", "P11", "P12", "P13", "P14", "P15"));
        // Create array for noten chart
        nNames.addAll(Arrays.asList("N1", "N2", "N3", "N4", "N5", "N6"));

        // Assign the pName as categories for the horizontal axis.
        xAxis.setCategories(pNames);
        // Assign the nName as categories for the horizontal axis.
        xAxis.setCategories(nNames);
    }

    // textfelder füllen
    private void showKlausurDetails(Klausur klausur) {
        if (klausur != null) {
            // Fill the labels with info from the klausur object.
            klausurLabel.setText(klausur.getKlausur());
            kursLabel.setText(klausur.getKurs());
            semesterLabel.setText(klausur.getSemester());
            datumLabel.setText(DateUtil.format(klausur.getDatum()));
            anzahlLabel.setText(Integer.toString(klausur.getAnzahl()));
            schnittPunkteLabel.setText(Berechnungen.formatErgebnis(klausur.getSchnittPunkte()));
            schnittNotenLabel.setText(Berechnungen.formatErgebnis(klausur.getSchnittNoten()));
            prozentUngenuegendLabel.setText(Berechnungen.formatErgebnis(klausur.getProzentUngenuegend()));
            infoLabel.setText(klausur.getInfo());

            klausurLabel2.setText(klausur.getKlausur());
            schnittPunkteLabel2.setText(Berechnungen.formatErgebnis(klausur.getSchnittPunkte()));
            schnittNotenLabel2.setText(Berechnungen.formatErgebnis(klausur.getSchnittNoten()));
            prozentUngenuegendLabel2.setText(Berechnungen.formatErgebnis(klausur.getProzentUngenuegend()));

            // fill chart with Klausur data

            if(klausur.getModus().equals("punkteModus")) {
                resetChartData();
                setPunkteChart(klausur);

            } else if(klausur.getModus().equals("notenModus")) {
                resetChartData();
                setNotenChart(klausur);
            }

        } else {
            // Klausur is null, remove all the text.
            klausurLabel.setText("");
            kursLabel.setText("");
            semesterLabel.setText("");
            datumLabel.setText("");
            anzahlLabel.setText("");
            schnittPunkteLabel.setText("");
            schnittNotenLabel.setText("");
            prozentUngenuegendLabel.setText("");
            infoLabel.setText("");

            klausurLabel2.setText("");
            schnittPunkteLabel2.setText("");
            schnittNotenLabel2.setText("");
            prozentUngenuegendLabel2.setText("");

            // no chart data needed
        }
    }

    /**
     * chart mit klausur daten füllen
     * @param klausur
     */
    public void setPunkteChart(Klausur klausur) {

        XYChart.Series pSeries = new XYChart.Series();

        // fill chart with klausur data
        pSeries.setName(klausur.getKlausur());

        // Create new array for punkte chart data
        pErgs.addAll(Arrays.asList(klausur.getP0(), klausur.getP1(), klausur.getP2(), klausur.getP3(),
                klausur.getP4(), klausur.getP5(), klausur.getP6(), klausur.getP7(),
                klausur.getP8(), klausur.getP9(), klausur.getP10(), klausur.getP11(),
                klausur.getP12(), klausur.getP13(), klausur.getP14(), klausur.getP15()));

        // fill chart with klausur data
        for (int i = 0; i <= 15; i++) {
            pSeries.getData().add(new XYChart.Data(pNames.get(i), pErgs.get(i)));
        }

        punkteChart.getData().addAll(pSeries);

        // clear array
        pErgs.clear();
    }

    /**
     * chart mit schulaufgaben daten füllen
     * @param klausur
     */
    public void setNotenChart(Klausur klausur) {

        XYChart.Series nSeries = new XYChart.Series();

        // fill chart with klausur data
        nSeries.setName(klausur.getKlausur());

        // Create new array for punkte chart data
        nErgs.addAll(Arrays.asList(klausur.getN1(), klausur.getN2(), klausur.getN3(),
                klausur.getN4(), klausur.getN5(), klausur.getN6()));

        // fill chart with klausur data
        for (int i = 0; i <= 5; i++) {
            nSeries.getData().add(new XYChart.Data(nNames.get(i), nErgs.get(i)));
        }

        punkteChart.getData().addAll(nSeries);

        // clear array
        nErgs.clear();
    }

    public void resetChartData() {
        punkteChart.getData().clear();
        punkteChart.layout();
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteKlausur() {
        int selectedIndex = klausurTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Klausur tempKlausur = new Klausur();
            tempKlausur = klausurTable.getItems().get(selectedIndex);
            mainApp.getKlausurData().remove(tempKlausur);
            // todo je nachdem ob mit oder ohne sortierfunktion
            // klausurTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            alertNoSelection();
        }
    }

    /**
     * Called when the user clicks the new button.
     * Opens a dialog to edit details for a new klausur.
     */
    @FXML
    private void handleNewKlausur() {
        Klausur tempKlausur = new Klausur();
        dialogTypeNew = true;
        dialogTypeModus = "new";
        boolean okClicked = mainApp.showKlausurEditDialog(tempKlausur);
        if (okClicked) {
            mainApp.getKlausurData().add(tempKlausur);
        }
    }

    /**
     * Called when the user clicks the edit button.
     * Opens a dialog to edit details for the selected klausur.
     */
    @FXML
    private void handleEditKlausur() {
        Klausur selectedKlausur = klausurTable.getSelectionModel().getSelectedItem();
        dialogTypeNew = false;
        if (selectedKlausur != null) {
            dialogTypeModus = selectedKlausur.getModus();
            boolean okClicked = mainApp.showKlausurEditDialog(selectedKlausur);
            if (okClicked) {
                showKlausurDetails(selectedKlausur);
            }

        } else {
            // Nothing selected.
            alertNoSelection();
        }
    }

    /**
     * Called when the user clicks the compare button.
     * Opens a dialog to compare several klausuren
     */
    @FXML
    private void handleCompare() {
        Klausur selectedKlausur = klausurTable.getSelectionModel().getSelectedItem();
        if (selectedKlausur != null) {
            boolean okClicked = mainApp.showKlausurCompareDialog(selectedKlausur);
            if (okClicked) {
                showKlausurDetails(selectedKlausur);
            }

        } else {
            // Nothing selected.
            alertNoSelection();
        }
    }

    private void alertNoSelection() {
        // Nothing selected.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Keine Auswahl!");
        alert.setHeaderText("Keine Prüfung ausgewählt");
        alert.setContentText("Bitte wähle eine Prüfung aus der Tabelle aus.");
        alert.showAndWait();
    }

    /**
     * Opens the print dialog.
     */
    @FXML
    private void handlePrint() {
        Klausur selectedKlausur = klausurTable.getSelectionModel().getSelectedItem();
        if (selectedKlausur != null) {
            print(klausurDetailsPane);

        } else {
            // Nothing selected.
            alertNoSelection();
        }
    }

    /**
     * Opens the options dialog.
     */
    @FXML
    private void handleEinstellungen() {
        mainApp.showOptionsEditDialog();
    }

    /**
     * print methode, skaliert den druckbereich, falls notwendig
     * @param node
     */
    public static void print(final Node node)  {

        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob(printer);

        // mit MarginType kann man die Ränder bestimmen
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        final double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        final double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        final double tempscale = Math.min(scaleX, scaleY);

        // skalierfaktor und druckbarer bereich
        System.out.println("tempScale: " + tempscale);
        System.out.println("PageLayout: " + pageLayout.toString());

        Scale scale = new Scale(tempscale, tempscale);

        // skalieren, falls notwendig
        if (tempscale < 1.0) {
            node.getTransforms().add(scale);
        }

        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
            }
        }

        if (tempscale < 1.0) {
            node.getTransforms().remove(scale);
        }
    }
}
