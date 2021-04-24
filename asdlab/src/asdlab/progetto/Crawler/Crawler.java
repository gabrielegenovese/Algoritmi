package asdlab.progetto.Crawler;

import asdlab.libreria.Grafi.*;

/* ============================================================================
 *  $RCSfile: Crawler.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.9 $
 */


/**
 * L'interfaccia <code>Crawler</code> definisce un metodo standard per la realizzazione di
 * crawler di siti Web. Il metodo <code>crawl</code> visita una porzione di Web a cominciare
 * da un indirizzo di partenza indicato da input e per un numero di pagine limitate
 * dalla memoria fisica disponibile e da un parametro, <code>maxDocs</code>, indicato da input.
 * Al termine della visita, il metodo restituisce un grafo orientato che rappresenta la porzione
 * di grafo del Web esplorata. In tale grafo, i nodi corrisponderanno alle pagine acquisite
 * e gli archi ai link eventualmente esistenti tra esse.
 *
 */
public interface Crawler {

	/**
	 * Opera il crawling del Web a cominciare da un indirizzo di partenza indicato da input.
	 * 
	 * @param urlIniz l'indirizzo Web da cui avviare il crawling
	 * @param dirArchivio il nome della directory archivio in cui salvare le pagine acquisite
	 * @param maxDocs il numero massimo di pagine da collezionare
	 * @param compresso abilita la compressione delle pagine archiviate
	 * @return il grafo corrispondente alla porzione di Web esplorata
	 */
	public Grafo crawl(String urlIniz, String dirArchivio, int maxDocs, boolean compresso);
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