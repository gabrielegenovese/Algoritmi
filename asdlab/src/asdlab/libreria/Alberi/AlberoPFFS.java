package asdlab.libreria.Alberi;

import java.util.*;
import asdlab.libreria.Eccezioni.*;
import asdlab.libreria.StruttureElem.*;
 /* ============================================================================
 *  $RCSfile: AlberoPFFS.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/02 16:29:55 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.24 $
 */

/**
 * La classe <code>AlberoPFFS</code> implementa l'interfaccia <code>Albero</code>
 * usando una rappresentazione di tipo primo figlio-fratello successivo.
 * I nodi dell'albero sono codificati come istanze della classe <code>NodoPFFS</code>.
 * La classe ha una sola variabile di istanza, <code>radice</code>, che mantiene
 * il riferimento alla radice dell'albero, tramite cui &egrave; possibile
 * accedere all'intera struttura. La radice di un oggetto appena creato &egrave; <code>null</code>. 
 *
 */
public class AlberoPFFS implements Albero {

	/**
	 * La radice dell'albero, &egrave; pari a <code>null</code> nel caso in cui l'albero sia vuoto
	 */
	protected NodoPFFS radice = null;
	
	/**
	 * Costruttore per l'istanziazione di un albero inizialmente vuoto
	 *
	 */
	public AlberoPFFS() {}

	/**
	 * Costruttore per l'istanziazione di un albero radicato in un nodo precedentemente creato
	 * 
	 * @param radice Il nodo che diventer&agrave; la radice del nuovo albero
	 */
	public AlberoPFFS(Nodo radice) {
		this.radice = (NodoPFFS) radice;
		this.radice.padre = null;
		this.radice.succ = null;
		this.radice.albero = this;
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
	 * Restituisce il numero di figli (grado) di un nodo <code>v</code> (<font color=red>Tempo O(grado di v)</font>). Tale numero viene
	 * determinato scorrendo e conteggiando l'elenco dei figli di <code>v</code>.
	 * 
	 * @param v il nodo di cui si vuol conoscere il numero di figli
	 * @return il numero di figli di <code>v</code>
	 */
	public int grado(Nodo v) {
		int grado = 0;
		if (((NodoPFFS)v).primo != null) {
			NodoPFFS nodo2 = ((NodoPFFS)v).primo;
			for (grado = 1; nodo2.succ != null; grado++)
				nodo2 = nodo2.succ;
		}
		return grado;
	}

	/**
	 * Restituisce il contenuto informativo di un nodo (<font color=red>Tempo O(1)</font>)
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
	 * @return la radice dell'albero.<code> null</code>, se l'albero &egrave; vuoto
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
		return ((NodoPFFS) v).padre;
	}


	/**
	 * Restituisce la lista dei figli di un nodo <code>v</code> (<font color=red>Tempo O(grado di v)</font>). La lista viene determinata
	 * scorrendo l'elenco dei figli di <code>v</code>. 
	 * 
	 * @param v il nodo di cui si vuol conoscere la lista dei figli
	 * @return la lista dei figli del nodo <code>v</code>
	 */
	public List figli(Nodo v) {
		List figli = new LinkedList();
		NodoPFFS temp = ((NodoPFFS) v).primo;
		while(temp != null){
			figli.add(temp);
			temp = temp.succ;
		} 
		return figli;
	}

	/**
	 * Camba il contenuto informativo di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuole cambiare il contenuto informativo
	 * @param info il nuovo contenuto informativo per il nodo <code>v</code>
	 */
	public void cambiaInfo(Nodo v, Object info){
		v.info = info;
	}

	/**
	 * Aggiunge la radice ad un albero vuoto (<font color=red>Tempo O(1)</font>). Viene creato un nuovo nodo
	 * il cui contenuto informativo &egrave; fornito da input. Il nuovo nodo
	 * diviene la radice dell'albero.
	 * 
	 * @param info il contenuto informativo da associare alla radice
	 * @return il riferimento al nodo radice creato
	 * @throws EccezioneNodoEsistente se l'albero era gi&agrave; dotato di una radice
	 */
	public Nodo aggiungiRadice(Object info) {
		if (radice != null) 
			throw new EccezioneNodoEsistente();
		radice = new NodoPFFS(info);
		radice.albero = this;
		return radice;
	}

	/**
	 * Inserisce un nuovo nodo nell'albero come figlio di un
	 * nodo preesistente <code>u</code> (<font color=red>Tempo O(1)</font>). Il nuovo nodo viene creato utilizzando
	 * il contenuto informativo fornito da input e viene aggiunto
	 * come primo figlio di <code>u</code>. L'eventuale primo figlio di <code>u</code> precedentemente
	 * esistente viene collegato come fratello successivo del nuovo nodo. 
	 * 
	 * @param u il nodo cui aggiungere il nuovo figlio
	 * @param info il contenuto informativo da associare al nuovo nodo
	 * @return il riferimento al nodo creato
	 */
	public Nodo aggiungiFiglio(Nodo u, Object info) {
		NodoPFFS z = (NodoPFFS) u;
		NodoPFFS v = new NodoPFFS(info);
		v.succ = z.primo;
		z.primo = v;
		v.padre = z;
		return v;
	}

	/** 
	 * Inserisce un sottoalbero <code>a</code> nell'albero come figlio di un nodo
	 * preesistente <code>u</code> (<font color=red>Tempo O(1)</font>). Il sottoalbero viene innestato inserendo
	 * la sua radice come primo figlio di <code>u</code>. L'eventuale primo figlio
	 * di <code>u</code> precedentemente esistente viene collegato come fratello
	 * successivo del nuovo nodo. Al termine dell'operazione,
	 * il sottoalbero <code>a</code> viene svuotato. 
	 * 
	 * @param u il nodo cui aggiungere il sottoalbero
	 * @param a l'albero la cui radice diventer&agrave; figlia di <code>u</code>
	 */
	public void innesta(Nodo u, Albero a) {
		NodoPFFS radiceSottoalbero = (NodoPFFS) a.radice();
		a.pota(radiceSottoalbero);
		if (((NodoPFFS) u).primo != null) {
			NodoPFFS nodoTemporaneo = ((NodoPFFS) u).primo;
			((NodoPFFS) u).primo = radiceSottoalbero;
			radiceSottoalbero.succ = nodoTemporaneo;
		} else 
			((NodoPFFS) u).primo = radiceSottoalbero;
		radiceSottoalbero.padre = (NodoPFFS) u;
	}

	/**
	 * Stacca e restituisce il sottoalbero radicato nel nodo <code>v</code> (<font color=red>Tempo O(grado di v)</font>).
	 * 
	 * @param v la radice del sottoalbero da staccare
	 * @return il sottoalbero radicato in <code>v</code>
	 */
	public Albero pota(Nodo v) {		
		if (v == radice) {
			radice = null;
			return new AlberoPFFS(v);
		}
		NodoPFFS u = ((NodoPFFS) v).padre;
		if (u.primo == v) {
			u.primo = u.primo.succ;
		} else {
			NodoPFFS temp = u.primo;
			boolean nodoTrovato = false;
			for (; temp.succ != null; temp = temp.succ) {
				if (temp.succ == v) {
					nodoTrovato = true;
					break;
				}
			}
			if (nodoTrovato)  temp.succ = temp.succ.succ;
		}
		((NodoPFFS) v).succ = null;
		return new AlberoPFFS(v);
	}

	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in profondit&agrave; (Depth First Search, DFS) (<font color=red>Tempo O(n)</font>).
	 * La visita dell'albero avviene mediante un approccio iterativo
	 * 
	 * @return la lista dei nodi incontrati durante la visita DFS
	 */
	public List visitaDFS() {
		PilaArray S = new PilaArray();
		List listaRitorno = new LinkedList();
		S.push(radice);
		while (!S.isEmpty()) {
			Nodo u = (Nodo) S.pop();
			if (u != null) {
				listaRitorno.add(u);
				Iterator elencoFigli = figli(u).iterator();
				while (elencoFigli.hasNext())
					S.push(elencoFigli.next());
			}
		}
		return listaRitorno;
	}

	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in ampiezza (Breadth First Search, BFS) (<font color=red>Tempo O(n)</font>).
	 * La visita dell'albero avviene mediante un approccio iterativo. 
	 * 
	 * @return la lista dei nodi incontrati durante la visita BFS
	 */
	public List visitaBFS() {
		Coda C = new CodaCollegata();
		List listaRitorno = new LinkedList();
		C.enqueue(radice);
		while (!C.isEmpty()) {
			Nodo u = (Nodo) C.dequeue();
			if (u != null) {
				listaRitorno.add(u);
				Iterator elencoFigli = figli(u).iterator();
				while (elencoFigli.hasNext()) 
					C.enqueue(elencoFigli.next());
			}
		}
		return listaRitorno;
	}

	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in profondit&agrave; (Depth First Search, DFS) (<font color=red>Tempo O(n)</font>).
	 * La visita viene concretamente realizzata mediante una chiamata ricorsiva
	 * del metodo <code>visitaDFSRic</code>.
	 * 
	 * @return la lista dei nodi incontrati durante la visita DFS
	 */
	public List visitaDFSRic() {
		List listaNodi = new LinkedList();
		visitaDFSRic(this, radice, listaNodi);
		return listaNodi;
	}

	/**
	 * Opera la visita ricorsiva in profondit&agrave; (Depth First Search, DFS) del sottoalbero
	 * radicato nel nodo <code>r</code>. I nodi incontrati durante la visita
	 * sono aggiunti in coda alla lista passata da input. 
	 * 
	 * @param albero l'albero su cui operare la visita
	 * @param r il nodo da visitare
	 * @param listaNodi la lista dei nodi sinora visitati
	 */
	private static void visitaDFSRic(Albero albero, Nodo r, List listaNodi) {
		if (r == null) return;
		listaNodi.add(r);
		Iterator elencoFigli = albero.figli(r).iterator();
		while (elencoFigli.hasNext())
			visitaDFSRic(albero, (Nodo)elencoFigli.next(), listaNodi);
	}

	/**
	 * Restituisce la taglia (numero di nodi) dell'albero radicato in un nodo
	 * <code>r</code>. Tale informazione viene determinata conteggiando
	 * ricorsivamente <code>r</code> e la taglia dei sottoalberi
	 * radicati nei figli di <code>r</code>.
	 * 
	 * @return il numero di nodi presenti nell'albero
	 */
	private int numNodi(Nodo r) {
		if (r == null) return 0;
		int totale = 0;
		NodoPFFS temp = ((NodoPFFS) r).primo;
		while (temp != null) {
			totale+= numNodi(temp);
			temp = temp.succ;
		}
		return totale + 1;
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