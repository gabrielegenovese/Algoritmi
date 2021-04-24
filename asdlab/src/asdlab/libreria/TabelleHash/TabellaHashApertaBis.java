package asdlab.libreria.TabelleHash;

import asdlab.libreria.Eccezioni.EccezioneTabellaHashPiena;

/* ============================================================================
 *  $RCSfile: TabellaHashApertaBis.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:05:27 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.6 $
 */

/**
 * La classe <code>TabellaHashApertaBis</code> implementa una tabella hash aperta
 * con supporto per la cancellazione di elementi derivando la classe {@link TabellaHashAperta}
 * ed implementando il metodo <code>delete</code>. L'implementazione dell'operazione di cancellazione
 * avviene marcando con uno speciale valore le caselle della tabella da cancellare. Le successive
 * operazioni di scansione ignoreranno, oltrepassandole, le celle il cui contenuto assume questo valore. 
 */
public class TabellaHashApertaBis extends TabellaHashAperta {

	/**
	 * Oggetto generico utilizzato per marcare le posizioni della tabella il cui contenuto
	 * &egrave; stato cancellato
	 */
	private static Object canc = new Object();

	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash ad indirizzamento aperto
	 * con supporto per la canellazione, utilizzante la funzione hash basata sul metodo della divisione e la funzione di scansione
	 * lineare. L'istanziazione delle singole liste associate a ciascuna cella della tabella
	 * viene rinviata al primo inserimento che le riguardano.
	 * 
	 * @param m taglia della tabella 
	 */
	public TabellaHashApertaBis(int m) {
		super(m);
	}

	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash ad indirizzamento aperto
	 * con supporto per le cancellazioni.
	 * L'istanziazione delle singole liste associate a ciascuna cella della tabella
	 * viene rinviata al primo inserimento che le riguardano.
	 * 
	 * @param m taglia della tabella 
	 * @param fHash funzione hash da utilizzare per l'indicizzazione degli elementi nella tabella
	 * @param fScansione funzione da utilizzare per la scansione delle celle della tabella in caso di conflitti
	 */

	public TabellaHashApertaBis(int m, Hash fHash, Scansione fScansione) {
		super(m, fHash, fScansione);
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code>. L'elemento
	 * <code>e</code> viene inserito nella cella di indice <code>fHash.h(k,m)</code>. 
	 * Nel caso la cella sia occupata, si considera come prossimo 
	 * indice utile il valore <code>fScansione(fHash(k, m))</code>. La ricerca
	 * prosegue per un numero massimo di <code>m</code> tentativi
	 * prima di terminare con una eccezione.
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 * @throws EccezioneTabellaHashPiena se sono state effettuate <code>m-1</code> scansioni della lista senza
	 * aver individuato una cella libera per l'elemento da inserirsi
	 */	
	
	public void insert(Object e, Comparable k) {
		int hash = fHash.h(k, v.length);
		for (int i = 0; i < v.length - 1; i++) {
			int indice = fScansione.c(i, hash, v.length);
			if (v[indice] == null || v[indice].elemento == canc){
				v[indice] = new Record(e, k);
				return;
			}
		}
		throw new EccezioneTabellaHashPiena();
	}

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code>.
	 * L'elemento viene ricercato mediante il metodo <code>indice</code>.
	 * Se presente, l'elemento viene rimosso ponendone il valore a <code>canc</code>.
	 * 
	 * @param k chiave dell'elemento da cancellare
	 */	
	public void delete(Comparable k) {
		int indice = indice(k);
		if (indice != -1) v[indice].elemento = canc;
	}

	/**
	 * Restituisce l'indice dell'elemento con chiave <code>k</code>. 
	 * L'elemento viene ricercato nella cella di indice <code>fHash.h(k,m)</code>. 
	 * Nel caso la cella sia occupata da un altro elemento,
	 * si considera come prossimo indice utile il
	 * valore <code>fScansione(fHash(k, m))</code>. La ricerca prosegue
	 * omettendo le celle con valore <code>canc</code> sino a che non si raggiunge una cella
	 * vuota o l'elemento viene individuato.
	 * 
	 *  @param k chiave dell'elemento di cui si vuole conoscere l'indice
	 *  @return l'indice dell'elemento con chiave <code>k</code>
	 */
	protected int indice(Comparable k) {
		int hash = fHash.h(k, v.length);
		for (int i = 0; i < v.length - 1; i++) {
			int indice = fScansione.c(i, hash, v.length);
			if (v[indice] == null) break;
			if (v[indice].chiave.equals(k)
					&& v[indice].elemento != canc)
				return indice;
		}
		return -1;
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