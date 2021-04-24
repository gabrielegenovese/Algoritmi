package asdlab.libreria.CodePriorita;

import java.util.Iterator;
import java.util.LinkedList;

import asdlab.libreria.Alberi.Albero;
import asdlab.libreria.Alberi.AlberoPFFS;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.Eccezioni.EccezioneChiaveNonValida;
import asdlab.libreria.Eccezioni.EccezioneNodoNonValido;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: HeapBinomiale.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:08:19 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.13 $
 */

/**
 *  La classe <code>HeapBinomiale</code> implementa il tipo di dato coda con priorit&agrave; mediante
 *  un heap binomiale, realizzato come una foresta di n nodi,
 *  ciascuno contenente una coppia (elem, chiave). Gli alberi binomiali che compongono l'heap
 *  sono rappresentati come istanze dell'interfaccia <code>Albero</code>. La classe
 *  <code>HeapBinomiale</code> mantiene inoltre un array di liste, <code>radici</code>, dove
 *  ciascuna lista <code>radici[i]</code> contiene la lista degli alberi binomiali di indice <code>i</code>.
 *  L'utilizzo delle liste si rende necessario per gestire i casi in cui, come durante la fusione di due heap binomiale,
 *  possono esistere due o tre alberi binomiali dello stesso indice.
 *  L'array <code>radici</code> ha una dimensione fissa di 32 posizioni consentendo la rappresentazione
 *  di heap binomiali contenenti sino a 4.294.967.295 elementi.
 *  
 *  
 */

public class HeapBinomiale implements CodaPriorita {
	
	/** 
	 * Classe definita localmente alla classe <code>HeapBinomiale</code>
	 * per la rappresentazione del contenuto informativo dei
	 * nodi dell'heap binomiale. 
	 * Memorizza la coppia (elem,chiave) ed il riferimento del nodo
	 * cui la coppia &egrave; associata. 
	 *
	 */

	public class InfoAlbBinom implements Rif{
		/**
		 * Elemento da conservare nell'heap binomiale
		 */
		public Object elem;
		
		/**
		 * Chiave associata ad <code>elem</code>
		 */
		public Comparable chiave;
		
		/**
		 * Riferimento al nodo dell'heap binomiale associato alla coppia (elem, chiave)
		 */
		protected Nodo nodo;
		
		
		/**
		 * Costruttore per l'allocazione di una nuova istanza di <code>InfoAlbBinom</code>
		 * 
		 * @param e elemento da conservare nella coda
		 * @param k chiave associata ad <code>e</code>
		 */
		public InfoAlbBinom(Object e, Comparable k) {
			elem = e; chiave = k; nodo = null;
		}
	}	
	/**
	 * Array di liste utilizzato per la memorizzazione degli alberi binomiali esistenti nell'heap
	 */
	private LinkedList[] radici;
	
	/**
	 * Il numero di elementi presenti nell'heap
	 */
	private int dim;
	
	/**
	 * Il minimo valore rappresentabile nel dominio delle chiavi degli elementi dell'heap
	 */
	private Comparable minComparable;

	/**
	 * Costruttore per l'istanziazione di un nuovo heap binomiale. Richiede l'indicazione
	 * del minimo valore esprimibile per il tipo di dato usato come chiave.
	 * 
	 * @param minComparable il minimo valore rappresentabile nel dominio delle chiavi degli elementi dell'heap
	 */
	public HeapBinomiale(Comparable minComparable) {
		this.minComparable = minComparable;
		dim = 0;
		radici = new LinkedList[32];
		for (int i = 0; i < radici.length; i++)
			radici[i] = new LinkedList(); 
	}

	/**
	 * Restituisce l'elemento di chiave minima presente nell'heap binomiale (<font color=red>Tempo O(log(n))</font>).
	 * L'elemento viene determinato individuando, mediante il metodo <code>findAlbMin</code>,
	 * l'albero la cui radice assume il valore minimo.
	 * 
	 * @return l'elemento di chiave minima
	 */	
	public Object findMin() {
		if (isEmpty()) return null;
		Albero min = findAlbMin();
		return ((InfoAlbBinom) min.info(min.radice())).elem;
	}

	
	/**
	 * Aggiunge all'heap binomiale un nuovo elemento <code>e</code> con chiave
	 * <code>k</code> (<font color=red>Tempo O(log(n))</font>).
	 * L'elemento viene inizialmente incapsulato in una nuova istanza di
	 * <code>InfoAlbBinom</code> e successivamente inserito mediante
	 * una chiamata al metodo protetto <code>insert</code>.
	 * 
	 * @param e l'elemento da inserire nella coda
	 * @param k la chiave da associare ad <code>e</code>
	 * @return il riferimento all'oggetto dell'heap binomiale che incapsula l'elemento inserito
	 */
	public Rif insert(Object e, Comparable k) {
		InfoAlbBinom i = new InfoAlbBinom(e, k); 
		i.nodo = insert(i);
		return i;
	}
	

	/**
	 * Cancella dall'heap binomiale l'oggetto <code>u</code> (<font color=red>Tempo O(log(n))</font>). L'elemento
	 * viene cancellato dapprima associandovi la chiave <code>minComparable</code>,
	 * rendendolo cos&igrave; l'elemento di chiave minima, e dopo invocando
	 * il metodo <code>deleteMin</code> per rimuoverlo.
	 *  
	 * @param u il riferimento all'oggetto dell'heap binomiale da cancellare
	 * @return l'elemento incapsulato nell'oggetto dell'heap binomiale cancellato
	 */

	public Object delete(Rif u) {
		if (u == null) throw new EccezioneNodoNonValido();
		decreaseKey(u, minComparable);
		deleteMin();
		return ((InfoAlbBinom) u).elem;
	}

	/**
	 * Cancella dall'heap binomiale l'elemento con chiave minima e lo restituisce (<font color=red>Tempo O(log(n))</font>). 
	 * La cancellazione avviene:
	 * <ul>
	 * <li> individuando l'albero <code>albMin</code> la cui radice ha chiave minima</li>
	 * <li> rimuovendo <code>albMin</code> da <code>radici</code> ed aggiungendo
	 * gli stessi figli di <code>albMin</code> in <code>radici</code> secondo il loro indice</li>
	 * <li> eseguendo il metodo <code>ristruttura</code> per ripristinare la struttura
	 * dell'heap binomiale</li>
	 * </ul>
	 *  
	 * @return l'elemento di chiave minima
	 */	
	public Object deleteMin() {
		if (dim == 0) return null;

		Albero albMin = findAlbMin();
		Nodo min = albMin.radice();
		radici[albMin.grado(min)].remove(albMin);
		Iterator figli = albMin.figli(min).iterator();
		
		for (int i = albMin.grado(min) - 1; i >= 0; i--) 
			radici[i].add(albMin.pota((Nodo) figli.next()));
		
		dim = dim - 1;
		ristruttura();
		return ((InfoAlbBinom) albMin.info(min)).elem;
	}

	/**
	 * Sostituisce la chiave dell'elemento incapsulato nell'oggetto <code>u</code>
	 * con <code>newKey</code> (<font color=red>Tempo O(log(n))</font>). 
	 * Assume che la nuova chiave abbia un valore non inferiore
	 * a quello della precedente chiave di <code>u</code>. La sostituzione avviene
	 * rimuovendo, attraverso il metodo <code>delete</code>, l'elemento <code>u</code> dall'heap
	 * binomiale e reinserendolo con il metodo <code>insert</code>, dopo aver
	 * mutato la sua chiave in <code>newKey</code>.
	 * 
	 * @param u il riferimento all'oggetto dell'heap cui aggiornare la chiave
	 * @param newKey la nuova chiave da associare ad <code>u</code>
	 * @throws EccezioneChiaveNonValida se <code>newKey</code> ha un valore inferiore a quello della precedente chiave di <code>u</code>
	 */	
	public void increaseKey(Rif u, Comparable newKey) {
		InfoAlbBinom i = ((InfoAlbBinom)u);
		if (newKey.compareTo(i.chiave)  < 0) throw new EccezioneChiaveNonValida();
		delete(u);
		i.chiave = newKey;
		i.nodo = insert(i);
	}

	
	/**
	 * Sostituisce la chiave dell'elemento incapsulato nell'oggetto <code>u</code>
	 * con <code>newKey</code> (<font color=red>Tempo O(log(n))</font>). 
	 * Assume che la nuova chiave abbia un valore non superiore
	 * a quello della precedente chiave di <code>u</code>. La sostituzione avviene
	 * aggiornando a <code>newKey</code> la chiave di <code>u</code> e facendolo
	 * risalire con il metodo <code>scambiaInfo</code> lungo l'albero
	 * binomiale che lo contiene sino a che la propriet&agrave; di 
	 * ordinamento a heap non &egrave; rispettata.
	 * 
	 * @param u il riferimento all'oggetto dell'heap cui aggiornare la chiave
	 * @param newKey la nuova chiave da associare ad <code>u</code>
	 * @throws EccezioneChiaveNonValida se <code>newKey</code> ha un valore superiore a quello della precedente chiave di <code>u</code>
	 */	
	public void decreaseKey(Rif u, Comparable newKey) {
		if (newKey.compareTo(((InfoAlbBinom)u).chiave) > 0) throw new EccezioneChiaveNonValida();
		((InfoAlbBinom)u).chiave = newKey;
		Nodo n = ((InfoAlbBinom)u).nodo;
		Albero alb = (Albero) n.contenitore();
		Nodo p = alb.padre(n);
		while ((p != null) &&  chiaveNodo(alb, n).compareTo(chiaveNodo(alb, p)) < 0) {
			scambiaInfo(alb, n, p);
			n = p; p = alb.padre(p); 
		}
	}

	/**
	 * Incorpora l'heap binomiale <code>c</code> indicato da input nell'heap
	 * binomiale su cui il metodo &egrave; invocato (<font color=red>Tempo O(log(n))</font>).
	 * L'operazione avviene inserendo in <code>radici</code> l'elenco degli
	 * alberi binomiali appartenenti a <code>c</code> ed invocando il metodo
	 * <code>ristruttura</code> per ripristinare l'heap binomiale.
	 * 
	 * @param c la coda con priorit&agrave; da fondere nella coda attuale
	 */
	public void merge(CodaPriorita c) {
		HeapBinomiale coda = (HeapBinomiale) c;
		dim = dim + coda.dim;
		for (int i = 0; i < coda.radici.length; i++) 
			radici[i].addAll(coda.radici[i]);
		ristruttura();
	}

	/**
	 * Verifica se l'heap binomiale &egrave; vuoto (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return <code>true</code>, se la <em>d</em>-heap &egrave; vuoto. <code>false</code>, altrimenti
	 */
	public boolean isEmpty() {
		if (dim == 0) return true;
		return false;
	}

	/**
	 * Determina l'albero la cui radice ha chiave minima. L'albero
	 * viene individuato scorrendo le liste contenute in <code>radici</code>
	 * e confrontando le chiavi dei rispettivi alberi.
	 * 
	 * @return l'albero la cui radice ha chiave minima
	 */
	protected Albero findAlbMin() {
		if (isEmpty()) return null;
		Albero min = null;
		for (int i = 0; i < radici.length; i++) {
			if (radici[i].size() > 0) {
				Albero alb = (Albero) radici[i].get(0); 
				if (min == null || chiaveRadice(alb).compareTo(chiaveRadice(min)) < 0)
					min = alb;
			}
		}
		return min;
	}

	/**
	 * Inserisce un nuovo elemento <code>i</code> di tipo <code>InfoAlbBinom</code> nell'albero
	 * binomiale. L'inserimento avviene creando un nuovo albero contenente
	 * il solo elemento <code>i</code>, inserendolo in <code>radici</code>
	 * ed invocando il metodo <code>ristruttura</code> per ripristinare
	 * l'heap binomiale.
	 * 
	 * @param i il contenuto informativo del nodo da inserire nell'heap binomiale
	 * @return il nodo contenente <code>i</code> nell'heap binomiale
	 */
	protected Nodo insert(InfoAlbBinom i){
		Albero alb = new AlberoPFFS();
		Nodo radice = alb.aggiungiRadice(i);
		radici[0].add(alb);
		dim = dim + 1;
		ristruttura();
		return radice;
	}	

	/**
	 * Scorre la lista delle radici degli alberi binomiali contenuti
	 * nell'heap fondendo eventuali coppie di alberi aventi lo stesso indice.
	 * L'operazione avviene scorrendo il contenuto dell'array <code>radici</code>
	 * ed utilizzando il metodo <code>innesta</code> di <code>Albero</code>
	 * per fondere eventuali coppie di alberi binomiali con lo stesso indice.
	 *
	 */
	protected void ristruttura() {
		for (int i = 0; i < radici.length; i++) {
			if (radici[i].size() > 1){
				Albero alb1 = (Albero) radici[i].remove(0);
				Albero alb2 = (Albero) radici[i].remove(0);
				if (chiaveRadice(alb1).compareTo(chiaveRadice(alb2)) < 0) {
					alb1.innesta(alb1.radice(), alb2);
					radici[i+1].add(alb1);
				} else {
					alb2.innesta(alb2.radice(), alb1);
					radici[i+1].add(alb2);
				}
			}
		}
	}
	
	/**
	 * Scambia il contenuto informativo di una coppia di nodi 
	 *   
	 * @param alb l'albero cui i due nodi appartengono  
	 * @param n1 Il primo nodo di cui scambiare il contenuto informativo
	 * @param n2 Il secondo nodo di cui scambiare il contenuto informativo
	 */	
	protected void scambiaInfo(Albero alb, Nodo n1, Nodo n2){
		InfoAlbBinom i1 = (InfoAlbBinom)alb.info(n1);
		InfoAlbBinom i2 = (InfoAlbBinom)alb.info(n2);
		i1.nodo = n2;
		i2.nodo = n1;
		alb.cambiaInfo(n1, i2);
		alb.cambiaInfo(n2, i1);
	}	
	
	/**
	 * Restituisce la chiave di un nodo <code>r</code>, dato l'albero
	 * che lo contiene.
	 * 
	 * @param alb l'albero contenente <code>r</code>
	 * @param r il nodo di cui si vuol conoscere la chiave
	 * @return la chiave di <code>r</code>
	 */
	protected Comparable chiaveNodo(Albero alb, Nodo r){
		return ((InfoAlbBinom) (alb.info(r))).chiave;
	}
	
	/**
	 * Restituisce la chiave della radice di un albero.
	 * 
	 * @param alb l'albero della cui radice si vuole conoscere la chiave
	 * @return la chiave della radice di <code>alb</code>
	 */
	protected Comparable chiaveRadice(Albero alb){
		return ((InfoAlbBinom) (alb.info(alb.radice()))).chiave;
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