package schnitte;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Created by Hock on 13.11.2016.
 */

public class PunkteName {
    private final StringProperty punkteName;

    public PunkteName(String punkteName) {
        this.punkteName = new SimpleStringProperty(punkteName);
    }

    public String getPunkteName() {
        return punkteName.get();
    }

    public void setPunkteName(String punkteName) {
        this.punkteName.set(punkteName);
    }

    public StringProperty punkteNameProperty() {
        return punkteName;
    }

    @Override
    public String toString() {
        return getPunkteName();
    }
}
