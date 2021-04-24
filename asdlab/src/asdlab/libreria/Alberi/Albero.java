package asdlab.libreria.Alberi;

import java.util.List;

/* ============================================================================
 *  $RCSfile: Albero.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/02 16:29:55 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.16 $
 */

/**
 * Il tipo di dato Albero, descritto dall'interfaccia <code>Albero</code>,
 * rappresenta alberi radicati non ordinati. Le operazioni
 * supportate permettono di recuperare informazioni sull'albero (radice, numero di nodi,
 * grado, padre, lista dei figli e contenuto informativo di un nodo),
 * di modificarne il contenuto informativo e la struttura
 * (aggiungendo nodi oppure aggiungendo o rimuovendo interi sottoalberi),
 * o di visitarne i nodi in un ordine sistematico. 
 * <p>I nodi di un albero sono rappresentati attraverso il tipo <code>Nodo</code>. 
 * I riferimenti a nodi vengono generati dalle operazioni che creano nodi (<code>aggiungiRadice</code> e <code>aggiungiFiglio</code>)
 * ed essi possono essere in seguito utilizzati per riferirsi ai nodi
 * dell'albero.
 * <br>Quando un sottoalbero viene staccato da un albero
 * mediante l'operazione <code>pota</code>, esso diventa una istanza di albero
 * a s&eacute; stante, ma i riferimenti ai nodi rimangono gli stessi. 
 * In modo simile, quando un albero viene innestato in un altro 
 * albero, smette di essere un'istanza di albero a s&eacute; stante,
 * ma conserva gli stessi riferimenti ai suoi nodi. 
 *
 */
public interface Albero {
	/**
	 * Restituisce il numero di nodi presenti nell'albero.
	 * 
	 * @return il numero di nodi presenti nell'albero
	 */
	public int numNodi();
	
	/**
	 * Restituisce il numero di figli di un nodo.
	 * 
	 * @param v il nodo di cui si vuol conoscere il numero di figli
	 * @return il numero di figli di <code>v</code>
	 */
	public int grado(Nodo v);

	/**
	 * Restituisce il contenuto informativo di un nodo.
	 * 
	 * @param v il nodo di cui si vuole conoscere il contenuto informativo
	 * @return il contenuto informativo di <code>v</code>
	 */
	public Object info(Nodo v);

	/**
	 * Restituisce la radice dell'albero.
	 * 
	 * @return la radice dell'albero. <code>null</code>, se l'albero &egrave; vuoto
	 */
	public Nodo radice();	
	
	/**
	 * Restituisce il padre di un nodo.
	 * 
	 * @param v il nodo di cui si vuole conoscere il padre
	 * @return il padre del nodo <code>v</code> nell'albero.
	 *          <code>null</code>, se <code>v</code> &egrave; la radice
	 */
	public Nodo padre(Nodo v);
	
	/**
	 * Restituisce la lista dei figli di un nodo.
	 * 
	 * @param v il nodo di cui si vuol conoscere la lista dei figli
	 * @return la lista dei figli di <code>v</code>
	 */
	public List figli(Nodo v);
	
	/**
	 * Camba il contenuto informativo di un nodo.
	 * 
	 * @param v il nodo di cui si vuole cambiare il contenuto informativo
	 * @param info il nuovo contenuto informativo per il nodo <code>v</code>
	 */
	public void cambiaInfo(Nodo v, Object info);
	
	/**
	 * Aggiunge la radice ad un albero vuoto.
	 * 
	 * @param info il contenuto informativo da associare alla radice
	 * @return il riferimento al nodo radice creato
	 */
	public Nodo aggiungiRadice(Object info);
	
	/**
	 * Inserisce un nuovo nodo nell'albero come figlio di un
	 * nodo preesistente.
	 * 
	 * @param u il nodo cui aggiungere il nuovo figlio
	 * @param info il contenuto informativo da associare al nuovo nodo
	 * @return il riferimento al nodo creato
	 */
	public Nodo aggiungiFiglio(Nodo u, Object info);
	
	/** 
	 * Inserisce un sottoalbero nell'albero come figlio di un nodo
	 * preesistente.
	 * 
	 * @param u il nodo cui aggiungere il sottoalbero
	 * @param a l'albero la cui radice diventer&agrave; figlia di <code>u</code>
	 */
	public void innesta(Nodo u, Albero a);
	
	/**
	 * Stacca e restituisce il sottoalbero radicato in un certo
	 * nodo dell'albero.
	 * 
	 * @param v la radice del sottoalbero da staccare
	 * @return il sottoalbero radicato in <code>v</code>
	 */
	public Albero pota(Nodo v);
	
	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in profondit&agrave; (Depth First Search, DFS).
	 * 
	 * @return la lista dei nodi incontrati durante la visita DFS
	 */
	public List visitaDFS();
	
	/**
	 * Restituisce la lista dei nodi dell'albero nell'ordine in cui vengono
	 * incontrati da una visita in ampiezza (Breadth First Search, BFS).
	 * 
	 * @return la lista dei nodi incontrati durante la visita BFS
	 */
	public List visitaBFS();
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