package asdlab.libreria.TabelleHash;

import asdlab.libreria.Eccezioni.EccezioneTabellaHashPiena;
import asdlab.libreria.StruttureElem.Dizionario;

/* ============================================================================
 *  $RCSfile: TabellaHashAperta.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:05:27 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.9 $
 */


/**
 * La classe <code>TabellaHash</code> implementa il tipo di dato Dizionario
 *  mediante una tabella hash basata su indirizzamento aperto. Tale tecnica
 * rappresenta la tabella hash mediante un array in cui ogni
 * cella in uso contiene coppie del tipo <code>(e,k)</code>.<br>
 * Siano <code>h</code> la funzione hash utilizzate e <code>m</code>
 * la taglia della tabella hash, qualora una operazione di inserimento tenti di utilizzare una cella
 * gi&agrave; occupata, si utilizza una funzione di scansione per
 * determinare l'indice della prossima cella da considerare ai fini dell'inserimento.
 * Le successive operazioni di ricerca e cancellazione 
 * di un elemento con chiave <code>k</code> utilizzano la stessa funzione
 * di scansione nel caso in cui la cella di indice <code>h(k,m)</code>
 * sia occupata da un elemento con chiave diversa da <code>k</code>
 * <br> La funzione di scansione utilizzata dalla tabella hash &egrave; codificata
 * come implementazione dell'interfaccia <code>Scansione</code>. La funzione
 * hash utilizzata dalla tabella hash &egrave; codificata come implementazione
 * dell'interfaccia <code>Hash</code>. Le funzioni hash e di scansione da utilizzarsi
 * in una tabella sono indicate da input all'atto dell'istanziazione della classe.
 * 
 */
public class TabellaHashAperta implements Dizionario {

	/** 
	 * Classe definita localmente ala classe <code>TabellaHashAperta</code>
	 * per il mantenimento dei record contenenti
	 * le coppie (elem, chiave)
	 *
	 */
	
	protected class Record {
		/**
		 * Elemento da conservare nel record
		 */
		public Object elemento;
		
		/**
		 * Chiave associata all'elemento conservato nel record
		 */
		public Comparable chiave;
		
		/**
		 * Costruttore per l'istanziazione di un nuovo record
		 * 
		 * @param elemento elemento da conservare nel record
		 * @param chiave chiave associata all'elemento da conservare nel record
		 */
		public Record(Object elemento, Comparable chiave) {
			this.elemento = elemento;
			this.chiave = chiave;
		}
	}
	/**
	 * Funzione hash per l'indicizzazione degli elementi della tabella
	 */
	protected Hash fHash;

	/**
	 * Funzione per la scansione delle celle della tabella in caso di conflitti
	 */
	protected Scansione fScansione;
	
	/**
	 * Array di record utilizzato per la rappresentazione della tabella
	 */
	protected Record[] v;

	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash ad indirizzamento aperto.
	 * L'istanziazione delle singole liste associate a ciascuna cella della tabella
	 * viene rinviata al primo inserimento che le riguardano.
	 * 
	 * @param m taglia della tabella 
	 * @param fHash funzione hash da utilizzare per l'indicizzazione degli elementi nella tabella
	 * @param fScansione funzione da utilizzare per la scansione delle celle della tabella in caso di conflitti
	 */
	public TabellaHashAperta(int m, Hash fHash, Scansione fScansione) {
		this.fHash = fHash;
		this.fScansione = fScansione;
		v = new Record[FabbricaPrimi.genera(m)];
	}
	
	/**
	 * Costruttore per l'istanziazione di una nuova tabella hash ad indirizzamento aperto
	 * utilizzante la funzione hash basata sul metodo della divisione e la funzione di scansione
	 * lineare. L'istanziazione delle singole liste associate a ciascuna cella della tabella
	 * viene rinviata al primo inserimento che le riguardano.
	 * 
	 * @param m taglia della tabella 
	 */
	public TabellaHashAperta(int m) {
		this(m, new HashDivisione(), new ScansioneQuadratica());
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
			if (v[indice] == null){
				v[indice] = new Record(e, k);
				return;
			}
		}
		throw new EccezioneTabellaHashPiena();
	}

	/**
	 * Operazione non supportata.
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void delete(Comparable k) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code>.
	 * L'elemento viene ricercato mediante una invocazione del metodo
	 * <code>indice</code>.
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	
	public Object search(Comparable k) {
		int indice = indice(k);
		if (indice == -1) return null;
		return v[indice].elemento;
	}

	/**
	 * Determina la posizione della chiave <code>k</code> ricercata. 
	 * La chiave viene inizialmente cercata nella cella di indice <code>fHash.h(k,m)</code>. 
	 * Nel caso la cella sia occupata da un'altra chiave,
	 * si considera come prossimo  indice utile il valore <code>fScansione(fHash(k, m))</code>. La ricerca
	 * prosegue per un numero massimo di <code>m</code> tentativi.
	 * 
	 * @param k la chiave di cui si vuole conoscere la posizione
	 * @return la posizione occupata da <code>k</code>. -1, se <code>k</code> &egrave; assente
	 */
	protected int indice(Comparable k) {
		int hash = fHash.h(k, v.length);
		for (int i = 0; i < v.length - 1; i++) {
			int indice = fScansione.c(i, hash, v.length);
			if (v[indice] == null) break;
			if (v[indice].chiave.equals(k))
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