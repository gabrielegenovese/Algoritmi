package asdlab.progetto.IndiceInverso;

/* ============================================================================
 *  $RCSfile: Ris.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.6 $
 */

/**
 * La classe <code>Ris</code> incapsula l'identificatore del documento
 * restituito da una query di ricerca su un insieme di documenti.
 * La classe riporta inoltre la somma delle frequenze con le quali le parole chiave
 * utilizzate nella query si presentano nel documento restituito. Infine,
 * la classe <code>Ris</code>  implementa l'interfaccia <code>Comparable</code> per consentire l'ordinamento di array di occorrenze di Ris
 * mediante i metodi della classe {@link asdlab.libreria.Ordinamento.AlgoritmiOrdinamento}. 
 */

public class Ris implements Comparable{

	/**
	 * Identificatore del documento restituito dalla query
	 */
    public int    IDDoc;
    
    /**
     * Somma delle frequenze con cui si presentano nel documento le parole chiave utilizzate dalla query
     */
    public double freq;

    /**
     * Costruttore per la creazione di una nuova istanza di <code>Ris</code>. 
     * 
     * @param IDDoc identificatore del documento
     * @param freq somma delle frequenze con cui le parole chiave si presentano nel documento di indice <code>IDDoc</code>
     */
    public Ris(int IDDoc, double freq) {
        this.IDDoc = IDDoc;
        this.freq  = freq;
    }
    
    /**
     * Determina l'ordinamento relativo esistente tra due istanze di <code>Ris</code>.
     * L'ordine determinato corrisponde all'ordine naturale delle somme delle
     * frequenze <code>freq</code> delle due istanze di <code>Ris</code>.
     * Nel caso le frequenze siano identiche,  si considera l'ordine naturale degli identificatori
     * dei rispettivi documenti.
     * 
     * @param o l'istanza di <code>Ris</code> da confrontare con l'istanza corrente
     * @return -1, se l'istanza corrente precede <code>o</code>. <br>+1, se <code>o</code> precede
     * l'istanza corrente.<br> 0, se le due istanze sono identiche.
     * @throws ClassCastException se l'oggetto <code>o</code> non &egrave; di tipo <code>Ris</code>
     */
    public int compareTo(Object o) {
        if (o instanceof Ris){
            Ris hit = (Ris) o;
            if (freq < hit.freq){
                return -1;
            } else if (freq > hit.freq){
                return 1;
            } else {
                if (IDDoc < hit.IDDoc){
                    return -1;
                } else if (IDDoc > hit.IDDoc){
                    return 1;
                } else {
                    return 0;
                }   
            }
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
