package asdlab.libreria.Alberi;

/* ============================================================================
 *  $RCSfile: NodoBinPF.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/23 10:22:56 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.10 $
 */


/**
 * Classe usata per l'implementazione di alberi utilizzanti una rappresentazione
 * di tipo puntatori a figli. Estende la classe <code>Nodo</code>
 * con le variabili di istanza per mantenere i puntatori al padre, 
 * al figlio sinistro e al figlio destro.
 */

public class NodoBinPF extends Nodo {

	/**
	 * Il padre del nodo corrente. <code>null</code>, se non vi &egrave; un padre
	 */
	public NodoBinPF padre;

	/**
	 * Il figlio sinistro del nodo corrente. <code>null</code>, se non vi &egrave; un figlio sinistro
	 */
	public NodoBinPF sin;

	/**
	 * Il figlio destro del nodo corrente. <code>null</code>, se non vi &egrave; un figlio destro
	 */
	public NodoBinPF des;
	
	/**
	 * L'albero cui il nodo appartiene.
	 */
	public AlberoBin albero;	
	
	/**
	 * Costruttore per l'istanziazione di nuovi nodi.
	 * 
	 * @param info il contenuto informativo da associare al nuovo nodo
	 */

	public NodoBinPF(Object info) {super(info);}
	
	/**
	 * Restituisce il riferimento alla struttura dati contenente il nodo (<font color=red>Tempo O(profondit&agrave;
	 * del nodo nell'albero che lo contiene)</font>).
	 * 
	 * @return il riferimento all'albero contenente il nodo
	 */
	public AlberoBin contenitore(){
		NodoBinPF n = this;
		while (n.padre != null) n = n.padre;
		return n.albero;
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