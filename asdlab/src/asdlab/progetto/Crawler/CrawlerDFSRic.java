package asdlab.progetto.Crawler;

import java.io.*;
import java.util.*;
import asdlab.libreria.Alberi.Nodo;
import asdlab.libreria.Grafi.*;
import asdlab.libreria.AlberiRicerca.*;

/* ============================================================================
 *  $RCSfile: CrawlerDFSRic.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.3 $
 */

/**
 * La classe <code>CrawlerDFSRic</code> implementa un crawler basato su visita DFS
 * ricorsiva. 
 * Le pagine Web esaminate durante la visita DFS vengono acquisite e memorizzate
 * su disco per poi essere successivamente indicizzate. La classe <code>CrawlerDFS</code>
 * implementa l'interfaccia {@link Crawler}, impegnandosi
 * cos&igrave; a fornire il metodo <code>crawl</code>. 
 * <br>
 * La struttura del grafo su cui si opera la visita &egrave; inizialmente sconosciuta
 * e viene rivelata durante la visita stessa mediante l'esecuzione del metodo
 * ricorsivo <code>crawlRic</code>. Ogni volta che un nodo viene visitato per
 * la prima volta, il metodo <code>crawlRic</code> ne acquisisce la corrispondente pagina Web
 * e la esamina estraendone i link ivi contenuti.
 * Tali link sono successivamente analizzati e, nel caso in cui colleghino nodi 
 * non ancora raggiunti dalla visita, aggiunti al grafo sotto forma di archi. 
 * Diversamente dagli archi, il numero dei nodi del grafo &egrave; noto in partenza
 * e corrisponde al numero di pagine Web che si intendono acquisire. 
 * Tali nodi, pur essendo creati prima di iniziare la visita, sono
 * effettivamente associati ad una pagina Web nel momento
 * in cui sono connessi al resto del grafo mediante la creazione
 * di un nuovo arco. La corrispondenza tra gli URL delle pagine Web
 * gi&agrave; acquisite ed i relativi nodi del grafo
 * viene esplicitamente mantenuta
 * attraverso il dizionario <code>raggiunti</code>.
 * L'acquisizione e la memorizzazione del contenuto delle pagine
 * Web avviene per mezzo della classe {@link ArchivioDoc}
 * mentre l'estrazione dei link in essere contenute 
 * viene realizzata tramite la classe {@link EstrattoreLink}.
 */

public class CrawlerDFSRic implements Crawler {

	/**
	 * Riferimento al sottografo del Web contenente le pagine visitate ed i link che le collegano
	 */
	private Grafo g;
	
	/**
	 * Marca i nodi del grafo gi&agrave; visitati
	 */
	protected boolean[] vis;
	
	/**
	 * Riferimento alla classe utilizzata per l'estrazione di link dalle pagine Web
	 */
	private EstrattoreLink est;
	
	/**
	 * Riferimento alla classe utilizzata per l'acquisizione e la memorizzazione di pagine Web
	 */
	private ArchivioDoc arc;
	
	/**
	 * Mantiene la corrispondenza tra URL delle pagine Web acquisite ed i relativi nodi del grafo
	 */
	private Dizionario raggiunti;
	
	/**
	 * Il numero di documenti attualmente raggiunti
	 */
	private int numDocs;
	
	/**
	 * Il numero massimo di documenti da acquisire
	 */
	private int maxDocs;

	/**
	 * Opera il crawling del Web a cominciare da un indirizzo di partenza indicato da input.
	 * Come passo preliminare, si crea il grafo della visita,  qui rappresentato come istanza
	 * della classe <code>GrafoLA</code>, cui vengono aggiunti un numero di nodi
	 * pari al numero di pagine che si intendono visitare. 
	 * La visita inizia acquisendo la pagina Web indicata da input, associandola
	 * al primo nodo del grafo precedentemente creato ed avviando da
	 * questi l'algoritmo di visita tramite l'esecuzione del metodo <code>crawlRic</code>.
	 * Il dizionario dei nodi gi&agrave; visitati viene rappresentato mediante un'istanza
	 * della classe <code>AlberoAVL</code>. 
	 * 
	 * @param urlIniz l'indirizzo Web da cui avviare il crawling
	 * @param dirArchivio il nome della directory archivio in cui salvare le pagine acquisite
	 * @param maxDocs il numero massimo di pagine da collezionare
	 * @param compresso abilita la compressione delle pagine archiviate
	 * @return il grafo corrispondente alla porzione di Web esplorata
	 */	
	public Grafo crawl(String urlIniz, String dirArchivio, int maxDocs, boolean compresso){
		this.est = new EstrattoreLink();
		this.arc = new ArchivioDoc(dirArchivio, compresso);
		this.raggiunti = new AlberoAVL();
		this.maxDocs = maxDocs;
		this.g = new GrafoLA();
		for (int i = 0; i < maxDocs; i++) g.aggiungiNodo(null);
		g.cambiaInfoNodo(g.nodo(0), urlIniz);
		raggiunti.insert(g.nodo(0), urlIniz);
		numDocs = 1;
		this.vis = new boolean[g.numNodi()];
		crawlRic(g.nodo(0));
		return g;
	}

	/**
	 * Visita il nodo di input <code>u</code> secondo la logica DFS ed
	 * aggiorna la struttura del grafo di conseguenza. La
	 * visita avviene innanzitutto marcando come visitato il nodo <code>u</code>,
	 * acquisendo, tramite l'oggetto <code>arc</code>, il contenuto della pagina
	 * ad esso corrispondente ed estraendone, tramite l'oggetto <code>est</code>,
	 * i link ivi contenuti. I link estratti sono esaminati ed aggiunti nell'ordine
	 * al grafo sotto forma di archi. Al termine di ogni aggiunta, nel caso
	 * il nodo di destinazione <code>v</code> del nuovo arco non sia stato ancora visitato,
	 * viene eseguita una chiamata ricorsiva di <code>crawlRic</code> su <code>v</code>.
	 * I nodi raggiunti per la prima volta
	 * da un arco sono associati agli URL delle corrispondenti pagine Web.
	 * 
	 * @param u il nodo da visitare
	 */	
	private void crawlRic(Nodo u) { 
		vis[g.indice(u)] = true;
		String urlU = (String)g.infoNodo(u);
		char[] doc = arc.archiviaDoc(urlU, g.indice(u));
		if (doc == null || numDocs == maxDocs) return;
		Iterator i = est.estraiLink(urlU,
				new CharArrayReader(doc)).iterator();
		while (i.hasNext() && numDocs < maxDocs){
			String urlV = (String)i.next();
			Nodo v = (Nodo)raggiunti.search(urlV);
			if (v == null) {
				v = g.nodo(numDocs);				
				g.cambiaInfoNodo(v, urlV);
				raggiunti.insert(v, urlV);
				numDocs = numDocs + 1;
			}
			g.aggiungiArco(u, v, 1);
			if (vis[g.indice(v)]) continue;
			crawlRic(v);
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

