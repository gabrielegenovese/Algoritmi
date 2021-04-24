package asdlab.libreria.Grafi;

import java.util.*;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.StruttureElem.*;

/* ============================================================================
 *  $RCSfile: VisitaBFS.java,v $
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
 * La classe <code>VisitaBFS</code> implementa un algoritmo iterativo di visita in ampiezza
 * di un grafo <code>G</code> (breadth-first search, BFS) a partire da un nodo sorgente <code>s</code>. 
 * In particolare, la classe implementa l'algoritmo di
 * visita in ampiezza come estensione dell'algoritmo
 * generico implementato da <code>VisitaGenerica</code>, da cui la classe deriva, gestendo l'insieme
 * di nodi <code>F</code> appartenenti alla frangia dell'albero <code>T</code>
 * mediante il tipo di dato coda specificato dall'interfaccia <code>Coda</code>.
 * La coda <code>F</code> registra implicitamente l'ordine in cui i nodi sono
 * esaminati dall'algoritmo di visita in ampiezza: i nodi in testa a <code>F</code>
 * sono stati esaminati per primi, mentre i nodi in coda ad <code>F</code>
 * sono stati esaminati per ultimi. Nel generico passo, l'algoritmo seleziona
 * il nodo in testa alla coda, ovvero il nodo che si trova da pi&ugrave;
 * tempo nella frangia <code>F</code> dell'albero <code>T</code>.
 * La classe <code>VisitaBFS</code> implementa inoltre l'interfaccia
 * <code>Visita</code> definendo un metodo, <code>calcolaLista</code>,
 * che, una volta invocato, calcola la lista dei nodi incontrati
 * durante la visita BFS del grafo <code>G</code> a partire dal nodo <code>s</code>.<br>
 * Il tempo di esecuzione dell'algoritmo di visita, eseguito tramite il metodo <code>calcola</code>,
 * &egrave; <font color=red>O(n+m)</font>
 * dove n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>,
 * nel caso il grafo <code>G</code> sia memorizzato con liste di adiacenza.
 *
 */
public class VisitaBFS extends VisitaGenerica implements Visita {
	
	/**
	 * Coda utilizzata per la rappresentazione della frangia <code>F</code>
	 */
	private Coda f;
	
	/**
	 * Lista dei nodi sinora incontrati durante la visita del grafo
	 */
	private List l;

	/**
	 * Calcola e restituisce la lista dei nodi del grafo di input <code>g</code>
	 * nell'ordine determinato da una visita BFS
	 * 
	 * @param g il grafo da visitare
	 * @param s il nodo di partenza della visita
	 * @return la lista dei nodi esplorati durante la visita BFS
	 */
	public List calcolaLista(Grafo g, Nodo s) { 
		calcola(g, s);
		return l; 
	}

	/**
	 * Inizializza le strutture dati necessarie alla visita. 
	 * La coda utilizzata per la rappresentazione di <code>F</code> viene allocata
	 * come istanza della classe <code>CodaCollegata</code> e viene
	 * inizialmente popolata con il nodo <code>s</code>.
	 */
	protected void inizializza(Nodo s) {
		l = new LinkedList();
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
	 * Estrae dalla frangia il prossimo nodo da considerare nella visita.
	 * Il nodo viene determinato mediante una operazione di <code>dequeue</code>
	 * dalla coda <code>f</code>.
	 * 
	 * @return il prossimo nodo restituito dalla frangia
	 */
	protected Nodo estrai() {
		return (Nodo)f.dequeue();
	}

	/**
	 * Visita il nodo di input <code>u</code>. La visita avviene
	 * aggiungendo il nodo <code>u</code> alla lista <code>l</code>
	 * dei nodi sinora esplorati.
	 * 
	 * @param u il nodo da visitare
	 */
	protected void visita(Nodo u) { 
		l.add(g.indice(u));
	}

	/**
	 * Aggiunge alla frangia il nodo di destinazione dell'arco di input <code>a</code>.
	 * L'aggiunta si realizza mediante una operazione di <code>enqueue</code> sulla coda <code>f</code>.
	 *  
	 * @param a il nuovo arco da aggiungere a <code>T</code>
	 */
	protected void inserisci(Arco a) {
		f.enqueue(a.dest);
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
