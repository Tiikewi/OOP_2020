package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.Tietoinen;

import java.util.LinkedList;

/**
 * Abstrakti dokumentti-luokka, jolla dokumenteille yhteiset piirteet.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020.
 * <p>
 *
 * @author Kimi Porthan (kimi.porthan@tuni.fi),
 * Informaatioteknologian ja viestinnän tiedekunta,
 * Tampereen yliopisto.
 */

public abstract class Dokumentti implements Comparable<Dokumentti>, Tietoinen<Dokumentti> {
    /**
     * Dokumentin tunniste
     */
    private int tunniste;
    /**
     * Dokumentin tekstisisältö
     */
    private String teksti;

    // Rakentaja
    public Dokumentti(int tunniste, String teksti) throws IllegalArgumentException {
        tunniste(tunniste);
        teksti(teksti);
    }

    // aksessorit
    public int tunniste() {
        return tunniste;
    }

    public void tunniste(int tunniste) throws IllegalArgumentException {
        if (tunniste > 0) {
            this.tunniste = tunniste;
        } else throw new IllegalArgumentException();
    }

    public String teksti() {
        return teksti;
    }

    public void teksti(String teksti) throws IllegalArgumentException {
        if (teksti != null && (!teksti.equals(""))) {
            this.teksti = teksti;
        } else throw new IllegalArgumentException();
    }

    /**
     * Dokumentin toString-korvaus
     * palauttaa tunnisteen ja tekstisisällön erottimella("///") erotutettuna.
     *
     * @return tunniste ja teksti toisistaan eroteltuna.
     */
    // korvataan toSring
    @Override
    public String toString() {
        /** Erotin tunnisteen ja tekstin väliin. */
        final String EROTIN = "///";
        return tunniste + EROTIN + teksti;
    }

    /**
     * Korvataan equals-metodi. Jos tunnisteet ovat samat, niin true
     *
     * @param obj vertailtava olio
     * @return true, jos tunnisteet ovat samat, false, jos tunnisteet eivät ole samat.
     */
    @Override
    public boolean equals(Object obj) {
        try {
            // asetetaan olioon dokumentti-viite, että voidaan vertailla.
            Dokumentti toinen = (Dokumentti) obj;
            // Oliot ovat samat, jos tunnisteet ovat samat.

            return toinen.tunniste == tunniste;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param toinen vertailtava olio.
     * @return -1 jos vertailtava on pienenmpi, 0 jos samat, 1 jos vertailtava suurempi.
     */
    @Override
    public int compareTo(Dokumentti toinen) {
        // jos toinen-olion tunniste on pienempi, palautetaan 1.
        if (toinen.tunniste < tunniste) {
            return 1;
        }
        // jos tunnisteet yhtä suuret, palautetaan 0.
        else if (toinen.tunniste == tunniste) {
            return 0;
        }
        // jos toinen-olion tunniste suurempi, palautetaan -1.
        else {
            return -1;
        }
    }


    /**
     * Testataan löytyykö hakusanoina annetut sanat vähintään kerran tekstissä.
     *
     * @param hakusanat lista dokumentin tekstistä haettavia sanoja.
     * @return true, jos listan sanat esiintyvät tekstissä. Muuten, false.
     * @throws IllegalArgumentException
     */
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        // splitataan teksti välilyönneillä. Voidaan vertailla hakusanojen kanssa.
        String[] splitTeksti = teksti.split(" ");
        try {
            // Jos tyhjä lista heitetään poikkeus
            if (hakusanat.size() == 0) throw new IllegalArgumentException();
            boolean löytyi = false;
            for (int i = 0; i < hakusanat.size(); i++) {
                löytyi = false;
                for (int j = 0; j < splitTeksti.length; j++) {
                    if (hakusanat.get(i).equals(splitTeksti[j])) {
                        löytyi = true;
                        break;
                    }
                    löytyi = false;
                }
                // jos eka sana ei täsmännyt palautetaan heti false:
                if (!löytyi) return false;
            }

            // jos jäänyt löytynyt -> true, palautetaan true
            if (löytyi) return true;
            else return false;
        } catch (Exception e) {
            throw new IllegalArgumentException("sanatTäsmäävät error!");
        }
    }

    /**
     * Metodi poistaa ensin dokumentin tekstistä kaikki annetut välimerkit ja
     * muuntaa sitten kaikki kirjainmerkit pieniksi ja poistaa lopuksi kaikki
     * sulkusanojen esiintymät.
     *
     * @param sulkusanat lista dokumentin tekstistä poistettavia sanoja.
     * @param välimerkit dokumentin tekstistä poistettavat välimerkit merkkijonona.
     * @throws IllegalArgumentException jos  null tai tyhjä lista.
     */
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit)
            throws IllegalArgumentException {
        try {
            if (sulkusanat.size() == 0 || välimerkit.length() == 0) {
                throw new IllegalArgumentException("Tyhjä parametri siivoa-metodissa");
            }
            // splitataan joka kirjain erilleen
            String[] merkit = välimerkit.split("");
            // Poistetaan välimerkit käyttäen replace().
            for (int i = 0; i < välimerkit.length(); i++) {
                teksti = teksti.replace(merkit[i], "");

            }
            // Muutetaan kirjaimet pieniksi
            teksti = teksti.toLowerCase();

            // Poistetaan sulkusanalistalla olevat sanat tekstistä
            String[] sanat = teksti.split(" ");

            StringBuilder builder = new StringBuilder();
            for (String word : sanat) {
                if (!sulkusanat.contains(word)) {
                    builder.append(word);
                    builder.append(' ');
                }
            }
            // takaisin merkkijonoksi
            teksti = builder.toString().trim();
            // poistetaan ylim. välimerkit
            teksti = teksti.replaceAll("\\s{2,}", " ").trim();


        } catch (Exception e) {
            throw new IllegalArgumentException("Siivoamis error!");
        }


    }
}
