package asdlab.libreria.TabelleHash;

/* ============================================================================
 *  $RCSfile: HashDivisione.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/23 20:32:36 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.3 $
 */


/**
 * La classe <code>HashDivisione</code> implementa il metodo
 * di hashing della divisione. La funzione hash calcolata da tale metodo
 * restituisce il resto della divisione della chiave <code>k</code> per <code>m</code>,
 * dove <code>m</code> &egrave; la dimensione della tabella.
 *
 */
public class HashDivisione implements Hash {
	/**
	 * Valuta la funzione hash basata sul metodo della divisione
	 * su una chiave di input <code>k</code> e restituisce un indice
	 * per una tabella di taglia <code>m</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param k la chiave su cui applicare la funzione hash
	 * @param m la taglia della tabella di cui generare l'indice
	 * @return l'indice della tabella determinato dalla funzione hash
	 */	
	public int h(Object k, int m) {
		return Math.abs(k.hashCode()) % m;
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