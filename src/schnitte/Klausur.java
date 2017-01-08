package schnitte;

import java.time.LocalDate;
import javafx.beans.property.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Hock on 13.11.2016.
 */

public class Klausur {

    // allgemeine daten
    private final StringProperty klausur;
    private final StringProperty kurs;
    private final StringProperty semester;
    private final StringProperty info;
    private final ObjectProperty<LocalDate> datum;
    private final StringProperty modus;

    // schnitte
    private final IntegerProperty anzahl;
    private final DoubleProperty schnittPunkte;
    private final DoubleProperty schnittNoten;
    private final DoubleProperty prozentUngenuegend;

    // Punkte
    private final IntegerProperty p15;
    private final IntegerProperty p14;
    private final IntegerProperty p13;
    private final IntegerProperty p12;
    private final IntegerProperty p11;
    private final IntegerProperty p10;
    private final IntegerProperty p9;
    private final IntegerProperty p8;
    private final IntegerProperty p7;
    private final IntegerProperty p6;
    private final IntegerProperty p5;
    private final IntegerProperty p4;
    private final IntegerProperty p3;
    private final IntegerProperty p2;
    private final IntegerProperty p1;
    private final IntegerProperty p0;

    // Noten
    private final IntegerProperty n1;
    private final IntegerProperty n2;
    private final IntegerProperty n3;
    private final IntegerProperty n4;
    private final IntegerProperty n5;
    private final IntegerProperty n6;

    /**
     * Default constructor.
     */
    public Klausur() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param klausur
     */
    public Klausur(String klausur) {
        this.klausur = new SimpleStringProperty(klausur);
        // initial dummy data: Kurs, Semester, Datum, Modus:
        this.kurs = new SimpleStringProperty("1ku1");
        this.semester = new SimpleStringProperty("11/1");
        this.info = new SimpleStringProperty("");
        this.datum = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        // noten- oder punkte-modus
        this.modus = new SimpleStringProperty("");
        // initial Berechnungen:
        this.anzahl = new SimpleIntegerProperty(0);
        this.schnittPunkte = new SimpleDoubleProperty(0);
        this.schnittNoten = new SimpleDoubleProperty(0);
        this.prozentUngenuegend = new SimpleDoubleProperty(0);
        // initial punkte-daten:
        this.p15 = new SimpleIntegerProperty(0);
        this.p14 = new SimpleIntegerProperty(0);
        this.p13 = new SimpleIntegerProperty(0);
        this.p12 = new SimpleIntegerProperty(0);
        this.p11 = new SimpleIntegerProperty(0);
        this.p10 = new SimpleIntegerProperty(0);
        this.p9 = new SimpleIntegerProperty(0);
        this.p8 = new SimpleIntegerProperty(0);
        this.p7 = new SimpleIntegerProperty(0);
        this.p6 = new SimpleIntegerProperty(0);
        this.p5 = new SimpleIntegerProperty(0);
        this.p4 = new SimpleIntegerProperty(0);
        this.p3 = new SimpleIntegerProperty(0);
        this.p2 = new SimpleIntegerProperty(0);
        this.p1 = new SimpleIntegerProperty(0);
        this.p0 = new SimpleIntegerProperty(0);
        // initial noten-daten:
        this.n1 = new SimpleIntegerProperty(0);
        this.n2 = new SimpleIntegerProperty(0);
        this.n3 = new SimpleIntegerProperty(0);
        this.n4 = new SimpleIntegerProperty(0);
        this.n5 = new SimpleIntegerProperty(0);
        this.n6 = new SimpleIntegerProperty(0);
    }

    public String getKlausur() {
        return klausur.get();
    }
    public void setKlausur(String klausur) {
        this.klausur.set(klausur);
    }
    public StringProperty klausurProperty() {
        return klausur;
    }

    public String getKurs() {
        return kurs.get();
    }
    public void setKurs(String kurs) {
        this.kurs.set(kurs);
    }
    public StringProperty kursProperty() {
        return kurs;
    }

    public String getSemester() {
        return semester.get();
    }
    public void setSemester(String semester) {
        this.semester.set(semester);
    }
    public StringProperty semesterProperty() {
        return semester;
    }

    public String getInfo() {
        return info.get();
    }
    public void setInfo(String info) {
        this.info.set(info);
    }
    public StringProperty infoProperty() {
        return info;
    }

    public String getModus() {
        return modus.get();
    }
    public void setModus(String modus) {
        this.modus.set(modus);
    }
    public StringProperty modusProperty() {
        return modus;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDatum() {
        return datum.get();
    }
    public void setDatum(LocalDate datum) {
        this.datum.set(datum);
    }
    public ObjectProperty<LocalDate> datumProperty() {
        return datum;
    }

    public int getAnzahl() {
        return anzahl.get();
    }
    public void setAnzahl(int anzahl) {
        this.anzahl.set(anzahl);
    }
    public IntegerProperty anzahlProperty() {
        return anzahl;
    }

    public double getSchnittPunkte() {
        return schnittPunkte.get();
    }
    public void setSchnittPunkte(double schnittPunkte) {
        this.schnittPunkte.set(schnittPunkte);
    }
    public DoubleProperty schnittPunkteProperty() {
        return schnittPunkte;
    }

    public double getSchnittNoten() {
        return schnittNoten.get();
    }
    public void setSchnittNoten(double schnittNoten) {
        this.schnittNoten.set(schnittNoten);
    }
    public DoubleProperty schnittNotenProperty() {
        return schnittNoten;
    }

    public double getProzentUngenuegend() {
        return prozentUngenuegend.get();
    }
    public void setProzentUngenuegend(double prozentUngenuegend) {
        this.prozentUngenuegend.set(prozentUngenuegend);
    }
    public DoubleProperty prozentUngenuegendProperty() {
        return prozentUngenuegend;
    }

    public int getP15() {
        return p15.get();
    }
    public void setP15(int p15) {
        this.p15.set(p15);
    }
    public IntegerProperty p15Property() {
        return p15;
    }

    public int getP14() {
        return p14.get();
    }
    public void setP14(int p14) {
        this.p14.set(p14);
    }
    public IntegerProperty p14Property() {
        return p14;
    }

    public int getP13() {
        return p13.get();
    }
    public void setP13(int p13) {
        this.p13.set(p13);
    }
    public IntegerProperty p13Property() {
        return p13;
    }

    public int getP12() {
        return p12.get();
    }
    public void setP12(int p12) {
        this.p12.set(p12);
    }
    public IntegerProperty p12Property() {
        return p12;
    }

    public int getP11() {
        return p11.get();
    }
    public void setP11(int p11) {
        this.p11.set(p11);
    }
    public IntegerProperty p11Property() {
        return p11;
    }

    public int getP10() {
        return p10.get();
    }
    public void setP10(int p10) {
        this.p10.set(p10);
    }
    public IntegerProperty p10Property() {
        return p10;
    }

    public int getP9() {
        return p9.get();
    }
    public void setP9(int p9) {
        this.p9.set(p9);
    }
    public IntegerProperty p9Property() {
        return p9;
    }

    public int getP8() {
        return p8.get();
    }
    public void setP8(int p8) {
        this.p8.set(p8);
    }
    public IntegerProperty p8Property() {
        return p8;
    }

    public int getP7() {
        return p7.get();
    }
    public void setP7(int p7) {
        this.p7.set(p7);
    }
    public IntegerProperty p7Property() {
        return p7;
    }

    public int getP6() {
        return p6.get();
    }
    public void setP6(int p6) {
        this.p6.set(p6);
    }
    public IntegerProperty p6Property() {
        return p6;
    }

    public int getP5() {
        return p5.get();
    }
    public void setP5(int p5) {
        this.p5.set(p5);
    }
    public IntegerProperty p5Property() {
        return p5;
    }

    public int getP4() {
        return p4.get();
    }
    public void setP4(int p4) {
        this.p4.set(p4);
    }
    public IntegerProperty p4Property() {
        return p4;
    }

    public int getP3() {
        return p3.get();
    }
    public void setP3(int p3) {
        this.p3.set(p3);
    }
    public IntegerProperty p3Property() {
        return p3;
    }

    public int getP2() {
        return p2.get();
    }
    public void setP2(int p2) {
        this.p2.set(p2);
    }
    public IntegerProperty p2Property() {
        return p2;
    }

    public int getP1() {
        return p1.get();
    }
    public void setP1(int p1) {
        this.p1.set(p1);
    }
    public IntegerProperty p1Property() {
        return p1;
    }

    public int getP0() {
        return p0.get();
    }
    public void setP0(int p0) {
        this.p0.set(p0);
    }
    public IntegerProperty p0Property() {
        return p0;
    }

    public int getN1() {
        return n1.get();
    }
    public void setN1(int n1) {
        this.n1.set(n1);
    }
    public IntegerProperty n1Property() {
        return n1;
    }

    public int getN2() {
        return n2.get();
    }
    public void setN2(int n2) {
        this.n2.set(n2);
    }
    public IntegerProperty n2Property() {
        return n2;
    }

    public int getN3() {
        return n3.get();
    }
    public void setN3(int n3) {
        this.n3.set(n3);
    }
    public IntegerProperty n3Property() {
        return n3;
    }

    public int getN4() {
        return n4.get();
    }
    public void setN4(int n4) {
        this.n4.set(n4);
    }
    public IntegerProperty n4Property() {
        return n4;
    }

    public int getN5() {
        return n5.get();
    }
    public void setN5(int n5) {
        this.n5.set(n5);
    }
    public IntegerProperty n5Property() {
        return n5;
    }

    public int getN6() {
        return n6.get();
    }
    public void setN6(int n6) {
        this.n6.set(n6);
    }
    public IntegerProperty n6Property() {
        return n6;
    }
}
