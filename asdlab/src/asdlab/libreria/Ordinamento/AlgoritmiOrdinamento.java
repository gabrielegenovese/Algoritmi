package asdlab.libreria.Ordinamento;

import java.util.LinkedList;
import java.util.List;

/* ============================================================================
 *  $RCSfile: AlgoritmiOrdinamento.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/08 15:19:33 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.12 $
 */


/**
 * La classe <code>AlgoritmiOrdinamento</code> contiene l'implementazione di diversi
 * algoritmi per l'ordinamento di collezioni di oggetti. Sono implementati sia algoritmi basati
 * su confronti, quali <code>selectionSort</code>, <code>bubbleSort</code>, <code>heapSort</code>,
 * <code>mergeSort</code>, <code>quickSort</code> che algoritmi capaci di operare, sotto opportune
 * ipotesi, in tempo lineare, quali <code>bucketSort</code>, <code>radixSort</code> e <code>integerSort</code>.
 * <br>
 * Tutte le implementazioni proposte assumono che l'input sia costituito da un array i cui
 * elementi implementano l'interfaccia standard <code>Comparable</code>. La sequenza ordinata
 * viene prodotta da ciascun algoritmo nello stesso array utilizzato per indicare la sequenza di input.
 */
public class AlgoritmiOrdinamento {

	/**
	 * Ordina <em>in loco </em> l'array di input utilizzando l'algoritmo di selection sort (<font color=red>Tempo O(n&sup2;)</font>).
	 * 
	 * @param A l'array da ordinare
	 */
	public static void selectionSort(Comparable A[]) {
		for (int k = 0; k < A.length - 1; k++) {
			int m = k;
			for (int j = k + 1; j < A.length; j++)
				if (A[j].compareTo(A[m]) < 0) m = j;
			if (m != k) {
				Comparable temp = A[m];
				A[m] = A[k];
				A[k] = temp;
			}
		}
	}

	/**
	 * Ordina <em>in loco </em> l'array di input utilizzando l'algoritmo di insertion sort (<font color=red>Tempo O(n&sup2;)</font>).
	 * 
	 * @param A l'array da ordinare
	 */
	public static void insertionSort(Comparable A[]) {
		for (int k = 1; k <= A.length - 1; k++) {
			int j;
			Comparable x = A[k];
			for (j = 0; j < k; j++)
				if (A[j].compareTo(x) > 0) break;
			if (j < k) {
				for (int t = k; t > j; t--) A[t] = A[t - 1];
				A[j] = x;
			}
		}
	}

	/**
	 * Ordina <em>in loco </em> l'array di input utilizzando l'algoritmo di bubble sort (<font color=red>Tempo O(n&sup2;)</font>).
	 * 
	 * @param A l'array da ordinare
	 */
	public static void bubbleSort(Comparable A[]) {
		for (int i = 1; i < A.length; i++) {
			boolean scambiAvvenuti = false;
			for (int j = 1; j <= A.length - i; j++)
				if (A[j - 1].compareTo(A[j]) > 0) {
					Comparable temp = A[j - 1];
					A[j - 1] = A[j];
					A[j] = temp;
					scambiAvvenuti = true;
				}
			if (!scambiAvvenuti) break;
		}
	}

	/**
	 * Ordina <em>in loco </em> l'array di input utilizzando l'algoritmo di merge sort (<font color=red>Tempo O(n&middot;log(n))</font>).
	 * L'ordinamento avviene richiamando internamente il metodo ricorsivo <code>mergeSortRec</code>.
	 * 
	 * @param A l'array da ordinare
	 */
	public static void mergeSort(Comparable A[]) {
		mergeSortRec(A, 0, A.length - 1);
	}

	/**
	 * Ordina <em>in loco </em> una porzione dell'array di input
	 * utilizzando l'algoritmo di merge sort.
	 * L'ordinamento avviene richiamando ricorsivamente lo stesso metodo
	 * <code>mergeSortRec</code>, per l'ordinamento di sottosequenze di <code>A</code>, ed il metodo
	 * <code>merge</code>, per la fusione di sottosequenze ordinate.
	 * 
	 * @param A l'array da ordinare
	 * @param i l'indice dell'estremo inferiore della sequenza da ordinare
	 * @param f l'indice dell'estremo superiore della sequenza da ordinare
	 * 
	 */
	private static void mergeSortRec(Comparable A[], int i, int f) {
		if (i >= f) return;
		int m = (i + f) / 2;
		mergeSortRec(A, i, m);
		mergeSortRec(A, m + 1, f);
		merge(A, i, m, f);
	}
	
	/**
	 * Fonde due sottosequenze di elementi consecutive appartenenti
	 * ad uno stesso array di input in un'unica sequenza ordinata.
	 * La sequenza prodotta dalla fusione viene collocata nelle
	 * posizioni originariamente occupate dalle due sottosequenze.
	 * Assume che gli elementi delle due sottosequenze siano
	 * ordinati
	 * 
	 * @param A l'array contenente le sottosequenze da ordinare
	 * @param i1 l'indice dell'estremo inferiore della prima sottosequenza
	 * @param f1 l'indice dell'estremo superiore della prima sottosequenza
	 * @param f2 l'indice dell'estremo superiore della seconda sottosequenza
	 */
	private static void merge(Comparable A[], int i1, int f1, int f2) {
		Comparable[] X = new Comparable[f2 - i1 + 1];
		int i = 0, 
		i2 = f1 + 1, 
		k = i1;
		while (i1 <= f1 && i2 <= f2) {
			if (A[i1].compareTo(A[i2]) < 0) X[i++] = A[i1++];
			else X[i++] = A[i2++];
		}
		if (i1 <= f1)
			for (int j = i1; j <= f1; j++, i++) X[i] = A[j];
		else for (int j = i2; j <= f2; j++, i++) X[i] = A[j];
		for (int t = 0; k <= f2; k++, t++) A[k] = X[t];
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
		int max = 2 * i;
		if (2 * i > c) return;
		if (2 * i + 1 <= c && S[2 * i].compareTo(S[2 * i + 1]) < 0)
			max = 2 * i + 1;
		if (S[i].compareTo(S[max]) < 0) {
			Comparable temp = S[max];
			S[max] = S[i];
			S[i] = temp;
			fixHeap(S, c, max);
		}
	}

	/**
	 * Restituisce l'elemento di chiave massima attualmente custodito nell'heap. 
	 * 
	 * @param S l'array usato per rappresentare l'heap
	 * @return l'elemento di chiave massima custodito nell'heap
	 * @throws ArrayOutOfBoundsException se l'heap &egrave; vuoto
	 */
	private static Comparable findMax(Comparable S[]) {
		return S[1];
	}
	
	/**
	 * Rimuove l'elemento con chiave massima attualmente presente nell'heap. 
	 * L'operazione viene realizzata sovrascrivendo l'elemento di indice 1 (l'elemento
	 * di chiave massima) con l'elemento di indice <code>c</code> ed invocando il metodo <code>fixHeap</code>
	 * per ripristinare la propriet&agrave; di ordinamento dell'heap
	 * 
	 * @param S l'array usato per rappresentare l'heap
	 * @param c il numero di elementi dell'heap
	 */
	private static void deleteMax(Comparable S[], int c) {
		if (c <= 0) return;
		S[1] = S[c];
		c--;
		fixHeap(S, c, 1);
	}

	/**
	 * Trasforma in heap un array di elementi a partire da un elemento
	 * di indice <code>i</code>. La trasformazione avviene invocando ricorsivamente il metodo
	 * <code>heapify</code> per la trasformazione dei figli del nodo di indice <code>i</code>,
	 * ed il metodo <code>fixHeap</code> sul nodo di indice <code>i</code> per il ripristino della propriet&agrave; 
	 * di ordinamento dell'heap
	 *  
	 * @param S l'array da trasformare in heap
	 * @param n il numero di elementi dell'array
	 * @param i l'indice dell'elemento dell'array da cui innescare la trasformazione
	 */
	private static void heapify(Comparable S[], int n, int i) {
		if (i > n) return;
		heapify(S, n, 2 * i);
		heapify(S, n, 2 * i + 1);
		fixHeap(S, n, i);
	}

	/**
	 * Ordina <em>in loco </em> l'array di input
	 * utilizzando l'algoritmo di heap sort (<font color=red>Tempo O(n&middot;log(n))</font>). L'ordinamento
	 * avviene trasformando l'array di input in un heap, 
	 * attraverso il metodo <code>heapify</code>, e determinando gli elementi della sequenza ordinata
	 * attraverso i metodi <code>findMax</code> e <code>deleteMax</code>.
	 * 
	 * @param S l'array da ordinare
	 */
	public static void heapSort(Comparable S[]) {
		heapify(S, S.length - 1, 1);
		for (int c = (S.length - 1); c > 0; c--) {
			Comparable k = findMax(S);
			deleteMax(S, c);
			S[c] = k;
		}
	}

	/**
	 * Ordina <em>in loco </em> l'array di input utilizzando l'algoritmo di quick sort (<font color=red>Tempo O(n&sup2;), tempo atteso O(n&middot;log(n)</font>).
	 * L'ordinamento avviene richiamando internamente il metodo ricorsivo <code>quickSortRec</code>.
	 * 
	 * @param A l'array da ordinare
	 */	
	public static void quickSort(Comparable A[]) {
		quickSortRec(A, 0, A.length - 1);
	}

	/**
	 * Ordina <em>in loco </em> una porzione dell'array di input
	 * utilizzando l'algoritmo di quick sort.
	 * L'ordinamento avviene invocando il metodo <code>partition</code> 
	 * per dividere la sequenza da ordinare in due sottosequenze
	 * nelle quali tutti gli elementi della prima sottosequenza sono pi&ugrave;
	 * piccoli di tutti gli elementi della seconda sottosequenza. 
	 * Le due sottosequenze sono a loro volta ordinate mediate una chiamata ricorsiva
	 * dello stesso metodo <code>quickSortRec</code>
	 * 
	 * @param A l'array da ordinare
	 * @param i l'indice dell'estremo inferiore della sequenza da ordinare
	 * @param f l'indice dell'estremo superiore della sequenza da ordinare
	 * 
	 */
	public static void quickSortRec(Comparable[] A, int i, int f) {
		if (i >= f) return;

		int m = partition(A, i, f);
		quickSortRec(A, i, m - 1);
		quickSortRec(A, m+1, f);
	}
	
	/**
	 * Partiziona <em>in loco</em> gli elementi di una sequenza di input in due sottosequenze
	 * nelle quali tutti gli elementi della prima sottosequenza sono pi&ugrave; piccoli di
	 * tutti gli elementi della seconda sottosequenza. L'elemento perno viene scelto
	 * come elemento casuale all'interno della sequenza da ordinare.
	 * 
	 * @param A l'array contenente la sottosequenza da ordinare
	 * @param i l'indice dell'estremo inferiore della sottosequenza da ordinare
	 * @param f l'indice dell'estremo superiore della sottosequenza da ordinare
	 * @return la posizione dell'elemento perno nella sequenza partizionata
	 */
	private static int partition(Comparable A[], int i, int f) {
		int inf = i, sup = f + 1, pos = i
				+ (int) Math.floor((f-i+1) * Math.random());
		Comparable temp, x = A[pos];
		A[pos] = A[i];
		A[i] = x;
		while (true) {
			do {inf++;}
			while (inf <= f && A[inf].compareTo(x) <= 0);

			do {sup--;}
			while (A[sup].compareTo(x) > 0);
			if (inf < sup) {
				temp = A[inf];
				A[inf] = A[sup];
				A[sup] = temp;
			} else
				break;
		}
		temp = A[i];
		A[i] = A[sup];
		A[sup] = temp;
		return sup;
	}
	
	/**
	 * Ordina l'array di input utilizzando l'algoritmo di integer sort (<font color=red>Tempo O(n+k)</font>).
	 * Assume che gli elementi dell'array abbiano tutti valore nell'intervallo
	 * [0, k - 1], dove k &egrave; indicato da input. 
	 *  
	 * @param A l'array da ordinare
	 * @param k il valore massimo assumibile dagli elementi di <code>A</code>
	 * @throws ArrayOutOfBoundsException se <code>A</code> contiene elementi il cui valore non &egrave; incluso nell'intervallo [0, k - 1]
	 * 
	 * 
	 */

	public static void integerSort(int[] A, int k) {
		int[] Y = new int[k];
		int j = 0;
		for (int i = 0; i < k; i++) Y[i] = 0;
		for (int i = 0; i < k; i++) Y[A[i]]++;
		for (int i = 0; i < k; i++) {
			while (Y[i] > 0) {
				A[j] = i;
				j++;
				Y[i]--;
			}
		}
	}

	/**
	 * Ordina l'array di input utilizzando l'algoritmo di radix sort (<font color=red>Tempo O(n&middot;(1 + log(k)/log(n)))</font>).
	 * Assume che gli elementi dell'array <code>A</code> abbiano tutti valore nell'intervallo
	 * [0, k - 1], dove k &egrave; indicato da input. L'ordinamento avviene
	 * applicando reiteratamente l'algoritmo di <code>bucketSort</code> sulle diverse cifre
	 * che compongono la rappresentazione in base <code>b</code> degli elementi di <code>A</code>
	 * 
	 * @param A l'array da ordinare
	 * @param k il valore massimo assumibile dagli elementi di <code>A</code>
	 * @param b la base rispetto cui decomporre la rappresentazione degli elementi di <code>A</code>
	 */
	public static void radixSort(int[] A, int k, int b) {
		int t = 0;
		while (t <= Math.ceil(Math.log(k) / Math.log(b))){
			bucketSort(A, b, t);
			t++;
		}
	}

	/**
	 * Ordina l'array di input utilizzando l'algoritmo di bucket sort (<font color=red>Tempo O(n + b)</font>).
	 * L'ordinamento avviene rispetto alla <code>t</code>-sima cifra della rappresentazione
	 * in base <code>b</code> degli elementi dell'array di input <code>A</code>.
	 * 
	 * 
	 * @param A l'array da ordinare
	 * @param b la base rispetto cui decomporre la rappresentazione degli elementi di <code>A</code>
	 * @param t l'indice della cifra della rappresentazione in base <code>b</code> degli elementi
	 * di <code>A</code> su cui fare perno durante l'ordinamento
	 */
	public static void bucketSort(int[] A, int b, int t) {
		List[] Y = new List[b];
		int temp, c, j;
		for (int i = 0; i < b; i++) Y[i] = new LinkedList();
		for (int i = 0; i < A.length; i++) {
			temp = A[i] % ((int) (Math.pow(b, t + 1)));
			c = (int) Math.floor(temp / (Math.pow(b, t)));
			Y[c].add(new Integer(A[i]));
		}
		j=0;
		for (int i = 0; i < b; i++) {
			while (Y[i].size() > 0){
				A[j] = ((Integer) Y[i].get(0)).intValue();
				j++;
			}
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