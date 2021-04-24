package asdlab.libreria.UnionFind;

import asdlab.libreria.Alberi.Albero;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: QuickUnion.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/02 07:46:39 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.12 $
 */

/**
 * La classe <code>QuickUnion</code> eredita la classe <code>UnionFindBase</code>
 * e rappresenta gli insiemi disgiunti mediante alberi di altezza non limitata. Gli
 * elementi di ciascun insieme sono rappresentati mediante i nodi del rispettivo albero,
 * mentre la radice di ciascun albero rappresenter&agrave; il riferimento (nome) dell'insieme. 
 * L'operazione di <code>union</code> implementata da <code>QuickUnion</code> fonde due
 * alberi innestando la radice di uno dei due alberi come figlio della radice dell'altro albero.
 * Tale approccio ha il vantaggio di essere particolarmente efficiente ma 
 * pu&ograve; portare alla creazione di alberi di altezza n, dove n &egrave; il numero
 * di elementi custoditi nella union-find, con la conseguenza di rendere
 * particolarmente costosa l'operazione di <code>find</code> (O(n)).
 * La classe <code>QuickUnion</code> implementa pertanto tre diverse euristiche di
 * compressione dei cammini che agiscono durante l'operazione di <code>find</code>
 * ed hanno lo scopo di comprimere il cammino visitato durante un'operazione <code>find</code>.
 * Le euristiche supportate sono:
 * 
 * <ul>
 * <li> <b>path compression:</b> rende tutti i nodi u(i),
 * 0 &le; i &le; (l - 3), figli della radice u(l-1)
 * (u(l-2) &egrave; gi&agrave; figlio della radice u(l-1)).</li> 
 * <li> <b>path splitting:</b> rende il nodo u(i), 0 &le; i &le; l - 3,
 * figlio del suo nonno u(i+2).</li> 
 * <li> <b>path halving:</b> rende il nodo u(2i), 0 &le; i &le (((l-1)/2)-1),
 * figlio del suo nonno u(2i+2). </li>
 * </ul>
 * La scelta del tipo di compressione dei cammini da utilizzare pu&ograve; essere
 * fatta durante l'istanziazione della classe <code>QuickUnion</code>.
 * 
 */

public class QuickUnion extends UnionFindBase {


	/**
	 * Enumera i diversi tipi di compressione dei cammini utilizzabili
	 * durante l'operazione di <code>find</code>.
	 * <ul>
	 * <li> NONE: nessuna compressione </li>
	 * <li> PCOMPRESS: path compression </li>
	 * <li> PSPLIT: path splitting </li>
	 * <li> PHALVE: path havling </li>
	 * </ul> 
	 */
	public enum TipoCompressione { NONE, PCOMPRESS, PSPLIT, PHALVE};

	/**
	 * Indica il tipo di compressione dei cammini da utilizzare
	 * durante l'operazione di <code>find</code>
	 */
	TipoCompressione t;
	
	/**
	 * Istanzia la classe <code>QuickUnion</code> adottando per le successive
	 * <code>find</code> il tipo di compressione indicato da input.
	 * 
	 * @param t il tipo di compressione dei cammini da utilizzare nelle operazioni di <code>find</code>
	 */
	public QuickUnion(TipoCompressione t) {
		this.t = t;
	}	

	/**
	 * Istanzia la classe <code>QuickUnion</code> senza prevedere alcun
	 * tipo di compressione dei cammini.
	 *
	 */
	public QuickUnion() {
		this.t = TipoCompressione.NONE;
	}	

	
	/**
	 * Fonde gli insiemi <code>alb1</code> ed <code>alb2</code> indicati
	 * da input (<font color=red>Tempo O(1)</font>). La fusione avviene 
	 * rendendo la radice di <code>alb2</code> figlia della radice di <code>alb1</code>
	 * mediante operazioni di <code>innesta</code> e <code>pota</code> di <code>Albero</code>.
	 * 
	 * @param alb1 il riferimento al primo insieme da fondere
	 * @param alb2 il riferimento al secondo insieme da fondere
	 * @return il riferimento all'insieme derivante dalla fusione
	 */
	public Rif union(Albero alb1, Albero alb2) {
		alb1.innesta(alb1.radice(), alb2);
		return alb1.radice();
	}
	
	/**
	 * Determina l'insieme contenente l'elemento indicato da input.
	 * L'effettiva ricerca dell'insieme viene delegata ad una delle varianti
	 * disponibili dell'operazione di <code>find</code> in funzione del valore
	 * assunto dalla variabile di classe <code>t</code>.
	 * 
	 * @param elem l'elemento di cui si vuole conoscere l'insieme di appartenza
	 * @return l'insieme contenente <code>elem</code>
	 */

    public Rif find(Rif elem) {
        if (t == TipoCompressione.PCOMPRESS) return findComp(elem);
        if (t == TipoCompressione.PSPLIT) return findSplit(elem);
        if (t == TipoCompressione.PHALVE) return findHalve(elem);
        return super.find(elem);
    }
    
	/**
	 * Determina l'insieme contenente l'elemento indicato da input
	 * utilizzando l'euristica di path-splitting. La compressione
	 * avviene risalendo sino alla radice l'albero contenente 
	 * elem ed utilizzando i metodi <code>pota</code> ed
	 * <code>innesta</code> di <code>Albero</code> per ristrutturare opportunamente
	 * l'albero.
	 * 
	 * @param elem l'elemento di cui si vuole conoscere l'insieme di appartenza
	 * @return l'insieme contenente <code>elem</code>
	 */
	public Rif findSplit(Rif elem) {
		Nodo n = (Nodo) elem;
		Albero alb = (Albero) n.contenitore();
		if (n == alb.radice()) return n;
		Nodo p = alb.padre(n);
		while(alb.padre(p) != null){
			Albero a = alb.pota(n);
			alb.innesta(alb.padre(p), a);
			n = p;
			p = alb.padre(p);
		}
		return p;
	}
	
	/**
	 * Determina l'insieme contenente l'elemento indicato da input
	 * utilizzando l'euristica di path-halving. La compressione
	 * avviene risalendo sino alla radice l'albero contenente 
	 * elem ed utilizzando i metodi <code>pota</code> ed
	 * <code>innesta</code> di <code>Albero</code> per ristrutturare opportunamente
	 * l'albero.
	 * 
	 * @param elem l'elemento di cui si vuole conoscere l'insieme di appartenza
	 * @return l'insieme contenente <code>elem</code>
	 */
	public Rif findHalve(Rif elem) {
		Nodo n = (Nodo) elem;
		Albero alb = (Albero) n.contenitore();
		if (n == alb.radice()) return n;
		Nodo p = alb.padre(n);
		while(p != null && alb.padre(p) != null){
			Albero a = alb.pota(n);
			alb.innesta(alb.padre(p), a);
			n = alb.padre(p);
			p = alb.padre(n);
		}
		if (p != null) return p;
		else return n;
	}
	
	/**
	 * Determina l'insieme contenente l'elemento indicato da input
	 * utilizzando l'euristica di path-compression. La compressione
	 * avviene risalendo sino alla radice l'albero contenente 
	 * elem ed utilizzando i metodi <code>pota</code> ed
	 * <code>innesta</code> di <code>Albero</code> per ristrutturare opportunamente
	 * l'albero.
	 * 
	 * @param elem l'elemento di cui si vuole conoscere l'insieme di appartenza
	 * @return l'insieme contenente <code>elem</code>
	 */
	public Rif findComp(Rif elem) {
		Nodo n = (Nodo) elem;
		Albero alb = (Albero) n.contenitore();
		Nodo radice = alb.radice();
		if (n == radice) return n;
		Nodo p;
		while(alb.padre(n) != radice){
			p = alb.padre(n);
			alb.innesta(radice, alb.pota(n));
			n = p;
		}
		return radice;
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
