package asdlab.libreria.StruttureElem;

import asdlab.libreria.Eccezioni.EccezioneChiaveNonValida;

/* ============================================================================
 *  $RCSfile: ArrayDoubling.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:44:23 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.8 $
 */

/**
 * La classe <code>ArrayDoubling</code> implementa il tipo di dato Dizionario
 * mantenendo le coppie (elemento, chiave) in un array S dimensionato
 * secondo la tecnica del raddoppiamento-dimezzamento (doubling-halving).
 * Tale tecnica consiste nel mantenere gli n elementi del dizionario
 * in un array di dimensione h, dove per ogni n &gt; 0, h soddisfa la seguente invariante: 
 * <p> n &le; h &lt; 4n. 
 * <p> Le prime n celle dell'array contengono gli elementi della collezione, mentre il 
 * contenuto delle altre celle &egrave; indefinito.
 */
public class ArrayDoubling implements Dizionario {

	/** 
	 * Mantiene tutte le coppie (elemento, chiave)
	 * attualmente presenti nel dizionario. 
	 * La dimensione iniziale di <code>S</code>
	 * &egrave; posta pari a uno per semplificare le successive
	 * operazioni di dimezzamento-raddoppiamento.
	 */
 
	private Coppia[] S = new Coppia[1];

	/**
	 * Il numero di elementi effettivamente presenti nel dizionario
	 */
	private int n;

    /**
     * Classe definita localmente alla classe <code>ArrayDoubling</code>
     * per il mantenimento delle coppie (elemento, chiave)
     *
     */
   
	public class Coppia {
		public Object elemento;

		public Comparable chiave;

		public Coppia(Object elemento, Comparable chiave) {
			this.elemento = elemento;
			this.chiave = chiave;
		}
	}

	/**
	 * Crea un nuovo dizionario di tipo <code>ArrayDoubling</code> con taglia
	 * iniziale pari a zero.
	 *
	 */
	public ArrayDoubling() {
		n = 0;
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo amm O(1)</font>).
	 * L'inserimento avviene nell'ultima posizione libera
	 * dell'array <code>S</code>, dopo aver eventualmente raddoppiato
	 * la taglia dello stesso.
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */
	public void insert(Object e, Comparable k) {
		if (n == S.length) {
			Coppia[] temp = new Coppia[2 * S.length];
			for (int i = 0; i < n; i++) temp[i] = S[i];
			S = temp;
		}
		S[n] = new Coppia(e, k);
		n = n + 1;
	}

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code> (<font color=red>Tempo O(n)</font>).
	 * In caso di duplicati, l'elemento cancellato
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca dell'elemento da cancellare avviene
	 * mediante una scansione lineare dell'array.
	 * Se il numero di celle inutilizzate raggiunge
	 * i 3/4 della taglia di <code>S</code>, l'array viene dimezzato.
	 * 
	 * @param k chiave dell'elemento da cancellare
	 * @throws EccezioneChiaveNonValida se la chiave ricercata non &egrave; presente nel dizionario
	 */
	public void delete(Comparable k) {
		int i;
		for (i = 0; i < S.length; i++)
			if (k.equals(S[i].chiave)) break;
		if (i == S.length)
			throw new EccezioneChiaveNonValida();
		n = n - 1;
		S[i] = S[n];

		if (n > 1 && n == S.length / 4) {
			Coppia[] temp = new Coppia[S.length / 2];
			for (i = 0; i < n; i++) temp[i] = S[i];
			S = temp;
		}
	}

	/**
	 * Restituisce l'elemento e con chiave <code>k</code> (<font color=red>Tempo O(n)</font>).
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca dell'elemento con chiave <code>k</code> 
	 * avviene mediante una scansione lineare dell'array.
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
	public Object search(Comparable k) {
		for (int i = 0; i < n; i++)
			if (k.equals(S[i].chiave)) return S[i].elemento;
		return null;
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
