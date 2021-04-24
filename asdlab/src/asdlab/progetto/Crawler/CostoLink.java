package asdlab.progetto.Crawler;

/**
 * L'interfaccia <code>CostoLink</code> definisce un metodo standard per associare 
 * un costo ad un arco di un grafo. Essa fornisce un metodo, <code>costo</code>, che 
 * date le informazioni associate agli estremi di un arco di un grafo, calcola
 * il costo associato a quell'arco.
 *
 */
public interface CostoLink {
	/**
	 * Restituisce il costo associato all'arco <code>(x,y)</code>.
	 * @param x il primo estremo dell'arco
	 * @param y il secondo estremo dell'arco
	 * @return il costo associato all'arco <code>(x,y)</code>
	 */
	public double costo(Object x, Object y);
}
