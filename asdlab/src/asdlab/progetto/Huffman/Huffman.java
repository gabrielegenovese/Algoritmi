package asdlab.progetto.Huffman;

import java.io.*;
import java.util.*;
import asdlab.libreria.Alberi.*;

/* ============================================================================
 *  $RCSfile: Huffman.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/10 15:34:46 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.23 $
 */

/**
 * La classe <code>Huffman</code> gestisce sia la compressione che la decompressione di file di testo
 * mediante codici di Huffman. La compressione di un file &egrave; accessibile mediante il metodo
 * statico <code>comprimi</code>, la decompressione di un file &egrave; accessibile mediante il metodo
 * statico <code>decomprimi</code>. <br>
 * La classe opera mantenendo una istanza di albero
 * dei codici di Huffman, rappresentato utilizzando il tipo di dato <code>AlberoBin</code>. Il formato
 * dei file di input supportato &egrave; quello ASCII ad 8 bit. Le codifiche associate a ciascun carattere
 * vengono internamente memorizzate tramite un array che indica, per ciascun carattere, la sequenza
 * di bit che lo codifica. <br>Il formato dei file compressi
 * generati dalla classe <code>Huffman</code> prevede la presenza di due sezioni. Nella prima sezione si memorizza
 * una codifica dell'albero di Huffman utilizzato per la compressione di quel particolare file
 * ed ottenuta mediante una visita in profondit&agrave; dello stesso albero. La seconda sezione
 * del file ospita la rappresentazione compressa del file originale. Le operazioni di scrittura e di
 * lettura di file compressi vengono realizzate operando su singoli bit mediante le classi {@link LettoreBit} e {@link ScrittoreBit}.
 */

public class Huffman {

	/**
	 * Comprime un file di testo indicato da input utilizzando i codici di Huffman. L'algoritmo
	 * di compressione opera in pi&ugrave; passi:
	 * <ul>
	 * <li> Si determinano, tramite il metodo <code>calcolaFrequenze</code>, le frequenze con le quali ciascun carattere ricorre nel file di input</li>
	 * <li> Si costruisce, tramite il metodo <code>costruisciAlbero</code>, l'albero dei codici di huffman associato alle frequenze precedentemente
	 * calcolate </li>
	 * <li> Si genera, tramite il metodo <code>generaCodifica</code>, la codifica associata all'albero di Huffman precedentemente calcolato </li>
	 * <li> Si genera in output, tramite il metodo <code>scriviOutput</code>, il file compresso in accordo alla codifica precedentemente determinata </li>
	 * </ul>
	 * 
	 * @param in il nome del file da comprimere
	 * @param out il nome del file compresso da generare
	 * @throws IOException se sono stati riscontrati errori nell'apertura nel file da comprimere o nella creazione
	 * del file compresso
	 */
	public static void comprimi(String in, String out) throws IOException {
		int[] freq = calcolaFrequenze(in);
		AlberoBin alb = costruisciAlbero(freq);
		String[] codifica = generaCodifica(alb);
		scriviOutput(in, out, alb, codifica);
	}

	/**
	 * Comprime un file di input utilizzando una codifica precedentemente determinata. Il metodo
	 * opera memorizzando sul file di destinazione dapprima una rappresentazione dell'albero dei
	 * codici di huffman, mediante il metodo <code>salvaAlberoRic</code>. Successivamente, legge
	 * carattere per carattere il file di input e, tramite la classe <code>ScrittoreBit</code>,
	 * lo trascrive nel file di destinazione in base alla codifica precedentemente indicata. 
	 * 
	 * @param in il nome del file da comprimere
	 * @param out il nome del file compresso
	 * @param alb l'albero dei codici da utilizzare per la compressione del file 
	 * @param codifica la codifica da utilizzare per la compressione del file 
	 * @throws IOException se sono stati riscontrati errori nell'apertura nel file da comprimere o nella creazione
	 * del file compresso
	 */
	private static void scriviOutput(
		String in, String out, AlberoBin alb, String[] codifica) throws IOException {

		InputStream is = new FileInputStream(in);
		OutputStream os = new FileOutputStream(out);
		
		salvaAlberoRic(alb, alb.radice(), os);
		os.write('2');

		ScrittoreBit sb = new ScrittoreBit(os);

		for (int c; (c = is.read()) != -1;) {
			if (c > 256) continue;
			sb.scriviParola(codifica[c]);
		}

		sb.chiudi();
		os.close();
	}

	/**
	 * Decomprime un file di testo indicato da input utilizzando i codici di Huffman. L'algoritmo
	 * di decompressione opera ricostruendo, tramite il metodo <code>caricaAlbero</code>, l'albero dei codici di Huffman
	 * utilizzato per la compressione originale del file. In seguito, si adopera la classe <code>LettoreBit</code>
	 * per leggere, un bit pe volta, il contenuto del file compresso. I bit letti dal file compresso
	 * vengono utilizzati per visitare l'albero di Huffman. Ogni qual volta si raggiunge una foglia dell'albero,
	 * la visita si arresta, il rispettivo carattere viene scritto nel file di output e si riposiziona
	 * il puntatore della visita alla radice dell'albero stesso. 
	 * 
	 * 
	 * @param in il nome del file da decomprimere
	 * @param out il nome del file risultante dalla decompressione 
	 * @throws IOException se sono stati riscontrati errori nell'apertura nel file compresso o nella creazione
	 * del file decompresso
	 */
	public static void decomprimi(String in, String out) throws IOException {
		InputStream is = new FileInputStream(in);
		OutputStream os = new FileOutputStream(out);
		
		AlberoBin alb = Huffman.caricaAlbero(is);
		LettoreBit lb = new LettoreBit(is);
		Nodo nodo = alb.radice();

		for(int bit; (bit = lb.leggiBit()) != -1; ){
			nodo = (bit == 0) ? alb.sin(nodo) : alb.des(nodo); 
			if (alb.grado(nodo) == 0) {
				os.write(((InfoHuffman) nodo.info).carattere);
				nodo = alb.radice();
			}
		}
		os.close();
	}

	/**
	 * Determina le frequenze con le quali ciascun carattere compare in un file di input. 
	 * Le frequenze sono determinate leggendo sequenzialmente l'intero file 
	 * e conteggiando le occorrenze di ciascun carattere.
	 * 
	 * @param in il nome del file di riferimento
	 * @return l'array delle frequenze di ciascun carattere
	 * @throws IOException se sono stati riscontrati errori durante l'apertura del file di input
	 */
	protected static int[] calcolaFrequenze(String in) throws IOException {
		int[] freq = new int[256];
		InputStream is = new FileInputStream(in);
		for (int c; (c = is.read()) != -1;)
			if (c < 256) freq[c]++;
		is.close();
		return freq;
	}

	/**
	 * Costruisce un albero dei codici di Huffman a partire da un array contenente le frequenze 	
	 * con le quali ciascun carattere si presenta in un dato file. Il metodo opera
	 * associando dapprima ad ogni carattere un albero binario
	 * composto di un solo nodo e rappresentato mediante la classe <code>AlberoBinPF</code>.
	 * Successivamente, gli alberi vengono fusi in accordo all'algoritmo di costruzione dei
	 * codici di Huffman ed utilizzando il metodo <code>estraiMinimo</code>. Le informazioni relative alle frequenze di ciascun carattere
	 * vengono incaspulate nei nodi dell'albero mediante la classe <code>InfoHuffman</code>.
	 * 
	 * @param freq l'array delle frequenze in base al quale costruire l'albero
	 * @return l'albero dei codici di Huffman associato a <code>freq</code>
	 */
	protected static AlberoBin costruisciAlbero(int[] freq) {
		AlberoBin alb, min1, min2;
		InfoHuffman info;
		LinkedList foresta = new LinkedList();

		for (int i = 0; i < freq.length; i++)
			if (freq[i] > 0) {
				info = new InfoHuffman(freq[i], (char) i);
				alb = new AlberoBinPF(info);
				foresta.add(alb);
			}

		while (foresta.size() > 1) {
			min1 = estraiMinimo(foresta);
			min2 = estraiMinimo(foresta);
			info = new InfoHuffman(((InfoHuffman) min1.radice().info).freq
					+ ((InfoHuffman) min2.radice().info).freq);
			alb = new AlberoBinPF(info);
			alb.innestaSin(alb.radice(), min1);
			alb.innestaDes(alb.radice(), min2);
			foresta.add(alb);
		}
		return (AlberoBin) foresta.getFirst();
	}

	/**
	 * Data una foresta di alberi, estrae l'albero la cui radice ha
	 * frequenza minima. 
	 * 
	 * @param foresta la foresta da cui estrarre l'albero la cui radice ha frequenza minima
	 * @return l'albero la cui radice ha frequenza minima
	 */
	private static AlberoBin estraiMinimo(List foresta) {
		if (foresta.size() == 0)
			return null;

		
		Iterator alberi = foresta.iterator();
		AlberoBin albMin = (AlberoBin) alberi.next();

		while(alberi.hasNext()){
			AlberoBin albero = (AlberoBin) alberi.next();
			InfoHuffman x = (InfoHuffman) albero.info(albero.radice());
			if (x.compareTo(albMin.info(albMin.radice())) < 0) {
				albMin = albero;
			}
			
		}
		foresta.remove(albMin);
		return albMin;
	}

	
	/**
	 * Genera la codifica associata ad un albero dei codici di Huffman. La codifica
	 * viene generata richiamando il metodo ricorsivo <code>generaCodificaRic</code>.
	 * 
	 * @param alb l'albero di cui generare la corrispondente codifica
	 * @return la codifica associata ad <code>alb</code>
	 */
	protected static String[] generaCodifica(AlberoBin alb) {
		String[] codifiche = new String[256];
		Huffman.generaCodificaRic(alb, alb.radice(), new StringBuffer(" "), -1, codifiche);
		return codifiche;
	}

	/**
	 * Genera ricorsivamente la codifica associata ad un albero dei codici di Huffman. Il metodo
	 * opera visitando ricorsivamente l'albero dei codici ed accumulando in un oggetto di tipo <code>StringBuffer</code>
	 * la sequenza di bit corrispondente alla codifica della posizione corrente nell'albero. 
	 * La ricorsione si arresta in corrispondenza della visita delle foglie dell'albero. In tale
	 * circostanza, si memorizza nell'array delle codifiche indicato da input la codifica del
	 * carattere associato al nodo foglio attualmente visitato. 
	 * 
	 * @param alb l'albero di cui generare la corrispondente codifica
	 * @param nodo il nodo da visitare nel processo di generazione delle codifiche
	 * @param bitSet la sequenza di bit corrispondente alla codifica della posizione corrente in <code>alb</code>
	 * @param l la lunghezza della codifica corrispondente al nodo corrente (decrementata di 1)
	 * @param codifica l'array delle codifiche
	 */
	protected static void generaCodificaRic(AlberoBin alb, Nodo nodo,
			StringBuffer bitSet, int l, String[] codifica) {

		if (alb.grado(nodo) == 0) {
			InfoHuffman record = (InfoHuffman) nodo.info;
			codifica[record.carattere] = bitSet.substring(0, l + 1);
			return;
		}

		if ((++l) >= bitSet.length())
			bitSet.setLength(bitSet.length() * 2);
		
		bitSet.setCharAt(l, (char) 0);
		generaCodificaRic(alb, alb.sin(nodo), bitSet, l, codifica);
		bitSet.setCharAt(l, (char) 1);
		generaCodificaRic(alb, alb.des(nodo), bitSet, l, codifica);

	}

	/**
	 * Memorizza ricorsivamente su un file di output un albero di codici di Huffman.
	 * L'albero viene memorizzato attraverso una visita in profondit&agrave; dello stesso.
	 * Ad ogni esecuzione ricorsiva di <code>salvaAlberoRic</code>, si memorizza nel file di output
	 * il carattere:
	 * <ul>
	 * <li>0, nel caso si stia per visitare il sottoalbero sinistro del nodo corrente</li>
	 * <li>1, nel caso si stia per visitare il sottoalbero destro del nodo corrente</li>
	 * <li> il carattere incapsulato nel nodo corrente, preceduto da un carattere #, nel caso questi sia un nodo foglia </li>
	 * </ul>
	 *   
	 * @param alb l'albero da memorizzare
	 * @param nodo il nodo correntemente visitato
	 * @param out il file su cui memorizzare l'albero
	 * @throws IOException se sono stati riscontrati errori nella creazione
	 * o nell'accesso del file che conterr&agrave; l'albero
	 */
	protected static void salvaAlberoRic(AlberoBin alb, Nodo nodo,
			OutputStream out) throws IOException {

		if (alb.grado(nodo) == 0) {
			out.write('#');
			out.write(((InfoHuffman) nodo.info).carattere);
			return;
		}

		out.write('0');
		salvaAlberoRic(alb, alb.sin(nodo), out);

		out.write('1');
		salvaAlberoRic(alb, alb.des(nodo), out);
	}

	/**
	 * Carica in memoria un albero di codici di Huffman, precedentemente memorizzato su file tramite il metodo <code>salvaAlberoRic</code>.
	 * Il caricamento avviene leggendo la sequenza di caratteri da file, risultato di una precedente visita
	 * in profondit&agrave; dell'albero, e ricostruendo la struttura dell'albero di conseguenza. 
	 * 
	 * @param in il file contenente l'albero da caricare
	 * @return l'albero dei codici caricato da file
	 * @throws IOException se sono stati riscontrati errori nella lettura del file contenente l'albero
	 */
	protected static AlberoBin caricaAlbero(InputStream in) throws IOException {
		AlberoBin albero = new AlberoBinPF(new InfoHuffman(0));
		Nodo nodo = albero.radice();

		for (char c = (char) in.read(); c != '2'; c = (char) in.read()) {
			if (c == '0') {
				if (albero.sin(nodo) == null)
					nodo = albero.aggiungiFiglioSin(nodo, new InfoHuffman(0));
				else
					nodo = albero.sin(nodo);
			} else if (c == '1') {
				if (albero.des(nodo) == null)
					nodo = albero.aggiungiFiglioDes(nodo, new InfoHuffman(0));
				else
					nodo = albero.des(nodo);
			} else {
				((InfoHuffman) nodo.info).carattere = (char) in.read();
				
				do {
					nodo = albero.padre(nodo);
				} while (nodo != null && albero.des(nodo) != null);
			}
		}

		return albero;
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