package asdlab.libreria.Eccezioni;

public class EccezioneNodoNonValido extends RuntimeException {

	public EccezioneNodoNonValido(String messaggioErrore) {
		super(messaggioErrore);
	}

	public EccezioneNodoNonValido() {
	}

}
