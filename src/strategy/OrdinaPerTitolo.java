package strategy;

import strutturedibase.Libro;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdinaPerTitolo implements StrategiaOrdinamento {
    @Override
    public List<Libro> ordina(List<Libro> libri) {
        return libri.stream()
                .sorted(Comparator.comparing(Libro::getTitolo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}
