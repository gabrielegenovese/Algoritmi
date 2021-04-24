package asdlab.libreria.AlberiRicerca;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.StruttureElem.*;

/* ============================================================================
 *  $RCSfile: AlberoBR.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:43:23 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.33 $
 */


/**
 * La classe <code>AlberoBR</code> implementa il tipo di dato
 * Dizionario mediante un albero binario di ricerca.
 * L'implementazione dell'albero binario di ricerca si realizza
 * mantenendo una variabile di istanza <code>alb</code> di tipo <code>AlberoBin</code>,
 * in cui viene mantenuto l'albero di ricerca. <br> Rispetto ad un albero binario, 
 * ogni nodo deve mantenere le informazioni relative alle coppia (elem, chiave). 
 * Per fare questo, si definisce una classe <code>InfoBR</code> che implementa il tipo <code>Rif</code>: 
 * la classe <code>InfoBR</code> &egrave; protetta e quindi sar&agrave; visibile anche dalle altre classi
 * che derivano dalla classe <code>AlberoBR</code>.  Si mantiene inoltre la propriet&agrave; che
 * il campo <code>info</code> di un nodo contiene un riferimento al corrispondente oggetto
 * della classe <code>InfoBR</code>: in tal modo, sar&agrave; sempre possibile risalire da un nodo
 * al suo corrispondente oggetto della classe <code>InfoBR</code>. 
 *
 */

public class AlberoBR implements Dizionario {

	/** 
	 * Classe definita localmente alla classe <code>AlberoBR</code>
	 * per il mantenimento dei record contenenti le coppie (elem, chiave). 
	 * Ciascun oggetto della classe <code>InfoBR</code>, oltre alle informazioni
	 * sulla coppia (elem,chiave), contiene un riferimento al nodo corrispondente
	 * a tale coppia.
	 *
	 */
 
	protected class InfoBR implements Rif {
		/**
		 * Elemento da conservare nel dizionario
		 */
		protected Object elem;
		/**
		 * Chiave associata ad <code>elem</code>
		 */
		protected Comparable chiave;
		/**
		 * Riferimento al nodo dell'albero binario di ricerca contenente
		 * la coppia (elem, chiave)
		 */
		protected Nodo nodo;
		/**
		 * Costruttore per l'allocazione di una nuova istanza di <code>InfoBR</code>
		 * 
		 * @param e elemento da conservare nel dizionario
		 * @param k chiave associata ad <code>e</code>
		 */
		protected InfoBR(Object e, Comparable k){
			elem = e; chiave = k; nodo = null;
		}
	}

	/**
	 * Riferimento all'albero binario utilizzato per la rappresentazione
	 * dell'albero binario di ricerca.
	 */
	protected AlberoBin alb;

	/**
	 * Costruttore per l'allocazione di un nuovo albero binario di ricerca.
	 *
	 */
	public AlberoBR() {
		alb = new AlberoBinPF();
	}

	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code> (<font color=red>Tempo O(altezza albero)</font>).
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca avviene confrontando la chiave <code>k</code>
	 * con la chiave del nodo radice. Se le due chiavi coincidono, l'elemento &egrave;
	 * stato individuato. Se il valore di <code>k</code> &egrave; inferiore
	 * al valore della chiave della radice, la ricerca procede nel sottoalbero
	 * sinistro, altrimenti, procede nel sottoalbero destro.
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave k, <code>null</code> se assente
	 */

	public Object search(Comparable k) {
		Nodo v = alb.radice();
		while (v != null) {
			InfoBR i = (InfoBR)alb.info(v);
			if (k.equals(i.chiave)) return i.elem;
			if (k.compareTo(i.chiave) < 0)  v = alb.sin(v);
			else v = alb.des(v);
		}
		return null;
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo O(altezza albero)</font>). L'inserimento
	 * avvviene allocando una nuova istanza dell'oggetto <code>InfoBR</code>
	 * contenente la coppia <code>(e,k)</code> ed utilizzando il metodo protetto
	 * <code>insert</code> per inserirla all'interno dell'albero binario di ricerca.
	 * Nel caso in cui l'albero sia vuoto, la nuova coppia <code>(e,k)</code> viene
	 * collocata nella radice dell'albero.
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 * @return il riferimento all'elemento <code>e</code> nel dizionario  
	 */
	
	public Rif insert(Object e, Comparable k) {
		InfoBR info = new InfoBR(e, k);
		insert(info);
		return info;
	}

	/**
	 * Rimuove dal dizionario l'elemento <code>r</code> (<font color=red>Tempo O(altezza albero)</font>).
	 * La rimozione avviene invocando il metodo protetto
	 * <code>delete</code>.
	 * 
	 * @param r riferimento dell'elemento da cancellare
	 */
	public void delete(Rif r) {
		delete((InfoBR)r);
	}

	/**
	 * Inserisce un oggetto <code>i</code> di tipo <code>InfoBR</code> nella corretta
	 * posizione all'interno dell'albero binario di ricerca. La posizione di inserimento
	 * viene determinata ricercando il genitore <code>p</code> del nuovo nodo secondo
	 * un criterio simile a quello adottato dal metodo <code>search</code>. 
	 * Nel caso in cui l'albero sia vuoto, l'oggetto <code>i</code> viene
	 * collocato nella radice dell'albero.
	 * 
	 * @param i l'oggetto di tipo <code>InfoBR</code> da inserire nell'albero binario di ricerca
	 * @return il riferimento al nodo dell'albero contenente l'oggetto inserito
	 */
	protected Nodo insert(InfoBR i) {
		if (alb.radice() == null) {
			i.nodo = alb.aggiungiRadice(i);
			return i.nodo;
		}
		Nodo v = alb.radice(), p = null;
		while (v != null) {
			p = v;
			if (i.chiave.compareTo(((InfoBR)alb.info(v)).chiave) > 0)
				 v = alb.des(v);
			else v = alb.sin(v);
		}
		if (i.chiave.compareTo(((InfoBR)alb.info(p)).chiave) > 0)
			 i.nodo = alb.aggiungiFiglioDes(p, i);
		else i.nodo = alb.aggiungiFiglioSin(p, i);
		return i.nodo;
	}

	/**
	 * Rimuove il nodo contenente l'oggetto <code>i</code>
	 * di tipo <code>InfoBR</code> dall'albero di ricerca.
	 * Nel caso in cui il nodo abbia due figli, si utilizza
	 * il metodo <code>scambiaInfo</code> per scambiarne
	 * il contenuto informativo con il suo predecessore.
	 * Dopodich&eacute;, si utilizza il metodo <code>contraiNodo</code>
	 * per l'eliminazione fisica del nodo (che avr&agrave; ora al pi&ugrave;
	 * un figlio) dall'albero.
	 *  
	 * @param i l'oggetto di tipo <code>InfoBR</code> associato al nodo da rimuovere dall'albero
	 * @return il riferimento al padre del nodo dell'albero rimosso
	 */
	protected Nodo delete(InfoBR i) {
		Nodo v = i.nodo;
		if (alb.grado(v) == 2) {
			Nodo pred = max(alb.sin(v));
			scambiaInfo(v, pred);
			v = pred;
		}
		Nodo p = alb.padre(v);
		contraiNodo(v);
		return p;
	}


	/**
	 * Rimuove il nodo di input <code>v</code> dall'albero agganciando
	 * il suo eventuale figlio al padre di <code>v</code>. Assume che il
	 * nodo <code>v</code> abbia al pi&ugrave; un figlio.
	 * 
	 * @param v il nodo da eliminare
	 */
	protected void contraiNodo(Nodo v){
		Nodo f = null ;
		if (alb.sin(v) != null) f = alb.sin(v); else
		if (alb.des(v) != null) f = alb.des(v);
		if (f == null) alb.pota(v);
		else {
			scambiaInfo(v, f);
			AlberoBin a = alb.pota(f);
			alb.innestaSin(v, a.pota(a.sin(f)));
			alb.innestaDes(v, a.pota(a.des(f)));
		}
	}

	/**
	 * Scambia il contenuto informativo di due nodi.
	 * 
	 * @param n1 il primo nodo di cui si vuole scambiare il contenuto informativo
	 * @param n2 il secondo nodo di cui si vuole scambiare il contenuto informativo
	 */
	protected void scambiaInfo(Nodo n1, Nodo n2){
		InfoBR i1 = (InfoBR)alb.info(n1);
		InfoBR i2 = (InfoBR)alb.info(n2);
		i1.nodo = n2;
		i2.nodo = n1;
		alb.cambiaInfo(n1, i2);
		alb.cambiaInfo(n2, i1);
	}

	/**
	 * Determina il nodo con chiave massima del sottoalbero radicato
	 * nel nodo di input <code>v</code>. Il nodo viene determinato
	 * discendendo, fin quando possibile, il lato destro del sottoalbero 
	 * radicato in <code>v</code>.
	 * 
	 * @param v la radice del sottoalbero di cui si vuole conoscere il nodo con chiave massima
	 * @return il nodo con chiave massima nel sottoalbero radicato in <code>v</code>
	 */
	protected Nodo max(Nodo v) {
		Nodo p = v;
		while (v != null){
			p = v;
			v = alb.des(v);
		}
		return p;
	}

}

/*
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo, Irene
 * Finocchi, Giuseppe F. Italiano
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */