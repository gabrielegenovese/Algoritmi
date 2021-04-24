package asdlab.progetto.Crawler;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.*;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;
import asdlab.libreria.AlberiRicerca.*;

/* ============================================================================
 *  $RCSfile: EstrattoreLink.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.15 $
 */


/**
 * La classe <code>EstrattoreLink</code> consente di estrarre i link contenuti
 * in una o pi&ugrave; pagine Web. 
 * La classe fornisce un metodo <code>estraiLink</code> che restituisce
 * una lista di stringhe contenenti gli URL trovati nella pagina esaminata.
 * Il metodo considera solo URL con protocollo http corrispondenti a pagine aventi
 * content type di tipo text/plain o text/html, contenenti cio&egrave; testo semplice
 * o html. Osserviamo che le pagine Web possono contenere link relativi
 * alla pagina in cui sono contenuti. Tali casi sono gestiti da <code>EstrattoreLink</code>
 * richiedendo che, assieme alla pagina da esaminare, sia indicato anche 
 * il suo URL. Il parsing vero e proprio della pagina Web viene delegato alla classe
 * annidata {@link Parser}, a sua volta implementata derivando la classe standard
 * {@link ParserCallback}. <br>
 * Per ciascun link individuato dalla classe <code>Parser</code>,
 * si procede ad interrogare il server Web che ospita la pagina corrispondente,
 * allo scopo di stabilire il content type della pagina stessa. Al fine di ridurre l'impatto
 * di queste interrogazioni, la classe <code>EstrattoreLink</code> mantiene in un dizionario,
 * <code>urlCache</code>, l'insieme degli URL relativi a pagine gi&agrave; in precedenza riconosciute
 * come di tipo testo o html.
 */

public class EstrattoreLink  {

	/**
	 * Tiene traccia degli URL in precedenza esaminati e relativi a pagine di tipo testo o html 
	 */
	protected Dizionario urlCache;
	
	/**
	 * Tiene traccia degli host rivelatisi in precedenza irraggiungibili 
	 */
	protected Dizionario hostNonRagg;
	
	/**
	 * Valore di timeout utilizzato nelle connessioni verso i server Web
	 */
	protected final static String timeout = "15000";

	/**
	 * Crea una nuova istanza di <code>EstrattoreLink</code>. I due
	 * dizionari <code>urlCache</code> e <code>hostNonRagg</code>
	 * sono inizializzati come istanze della classe <code>AlberoAVL</code>.
	 *
	 */
	public EstrattoreLink() {
		urlCache = new AlberoAVL();
		hostNonRagg = new AlberoAVL();
	}

	/**
	 * Estrae i link contenuti nella pagina indicata da input. L'effettiva
	 * estrazione dei link viene delegata ad una nuova istanza della classe
	 * <code>Parser</code> da cui l'elenco dei link viene ottenuto
	 * mediante esecuzione del metodo <code>elencoLink</code>.
	 * 
	 * @param baseUrl l'indirizzo della pagina da esaminare
	 * @param reader il riferimento al contenuto della pagina da esaminare
	 * @return l'elenco dei link di tipo testo o html contenuti nella pagina esaminata
	 */
	public List estraiLink(String baseUrl, Reader reader){
		System.setProperty("sun.net.client.defaultConnectTimeout", timeout);
		Parser p = new Parser(baseUrl, reader);
		return p.elencoLink();
	}
	
	/**
	 * La classe annidata <code>Parser</code> estrae i link contenuti in una pagina Web. 
	 * L'analisi della pagina avviene derivando la classe {@link ParserCallback}
	 * e ridefinendo il metodo {@link ParserCallback#handleStartTag(Tag, MutableAttributeSet, int)}.
	 * Tale metodo viene eseguito dalla classe {@link ParserCallback} ogni volta che,
	 * durante l'analisi di una pagina Web, si incontra un qualsiasi tag html. 
	 * Il metodo ridefinito <code>handleStartTag</code> considera i soli tag
	 * di tipo "anchor", contenenti collegamenti ipertestuali, delegandone 
	 * l'esame al metodo <code>aggiungiLink</code> che esamina il content type delle
	 * pagine cui essi puntano ed, eventualmente, li aggiunge all'elenco dei
	 * link presenti nella pagina.
	 *
	 */
	private class Parser extends ParserCallback {

		/**
		 * Elenco dei link sinora individuati nella pagina attualmente esaminata
		 */
		private List elencoLink = new LinkedList();
		
		/**
		 * L'URL di riferimento della pagina attualmente esaminata
		 */
		private URL contesto;
		
		/**
		 * Abilita la visualizzazione di informazioni circa lo stato di avanzamento dell'analisi
		 */
		private final static boolean verbose = true;

		/**
		 * Crea una nuova istanza di <code>Parser</code>. L'istanza cos&igrave; ottenuta
		 * viene fornita in input al metodo {@link ParserDelegator#parse(Reader, ParserCallback, boolean)}
		 * della classe {@link ParserDelegator} per operare il parsing della pagina Web indicata da input.
		 * 
		 * @param baseAddr l'URL di riferimento della pagina attualmente esaminata
		 * @param reader il riferimento al contenuto della pagina da esaminare
		 */
		public Parser(String baseAddr, Reader reader) {

			ParserDelegator d = new ParserDelegator();
			try {
				this.contesto = new URL(baseAddr);
				if (urlCache.search(nomeFile(this.contesto)) == null) 
					urlCache.insert(baseAddr, nomeFile(this.contesto));
				d.parse(reader, this, true);
			} 
			catch (IOException e) {
				System.out.println("Errore nel parsing del documento " + e.getMessage());
			}
		}

		/**
		 * Delega al metodo {@link Parser#aggiungiLink(String)} la gestione
		 * dei tag di tipo "A" incontrati durante il parsing della pagina Web.
		 * Il metodo <code>handleStart</code> viene automaticamente invocato,
		 * durante il parsing di una pagina Web, ogni qual volta si incontri
		 * un tag html. In tal caso, esso verifica che il tag sia di tipo "A"
		 * ed, in caso affermativo, ne delega l'analisi al metodo  {@link Parser#aggiungiLink(String)}. 
		 */
		public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
			if (t == Tag.A)
				aggiungiLink((String) a.getAttribute(Attribute.HREF));
			super.handleStartTag(t, a, pos);
		}

		/**
		 * Esamina il content type di una pagina e, nel caso sia di tipo
		 * testo o html e non sia stata sinora considerata, ne aggiunge
		 * il link all'elenco dei link sinora incontrati. Il metodo
		 * opera utilizzando la classe standard {@link URL} per determinare
		 * il protocollo con cui &egrave, resa disponibile la risorsa
		 * e, nel caso sia una risorsa accessibile tramite il protcollo http,
		 * ne esamina il content type. Nel caso la risorsa sia una pagina di testo
		 * o html, il suo URL viene aggiunto ad <code>elencoLink</code>. Se
		 * la pagina &egrave; stata considerata per la prima volta, il suo URL
		 * viene inserito nel dizionario <code>urlCache</code> per velocizzare
		 * le eventuali future interrogazioni sulla stessa pagina. Nel caso
		 * l'host che ospita la pagina sia irraggiungibile, il suo indirizzo
		 * viene aggiunto al dizionario <code>hostNonRagg</code> per abortire
		 * ulteriori interrogazioni future che abbiano per oggetto lo stesso host.

		 * 
		 * @param link l'indirizzo della risorsa da esaminare
		 */
		private void aggiungiLink(String link){
			
			if (link.contains("google") || link.contains("cache")) {
				if (verbose) System.out.println("  ++ skip google");
				return;
			}
			URL url = null;
			String host = null;
			if (verbose) System.out.println(
					"  ++ trovato link: " + link + 
					"\n   | contesto: " + contesto.toString());
			try {
				url = new URL(contesto, link);
				if (!url.getProtocol().equals("http")) return;
			}
			catch (Exception e) {
				if (verbose) System.out.println(
						"   | URL malformata: " + link);
				return;
			}
			
			try {
				host = url.getHost();
				if (hostNonRagg.search(host) != null) {
					if (verbose) System.out.println(
							"   | tralascio host marcato come non raggiungible: " + host);
					return;
				}
				String cUrl = (String)urlCache.search(nomeFile(url));
				if (cUrl != null) {
					elencoLink.add(cUrl);
					if (verbose) System.out.println("   | in cache: " + cUrl);
				}
				else {
					URLConnection c = url.openConnection();
					String contType = c.getContentType();
					if (contType == null) {
						if (verbose)
							System.out.println(
									"   | impossibile aprire file: " + nomeFile(url));
					}
					else {
						if (verbose)
							System.out.println(
									"   | file: " + nomeFile(url) +
									"\n   | content type: " + contType);
						if (contType.startsWith("text/html") ||
								contType.startsWith("text/plain")){
							elencoLink.add(url.toString());
							urlCache.insert(url.toString(), nomeFile(url));
						}
					}
				}
			}
			catch (Exception e) {
				if (host != null && hostNonRagg.search(host) == null)
					hostNonRagg.insert(host, host);
				if (verbose) System.out.println("   | *** errore accesso a: " + link);
			}		
		}
		
		/**
		 * Restituisce una rappresentazione testuale dell'indirizzo codificato in un URL. 
		 * 
		 * @param u l'URL di input
		 * @return la rappresentazione testuale dell'indirizzo codificato in <code>u</code>
		 */
		private String nomeFile(URL u){
			return u.getProtocol()+"://"+u.getAuthority()+u.getFile();
		}

		/**
		 * Restituice l'elenco dei link sinora incontrati durante il parsing di una pagina Web
		 * @return l'elenco dei link
		 */
		public List elencoLink() {
			return elencoLink;
		}
	}

	public static void main(String[] args) {		
		if (args.length > 0) {
			try {
				URL url = new URL(args[0]);
				EstrattoreLink es = new EstrattoreLink();
				List e = es.estraiLink(args[0], new InputStreamReader(url.openStream()));
				System.out.println("Elenco link: ");
				Iterator i = e.iterator();
				while (i.hasNext()) 
					System.out.println(i.next());
			} catch (Exception e) {
				System.out.println("Errore nell'accesso all'url: " + args[0]);
			}
		}
		else System.out.println("Sintassi: java EstrattoreLink url");
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