package asdlab.libreria.MST;

import java.util.*;
import asdlab.libreria.Alberi.*;
import asdlab.libreria.Grafi.*;
import asdlab.libreria.StruttureElem.*;
import asdlab.libreria.UnionFind.*;

/* ============================================================================
 *  $RCSfile: Kruskal.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/30 18:53:44 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.5 $
 */

/**
 * La classe <code>Kruskal</code> implementa l'algoritmo di Kruskal
 * per il calcolo del minimo albero ricoprente di un grafo <code>G</code> non orientato. 
 * Essa implementa l'interfaccia <code>MST</code> ed &egrave; in grado di operare
 * con grafi espressi come implementazioni dell'interfaccia <code>Grafo</code>.
 * La classe <code>Kruskal</code> dichiara al suo interno una seconda classe, <code>CompArchi</code>,
 * utilizzata durante la fase di ordinamento degli archi del grafo <code>G</code>
 * per confrontare il costo di due archi. Si assume che il costo di ciascun arco sia codificato
 * come valore numerico di tipo <code>Double</code> nel campo <code>info</code> degli archi del grafo. L'implementazione
 * realizzata dell'algoritmo di Kruskal utilizza la classe <code>QuickUnionBS</code>,
 * istanza del tipo <code>UnionFind</code>, per rappresentare la foresta di alberi
 * mantenuta dall'algoritmo durante la sua esecuzione.
 * La complessit&agrave; dell'algoritmo &egrave; <font color=red>O(m&middot;log(n))</font>, dove
 * n &egrave; il numero di nodi di <code>G</code> ed m il numero di archi di <code>G</code>.
 */

public class Kruskal implements MST {

	/**
	 * Classe definita localmente alla classe <code>Kruskal</code> per il confronto
	 * di coppie di archi. La classe implementa l'interfaccia standard <code>Comparator</code>
	 * per consentire l'ordinamento di insiemi di archi tramite il metodo <code>sort</code>
	 * della classe standard <code>Arrays</code>.
	 *
	 */
	private class CompArchi implements Comparator {
		/**
		 * Confronta una coppia di archi determinandone l'ordine in funzione del
		 * loro costo
		 * 
		 * @param o1 il primo arco da confrontare
		 * @param o2 il secondo arco da confrontare
		 * @return -1, se o1 &lt; o2. 0, se o1 = o2. +1, altrimenti.
		 */
		public int compare(Object o1, Object o2) {
			double a = (Double)((Arco)o1).info;
			double b = (Double)((Arco)o2).info;
			return (a < b)? -1 : (a > b)? 1 : 0 ;
		}
	}

	/**
	 * L'albero contenente il minimo albero ricoprente determinato dall'ultima esecuzione del metodo <code>calcola</code>
	 */
	Albero alb;
	
	/**
	 * Il costo del minimo albero ricoprente determinato dall'ultima esecuzione del metodo <code>calcola</code>
	 */
	double costo;

	/**
	 * Calcola il minimo albero ricoprente del grafo di input <code>g</code>
	 * utilizzando l'algoritmo di Kruskal (<font color=red>Tempo O(m&middot;log(n))</font>).
	 * 
	 * @param g il grafo di cui calcolare il minimo albero ricoprente
	 */
	public void calcola(Grafo g) {
		Grafo t = new GrafoLA();
		Rif[] rif = new Rif[g.numNodi()];
		UnionFind uf = new QuickUnionBS();
		Arco[] archi = g.archi();

		Arrays.sort(archi, new CompArchi());

		for (int i = 0; i < g.numNodi(); i++){
			rif[i] = uf.makeSet();
			t.aggiungiNodo(null);
		}
		costo = 0;
		for (int i = 0; i < archi.length; i++){
			int u = g.indice(archi[i].orig), 
			    v = g.indice(archi[i].dest);
			Rif ru = uf.find(rif[u]), rv = uf.find(rif[v]);
			if (ru != rv) {
				uf.union(ru, rv);
				t.aggiungiArco(t.nodo(u), t.nodo(v), null);
				t.aggiungiArco(t.nodo(v), t.nodo(u), null);
				costo += (Double)g.infoArco(archi[i]);
			}
		}
		alb = new VisitaBFSAlb().calcolaAlbero(t, t.nodo(0));
	}

	/**
	 * Restituisce il minimo albero ricoprente calcolato dall'ultima
	 * esecuzione del metodo <code>calcola</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il minimo albero ricoprente determinato dall'ultima esecuzione di <code>calcola</code>
	 */
	public Albero albero() { return alb; }

	/**
	 * Restituisce il costo (i.e., la somma dei pesi degli archi) del minimo albero ricoprente
	 * calcolato durante l'ultima esecuzione del metodo <code>calcola</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il costo dell'ultimo minimo albero ricoprente determinato da <code>calcola</code>
	 */
	public double costo() { return costo; }
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