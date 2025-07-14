package strutturedibase.filtri;


import strutturedibase.Libro;
import strutturedibase.strutture_utili.StatoLettura;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPerStatoLettura {

    public static List<Libro> filtra(List<Libro> libri, StatoLettura stato) {
        return libri.stream()
                .filter(libro -> libro.getStato() == stato)
                .collect(Collectors.toList());
    }
}
