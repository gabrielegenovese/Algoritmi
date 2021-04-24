package asdlab.libreria.Alberi;

import java.util.*;
import asdlab.libreria.Eccezioni.*;
import asdlab.libreria.StruttureElem.*;
/* ============================================================================
 *  $RCSfile: AlberoBinPF.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:43:23 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.27 $
 */

/**
 * La classe <code>AlberoBinPF</code> implementa l'interfaccia <code>AlberoBin</code>
 * usando una rappresentazione di tipo puntatori a figli.
 * I nodi dell'albero sono codificati come istanze della classe <code>NodoBinPF</code>.
 * La classe ha una sola variabile di istanza, <code>radice</code>, che mantiene
 * il riferimento alla radice dell'albero, tramite cui &egrave; possibile
 * accedere all'intera struttura. La radice di un albero vuoto &egrave; <code>null</code>. 
 *
 */
public class AlberoBinPF implements AlberoBin {

	/**
	 * La radice dell'albero
	 */	
	private NodoBinPF radice = null;
	
	/**
	 * Costruttore per l'istanziazione di un albero binario inizialmente vuoto
	 *
	 */
	public AlberoBinPF() {}

	/**
	 * Costruttore per l'istanziazione di un albero binario e contestuale creazione
	 * del nodo radice 
	 * 
	 * @param info Il contenuto informativo da associare alla radice del nuovo albero
	 */	
	public AlberoBinPF(Object info) {
		this.aggiungiRadice(info);
	}

	/**
	 * Restituisce il numero di nodi presenti nell'albero (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il numero di nodi presenti nell'albero
	 */
	public int numNodi() {
		return numNodi(radice);
	}

	/**
	 * Restituisce il numero di figli di un nodo <code>v</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuol conoscere il numero di figli
	 * @return il numero di figli di <code>v</code>
	 */
	public int grado(Nodo v) {
		int grado = 0;
		if (v == null) return -1;
		if (((NodoBinPF) v).sin != null) grado++;
		if (((NodoBinPF) v).des != null) grado++;
		return grado;
	}

	/**
	 * Restituisce il contenuto informativo di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuole conoscere il contenuto informativo
	 * @return il contenuto informativo di <code>v</code>
	 */
	public Object info(Nodo v) {
		return v.info;
	}

	/**
	 * Restituisce la radice dell'albero (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return la radice dell'albero. <code> null</code>, se l'albero &egrave; vuoto
	 */
	public Nodo radice() {
		return radice;
	}

	/**
	 * Restituisce il padre di un nodo (<font color=red>Tempo O(1)</font>). 
	 * 
	 * @param v il nodo di cui si vuole conoscere il padre
	 * @return il padre del nodo <code>v</code>. <code>null</code>, se <code>v</code> &egrave; la radice
	 */	
	public Nodo padre(Nodo v) {
		return ((NodoBinPF) v).padre;
	}

	/**
	 * Restituisce il figlio sinistro di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuole conoscere il figlio sinistro
	 * @return il figlio sinistro del nodo <code>v</code>. <code>null</code>, se il figlio
	 * sinistro  &egrave; assente
	 */	
	public Nodo sin(Nodo v) {
		return ((NodoBinPF)v).sin;
	}
	
	/**
	 * Restituisce il figlio destro di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuole conoscere il figlio destro
	 * @return il figlio destro del nodo <code>v</code>. <code>null</code>, se il figlio
	 * destro  &egrave; assente
	 */	
	public Nodo des(Nodo v) {
		return ((NodoBinPF)v).des;
	}

	/**
	 * Cambia il contenuto informativo di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuole cambiare il contenuto informativo
	 * @param info il nuovo contenuto informativo per il nodo <code>v</code>
	 */
	public void cambiaInfo(Nodo v, Object info){
		v.info = info;
	}

	/**
	 * Aggiunge la radice ad un albero binario vuoto (<font color=red>Tempo O(1)</font>). Viene creato un nuovo nodo
	 * il cui contenuto informativo &egrave; fornito da input. Il nuovo nodo
	 * diviene la radice dell'albero. 
	 * 
	 * @param info il contenuto informativo da associare alla radice
	 * @return il riferimento al nodo radice creato
	 * @throws EccezioneNodoEsistente se l'albero era gi&agrave; dotato di una radice
	 */
	public Nodo aggiungiRadice(Object info) {
		if (radice != null) throw new EccezioneNodoEsistente();
		radice = new NodoBinPF(info);
		radice.albero = this;
		return radice;
	}

	/**
	 * Inserisce un nuovo nodo nell'albero come figlio sinistro di un
	 * nodo preesistente <code>u</code> (<font color=red>Tempo O(1)</font>). Il nuovo nodo viene creato utilizzando
	 * il contenuto informativo fornito da input.
	 * 
	 * @param u il nodo cui aggiungere il nuovo figlio
	 * @param info il contenuto informativo da associare al nuovo nodo
	 * @return il riferimento al nodo creato
	 * @throws EccezioneNodoEsistente se <code>u</code> gi&agrave; possiede un figlio sinistro
	 */
	public Nodo aggiungiFiglioSin(Nodo u, Object info) {
		NodoBinPF z = (NodoBinPF) u;
		if (z.sin != null) throw new EccezioneNodoEsistente();
		z.sin = new NodoBinPF(info);
		z.sin.padre = z;
		return z.sin;
	}

	/**
	 * Inserisce un nuovo nodo nell'albero come figlio destro di un
	 * nodo preesistente <code>u</code> (<font color=red>Tempo O(1)</font>). Il nuovo nodo viene creato utilizzando
	 * il contenuto informativo fornito da input.
	 * 
	 * @param u il nodo cui aggiungere il nuovo figlio
	 * @param info il contenuto informativo da associare al nuovo nodo
	 * @return il riferimento al nodo creato
	 * @throws EccezioneNodoEsistente se <code>u</code> gi&agrave; possiede un figlio destro
	 */
	public Nodo aggiungiFiglioDes(Nodo u, Object info) {
		NodoBinPF z = (NodoBinPF) u;
		if (z.des != null) throw new EccezioneNodoEsistente();
		z.des = new NodoBinPF(info);
		z.des.padre = z;
		return z.des;
	}

	/** 
	 * Inserisce un sottoalbero <code>a</code> nell'albero come figlio sinistro
	 * di un nodo preesistente <code>u</code> (<font color=red>Tempo O(1)</font>).
	 * Al termine dell'operazione, il sottoalbero <code>a</code> viene svuotato. 
	 * 
	 * @param u il nodo cui aggiungere il sottoalbero
	 * @param a l'albero la cui radice diventer&agrave; figlia di <code>u</code>
	 * @throws EccezioneNodoEsistente se <code>u</code> gi&agrave; possiede un figlio sinistro
	 */
	public void innestaSin(Nodo u, AlberoBin a) {
		NodoBinPF z = (NodoBinPF) u;
		if (z.sin != null) throw new EccezioneNodoEsistente();
		if (a.radice() == null) return;
		z.sin = (NodoBinPF) a.radice();
		z.sin.padre = z;
		((AlberoBinPF)a).radice = null;
	}

	/** 
	 * Inserisce un sottoalbero <code>a</code> nell'albero come figlio destro
	 * di un nodo preesistente <code>u</code> (<font color=red>Tempo O(1)</font>).
	 * Al termine dell'operazione, il sottoalbero <code>a</code> viene svuotato. 
	 * 
	 * @param u il nodo cui aggiungere il sottoalbero
	 * @param a l'albero la cui radice diventer&agrave; figlia di <code>u</code>
	 * @throws EccezioneNodoEsistente se <code>u</code> gi&agrave; possiede un figlio destro
	 */
	public void innestaDes(Nodo u, AlberoBin a) {
		NodoBinPF z = (NodoBinPF) u;
		if (z.des != null) throw new EccezioneNodoEsistente();
		if (a.radice() == null) return;
		z.des = (NodoBinPF) a.radice();
		z.des.padre = z;
		((AlberoBinPF)a).radice = null;
	}

	/**
	 * Stacca e restituisce il sottoalbero radicato nel nodo <code>v</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v la radice del sottoalbero da staccare
	 * @return il sottoalbero radicato in <code>v</code>
	 */	
	public AlberoBin pota(Nodo v) {
		NodoBinPF vpf = (NodoBinPF)v;
		if (vpf == null) return new AlberoBinPF();
		if (vpf == radice)  radice = null;
		else {
			if (vpf.padre.sin == v) 
				 vpf.padre.sin = null;
			else vpf.padre.des = null;
			vpf.padre = null;
		}
		AlberoBinPF a = new AlberoBinPF();
		a.radice  = vpf;
		return a;
	}

	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in ampiezza (Breadth First Search, BFS) (<font color=red>Tempo O(n)</font>).
	 * La visita dell'albero avviene mediante un approccio iterativo.
	 * 
	 * @return la lista dei nodi incontrati durante la visita BFS
	 */
	public List visitaBFS() {
		Coda c = new CodaCollegata();
		List listaRitorno = new LinkedList();
		c.enqueue(radice);
		while (!c.isEmpty()) {
			NodoBinPF u = (NodoBinPF) c.dequeue();
			if (u != null) {
				listaRitorno.add(u);
				c.enqueue(u.sin);
				c.enqueue(u.des);
			}
		}
		return listaRitorno;
	}

	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in profondit&agrave; (Depth First Search, DFS) (<font color=red>Tempo O(n)</font>).
	 * La visita dell'albero avviene mediante un approccio iterativo. 
	 * 
	 * @return la lista dei nodi incontrati durante la visita DFS
	 */
	
	public List visitaDFS() {
		PilaArray p = new PilaArray();
		List listaNodi = new LinkedList();
		p.push(radice);
		while (!p.isEmpty()) {
			NodoBinPF u = (NodoBinPF) p.pop();
			if (u != null) {
				listaNodi.add(u);
				p.push(u.des);
				p.push(u.sin);
			}
		}
		return listaNodi;
	}

	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in profondit&agrave; (Depth First Search, DFS)
	 * il cui tipo &egrave; indicato da input (<font color=red>Tempo O(n)</font>).
	 * La visita viene concretamente realizzata mediante una chiamata ricorsiva
	 * del metodo <code>visitaDFSRic</code>.
	 * 
	 * @param t il tipo di visita da utilizzare
	 * @return la lista dei nodi incontrati durante la visita DFS
	 */
	public List visitaDFS(TipoVisita t) {
		List listaNodi = new LinkedList();
		visitaDFSRic(radice, listaNodi, t);
		return listaNodi;
	}

	/**
	 * Opera la visita ricorsiva in profondit&agrave; (Depth First Search, DFS) del sottoalbero
	 * radicato nel nodo <code>r</code>. I nodi incontrati durante la visita
	 * sono aggiunti in coda alla lista passata da input. 
	 * 
	 * @param r il nodo correntemente visitato
	 * @param listaNodi la lista dei nodi sinora visitati
	 * @param t il tipo di visita da utilizzare
	 */
	private static void visitaDFSRic(NodoBinPF r, List listaNodi, TipoVisita t) {
		if (r == null) return;
		if (t == TipoVisita.PREORDER) listaNodi.add(r);
		visitaDFSRic(r.sin, listaNodi, t);
		if (t == TipoVisita.INORDER) listaNodi.add(r);
		visitaDFSRic(r.des, listaNodi, t);
		if (t == TipoVisita.POSTORDER) listaNodi.add(r);
	}

	/**
	 * Restituisce la taglia (numero di nodi) dell'albero radicato in un nodo
	 * <code>r</code>. Tale informazione viene determinata conteggiando
	 * ricorsivamente la taglia dei sottoalberi
	 * radicati nei figli di <code>r</code>.
	 * 
	 * @return il numero di nodi presenti nell'albero
	 */
	private int numNodi(NodoBinPF r) {
		return r == null ? 0 : numNodi(r.sin) + numNodi(r.des) + 1;
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