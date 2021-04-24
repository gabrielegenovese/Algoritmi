package classi;

public interface BankAccount {
    /**
     * Method to deposit money on thr bank account
     * 
     * @param amount amount to deposit on the bank account
     */
    public boolean deposit(double amount);
    /**
     * Method to withdraw money from the bank account
     * @param amount amount to withdraw on the bank account
     */
    public boolean withdraw(double amount);
    
    @Override
    public String toString();

}