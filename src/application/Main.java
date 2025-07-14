package application;
import strutturedibase.Libro;
import factory.LibroFactory;
import gestione.GestoreLibreria;
import persistenza.Persist;
import persistenza.PersistCSV;
import strutturedibase.strutture_utili.Genere;
import strutturedibase.strutture_utili.StatoLettura;
import strutturedibase.strutture_utili.Valutazione;

import java.util.List;

    public class Main {
        public static void main(String[] args) {

            GestoreLibreria gestore = GestoreLibreria.getInstance();
            Persist persister = new PersistCSV();

            Libro libro1 = LibroFactory.creaLibroConValutazione(
                    "TitoloTest", "AutoreTest", "9780451524931",
                    Genere.ROMANZO, StatoLettura.LETTO, Valutazione.CINQUE_STELLE);

            Libro libro2 = LibroFactory.creaLibro(
                    "TitoloTest1", "AutoreTest1", "9788804702479",
                    Genere.FANTASCIENZA, StatoLettura.DA_LEGGERE);

            gestore.aggiungiLibro(libro1);
            gestore.aggiungiLibro(libro2);


            String filePath = "libri.csv";
            boolean salvato = persister.salvaTutti(gestore.getListaLibri(), filePath);
            System.out.println("Salvataggio completato: " + salvato);


            gestore.svuotaLibreria();


            List<Libro> caricati = persister.caricaLibri(filePath);
            System.out.println("Libri caricati dal file:");
            for (Libro l : caricati) {
                System.out.println(l);
            }
        }
    }


