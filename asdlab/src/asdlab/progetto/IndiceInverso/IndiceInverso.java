package asdlab.progetto.IndiceInverso;

import java.util.*;

import asdlab.libreria.Ordinamento.AlgoritmiOrdinamento;
import asdlab.progetto.IndiceDiretto.*;
import asdlab.progetto.Lexicon.*;

/* ============================================================================
 *  $RCSfile: IndiceInverso.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.17 $
 */

/**
 * La classe <code>IndiceInverso</code> mantiene informazioni, per ogni parola
 * del vocabolario, circa i documenti in cui essa compare e la frequenza
 * con cui vi compare. Tali informazioni vengono mantenute in un array di
 * istanze della classe <code>Hit</code>, <code>arrayHit</code>, corrispondenti
 * alle occorrenze di ciascuna parola in ciascuno dei documenti in cui essa si presenta.
 * Gli <code>Hit</code> presenti in <code>arrayHit</code> sono ordinati utilizzando
 * l'identificatore della parola cui fanno riferimento come chiave nell'ordinamento.
 * Questo implica che tutti i documenti che contengono una certa parola appariranno
 * raggruppati nell'indice inverso. Questo tipo di organizzazione semplifica 
 * la realizzazione dell'operazione di interrogazione, implementata dal metodo
 * <code>query</code>, con la quale &egrave; possibile ottenere l'elenco
 * dei documenti al cui interno si presenta un certo insieme di parole chiave
 * indicate da input. 
 */

public class IndiceInverso {
	
    /**
     * Istanza di <code>Lexicon</code> utilizzata per associare ad ogni parola un identificatore numerico univoco
     */
	protected Lexicon lexicon;

	/**
	 * Array di <code>Hit</code> ordinato secondo l'identificatore delle parole 
	 */
	protected Hit[] arrayHit;
    
	/**
	 * Costruttore per la creazione di nuove istanze della classe <code>IndiceInverso</code>. La classe
	 * <code>IndiceInverso</code> viene allocata a partire da una istanza di <code>IndiceDiretto</code>
	 * precedentemente allocata e popolata, di cui utilizza il lexicon ed il contenuto. 
	 *  
	 * @param indiceDiretto istanza di indice diretto da utilizzarsi per la creazione dell'indice inverso
	 */
    public IndiceInverso(IndiceDiretto indiceDiretto) {
        this.lexicon = indiceDiretto.lexicon;
        arrayHit = indiceDiretto.toArray();
        AlgoritmiOrdinamento.quickSort(arrayHit);
    }
    
    /**
     * Restituisce un array contenente gli <code>Hit</code> presenti nell'indice inverso.
     * 
     * @return l'array ordinato degli <code>Hit</code> presenti nell'indice inverso
     */
    public Hit[] toArray() { return arrayHit; }
    
    /**
     * Restituisce il riferimento al lexicon utilizzato nella costruzione dell'indice.
     * 
     * @return il riferimento al lexicon
     */
    public Lexicon getLexicon(){ return lexicon;}

    /**
     * Restituisce l'elenco dei documenti al cui interno si presentano tutte le
     * parole indicate da input. L'elenco viene determinato costruendo gli
     * elenchi per le singole parole, mediante il metodo <code>listaDocumenti</code>,
     * ed intersecando i diversi elenchi mediante il metodo <code>intersecaListe</code>.
     * 
     * @param parole l'array delle parole che si intendono ricercare
     * @return l'elenco dei documenti contenenti le parole ricercate
     */
    public Ris[] query(String[] parole) {

        if (parole.length == 0) return new Ris[0];
        
        List listaDocumenti1 = listaDocumenti(parole[0]);

        for (int i = 1; i < parole.length; i++) {
        	List listaDocumenti2 = listaDocumenti(parole[i]);
            listaDocumenti1 = intersercaListe(listaDocumenti1,
                    listaDocumenti2);
        }

        return (Ris[])listaDocumenti1.toArray(new Ris[0]);
    }

    /**
     * Restituisce l'elenco dei documenti in cui si presenta una parola. 
     * L'elenco viene determinato una procedura di ricerca binaria per 
     * determinare l'indice di uno qualsiasi degli (eventuali) <code>Hit</code>
     * corrispondenti alla parola ricercata. Una volta individuato l'indice, si opera
     * una ricerca sequenza alla sua sinistra ed alla sua destra, sino a racchiudere tutti
     * gli <code>Hit</code> facenti riferimento alla stessa parola. I risultati
     * sono incapsulati in istanze della classe <code>Ris</code> ed aggiunti
     * ad una lista restituita come valore di ritorno.
     * 
     * @param parola la parola in base alla quale operare la ricerca
     * @return l'elenco dei documenti contenenti <code>parola</code>
     */
    public List listaDocumenti(String parola){
        int m = 0, indice;
        int a = 0;
        int b = arrayHit.length;
        List listaHit = new LinkedList();
        Integer IDParola = lexicon.getIDParola(parola, false);

        if (IDParola == null) return new LinkedList();
        
        while (a < b) {
            m = ((a + b) / 2);
            if (arrayHit[m].IDParola == IDParola) break;
            if (arrayHit[m].IDParola < IDParola)
            	 a = m + 1;
            else b = m - 1;
        }

        if (a >= b) return listaHit;

        for (indice = m; indice >= 0; indice--)
        	if (arrayHit[indice].IDParola != IDParola) break;

        for (int i = indice + 1; i < arrayHit.length && arrayHit[i].IDParola == IDParola; i++) 
        	listaHit.add(new Ris(arrayHit[i].IDDoc, arrayHit[i].freq));

        return listaHit;
    }

 
    /**
     * Interseca due liste di <code>Ris</code> restituendo una terza lista contenente i soli
     * elementi in comune. 
     * 
     * @param lista1 la prima lista da intersecare
     * @param lista2 la seconda lista da intersecare
     * @return la lista risultante dall'intersezione di <code>lista1</code> e <code>lista2</code>
     */
    protected List intersercaListe(List lista1, List lista2) {

        if ((lista1.size() == 0) || (lista2.size() == 0)) {
            return new LinkedList();
        }

        Iterator elencoRis1 = lista1.iterator();
        Iterator elencoRis2 = lista2.iterator();

        List listaRitorno = new LinkedList();

        Ris ris1 = (Ris) elencoRis1.next();
        Ris ris2 = (Ris) elencoRis2.next();

        while (elencoRis1.hasNext() && elencoRis2.hasNext()) {
            if (ris1.IDDoc < ris2.IDDoc) {
                ris1 = (Ris) elencoRis1.next();
            } else if (ris1.IDDoc > ris2.IDDoc) {
                ris2 = (Ris) elencoRis2.next();
            } else {
                listaRitorno.add(new Ris(ris1.IDDoc, ris1.freq + ris2.freq));

                ris1 = (Ris) elencoRis1.next();
                ris2 = (Ris) elencoRis2.next();
            }
        }

        if (ris1.IDDoc == ris2.IDDoc) {
            listaRitorno.add(new Ris(ris1.IDDoc, ris1.freq + ris2.freq));
        }

        while (elencoRis1.hasNext()) {
            ris1 = (Ris) elencoRis1.next();
            if (ris1.IDDoc == ris2.IDDoc) {
                listaRitorno.add(new Ris(ris1.IDDoc, ris1.freq + ris2.freq));
            }
        }

        while (elencoRis2.hasNext()) {
            ris2 = (Ris) elencoRis2.next();
            if (ris1.IDDoc == ris2.IDDoc) {
                listaRitorno.add(new Ris(ris1.IDDoc, ris1.freq + ris2.freq));
            }
        }

        return listaRitorno;
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