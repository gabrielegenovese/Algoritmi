package asdlab.progetto.Test;

import asdlab.progetto.Clustering.Clustering;
import asdlab.progetto.Clustering.CostoPC;
import asdlab.progetto.IndiceInverso.Ris;

public class TestClustering extends TestIndiceInverso {

    public void stampaRisultati(Ris[] risultati){
    	Ris[][] cl = Clustering.cluster(risultati, 
    			new CostoPC(risultati, indiceDiretto));
    	System.out.println("- Cluster 1: ");
    	super.stampaRisultati(cl[0]);
    	if (cl.length == 1) return;
    	System.out.println("- Cluster 2: ");
    	super.stampaRisultati(cl[1]);
    }

	public static void main(String[] args) {

    	TestClustering testClustering = new TestClustering();

        if (args.length == 0 || args[0].equals("-query") && args.length < 2) { 
            System.out.println(
                    "sintassi: \n"
                            + "'TestClustering <-query fileQuery> nomeFile1 nomeFile2 ...' oppure \n"
                            + "'TestClustering <-query fileQuery> nomeDirectory1 nomeDirectory2 ...");
        } 
        else if (args[0].equals("-query")) {
            testClustering.indicizzaFileDir(args, 2);
            testClustering.interrogazioneFile(args[1]);
        } 
        else {
            testClustering.indicizzaFileDir(args, 0);
            testClustering.interrogazioneInterattiva();
        }
    }
}
