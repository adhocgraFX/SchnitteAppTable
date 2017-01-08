package schnitte;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Created by Hock on 13.11.2016.
 */

public class Semester {
    private final StringProperty semesterName;

    public Semester(String semesterName) {
        this.semesterName = new SimpleStringProperty(semesterName);
    }

    public String getSemesterName() {
        return semesterName.get();
    }

    public void setSemesterName(String semesterName) {
        this.semesterName.set(semesterName);
    }

    public StringProperty semesterNameProperty() {
        return semesterName;
    }

    @Override
    public String toString() {
        return getSemesterName();
    }
}
