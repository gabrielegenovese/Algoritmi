package asdlab.libreria.Eccezioni;

public class EccezioneInputNonValido extends RuntimeException {

	public EccezioneInputNonValido(String messaggioErrore){
		super(messaggioErrore);
	}

}
