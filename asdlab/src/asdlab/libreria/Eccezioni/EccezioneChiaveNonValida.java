package asdlab.libreria.Eccezioni;

public class EccezioneChiaveNonValida extends RuntimeException {

	public EccezioneChiaveNonValida(String messaggioErrore){
		super(messaggioErrore);
	}

	public EccezioneChiaveNonValida() {
	}
}
