package asdlab.progetto.Ranking;

import asdlab.libreria.Eccezioni.EccezioneInputNonValido;
import asdlab.libreria.Ordinamento.AlgoritmiOrdinamento;
import asdlab.libreria.Selezione.AlgoritmiSelezione;
import asdlab.progetto.IndiceInverso.Ris;

/* ============================================================================
 *  $RCSfile: Ranking.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.10 $
 */

/**
 * La classe <code>Ranking</code> implementa tre diversi metodi per il ranking dei risultati
 * di interrogazioni su gruppi di documenti. L'utilizzo di questi algoritmi consente di raggruppare
 * i risultati di una interrogazione per priorit&agrave; decrescente e di restituire solo un loro determinato
 * sottoinsieme. Gli algoritmi proposti operano sul risultato di operazioni di <code>query</code>
 * eseguite mediante la classe <code>IndiceInverso</code> ed utilizzano come misura di prorit&agrave; la somma delle frequenze
 * con cui le parole chiave compaiono in ciascun documento. Si ritengono perci&ograve; pi&ugrave; significativi i documenti
 * nei quali le parole chiave compaiono pi&ugrave; spesso.
 */

public class Ranking {

	/**
	 * Dato un insieme di documenti <code>docs</code> risultanti da una query, restituisce
	 * i documenti nell'intervallo <code>[i,f]</code>. Il metodo opera eseguendo ripetutamente
	 * l'algoritmo di selezione {@link AlgoritmiSelezione#quickSelect(Comparable[], int)} per determinare il valore di ciascun elemento di rango <code>k</code>,
	 * con <code>k</code> variante da <code>i</code> ad <code>f</code>.
	 * 
	 * @param docs l'insieme dei documenti su cui operare la selezione
	 * @param i l'estremo inferiore dell'intervallo di selezione
	 * @param f l'estremo superiore dell'intervallo di selezione
	 * @return l'array di documenti inclusi nell'intervallo di selezione
	 */
	public static Ris[] filtra1(Ris[] docs, int i, int f) {
		if ((i < 0) || (i >= f) || (f > docs.length))
			throw new EccezioneInputNonValido("L'intervallo di ricerca indicato non e' valido");

		Ris[] risultato = new Ris[f - i];

		for (int k = i; k < f; k++) 
			risultato[k-i] = (Ris) AlgoritmiSelezione.quickSelect(docs,	k);

		return risultato;
	}

	/**
	 * Dato un insieme di documenti <code>docs</code> risultanti da una query, restituisce
	 * i documenti nell'intervallo <code>[i,f]</code>. Il metodo opera eseguendo
	 * l'algoritmo di selezione {@link AlgoritmiSelezione#quickSelect(Comparable[], int)} per determinare
	 * il valore dei due elementi di rango <code>i</code> ed <code>f</code>,
	 * ed utilizzando una scansione lineare sull'intero insieme di documenti
	 * per determinare i documenti che ricadono al loro interno. 
	 * L'insieme risultante viene infine ordinato mediante il metodo
	 * {@link AlgoritmiOrdinamento#quickSort(Comparable[])}.
	 * 
	 * @param docs l'insieme dei documenti su cui operare la selezione
	 * @param i l'estremo inferiore dell'intervallo di selezione
	 * @param f l'estremo superiore dell'intervallo di selezione
	 * @return l'array di documenti inclusi nell'intervallo di selezione
	 */
	public static Ris[] filtra2(Ris[] docs, int i, int f) {
		if ((i < 0) || (i >= f) || (f > docs.length))
			throw new EccezioneInputNonValido("L'intervallo di ricerca indicato non e' valido");

		Ris r1 = (Ris) AlgoritmiSelezione.quickSelect(docs, i+1);
		Ris r2 = (Ris) AlgoritmiSelezione.quickSelect(docs, f);

		Ris[] risultato = new Ris[f - i];

		for (int k = 0, j = 0; k < docs.length; k++) {
			if ((docs[k].compareTo(r1) >= 0)
					&& (docs[k].compareTo(r2) <= 0))
				risultato[j++] = docs[k];
		}

		AlgoritmiOrdinamento.quickSort(risultato);
		return risultato;

	}

	/**
	 * Dato un insieme di documenti <code>docs</code> risultanti da una query, restituisce
	 * i documenti nell'intervallo <code>[i,f]</code>.  Il metodo opera eseguendo
	 * il metodo ricorsivo <code>filtra3Rec</code> per determinare l'insieme dei documenti
	 * che ricadono nell'intervallo <code>[i,f]</code>. L'insieme cos&igrave; ottenuto viene
	 * ordinato mediante il metodo {@link AlgoritmiOrdinamento#quickSort(Comparable[])}.
	 * 
	 * @param docs l'insieme dei documenti su cui operare la selezione
	 * @param i l'estremo inferiore dell'intervallo di selezione
	 * @param f l'estremo superiore dell'intervallo di selezione
	 * @return l'array di documenti inclusi nell'intervallo di selezione
	 */
	public static Ris[] filtra3(Ris[] docs, int i, int f) {
		
		if ((i < 1) || (i >= f) || (f > docs.length))
			throw new EccezioneInputNonValido("L'intervallo di ricerca indicato non e' valido");
		
		Ris[] risultato = filtra3Rec(docs, 0, docs.length - 1, i, f);
		
		AlgoritmiOrdinamento.quickSort(risultato);
		
		return risultato;
	}

	/**
	 * Dato il sottoinsieme di documenti <code>docs</code> dell'intervallo [l,u], restituisce
	 * i documenti presenti nell'intervallo <code>[i,f]</code>. La selezione avviene partizionando
	 * l'array <code>docs</code> in due sottointervalli mediante il metodo {@link AlgoritmiSelezione#partition(Comparable[], int, int, int)}
	 * e verificando se l'intervallo di documenti cercato  <code>[i,f]</code> ricade interamente in uno
	 * dei due sottointervalli. In caso affermativo, la ricerca prosegue ricorsivamente con una ulteriore
	 * esecuzione di <code>filtra3Rec</code>. In caso contrario, l'algoritmo procede utilizzando il metodo
	 * {@link AlgoritmiSelezione#quickSelectRec(Comparable[], int, int, int)} per individuare i due elementi di rango <code>i</code>
	 * ed <code>f</code>, ed utilizzando una scansione lineare sull'intero insieme di documenti
	 * per determinare i documenti che ricadono al loro interno. L'insieme risultante viene restituito
	 * come valore di ritorno. 
	 * 
	 * @param docs l'insieme dei documenti su cui operare la selezione
	 * @param l l'estremo inferiore del sottoinsieme di <code>docs</code> su cui operare
	 * @param u l'estremo superiore del sottoinsieme di <code>docs</code> su cui operare
	 * @param i l'estremo inferiore dell'intervallo di selezione
	 * @param f l'estremo superiore dell'intervallo di selezione
	 * @return l'array di documenti inclusi nell'intervallo di selezione
	 */
	private static Ris[] filtra3Rec(Ris[] docs, int l, int u, int i, int f) {
		int n = u - l - 1;

		int perno = (int) (Math.random() * (n - 1)) + 1 + l;
		
		int m = AlgoritmiSelezione.partition(docs, l, u, perno);
		
		if (i> m + 1) 
			return filtra3Rec(docs, m + 1 , u, i, f);
		else if (f <= m + 1)
			return filtra3Rec(docs, l, m - 1, i, f);
		else {
			Ris r1 = (Ris) AlgoritmiSelezione.quickSelectRec(docs, l, m , i - l);
			Ris r2 = (Ris) AlgoritmiSelezione.quickSelectRec(docs, m, u, f - m - 1);

			Ris[] risultato = new Ris[f - i];

			for (int k = l, j = 0; k <= u; k++) {
				if ((docs[k].compareTo(r1) >= 0)
						&& (docs[k].compareTo(r2) <= 0))
					risultato[j++] = docs[k];
			}
			return risultato;
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
