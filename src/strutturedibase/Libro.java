package strutturedibase;


import strutturedibase.strutture_utili.Valutazione;
import strutturedibase.strutture_utili.Genere;
import strutturedibase.strutture_utili.StatoLettura;

import java.io.Serializable;

public class Libro implements Serializable {

    private String autore;
    private Genere genere;
    private String ISBN;
    private StatoLettura stato;
    private String titolo;
    private Valutazione valutazione;

    public Libro() {
        this.valutazione = Valutazione.NON_VALUTATO;
        this.stato = StatoLettura.DA_LEGGERE;
    }

    public Libro(String autore, Genere genere, String ISBN, StatoLettura stato, String titolo) {
        this.autore = autore;
        this.genere = genere;
        this.ISBN = ISBN;
        this.stato = stato;
        this.titolo = titolo;
        this.valutazione = Valutazione.NON_VALUTATO;
    }

    public Libro(String autore, Genere genere, String ISBN, StatoLettura stato, String titolo, Valutazione valutazione) {
        this.autore = autore;
        this.genere = genere;
        this.ISBN = ISBN;
        this.stato = stato;
        this.titolo = titolo;
        this.valutazione = valutazione;
    }

    public Libro(Libro libro) {
        this.autore = libro.autore;
        this.genere = libro.genere;
        this.ISBN = libro.ISBN;
        this.stato = libro.stato;
        this.titolo = libro.titolo;
        this.valutazione = libro.valutazione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public StatoLettura getStato() {
        return stato;
    }

    public void setStato(StatoLettura stato) {
        this.stato = stato;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    @Override
    public String toString() {
        return String.format("Libro[titolo='%s', autore='%s', ISBN='%s', genere=%s, stato=%s, valutazione=%s]",
                titolo, autore, ISBN, genere, stato, valutazione);
    }
}
