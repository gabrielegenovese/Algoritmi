package asdlab.progetto.Clustering;

import java.util.*;
import asdlab.progetto.IndiceInverso.*;
import asdlab.libreria.Grafi.*;
import asdlab.libreria.MST.*;
import asdlab.libreria.Alberi.*;

/* ============================================================================
 *  $RCSfile: Clustering.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.10 $
 */

/**
 * La classe <code>Clustering</code> implementa un algoritmo per il clustering dei
 * risultati di una interrogazione eseguita mediante la classe {@link IndiceInverso}. L'algoritmo
 * opera suddividendo e raggruppando in cluster i documenti risultanti dall'interrogazione
 * in base ad una funzione di somiglianza (o similarit&agrave;). Documenti simili saranno
 * classificati nello stesso cluster. <br>
 * Inizialmente, l'algoritmo cotruisce un grafo non-orientato
 * completo, dove ciascun nodo del grafo corrisponde a ciascuno dei documenti da classificare
 * e ciascun arco <code>(x,y)</code> contiene il valore della funzione di similiarit&agrave; calcolato
 * sulla coppia di documenti corrispondenti ai nodi <code>x</code> ed <code>y</code>. 
 * L'effettivo calcolo della funzione di similarit&agrave; viene delegato all'interfaccia
 * <code>Costo</code>. Una volta costruito il grafo, la classificazione dei documenti
 * in <code>k</code> cluster avviene eseguendo un algoritmo per il calcolo del massimo
 * albero ricoprente e, successivamente, rimuovendo dall'albero calcolato
 *  i <code>k-1</code> archi di peso minimo. I <code>k</code> alberi della foresta derivante
 *  dalla rimozione degli archi corrisponderanno ai <code>k</code> cluster.<br> Per semplicit&agrave;
 *  di implementazione, l'algoritmo di massimo albero ricoprente &egrave; ottenuto assegnando
 *  a ciascun arco del grafo un costo con peso negativo (i.e., due documenti risulteranno
 *  tanto pi&ugrave; simili quanto pi&ugrave; &egrave; piccolo il peso dell'arco) ed utilizzando
 *  l'algoritmo di Prim, implementato dalla classe {@link Prim}, per il calcolo del minimo
 *  albero ricoprente.
 */

public class Clustering {
	
	/**
	 * Classifica un insieme di documenti di input in 2 cluster utilizzando
	 * la funzione di similarit&agrave; indicata da input. Il metodo opera
	 * innanzitutto allocando un nuovo grafo completo, istanza della classe
	 * <code>GrafoLA</code>, che associa a ciascun documento un vertice.
	 * Il peso di ciascun arco <code>(x,y)</code> &egrave; determinato dalla
	 * funzione di similarit&agrave; indicata da input. Successivamente, si
	 * determina il massimo albero ricoprente del grafo tramite il metodo
	 * <code>calcola</code> della classe <code>Prim</code> e si elimina dall'albero
	 * risultante l'arco di peso massimo (corrispondente alla coppia di documenti
	 * con similarit&agrave; minima) tramite il metodo <code>nodoMax</code>.
	 * Il contenuto dei due alberi risultanti (i due cluster) viene ricopiato in due
	 * array restituiti come valore di ritorno.
	 * 
	 * @param ris l'insieme di documenti da raggruppare in cluster
	 * @param c la funzione di similarit&agrave;
	 * @return i due array contenenti i documenti appartenenti ai rispettivi cluster
	 */
	public static Ris[][] cluster(Ris[] ris, Costo c){
		if (ris.length < 2) 
			{ Ris[][] cl = new Ris[1][]; cl[0] = ris; return cl; }
		Grafo g = new GrafoLA();
		for (int i = 0; i < ris.length; i++)
			g.aggiungiNodo(ris[i]);
		for (int i = 0; i < ris.length; i++)
			for (int j = i + 1; j < ris.length; j++) {
				double costo = c.costo(i, j);
				g.aggiungiArco(g.nodo(i),g.nodo(j), -costo);
				g.aggiungiArco(g.nodo(j),g.nodo(i), -costo);
			}
		MST mst = new Prim();
		mst.calcola(g);
		Albero t = mst.albero();
		Nodo min = nodoMax(g, t);
		Albero t2 = t.pota(min);
		Ris[][] cl = new Ris[2][];
		cl[0] = creaCluster(g, t);
		cl[1] = creaCluster(g, t2);
		return cl;
	}

	/**
	 * Dato il minimo albero ricoprente di un grafo, calcola e restituisce
	 * il nodo su cui incide l'arco dell'albero di peso massimo. Il nodo
	 * viene determinando operando gli archi dell'albero nell'ordine
	 * determinato da una visita BFS e cercando l'arco di peso massimo.
 i
	 * @param g il grafo di riferimento
	 * @param t il minimo albero ricoprente di <code>g</code>
	 * @return il nodo di <code>t/code> su cui incide l'arco dell'albero di peso massimo
	 */
	private static Nodo nodoMax(Grafo g, Albero t){
		Nodo min = null;
		double minCosto = Double.MAX_VALUE;
		Iterator i = t.visitaBFS().iterator();
		while (i.hasNext()){
			Nodo x = (Nodo)i.next();
			if (t.padre(x) == null) continue;
			int iu = (Integer)t.info(t.padre(x));
			int iv = (Integer)t.info(x);
			Arco a = g.sonoAdiacenti(g.nodo(iu), g.nodo(iv));
			double costo = -(Double)g.infoArco(a);
			if (costo < minCosto) {
				minCosto = costo;
				min = x;
			}
		}
		return min;
	}

	/**
	 * Dato un grafo e un albero ricoprente di un suo sottografo,
	 * crea un array in cui memorizza gli oggetti di tipo <code>Ris</code>
	 * corrispondenti ai nodi del grafo ricoperti dall'albero.
	 * 
	 * @param g il grafo di riferimento
	 * @param t l'albero ricoprente di un sottografo di <code>g</code>
	 * @return un array contenente i nodi corrispondenti ai nodi di <code>g</code> ricoperti da <code>t</code>
	 */
	private static Ris[] creaCluster(Grafo g, Albero t){
		List l = new LinkedList();
		Iterator i = t.visitaBFS().iterator();
		while (i.hasNext())
			l.add(g.infoNodo(g.nodo((Integer)t.info((Nodo)i.next()))));
		return (Ris[])l.toArray(new Ris[0]);
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