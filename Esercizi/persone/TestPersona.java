import classi.*;

public class TestPersona {
	public static void main(String[] args) {
		Person p1   = new Person("Mario","Rossi");
		Person p2 = new Person("Giuseppe","Bianchi", Cittadinanza.ExtraEU);
		
		System.out.println(p1);				//chiama di default la funzione toString()
		System.out.println(p1.toString());
    	System.out.println(p2.toString());
		System.out.println(p2.hashCode());
  	}
}