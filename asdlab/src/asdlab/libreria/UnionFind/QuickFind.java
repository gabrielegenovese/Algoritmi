package asdlab.libreria.UnionFind;

import java.util.Iterator;

import asdlab.libreria.Alberi.Albero;
import asdlab.libreria.Alberi.AlberoPFFS;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: QuickFind.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:09:44 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.11 $
 */
/**
 * La classe <code>QuickFind</code> eredita la classe <code>UnionFindBase</code>
 * e rappresenta gli insiemi disgiunti mediante alberi di altezza 1. Gli
 * elementi di ciascun insieme sono rappresentati mediante i nodi del rispettivo albero,
 * mentre la radice di ciascun albero rappresenter&agrave; il riferimento (nome) dell'insieme. 
 * L'operazione di <code>union</code> implementata da <code>QuickFind</code> fonde due
 * alberi potando tutti i figli di una radice ed innestandoli come figli
 * dell'altra radice. 
 */

public class QuickFind extends UnionFindBase{
	
	
	/**
	 * Crea un nuovo insieme e ne restituisce il riferimento (<font color=red>Tempo O(1)</font>).
	 * L'insieme viene creato istanziando un nuovo albero di tipo
	 * <code>AlberoPFFS</code> contenente una radice ed un solo figlio, rappresentante
	 * dell'unico elemento dell'insieme creato. 
	 * 
	 * @return il riferimento all'elemento dell'insieme creato
	 */
	public Rif makeSet() {
		Albero alb = new AlberoPFFS();
		alb.aggiungiRadice(null);
		return alb.aggiungiFiglio(alb.radice(), null);
	}
	
	/**
	 * Fonde gli insiemi <code>alb1</code> ed <code>alb2</code> indicati
	 * da input (<font color=red>Tempo O(n)</font>). La fusione avviene spostando i figli di <code>alb2</code> a figli di <code>alb1</code>
	 * mediante operazioni di <code>innesta</code> e <code>pota</code> di <code>Albero</code>.
	 * 
	 * @param alb1 il riferimento all'elemento contenuto nel primo insieme da fondere
	 * @param alb2 il riferimento all'elemento contenuto nel secondo insieme da fondere
	 * @return il riferimento all'insieme derivante dalla fusione
	 */
	public Rif union(Albero alb1, Albero alb2) {
		Iterator figli = alb2.figli(alb2.radice()).iterator();
		while(figli.hasNext()){
			Albero alb = alb2.pota((Nodo) figli.next());
			alb1.innesta(alb1.radice(), alb);
		}
		return alb1.radice();
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
