package asdlab.libreria.MST;

import asdlab.libreria.Alberi.*;
import asdlab.libreria.Grafi.*;

/* ============================================================================
 *  $RCSfile: MST.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/30 18:53:44 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.5 $
 */

/**
 * L'interfaccia <code>MST</code> definisce un insieme di metodi necessari per il calcolo del minimo
 * albero ricoprente (minimum spanning tree, MST) di un grafo <code>G</code> non orientato espresso come implementazione
 * dell'interfaccia <code>Grafo</code>.
 */


public interface MST {

	/**
	 * Calcola il minimo albero ricoprente del grafo di input <code>g</code>.
	 * 
	 * @param g il grafo di cui calcolare il minimo albero ricoprente
	 */
	public void calcola(Grafo g);
	
	/**
	 * Restituisce il minimo albero ricoprente calcolato dall'ultima
	 * esecuzione del metodo <code>calcola</code>.
	 * 
	 * @return il minimo albero ricoprente determinato dall'ultima esecuzione di <code>calcola</code>
	 */
	public Albero albero();
	
	/**
	 * Restituisce il costo (i.e., la somma dei pesi degli archi) del minimo albero ricoprente
	 * determinato durante l'ultima esecuzione del metodo <code>calcola</code>.
	 * 
	 * @return il costo dell'ultimo minimo albero ricoprente determinato da <code>calcola</code>
	 */
	public double costo();
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