package classi;

public class Person {
	private String name;
    private String surname;
	private Cittadinanza cittadinanza;
	
	public Person(String name, String surname, Cittadinanza cittadinanza) {
		this.name         = name;
		this.surname      = surname;
		this.cittadinanza = cittadinanza;
	} 

	public Person(String name, String surname) {
		this.name         = name;
		this.surname      = surname;
		this.cittadinanza = Cittadinanza.Italiana;
	}
	
	public String getInfo() {
		return "Mi chiamo " + this.name + " " + this.surname + " e ho cittadinanza " + this.cittadinanza;
	}

	//overriding della funzione toString() della classe base Object
	@Override
	public String toString(){
		return getInfo();
	}
}
