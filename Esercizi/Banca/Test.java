import classi.*;

public class Test {
	public static void main(String[] args) {

        ContoCorrente myConto = new ContoCorrente();

        System.out.println(myConto.toString());
        myConto.deposit(5000);
        System.out.println(myConto.toString());
        myConto.withdraw(-1204.18);
        System.out.println(myConto.toString());
    }
}