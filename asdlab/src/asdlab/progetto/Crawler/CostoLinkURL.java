package asdlab.progetto.Crawler;

import java.net.URL;

/**
 * La classe <code>CostoLinkURL</code> associa all'arco di un grafo
 * un costo funzione del grado di correlazione esistente tra gli 
 * indirizzi Web (URL) delle pagine corrispondenti ai due estremi dell'arco. 
 * La funzione opera innanzitutto assegnando un valore alto (1000)
 * al peso di un arco che connette pagine sotto domini diversi,
 * e una misura di "distanza" tra pagine sullo stesso server altrimenti.
 * La distanza &egrave; calcolata come il quadrato del numero
 * di "salite" pi&ugrave; il numero di "discese" nel file system
 * per arrivare da una pagina all'altra spostandosi verticalmente
 * fra directory adiacenti. <br>Si noti che il costo di un arco fra
 * due documenti nella stessa directory &egrave; minimo, mentre il costo
 * aumenta all'aumentare della loro distanza nell'albero delle directory. 
 *
 */

public class CostoLinkURL implements CostoLink {
	
	/**
	 * Valore di costo utilizzato per gli archi che connettono pagine di domini diversi
	 */
	private final static int penalita = 1000; 
	
	
	/**
	 * Restituisce il costo associato all'arco <code>(x,y)</code>.
	 * Il costo viene calcolato in funzione del grado di correlazione
	 * tra gli URL corrispondenti ad <code>x</code> ed <code>y</code>.
	 * 
	 * @param x l'URL della prima pagina Web 
	 * @param y l'URL della seconda pagina Web
	 * @return il costo associato all'arco <code>(x,y)</code>
	 */
	public double costo(Object x, Object y) {
		URL url1, url2;
		try {
			url1 = new URL((String)x);
			url2 = new URL((String)y);
		}
		catch (Exception e) { return -1; }
		if (!url1.getHost().equals(url2.getHost())) return penalita;
		String str1 = url1.getPath(), str2 = url2.getPath();
		int pref = prefissoPiuLungo(str1, str2);
		str1 = str1.substring(pref); str2 = str2.substring(pref);
		double costo = Math.pow(str1.split("/").length + 
					   	  	    str2.split("/").length - 2, 2);
		return costo;
	}
	
	/**
	 * Determina la lunghezza del pi&ugrave; lungo prefisso comune a
	 * due stringhe <code>a</code> e <code>b</code>.
	 * 
	 * @param a la prima stringa
	 * @param b la seconda stringa
	 * @return il pi&ugrave; lungo prefisso comune ad <code>a</code> e <code>b</code>
	 */
	private int prefissoPiuLungo(String a, String b){
		int i, maxLen = Math.min(a.length(), b.length());
		for (i = 0; i < maxLen; i++)
			if (a.charAt(i) != b.charAt(i)) break;
		return i;
	}
}
