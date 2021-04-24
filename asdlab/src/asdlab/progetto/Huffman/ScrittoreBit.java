package asdlab.progetto.Huffman;

import java.io.IOException;
import java.io.OutputStream;


/* ============================================================================
 *  $RCSfile: ScrittoreBit.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.9 $
 */


/**
 * La classe <code>ScrittoreBit</code> consente la scrittura su file di sequenze di bit. 
 * La classe opera assemblando, tramite gli operatori binari del linguaggio Java,
 * numeri interi rappresentanti sequenze di bit indicate da input tramite il metodo
 * <code>scriviParola</code>. bit scritti vengono accumulati in una variabile temporanea,
 * <code>carattereCorrente</code>, per poi essere memorizzati su file non appena raggiungono
 * il numero di 8. Il formato del file prevede pertanto che le
 * sequenze di bit siano aggregate, mediante numeri di tipo <code>int</code>,
 * in gruppi di <code>8</code> bit ciascuno. Si prevede inoltre la presenza
 * di un valore numerico al termine del file che indica il numero di bit da considerare
 * nell'ultimo valore di tipo int che codifica la sequenza di bit, utile nel caso la
 * sequenza originale contasse un numero di bit non multiplo di <code>8</code>.
 * 
 */
public class ScrittoreBit {

	/**
	 * Variabile temporanea utilizzata per mantenere la sequenza di bit in attesa di essere memorizzata su file
	 */
	private int carattereCorrente;

	/**
	 * Numero di bit attualmente presenti nella variabile <code>carattereCorrente</code>
	 */
	private int conteggioBit;
	
	/**
	 * Flag booleano per l'attivazione della modalit&agrave; di debug
	 */
	private static boolean debug = false;
	
	/**
	 * Riferimento al file utilizzato per la memorizzazione delle sequenze di bit
	 */
	private OutputStream writer;

	/**
	 * Crea una nuova istanza della classe <code>ScrittoreBit</code>. 
	 * 
	 * @param writer il riferimento al file da utilizzare per la memorizzazione delle sequenze di bit
	 */
	public ScrittoreBit(OutputStream writer) {
		if (debug) System.out.println("*** Scrittore");
		this.writer = writer;
	}

	
	/**
	 * Conclude la scrittura della sequenza di bit su file. Il metodo
	 * opera scrivendo su file i bit residui presenti in <code>carattereCorrente</code> ed accodando al file
	 * stesso, come terminatore, il valore indicante il numero di bit significativi dell'ultimo valore 
	 * di <code>carattereCorrente</code> scritto.
	 * 
	 * @throws IOException se sono stati riscontrati errori durante la scrittura del file di output
	 */
	public void chiudi() throws IOException {
			writer.write(carattereCorrente);
			writer.write(conteggioBit);
			writer.flush();
	}

	/**
	 * Scrive al termine del file la sequenza di bit indicata da input.
	 * Si assume che la sequenza sia codificata come una stringa
	 * in cui ciascun carattere assume il valore <code>0</code> o <code>1</code>.
	 * Il metodo opera scrivendo ciascun bit della sequenza mediante il metodo
	 * <code>scriviBit</code>.
	 * 
	 * @param parola la sequenza di bit da scrivere
	 * @throws IOException se sono stati riscontrati errori durante la scrittura del file di output
	 */
	public void scriviParola(String parola) throws IOException {
		for (int i = 0; i < parola.length(); i++)
			scriviBit(parola.charAt(i));
	}
	
	/**
 	 * Scrive un bit al termine del file. Il bit viene accumulato
 	 * nella variabile temporanea <code>carattereCorrente</code>
 	 * utilizzando gli operatori per l'aritmetica binaria.
 	 * Nel caso si raggiunga la capacit&agrave; massima di <code>carattereCorrente</code>,
 	 * il suo contenuto viene concretamente memorizzato nel file di output.  
 	 * 
 	 * @param bit il bit da scrivere
	 * @throws IOException se sono stati riscontrati errori durante la scrittura del file di output
	 */
	public void scriviBit(int bit) throws IOException {
		if (debug) System.out.print(bit);
		if (!(bit == 0 || bit == 1)) {
			return;
		}

		carattereCorrente = carattereCorrente << 1;
		carattereCorrente += bit;

		conteggioBit++;

		if (conteggioBit == 8) {
			if (debug){
				System.out.println();
				System.out.println("Scrittura carattere: " + carattereCorrente);
			}
			writer.write(carattereCorrente);
			conteggioBit = 0;
			carattereCorrente = 0;
		}

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