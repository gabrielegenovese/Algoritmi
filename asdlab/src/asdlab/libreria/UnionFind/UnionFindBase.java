package asdlab.libreria.UnionFind;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: UnionFindBase.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */

/**
 * La classe <code>UnionFindBase</code> supporta l'implementazione dell'interfaccia <code>UnionFind</code>
 * mediante una rappresentazione basata su alberi. Tale rappresentazione associa ad ogni insieme della collezione un albero di tipo {@link AlberoPFFS}:
 * i nodi dell'albero corrispondono agli elementi dell'insieme, mentre la radice contiene
 * anche l'informazione relativa al riferimento (nome) dell'insieme. In tale scenario,
 * la collezione di insiemi disgiunti corrisponde ad una foresta di alberi e si avr&agrave;
 * esattamente un albero per ciascun insieme.<br> 
 * La classe <code>UnionFindBase</code> &egrave; astratta ed  &egrave; progettata per essere derivata da altre
 * classi che implementano diversi possibili approcci all'implementazione del tipo union-find. 
 * Le operazioni di <code>makeSet</code> e <code>find</code> sono completamente implementate mentre
 * l'operazione di <code>union</code>, dati due elementi in input, individua gli insiemi che li contengono
 * per poi delegare l'effettiva unione ad un secondo metodo <code>union</code> la cui effettiva implementazione &egrave;
 * a carico delle classi che derivano <code>UnionFindBase</code>. 
 */
public abstract class UnionFindBase implements UnionFind{

	/**
	 * Crea un nuovo insieme e ne restituisce il riferimento (<font color=red>Tempo O(1)</font>).
	 * L'insieme viene creato istanziando un nuovo albero di tipo
	 * <code>AlberoPFFS</code> e restituendone il riferimento alla radice.
	 * 
	 * @return il riferimento all'insieme creato
	 */
	public Rif makeSet() {
		Albero alb = new AlberoPFFS();
		return alb.aggiungiRadice(null);
	}

	/**
	 * Fonde gli insiemi contenenti gli elementi indicati da input.
	 * La fusione viene eseguita determinando, mediante il metodo <code>contenitore</code>
	 * della classe <code>Nodo</code>, l'insieme cui appartiene ciascuno
	 * dei due elementi e richiamando il metodo <code>union</code>
	 * sui riferimenti ai due insiemi.
	 * 
	 * @param rif1 il riferimento all'elemento contenuto nel primo insieme da fondere
	 * @param rif2 il riferimento all'elemento contenuto nel secondo insieme da fondere
	 * @return il riferimento all'insieme derivante dalla fusione
	 */
	public Rif union(Rif rif1, Rif rif2) {
		Albero alb1 = (Albero) ((Nodo) rif1).contenitore();
		Albero alb2 = (Albero) ((Nodo) rif2).contenitore();
		if (alb1 == alb2) return alb1.radice();
		return union(alb1, alb2);
	}

	/**
	 * Fonde gli insiemi il cui riferimento &egrave; indicato da input. 
	 * L'effettiva implementazione di questo metodo &egrave; delegata
	 * alle classi che derivano la classe <code>UnionFindBase</code>.
	 * 
	 * @param alb1 il riferimento al primo insieme da fondere
	 * @param alb2 il riferimento al secondo insieme da fondere
	 * @return il riferimento all'insieme risultante dalla fusione
	 */
	public abstract Rif union(Albero alb1, Albero alb2);

	/**
	 * Determina l'insieme contenente l'elemento <code>elem</code> indicato da input (<font color=red>Tempo O(log(n))</font>).
	 * L'insieme viene determinato invocando il metodo <code>contenitore</code>
	 * della classe <code>Nodo</code> sull'oggetto <code>elem</code>.
	 * 
	 * @param elem l'elemento di cui si vuole conoscere l'insieme di appartenza
	 * @return l'insieme contenente <code>elem</code>
	 */
	public Rif find(Rif elem) {
		Albero alb = (Albero) ((Nodo) elem).contenitore();
		return alb.radice();
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