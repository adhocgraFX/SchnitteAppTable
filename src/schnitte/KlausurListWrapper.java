package schnitte;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Hock on 13.11.2016.
 */

@XmlRootElement(name = "klausuren")

public class KlausurListWrapper {

    private List<Klausur> klausuren;

    @XmlElement(name = "klausur")
    public List<Klausur> getKlausuren() {
        return klausuren;
    }

    public void setKlausuren(List<Klausur> klausuren) {
        this.klausuren = klausuren;
    }
}
