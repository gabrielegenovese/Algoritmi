package asdlab.libreria.TabelleHash;

import asdlab.libreria.StruttureElem.Dizionario;
import asdlab.libreria.StruttureElem.StrutturaCollegata;


/* ============================================================================
 *  $RCSfile: TabellaHashListeColl.java,v $
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
 * La classe <code>TabellaHashListeColl</code> implementa
 * l'interfaccia <code>Dizionario</code> mediante una tabella
 * hash basata su liste di collisione. Tale tecnica
 * associa a ciascuna cella della tabella hash una lista
 * di chiavi, detta appunto lista di collisione, piuttosto che una singola chiave.
 * Laddove due chiavi dovessero collidere sulla stessa cella, entrambe
 * le chiavi verranno a trovarsi all'interno della lista associata a quella
 * cella. Per verificare se una chiave <code>k</code> &egrave; presente all'interno
 * della tabella, sar&agrave; quindi necessario cercarla nella lista con indice <code>h(k, m)</code>,
 * dove <code>h</code> &egrave; la funzione di hash utilizzata ed <code>m</code> la taglia della tabella. 
 * 
 */
public class TabellaHashListeColl implements Dizionario  {

	/**
	 * Funzione hash per l'indicizzazione degli elementi della tabella
	 */
	private Hash fHash;

	/**
	 * Array di liste collegate utilizzato per la rappresentazione della tabella
	 */
	private StrutturaCollegata[] v;

	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash con liste collegate.
	 * L'istanziazione delle singole liste associate a ciascuna cella della tabella
	 * viene rinviata al primo inserimento che le riguarda
	 * 
	 * @param m taglia della tabella da istanziare
	 * @param fHash funzione hash da utilizzare per l'indicizzazione degli elementi nella tabella
	 */
	public TabellaHashListeColl(int m, Hash fHash) {
		this.fHash = fHash;
		v = new StrutturaCollegata[m];
	}
	
	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash con liste collegate utilizzante
	 * la funzione hash basata sul metodo della divisione
	 * 
	 * @param m taglia della tabella da istanziare
	 */
	public TabellaHashListeColl(int m) {
		this(m, new HashDivisione());
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo O(1)</font>). L'elemento
	 * <code>e</code> viene inserito in testa alla lista di indice
	 * <code>fHash.h(k,m)</code>. Nel caso in cui la lista non esista ancora,
	 * viene istanziata sul momento
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */	
	public void insert(Object e, Comparable k) {
		int indice = fHash.h(k, v.length);
		if (v[indice] == null) v[indice] = new StrutturaCollegata();
		v[indice].insert(e, k);
	}

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code> (<font color=red>Tempo O(n + m)</font>).
	 * L'elemento viene ricercato, ed eventualmente rimosso, nella 
	 * lista di indice <code>fHash.h(k,m)</code>
	 * 
	 * @param k chiave dell'elemento da cancellare
	 */
	public void delete(Comparable k) {
		StrutturaCollegata l = v[fHash.h(k, v.length)];
		if (l == null) return;
		l.delete(k);
	}

	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code> (<font color=red>Tempo O(n + m)</font>).
	 * L'elemento viene ricercato nella lista di indice <code>fHash.h(k,m)</code>
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	public Object search(Comparable k) {
		StrutturaCollegata l = v[fHash.h(k, v.length)];
		if (l == null) return null;
		return l.search(k);
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