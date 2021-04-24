package asdlab.progetto.Lexicon;

import asdlab.libreria.StruttureElem.ArrayOrdinato;
import asdlab.libreria.StruttureElem.Dizionario;

/* ============================================================================
 *  $RCSfile: Lexicon.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */


/**
 * La classe <code>Lexicon</code> associa un identificatore numerico distinto
 * a parole distinte. La corrispondenza tra parole ed identificatori viene
 * esplicitamente mantenute mediante il ricorso a due istanze di <code>Dizionario</code>.
 * La prima, <code>diz</code>, indica per ciascuna parola, il corrispondente identificatore.
 * La seconda, <code>dizInv</code>, ha il ruolo opposto. L'indicizzazione di una nuova
 * parola avviene verificando dapprima che questa non sia presente in <code>diz</code>
 * dopodich&eacute; vi si assegna un identificatore univoco ottenuto incrementando una opportuna
 * variabile contatore.
 *
 */
public class Lexicon {

	/**
	 * Dizionario utilizzato per mantenere la corrispondenza (parola,identificatore)
	 */
    private Dizionario diz;
    
    /**
     * Dizionario utilizzato per mantenere la corrispondenza (identificatore,parola)
     */
    private Dizionario dizInv;
    
    /**
     * Variabile contatore utilizzata per assegnare a ciascuna nuova parola un identificatore univoco
     */
    private int IDCorr;

    /**
     * Costruttore per la creazione di una nuova istanza di <code>Lexicon</code>. I dizionari <code>diz</code>
     * e <code>dizInv</code> sono allocati come istanze della classe <code>ArrayOrdinato</code>.
     *
     */
    public Lexicon() {
        diz    = new ArrayOrdinato();
        dizInv = new ArrayOrdinato();
        IDCorr = 0;
    }

    /**
     * Restituisce l'identificatore associato ad una parola <code>par</code>. Nel caso <code>par</code>
     * sia assente e l'argomento <code>ins</code> sia posto a <code>true</code>,
     * <code> par</code> viene inserita nel lexicon ed il suo identificatore viene restituito.
     * 
     * @param par la parola da ricercare
     * @param ins se <code>true</code>, abilita l'inserimento di <code>par</code> nel lexicon nel caso questi sia assente
     * @return l'identificatore di <code>par</code>. <code>null</code>, se par non &egrave; presente nel lexicon
     */
    public Integer getIDParola(String par, boolean ins){
        Integer ID = (Integer)diz.search(par);
        if (ins == false) return ID;
        if (ID == null) {
            ID = new Integer(IDCorr);
            IDCorr++;
            diz.insert(ID, par);
            dizInv.insert(par, ID);
        }
        return ID;
    }

    /**
     * Restituisce la parola associata ad un identificatore.
     * 
     * @param IDParola l'identificatore di cui cercare la corrispondente parola
     * @return la parola corrispondente a <code>IDParola</code>
     */
    public String getParolaID(Integer IDParola) {
        return (String)dizInv.search(IDParola);
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