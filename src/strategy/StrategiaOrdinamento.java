package strategy;

import java.util.List;
import strutturedibase.Libro;

public interface StrategiaOrdinamento {
    List<Libro> ordina(List<Libro> libri);
}
