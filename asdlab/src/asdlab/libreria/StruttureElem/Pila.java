package asdlab.libreria.StruttureElem;

/* ============================================================================
 *  $RCSfile: Pila.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/22 17:06:14 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */


/**
 * Una pila &egrave; una struttura dati lineare nella quale 
 * gli accessi avvengono secondo uno schema di tipo LIFO (last-in, first-out).
 * Questo implica che gli inserimenti in una pila (<code>push</code>)
 * aggiungono elementi alla fine della sequenza, mentre 
 * le cancellazioni (<code>pop</code>) ne rimuovono sempre l'ultimo elemento. 
 * In una pila, gli accessi avvengono quindi ad una sola estremit&agrave;
 * della sequenza di elementi, e nessun elemento interno pu&ograve;
 * essere estratto prima di aver estratto tutti quelli che sono
 * stati inseriti dopo di esso.
 */
public interface Pila {
	/**
	 * Verifica se la pila &egrave; vuota.
	 * 
	 * @return <code>true</code>, se la pila &egrave; vuota. <code>false</code>, altrimenti
	 */
	public boolean isEmpty();
	
	/**
	 * Aggiunge l'elemento <code>e</code> al termine della sequenza di elementi
	 * presenti nella pila.
	 * 
	 * @param e l'elemento da mantenere nella pila
	 */
	public void push(Object e);
	
	/**
	 * Restituisce l'ultimo elemento della sequenza di elementi
	 * presenti nella pila.
	 * 
	 * @return l'ultimo elemento della pila
	 */
	public Object top();
	
	/** 
	 * Cancella l'ultimo elemento della sequenza di elementi presenti nella pila.
	 * 
	 * @return l'elemento rimosso
	 */
	public Object pop();
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
