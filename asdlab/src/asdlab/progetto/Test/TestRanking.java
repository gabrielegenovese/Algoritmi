package asdlab.progetto.Test;

import java.io.*;
import asdlab.progetto.IndiceInverso.*;
import asdlab.progetto.Ranking.Ranking;

public class TestRanking extends TestIndiceInverso {
	
	private final int lungPag = 10;	
	
    public void stampaRisultati(Ris[] risultati){
    	for (int i = 0; i < risultati.length; i += lungPag) {
    		int delta = Math.min(risultati.length - i, lungPag);
    		System.out.println("\n+ Documenti da " + (i+1) + " a " + (i+delta));
    		Ris[] risultato = Ranking.filtra2(risultati, i, i + delta);
    		super.stampaRisultati(risultato);
    	}
    }
    
   	public void interrogazioneFile(String fileName) {

		try {
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			String inputUtente = input.readLine();
			
			while (inputUtente != null) {
				if (inputUtente.compareTo("0") == 0) break;
	
				Ris[] risultati = indiceInverso.query(inputUtente.split(" "));

				if (risultati.length > 0) {
					System.out
					.println("\n-Documenti individuati: " + risultati.length);

				}
				else {
					System.out
							.println("Non esistono documenti contenenti le parole indicate");
				}
				
				inputUtente = input.readLine();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   	
   	
	public static void main(String[] args) {
		
		TestRanking testRanking = new TestRanking();

		if (args.length == 0 || args[0].equals("-query") && args.length < 2) 
	        System.out.println("sintassi: \n"+
	        		"'TestRanking <-query fileQuery> nomeFile1 nomeFile2 ...' oppure \n"+
	        		"'TestRanking <-query fileQuery> nomeDirectory1 nomeDirectory2 ...");
	    else if (args[0].equals("-query")) {
	        testRanking.indicizzaFileDir(args, 2);
	        testRanking.interrogazioneFile(args[1]);
	    } 
	    else {
			testRanking.indicizzaFileDir(args, 0);
			testRanking.interrogazioneInterattiva();
	    }
	}
}
