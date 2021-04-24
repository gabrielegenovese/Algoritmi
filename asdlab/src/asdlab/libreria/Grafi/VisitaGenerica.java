package asdlab.libreria.Grafi;

import java.util.Iterator;
import asdlab.libreria.Alberi.*;

/* ============================================================================
 *  $RCSfile: VisitaGenerica.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/02 16:29:55 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.10 $
 */

/**
 * La classe <code>VisitaGenerica</code> implementa un algoritmo 
 * di visita generica di un grafo <code>G</code> a partire da un nodo sorgente <code>s</code> e costruisce
 * implicitamente un sottografo <code>T</code> di <code>G</code> i cui archi formano un albero radicato in <code>s</code>. 
 * Per evitare di visitare pi&ugrave; volte lo stesso nodo, l'algoritmo
 * adotta un'apposita marcatura dei nodi: ogni nodo pu&ograve;
 * essere <b>inesplorato</b>, <b>aperto</b> o <b>chiuso</b>. All'inizio, tutti i nodi saranno
 * <b>inesplorati</b>.<br> Quando l'algoritmo incontrer&agrave; un nodo per la prima volta,
 * ne cambier&agrave; lo stato in <b>aperto</b>. Infine, quando il nodo verr&agrave;
 * visitato definitivamente, esaminando tutti i suoi vicini, lo stato diventer&agrave;
 * <b>chiuso</b>. In ogni istante durante la visita, l'albero <code>T</code> contiene
 * i nodi incontrati fino a quel punto.<p>
 * L'algoritmo mantiene inoltre un insieme <code>F &sube; T</code> di nodi
 * che costituiscono la "frangia" di <code>T</code>, definita nel modo seguente.
 * Se un nodo <code>v</code> appartiene a <code>T - F</code>, allora anche tutti gli archi
 * di <code>G</code> incidenti su <code>v</code> sono gi&agrave; stati mai
 * incontrati dall'algoritmo: il nodo <code>v</code> &egrave; quindi <b>chiuso</b>.
 * Altrimenti, un nodo <code>v &isin; T</code> appartiene alla frangia <code>F</code>
 * se esistono ancora degli archi incidenti su <code>v</code> che non sono
 * stati esaminati dall'algoritmo: in tal caso il nodo &egrave; ancora <b>aperto</b>. 
 * L'albero e la frangia partizionano quindi i nodi del grafo in tre insiemi: <code>T - F</code>,
 * <code>F</code> e <code>V - T</code>, che coincidono rispettivamente con gli insiemi dei nodi <b>chiusi</b>, 
 * <b>aperti</b> e <b>inesplorati</b>. <br>
 * La classe <code>VisitaGenerica</code> offre numerosi metodi non ancora
 * specificati la cui successiva implementazione consente di realizzare diverse strategie di visita.
 * In particolare, la frangia <code>F</code> pu&ograve; essere gestita tramite strutture
 * dati anche molto differenti (quali pile, code o code con priorit&agrave;),
 * implementando di conseguenza le operazioni <code>inizializza</code>,
 * <code>frangiaVuota</code>, <code>estrai</code>, <code>inserisci</code> ed <code>aggiorna</code>.<br>
 * Il tempo di esecuzione dell'algoritmo di visita, eseguito tramite il metodo <code>calcola</code>,
 * &egrave; <font color=red>O(n+m)</font>
 * dove n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>,
 * nel caso il grafo <code>G</code> sia memorizzato con liste di adiacenza.
 * 
 */
public abstract class VisitaGenerica {
	
	/**
	 * Enumera i tre diversi possibili stati in cui
	 * pu&ograve; trovarsi ogni nodo del grafo durante la visita. I possibili
	 * stati sono:
	 * <ul>
	 * <li> INESPLORATO: il nodo non &egrave; stato ancora interessato dalla visita
	 * <li> APERTO: il nodo &egrave; stato gi&agrave; incontrato dalla visita ma non
	 * &egrave; ancora esplorato definitivamente
	 * <li> CHIUSO: il nodo &egrave; stato visitato definitivamente
	 *
	 */
	protected enum Stato { INESPLORATO, APERTO, CHIUSO };

	/**
	 * Riferimento al grafo su cui viene operata la visita
	 */
	protected Grafo   g;
	
	/**
	 * Array utilizzato per mantenere lo stato di esplorazione
	 * dei nodi
	 */
	protected Stato[] stato;

	
	/**
	 * Implementa l'algoritmo di visita generica di <code>VisitaGenerica</code>.
	 * 
	 * @param in il grafo da visitare
	 * @param s il nodo di partenza della visita
	 */
	public final void calcola(Grafo in, Nodo s){
		g = in;
		stato = new Stato[g.numNodi()];
		for (int i = 0; i < g.numNodi(); i++)
			stato[i] = Stato.INESPLORATO;
		stato[g.indice(s)] = Stato.APERTO;
		inizializza(s);
		while (!frangiaVuota()){
			Nodo u = estrai();
			if (stato[g.indice(u)] == Stato.CHIUSO) continue;
			stato[g.indice(u)] = Stato.CHIUSO;
			visita(u);
			Iterator archi = g.archiUscenti(u).iterator();
			while (archi.hasNext()){
				Arco a = (Arco) archi.next();
				Nodo v = a.dest;
				if (stato[g.indice(v)] == Stato.INESPLORATO) {
					stato[g.indice(v)] = Stato.APERTO;
					inserisci(a);
				}
				else if (serveAggiornamento(a)) aggiorna(a);
				}
		}
	}

	/**
	 * Inizializza le strutture dati necessarie alla visita.
	 * 
	 * @param s il nodo di partenza della visita
	 */
	protected void inizializza(Nodo s) { }
	
	/**
	 * Controlla se la frangia &egrave; vuota.
	 * 
	 * @return <code>true</code>, se la frangia &egrave; vuota. <code>false</code>, altrimenti.
	 */
	protected boolean frangiaVuota(){ return true; }
	
	/**
	 * Estrae dalla frangia il prossimo nodo da considerare nella visita.
	 * 
	 * @return il prossimo nodo restituito dalla frangia
	 */
	protected Nodo estrai() { return null; }
	
	/**
	 * Visita il nodo di input <code>u</code>.
	 * 
	 * @param u il nodo da visitare
	 */
	protected void visita(Nodo u) { }
	
	/**
	 * Aggiunge all'albero della visita <code>T</code> un nuovo arco
	 * aggiornando conseguentemente la sua frangia.
	 * 
	 * @param a il nuovo arco da aggiungere a <code>T</code>
	 */
	protected void inserisci(Arco a) { }
	
	/**
	 * Determina se &egrave; necessario aggiornare la frangia rispetto
	 * all'arco di input <code>a</code>.
	 * 
	 * @param a l'arco in base al quale verificare se aggiornare la frangia
	 * @return <code>true</code>, se la frangia deve essere aggiornata. <code>false</code>, altrimenti.
	 */
	protected boolean serveAggiornamento(Arco a){ return false; }
	
	/**
	 * Aggiorna la frangia considerando l'arco di input <code>a</code>
	 * e l'albero della visita <code>T</code>.
	 * 
	 * @param a l'arco da utilizzare per l'aggiornamento
	 */
	protected void aggiorna(Arco a) { }
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
