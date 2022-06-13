package harjoitustyo.kokoelma;


import harjoitustyo.apulaiset.Kokoava;
import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;
import harjoitustyo.omalista.OmaLista;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * Vastaa ohjelman logiikasta.
 * Kokoelma toteuttaa komennot omaa listaa käsittelemällä.
 */

public class Kokoelma implements Kokoava<Dokumentti> {
    /**
     * Attribuutti dokumenteille
     */
    private OmaLista<Dokumentti> dokumentit;

    // Rakentaja
    public Kokoelma() {
        dokumentit = new OmaLista<>();
    }

    // Lukeva aksessori
    public OmaLista<Dokumentti> dokumentit() {
        return dokumentit;
    }

    /**
     * Lisää kokoelmaan käyttöliittymän kautta annetun dokumentin.
     *
     * @param uusi viite lisättävään dokumenttiin.
     * @throws IllegalArgumentException jos dokumentin vertailu Comparable-rajapintaa
     *                                  käyttäen ei ole mahdollista, listalla on jo equals-metodin mukaan sama
     *                                  dokumentti eli dokumentti, jonka tunniste on sama kuin uuden dokumentin
     *                                  tai parametri on null.
     */

    @Override
    public void lisää(Dokumentti uusi) throws IllegalArgumentException {
        try {
            // Jos uusi arvo null, niin heitetään poikkeus.
            if (uusi == null) {
                throw new IllegalArgumentException("Virheellinen arvo.");
            }
            // Jos listalla on jo samalainen dokumentti, niin heiteitään poikkeus.
            for (Dokumentti dokumentti : dokumentit) {
                if (uusi.equals(dokumentti)) {
                    throw new IllegalArgumentException("Listalla on jo samalainen dokumentti.");
                }
            }
            // lisätään listalle
            dokumentit.lisää(uusi);


        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }


    /**
     * Hakee kokoelmasta dokumenttia, jonka tunniste on sama kuin parametrin
     * arvo.
     * <p>
     * Tästä metodista on paljon hyötyä Kokoelma-luokassa, koska moni ohjelman
     * komennoista yksilöi dokumentin sen tunnisteen perusteella.
     *
     * @param tunniste haettavan dokumentin tunniste.
     * @return viite löydettyyn dokumenttiin. Paluuarvo on null, jos haettavaa
     * dokumenttia ei löydetty.
     */
    @Override
    public Dokumentti hae(int tunniste) {
        // Käydään lista läpi ja etsitään samaa tunnistetta
        // Jos löytyy palautetaan viite.
        for (Dokumentti dokumentti : dokumentit) {
            if (dokumentti.tunniste() == tunniste) {
                return dokumentti;
            }
        }
        // jos ei löytynyt, palautetaan null.
        return null;
    }


    /**
     * @param dok
     * @param kumpiTyyppi
     * @return palauttaa false, jos tiedostoa ei löytynyt. Muutoin true.
     */
    // Ladataan tiedosto kokoelmaan.
    public boolean lataaDokumentti(String dok, String sulku, int kumpiTyyppi) {
        try {
            FileInputStream dokum = new FileInputStream(dok);
            FileInputStream sulkus = new FileInputStream(sulku);
            // Avataan scannerit
            Scanner dokumentti = new Scanner(dokum);


            // lisätään tiedoston sisältö kokoelmaan.
            while (dokumentti.hasNext()) {
                String[] lisattava = dokumentti.nextLine().split("///");
                int tunniste = Integer.parseInt(lisattava[0]);
                // Lisätään vitsi kokoelmaan.
                if (kumpiTyyppi == 0) {
                    lisää(new Vitsi(tunniste, lisattava[1], lisattava[2]));
                }
                // lisätään uutisia
                else if (kumpiTyyppi == 1) {
                    DateTimeFormatter muotoilija = DateTimeFormatter.ofPattern("d.M.yyyy");
                    // Muutetaan dokumentissa oleva päivämäärä LocalDate muotoon.
                    LocalDate localDate = LocalDate.parse(lisattava[1], muotoilija);
                    // lisätään uutinen kokoelmaan
                    lisää(new Uutinen(tunniste, localDate, lisattava[2]));
                }
            }
        } catch (FileNotFoundException e) {
            // Jos tiedostoa ei löydy
            System.out.println("Missing file!");
            System.out.println("Program terminated.");
            // Ei jatketa ohjelmaa
            return false;
        }
        return true;
    }

    /**
     * Poistetaan tunnisteen mukainen dokumentti kokoelmasta.
     *
     * @param tunniste käyttäjän syöttämä parametri poistettavan dokumentin tunnisteelle.
     */
    public void poistaDokumentti(int tunniste) {
        dokumentit.remove(hae(tunniste));
    }

    /**
     * Lisätään käyttäjän syöttämä dokumentti listaan, jos oikeellinen.
     *
     * @param komento käyttäjän syöttämä komento
     * @param dok     käyttäjän komentona syöttämä dokumentti
     */
    public void lisääDokumentti(String[] komento, String dok) {
        // jos ei parametriä -> error
        if (komento.length == 1) {
            System.out.println("Error!");
        } else {
            // kootaan splitattu parametri takaisin merkkijonoksi.
            String param = "";
            for (int i = 1; i < komento.length - 1; i++) {
                param += komento[i] + " ";
            }
            // viimeinen lisäys ilman välilyöntiä loppuun
            param += komento[komento.length - 1];

            // Katsotaan tiedoston nimestä onko kyseessä uutisia vai vitsejä
            String[] kumpia = dok.split("_");

            String[] tiedot = param.split("///");
            try {
                int tunniste = Integer.parseInt(tiedot[0]);

                // Jos on jo samalla tunnisteella oleva dokumentti
                if (hae(tunniste) != null) {
                    System.out.println("Error!");
                } else {
                    // jos vitsejä, lisätään vitsi ja estetään uutisten lisääminen.
                    if (kumpia[0].contains("jokes")) {
                        // kokeillaan onnistuuko dokumentista luoda uutisen. Jos ei lisätään vitsi.
                        try {
                            LocalDate localDate = LocalDate.parse(tiedot[1], DateTimeFormatter.ofPattern("d.M.yyyy"));
                            Uutinen uutinen = new Uutinen(tunniste, localDate, tiedot[2]);
                            lisää(uutinen);
                            System.out.println("Error!");
                        } catch (Exception e) {
                            lisää(new Vitsi(tunniste, tiedot[1], tiedot[2]));
                        }
                    }
                    // jos uutisia lisätään uutinen.
                    else {
                        try {
                            LocalDate localDate = LocalDate.parse(tiedot[1], DateTimeFormatter.ofPattern("d.M.yyyy"));
                            lisää(new Uutinen(tunniste, localDate, tiedot[2]));
                        } catch (Exception e) {
                            System.out.println("Error!");
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }

    /**
     * etsitään vastaavuuksia käyttäen apuna ahe-metodia
     *
     * @param komento käyttäjän syöttämä komentoparametri
     */
    public void etsiVastaavuudet(String[] komento) {
        // kootaan splitattu parametri takaisin merkkijonoksi.
        LinkedList<String> param = new LinkedList<>();
        for (int i = 1; i < komento.length; i++) {
            param.addFirst(komento[i]);
        }
        // dokumentti loop.
        for (int i = 0; i < dokumentit.size(); i++) {
            // jos täsmää, niin printataan tunniste.
            if (dokumentit.get(i).sanatTäsmäävät(param)) {
                System.out.println(dokumentit.get(i).tunniste());
            }
        }
    }

    /**
     * Poistaa dokumentisa sulkusanalistan sanat.
     * @param sulku
     * @param komento
     * @throws FileNotFoundException
     */
    public void kasitteleDokumentti(String sulku, String[] komento) throws FileNotFoundException {
        FileInputStream sulkusanat = new FileInputStream(sulku);
        // tehdään sulkusanalistasta LinkedList
        LinkedList<String> sulkusanatLista = new LinkedList<>();
        // scanneri sulkusanoille
        Scanner sulkuscanner = new Scanner(sulkusanat);
        while (sulkuscanner.hasNext()) {
            sulkusanatLista.add(sulkuscanner.nextLine());
        }
        for (int i = 0; i < dokumentit().size(); i++) {
            dokumentit().get(i).siivoa(sulkusanatLista, komento[1]);
        }
    }
}