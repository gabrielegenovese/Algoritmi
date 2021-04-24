package asdlab.libreria.AlberiRicerca;

import asdlab.libreria.Alberi.*;
/* ============================================================================
 *  $RCSfile: AlberoBRR.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/23 17:27:32 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.13 $
 */

/**
 * La classe <code>AlberoBRR</code> deriva dalla classe <code>AlberoBR</code>
 * e vi aggiunge i metodi protetti <code>ruotaSin</code> e <code>ruotaDes</code>,
 * che implementano le operazioni di rotazione a sinistra e rotazione a destra
 * di un nodo. La classe non &egrave; istanziabile, ma &egrave;
 * pensata per essere derivata da classi che implementano strategie di ribilanciamento
 * mediante rotazioni quali <code>AlberoAVL</code>.
 *
 */
public abstract class AlberoBRR extends AlberoBR {

	/**
	 * Opera una rotazione a destra sul nodo di input <code>u</code> (<font color=red>Tempo O(1)</font>).
	 * L'operazione viene realizzata decomponendo il sottoalbero
	 * originario in sottoalberi, mediante il metodo <code>pota</code>,
	 * e ricomponendo opportunamente gli alberi mediante i metodi di
	 * <code>innestaSin</code> e <code>innestaDes</code>.
	 * 
	 * @param u il nodo su cui operare la rotazione a destra
	 */
	protected void ruotaDes(Nodo u) {
		scambiaInfo(u, alb.sin(u));
		AlberoBin tv = alb.pota(alb.sin(u));
		AlberoBin t3 = alb.pota(alb.des(u));
		AlberoBin t1 = tv.pota(tv.sin(tv.radice()));
		AlberoBin t2 = tv.pota(tv.des(tv.radice()));
		tv.innestaSin(tv.radice(), t2);
		tv.innestaDes(tv.radice(), t3);
		alb.innestaSin(u, t1);
		alb.innestaDes(u, tv);
	}

	/**
	 * Opera una rotazione a sinistra sul nodo di input <code>u</code> (<font color=red>Tempo O(1)</font>).
	 * L'operazione viene realizzata decomponendo il sottoalbero
	 * originario in sottoalberi, mediante il metodo <code>pota</code>,
	 * e ricomponendo opportunamente gli alberi mediante i metodi di
	 * <code>innestaSin</code> e <code>innestaDes</code>.
	 * 
	 * @param u il nodo su cui operare la rotazione a sinistra
	 */
	protected void ruotaSin(Nodo u) {
		scambiaInfo(u, alb.des(u));
		AlberoBin t1 = alb.pota(alb.sin(u));
		AlberoBin tv = alb.pota(alb.des(u));
		AlberoBin t2 = tv.pota(tv.sin(tv.radice()));
		AlberoBin t3 = tv.pota(tv.des(tv.radice()));
		tv.innestaSin(tv.radice(), t1);
		tv.innestaDes(tv.radice(), t2);
		alb.innestaDes(u, t3);
		alb.innestaSin(u, tv);
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