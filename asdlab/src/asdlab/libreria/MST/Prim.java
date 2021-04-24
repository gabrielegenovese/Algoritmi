package asdlab.libreria.MST;

import asdlab.libreria.Grafi.*;
import asdlab.libreria.Alberi.*;
import asdlab.libreria.CodePriorita.*;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: Prim.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.7 $
 */

/**
 * La classe <code>Prim</code> implementa l'algoritmo di Prim
 * per il calcolo del minimo albero ricoprente di un grafo <code>G</code> non orientato. 
 * L'implementazione dell'algoritmo &egrave; codificata come una specializzazione dell'algoritmo
 * di visita generico implementato dalla classe <code>VisitaGenericaAlb</code> da cui 
 * &egrave; derivata la stessa classe <code>Prim</code>. 
 * L'algoritmo di Prim, infatti, esplora il grafo costruendo un albero <code>T</code>
 * radicato in un nodo sorgente <code>s</code> (sceglibile arbitrariamente, nel caso
 * del problema del minimo albero ricoprente). Ad ogni passo dell'algoritmo, un nodo della
 * frangia di <code>T</code> viene visitato ed espanso: il nodo &egrave; 
 * scelto come quello avente priorit&agrave; minima, dove la priorit&agrave;
 * di un nodo corrisponde al peso dell'arco di costo minimo che lo connette
 * a <code>T</code>. La selezione
 * del nodi di priorit&agrave; minima viene gestita utilizzando
 * la classe {@link DHeap}, istanza del tipo di dato coda con priorit&agrave;,
 * per mantenere l'insieme dei nodi appartenenti alla frangia indicizzati secondo
 * le loro priorit&agrave;. Le priorit&agrave; dei nodi di <code>G</code> sono memorizzate
 * in un array <code>d</code> mentre i riferimenti agli elementi della coda che li incapsulano
 * sono custoditi in un array <code>r</code>, dove il generico elemento <code>r[i]</code>
 * custodisce il riferimento all'elemento della coda che incapsula il nodo di <code>G</code>
 * di indice <code>i</code>. Infine, la classe <code>Prim</code> assume che il costo di ciascun arco sia codificato
 * come valore numerico di tipo <code>Double</code> nel campo <code>info</code> degli archi del grafo.<br> 
 * Il tempo di esecuzione dell'algoritmo &egrave; influenzato dal tipo di coda con priorit&agrave;
 * utilizzata per mantenere i nodi della frangia in funzione del costo degli archi
 * che li connettono a <code>T</code>. Nella fattispecie, la classe <code>Prim</code>
 * implementa la coda con priorit&agrave; come istanza della classe {@link DHeap}, con d=2.
 * La complessit&agrave; risultante &egrave; di <font color=red>O(m&middot;log(n))</font>, dove
 * n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>.
 */

public class Prim extends VisitaGenericaAlb implements MST {

	/**
	 * Coda a priorit&agrave; utilizzata per il mantenimento dei nodi della frangia
	 */
	protected CodaPriorita f;
	
	
	/**
	 * Array utilizzato per memorizzare i riferimenti agli elementi della coda <code>f</code>
	 */
	protected Rif[] r;
	
	/**
	 * Array utilizzato per memorizzare le priorit&agrave; di ciascun nodo
	 */
	protected double[] d;
	
	/**
	 * Il costo del minimo albero ricoprente determinato dall'ultima esecuzione del metodo <code>calcola</code>
	 */
	protected double costo;

	/**
	 * Calcola il minimo albero ricoprente del grafo di input <code>g</code>
	 * utilizzando l'algoritmo di Prim (<font color=red>Tempo O(m&middot;log(n))</font>).  
	 * 
	 * @param g il grafo di cui calcolare il minimo albero ricoprente
	 */
	public void calcola(Grafo g){
		calcola(g, g.nodo(0));
	}

	/**
	 * Restituisce il minimo albero ricoprente calcolato dall'ultima
	 * esecuzione del metodo <code>calcola</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il minimo albero ricoprente determinato dall'ultima esecuzione di <code>calcola</code>
	 */
	public Albero albero() {
		return super.albero();
	}
	
	
	
	/**
	 * Restituisce il costo (i.e., la somma dei pesi degli archi) del minimo albero ricoprente
	 * calcolato durante l'ultima esecuzione del metodo <code>calcola</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il costo dell'ultimo minimo albero ricoprente determinato da <code>calcola</code>
	 */
	public double costo() { return costo; }
	
	/**
	 * Inizializza le strutture dati utilizzate nell'algoritmo di Prim. La coda con priorit&agrave; <code>f</code>
	 * viene mantenuta come istanza della classe <code>DHeap</code>, con d = 2. Si inizializzano inoltre gli array
	 * <code>r</code> e <code>d</code> associando a ciascun nodo del grafo una priorit&agrave; iniziale pari a
	 * 0. Infine, si inserisce nella coda con priorit&agrave; il nodo di partenza <code>s</code> associandovi
	 * la priorit&agrave; 0.
	 * 
	 * @param s il nodo di partenza della visita
	 */
	protected void inizializza(Nodo s) {
		super.inizializza(s);
		f = new DHeap(2);
		r = new Rif[g.numNodi()];
		d = new double[g.numNodi()];
		for (int i = 0; i < g.numNodi(); i++) 
			d[i] = Double.MAX_VALUE;
		d[g.indice(s)] = 0.0;
		r[g.indice(s)] = f.insert(s, d[g.indice(s)]);
		costo = 0;
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
	 * Estrae il nodo <code>v</code> con priorit&agrave;. Contestualmente
	 * all'estrazione, si aggiorna il costo del minimo albero ricoprente
	 * aggiungendovi la priorit&agrave; di <code>v</code> (i.e., il peso
	 * dell'arco incidente a <code>v</code> incluso nel MST). 
	 * 
	 * @return il nodo con priorit&agrave; minima presente in <code>f</code>
	 */
	protected Nodo estrai() {
		Nodo v = (Nodo)f.deleteMin();
		costo += d[g.indice(v)];
		return v;
	}

	/**
	 * Aggiunge l'arco <code>a=(u,v)</code> al minimo albero ricoprente. L'aggiunta avviene 
	 * innanzitutto richiamando il metodo <code>inserisci</code> della classe
	 * <code>VisitaGenericaAlb</code> da cui <code>Prim</code> deriva. Dopodich&eacute;,
	 * si associa come priorit&agrave; al nodo <code>v</code> il peso dell'arco <code>a</code>
	 * e si inserisce <code>v</code> con la sua priorit&agrave; aggiornata nella coda <code>f</code>.
	 * 
	 * @param a l'arco da aggiungere al minimo albero ricoprente
	 */
	protected void inserisci(Arco a) {
		super.inserisci(a);
		int v = g.indice(a.dest);
		d[v] = (Double)g.infoArco(a);
		r[v] = f.insert(a.dest, d[v]);
	}

	/**
	 * Verifica se la distanza del nodo di destinazione dell'arco <code>a=(u,v)</code>
	 * ha un costo maggiore di quella indotta dall'utilizzo del percorso passante dall'arco <code>a</code>.
	 * 
	 * @param a l'arco in base al quale verificare se aggiornare la priorit&agrave; di <code>v</code>
	 * @return <code>true</code>, se la priorit&agrave; di <code>v</code> deve essere aggiornata. <code>false</code>, altrimenti
	 */
	protected boolean serveAggiornamento(Arco a){ 
		return (Double)g.infoArco(a) < d[g.indice(a.dest)]; 
	}

	
	/**
	 * Aggiorna il minimo albero ricoprente includendo l'arco <code>a=(u,v)</code>. L'aggiornamento
	 * avviene ponendo, tramite il metodo <code>decreaseKey</code>, la priorit&agrave; di <code>v</code>
	 * pari al costo di <code>a</code> ed eseguendo il metodo <code>aggiorna</code> della classe
	 * <code>VisitaGenericaAlb</code> da cui <code>Prim</code> deriva.
	 */
	protected void aggiorna(Arco a) { 
		super.aggiorna(a);
		int v = g.indice(a.dest);
		d[v] = (Double)g.infoArco(a);
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
