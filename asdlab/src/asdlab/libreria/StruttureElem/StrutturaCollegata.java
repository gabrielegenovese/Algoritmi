package asdlab.libreria.StruttureElem;

import asdlab.libreria.Eccezioni.EccezioneChiaveNonValida;

/* ============================================================================
 *  $RCSfile: StrutturaCollegata.java,v $
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
 * La classe <code>StrutturaCollegata</code> implementa il tipo di dato
 * Dizionario mediante una collezione di n record contenenti
 * ciascuno una quadrupla  (elem, chiave, next, prev),
 * dove next e prev sono puntatori al successivo e precedente
 * record nella collezione. 
 *
 */
public class StrutturaCollegata implements Dizionario {

	/**
	 * Punta alla testa della lista di
	 * quadruple(elem, chiave, next, prev) presenti nel dizionario
	 */
	private Record list = null;

	/** 
	 * Classe definita localmente ala classe <code>StrutturaCollegata</code>
	 * per il mantenimento dei record contenenti
	 * le quadruple (elem, chiave, next, prev)
	 *
	 */
    private final class Record {
    	/**
    	 * Elemento da conservare nel record
    	 */
        public Object     elem;
        /**
         * Chiave associata all'elemento da conservare nel record
         */
        public Comparable chiave;
        
        /**
         * Puntatore al prossimo record nella struttura collegata
         */
        public Record     next;
        
        /**
         * Puntatore al record precedente nella struttura collegata
         */
        public Record     prev;
        /**
         * Costruttore per l'allocazione di un nuovo record 
         * 
         * @param e l'elemento da conservare nel record
         * @param k la chiave associata all'elemento da conservare nel record
         */
        public Record(Object e, Comparable k) {
            elem = e;
            chiave = k;
            next = prev = null;
        }
    }

	/**
	 * Aggiunge al dizionario la coppia <code>(e,k)</code> (<font color=red>Tempo O(1)</font>).
	 * L'inserimento avviene creando un nuovo record ed inserendolo
	 * in testa alla lista.  
	 * 
	 * @param e elemento da mantenere nel dizionario
	 * @param k chiave associata all'elemento
	 */
    public void insert(Object e, Comparable k) {
        Record p = new Record(e, k);
        if (list == null)
            list = p.prev = p.next = p;
        else {
            p.next = list.next;
            list.next.prev = p;
            list.next = p;
            p.prev = list;
        }
    }

	/**
	 * Rimuove dal dizionario il record relativo
	 * all'elemento con chiave <code>k</code> (<font color=red>Tempo O(n)</font>).
	 * In caso di duplicati, l'elemento cancellato
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca dell'elemento da cancellare avviene
	 * mediante una scansione lineare della lista.
	 * 
	 * @param k chiave dell'elemento da cancellare
	 * @throws EccezioneChiaveNonValida se la chiave ricercata non &egrave; presente nel dizionario
	 */
    public void delete(Comparable k) {
        Record p = null;
        if (list != null)
            for (p = list.next; ; p = p.next){
                if (p.chiave.equals(k)) break;
                if (p == list) { p = null; break; }
            }
        if (p == null)
            throw new EccezioneChiaveNonValida();
        if (p.next == p) list = null; 
        else {
            if (list == p) list = p.next;
            p.next.prev = p.prev;
            p.prev.next = p.next;
        }
    }
	/**
	 * Restituisce l'elemento <code>e</code> con chiave <code>k</code> (<font color=red>Tempo O(n)</font>).
	 * In caso di duplicati, l'elemento restituito
	 * &egrave; scelto arbitrariamente tra quelli con chiave <code>k</code>.
	 * La ricerca dell'elemento con chiave <code>k</code> 
	 * avviene mediante una scansione lineare della lista.
	 * 
	 * @param k chiave dell'elemento da ricercare
	 * @return elemento di chiave <code>k</code>, <code>null</code> se assente
	 */
    public Object search(Comparable k) {
        if (list == null) return null;
        for (Record p = list.next; ; p = p.next){
            if (p.chiave.equals(k)) return p.elem;
            if (p == list) return null;
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