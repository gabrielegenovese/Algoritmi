package asdlab.libreria.Grafi;

import java.util.List;
import asdlab.libreria.Alberi.Nodo;

/* ============================================================================
 *  $RCSfile: Visita.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.3 $
 */

/**
 * L'interfaccia <code>Visita</code> definisce un metodo standard,
 * <code>calcolaLista</code>, per il calcolo della lista
 * dei nodi esplorati durante la visita di un grafo <code>G</code>
 * a partire da un nodo <code>s</code>.
 */
public interface Visita {

	/**
	 * Calcola e restituisce la lista dei nodi del grafo <code>g</code>
	 * nell'ordine in cui sono esplorati dall'algoritmo di visita
	 * a partire dal nodo <code>s</code>.
	 * 
	 * @param g il grafo da visitare
	 * @param s il nodo di partenza della visita
	 * @return la lista dei nodi esplorati durante la visita
	 */
	public List calcolaLista(Grafo g, Nodo s);
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