package asdlab.libreria.UnionFind;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.StruttureElem.Rif;

/**
 * La classe <code>QuickFindBilanciato</code> deriva la classe
 * <code>QuickFind</code> introducendo una euristica di bilanciamento
 * sull'operazione di <code>union</code>. L'euristica opera innanzitutto
 * associando a ciascun albero la sua cardinalit&agrave;, esplicitamente
 * mantenuta nella radice dell'albero stesso. Ad ogni operazione
 * di <code>union</code> di due insiemi, <code>QuickFindBilanciato</code>
 * determina l'ordine di fusione in modo da fondere l'albero di cardinalit&agrave;
 * minore nell'albero di cardinalit&agrave; maggiore.
 *
 */
public class QuickFindBilanciato extends QuickFind {

	/**
	 * Crea un nuovo insieme e ne restituisce il riferimento (<font color=red>Tempo O(1)</font>).
	 * L'insieme viene creato istanziando un nuovo albero di tipo
	 * <code>AlberoPFFS</code> contenente una radice ed un solo figlio, rappresentante
	 * dell'unico elemento dell'insieme creato. Alla radice dell'albero
	 * creato viene associata la cardinalit&agrave; 1.
	 * 
	 * @return il riferimento all'elemento dell'insieme creato
	 */
	public Rif makeSet() {
		Albero alb = new AlberoPFFS();
		alb.aggiungiRadice(1);
		return alb.aggiungiFiglio(alb.radice(), 1);
	}
	
	/**
	 * Fonde gli insiemi <code>alb1</code> ed <code>alb2</code> indicati
	 * da input secondo le loro cardinalit&agrave; (<font color=red>Tempo O(n)</font>). 
	 * La fusione avviene spostando i figli dell'albero di cardinalit&agrave; 
	 * nell'albero di cardinalit&agrave; maggiore
	 * mediante operazioni di <code>innesta</code> e <code>pota</code> di <code>Albero</code>.
	 * Nel caso in cui <code>alb1</code> ed <code>alb2</code> abbiano la stessa cardinalit&agrave;
	 * <code>alb2</code> viene fuso in <code>alb1</code>. 
	 * Infine, aggiorna la cardinalit&agrave; dell'albero derivante dalla fusione.
	 * 
	 * @param alb1 il riferimento all'elemento contenuto nel primo insieme da fondere
	 * @param alb2 il riferimento all'elemento contenuto nel secondo insieme da fondere
	 * @return il riferimento all'insieme derivante dalla fusione
	 */
	public Rif union(Albero alb1, Albero alb2) {
		int size1 = (Integer) alb1.info(alb1.radice());
		int size2 = (Integer) alb2.info(alb2.radice());
		if (size1 < size2){
			alb2.cambiaInfo(alb2.radice(), size1 + size2);
			return super.union(alb2, alb1);
		}
		alb1.cambiaInfo(alb1.radice(), size1 + size2);
		return super.union(alb1, alb2);
	}
}
