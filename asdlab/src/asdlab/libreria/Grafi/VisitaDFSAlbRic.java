package asdlab.libreria.Grafi;

import java.util.Iterator;
import asdlab.libreria.Alberi.*;


/**
 * La classe <code>VisitaDFSAlbRic</code> implementa un algoritmo ricorsivo di visita in profondit&agrave;
 * di un grafo <code>G</code> (depth-first search, DFS) a partire da un nodo sorgente <code>s</code>. 
 * La classe <code>VisitaDFS</code> implementa l'interfaccia
 * <code>VisitaAlb</code> definendo un metodo, <code>calcolaAlbero</code>,
 * che, una volta invocato, calcola l'albero dei nodi incontrati
 * durante la visita DFS del grafo <code>G</code> a partire dal nodo <code>s</code>. 
 * L'operazione di visita vera e propria &egrave; implementata dal metodo ricorsivo
 * <code>calcolaRic</code> che utilizza un opportuno array, <code>nodiAlb</code>, passato
 * come parametro da input per marcare i nodi del grafo che sono stati gi&agrave; visitati.
 * Il risultato della visita DFS viene mantenuto in un albero, <code>alb</code>,
 * anch'essa passato come parametro nell'esecuzione ricorsiva di <code>calcolaRic</code>.
 */
public class VisitaDFSAlbRic implements VisitaAlb {

	/**
	 * Calcola e restituisce l'albero risultante dalla visita DFS del grafo di input <code>g</code>,
	 * contenente n nodi ed m archi, a partire dal nodo <code>s</code> (<font color=red>Tempo O(n+m)</font>).
	 * 
	 * @param g il grafo da visitare
	 * @param s il nodo di partenza della visita
	 * @return l'albero dei nodi esplorati durante la visita DFS
	 */	
	public Albero calcolaAlbero(Grafo g, Nodo s){
		Albero alb = new AlberoPFFS();
		Nodo[] nodiAlb = new Nodo[g.numNodi()];
		nodiAlb[g.indice(s)] = alb.aggiungiRadice(g.indice(s));
		calcolaRic(g, s, nodiAlb, alb);
		return alb;
	}

	/**
	 * Opera una visita DFS ricorsiva del nodo <code>u</code> appartenente
	 * al grafo G. 
	 *   
	 * @param g il grafo da visitare
	 * @param u il nodo da visitare
	 * @param nodiAlb array utilizzato per marcare i nodi gi&agrave; visitati e determinare il nodo dell'albero ad essi associato
	 * @param alb l'albero della visita DFS relativa ai nodi sinora visitati
	 */
	private void calcolaRic(Grafo g, Nodo u, Nodo[] nodiAlb, Albero alb){
		int iu = g.indice(u);
		Iterator archi = g.archiUscenti(u).iterator();
		while (archi.hasNext()){
			Arco a = (Arco) archi.next();
			int iv = g.indice(a.dest);
			if (nodiAlb[iv] != null) continue;
			nodiAlb[iv] = alb.aggiungiFiglio(nodiAlb[iu], iv);
			calcolaRic(g, a.dest, nodiAlb, alb);
		}
	}	
}
