package asdlab.libreria.UnionFind;

import asdlab.libreria.StruttureElem.Rif;

/* ============================================================================
 *  $RCSfile: UnionFind.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:09:57 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.10 $
 */

/**
 * Il tipo di dato union-find, specificato dall'interfaccia <code>UnionFind</code>, consente di mantenere una collezione di insiemi
 * disgiunti. Le operazioni supportate consentono di creare un nuovo insieme 
 * (<code>makeSet</code>), fondere due insiemi in uno (<code>union</code>)
 * e determinare l'insieme che contiene un dato elemento (<code>find</code>).
 */


public interface UnionFind {
	/**
	 * Crea un nuovo insieme e ne restituisce il riferimento.
	 * 
	 * @return il riferimento all'insieme creato
	 */
	public Rif makeSet();
	
	/**
	 * Fonde gli insiemi contenenti gli elementi indicati da input.
	 * 
	 * @param a il riferimento all'elemento contenuto nel primo insieme da fondere
	 * @param b il riferimento all'elemento contenuto nel secondo insieme da fondere
	 * @return il riferimento all'insieme derivante dalla fusione
	 */
	public Rif union(Rif a, Rif b);
	
	/**
	 * Determina l'insieme contenente l'elemento indicato da input.
	 * 
	 * @param elem l'elemento di cui si vuole conoscere l'insieme di appartenza
	 * @return l'insieme contenente <code>elem</code>
	 */
	public Rif find(Rif elem);
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