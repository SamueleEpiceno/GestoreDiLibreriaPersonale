package GUI;



import strategy.OrdinaPerValutazione;
import strutturedibase.Libro;
import factory.LibroFactory;
import gestione.GestoreLibreria;
import persistenza.Persist;
import persistenza.PersistCSV;
import strutturedibase.filtri.FiltroPerGenere;
import strutturedibase.strutture_utili.Genere;
import strutturedibase.strutture_utili.StatoLettura;
import strutturedibase.strutture_utili.Valutazione;

import java.util.ArrayList;
import java.util.List;

import strategy.OrdinaPerTitolo;
import strategy.OrdinaPerAutore;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class gui_completa extends JFrame {

    private JComboBox<String> ordinamentoBox;
    private JButton ordinaBtn;


        private JTable tabella;
        private DefaultTableModel modelloTabella;
        private JTextField titoloField, autoreField, isbnField;
        private JComboBox<Genere> genereBox, filtroGenereBox;
        private JComboBox<StatoLettura> statoBox, filtroStatoBox;
        private JComboBox<Valutazione> valutazioneBox;
        private JTextField ricercaField;
        private JComboBox<String> ricercaTipoBox;

        private final GestoreLibreria gestore = GestoreLibreria.getInstance();
        private final Persist persister = new PersistCSV();
        private final String filePath = "libri.csv";

        public gui_completa() {
            setTitle("Gestore Libreria Personale - Completo");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1000, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            modelloTabella = new DefaultTableModel(new Object[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
            tabella = new JTable(modelloTabella);
            tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tabella.getSelectionModel().addListSelectionListener(e -> {
                int row = tabella.getSelectedRow();
                if (row != -1) {
                    titoloField.setText((String) tabella.getValueAt(row, 0));
                    autoreField.setText((String) tabella.getValueAt(row, 1));
                    isbnField.setText((String) tabella.getValueAt(row, 2));
                    genereBox.setSelectedItem(Genere.valueOf(tabella.getValueAt(row, 3).toString()));
                    valutazioneBox.setSelectedItem(Valutazione.valueOf(tabella.getValueAt(row, 4).toString()));
                    statoBox.setSelectedItem(StatoLettura.valueOf(tabella.getValueAt(row, 5).toString()));
                }
            });
            add(new JScrollPane(tabella), BorderLayout.CENTER);

            JPanel formPanel = new JPanel(new GridLayout(2, 6));
            titoloField = new JTextField();
            autoreField = new JTextField();
            isbnField = new JTextField();
            genereBox = new JComboBox<>(Genere.values());
            statoBox = new JComboBox<>(StatoLettura.values());
            valutazioneBox = new JComboBox<>(Valutazione.values());

            formPanel.add(new JLabel("Titolo"));
            formPanel.add(new JLabel("Autore"));
            formPanel.add(new JLabel("ISBN"));
            formPanel.add(new JLabel("Genere"));
            formPanel.add(new JLabel("Stato"));
            formPanel.add(new JLabel("Valutazione"));
            formPanel.add(titoloField);
            formPanel.add(autoreField);
            formPanel.add(isbnField);
            formPanel.add(genereBox);
            formPanel.add(statoBox);
            formPanel.add(valutazioneBox);

            add(formPanel, BorderLayout.NORTH);

            JPanel bottomPanel = new JPanel(new GridLayout(4, 1));

            JButton aggiungiBtn = new JButton("Aggiungi");
            JButton salvaBtn = new JButton("Salva su CSV");
            JButton caricaBtn = new JButton("Carica da CSV");
            JButton eliminaBtn = new JButton("Elimina selezionato");
            JButton modificaBtn = new JButton("Modifica selezionato");
            JButton ordinaPerValutazioneBtn = new JButton("Ordina per Valutazione");

            aggiungiBtn.addActionListener(e -> aggiungiLibro());
            salvaBtn.addActionListener(e -> {
                persister.salvaTutti(gestore.getListaLibri(), filePath);
                JOptionPane.showMessageDialog(this, "Salvataggio completato!");
            });
            caricaBtn.addActionListener(e -> {
                List<Libro> caricati = persister.caricaLibri(filePath);
                gestore.svuotaLibreria();
                for (Libro l : caricati) gestore.aggiungiLibro(l);
                aggiornaTabella(gestore.getListaLibri());
            });
            eliminaBtn.addActionListener(e -> eliminaLibro());
            modificaBtn.addActionListener(e -> modificaLibro());


            JPanel buttonPanel = new JPanel();


            buttonPanel.add(aggiungiBtn);
            buttonPanel.add(modificaBtn);
            buttonPanel.add(eliminaBtn);
            buttonPanel.add(salvaBtn);
            buttonPanel.add(caricaBtn);

            JPanel filtroPanel = new JPanel();
            filtroGenereBox = new JComboBox<>(Genere.values());
            filtroStatoBox = new JComboBox<>(StatoLettura.values());
            JButton filtraBtn = new JButton("Filtra");
            filtraBtn.addActionListener(e -> filtraLibri());
            filtroPanel.add(new JLabel("Genere:"));
            filtroPanel.add(filtroGenereBox);
            filtroPanel.add(new JLabel("Stato:"));
            filtroPanel.add(filtroStatoBox);
            filtroPanel.add(filtraBtn);

            JPanel ricercaPanel = new JPanel();
            ricercaField = new JTextField(15);
            ricercaTipoBox = new JComboBox<>(new String[]{"Titolo", "Autore"});
            JButton cercaBtn = new JButton("Cerca");
            cercaBtn.addActionListener(e -> cercaLibri());
            ricercaPanel.add(new JLabel("Ricerca:"));
            ricercaPanel.add(ricercaField);
            ricercaPanel.add(ricercaTipoBox);
            ricercaPanel.add(cercaBtn);

            JPanel ordinamentoPanel = new JPanel();
            ordinamentoBox = new JComboBox<>(new String[]{"Titolo", "Autore", "Valutazione"});
            ordinaBtn = new JButton("Ordina");

            ordinaBtn.addActionListener(e -> {
                String criterio = (String) ordinamentoBox.getSelectedItem();
                switch (criterio) {
                    case "Titolo" -> gestore.setStrategia(new OrdinaPerTitolo());
                    case "Autore" -> gestore.setStrategia(new OrdinaPerAutore());
                    case "Valutazione" -> gestore.setStrategia(new OrdinaPerValutazione());
                }
                List<Libro> ordinati = gestore.ordinaLibri();
                aggiornaTabella(ordinati);
            });

            ordinamentoPanel.add(new JLabel("Ordina per:"));
            ordinamentoPanel.add(ordinamentoBox);
            ordinamentoPanel.add(ordinaBtn);



            bottomPanel.add(ordinamentoPanel);
            bottomPanel.add(buttonPanel);
            bottomPanel.add(filtroPanel);
            bottomPanel.add(ricercaPanel);

            add(bottomPanel, BorderLayout.SOUTH);

            aggiornaTabella(gestore.getListaLibri());
            setVisible(true);

        }

        private void modificaLibro() {
            int row = tabella.getSelectedRow();
            if (row != -1) {
                String isbn = (String) tabella.getValueAt(row, 2);
                List<Libro> tutti = gestore.getListaLibri();
                Libro daModificare = null;
                for (Libro l : tutti) {
                    if (l.getISBN().equals(isbn)) {
                        daModificare = l;
                        break;
                    }
                }
                if (daModificare != null) {
                    String nuovoTitolo = titoloField.getText();
                    String nuovoAutore = autoreField.getText();
                    Genere nuovoGenere = (Genere) genereBox.getSelectedItem();
                    StatoLettura nuovoStato = (StatoLettura) statoBox.getSelectedItem();
                    Valutazione nuovaValutazione = (Valutazione) valutazioneBox.getSelectedItem();

                    daModificare.setTitolo(nuovoTitolo);
                    daModificare.setAutore(nuovoAutore);
                    daModificare.setGenere(nuovoGenere);
                    daModificare.setStato(nuovoStato);
                    daModificare.setValutazione(nuovaValutazione);

                    aggiornaTabella(gestore.getListaLibri());
                    tabella.clearSelection();
                    titoloField.setText("");
                    autoreField.setText("");
                    isbnField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare e riempi i campi.");
            }
        }

        private void cercaLibri() {
            String testo = ricercaField.getText().toLowerCase();
            String tipo = (String) ricercaTipoBox.getSelectedItem();

            List<Libro> risultati;
            if (tipo.equals("Titolo")) {
                risultati = gestore.cercaPerTitolo(testo);
            } else {
                risultati = gestore.cercaPerAutore(testo);
            }

            aggiornaTabella(risultati);
        }

        private void aggiungiLibro() {
            String titolo = titoloField.getText();
            String autore = autoreField.getText();
            String isbn = isbnField.getText();
            Genere genere = (Genere) genereBox.getSelectedItem();
            StatoLettura stato = (StatoLettura) statoBox.getSelectedItem();
            Valutazione valutazione = (Valutazione) valutazioneBox.getSelectedItem();

            if (titolo.isEmpty() || autore.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tutti i campi di testo sono obbligatori!");
                return;
            }

            Libro libro = LibroFactory.creaLibroConValutazione(titolo, autore, isbn, genere, stato, valutazione);
            gestore.aggiungiLibro(libro);
            aggiornaTabella(gestore.getListaLibri());

            titoloField.setText("");
            autoreField.setText("");
            isbnField.setText("");
        }

        private void aggiornaTabella(List<Libro> libri) {
            modelloTabella.setRowCount(0);
            for (Libro l : libri) {
                modelloTabella.addRow(new Object[]{
                        l.getTitolo(), l.getAutore(), l.getISBN(),
                        l.getGenere(), l.getValutazione(), l.getStato()
                });
            }
        }

        private void eliminaLibro() {
            int row = tabella.getSelectedRow();
            if (row != -1) {
                String isbn = (String) tabella.getValueAt(row, 2);
                List<Libro> tutti = gestore.getListaLibri();
                Libro daRimuovere = null;
                for (Libro l : tutti) {
                    if (l.getISBN().equals(isbn)) {
                        daRimuovere = l;
                        break;
                    }
                }
                if (daRimuovere != null) {
                    gestore.rimuoviLibro(daRimuovere);
                    aggiornaTabella(gestore.getListaLibri());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da eliminare.");
            }
        }

    private void filtraLibri() {
        Genere genereSelezionato = (Genere) filtroGenereBox.getSelectedItem();
        StatoLettura statoSelezionato = (StatoLettura) filtroStatoBox.getSelectedItem();
        List<Libro> listaFiltrataPerGenere = FiltroPerGenere.filtra(gestore.getListaLibri(), genereSelezionato);
        List<Libro> listaFiltrataFinale = new ArrayList<>();
        for (Libro libro : listaFiltrataPerGenere) {
            if (libro.getStato() == statoSelezionato) {
                listaFiltrataFinale.add(libro);
            }
        }
        aggiornaTabella(listaFiltrataFinale);
    }


    public static void main(String[] args) {
            SwingUtilities.invokeLater(gui_completa::new);
        }
    }
