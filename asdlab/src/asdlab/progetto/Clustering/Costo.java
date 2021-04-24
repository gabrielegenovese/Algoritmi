package asdlab.progetto.Clustering;

/**
 * L'interfaccia <code>Costo</code> specifica la struttura
 * di una funzione generica per valutare la similarit&agrave; di una coppia
 * di documenti. L'interfaccia assume che le classi che la implementano dispongano
 * internamente di un insieme di documenti di cui determinare la similarit&agrave;
 * indicizzati in base ad un indice numerico.
 *
 */

public interface Costo {
	/**
	 * Valuta la similarit&agrave; esistente tra una coppia di documenti.
	 * 
	 * @param a l'indice del primo documento
	 * @param b l'indice del secondo documento
	 * @return il valore di similarit&agrave; esistente tra i documenti <code>a</code> e <code>b</code>
	 */
	public double costo(int a, int b);
}
