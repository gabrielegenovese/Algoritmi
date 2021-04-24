package asdlab.libreria.TabelleHash;

/* ============================================================================
 *  $RCSfile: FabbricaPrimi.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:05:27 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.5 $
 */

/**
 * La classe <code>FabbricaPrimi</code> implementa il metodo <code>genera</code> per la generazione
 * di numeri primi. Il suo scopo &egrave; quello di supportare la creazione di tabelle hash
 * la cui taglia &egrave; un numero primo, una condizione necessaria per l'utilizzo ottimale di tabelle
 * che impiegano funzioni di scansione per la risoluzione delle collisioni.
 */

public class FabbricaPrimi {
	/**
	 * Memorizza una collezione di 26 numeri primi pre-generati che spaziano nell'intervallo [53,1.610.612.741]
	 */
	public static final int[] primi = 
       { 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 
		 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 
		 12582917, 25165843, 50331653, 100663319, 201326611, 
		 402653189, 805306457, 1610612741 };

	/**
	 * Restituisce un numero primo m. Il numero generato m rispetta
	 * la propriet&agrave; che: 
	 * <br>
	 * n &le; m &le; 2n,
	 * <br> dove n &egrave; un parametro di input
	 * 
	 * @param n parametro di input 
	 * @return un numero primo compreso nell'intervallo [n,2n]
	 */
	public static int genera(int n) {
		int i;
		if (n > 1610612741) return 1610612741;
	    for (i = 0; primi[i] < n; i++) { }
		return primi[i];
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