package harjoitustyo.dokumentit;


/**
 * Vitsiä kuvaava konkreettinen luokka.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020.
 * <p>
 *
 * @author Kimi Porthan (kimi.porthan@tuni.fi),
 * Informaatioteknologian ja viestinnän tiedekunta,
 * Tampereen yliopisto.
 */

public class Vitsi extends Dokumentti {
    /**
     * Vitsin lajia kuvaava attribuutti
     */
    private String laji;

    // Rakentaja
    public Vitsi(int tunniste, String laji, String teksti) throws IllegalArgumentException {
        super(tunniste, teksti);
        laji(laji);
    }

    // Aksessorit
    public String laji() {
        return laji;
    }

    public void laji(String laji) throws IllegalArgumentException {
        if (laji != null && (!laji.equals(""))) {
            this.laji = laji;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Vitsin toString-korvaus
     *
     * @return Lisää tunnisteen ja tekstin väliin vitsin lajin.
     */
    // korvataan toString
    @Override
    public String toString() {
        /** Erotin tunnisteen, lajin ja tekstin väliin. */
        final String EROTIN = "///";
        /** yliluokan toSring palautuksen splittaus. listään väliin vitsin laji*/
        String[] splittaus = super.toString().split("///");

        // lisätään erottimen ja tekstin väliin laji
        return splittaus[0] + EROTIN + laji + EROTIN + splittaus[1];
    }
}
