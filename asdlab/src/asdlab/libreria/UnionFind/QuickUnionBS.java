package asdlab.libreria.UnionFind;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: QuickUnionBS.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:45:01 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.3 $
 */

/**
 * La classe <code>QuickUnionBS</code> deriva la classe
 * <code>QuickUnion</code> introducendo una euristica di bilanciamento
 * sull'operazione di <code>union</code>. L'euristica opera innanzitutto
 * associando a ciascun albero la sua cardinalit&agrave;, esplicitamente
 * mantenuta nella radice dell'albero stesso. Ad ogni operazione
 * di <code>union</code> di due insiemi, <code>QuickUnionBS</code>
 * determina l'ordine di fusione in modo da fondere l'albero di cardinalit&agrave;
 * minore nell'albero di cardinalit&agrave; maggiore.
 *
 */


public class QuickUnionBS extends QuickUnion {
	
	/**
	 * Crea un nuovo insieme e ne restituisce il riferimento (<font color=red>Tempo O(1)</font>).
	 * L'insieme viene creato istanziando un nuovo albero di tipo
	 * <code>AlberoPFFS</code> contenente una radice ed un solo figlio, rappresentante
	 * dell'unico elemento dell'insieme creato. Alla radice dell'albero
	 * creato viene associata la cardinalit&agrave; 1.
	 * 
	 * @return il riferimento all'elemento dell'insieme creato
	 */
	public Rif makeSet() {
		Albero alb = new AlberoPFFS();
		return alb.aggiungiRadice(1);
	}

	/**
	 * Fonde gli insiemi <code>alb1</code> ed <code>alb2</code> indicati
	 * da input secondo le loro cardinalit&agrave; (<font color=red>Tempo O(1)</font>). 
	 * La fusione avviene innestando, come figlio, la radice dell'albero
	 * di cardinalit&agrave; inferiore alla radice dell'albero di cardinalit&agrave; superiore, 
	 * mediante operazioni di <code>innesta</code> e <code>pota</code> di <code>Albero</code>.
	 * Nel caso in cui <code>alb1</code> ed <code>alb2</code> abbiano la stessa cardinalit&agrave;
	 * <code>alb2</code> viene fuso in <code>alb1</code>. 
	 * Infine, aggiorna la cardinalit&agrave; dell'albero derivante dalla fusione.
	 * 
	 * @param alb1 il riferimento al primo insieme da fondere
	 * @param alb2 il riferimento al secondo insieme da fondere
	 * @return il riferimento all'insieme derivante dalla fusione
	 */
	public Rif union(Albero alb1, Albero alb2) {	
		int size1 = (Integer) alb1.info(alb1.radice());
		int size2 = (Integer) alb2.info(alb2.radice());
		if (size1 < size2) {
			alb2.cambiaInfo(alb2.radice(), size1 + size2);
			return super.union(alb2, alb1);
		}
		alb1.cambiaInfo(alb1.radice(), size1 + size2);
		return super.union(alb1, alb2);	
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
