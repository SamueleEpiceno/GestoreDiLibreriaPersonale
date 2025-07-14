package strategy;

import strutturedibase.Libro;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdinaPerValutazione implements StrategiaOrdinamento {
    @Override
    public List<Libro> ordina(List<Libro> libri) {
        return libri.stream()
                .sorted(Comparator.comparing(Libro::getValutazione))
                .collect(Collectors.toList());
    }
}
