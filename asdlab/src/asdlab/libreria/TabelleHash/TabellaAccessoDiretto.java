package asdlab.libreria.TabelleHash;

import asdlab.libreria.Eccezioni.EccezioneChiaveNonValida;
import asdlab.libreria.StruttureElem.Dizionario;

/* ============================================================================
 *  $RCSfile: TabellaAccessoDiretto.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:05:27 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.5 $
 */


/**
 * La classe <code>TabellaAccessoDiretto</code> implementa l'interfaccia <code>Dizionario</code>
 *  mediante una tabella ad accesso diretto. La tabella &egrave; a sua volta rappresentata
 *  utilizzando un array <code>v</code> di dimensione <code>m</code> tale che,
 *  se nel dizionario &egrave; presente un elemento <code>elem</code> con chiave <code>k</code>,
 *  allora tale elemento &egrave; memorizzato nella cella di indice <code>k</code>: <code>v[k] = elem</code>.
 *  Se invece una chiave <code>k</code> non &egrave; associata a nessun elemento nel dizionario,
 *  poniamo <code>v[k]=null</code> per segnalare che la cella non contiene nessun elemento. <br>In tale
 *  rappresentazione si mantengono i soli elementi dal momento che le chiavi sono implicitamente
 *  codificate negli indici dell'array. Per questa stessa ragione, si richiede, come prerequisito, che gli
 *  elementi da inserire nel dizionario siano indicizzati con chiavi di tipo <code>Integer</code>
 *  che assumano valore nell'intervallo <code>[0,m-1]</code>
 *
 */
public class TabellaAccessoDiretto implements Dizionario {

	/**
	 * Array di oggetti utilizzato per la rappresentazione della tabella 
	 */
	Object[] v;
	
	/**
	 * Costruttore per l'istanziazione di una nuova tabella ad accesso diretto
	 * 
	 * @param m taglia della tabella 
	 */
	public TabellaAccessoDiretto(int m) {
		v = new Object[m];
	}

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 * @throws EccezioneChiaveNonValida se <code>k</code> non &egrave; di tipo <code>Integer</code>
	 * @throws ArrayOutOfBoundsException se <code>k</code> non appartiene all'intervallo <code>[0,m-1]</code>
	 */	
	public void insert(Object e, Comparable k) {
		if (!(k instanceof Integer)) 
			throw new EccezioneChiaveNonValida();
		v[(Integer)k] = e;
	}

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param k chiave dell'elemento da cancellare
	 * @throws EccezioneChiaveNonValida se <code>k</code> non &egrave; di tipo <code>Integer</code>
	 * @throws ArrayOutOfBoundsException se <code>k</code> non appartiene all'intervallo <code>[0,m-1]</code>
	 */
	public void delete(Comparable k) {
		if (!(k instanceof Integer)) 
			throw new EccezioneChiaveNonValida();
		v[(Integer)k] = null;
	}

	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 * @throws EccezioneChiaveNonValida se <code>k</code> non &egrave; di tipo <code>Integer</code>
	 * @throws ArrayOutOfBoundsException se <code>k</code> non appartiene all'intervallo <code>[0,m-1]</code>
	 */
	public Object search(Comparable k) {
		if (!(k instanceof Integer)) 
			throw new EccezioneChiaveNonValida();
		return v[(Integer)k];
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
