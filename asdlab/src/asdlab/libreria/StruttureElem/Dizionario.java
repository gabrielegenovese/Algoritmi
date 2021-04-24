package asdlab.libreria.StruttureElem;

/* ============================================================================
 *  $RCSfile: Dizionario.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:44:23 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */


/**
 * Un dizionario &egrave; una collezione di elementi a cui sono associate chiavi.
 * Ad esempio, un elenco telefonico potrebbe essere gestito mediante un
 * dizionario che ha come elementi i numeri telefonici e come chiavi
 * i nominativi degli abbonati. Operazioni tipiche su un dizionario
 * sono l'inserimento (<code>insert</code>) di un elemento e di una chiave ad
 * esso associata, la cancellazione (<code>delete</code>) di un elemento data
 * una chiave, e la ricerca (<code>search</code>) dell'elemento associato
 * a una data chiave. L'interfaccia <code>Dizionario</code> supporta le tre operazioni
 * <code>insert</code>, <code>search</code> e <code>delete</code>
 * assumendo che gli elementi da conservare nel dizionario siano oggetti generici di tipo
 * <code>Object</code> e che le chiavi usate per indicizzarle siano oggetti di tipo <code>Comparable</code>.
 * Tale interfaccia rappresenta una variante dell'interfaccia {@link asdlab.libreria.AlberiRicerca.Dizionario}
 * offerta dal package <code>asdlab.libreria.AlberiRicerca</code> poich&eacute;,
 * a differenza di quest'ultima, il metodo delete riceve la chiave dell'elemento
 * da cancellare, piuttosto che il suo riferimento all'interno del dizionario.
 * 
 */

public interface Dizionario { 
	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code>.
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */
	public void insert(Object e, Comparable k);
	
	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code>.
	 * In caso di duplicati, l'elemento cancellato
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * 
	 * @param k chiave dell'elemento da cancellare
	 */
	public void delete(Comparable k);
	
	/**
	 * Restituisce l'elemento e con chiave <code>k</code>.
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	public Object search(Comparable k);
	
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