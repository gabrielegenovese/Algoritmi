package asdlab.progetto.Crawler;

import java.io.*;
import java.net.URL;
import asdlab.progetto.Huffman.*;

/* ============================================================================
 *  $RCSfile: ArchivioDoc.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:37 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.8 $
 */

/**
 * La classe <code>ArchivioDoc</code> consente l'acquisizione del contenuto di pagine Web. 
 * La classe utilizza le primitive messe a disposizione dalla classe standard
 * <code>URL</code> per acquisire dal Web il contenuto di una pagina di cui
 * si conoscere l'indirizzo ed archiviarlo su disco. Le pagine acquisite
 * sono memorizzate in una directory il cui nome &egrave; indicato all'atto
 * dell'istanziazione di <code>ArchivioDoc</code>. Contestualmente
 * alla memorizzazione delle singole pagine, la classe compila un file
 * di indice nel quale sono indicati gli URL dei documenti scaricati ed il nome
 * del file corrispondente. La classe supporta
 * la scrittura di file compressi mediante le primitive offerte
 * dalla classe {@link Huffman}. 
 */

public class ArchivioDoc {

	/**
	 * Abilita la modalit&agrave; debug
	 */
	private static final boolean debug = true;
	
	/**
	 * Nome del file utilizzato per mantenere la corrispondenza tra i nomi dei file contenenti le pagine ed i rispettivi URL
	 */
	private static final String fileElenco = "crawl.txt";
	
	/**
	 * Valore di timeout utilizzato nelle connessioni verso i server Web per l'acquisizione delle pagine
	 */
	protected final static String timeout = "5000";
	
	/**
	 * Directory di archiviazione dei file
	 */
	private String archiveDir;
	
	/**
	 * Riferimento al file utilizzato per mantenere la corrispondenza tra nomi file ed URL
	 */
	private PrintStream elenco;
	
	/**
	 * Indica se abilitare o meno la compressione delle pagine acquisite
	 */
	private boolean compresso;

	
	/**
	 * Crea una nuova istanza di <code>ArchivioDoc</code>. Contestualmente all'allocazione,
	 * si crea il file <code>fileElenco</code> utilizzato per mantenere la corrispondenza
	 * tra nomi file ed URL. 
	 * 
	 * @param dirArchiv la directory in cui memorizzare le pagine acquisite
	 * @param compr indica se abilitare o meno la compressione delle pagine acquisite
	 */
	public ArchivioDoc(String dirArchiv, boolean compr) {
		if (dirArchiv.endsWith("/"))
			archiveDir = dirArchiv;
		else
			archiveDir = dirArchiv + "/";
		
		compresso = compr;
		try {
			elenco = new PrintStream(
					 new FileOutputStream(new File(dirArchiv+fileElenco)));
		}
		catch (Exception e) {
			System.out.println("Errore creazione file elenco: " + e.getMessage());			
		}
	}
		
	/**
	 * Acquisisce dal Web una copia della pagina il cui URL &egrave; indicato da input
	 * e la memorizza su file. Il contenuto della pagina viene caricato in memoria
	 * sfruttando le primitive della classe <code>URL</code> e successivamente
	 * riversato su disco mediante la classe standard <code>FileWriter</code>.
	 * Nel caso la compressione sia abilitata, la pagina viene memorizzata su disco
	 * compressa e con estensione ".huf". Diversamente, viene memorizzata in chiaro
	 * con estensione ".html". Contestualmente alla memorizzazione della pagina,
	 * si aggiorna con la coppia (indirizzo,ID) il file <code>fileElenco</code>.
	 * 
	 * @param indirizzo l'URL della pagina Web da acquisire
	 * @param ID l'identificativo da utilizzare nella memorizzazione del file
	 * @return il contenuto della pagina Web acquisita
	 */
	public char[] archiviaDoc(String indirizzo, Integer ID) {

		URL url;
		char[] documento = null;
		String nomeFile = ID.toString() + ".html";
		String nomeFileComp = nomeFile + ".huf";

		System.setProperty("sun.net.client.defaultConnectTimeout", timeout);
		System.setProperty("sun.net.client.defaultReadTimeout", timeout);

		try {
			url = new URL(indirizzo);
			if (debug) System.out.println(">> Download della pagina: " + url.toString());
			InputStream is = url.openStream();

			int c;
			StringBuffer sb = new StringBuffer();
			while (((c = is.read()) != -1)) sb.append((char) c);

			documento = new char[sb.length()];
			sb.getChars(0, sb.length(), documento, 0);
		}

		catch (Exception e) {
			System.out.println("   Errore nel download della pagina: " + e.getMessage());
			return documento;
		}

		try {
			FileWriter f = new FileWriter(archiveDir + nomeFile);
			f.write(documento);
			f.close();
		}

		catch (IOException e) {
			System.out.println("   Errore nel salvataggio della pagina");
			return documento;
		}

		if (compresso) {
			try {
				Huffman.comprimi(archiveDir + nomeFile, archiveDir + nomeFileComp);
				new File(archiveDir + nomeFile).delete();
			}
			catch (Exception e){
				System.out.println("   Errore nella compressione della pagina");
			}
		}

		elenco.println(String.format("%1$-7d %2$s", ID, url.toString()));

		return documento;
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
