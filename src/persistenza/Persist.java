package persistenza;


import strutturedibase.Libro;
import java.util.List;

public interface Persist {
    boolean salvaLibro(Libro libro, String path);
    boolean eliminaLibro(Libro libro, String path);
    boolean salvaTutti(List<Libro> libri, String path);
    List<Libro> caricaLibri(String path);

}
