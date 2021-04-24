package asdlab.progetto.Clustering;

import java.util.List;

import asdlab.progetto.IndiceDiretto.*;
import asdlab.progetto.IndiceInverso.IndiceInverso;
import asdlab.progetto.IndiceInverso.Ris;
import asdlab.libreria.AlberiRicerca.*;

/**
 * La classe <code>CostoPC</code> consente di determinare la similarit&agrave; esistente
 * tra coppie di documenti risultanti da una precedente interrogazione <code>q</code>
 * realizzata mediante il metodo {@link IndiceInverso#query(String[])} della
 * classe {@link IndiceInverso}. La classe opera costruendo un array di array, <code>hits</code>,
 * che mantiene per ciascun documento la lista delle parole che vi compaiono. 
 * Successivamente, si determina la similarit&agrave; di una coppia di documenti <code>(a,b)</code> 
 * considerando la somma del minimo, tra <code>a</code> e <code>b</code>, della frequenza
 * con cui ciascuna parola chiave dell'interrogazione <code>q</code>
 * compare sia in <code>a</code> che in <code>b</code>.
 *
 */
public class CostoPC implements Costo {

	/**
	 * Mantiene l'elenco delle parole appartenenti a ciascuno dei documenti
	 * restituiti dall'interrogazione 
	 */
	private Hit[][] hits;
	
	/**
	 * Array di dizionari utilizzati per indicizzare le parole che compaiono in ciascun
	 * documento 
	 */
	private Dizionario[] diz;

	/**
	 * Costruttore per l'istanziazione di una nuova classe <code>CostoPC</code>.
	 * Il costruttore acquisisce in input l'insieme di documenti <code>ris</code> di cui verificare la similarit&agrave;
	 * ed il riferimento all'indice diretto utilizzato per la precedente indicizzazione dei documenti.
	 * Successivamente, istanzia la variabile <code>hits</code> determinando l'elenco delle parole
	 * appartenendo a ciascun documento incluso in <code>ris</code> e costruisce per ciascun documento
	 * un dizionario, rappresentato mediante la classe <code>AlberoAVL</code>, con cui
	 * ciascuna parola del documento stesso viene indicizzata in base al proprio identificatore.
	 *  
	 * @param ris l'insieme di documenti di cui verificare successivamente la similarit&agrave;
	 * @param id il riferimento all'indice diretto utilizzato in precedenza per indicizzare i documenti
	 */
	public CostoPC(Ris[] ris, IndiceDiretto id){
		Hit[] arrayHit = id.toArray();
		hits = new Hit[ris.length][];
		diz = new Dizionario[ris.length];
		for (int i = 0; i < ris.length; i++){
			diz[i] = new AlberoAVL();
		    List l = IndiceDiretto.listaParole(arrayHit, ris[i].IDDoc);
		    hits[i] = (Hit[])l.toArray(new Hit[0]);
		    for (int j = 0; j < hits[i].length; j++)
		    	diz[i].insert(hits[i][j], hits[i][j].IDParola);
		}
	}

	/**
	 * Valuta la similarit&agrave; esistente tra una coppia di documenti. Il metodo
	 * determina la similarit&agrave; di una coppia di documenti <code>(a,b)</code> 
	 * considerando la somma del minimo, tra <code>a</code> e <code>b</code>, della frequenza
	 * con cui ciascuna parola chiave dell'interrogazione originaria
	 * compare sia in <code>a</code> che in <code>b</code>.
	 * 
	 * @param a l'indice del primo documento
	 * @param b l'indice del secondo documento
	 * @return il valore di similarit&agrave; esistente tra i documenti <code>a</code> e <code>b</code>
	 */
	public double costo(int a, int b){
		double c = 0.0;
		for (int i = 0; i < hits[a].length; i++) {
			Hit ha = hits[a][i];
			Hit hb = (Hit)diz[b].search(ha.IDParola);
			if (hb != null)
				c += Math.min(ha.freq, hb.freq);
		}
		return c;
	}
}
