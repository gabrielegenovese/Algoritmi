package asdlab.libreria.Eccezioni;

public class EccezioneNodoEsistente extends RuntimeException {

	public EccezioneNodoEsistente(String messaggioErrore) {
		super(messaggioErrore);
	}
	
	public EccezioneNodoEsistente() {
	}
}
