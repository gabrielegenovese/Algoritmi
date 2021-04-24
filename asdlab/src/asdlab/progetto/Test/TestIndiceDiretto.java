package asdlab.progetto.Test;

import java.io.*;
import java.util.*;

import asdlab.progetto.IndiceDiretto.Hit;
import asdlab.progetto.IndiceDiretto.IndiceDiretto;
import asdlab.progetto.Lexicon.*;
import asdlab.libreria.AlberiRicerca.*;

public class TestIndiceDiretto {

	protected int docId;
	protected IndiceDiretto indiceDiretto;
	protected Dizionario urlDiz;
	
	protected final static boolean debug = true;
	protected final static int NUM_RIS = 500;

    public void indicizzaFileDir(String[] fileDocumenti, int pos) {

        indiceDiretto = new IndiceDiretto();
        urlDiz = new AlberoAVL();
        docId = 0;

        for (int i = pos; i < fileDocumenti.length; i++)
        	indicizzaUnFileDir(fileDocumenti[i]);

        if (debug) {
        	Hit[] arrayHit = indiceDiretto.toArray();
        	Lexicon lexicon = indiceDiretto.getLexicon();
        	int n = arrayHit.length < NUM_RIS ? arrayHit.length : NUM_RIS;
        	System.out.println("- Lunghezza indice: " + arrayHit.length);
        	System.out.println("- Prime " + n + " parole dell'indice: ");
        	for (int i=0; i<n; i++) 
        		System.out.println(lexicon.getParolaID(new Integer(arrayHit[i].IDParola)) + 
        				" - IDDoc " + arrayHit[i].IDDoc +
        				" - freq " + arrayHit[i].freq);
        }
    }

    private void indicizzaUnFileDir(String nomeFileDir) {

    	if (indicizzaArchivio(nomeFileDir)) return;
    	
    	File file = new File(nomeFileDir);
        File[] elencoFile;
        
        if (file.isDirectory())
        	elencoFile = file.listFiles();
        else {
            elencoFile = new File[1];
            elencoFile[0] = file;
        }

        for (int j = 0; j < elencoFile.length; j++) {
        	if (debug) 
				System.out.println("\n-Indicizzazione di: " + elencoFile[j]);

        	if (indicizzaUnFile(elencoFile[j], docId)) {
        		urlDiz.insert(elencoFile[j].getName(), docId);
        		docId++;
        	}
        }
    }

    private boolean indicizzaArchivio(String nomeDir){
        File file = new File(nomeDir);
        if (!file.isDirectory()) return false;
    	File crawlFile = new File(nomeDir+"/crawl.txt");
    	if (!crawlFile.exists()) return false;

    	try {
    		BufferedReader br = new BufferedReader(new FileReader(crawlFile));
    		for (;;) {
    			String s = br.readLine();
    			if (s == null) break;
    		    StringTokenizer st = new StringTokenizer(s);
    		    if (!st.hasMoreTokens()) continue;
    		    int id = Integer.parseInt(st.nextToken());
    		    if (!st.hasMoreTokens()) continue;
    		    String url = st.nextToken();
    		    File docFile = new File(nomeDir + "/" + id + ".html");
    		    if (!docFile.exists()) continue;
            	if (indicizzaUnFile(docFile, docId)) {
            		urlDiz.insert(url, docId);
            		docId++;
            	}
//            	System.out.println("id = " + id + " - url = " + url);
    		}
    	}
    	catch (Exception e) {
            if (debug) 
            	System.out.println("Errore indicizzazione archivio: "+ file.getName());
    		return false;
    	}

    	return true;
    }
    
    private boolean indicizzaUnFile(File file, int docID) {
        try {
            if (debug) System.out.println("Scansione documento: "+ file.getName());
            if (file.getName().endsWith(".txt") || 
            		file.getName().endsWith(".htm") || 
            		file.getName().endsWith(".html")) {
                indiceDiretto.indicizzaDoc(new BufferedReader(new FileReader(file)), docId);
            } else {
            	System.out.println("Formato file non supportato");
            	return false;
            }
        } 
        catch (Exception e) {
            System.out.println("Impossibile aprire il file " + file.getName());
        	return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        if (args.length == 0) 
            System.out.println("sintassi: \n'TestIndiceDiretto nomeFile1 nomeFile2 ...' oppure \n"+
                               "'TestIndiceDiretto nomeDirectory1 nomeDirectory2 ...");
        else new TestIndiceDiretto().indicizzaFileDir(args, 0);
    }
}
