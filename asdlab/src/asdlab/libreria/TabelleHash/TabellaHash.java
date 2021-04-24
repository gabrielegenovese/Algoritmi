package asdlab.libreria.TabelleHash;

import asdlab.libreria.StruttureElem.Dizionario;

/* ============================================================================
 *  $RCSfile: TabellaHash.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/23 20:32:36 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.5 $
 */


/**
 * La classe <code>TabellaHash</code> implementa il tipo di dato Dizionario
 *  mediante una tabella hash rappresentata tramite un array di taglia <code>m</code>. La posizione
 * in cui memorizzare un elemento <code>e</code> con chiave <code>k</code>
 * &egrave; data dal valore <code>h(k)</code> assunto dalla funzione hash <code>h</code>
 * in <code>k</code>. La funzione hash da utilizzarsi viene specificata da input
 * all'atto dell'istanziazione della classe ed &egrave; codificata come implementazione
 * dell'interfaccia <code>Hash</code>. La classe <code>TabellaHash</code> non supporta
 * la gestione delle collisioni ed &egrave pertanto in grado di poter operare correttamente
 * soltanto con funzioni hash perfette.
 *
 */
public class TabellaHash implements Dizionario {
	

	/**
	 * Funzione hash per l'indicizzazione degli elementi della tabella
	 */
	protected Hash fHash;
	
	/**
	 * Array di oggetti utilizzato per la rappresentazione della tabella 
	 */
	private Object[] v;

	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash. 
	 * 
	 * @param m taglia della tabella
	 * @param fHash funzione hash da utilizzare per l'indicizzazione degli elementi nella tabella
	 */
	public TabellaHash(int m, Hash fHash) {
		v = new Object[m];
		this.fHash = fHash;
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */	
	public void insert(Object e, Comparable k) {
		v[fHash.h(k, v.length)] = e;
	}

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param k chiave dell'elemento da cancellare
	 */
	public void delete(Comparable k) {
		v[fHash.h(k, v.length)] = null;
	}

	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	public Object search(Comparable k) {
		return v[fHash.h(k, v.length)];
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