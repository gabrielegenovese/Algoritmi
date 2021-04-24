package asdlab.libreria.AlberiRicerca;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.StruttureElem.*;

/**
 * La classe <code>AlberoAVL</code> implementa un albero di ricerca binario bilanciato in altezza (albero AVL).
 * La classe &egrave; ottenuta per derivazione dalla classe astratta <code>AlberoBRR</code> di cui
 * sfrutta le operazioni di rotazione a sinistra e rotazione a destra di un nodo.
 * Nell'implementazione realizzata, ogni nodo dell'albero AVL non mantiene
 * esplicitamente il fattore di bilanciamento, ma piuttosto l'altezza
 * del sottoalbero in esso radicato. Tale informazione viene associata a ciascun nodo
 * mediante la classe <code>InfoAVL</code>, derivazione della classe <code>InfoBR</code>
 * utilizzata in <code>AlberoBR</code> per custodire la coppia (elem, chiave).
 *
 */
public class AlberoAVL extends AlberoBRR {
	
	/** 
	 * Classe definita localmente ala classe <code>AlberoAVL</code>
	 * per il mantenimento dell'altezza del sottoalbero radicato nel nodo
	 * corrente. La classe deriva la classe <code>InfoBR</code>, utilizzata a sua volta
	 * per memorizzare la coppia (elem,chiave) associata a ciascun nodo dell'albero,
	 * oltre che il riferimento del nodo dell'albero binario di ricerca corrispondente
	 * a tale coppia.
	 *
	 */
	protected class InfoAVL extends InfoBR {
		/**
		 * Altezza del sottoalbero radicato nel nodo corrente
		 */
		protected int altezza;
		
		/**
		 * Costruttore per l'allocazione di una nuova istanza di <code>InfoAVL</code>.
		 * L'altezza di un nuovo nodo &egrave: inizialmente posta pari ad 1
		 * 
		 * @param e elemento da conservare nel dizionario
		 * @param k chiave associata ad <code>e</code>
		 */
		protected InfoAVL(Object e, Comparable k){
			super(e,k);
			altezza = 1;
		}
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> e ribilancia
	 * l'albero risultante (<font color=red>Tempo O(log(n))</font>). L'inserimento avvviene identicamente
	 * all'operazione <code>insert</code> di <code>AlberoBR</code>.
	 * Una volta inserito il nuovo nodo, si parte dal suo genitore
	 * e si procede in alto, verso la radice, ricalcolando i fattori di bilanciamento mutati
	 * in seguito all'inserimento tramite i metodi <code>bil</code> e <code>aggiornaAltezza</code>. 
	 * Nel caso si individui un nodo con fattore di bilanciamento pari a +/-2, si
	 * ribilancia l'albero in quel punto tramite il metodo <code>ruota</code>,
	 * e si interrompe l'esecuzione del metodo.
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 * @return il riferimento all'elemento <code>e</code> nel dizionario  
	 */	
	public Rif insert(Object e, Comparable k) {
		InfoAVL i = new InfoAVL(e, k);
		Nodo v = super.insert(i);
		Nodo p = alb.padre(v);
		while (p != null) {
			if (bil(p)==-2 || bil(p)==2) break;
			aggiornaAltezza(p);
			p = alb.padre(p);
		}
		if (p != null) ruota(p);
		return i;
	}

	/**
	 * Rimuove dal dizionario l'elemento <code>i</code>
	 * e ribilancia l'albero risultante (<font color=red>Tempo O(log(n))</font>).
	 * La rimozione avviene invocando il metodo 
	 * <code>delete</code> della classe <code>AlberoBR</code>.
	 * Successivamente, si risale l'albero verso la radice 
	 * utilizzando i metodi <code>bil</code> e <code>aggiornaAltezza</code>
	 * per ricalcolare i fattori di bilanciamento mutati. 
	 * Nel caso si individui un nodo con fattore di bilanciamento pari a +/-2, si
	 * ribilancia l'albero in quel punto tramite il metodo <code>ruota</code>.
	 * 
	 * @param i riferimento dell'elemento da cancellare
	 */
	public void delete(Rif i) {
		Nodo p = super.delete((InfoAVL)i);
		while (p != null) {
			if (bil(p)==-2 || bil(p)==2) ruota(p);
			else aggiornaAltezza(p);
			p = alb.padre(p);
		}
	}

	/**
	 * Opera una rotazione a sinistra sul nodo di input <code>u</code>
	 * ed aggiorna l'altezza dei nodi coinvolti (<font color=red>Tempo O(log(n))</font>).
	 * L'operazione viene realizzata richiamando il metodo <code>ruotaSin</code>
	 * della classe padre <code>AlberoBRR</code> ed aggiornando
	 * l'altezza dei nodi coinvolti nella rotazione. 
	 * 	 
	 * @param u il nodo su cui operare la rotazione a sinistra
	 */	
	protected void ruotaSin(Nodo u) {
		super.ruotaSin(u);
		aggiornaAltezza(alb.sin(u));
		aggiornaAltezza(u);
	}

	/**
	 * Opera una rotazione a destra sul nodo di input <code>u</code>
	 * ed aggiorna l'altezza dei nodi coinvolti.
	 * L'operazione viene realizzata richiamando il metodo <code>ruotaSin</code>
	 * della classe padre <code>AlberoBRR</code> ed aggiornando
	 * l'altezza dei nodi coinvolti nella rotazione. 
	 * 	 
	 * @param u il nodo su cui operare la rotazione a destra
	 */	
	protected void ruotaDes(Nodo u) {
		super.ruotaDes(u);
		aggiornaAltezza(alb.des(u));
		aggiornaAltezza(u);
	}

	/**
	 * Sceglie ed esegue le rotazioni necessarie a bilanciare un nodo di input v sbilanciato.
	 * La scelta viene fatta analizzando il fattore di bilanciamento di <code>v</code>
	 * e quello dei suoi figli ed implica l'esecuzione di una o due rotazioni.
	 * 
	 * @param v il nodo sbilanciato su cui operare il ribilanciamento
	 */
	private void ruota(Nodo v) {
		switch (bil(v)) {
		  	case +2:
				if (bil(alb.sin(v)) > -1)  // Caso SS (0, +1)
					ruotaDes(v); 
				else { 				       // Caso SD (-1)
					ruotaSin(alb.sin(v));
					ruotaDes(v);
				}
				break;
		    case -2:
				if (bil(alb.des(v)) < +1)  // Caso DD (-1, 0)
					ruotaSin(v);
				else {  				   // Caso DS (+1)
					ruotaDes(alb.des(v));
					ruotaSin(v);
				}
				break;
		}
	}

	/**
	 * Aggiorna l'altezza di un nodo di input <code>v</code>.
	 * L'altezza aggiornata viene determinata a partire dall'altezza
	 * dei figli di <code>v</code>.
	 * 
	 * @param v il nodo di cui si intende aggiornare l'altezza
	 */
	private void aggiornaAltezza(Nodo v){
		InfoAVL i = (InfoAVL) alb.info(v);
		int hsin = altezza(alb.sin(v));
		int hdes = altezza(alb.des(v));
		i.altezza = Math.max(hsin, hdes) + 1;
	}

	/**
	 * Restituisce l'altezza di un nodo. L'informazione viene determinata
	 * accedendo al campo <code>altezza</code> dell'oggetto <code>InfoAVL</code> associato al nodo.
	 * 
	 * @param v
	 * @return l'altezza di <code>v</code>
	 */
	private int altezza(Nodo v) {
		if (v == null) return 0;
		return ((InfoAVL)alb.info(v)).altezza;
	}

	/**
	 * Restituisce il fattore di bilanciamento di un nodo <code>v</code>. L'informazione
	 * viene determinata confrontando le altezze dei figli di <code>v</code>.
	 * 
	 * @param v il nodo di cui si vuol conoscere il fattore di bilanciamento
	 * @return il fattore di bilanciamento di <code>v</code>
	 */
	private int bil(Nodo v) {
		if (v == null) return 0;
		return altezza(alb.sin(v)) - altezza(alb.des(v));
	}
}

