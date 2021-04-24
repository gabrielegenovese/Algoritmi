package asdlab.libreria.Grafi;

import asdlab.libreria.Alberi.*;
/* ============================================================================
 *  $RCSfile: VisitaGenericaAlb.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:17 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.7 $
 */
/**
 * La classe <code>VisitaGenericaAlb</code> estende la classe <code>VisitaGenerica</code> e vi aggiunge la possibilit&agrave;
 * di mantenere esplicitamente l'albero della visita calcolato dall'algoritmo. L'albero
 * viene rappresentato come istanza della classe <code>AlberoPFFS</code> e la sua
 * costruzione &egrave; realizzata ridefinendo i metodi <code>inizializza</code>, <code>inserisci</code>
 * e <code>aggiorna</code> di <code>VisitaGenerica</code>. La classe mantiene inoltre un array, <code>nodiAlb</code>, con l'elenco
 * dei nodi attualmente presenti nell'albero. Tale array stabilisce la corrispondenza tra nodi
 * del grafo e nodi dell'albero garantendo che <code>nodiAlb[i]</code> contenga il riferimento
 * al nodo dell'albero relativo al nodo di indice <code>i</code> del grafo e consentendo
 * quindi di conoscere, dato il nodo di un grafo, il suo corrispondente nodo nell'albero.
 * Inoltre, nel campo <code>info</code> dei nodi dell'albero viene mantenuto l'indice del corrispondente
 * nodo del grafo, consentendo quindi di conoscere, per ciascun nodo dell'albero, il nodo del grafo
 * cui esso fa riferimento.<br>
 * Il tempo di esecuzione dell'algoritmo di visita, eseguito tramite il metodo <code>calcola</code>,
 * &egrave; <font color=red>O(n+m)</font>
 * dove n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>,
 * nel caso il grafo <code>G</code> sia memorizzato con liste di adiacenza.
 */

public abstract class VisitaGenericaAlb extends VisitaGenerica {

	/**
	 * L'albero derivante dalla visita del grafo
	 */
	protected Albero  alb;
	
	/**
	 * Array contenente i nodi dell'albero. Utilizzato per mantenere
	 * la corrispondenza tra i nodi dell'albero ed i nodi del grafo di cui esso
	 * rappresenta la visita
	 */
	protected Nodo[]  nodiAlb;

	/**
	 * Restituisce l'albero della visita
	 * 
	 * @return l'albero della visita
	 */
	public Albero albero() { return alb; }
	
	/**
	 * Inizializza l'albero della visita a partire dal nodo <code>s</code>. L'albero viene
	 * creato come istanza della classe <code>AlberoPFFS</code> e radicato nel nodo <code>s</code>.
	 * Infine, si istanzia l'array <code>nodiAlb</code>.
	 * 
	 * @param s il nodo di partenza della visita
	 */
	protected void inizializza(Nodo s) {
		alb     = new AlberoPFFS();
		nodiAlb = new Nodo[g.numNodi()];
		int is = g.indice(s);
		nodiAlb[is] = alb.aggiungiRadice(is);
	}

	/**
	 * Aggiunge l'arco <code>a=(u,v)</code> all'albero della visita. L'aggiunta avviene 
	 * connettendo il nodo <code>v</code> al nodo di <code>alb</code> corrispondente a <code>u</code>
	 * mediante il metodo <code>aggiungiFiglio</code> ed inserendo il nodo dell'albero risultante
	 * in <code>nodiAlb</code>.
	 * 
	 * @param a il nuovo arco da aggiungere all'albero della visita
	 */
	protected void inserisci(Arco a) {
		int iu = g.indice(a.orig), iv = g.indice(a.dest);
		nodiAlb[iv] = alb.aggiungiFiglio(nodiAlb[iu], iv);
	}

	/**
	 * Aggiorna la frangia considerando l'arco di input <code>a=(x,y)</code>
	 * e l'albero della visita <code>alb</code>. Il nodo <code>y</code> viene
	 * disconnesso dalla sua precedente posizione in  <code>alb</code> e viene
	 * riconnesso come figlio di <code>x</code>.
	 * 
	 * @param a l'arco da utilizzare per l'aggiornamento
	 */
	protected void aggiorna(Arco a) { 
		Nodo u = nodiAlb[g.indice(a.orig)];
		Nodo v = nodiAlb[g.indice(a.dest)];
		alb.innesta(u, alb.pota(v));
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