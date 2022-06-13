package harjoitustyo.kayttoliittyma;


import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.kokoelma.Kokoelma;

import java.util.Scanner;

/**
 * Vastuussa käyttäjän kanssa käytävästä vuorovaikutuksesta ja tulosteista.
 */

public class Kayttoliittyma {
    /**
     * sulkusanalistan sisäältävä attribuutti.
     */
    private String sulku;
    /**
     * dokumentin sijainnin ja nien sisältävä attribuutti.
     */
    private String dok;

    /**
     * 0 kyseessä vitsi-tiedosto, 1, niin kyseessä uutis-tiedosto
     */
    int kumpiTyyppi;

    /**
     * Pääsilmukan jatkamisesta kertova attribuutti
     */
    private boolean jatka = true;

    /**
     * attribuutti tiedolle onko kaiutus päällä vai ei.
     */
    private boolean kaiutus = false;

    // scanneri käyttäjän syötteille
    Scanner komentosc = new Scanner(System.in);

    /**
     * Rakentajassa testaus onko oikea määrä parametrejä
     *
     * @param args käyttäjän käynnistäessä antamat komentoriviparametrit
     */
    public Kayttoliittyma(String[] args) {
        System.out.println("Welcome to L.O.T.");
        if (args.length != 2) {
            System.out.println("Wrong number of command-line arguments!");
            System.out.println("Program terminated.");
            jatka = false;
        } else {
            this.dok = args[0];
            this.sulku = args[1];

        }

    }


    public void paasilmukka() {

        if (jatka) {
            // onko kyseessä uutis- vai vitsitiedosto
            String[] kmp = dok.split("_");
            if (kmp[0].equals("jokes")) {
                kumpiTyyppi = 0;
            } else if (kmp[0].equals("news")) {
                kumpiTyyppi = 1;
            }
            // luodaan kokoelma-olio ja ladataan dokumentti kokoelmaan.
            Kokoelma kokoelma = new Kokoelma();
            jatka = kokoelma.lataaDokumentti(dok, sulku, kumpiTyyppi);


            // Pääsilmukka ajoon, jos ei ole tullut esteitä.
            while (jatka) {

                System.out.println("Please, enter a command:");
                // splitataan käyttäjän komento, niin päästään mahdollisiin parametreihin helposti käsiksi
                /**
                 * Käyttäjän antama komento splitattuna
                 */
                String kk = komentosc.nextLine();
                String[] komento = kk.split(" ");

                // Jos kaiutus päällä toistetaan komento
                if (kaiutus) {
                    System.out.println(kk);

                    // echo toistetaan aina
                } else if (komento[0].equals("echo")) {
                    System.out.println("echo");
                }

                // PRINT
                switch (komento[0]) {
                    case "print":
                        // Jos liikaa parametrejä
                        if (komento.length > 2) {
                            System.out.println("Error!");
                        }
                        // tulostetaan parametrin mukainen dokumentti.
                        else if (komento.length == 2) {
                            try {
                                int tunniste = Integer.parseInt(komento[1]);


                                Dokumentti prnt = kokoelma.hae(tunniste);
                                // Jos kokoelmassa ei ollut tunnisteella löytyvää dokumenttia --> Error
                                if (prnt == null) {
                                    System.out.println("Error!");
                                } else {
                                    System.out.println(prnt);
                                }
                            } catch (Exception e) {
                                System.out.println("Error!");
                            }
                        }
                        // tulostetaan koko kokoelma
                        else {
                            for (int i = 0; i < kokoelma.dokumentit().size(); i++) {
                                System.out.println(kokoelma.dokumentit().get(i));
                            }
                        }
                        break;
                    // ADD
                    case "add":
                        // jos ei parametrejä
                        if (komento.length == 1) {
                            System.out.println("Error!");
                        } else {
                            kokoelma.lisääDokumentti(komento, dok);
                        }
                        break;
                    // REMOVE
                    case "remove":
                        try {
                            // Jos liikaa parametrejä tai ei jos ei ole
                            if (komento.length > 2 || komento.length == 1) {
                                System.out.println("Error!");
                            }
                            // Jos dokumenttia ei ole
                            else if (kokoelma.hae(Integer.parseInt(komento[1])) == null) {
                                System.out.println("Error!");
                            } else {
                                // Poistetaan tunnisteen mukainen dokumentti
                                kokoelma.poistaDokumentti(Integer.parseInt(komento[1]));
                            }
                        } catch (Exception e) {
                            System.out.println("Error!");
                        }
                        break;

                    // FIND
                    case "find":
                        // jos ei parametrejä
                        if (komento.length < 2) {
                            System.out.println("Error!");
                        } else kokoelma.etsiVastaavuudet(komento);
                        break;
                    // POLISH
                    case "polish":
                        try {
                            // jos liikaa parametrejä
                            if (komento.length != 2) throw new IllegalArgumentException();
                            // käsittelee dokumentista sulkusanaistan sanat pois
                            kokoelma.kasitteleDokumentti(sulku, komento);

                        } catch (Exception e) {
                            System.out.println("Error!");
                        }
                        break;
                    // RESET
                    case "reset":
                        // jos komennon mukana yritetään antaa parametrejä
                        if (komento.length != 1) {
                            System.out.println("Error!");
                        }
                        // ladataan tiedosto uudelleen
                        paasilmukka();
                        break;
                    // ECHO
                    case "echo":
                        if (komento.length == 1) {
                            kaiutus = !kaiutus;
                        }
                        break;
                    // QUIT
                    case "quit":
                        System.out.println("Program terminated.");
                        // ei jatketa ohjelmaa
                        jatka = false;
                        break;
                    // Virheellinen komento
                    default:
                        System.out.println("Error!");
                        break;
                }
            }
        }
    }
}
