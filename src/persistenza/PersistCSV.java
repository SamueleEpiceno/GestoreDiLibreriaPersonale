package persistenza;
import factory.LibroFactory;
import strutturedibase.Libro;
import strutturedibase.strutture_utili.Genere;
import strutturedibase.strutture_utili.StatoLettura;
import strutturedibase.strutture_utili.Valutazione;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistCSV implements Persist {

        @Override
        public boolean salvaLibro(Libro libro, String path) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
                String riga = String.join(",",
                        libro.getAutore(),
                        libro.getGenere().name(),
                        libro.getISBN(),
                        libro.getStato().name(),
                        libro.getTitolo(),
                        libro.getValutazione().name()
                );
                writer.write(riga);
                writer.newLine();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public boolean eliminaLibro(Libro libro, String path) {
            try {
                List<Libro> lista = caricaLibri(path);
                lista.removeIf(l -> l.getISBN().equals(libro.getISBN()));
                return salvaTutti(lista, path);
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public List<Libro> caricaLibri(String path) {
            List<Libro> libri = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] campi = linea.split(",", -1);
                    if (campi.length != 6) continue;

                    String autore = campi[0];
                    Genere genere = Genere.valueOf(campi[1]);
                    String isbn = campi[2];
                    StatoLettura stato = StatoLettura.valueOf(campi[3]);
                    String titolo = campi[4];
                    Valutazione valutazione = Valutazione.valueOf(campi[5]);

                    Libro libro = LibroFactory.creaLibroConValutazione(titolo, autore, isbn, genere, stato, valutazione);

                    libri.add(libro);
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            return libri;
        }

        @Override
        public boolean salvaTutti(List<Libro> libri, String path) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) {
                for (Libro libro : libri) {
                    String riga = String.join(",",
                            libro.getAutore(),
                            libro.getGenere().name(),
                            libro.getISBN(),
                            libro.getStato().name(),
                            libro.getTitolo(),
                            libro.getValutazione().name()
                    );
                    writer.write(riga);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }


