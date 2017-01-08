package schnitte;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Hock on 13.11.2016.
 */

public class Kurse {
    private final StringProperty kursName;

    public Kurse(String kursName) {
        this.kursName = new SimpleStringProperty(kursName);
    }

    public String getKursName() {
        return kursName.get();
    }

    public void setkursName(String kursName) {
        this.kursName.set(kursName);
    }

    public StringProperty kursNameProperty() {
        return kursName;
    }

    @Override
    public String toString() {
        return getKursName();
    }
}
