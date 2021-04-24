package asdlab.libreria.TabelleHash;

/* ============================================================================
 *  $RCSfile: Scansione.java,v $
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
 * L'interfaccia <code>Scansione</code> definisce una interfaccia generica per l'implementazione
 * di funzioni di scansione per una tabella hash. 
 *
 */
public interface Scansione {
	/**
	 * Determina il prossimo indice da considerare nella scansione di una tabella hash. 
	 * 
	 * @param i l'indice della posizione corrente nella scansione
	 * @param hk il risultato dell'applicazione della funzione hash alla chiave di input
	 * @param m la taglia della tabella
	 * @return il prossimo indice della tabella da considerare nella scansione
	 */
	public int c(int i, int hk, int m);
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
