package factory;


import strutturedibase.Libro;
import strutturedibase.strutture_utili.Genere;
import strutturedibase.strutture_utili.StatoLettura;
import strutturedibase.strutture_utili.Valutazione;

public class LibroFactory {

    public static Libro creaLibro(String titolo, String autore, String isbn, Genere genere, StatoLettura stato) {
        return new Libro(autore, genere, isbn, stato, titolo);
    }

    public static Libro creaLibroConValutazione(String titolo, String autore, String isbn, Genere genere, StatoLettura stato, Valutazione valutazione) {
        return new Libro(autore, genere, isbn, stato, titolo, valutazione);
    }

    Libro nuovoLibro = LibroFactory.creaLibro("Titolo_Test", "Mario Rossi", "9781234567890", Genere.ROMANZO, StatoLettura.LETTO);

}
