package gestione;

import strategy.StrategiaOrdinamento;
import strutturedibase.Libro;

import java.util.ArrayList;
import java.util.List;

public class GestoreLibreria {

    private StrategiaOrdinamento strategia;


    private static GestoreLibreria istanza = null;
    private final List<Libro> listaLibri;

    private GestoreLibreria() {
        listaLibri = new ArrayList<>();
    }

    public static GestoreLibreria getInstance() {
        if (istanza == null) {
            istanza = new GestoreLibreria();
        }
        return istanza;
    }

    public void setStrategia(StrategiaOrdinamento strategia) {
        this.strategia = strategia;
    }


    public void aggiungiLibro(Libro libro) {
        listaLibri.add(libro);
    }

    public void rimuoviLibro(Libro libro) {
        listaLibri.remove(libro);
    }

    public List<Libro> getListaLibri() {
        return new ArrayList<>(listaLibri);
    }

    public List<Libro> cercaPerTitolo(String titolo) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro libro : listaLibri) {
            if (libro.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                risultati.add(libro);
            }
        }
        return risultati;
    }

    public List<Libro> cercaPerAutore(String autore) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro libro : listaLibri) {
            if (libro.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(libro);
            }
        }
        return risultati;
    }


    public List<Libro> ordinaLibri() {
        if (strategia != null) {
            return strategia.ordina(listaLibri);
        }
        return listaLibri;
    }


    public void svuotaLibreria() {
        listaLibri.clear();
    }
}
