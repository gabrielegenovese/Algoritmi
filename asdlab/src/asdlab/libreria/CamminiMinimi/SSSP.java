package asdlab.libreria.CamminiMinimi;

import asdlab.libreria.Grafi.*;
import asdlab.libreria.Alberi.*;

/* ============================================================================
 *  $RCSfile: SSSP.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:17 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */

/**
 * L'interfaccia <code>SSSP</code> definisce un insieme di metodi necessari per la
 * soluzione del problema dei cammini minimi a sorgente singola (single-source
 * shortest paths, SSSP) di un grafo <code>G</code> orientato. L'interfaccia assume
 * che il grafo sia espresso come implementazione dell'interfaccia <code>Grafo</code>
 * e che i cammini debbano essere determinati a partire da un nodo sorgente <code>s</code>, appartenente
 * a <code>G</code>.
 */
public interface SSSP {


	/**
	 * Calcola i camminimi minimi a sorgente singola per il grafo di
	 * input <code>g</code> a partire dal nodo sorgente <code>s</code>.
	 * 
	 * @param g il grafo di cui calcolare il minimo albero ricoprente
	 * @param s il nodo sorgente da cui calcolare i cammini minimi
	 */
	public void calcola(Grafo g, Nodo s);

	/**
	 * Restituisce l'array contenente le distanze dei cammini minimi
	 * di ciascun nodo di un grafo <code>g</code> dal nodo sorgente <code>s</code>.
	 * I valori fanno riferimento all'ultima esecuzione del metodo <code>calcola</code>.
	 * Il generico valore di posizione <code>i</code> riporta la distanza
	 * da <code>s</code> del nodo di indice <code>i</code>.
	 * 
	 * @return l'array delle distanze dei cammini minimi
	 */
	public double[] distanze();
	
	/**
	 * 	
	 * Restituisce l'albero dei cammini minimi determinato dall'ultima
	 * esecuzione del metodo <code>calcola</code>.
	 * 
	 * @return l'albero dei cammini minimi determinato dall'ultima esecuzione di <code>calcola</code>
	 */
	public Albero albero();
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
