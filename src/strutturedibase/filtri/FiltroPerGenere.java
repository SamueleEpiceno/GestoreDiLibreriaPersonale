package strutturedibase.filtri;


import strutturedibase.Libro;
import strutturedibase.strutture_utili.Genere;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPerGenere {

    public static List<Libro> filtra(List<Libro> libri, Genere genere) {
        return libri.stream()
                .filter(libro -> libro.getGenere() == genere)
                .collect(Collectors.toList());
    }
}
