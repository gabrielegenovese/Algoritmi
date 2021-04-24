package asdlab.progetto.IndiceDiretto;

import java.io.*;
import java.util.*;

import asdlab.libreria.StruttureElem.*;
import asdlab.progetto.Lexicon.*;


/* ============================================================================
 *  $RCSfile: IndiceDiretto.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:54 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.12 $
 */

/**
 * La classe <code>IndiceDiretto</code> mantiene informazioni sulla frequenza
 * delle parole che compaiono all'interno di un documento. Le informazioni
 * vengono mantenute associando a ciascuna parola un identificatore numerico univoco,
 * tramite la classe <code>Lexicon</code>. L'identificatore e la frequenza di ciascuna parola
 * vengono mantenuti in una lista di istanze della classe <code>Hit</code>. 
 * L'indicizzazione delle parole e la costruzione della relativa lista avvengono
 * per mezzo dei due metodi <code>indicizzaDoc</code>.
 * 
 */
public class IndiceDiretto {

    /**
     * Elenco degli <code>Hit</code> presenti nell'indice diretto
     */
    private LinkedList listaHit;
    
    /**
     * Istanza di <code>Lexicon</code> utilizzata per associare ad ogni parola un identificatore numerico univoco
     */
    public  Lexicon    lexicon;

    /**
     * Costruttore per l'allocazione di una nuova istanza di <code>IndiceDiretto</code>
     *
     */
    public IndiceDiretto() {
        listaHit = new LinkedList();
        lexicon  = new Lexicon();
    }

    
    /**
     * Aggiorna l'indice diretto in funzione delle parole presenti
     * nella fonte di dati indicata da input. Il metodo opera acquisendo
     * le parole presenti nell' input utilizzando la classe <code>ParserDocumento</code>.
     * Per ciascuna parola letta si verifica se &egrave; gi&agrave; stata incontrata
     * in precedenza. In caso affermativo, si accede all'istanza di <code>Hit</code>
     * associata alla parola aggiornandone la frequenza assoluta. Nel caso la parola
     * sia incontrata per la prima volta, si utilizza la variabile <code>lexicon</code>
     * per ottenere un identificatore univoco per la nuova parola e si alloca, a partire
     * da questo, una nuova istanza della classe <code>Hit</code> da aggiungere
     * a <code>listaHit</code>
     * 
     * @param doc il riferimento al file contenente il documento da indicizzare
     * @param IDDoc l'identificatore del documento
     */
    
    public void indicizzaDoc(Reader doc, int IDDoc) {

    	Dizionario dizionarioHit     = new ArrayOrdinato();
		LinkedList listaHitDocumento = new LinkedList();
	  	int numeroParole = 0;
	  	 	
        Iterator   parser = new ParserDocumento(doc);

        while (parser.hasNext()) {
        
            String parolaCorrente = (String)parser.next();            
            Integer IDParola = lexicon.getIDParola(parolaCorrente, true);
            numeroParole++;
            
            Object x = dizionarioHit.search(IDParola);
            Hit hitCorrente = (Hit) x;

            if (hitCorrente == null) {
                hitCorrente = new Hit(IDParola.intValue(), IDDoc);
                dizionarioHit.insert(hitCorrente, IDParola);
                listaHitDocumento.add(hitCorrente);
            }

            hitCorrente.freq++;
        }
        
        Iterator elencoHit = listaHitDocumento.iterator();
        while (elencoHit.hasNext()) {
            Hit hitCorrente = (Hit) elencoHit.next();
            hitCorrente.freq = hitCorrente.freq / numeroParole;
        }

        listaHit.addAll(listaHitDocumento);
    }

    /**
     * Aggiorna l'indice diretto in funzione delle parole presenti
     * nel documento memorizzato nel file indicato da input. Il metodo opera acquisendo
     * le parole presenti nel file di input utilizzando la classe <code>ParserDocumento</code>.
     * Per ciascuna parola letta si verifica se &egrave; gi&agrave; stata incontrata
     * in precedenza. In caso affermativo, si accede all'istanza di <code>Hit</code>
     * associata alla parola aggiornandone la frequenza assoluta. Nel caso la parola
     * sia incontrata per la prima volta, si utilizza la variabile <code>lexicon</code>
     * per ottenere un identificatore univoco per la nuova parola e si alloca, a partire
     * da questo, una nuova istanza della classe <code>Hit</code> da aggiungere
     * a <code>listaHit</code>
     * 
     * @param fileName il nome del file contenente il documento da indicizzare
     * @param IDDoc l'identificatore del documento
     */
    public void indicizzaDoc(String fileName, int IDDoc) {

    	Dizionario dizionarioHit = new ArrayOrdinato();
        LinkedList listaHitDocumento = new LinkedList();
     	int numeroParole = 0;
        
        Iterator parser = null;
		try {
			parser = new ParserDocumento(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        while (parser.hasNext()) {
        
            String parolaCorrente = (String)parser.next();
            Integer IDParola = lexicon.getIDParola(parolaCorrente, true);
            numeroParole++;
            
            Object x = dizionarioHit.search(IDParola);
            Hit hitCorrente = (Hit) x;

            if (hitCorrente == null) {
                hitCorrente = new Hit(IDParola.intValue(), IDDoc);
                dizionarioHit.insert(hitCorrente, IDParola);
                listaHitDocumento.add(hitCorrente);
            }

            hitCorrente.freq++;
        }

        Iterator elencoHit = listaHitDocumento.iterator();
        while (elencoHit.hasNext()) {
            Hit hitCorrente = (Hit) elencoHit.next();
            hitCorrente.freq = hitCorrente.freq / numeroParole;
        }

        listaHit.addAll(listaHitDocumento);
    }

    /**
     * Restituisce un array contenente tutti gli <code>Hit</code> presenti nell'indice diretto.
     * 
     * 
     * @return l'array degli <code>Hit</code> contenuti nell'indice diretto
     */
    public Hit[] toArray() {
        return (Hit[])listaHit.toArray(new Hit[0]);
    }

    /**
     * Restituisce il riferimento al lexicon utilizzato nella costruzione dell'indice diretto
     * 
     * @return il leixcon utilizzato nella costruzione dell'indice diretto
     */
    public Lexicon getLexicon() {
        return lexicon;
    }

    /**
     * Restituisce la lista degli <code>Hit</code> relativi alle parole di uno stesso documento.
     * 
     * @param IDDoc l'identificatore del documento di cui estrarre la lista delle parole
     * @return la lista degli <code>Hit</code> relativi alle parole del documento <code>IDDoc</code>
     */
    public List listaParole(int IDDoc){
    	return listaParole(toArray(), IDDoc);
    }

    /**
     * Dato un array di <code>Hit</code>, restituisce la lista dei soli <code>Hit</code> appartenenti
     * ad un documento indicato da input. 
     * 
     * @param arrayHit l'array di <code>Hit</code> su cui operare la selezione
     * @param IDDoc l'identificatore del documento in base al quale operare la selezione
     * @return la lista degli <code>Hit</code> di <code>arrayHit</code> relativi alle parole del documento <code>IDDoc</code>
     */
    public static List listaParole(Hit[] arrayHit, int IDDoc){
        int m = 0, indice;
        int a = 0;
        int b = arrayHit.length;
        List listaHit = new LinkedList();

        while (a < b) {
            m = ((a + b) / 2);
            if (arrayHit[m].IDDoc == IDDoc) break;
            if (arrayHit[m].IDDoc < IDDoc)
            	 a = m + 1;
            else b = m - 1;
        }

        if (a >= b) return listaHit;

        for (indice = m; indice >= 0; indice--)
        	if (arrayHit[indice].IDDoc != IDDoc) break;

        for (int i = indice + 1; i < arrayHit.length && arrayHit[i].IDDoc == IDDoc; i++) 
        	listaHit.add(arrayHit[i]);

        return listaHit;
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