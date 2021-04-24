package asdlab.progetto.IndiceDiretto;

import java.io.*;
import java.util.Iterator;

/**
 * La classe <code>ParserDocumento</code> consente l'estrazione di parole da un fonte di
 * dati in input.
 * La classe &egrave; realizzata come implementazione dell'interfaccia standard
 * <code>Iterator</code> e consente di acquisire il contenuto di un documento
 * attraverso i metodi standard <code>next</code> ed <code>hasNext</code>. L'algoritmo di parsing esamina sequenzialmente la fonte
 * di dati in input determinando, ad ogni esecuzione del metodo <code>determinaProssimaParola</code>,
 * il solo valore della prossima parola da acquisire. Il processo di acquisizione
 * di una singola parola si arresta non appena si incontra
 * un qualsiasi carattere non alfabetico. La classe supporta anche il parsing di file
 * html dei quali ignora la struttura dei tag e considera la sola parte di contenuto
 * racchiusa nel documento.
 *
 */
public class ParserDocumento implements Iterator {


    /**
     * La prossima parola ad essere restituita nel parsing del documento
     */
	String prossimaParola;
    
	/**
	 * La parola successiva alla prossima ad essere restituita nel parsing del documento.  
	 */
    String parolaSuccessiva;
    
    /**
     * Il riferimento al flusso di dati in input su cui operare il parsing
     */
    Reader f;

    /**
     * Crea una nuova istanza della classe <code>ParserDocumento</code> per il parsing
     * di un documento indicato da input.
     * 
     * @param doc il documento da esaminare
     */
    public ParserDocumento(Reader doc) {
        f = doc;
        parolaSuccessiva = determinaProssimaParola();
    }

    /**
     * Operazione non supportata
     */
    public void remove() {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    /**
     * Verifica se esiste una ulteriore parola del documento da restituire.
     * 
     *  @return <code>true</code>, se esiste almeno un'altra parola da restituire. <code>false</code>, se si &egrave; giunti
     *  alla fine del documento  
     */
    public boolean hasNext() {
        if (parolaSuccessiva == null) {
            return false;
        }
        return true;
    }

    /**
     * Restituisce la prossima parola derivante dal parsing di <code>doc</code>. La parola
     * restituita &egrave; quella memorizzata nella variabile <code>prossimaParola</code>. Contestualmente,
     * la variabile <code>prossimaParola</code> assume il valore di <code>parolaSuccessiva</code>, ed il valore di <code>parolaSuccessiva</code>
     * viene aggiornato eseguendo il metodo <code>determinaProssimaParola</code>
     * per operare il parsing della parola succesiva.
     */
    public Object next() {
        prossimaParola = parolaSuccessiva;
        parolaSuccessiva = determinaProssimaParola();
        return prossimaParola.toLowerCase();
    }

    /**
     * Effettua il parsing del documento sino a determinare il valore della prossima parola.
     * Il parsing avviene esaminando la fonte di dati in input un carattere per volta, a partire
     * dall'ultima posizione considerata. La parola viene determinata come sequenza di caratteri
     * alfabetici terminata da un carattere non-alfabetico.
     * 
     * @return la prossima parola derivante dal parsing di <code>doc</code>
     */
    private String determinaProssimaParola() {
        StringBuffer sb = new StringBuffer();
        int nextChar;
        boolean insideTag = false;

        try {
            nextChar = f.read();
            while (nextChar != -1) {
                if (nextChar == '<') {
                    insideTag = true;
                } else if (nextChar == '>') {
                    insideTag = false;
                } else if (!insideTag) {
                    if (nextChar == 'à') {
                        sb.append('a');
                        sb.append((char) '\'');
                    }
                    else if (nextChar == 'è' || nextChar == 'é') {
                        sb.append('e');
                        sb.append((char) '\'');
                    }
                    else if (nextChar == 'ì') {
                        sb.append('i');
                        sb.append((char) '\'');
                    }
                    else if (nextChar == 'ò') {
                        sb.append('o');
                        sb.append((char) '\'');
                    }
                    else if (nextChar == 'ù') {
                        sb.append('u');
                        sb.append((char) '\'');
                    }
                    else if (Character.isLetter((char)nextChar)) 
                        sb.append((char) nextChar);
                    else {
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
                nextChar = f.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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