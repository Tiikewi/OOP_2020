package harjoitustyo.dokumentit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Uutista kuvaava konkreettinen luokka.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020.
 * <p>
 *
 * @author Kimi Porthan (kimi.porthan@tuni.fi),
 * Informaatioteknologian ja viestinnän tiedekunta,
 * Tampereen yliopisto.
 */

public class Uutinen extends Dokumentti {
    /**
     * Päivämäärä attribuutti uutiselle
     */

    private LocalDate päivämäärä;

    // Rakentaja
    public Uutinen(int tunniste, LocalDate pvm, String teksti) throws IllegalArgumentException {
        super(tunniste, teksti);
        päivämäärä(pvm);
    }

    // Aksessorit
    public LocalDate päivämäärä() {
        return päivämäärä;
    }

    public void päivämäärä(LocalDate päivämäärä) throws IllegalArgumentException {
        if (päivämäärä != null) {
            this.päivämäärä = päivämäärä;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Uutisen toString-korvaus
     * Lisää dokumentin tunnisteen ja tekstisällön väliin päivämäärä.
     *
     * @return Tunnisteen ja tekstisiällön väliin lisätään päivämäärä.
     */
    // korvataan toString
    @Override
    public String toString() {
        /** Erotin tunnisteen, pvm:än ja tekstin väliin. */
        final String EROTIN = "///";
        /** yliluokan toSring palautuksen splittaus. listään väliin päivämäärä*/
        String[] splittaus = super.toString().split("///");

        // lisätään erottimen ja tekstin väliin laji
        return splittaus[0] + EROTIN + päivämäärä.format(DateTimeFormatter.ofPattern("d.M.YYYY"))
                + EROTIN + splittaus[1];
    }

}
