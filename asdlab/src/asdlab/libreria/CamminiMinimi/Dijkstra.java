package asdlab.libreria.CamminiMinimi;

import asdlab.libreria.Grafi.*;
import asdlab.libreria.Alberi.Albero;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.CodePriorita.*;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: Dijkstra.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.6 $
 */

/**
 * La classe <code>Dijkstra</code> implementa l'algoritmo di Dijkstra
 * per la soluzione del problema dei cammini minimi a sorgente singola di
 * un grafo <code>G</code> orientato. 
 * L'implementazione dell'algoritmo &egrave; codificata come una specializzazione dell'algoritmo
 * di visita generico implementato dalla classe <code>VisitaGenericaAlb</code> da cui 
 * &egrave; derivata la stessa classe <code>Dijkstra</code>.<br>
 * In particolare, l'algoritmo di Dijkstra esplora il grafo <code>G</code>
 * costruendo un albero <code>T'</code> radicato nel nodo <code>s</code>
 * da cui occorre calcolare i cammini minimi. Al termine dell'esecuzione <code>T'</code>
 * coincide con l'albero <code>T</code> dei cammini minimi da <code>s</code>.
 * Ad ogni passo, un nodo della frangia di <code>T'</code> viene visitato
 * ed espanso: il nodo &egrave; scelto come quello avente priorit &agrave; minima.
 * La classe <code>Dijkstra</code> ha tre variabili di istanza:  <code>f</code>
 * rappresenta la coda con priorit&agrave;, l'array <code>r</code> di oggetti
 * di tipo generico <code>Rif</code> mantiene i riferimenti agli elementi
 * di <code>f</code>, e l'array <code>d</code> memorizza le priorit&agrave;
 * dei nodi, ovvero le distanze (presunte) da <code>s</code>.
 * Le distanze iniziali dei nodi di <code>G</code> da <code>s</code> mantenute da <code>d</code>
 * sono tutte inizialmente poste al massimo valore rappresentabile.
 * Durante l'algoritmo di visita, il metodo <code>serveAggiornamento</code>,
 * quando si esamina l'arco <code>a=(u,v)</code>, restituisce <code>true</code>
 * solo se la distanza corrente di <code>v</code> dalla sorgente <code>s</code>
 * &egrave; maggiore della distanza di <code>u</code> pi&ugrave; il
 * peso dell'arco <code>a</code>. Nel caso in cui l'aggiornamento sia
 * necessario, il metodo <code>aggiorna</code> rende innanzitutto <code>u</code>
 * nuovo padre di <code>v</code> nell'albero. La priorit&agrave; della destinazione
 * <code>v</code> viene poi modificata con la distanza ottenuta passando per
 * l'arco <code>a</code>, e un'operazione <code>decreaseKey</code> sul nodo
 * <code>v</code> va eseguita nella coda con priorit&agrave;. Infine,
 * la classe <code>Dijkstra</code> assume che il costo di ciascun arco sia codificato
 * come valore numerico di tipo <code>Double</code> nel campo <code>info</code> degli archi del grafo. <br>
 * Il tempo di esecuzione dell'algoritmo, eseguito mediante il metodo <code>calcola</code>, &egrave; influenzato dal tipo di coda con priorit&agrave;
 * utilizzata per mantenere i nodi della frangia in funzione della loro distanza
 * dal nodo di origine <code>s</code>. Nella fattispecie, la classe <code>Dijkstra</code>
 * implementa la coda con priorit&agrave; come istanza della classe {@link DHeap}, con d=2.
 * La complessit&agrave; risultante &egrave; di <font color=red>O(m&middot;log(n))</font>, dove
 * n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>.
 */


public class Dijkstra extends VisitaGenericaAlb implements SSSP {

	/**
	 * Mantiene i nodi del grafo in funzione della loro distanza dal nodo sorgente <code>s</code>
	 */
	protected CodaPriorita f;
	
	/**
	 * Array utilizzato per memorizzare i riferimenti agli elementi della coda <code>f</code>
	 */
	protected Rif[] r;

	/**
	 * Array utilizzato per memorizzare le distanze di ciascun nodo dal nodo sorgente <code>s</code>
	 */
	protected double[] d;

	/**
	 * Restituisce l'array contenente le distanze dei cammini minimi
	 * di ciascun nodo di un grafo <code>g</code> dal nodo sorgente <code>s</code> (<font color=red>Tempo O(1)</font>).
	 * I valori fanno riferimento all'ultima esecuzione del metodo <code>calcola</code>.
	 * Il generico valore di posizione <code>i</code> riporta la distanza
	 * da <code>s</code> del nodo di indice <code>i</code>.
	 * 
	 * @return l'array delle distanze dei cammini minimi
	 */
	public double[] distanze() { return d; }

	/**
	 * Restituisce l'albero dei cammini minimi di un grafo <code>g</code>
	 * dal nodo sorgente <code>s</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return l'albero dei cammini
	 */
	public Albero albero() {
		return super.albero();
	}

	
	/**
	 * Inizializza le strutture dati utilizzate nell'algoritmo di Dijkstra. La coda con priorit&agrave; <code>f</code>
	 * viene mantenuta come istanza della classe <code>DHeap</code>. Si inizializzano inoltre gli array
	 * <code>r</code> e <code>d</code> associando a ciascun nodo del grafo una priorit&agrave; iniziale pari a
	 * 0. Infine, si inserisce nella coda con priorit&agrave; il nodo sorgente <code>s</code> associandovi
	 * la distanza 0.
	 * 
	 * @param s il nodo sorgente dell'albero dei cammini
	 */
	protected void inizializza(Nodo s) {
		super.inizializza(s);
		f = new DHeap(2);
		r = new Rif[g.numNodi()];
		d = new double[g.numNodi()];
		for (int i = 0; i < g.numNodi(); i++) 
			d[i] = Double.MAX_VALUE;
		d[g.indice(s)] = 0;
		r[g.indice(s)] = f.insert(s, d[g.indice(s)]);
	}

	/**
	 * Controlla se la frangia &egrave; vuota.
	 * 
	 * @return <code>true</code>, se la frangia &egrave; vuota. <code>false</code>, altrimenti.
	 */
	protected boolean frangiaVuota() {
		return f.isEmpty();
	}

	/**
	 * Estrae il nodo <code>v</code> con distanza minima dalla sorgente. 
	 * Il nodo viene estratto dalla coda <code>f</code> mediante il metodo <code>deleteMin</code>.
	 * 
	 * @return il nodo a distanza minima dalla sorgente presente in <code>f</code>
	 */
	protected Nodo estrai() {
		return (Nodo)f.deleteMin();
	}

	/**
	 * Aggiunge l'arco <code>a=(u,v)</code> all'albero dei cammini minimi. L'aggiunta avviene 
	 * innanzitutto richiamando il metodo <code>inserisci</code> della classe
	 * <code>VisitaGenericaAlb</code> da cui <code>Dijkstra</code> deriva. Dopodich&eggrave;,
	 * si associa come distanza al nodo <code>v</code> la distanza del nodo <code>u</code>
	 * aumentata del costo dell'arco <code>a</code> e si inserisce <code>v</code>
	 * con la sua priorit&agrave; aggiornata nella coda <code>f</code>.
	 * 
	 * @param a l'arco da aggiungere al minimo albero ricoprente
	 */
	protected void inserisci(Arco a) {
		super.inserisci(a);
		int u = g.indice(a.orig), v = g.indice(a.dest);
		d[v] = d[u] + (Double)g.infoArco(a);
		r[v] = f.insert(a.dest, d[v]);
	}

	/**
	 * Verifica se la distanza del nodo di destinazione dell'arco <code>a=(u,v)</code>
	 * ha un valore  maggiore di quella indotta dal costo dell'arco <code>a</code>.
	 * 
	 * @param a l'arco in base al quale verificare se aggiornare la distanza di <code>v</code>
	 * @return <code>true</code>, se la distanza di <code>v</code> deve essere aggiornata. <code>false</code>, altrimenti
	 */
	protected boolean serveAggiornamento(Arco a){ 
		int u = g.indice(a.orig), v = g.indice(a.dest);
		return d[u] + (Double)g.infoArco(a) < d[v]; 
	}

	
	/**
	 * Aggiorna l'albero dei cammini minimi rispetto all'arco <code>a=(u,v)</code>.
	 * Il metodo opera innanzitutto rendendo <code>u</code> nuovo padre di <code>v</code>
	 * nell'albero ed aggiorna la distanza della destinazione <code>v</code> con
	 * la distanza ottenuta passando per l'arco <code>a</code>. Il nuovo valore
	 * della distanza di <code>v</code> viene aggiornato anche nella coda <code>f</code>
	 * mediante il metodo <code>decreaseKey</code>.
	 * 
	 * 
	 * @param a l'arco in base al quale operare l'aggiornamento
	 */
	protected void aggiorna(Arco a) {
		super.aggiorna(a);
		int u = g.indice(a.orig), v = g.indice(a.dest);
		d[v] = d[u] + (Double)g.infoArco(a);
		f.decreaseKey(r[v], d[v]);
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