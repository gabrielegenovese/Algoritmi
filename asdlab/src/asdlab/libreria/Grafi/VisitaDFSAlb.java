package asdlab.libreria.Grafi;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.StruttureElem.*;

/* ============================================================================
 *  $RCSfile: VisitaDFSAlb.java,v $
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
 * La classe <code>VisitaDFSAlb</code> implementa un algoritmo iterativo di visita in profondit&agrave;
 * di un grafo <code>G</code> a partire da un nodo sorgente <code>s</code> e genera un albero <code>T</code>
 * definito albero DFS (depth-first search). 
 * In particolare, la classe implementa l'algoritmo di
 * visita in profondit&agrave; come estensione dell'algoritmo
 * generico implementato da <code>VisitaGenericaAlb</code>, da cui la classe deriva, gestendo l'insieme
 * di nodi <code>F</code> appartenenti alla frangia dell'albero <code>T</code>
 * mediante il tipo di dato pila specificato dall'interfaccia <code>Pila</code>.
 * La pila <code>F</code> registra implicitamente l'ordine in cui i nodi sono
 * esaminati dall'algoritmo di visita in ampiezza: i nodi in fondo a <code>F</code>
 * sono stati esaminati per primi, mentre i nodi in cima ad <code>F</code>
 * sono stati esaminati per ultimi. Nel generico passo, l'algoritmo seleziona
 * il nodo in cima alla pila, ovvero il nodo che si trova da meno
 * tempo nella frangia <code>F</code> dell'albero <code>T</code>.
 * La classe <code>VisitaDFSAlb</code> implementa inoltre l'interfaccia
 * <code>VisitaAlb</code> definendo un metodo, <code>calcolaLista</code>,
 * che, una volta invocato, calcola la lista dei nodi incontrati
 * durante la visita DFS del grafo <code>G</code> a partire dal nodo <code>s</code>.<br>
 * Il tempo di esecuzione dell'algoritmo di visita, eseguito tramite il metodo <code>calcola</code>,
 * &egrave; <font color=red>O(n+m)</font>
 * dove n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>,
 * nel caso il grafo <code>G</code> sia memorizzato con liste di adiacenza.
 *
 */

public class VisitaDFSAlb extends VisitaGenericaAlb implements VisitaAlb {

	/**
	 * Pila utilizzata per la rappresentazione della frangia <code>F</code>
	 */
	private Pila f;

	/**
	 * Calcola e restituisce l'albero dei nodi esplorati durante la visita
	 * DFS del grafo <code>g</code> a partire dal nodo <code>s</code>
	 * 
	 * @param g il grafo da visitare
	 * @param s il nodo di partenza della visita
	 * @return l'albero dei nodi esplorati dalla visita
	 */
	public Albero calcolaAlbero(Grafo g, Nodo s) {
		calcola(g, s);
		return albero();
	}

	
	/**
	 * Restituisce l'albero della visita DFS del grafo <code>g</code>
	 * a partire dal nodo <code>s</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return l'albero dei cammini
	 */
	public Albero albero() {
		return super.albero();
	}
	
	/**
	 * Inizializza le strutture dati necessarie alla visita. 
	 * La pila utilizzata per la rappresentazione di <code>F</code> viene allocata
	 * come istanza della classe <code>PilaArray</code> e viene
	 * inizialmente popolata con il nodo <code>s</code>.
	 */
	protected void inizializza(Nodo s) {
		super.inizializza(s);
		f = new PilaArray();
		f.push(s);
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
	 * Il nodo viene determinato mediante una operazione di <code>pop</code>
	 * dalla pila <code>f</code>.
	 * 
	 * @return il prossimo nodo restituito dalla frangia
	 */
	protected Nodo estrai() {
		return (Nodo)f.pop();
	}

	/**
	 * Agginge all'albero della visita l'arco <code>a</code> ed
	 * aggiorna la frangia di conseguenza. L'aggiornamento
	 * della frangia si realizza mediante
	 * una operazione di <code>push</code> del nodo di destinazione
	 * di <code>a</code> sulla pila <code>f</code>.
	 *  
	 * @param a il nuovo arco da aggiungere a <code>T</code>
	 */
	protected void inserisci(Arco a) {
		super.inserisci(a);
		f.push(a.dest);
	}

	/**
	 * Determina se &egrave; necessario aggiornare la frangia rispetto
	 * all'arco di input <code>a</code>. L'aggiornamento
	 * viene richiesto nel caso lo stato del nodo sia aperto.
	 * 
	 * @param a l'arco in base al quale verificare se aggiornare la frangia
	 * @return <code>true</code>, se lo stato di <code>a</code> &egrave; aperto. <code>false</code>, altrimenti.
	 */
	protected boolean serveAggiornamento(Arco a) { 
		return stato[g.indice(a.dest)] == Stato.APERTO; 
	}

	/**
	 * Aggiorna la frangia considerando l'arco di input <code>a</code>
	 * e l'albero della visita <code>T</code>. L'aggiornamento
	 * avviene aggiungendo alla frangia il nodo di destinazione
	 * di <code>a</code> mediante una operazione di <code>push</code>.
	 * 
	 * @param a l'arco da utilizzare per l'aggiornamento
	 */
	protected void aggiorna(Arco a) { 
		super.aggiorna(a);
		f.push(a.dest);
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