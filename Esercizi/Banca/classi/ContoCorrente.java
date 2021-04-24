package classi;

public class ContoCorrente implements BankAccount {

    private double saldo;
    
    public ContoCorrente() {
        saldo = 0;
    }

    public ContoCorrente(double saldo) {
        if(saldo < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        } else {
            this.saldo = saldo;
        }
    }

    // Method from BankAccount interface
    public boolean deposit(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        } else {
            saldo += amount;
            return true;
        }
    }

    // Method from BankAccount interface
    public boolean withdraw(double amount) {
        if(amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");

        if(saldo - amount > 0) {
            saldo -= amount;
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Saldo attuale: " + this.saldo;
    }
}