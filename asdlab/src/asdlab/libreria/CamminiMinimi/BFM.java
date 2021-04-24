package asdlab.libreria.CamminiMinimi;

import asdlab.libreria.Alberi.Albero;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.Grafi.*;
import asdlab.libreria.StruttureElem.*;

/* ============================================================================
 *  $RCSfile: BFM.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:17 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.6 $
 */

/**
 * La classe <code>BFM</code> implementa l'algoritmo di Bellman, Ford e Moore
 * per la soluzione del problema dei cammini minimi a sorgente singola di
 * un grafo <code>G</code> orientato. 
 * L'implementazione dell'algoritmo &egrave; codificata come una specializzazione dell'algoritmo
 * di visita generico implementato dalla classe <code>VisitaGenericaAlb</code> da cui 
 * &egrave; derivata la stessa classe <code>BFM</code>.
 * La classe <code>BFM</code> ha due variabili di istanza: la coda <code>f</code> mantiene
 * i nodi appartenenti alla frangia dell'albero dei cammini minimi 
 * e l'array <code>d</code> memorizza le distanze (presunte) da un nodo sorgente <code>s</code>.
 * Le distanze iniziali dei nodi di <code>G</code> da <code>s</code> mantenute da <code>d</code>
 * sono tutte inizialmente poste al massimo valore rappresentabile.
 * Durante l'algoritmo di visita, il metodo <code>serveAggiornamento</code>,
 * quando si esamina l'arco <code>a=(u,v)</code>, verifica la condizione di Bellman sui nodi <code>u</code>
 * e <code>v</code>, restituendo <code>true</code> solo se la distanza corrente
 * di <code>v</code> dalla sorgente <code>s</code> &egrave; maggiore della distanza
 * di <code>u</code> pi&ugrave; il peso dell'arco <code>a</code>.
 * Nel caso in cui l'aggiornamento sia necessario, il metodo <code>aggiorna</code> rende
 * innanzitutto <code>u</code> nuovo padre di <code>v</code> nell'albero ed aggiorna la
 * distanza della destinazione <code>v</code> con la distanza ottenuta passando
 * per l'arco <code>a</code>. Se il nodo <code>v</code> era chiuso, poich&eacute; la 
 * sua distanza &egrave; diminuita, esso viene riaperto ed accodato. 
 * Infine, la classe <code>BFM</code> assume che il costo di ciascun arco sia codificato
 * come valore numerico di tipo <code>Double</code> nel campo <code>info</code> degli archi del grafo.<br>
 * Il tempo di esecuzione dell'algoritmo, eseguito mediante il metodo <code>calcola</code>, &egrave; di <font color=red>O(n*m)</font>, dove
 * n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>.
 */
public class BFM extends VisitaGenericaAlb implements SSSP {
	
	/**
	 * Coda utilizzata per mantenere l'insieme dei nodi appartenenti alla frangia dei cammini minimi
	 */
	protected Coda f;
	
	/**
	 * Array delle distanze (presunte) di ciascun nodo del grafo dal nodo sorgente <code>s</code>
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
	 * Inizializza le strutture dati utilizzate nell'algoritmo di Bellman, Ford e Moore.
	 * La coda <code>f</code> viene mantenuta come istanza della classe <code>CodaCollegata</code>.
	 * Si inizializza inoltre l'array <code>d</code> associando a ciascun nodo di <code>G</code>
	 * una distanza da <code>s</code> pari al massimo valore esprimibile
	 * per il tipo <code>Double</code>. Infine, si inserisce nella coda il nodo di partenza
	 * <code>s</code> associandovi la distanza 0.
	 * 
	 * @param s il nodo di origine dei cammini
	 */
	protected void inizializza(Nodo s) {
		super.inizializza(s);
		d = new double[g.numNodi()];
		for (int i = 0; i < g.numNodi(); i++) 
			d[i] = Double.MAX_VALUE;
		d[g.indice(s)] = 0;
		f = new CodaCollegata();
		f.enqueue(s);
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
	 * Estrae il prossimo nodo dalla frangia dell'albero dei cammini minimi.
	 * L'elemento viene estratto mediante il metodo <code>dequeue</code>
	 * da <code>f</code>. 
	 * 
	 * @return il prossimo nodo della frangia dell'albero dei cammini minimi
	 */
	protected Nodo estrai() {
		return (Nodo)f.dequeue();
	}

	/**
	 * Aggiunge l'arco <code>a=(u,v)</code> all'albero dei cammini minimi.
	 * L'aggiunta avviene  innanzitutto richiamando il metodo <code>inserisci</code> della classe
	 * <code>VisitaGenericaAlb</code> da cui <code>BFM</code> deriva. Dopodich&eacute;,
	 * si pone la distanza del nodo <code>v</code> pari alla distanza del nodo <code>u</code>
	 * aumentata del peso dell'arco <code>a</code>. Infine, si inserisce il nodo <code>v</code>
	 * nella coda <code>f</code>.
	 * 
	 * @param a l'arco da aggiungere all'albero dei cammini minimi
	 */
	protected void inserisci(Arco a) {
		super.inserisci(a);
		int u = g.indice(a.orig), v = g.indice(a.dest);
		d[v] = d[u] + (Double)g.infoArco(a);
		f.enqueue(a.dest);
	}

	/**
	 * Verifica se la distanza del nodo di destinazione dell'arco <code>a=(u,v)</code>
	 * ha un costo maggiore di quella indotta dall'utilizzo del percorso passante dall'arco <code>a</code>.
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
	 * la distanza ottenuta passando per l'arco <code>a</code>.
	 * Se il nodo <code>v</code> era chiuso, poich&eacute; la 
	 * sua distanza &egrave; diminuita, esso viene riaperto ed accodato ad <code>f</code>. 
	 * 
	 * @param a l'arco in base al quale operare l'aggiornamento
	 */
	protected void aggiorna(Arco a) {
		super.aggiorna(a);
		int u = g.indice(a.orig), v = g.indice(a.dest);
		d[v] = d[u] + (Double)g.infoArco(a);
		if (stato[v] == Stato.CHIUSO) {
			stato[v] = Stato.APERTO;
			f.enqueue(a.dest);
		}
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