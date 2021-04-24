package asdlab.libreria.Selezione;

import asdlab.libreria.Ordinamento.AlgoritmiOrdinamento;

/* ============================================================================
 *  $RCSfile: AlgoritmiSelezione.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/08 15:19:33 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.14 $
 */

/**
 * La classe <code>AlgoritmiSelezione</code> contiene l'implementazione di numerosi
 * algoritmi per la risoluzione di problemi di selezione e statistiche d'ordine.<br>
 * Tutte le implementazioni proposte assumono che l'input sia costituito da un array i cui
 * elementi implementano l'interfaccia standard <code>Comparable</code>. 
 */

public class AlgoritmiSelezione {

	/**
	 * Restituisce l'elemento di chiave minima tra quelli appartenenti
	 * alla sequenza di input (<font color=red>Tempo O(n)</font>). L'elemento minimo viene determinato 
	 * in base ad una scansione lineare dell'array di input.
	 * 
	 * @param A l'array di cui ricercare l'elemento minimo
	 * @return l'elemento di <code>A</code> con chiave minima
	 */
	public static Comparable minimo(Comparable[] A) {
		Comparable min = A[0];
		for (int i = 1; i < A.length; i++)
			if (A[i].compareTo(min) < 0) min = A[i];
		return min;
	}

	/**
	 * Restituisce il secondo elemento di chiave minima tra quelli appartenenti
	 * alla sequenza di input (<font color=red>Tempo O(n)</font>). L'elemento minimo viene determinato 
	 * in base ad una scansione lineare dell'array di input.
	 * 
	 * @param A l'array di cui ricercare il secondo elemento minimo 
	 * @return il secondo elemento di <code>A</code> con chiave minima
	 */
	public static Comparable secondoMinimo(Comparable[] A) {
		Comparable primoMin = A[0].compareTo(A[1]) < 0 ? A[0] : A[1];
		Comparable secondoMin = A[0].compareTo(A[1]) < 0 ? A[1] : A[0];

		for (int i = 2; i < A.length; i++)
			if (A[i].compareTo(secondoMin) < 0) {
				if (A[i].compareTo(primoMin) < 0) {
					secondoMin = primoMin;
					primoMin = A[i];
				} else
					secondoMin = A[i];
			}
		return secondoMin;
	}


	/**
	 * Seleziona l'elemento di ordine k dall'array di input mediante
	 * l'uso di un heap (<font color=red>Tempo O(n + k&middot;log(n))</font>). La selezione viene effettuata trasformando
	 * l'array di input in heap, attraverso il metodo <code>heapify</code>,
	 * ed eseguendo k operazioni di estrazione dell'elemento
	 * con chiave mimina (<code>deleteMin</code>). 
	 * 
	 * @param A l'array di cui ricercare l'elemento di ordine <code>k</code> 
	 * @param k l'ordine dell'elemento che si intende selezionare
	 * @return l'elemento di <code>A</code> di ordine <code>k</code>
	 */
	public static Comparable heapSelect(Comparable[] A, int k) {
		 heapify( A, A.length-1, 1);
		 for (int i=1; i < k; i++) deleteMin(A, A.length-i);
		 return findMin(A);
	}
	
	/**
	 * Ripristina la propriet&agrave; di ordinamento di un heap rispetto
	 * ad un nodo di indice <code>i</code>. L'operazione avviene confrontando ricorsivamente
	 * il nodo di indice i con i suoi figli ed operando uno scambio di nodi
	 * ogni qual volta la propriet&agrave; di ordinamento non sia verificata. 
	 *  
	 * @param S l'array utilizzato per rappresentare l'heap
	 * @param c il numero di elementi dell'heap
	 * @param i l'indice dell'elemento su cui verificare la propriet&agrave; di ordinamento dell'heap
	 */	
	private static void fixHeap(Comparable S[], int c, int i) {
		int min = 2 * i;
		if (2 * i > c)
			return;
		if (2 * i + 1 <= c && S[2 * i].compareTo(S[2 * i + 1]) > 0)
			min = 2 * i + 1;
		if (S[i].compareTo(S[min]) > 0) {
			Comparable temp = S[min];
			S[min] = S[i];
			S[i] = temp;
			fixHeap(S, c, min);
		}
	}

	/**
	 * Restituisce l'elemento di chiave minima attualmente custodito nell'heap. 
	 * 
	 * @param S l'array usato per rappresentare l'heap
	 * @return l'elemento di chiave minima custodito nell'heap
	 * @throws ArrayOutOfBoundsException se l'heap &egrave; vuoto
	 */
	private static Comparable findMin(Comparable S[]) {
		return S[1];
	}

	/**
	 * Trasforma in heap un array di elementi a partire da un elemento
	 * di indice <code>i</code>. La trasformazione avviene invocando ricorsivamente il metodo
	 * <code>heapify</code> per la trasformazione dei figli del nodo di indice <code>i</code>,
	 * ed il metodo <code>fixHeap</code> sul nodo di indice <code>i</code> per il ripristino della propriet&agrave;
	 * di ordinamento dell'heap.
	 *  
	 * @param S l'array da trasformare in heap
	 * @param n il numero di elementi dell'array
	 * @param i l'indice dell'elemento dell'array da cui innescare la trasformazione
	 */	
	private static void heapify(Comparable S[], int n, int i) {
		if (i > n)
			return;
		heapify(S, n, 2 * i);
		heapify(S, n, 2 * i + 1);
		fixHeap(S, n, i);
	}

	/**
	 * Rimuove l'elemento con chiave minima attualmente presente nell'heap. 
	 * L'operazione viene realizzata sovrascrivendo l'elemento di indice 1 (l'elemento
	 * di chiave minima) con l'elemento di indice <code>c</code> ed invocando il metodo <code>fixHeap</code>
	 * per ripristinare la propriet&agrave; di ordinamento dell'heap.
	 * 
	 * @param S l'array usato per rappresentare l'heap
	 * @param c il numero di elementi dell'heap
	 */	
	private static void deleteMin(Comparable S[], int c) {
		if (c <= 0)
			return;
		S[1] = S[c];
		c--;
		fixHeap(S, c, 1);
}


	/**
  	 * Seleziona l'elemento di ordine k dall'array di input mediante
	 * un algoritmo di selezione basato su partizionamento (<font color=red>Tempo O(n&sup2;), tempo atteso O(n)</font>). La selezione
	 * avviene invocando il metodo ricorsivo <code>quickSelectRec</code>.
	 * 
	 * @param A l'array di cui ricercare l'elemento di ordine <code>k</code> 
	 * @param k l'ordine dell'elemento che si intende selezionare
	 * @return l'elemento di <code>A</code> di ordine <code>k</code>
	 */
	public static Comparable quickSelect(Comparable A[], int k) {
		return quickSelectRec(A, 0, A.length-1, k); 
	}
	
	
	/**
	 * Seleziona l'elemento di ordine k di una sottosequenza di
	 * elementi dell'array di input. La selezione avviene innanzitutto
	 * partizionando, mediante il metodo <code>partition</code>,
	 * la sottosequenza di input in tre parti. Dopodich&eacute;, valuta
	 * quale delle tre parti contiene l'elemento di ordine k ed,
	 * eventualmente, opera una ulteriore chiamata ricorsiva
	 * del metodo <code>quickSelectRec</code>.
	 * 
	 * @param A l'array di cui ricercare l'elemento di ordine <code>k</code> 
	 * @param i l'indice dell'estremo inferiore della sequenza su cui operare la selezione
	 * @param f l'indice dell'estremo superiore della sequenza su cui operare la selezione
	 * @param k l'ordine dell'elemento che si intende selezionare
	 * @return l'elemento di <code>A</code> di ordine <code>k</code>
	 */
	public static Comparable quickSelectRec(Comparable[] A, int i, int f, int k) {
		int m = partition(A, i, f); 
		int l = m-i;
		if (k <= l)
		      return quickSelectRec(A, i, m-1, k);
		else if (k > l+1)
		      return quickSelectRec(A, m+1, f, k-l-1);
		else return A[m];
	}

	/**
	 * Partiziona <em>in loco</em> gli elementi di una sequenza di input in due sottosequenze
	 * nelle quali tutti gli elementi della prima sottosequenza sono pi&ugrave; piccoli di
	 * tutti gli elementi della seconda sottosequenza. L'effettivo partizionamento
	 * viene demandato al secondo metodo <code>partition</code> cui viene fornito
	 * anche l'indice dell'elemento che funger&agrave; da perno. L'elemento perno viene scelto
	 * come elemento casuale all'interno della sequenza da ordinare.
	 * 
	 * @param A l'array contenente la sottosequenza da ordinare
	 * @param i l'indice dell'estremo inferiore della sottosequenza da ordinare
	 * @param f l'indice dell'estremo superiore della sottosequenza da ordinare
	 * @return la posizione dell'elemento perno nella sequenza partizionata
	 */
	
	private static int partition(Comparable A[], int i, int f) {
		int pos = i + (int) Math.floor((f - i + 1) * Math.random());
		return partition(A, i, f, pos);
	}

	/**
	 * Partiziona <em>in loco</em> gli elementi di una sequenza di input in due sottosequenze
	 * nelle quali tutti gli elementi della prima sottosequenza sono pi&ugrave; piccoli di
	 * tutti gli elementi della seconda sottosequenza. L'indice dell'elemento
	 * perno viene indicato da input.
	 *  
	 * @param A l'array contenente la sottosequenza da ordinare
	 * @param i l'indice dell'estremo inferiore della sottosequenza da ordinare
	 * @param f l'indice dell'estremo superiore della sottosequenza da ordinare
	 * @param pos l'indice dell'elemento perno
	 * @return la posizione dell'elemento perno nella sequenza partizionata
	 */	
	public static int partition(Comparable[] A, int i, int f,  int pos) {
		int inf = i, sup = f + 1;
		
		Comparable temp, x = A[pos];
		A[pos] = A[i];
		A[i] = x;

		while (true) {
			do {
				inf++;
			} while (inf <= f && A[inf].compareTo(x) <= 0);

			do {
				sup--;
			} while (A[sup].compareTo(x) > 0);
			
			if (inf < sup) {
				temp = A[inf];
				A[inf] = A[sup];
				A[sup] = temp;
			} else {
				break;
			}
		}

		temp = A[i];
		A[i] = A[sup];
		A[sup] = temp;
		return sup;
	}
	
	/**
	 * Restituisce l'elemento mediano di una sottosequenza di lunghezza 3
	 * della sequenza di input utilizzando due confronti. 
	 * 
	 * @param A la sequenza contenente la sottosequenza di cui si vuole conoscere l'elemento mediano
	 * @param i l'indice del primo elemento della sottosequenza di 3 elementi 
	 * @return l'elemento mediano
	 */
	private static Comparable med3(Comparable[] A, int i) {
		if (A[i].compareTo(A[i + 1]) < 0) {
			if (A[i + 1].compareTo(A[i + 2]) < 0) {
				return A[i + 1];
			}
			return A[i + 2];
		} else if (A[i].compareTo(A[i + 2]) < 0) {
			return A[i];
		} else {
			return A[i + 2];
		}
	}

	/**
	 * Restituisce l'elemento mediano di una sottosequenza di lunghezza 4
	 * della sequenza di input utilizzando al pi&ugrave; tre confronti. 
	 * 
	 * @param A la sequenza contenente la sottosequenza di cui si vuole conoscere l'elemento mediano
	 * @param i l'indice del primo elemento della sottosequenza di 4 elementi 
	 * @return l'elemento mediano
	 */
	private static Comparable med4(Comparable[] A, int i) {
		if (A[i].compareTo(A[i + 1]) < 0) {
			if (A[i + 1].compareTo(A[i + 2]) < 0) {
				return A[1];
			} else if (A[i].compareTo(A[i + 2]) < 0) {
				return A[i + 2];
			} else {
				return A[i];
			}
		}

		if (A[i].compareTo(A[i + 2]) < 0) {
			return A[i];
		} else if (A[i + 1].compareTo(A[i + 2]) < 0) {
			return A[i + 2];
		} else {
			return A[i + 1];
		}
	}

	/**
	 * Restituisce l'elemento mediano di una sottosequenza di lunghezza 5
	 * della sequenza di input utilizzando al pi&ugrave; sei confronti. 
	 * 
	 * @param A la sequenza contenente la sottosequenza di cui si vuole conoscere l'elemento mediano
	 * @param i l'indice del primo elemento della sottosequenza di 5 elementi 
	 * @return l'elemento mediano
	 */	
	private static Comparable med5(Comparable[] A, int i) {
		 if (A[i].compareTo(A[i+1]) > 0) swap(A,i,i+1);
		 if (A[i+2].compareTo(A[i+3]) > 0) swap(A,i+2,i+3);
		 if (A[i].compareTo(A[i+2]) <= 0) { 
		    swap(A,i,i+4); 
		    if (A[i].compareTo(A[i+1]) > 0) swap(A,i,i+1); 
		 } 
		 else { 
		    swap(A,i+2,i+4); 
		    if (A[i+2].compareTo(A[i+3]) > 0) swap(A,i+2,i+3); 
		 } 
		 if (A[i].compareTo(A[i+2]) <= 0) { 
		    if (A[i+1].compareTo(A[i+2]) <= 0) return A[i+1]; 
		    else return A[i+2]; 
		 } 
		 else { 
		    if (A[i].compareTo(A[i+3]) <= 0) return A[i]; 
		    else return A[i+3]; 
		 }
	}

	/**
	 * Scambia il contenuto di due diverse posizioni di un array di input.
	 * 
	 * @param A l'array in cui operare lo scambio
	 * @param i l'indice del primo elemento da scambiare
	 * @param j l'indice del secondo elemento da scambiare
	 */
	private static void swap(Comparable[] A, int i, int j) {
		Comparable temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
	/**
  	 * Seleziona l'elemento di ordine k dall'array di input mediante
	 * un algoritmo di selezione basato sul mediano dei mediani (<font color=red>Tempo O(n)</font>). 
	 * La selezione avviene invocando il metodo ricorsivo <code>selectRec</code>.
	 * 
	 * @param A l'array di cui ricercare l'elemento di ordine <code>k</code> 
	 * @param k l'ordine dell'elemento che si intende selezionare
	 * @return l'elemento di <code>A</code> di ordine <code>k</code>
	 */
	
	public static Comparable select(Comparable A[], int k) {
		return selectRec(A, 0, A.length-1, k); 
	}

	/**
	 * Seleziona l'elemento di ordine k di una sottosequenza dell'array
	 * di input mediante un algoritmo di selezione basato sul mediano
	 * dei mediani. L'algoritmo opera:
	 * <ul>
	 * <li> suddividendo la sottosequenza di input in gruppi aventi al pi&ugrave; 5 elementi </li>
	 * <li> calcolando, con l'eventuale ausilio dei metodi <code>med3</code>, <code>med4</code> e <code>med5</code>, i mediani
	 * di ciascun gruppo </li>
	 * <li> calcolando, mediante il metodo <code>select</code>, il mediano dei mediani precedentemente calcolati</li>
	 * <li> partizionando la sottosequenza di input mediante il metodo <code>partition</code> utilizzando come perno
	 * il mediano dei mediani </li>
	 * <li> invocando, eventualmente, lo stesso metodo ricorsivo <code>selectRec</code> su una delle sottosequenze
	 * determinate dal metodo <code>partition</code></li>
	 * </ul>
	 * Nel caso in cui la taglia della sottosequenza di input su cui operare la selezione sia inferiore a 10, il metodo
	 * utilizza il metodo <code>quickSortRec</code> per ordinare la sottosequenza di input e determinare direttamente
	 * l'elemento di ordine k ricercato
	 * 
	 * @param A l'array di cui ricercare l'elemento di ordine <code>k</code> 
	 * @param i l'indice dell'estremo inferiore della sequenza su cui operare la selezione
	 * @param f l'indice dell'estremo superiore della sequenza su cui operare la selezione
	 * @param k l'ordine dell'elemento che si intende selezionare
	 * @return l'elemento di <code>A</code> di ordine <code>k</code>
	 */
	private static Comparable selectRec(Comparable[] A, 
			                                 int i, int f, int k) { 
	   int p, n = f-i+1; 
	   if (n < 10) { 
	      AlgoritmiOrdinamento.quickSortRec(A,i,f); 
	      return A[i+k-1]; 
	   } 
	   
	   int q = (n%5 == 0) ? n/5 : n/5+1; 
	   Comparable[] M = new Comparable[q]; 
	   int j = 0;
	   for (p=i, j=0; p < i + 5*(q-1); p+=5, j++) M[j] = med5(A,p); 
	   M[q-1] = (n%5 == 0) ? med5(A, p) : 
	            (n%5 == 4) ? med4(A, p) : 
	            (n%5 == 3) ? med3(A, p) : 
	            (n%5 == 1) ? A[p] : 
	            (A[p].compareTo(A[p+1])>0) ? A[p]: A[p+1]; 
	   
	   Comparable perno = select(M, n/10 + 1); 
	   for (p=i; p<f; p++) 
		   if (A[p].equals(perno)) 
			   break; 
	   int m = partition(A, i, f, p); 
	   
	   int l = m-i; 
	   if (k <= l) return selectRec(A, i, m-1, k); 
	   else if (k > l+1) return selectRec(A, m+1, f, k-l-1); 
	   else return perno; 
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