package asdlab.libreria.Eccezioni;

public class EccezioneStrutturaVuota extends RuntimeException {

	public EccezioneStrutturaVuota(String messaggioErrore) {
		super(messaggioErrore);
	}

	public EccezioneStrutturaVuota() {
	}
}
