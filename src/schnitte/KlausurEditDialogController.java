package schnitte;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.LocalDate.now;
import static schnitte.MainApp.dialogTypeModus;
import static schnitte.MainApp.tabModus;
import static schnitte.MainApp.editFlag;

import java.io.*;
import java.util.Properties;

/**
 * Created by Hock on 19.10.2016.
 * Package schnitte.
 */

public class KlausurEditDialogController {

    @FXML
    private ComboBox<Semester> comboSemester;
    private ObservableList<Semester> myComboSemesterData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Kurse> comboKurse;
    private ObservableList<Kurse> myComboKurseData = FXCollections.observableArrayList();

    @FXML
    private DatePicker pickerDate;

    // datumsformat
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private String formattedDateValue;
    private LocalDate date;

    @FXML
    private TextField klausurFeld;
    @FXML
    private TextField infoFeld;
    // Text Area output
    @FXML
    private TextArea txtErgebnis;
    // Spinner Punkte
    @FXML
    private Spinner<Integer> P0;
    @FXML
    private Spinner<Integer> P1;
    @FXML
    private Spinner<Integer> P2;
    @FXML
    private Spinner<Integer> P3;
    @FXML
    private Spinner<Integer> P4;
    @FXML
    private Spinner<Integer> P5;
    @FXML
    private Spinner<Integer> P6;
    @FXML
    private Spinner<Integer> P7;
    @FXML
    private Spinner<Integer> P8;
    @FXML
    private Spinner<Integer> P9;
    @FXML
    private Spinner<Integer> P10;
    @FXML
    private Spinner<Integer> P11;
    @FXML
    private Spinner<Integer> P12;
    @FXML
    private Spinner<Integer> P13;
    @FXML
    private Spinner<Integer> P14;
    @FXML
    private Spinner<Integer> P15;

    // Spinner Noten
    @FXML
    private Spinner<Integer> N1;
    @FXML
    private Spinner<Integer> N2;
    @FXML
    private Spinner<Integer> N3;
    @FXML
    private Spinner<Integer> N4;
    @FXML
    private Spinner<Integer> N5;
    @FXML
    private Spinner<Integer> N6;

    @FXML
    private Tab tabNoten;
    @FXML
    private Tab tabPunkte;
    @FXML
    private TabPane tabPane;

    // Tab selection
    private boolean tabSelection = false;
    private boolean initReady = false;

    private Stage dialogStage;
    private Klausur klausur;
    private boolean okClicked = false;


    public KlausurEditDialogController() {

        // todo catch exceptions evtl anpassen

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the properties value
            // Create data for Semester Combo Boxes
            myComboSemesterData.add(new Semester(prop.getProperty("s1")));
            myComboSemesterData.add(new Semester(prop.getProperty("s2")));
            myComboSemesterData.add(new Semester(prop.getProperty("s3")));
            myComboSemesterData.add(new Semester(prop.getProperty("s4")));
            myComboSemesterData.add(new Semester(prop.getProperty("s5")));
            myComboSemesterData.add(new Semester(prop.getProperty("s6")));
            myComboSemesterData.add(new Semester(prop.getProperty("s7")));
            myComboSemesterData.add(new Semester(prop.getProperty("s8")));

            // Create data for Kurse Combo Boxes
            myComboKurseData.add(new Kurse(prop.getProperty("k1")));
            myComboKurseData.add(new Kurse(prop.getProperty("k2")));
            myComboKurseData.add(new Kurse(prop.getProperty("k3")));
            myComboKurseData.add(new Kurse(prop.getProperty("k4")));
            myComboKurseData.add(new Kurse(prop.getProperty("k5")));
            myComboKurseData.add(new Kurse(prop.getProperty("k6")));
            myComboKurseData.add(new Kurse(prop.getProperty("k7")));
            myComboKurseData.add(new Kurse(prop.getProperty("k8")));

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
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // welche tabs enabled, je nach modus
        if (dialogTypeModus.equals("punkteModus")) {
            tabNoten.setDisable(true);
            tabPunkte.setDisable(false);
            tabPane.getSelectionModel().selectFirst();
            tabModus = tabPunkte.getId();

            System.out.println("dtm: punkteModus, tm: " + tabModus);

        } else if (dialogTypeModus.equals("notenModus")) {
            tabNoten.setDisable(false);
            tabPunkte.setDisable(true);
            tabPane.getSelectionModel().selectLast();
            tabModus = tabNoten.getId();

            System.out.println("dtm: notenModus, tm: " + tabModus);

        } else if (dialogTypeModus.equals("new")) {
            tabNoten.setDisable(false);
            tabPunkte.setDisable(false);
            tabPane.getSelectionModel().selectFirst();

            System.out.println("dtm: new");
        }

        // Init ComboBox items.
        comboSemester.setItems(myComboSemesterData);
        comboSemester.setEditable(true);
        comboSemester.getSelectionModel().select(0);
        comboSemester.setConverter(new StringConverter<Semester>() {
            @Override
            public String toString(Semester object) {
                return object.getSemesterName();
            }

            @Override
            public Semester fromString(String string) {
                Semester s = null;
                if (string != null) {
                    s = new Semester(string);
                }
                return s;
            }
        });

        comboKurse.setItems(myComboKurseData);
        comboKurse.setEditable(true);
        comboKurse.getSelectionModel().select(0);
        comboKurse.setConverter(new StringConverter<Kurse>() {
            @Override
            public String toString(Kurse object) {
                return object.getKursName();
            }

            @Override
            public Kurse fromString(String string) {
                Kurse k = null;
                if (string != null) {
                    k = new Kurse(string);
                }
                return k;
            }
        });

        // init pickerdate
        pickerDate.setValue(now());
        // todo besser now als null
        // pickerDate.setValue(null);

        // allgemeiner reset
        Berechnungen.punkteInstanziieren();
        Berechnungen.notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        Berechnungen.notenSynchronisieren();

        // punkte inputs reset
        resetPunkteInputValues();
        resetNotenInputValues();

        // combos, datepicker und txt ergebnis resetten
        resetInputValues();

        // kontrolle
        System.out.println("initialize ausgeführt");
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
     * Sets the klausur to be edited in the dialog.
     * getter und setter
     * @param klausur
     */
    public void setKlausur(Klausur klausur) {
        this.klausur = klausur;

        // felder setzen
        klausurFeld.setText(klausur.getKlausur());
        infoFeld.setText(klausur.getInfo());
        pickerDate.setValue(klausur.getDatum());

        // combos
        Semester tempSemester = new Semester(klausur.getSemester());
        comboSemester.setValue(tempSemester);

        Kurse tempKurse = new Kurse(klausur.getKurs());
        comboKurse.setValue(tempKurse);

        // zur kontrolle
        System.out.println(tempSemester);
        System.out.println(tempKurse);
        System.out.println(dialogTypeModus);

        // punkte
        P15.getValueFactory().setValue(klausur.getP15());
        P14.getValueFactory().setValue(klausur.getP14());
        P13.getValueFactory().setValue(klausur.getP13());
        P12.getValueFactory().setValue(klausur.getP12());
        P11.getValueFactory().setValue(klausur.getP11());
        P10.getValueFactory().setValue(klausur.getP10());
        P9.getValueFactory().setValue(klausur.getP9());
        P8.getValueFactory().setValue(klausur.getP8());
        P7.getValueFactory().setValue(klausur.getP7());
        P6.getValueFactory().setValue(klausur.getP6());
        P5.getValueFactory().setValue(klausur.getP5());
        P4.getValueFactory().setValue(klausur.getP4());
        P3.getValueFactory().setValue(klausur.getP3());
        P2.getValueFactory().setValue(klausur.getP2());
        P1.getValueFactory().setValue(klausur.getP1());
        P0.getValueFactory().setValue(klausur.getP0());

        // noten
        N1.getValueFactory().setValue(klausur.getN1());
        N2.getValueFactory().setValue(klausur.getN2());
        N3.getValueFactory().setValue(klausur.getN3());
        N4.getValueFactory().setValue(klausur.getN4());
        N5.getValueFactory().setValue(klausur.getN5());
        N6.getValueFactory().setValue(klausur.getN6());

        // die weiteren Daten werden über die Berechnung erzeugt
        if (dialogTypeModus.equals("punkteModus")) {
            punkteBerechnen();
            System.out.println("getter: Punkte Berechnen verwendet, modus punkteModus");

        } else if (dialogTypeModus.equals("notenModus")) {
            notenBerechnen();
            System.out.println("getter: Noten Berechnen verwendet, modus notenModus");

        } else if (dialogTypeModus.equals("new")) {
            punkteBerechnen();
            System.out.println("getter: Punkte Berechnen verwendet, modus new");
            notenBerechnen();
            System.out.println("getter: Noten Berechnen verwendet, modus new");
        }
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
        if (isInputValid()) {
            klausur.setKlausur(klausurFeld.getText());
            klausur.setInfo(infoFeld.getText());
            klausur.setDatum(pickerDate.getValue());
            klausur.setSemester(comboSemester.getSelectionModel().getSelectedItem().toString());
            klausur.setKurs(comboKurse.getSelectionModel().getSelectedItem().toString());

            // wann welcher modus
            if (tabModus.equals("tabPunkte")) {
                // modus setzen
                klausur.setModus("punkteModus");
                // punkte
                klausur.setP15(P15.getValueFactory().getValue());
                klausur.setP14(P14.getValueFactory().getValue());
                klausur.setP13(P13.getValueFactory().getValue());
                klausur.setP12(P12.getValueFactory().getValue());
                klausur.setP11(P11.getValueFactory().getValue());
                klausur.setP10(P10.getValueFactory().getValue());
                klausur.setP9(P9.getValueFactory().getValue());
                klausur.setP8(P8.getValueFactory().getValue());
                klausur.setP7(P7.getValueFactory().getValue());
                klausur.setP6(P6.getValueFactory().getValue());
                klausur.setP5(P5.getValueFactory().getValue());
                klausur.setP4(P4.getValueFactory().getValue());
                klausur.setP3(P3.getValueFactory().getValue());
                klausur.setP2(P2.getValueFactory().getValue());
                klausur.setP1(P1.getValueFactory().getValue());
                klausur.setP0(P0.getValueFactory().getValue());

                // die weiteren Daten werden über die Berechnung erzeugt
                punkteBerechnen();
                System.out.println("setter: Punkte Berechnen verwendet");

            } else if (tabModus.equals("tabNoten")) {
                // modus setzen
                klausur.setModus("notenModus");
                // noten
                klausur.setN1(N1.getValueFactory().getValue());
                klausur.setN2(N2.getValueFactory().getValue());
                klausur.setN3(N3.getValueFactory().getValue());
                klausur.setN4(N4.getValueFactory().getValue());
                klausur.setN5(N5.getValueFactory().getValue());
                klausur.setN6(N6.getValueFactory().getValue());

                // die weiteren Daten werden über die Berechnung erzeugt
                notenBerechnen();
                System.out.println("setter: Noten Berechnen verwendet");
            }

            okClicked = true;
            editFlag = true;
            System.out.println("editFlag: " + editFlag);

            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
        System.out.println("editFlag: " + editFlag);
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (klausurFeld.getText() == null || klausurFeld.getText().length() == 0) {
            // klausur bezeichnung sollte unbedingt sein ok?
            errorMessage += "Keine gültige Bezeichnung!\n";
        }
        if (comboSemester.getValue() == null) {
            // Semester-Feld wird standardmäßig auf 11/1 gesetzt, ansonsten
            errorMessage += "Kein gültiges Semester!\n";
        }
        if (comboKurse.getValue() == null) {
            // Kurs-Feld wird standardmäßig auf 1ku1 gesetzt, ansonsten
            errorMessage += "Kein gültiger Kurs!\n";
        }
        // todo wie soll pickerdate gesetzt werden?
        if (pickerDate.getValue() == null) {
            // auf null oder today?
            // pickerDate.setValue(now());
            pickerDate.setPromptText("dd.mm.yyyy");
            pickerDate.setValue(null);
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            stage.getIcons().add(new Image(this.getClass().getResource("icons/book.png").toString()));
            alert.setTitle("Ungültige Felder");
            alert.setHeaderText("Bitte korrigiere die ungültigen Felder");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void actionBerechnen(ActionEvent actionEvent) {
        // berechnen code
        if (tabModus.equals("tabPunkte")) {
            punkteBerechnen();
            System.out.println("Punkte Berechnen button has been clicked");
        } else if (tabModus.equals("tabNoten")) {
            notenBerechnen();
            System.out.println("Noten Berechnen button has been clicked");
        }
    }

    @FXML
    private void actionReset(ActionEvent actionEvent) {
        // reset code
        if (tabModus.equals("tabPunkte")) {
            punkteReset();
            System.out.println("Punkte Reset button has been clicked");
        } else if (tabModus.equals("tabNoten")) {
            notenReset();
            System.out.println("Noten Reset button has been clicked");
        }
    }

    @FXML
    private void actionChooseDate(ActionEvent actionEvent) {
        LocalDate datum = pickerDate.getValue();
        System.out.println("pickerDate Action selected: " + pickerDate.getValue() + " = " + datum);
        Berechnungen.datum = pickerDate.getValue();
        System.out.println("pickerDate Action selected: " + Berechnungen.datum);
    }

    @FXML
    private void actionChooseKurs(ActionEvent actionEvent) {
        Kurse selectedKurs = comboKurse.getSelectionModel().getSelectedItem();
        System.out.println("comboKurse Action (selected: " + selectedKurs.toString() + ")");
    }

    @FXML
    private void actionChooseSemester(ActionEvent actionEvent) {
        Semester selectedSemester = comboSemester.getSelectionModel().getSelectedItem();
        System.out.println("comboSemester Action (selected: " + selectedSemester.toString() + ")");
    }

    /**
     * Methode: noten-anzahlen aus jspinner einlesen und noten-array [i][1] damit füllen
     */
    private void getNotenAnzahlen() {
        // Noten in array einlesen
        Berechnungen.noten[6][1] = N6.getValue();
        Berechnungen.noten[5][1] = N5.getValue();
        Berechnungen.noten[4][1] = N4.getValue();
        Berechnungen.noten[3][1] = N3.getValue();
        Berechnungen.noten[2][1] = N2.getValue();
        Berechnungen.noten[1][1] = N1.getValue();
    }

    /**
     * Methode: punkte-anzahlen aus jspinner einlesen und punkte-array [i][1] damit füllen
     */
    private void getPunkteAnzahlen() {
        // zweiten wert mit punkte-anzahlen füllen
        Berechnungen.punkte[15][1] = P15.getValue();
        Berechnungen.punkte[14][1] = P14.getValue();
        Berechnungen.punkte[13][1] = P13.getValue();
        Berechnungen.punkte[12][1] = P12.getValue();
        Berechnungen.punkte[11][1] = P11.getValue();
        Berechnungen.punkte[10][1] = P10.getValue();
        Berechnungen.punkte[9][1] = P9.getValue();
        Berechnungen.punkte[8][1] = P8.getValue();
        Berechnungen.punkte[7][1] = P7.getValue();
        Berechnungen.punkte[6][1] = P6.getValue();
        Berechnungen.punkte[5][1] = P5.getValue();
        Berechnungen.punkte[4][1] = P4.getValue();
        Berechnungen.punkte[3][1] = P3.getValue();
        Berechnungen.punkte[2][1] = P2.getValue();
        Berechnungen.punkte[1][1] = P1.getValue();
        Berechnungen.punkte[0][1] = P0.getValue();
    }

    // Methode alle textfelder resetten, auch combos und datepicker
    private void resetInputValues() {
        klausurFeld.setText(null);
        infoFeld.setText(null);
        txtErgebnis.setText(null);
        comboSemester.getSelectionModel().select(0);
        comboKurse.getSelectionModel().select(0);
        pickerDate.setValue(now());
    }

    // Methode Punkte Reset
    private void punkteReset() {
        // allgemeiner reset
        Berechnungen.punkteInstanziieren();
        Berechnungen.notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        Berechnungen.notenSynchronisieren();

        // punkte inputs reset
        resetPunkteInputValues();

        // combos, datepicker und txt ergebnis reset
        resetInputValues();
    }

    // Method action notenReset
    private void notenReset() {
        // allgemeiner reset
        Berechnungen.punkteInstanziieren();
        Berechnungen.notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        Berechnungen.notenSynchronisieren();

        // noten inputs resetten
        resetNotenInputValues();

        // combos, datepicker und txt ergebnis resetten
        resetInputValues();
    }

    // Methode noten inputs reset
    private void resetNotenInputValues() {
        // inputs auf 0 setzen
        N1.getValueFactory().setValue(0);
        N2.getValueFactory().setValue(0);
        N3.getValueFactory().setValue(0);
        N4.getValueFactory().setValue(0);
        N5.getValueFactory().setValue(0);
        N6.getValueFactory().setValue(0);
    }


    // Methode punkte inputs reset
    private void resetPunkteInputValues() {
        // inputs auf 0 setzen
        P15.getValueFactory().setValue(0);
        P14.getValueFactory().setValue(0);
        P13.getValueFactory().setValue(0);
        P12.getValueFactory().setValue(0);
        P11.getValueFactory().setValue(0);
        P10.getValueFactory().setValue(0);
        P9.getValueFactory().setValue(0);
        P8.getValueFactory().setValue(0);
        P7.getValueFactory().setValue(0);
        P6.getValueFactory().setValue(0);
        P5.getValueFactory().setValue(0);
        P4.getValueFactory().setValue(0);
        P3.getValueFactory().setValue(0);
        P2.getValueFactory().setValue(0);
        P1.getValueFactory().setValue(0);
        P0.getValueFactory().setValue(0);
    }

    // Methode Punkte berechnen
    private void punkteBerechnen() {

        // punkte-anzahlen aus jspinner einlesen und punkte-array [i][1] damit füllen
        getPunkteAnzahlen();

        // punkte-anzahlen zu noten-anzahlen
        Berechnungen.notenSynchronisieren();

        // temporäre Berechnungen
        Berechnungen.tempPunkteBerechnungen();

        // Berechnungen
        Berechnungen.klausurenAnzahlBerechnen();

        if (Berechnungen.klausurenAnzahlBerechnen() != 0) {
            // nur wenn werte vorliegen
            Berechnungen.punkteSchnittBerechnen(Berechnungen.temppunktesumme, Berechnungen.anzahl);
            Berechnungen.notenSchnittBerechnen(Berechnungen.tempnotensumme, Berechnungen.anzahl);
            Berechnungen.ungenuegendBerechnen(Berechnungen.tempungenuegend, Berechnungen.anzahl);

            // klausur berechnete daten setzen
            klausur.setAnzahl(Berechnungen.anzahl);
            klausur.setSchnittPunkte(Berechnungen.punkteschnitt);
            klausur.setSchnittNoten(Berechnungen.notenschnitt);
            klausur.setProzentUngenuegend(Berechnungen.ungenuegend);

            // Ergebnis
            txtErgebnis.setText(null);
            txtErgebnis.setText("Ergebnis:" + "\n" +
                    "Anzahl der Arbeiten: " + klausur.getAnzahl() + "\n" +
                    "Punkteschnitt: " + Berechnungen.formatErgebnis(klausur.getSchnittPunkte()) + "\n" +
                    "Notenschnitt: " + Berechnungen.formatErgebnis(klausur.getSchnittNoten()) + "\n" +
                    "Arbeiten mit mangelhaft bzw. ungenügend: " + Berechnungen.formatErgebnis(klausur.getProzentUngenuegend()) + "%");

        } else if (Berechnungen.klausurenAnzahlBerechnen() == 0) {
            // klausur ergebnis daten auf null setzen
            klausur.setAnzahl(0);
            klausur.setSchnittPunkte(0);
            klausur.setSchnittNoten(0);
            klausur.setProzentUngenuegend(0);

            txtErgebnis.setText(null);
            txtErgebnis.setText("Es liegt noch kein Ergebnis vor.");
        }

        // allgemeiner reset
        Berechnungen.punkteInstanziieren();
        Berechnungen.notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        Berechnungen.notenSynchronisieren();
    }

    // Methode Noten berechnen
    private void notenBerechnen() {
        // noten-anzahlen aus jspinner einlesen und punkte-array [i][1] damit füllen
        getNotenAnzahlen();

        // temporäre Berechnungen
        Berechnungen.tempNotenBerechnungen();

        // Berechnungen
        Berechnungen.schulaufgabenAnzahlBerechnen();

        if (Berechnungen.schulaufgabenAnzahlBerechnen() != 0) {
            // nur wenn werte vorliegen
            Berechnungen.notenSchnittBerechnen(Berechnungen.tempnotensumme, Berechnungen.anzahl);
            Berechnungen.ungenuegendBerechnen(Berechnungen.tempungenuegend, Berechnungen.anzahl);

            // klausur berechnete daten setzen
            klausur.setAnzahl(Berechnungen.anzahl);
            klausur.setSchnittNoten(Berechnungen.notenschnitt);
            klausur.setProzentUngenuegend(Berechnungen.ungenuegend);

            // Ergebnis
            txtErgebnis.setText(null);
            txtErgebnis.setText("Ergebnis:" + "\n" +
                    "Anzahl der Arbeiten: " + klausur.getAnzahl() + "\n" +
                    "Notenschnitt: " + Berechnungen.formatErgebnis(klausur.getSchnittNoten()) + "\n" +
                    "Arbeiten mit mangelhaft bzw. ungenügend: " + Berechnungen.formatErgebnis(klausur.getProzentUngenuegend()) + "%");

        } else if (Berechnungen.schulaufgabenAnzahlBerechnen() == 0) {
            // klausur ergebnis daten auf null setzen
            klausur.setAnzahl(0);
            klausur.setSchnittNoten(0);
            klausur.setProzentUngenuegend(0);

            txtErgebnis.setText(null);
            txtErgebnis.setText("Es liegt noch kein Ergebnis vor.");
        }

        // allgemeiner reset
        Berechnungen.punkteInstanziieren();
        Berechnungen.notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        Berechnungen.notenSynchronisieren();
    }

    @FXML
    void tabNotenSelected(Event event) {
        if (checkTab()) {
            tabModus = tabNoten.getId();
            System.out.println(tabModus);
        }

        if (!checkInit()) {
            punkteReset();
            System.out.println("Punktereset nicht bei init");
        }
    }

    @FXML
    void tabPunkteSelected(Event event) {
        if (checkTab()) {
            tabModus = tabPunkte.getId();
            System.out.println(tabModus);
        }

        // todo hier noten reset
        if (!checkInit()) {
            notenReset();
            System.out.println("Notenreset nicht bei init");
        }
    }

    private boolean checkTab() {
        tabSelection = !tabSelection;
        return tabSelection;
    }

    private boolean checkInit() {
        initReady = !initReady;
        return initReady;
    }
}
