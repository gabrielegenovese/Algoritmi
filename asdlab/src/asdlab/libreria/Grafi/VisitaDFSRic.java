package asdlab.libreria.Grafi;

import java.util.*;
import asdlab.libreria.Alberi.*;

/* ============================================================================
 *  $RCSfile: VisitaDFSRic.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/02 07:44:56 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.3 $
 */

/**
 * La classe <code>VisitaDFSRic</code> implementa un algoritmo ricorsivo di visita in profondit&agrave;
 * di un grafo <code>G</code> (depth-first search, DFS) a partire da un nodo sorgente <code>s</code>. 
 * La classe <code>VisitaDFS</code> implementa l'interfaccia
 * <code>Visita</code> definendo un metodo, <code>calcolaLista</code>,
 * che, una volta invocato, calcola la lista dei nodi incontrati
 * durante la visita DFS del grafo <code>G</code> a partire dal nodo <code>s</code>. 
 * L'operazione di visita vera e propria &egrave; implementata dal metodo ricorsivo
 * <code>calcolaRic</code> che utilizza un opportuno array, <code>vis</code>, passato
 * come parametro da input per marcare i nodi del grafo che sono stati gi&agrave; visitati.
 * L'elenco dei nodi risultanti dalla visita DFS viene mantenuto in una lista, <code>l</code>,
 * anch'essa passata come parametro nell'esecuzione ricorsiva di <code>calcolaRic</code>.
 */

public class VisitaDFSRic implements Visita {

	/**
	 * Calcola e restituisce la lista dei nodi del grafo di input <code>g</code>,
	 * contenente n nodi ed m archi, nell'ordine determinato da una visita DFS (<font color=red>Tempo O(n+m)</font>).
	 * 
	 * @param g il grafo da visitare
	 * @param s il nodo di partenza della visita
	 * @return la lista dei nodi esplorati durante la visita DFS
	 */	
	public List calcolaLista(Grafo g, Nodo s){
		List l = new LinkedList();
		boolean[] vis = new boolean[g.numNodi()];
		calcolaRic(g, s, l, vis);
		return l;
	}

	/**
	 * Opera una visita DFS ricorsiva del nodo <code>u</code> appartenente
	 * al grafo G. 
	 *   
	 * @param g il grafo da visitare
	 * @param u il nodo da visitare
	 * @param l l'elenco dei nodi sinora visitati
	 * @param vis array utilizzato per marcare i nodi gi&agrave; visitati
	 */
	private void calcolaRic(Grafo g, Nodo u, List l, boolean[] vis){
		l.add(g.indice(u));
		vis[g.indice(u)] = true;
		Iterator archi = g.archiUscenti(u).iterator();
		while (archi.hasNext()){
			Arco a = (Arco) archi.next();
			if (vis[g.indice(a.dest)]) continue;
			calcolaRic(g, a.dest, l, vis);
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