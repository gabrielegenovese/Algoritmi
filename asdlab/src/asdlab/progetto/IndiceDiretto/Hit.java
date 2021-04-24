package asdlab.progetto.IndiceDiretto;


/* ============================================================================
 *  $RCSfile: Hit.java,v $
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
 * La classe <code>Hit</code> viene utilizzata per rappresentare le occorrenze di una parola
 * in un documento. Per ciascuna parola si memorizzano l'identificatore della parola,
 * l'identificatore del documento cui appartiene e la frequenza con la quale
 * la parola compare nel documento. La classe <code>Hit</code>  implementa l'interfaccia
 * <code>Comparable</code> per consentire l'ordinamento di array di occorrenze di Hit
 * mediante i metodi della classe {@link asdlab.libreria.Ordinamento.AlgoritmiOrdinamento}. 
 */
public class Hit implements Comparable {

	/**
	 * L'identificatore della parola
	 */
	 public int IDParola;
	
	 /**
	  * La frequenza con cui la parola compare nel documento
	  */
	 public float freq;
	   
	 /**
	  * L'identificatore del documento cui la parola appartiene
	  */
	 public int IDDoc;

	/**
	 * Costruttore per l'allocazione di una nuova istanza di <code>Hit</code>.
	 * 
	 * @param IDParola l'identitificatore della parola
	 * @param IDDoc l'identificatore del documento cui la parola appartiene
	 */
    public Hit(int IDParola, int IDDoc) {
        this.IDParola = IDParola;
        this.IDDoc    = IDDoc;
        freq = 0;
    }


    /**
     * Determina l'ordinamento relativo esistente tra due istanze di <code>Hit</code>.
     * L'ordine determinato corrisponde all'ordine naturale degli identificatori 
     * delle parole incapsulate nelle due istanze. Nel caso gli identificatori
     * delle parole siano identici, si considera l'ordine naturale degli identificatori
     * dei rispettivi documenti.
     * 
     * @param o l'istanza di <code>Hit</code> da confrontare con l'istanza corrente
     * @return -1, se l'istanza corrente precede <code>o</code>. <br>+1, se <code>o</code> precede
     * l'istanza corrente.<br> 0, se le due istanze sono identiche.
     * @throws ClassCastException se l'oggetto <code>o</code> non &egrave; di tipo <code>Hit</code>
     */
    public int compareTo(Object o) {
        if (o instanceof Hit){
            Hit hit = (Hit) o;
            if (IDParola == hit.IDParola){
                if (IDDoc < hit.IDDoc) return -1;
                if (IDDoc > hit.IDDoc) return 1;
                return 0;
            }

            if (IDParola < hit.IDParola) return -1;
            if (IDParola > hit.IDParola) return 1;
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