public interface CatCafe {

    void BuyCat(Cat cat)throws InsufficientBalanceException;

    void serveCustomer(Customer customer)throws CatNotFoundException;

    void Close();
}
