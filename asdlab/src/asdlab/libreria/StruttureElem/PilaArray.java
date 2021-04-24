package asdlab.libreria.StruttureElem;

import asdlab.libreria.Eccezioni.*;

/* ============================================================================
 *  $RCSfile: PilaArray.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/22 17:06:14 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.5 $
 */


/**
 * La classe <code>PilaArray</code> implementa il tipo di dato Pila utilizzando
 * una rappresentazione basata su array dimensionato mediante la tecnica
 * del dimezzamento-raddoppiamento. <p>
 * Ogni oggetto della classe mantiene un contatore n del numero di elementi
 * effettivamente presenti nella pila e un array S di dimensione
 * iniziale 1 dove verranno mantenuti gli elementi della pila
 */
public class PilaArray implements Pila {
	/** 
	 * Rappresentazione interna del tipo <code>PilaArray</code>.
	 * La dimensione iniziale di <code>S</code>
	 * &egrave; posta pari a 1 per semplificare le successive
	 * operazioni di dimezzamento-raddoppiamento.
	 */
	private Object[] S = new Object[1];
	
	/**
	 * Il numero di elementi effettivamente presenti nella pila
	 */
	private int n = 0;

	/**
	 * Verifica se la pila &egrave; vuota (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return true, se la pila &egrave; vuota. false, altrimenti
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Aggiunge l'elemento <code>e</code> al termine della sequenza di elementi
	 * presenti nella pila (<font color=red>Tempo amm O(1)</font>). L'inserimento avviene nell'ultima
	 * posizione libera dell'array <code>S</code>, dopo aver eventualmente raddoppiato
	 * la taglia dello stesso.
	 * 
	 * @param e l'elemento da mantenere nella pila
	 */
	public void push(Object e) {
		if (n == S.length) {
			Object[] temp = new Object[2 * S.length];
			for (int i = 0; i < n; i++) temp[i] = S[i];
			S = temp;
		}
		S[n] = e;
		n = n + 1;
	}

	/**
	 * Restituisce l'ultimo elemento della sequenza di elementi
	 * presenti nella pila (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return l'ultimo elemento della pila
	 * @throws EccezioneStrutturaVuota se la pila &egrave; vuota
	 */
	public Object top() {
		if (this.isEmpty())
			throw new EccezioneStrutturaVuota("Pila vuota");
		return S[n - 1];
	}

	/** 
	 * Cancella l'ultimo elemento della sequenza di elementi
	 * presenti nella pila (<font color=red>Tempo amm O(1)</font>). Se il numero di celle inutilizzate
	 * raggiunge i 3/4 della taglia di <code>S</code>, l'array viene dimezzato.
	 * 
	 * @return l'elemento rimosso
	 * @throws EccezioneStrutturaVuota se la pila &egrave; vuota
	 */
	public Object pop() {
		if (this.isEmpty())
			throw new EccezioneStrutturaVuota("Pila vuota");
		n = n - 1;
		Object e = S[n];
		if (n > 1 && n == S.length / 4) {
			Object[] temp = new Object[S.length / 2];
			for (int i = 0; i < n; i++) temp[i] = S[i];
			S = temp;
		}		
		return e;
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