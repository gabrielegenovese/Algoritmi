package asdlab.progetto.Huffman;

import java.io.IOException;
import java.io.InputStream;

/* ============================================================================
 *  $RCSfile: LettoreBit.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:54 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.8 $
 */

/**
 * La classe <code>LettoreBit</code> consente la lettura di file contenenti
 * sequenze di bit e creati mediante la classe <code>ScrittoreBit</code>. 
 * La classe opera leggendo, come caratteri, il contenuto di un file di input ed
 * utilizzando gli operatori binari del linguaggio Java per estrapolarne
 * il contenuto. La strategia di interpretazione del file prevede il mantenimento
 * in memoria del prossimo carattere da interpretare per estrapolarne
 * i bit che vi compaiono, <code>carattereCorrente</code>, e dei due caratteri
 * successivi, <code>secondoCarattere</code> e <code>terzoCarattere</code>. 
 * Questi ultimi due caratteri vengono esaminati da <code>LettoreBit</code>
 * per verificare le condizioni di raggiungimento di fine file ed, eventualmente,
 * stabilire il numero di bit significativi da estrapolare dall'ultimo carattere
 * letto, in accordo al formato del file stabilito dalla classe {@link ScrittoreBit}.
 *  
 */


public class LettoreBit {

	/**
	 * Maschera di bit utilizzata per le operazioni di estrapolazione dei bit
	 */
	private int andMask = 128;
	
	/**
	 * Numero di bit significativi per ciascun carattere
	 */
	private int numBit = 8;

	/**
	 * Flag booleano per l'attivazione della modalit&agrave; di debug
	 */
	private static boolean debug = false;

	
	/**
	 * Il carattere corrente del file di input da cui sono attualmente estrapolate sequenze di bit
	 */
	private int carattereCorrente;
	
	/**
	 * Il carattere successivo a <code>carattereCorrente</code>
	 */
	private int secondoCarattere;
	
	/**
	 * Il carattere successivo a <code>secondoCarattere</code>. <code>-1</code>, nel caso il file sia terminato
	 */
	private int terzoCarattere;

	/**
	 * L'indice all'interno di <code>carattereCorrente</code> del prossimo bit da estrapolare 
	 */
	private int idBitCorrente = 0;

	/**
	 * Riferimento al file da cui leggere le sequenze di bit
	 */
	private InputStream reader;

	/**
	 * Costruttore per la creazione di una nuova istanza di <code>LettoreBit</code>.
	 * All'atto dell'istanziazione, il costruttore riceve un riferimento
	 * al file da cui leggere sequenze di bit e lo utilizza per acquisirne i primi
	 * tre caratteri, memorizzati rispettivamente in, <code>carattereCorrente</code>,
	 * <code>secondoCarattere</code> e <code>terzoCarattere</code>.
	 * 
	 * @param reader il file da cui leggere le sequenze di bit
	 * @throws IOException se sono stati riscontrati errori durante l'accesso al file di input
	 */
	public LettoreBit(InputStream reader) throws IOException {
		this.reader = reader;
		carattereCorrente = reader.read();
		if (debug) System.out.println("Lettura carattere: " + carattereCorrente);
		secondoCarattere = reader.read();
		terzoCarattere = reader.read();
		if (terzoCarattere == -1) {
			idBitCorrente = numBit - secondoCarattere;
			carattereCorrente = carattereCorrente << idBitCorrente;
		}
	}

	/**
	 * Restituisce il prossimo bit codificato nel file di input. Il metodo
	 * opera utilizzando gli operatori binari del linguaggio Java per estrapolare
	 * da <code>carattereCorrente</code> il prossimo bit, alla posizione indicata
	 * dalla variabile <code>idBitCorrente</code>. Se il contenuto di <code>carattereCorrente</code>
	 * &egrave; stato gi&agrave; interamente estrapolato, si legge dal file
	 * di input un nuovo carattere e si aggiornano le variabili <code>carattereCorrente</code>,
	 * <code>secondoCarattere</code> e <code>terzoCarattere</code> di conseguenza.
	 * 
	 * @return il prossimo bit della sequenza di input. <code>-1</code>, se la sequenza &egrave; terminata
	 * @throws IOException se sono stati riscontrati problemi durante l'accesso al file di input
	 */
	public int leggiBit() throws IOException {

		if (idBitCorrente == numBit) {
			if (debug) System.out.println("\nCarattere corrente: " + secondoCarattere);
			

			if (terzoCarattere == -1) return -1;

			carattereCorrente = secondoCarattere;
			secondoCarattere = terzoCarattere;

			terzoCarattere = reader.read();

			idBitCorrente = 0;

			if (terzoCarattere == -1) {
				idBitCorrente = numBit - secondoCarattere;
				carattereCorrente = carattereCorrente << (numBit - secondoCarattere);
			}

		}

		int prossimoBit = andMask & carattereCorrente;
		if (debug) System.out.print(prossimoBit > 0 ? 1 : 0);
		if (idBitCorrente < numBit)
			idBitCorrente++;
		carattereCorrente = carattereCorrente << 1;

		return prossimoBit > 0 ? 1 : 0;
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