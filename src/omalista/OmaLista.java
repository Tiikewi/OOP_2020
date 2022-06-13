package harjoitustyo.omalista;

import harjoitustyo.apulaiset.Ooperoiva;

import java.util.LinkedList;


public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

    /**
     * Lisätään uusi alkio listaan pienimmästä suurimpaan.
     *
     * @param uusi viite olioon, jonka luokan tai luokan esivanhemman oletetaan
     * toteuttaneen Comparable-rajapinnan.
     * @throws IllegalArgumentException jos lisäys epäonnistui, koska uutta alkiota
     * ei voitu vertailla. Vertailu epäonnistuu, kun parametri on null-arvoinen
     * tai siihen liittyvän olion luokka tai luokan esivanhempi ei vastoin oletuksia
     * toteuta Comparable-rajapintaa.
     */
    @SuppressWarnings({"unchecked"})
    public void lisää(E uusi) throws IllegalArgumentException {
        try {
            // jos ei tarvittavia viitteitä, tai null, heitetään poikkeus.
            if (!(uusi instanceof Comparable)) {
                throw new IllegalArgumentException("Listalle lisättävä oli null.");
            }
            int ind = 0;
            // jos lista on tyhjä, listään arvo suoraan.
            if (size() == 0) {
                add(uusi);
            } else while (size() > ind) {
                // Lisätään vertailun mahdollidtava apuviite
                Comparable nykyinen = (Comparable) get(ind);
                // Jos uusi on pienempi kuin nykyinen listassa käytävä, niin lisätään.
                if (nykyinen.compareTo(uusi) > 0) {
                    // 1 < 2 // A < B
                    // Lisätään alkio nykyiseen indeksiin.
                    add(ind, uusi);
                    // Alkio lisätty voidaan lopettaa loop
                    break;
                    // Jos ollaan listan lopussa, ja ei olla vielä lisätty, niin lisätään viimeiseksi.
                } else if (ind + 1 == size()) {
                    addLast(uusi);
                    // Alkio lisätty voidaan lopettaa loop
                    break;
                }
                ind++;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Lisäys epäonnistui");
        }


    }
}
