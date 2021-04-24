package asdlab.libreria.StruttureElem;

import asdlab.libreria.Eccezioni.EccezioneChiaveNonValida;


/* ============================================================================
 *  $RCSfile: ArrayOrdinato.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/08 15:19:33 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.7 $
 */

/**
 * La classe <code>ArrayOrdinato</code> implementa il tipo di dato Dizionario
 * mantenendo le coppie (elemento, chiave) in un array S in modo
 * tale che coppie consecutive abbiano chiavi non decrescenti,
 * cio&egrave; che l'array sia ordinato in base alle chiavi.
 */
public class ArrayOrdinato implements Dizionario {

	/** 
	 * Mantiene tutte le coppie (elemento, chiave)
	 * attualmente presenti nel dizionario. Gli elementi in <code>S</code>
	 * sono ordinati rispetto al proprio campo chiave. La taglia di
	 * <code>S</code> &egrave; inizialmente pari a zero
	 */
    private Coppia[] S= new Coppia[0];

    /**
     * Classe definita localmente alla classe <code>ArrayOrdinato</code>
     * per il mantenimento delle coppie (elemento, chiave)
     *
     */
    private class Coppia {
        public Object elem;
        public Comparable chiave;
        public Coppia(Object e, Comparable k) {
            this.elem = e;
            this.chiave = k;
        }
    }

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo O(n)</font>).
	 * L'inserimento viene realizzato incrementando
	 * di uno la taglia dell'array e collocando la nuova
	 * coppia in modo tale da rispettare la relazione
	 * di ordinamento.
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */
    public void insert(Object e, Comparable k) {
        int i, j;
        Coppia[] temp = new Coppia[S.length + 1];
        for (i = 0; i < S.length; i++) temp[i] = S[i];
        S = temp;
        for (i = 0; i < S.length - 1; i++)
            if (k.compareTo(S[i].chiave) <= 0) break;
        for (j = S.length - 1; j > i; j--)
            S[j] = S[j - 1];
        S[i] = new Coppia(e, k);
    }

	/**
	 * Rimuove dal dizionario l'elemento con chiave <code>k</code> (<font color=red>Tempo O(n)</font>).
	 * In caso di duplicati, l'elemento cancellato
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca dell'elemento da cancellare avviene
	 * mediante una scansione lineare dell'array.
	 * 
	 * @param k chiave dell'elemento da cancellare
	 * @throws EccezioneChiaveNonValida se la chiave ricercata non &egrave; presente nel dizionario
	 */
    public void delete(Comparable k) {
        int i, j;
        for (i = 0; i < S.length; i++)
            if (k.equals(S[i].chiave)) break;
        if (i == S.length)
            throw new EccezioneChiaveNonValida();
        for (j = i; j < S.length - 1; j++)
            S[j] = S[j + 1];
        Coppia[] temp = new Coppia[S.length - 1];
        for (i = 0; i < temp.length; i++) temp[i] = S[i];
        S = temp;
    }

	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code> (<font color=red>Tempo O(log(n)</font>).
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca dell'elemento con chiave <code>k</code> 
	 * avviene utilizzando l'algoritmo di ricerca binaria.
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
    public Object search(Comparable k) {
        int i = 0, j = S.length;
        while (j - i > 0) {
           int m = (i + j) / 2;
           if (k.equals(S[m].chiave)) return S[m].elem;
           if (k.compareTo(S[m].chiave) < 0) j = m;
           else i = m + 1;
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