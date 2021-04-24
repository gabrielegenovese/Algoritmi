package asdlab.libreria.TabelleHash;

/* ============================================================================
 *  $RCSfile: ScansioneLineare.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:05:27 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */


/**
 * La classe <code>ScansioneLineare</code> implementa l'interfaccia <code>Scansione</code>
 * tramite una funzione di scansione lineare. Siano <code>i</code> l'indice corrente, <code>hk</code>
 * il risultato della funzione hash applicata alla chiave <code>k</code>
 * ed <code>m</code> la taglia della tabella, la funzione di scansione lineare <code>c(i,hk,m)</code> 
 * restituisce l'indice dato dalla seguente formula: <p>
 * <code>(hk + i) % m</code>
 * 
 */
public class ScansioneLineare implements Scansione {

	/**
	 * Determina il prossimo indice da considerare nella scansione lineare di una tabella hash (<font color=red>Tempo O(1)</font>). 
	 * 
	 * @param i l'indice della posizione corrente nella scansione
	 * @param hk il risultato dell'applicazione della funzione hash alla chiave di input
	 * @param m la taglia della tabella
	 * @return il prossimo indice della tabella da considerare nella scansione
	 */
	public int c(int i, int hk, int m) {
		return (hk + i) % m;
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
