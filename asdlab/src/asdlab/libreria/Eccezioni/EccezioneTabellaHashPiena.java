package asdlab.libreria.Eccezioni;

public class EccezioneTabellaHashPiena extends RuntimeException {

	public EccezioneTabellaHashPiena(String messaggioErrore) {
		super(messaggioErrore);
	}

	public EccezioneTabellaHashPiena() {
	}
}
