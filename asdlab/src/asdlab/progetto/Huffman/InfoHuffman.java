package asdlab.progetto.Huffman;

/* ============================================================================
 *  $RCSfile: InfoHuffman.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:54 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.3 $
 */


/**
 * La classe <code>InfoHuffman</code> viene utilizzata per incapsulare
 * le informazioni relative ad ogni singolo carattere durante la costruzione
 * di un albero dei codici di Huffman. Per ciascun carattere riporta
 * il valore del carattere stesso e la frequenza con la quale il carattere
 * compare nel testo considerato. La classe viene anche utilizzata per rappresentare
 * i nodi interni di un albero dei codici. In tal caso, il campo <code>carattere</code>
 * non viene valorizzato mentre il campo indicante la frequenza esprime
 * la somma delle frequenze dei nodi dell'intero sottoalbero. La classe <code>InfoHuffman</code> implementa
 * l'interfaccia <code>Comparable</code> per semplificare le operazioni di ricerca dei nodi con frequenza minima.
 *
 */
public class InfoHuffman implements Comparable {
	/**
	 * Il carattere incapsulato nella classe
	 */
	public char carattere;
	/**
	 * La frequenza del carattere
	 */
	public float freq;
	
	/**
	 * Costruttore per la creazione di una nuova istanza di <code>InfoHuffman</code>.
	 * Il costruttore viene utilizzato per l'allocazione dei nodi interni dell'albero dei
	 * codici.
	 * 
	 * @param f la frequenza associata al nodo
	 */
	public InfoHuffman(float f){ this.freq = f;}
	
	/**
	 * Costruttore per la creazione di una nuova istanza di <code>InfoHuffman</code>.
	 * Il costruttore viene utilizzato per l'allocazione dei nodi foglia dell'albero dei codici.
	 * 
	 * @param f la frequenza associata al nodo
	 * @param c il carattere associato al nodo
	 */
	public InfoHuffman(float f, char c){ 
		this.carattere = c;
		this.freq = f;
	}

    /**
     * Determina l'ordinamento relativo esistente tra due istanze di <code>InfoHuffman</code>.
     * L'ordine determinato corrisponde all'ordine naturale delle
     * frequenze <code>freq</code> delle due istanze di <code>InfoHuffman</code>.
     * Nel caso le frequenze siano identiche,  si considera l'ordine naturale dei caratteri incapsulati
     * nelle rispettive istanze
     * 
     * @param o l'istanza di <code>InfoHuffman</code> da confrontare con l'istanza corrente
     * @return -1, se l'istanza corrente precede <code>o</code>. <br>+1, se <code>o</code> precede
     * l'istanza corrente.<br> 0, se le due istanze sono identiche.
     * @throws ClassCastException se l'oggetto <code>o</code> non &egrave; di tipo <code>InfoHuffman</code>
     */
    public int compareTo(Object o) {
        if (o instanceof InfoHuffman){
        	InfoHuffman infoh = (InfoHuffman) o;
            if (freq == infoh.freq){
                if (carattere < infoh.carattere) return -1;
                if (carattere > infoh.carattere) return 1;
                return 0;
            }

            if (freq < infoh.freq) return -1;
            if (freq > infoh.freq) return 1;
        }
        
        throw new ClassCastException(); 
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