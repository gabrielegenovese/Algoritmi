package asdlab.progetto.Test;

import java.io.*;
import asdlab.progetto.IndiceDiretto.*;
import asdlab.progetto.IndiceInverso.IndiceInverso;
import asdlab.progetto.IndiceInverso.Ris;
import asdlab.progetto.Lexicon.Lexicon;

public class TestIndiceInverso extends TestIndiceDiretto {

    protected IndiceInverso indiceInverso;

    public void indicizzaFileDir(String[] fileDocumenti, int pos) {
        super.indicizzaFileDir(fileDocumenti, pos);
        indiceInverso = new IndiceInverso(indiceDiretto);
        System.out.println("\n- Numero hit dell'indice inverso: " + indiceInverso.toArray().length);
        if (debug) {
        	Hit[] arrayHit = indiceInverso.toArray();
        	Lexicon lexicon = indiceDiretto.getLexicon();
        	int n = arrayHit.length < NUM_RIS ? arrayHit.length : NUM_RIS;
        	System.out.println("- Prime " + n + " parole dell'indice: ");
        	for (int i=0; i<n; i++) 
        		System.out.println(lexicon.getParolaID(new Integer(arrayHit[i].IDParola)) + 
        				" - IDDoc " + arrayHit[i].IDDoc +
        				" - freq " + arrayHit[i].freq);
        }
    }

    public void interrogazioneInterattiva() {

        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(System.in));

            while (true) {
                System.out.println(
                        "\nInserire i termini da ricercare separati da spazi (0 per uscire)");

                String inputUtente = input.readLine();

                if (inputUtente.equals("0")) break;

                Ris[] risultati = indiceInverso.query(inputUtente.split(" "));

                if (risultati.length > 0) {
                    System.out.println(
                            "\n- Documenti individuati: " + risultati.length);
                    stampaRisultati(risultati);
                } else {
                    System.out.println(
                            "Non esistono documenti contenenti le parole indicate");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void interrogazioneFile(String fileName) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String inputUtente = input.readLine();
            
            while (inputUtente != null) {
                if (inputUtente.compareTo("0") == 0) {
                    break;
                }
                System.out.println("\n* Query: " + inputUtente);
                Ris[] risultati = indiceInverso.query(inputUtente.split(" "));
                if (risultati.length > 0) {
                    System.out.println(
                            "\n- Documenti individuati: " + risultati.length);
                    stampaRisultati(risultati);
                } else {
                    System.out.println(
                            "Non esistono documenti contenenti le parole indicate");
                }
                inputUtente = input.readLine(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void stampaRisultati(Ris[] risultati){
    	for (int i = 0; i < risultati.length; i++){
    		String url = (String)urlDiz.search(risultati[i].IDDoc);
    		System.out.println("[" + risultati[i].IDDoc + "] " + url);
    	}
    }

    public void conteggiaParole() {
        int conteggioOccorrenze = 1;
        int conteggioParole = 0;
        int numeroParole = 1000;
    
        PrintStream out = null;

        try {
            out = new PrintStream(System.out, true, "CP850");
        } catch (UnsupportedEncodingException e) {}

        Hit[] arrayHit = indiceInverso.toArray();
        Hit hit = arrayHit[0];
        Lexicon lexicon = indiceInverso.getLexicon();
        
        for (int i = 0; (conteggioParole < numeroParole)
                && (i < arrayHit.length); i++) {
            if (arrayHit[i].IDParola == hit.IDParola) {
                conteggioOccorrenze++;
            } else {
                out.println(
                        "parola: "
                                + lexicon.getParolaID(
                                        new Integer(hit.IDParola))
                                        + " conteggio: "
                                        + conteggioOccorrenze);
                conteggioOccorrenze = 1;
                conteggioParole++;
                hit = arrayHit[i];
            }
        }
    }
  
    public static void main(String[] args) {
        
        TestIndiceInverso testIndiceInverso = new TestIndiceInverso();

        if (args.length == 0 || args[0].equals("-query") && args.length < 2) { 
            System.out.println(
                    "sintassi: \n"
                            + "'TestIndiceInverso <-query fileQuery> nomeFile1 nomeFile2 ...' oppure \n"
                            + "'TestIndiceInverso <-query fileQuery> nomeDirectory1 nomeDirectory2 ...");
        } else if (args[0].equals("-query")) {
            testIndiceInverso.indicizzaFileDir(args, 2);
            testIndiceInverso.interrogazioneFile(args[1]);
        } else {
            testIndiceInverso.indicizzaFileDir(args, 0);
            testIndiceInverso.interrogazioneInterattiva();
        }
    }
}
