import java.util.ArrayList;
import java.util.Random;

public class MyCatCafe implements CatCafe{
    public double balance;
    public int rua=0;
    ArrayList<Cat> catList= new ArrayList<>();
    ArrayList<Customer> customerList= new ArrayList<>();

    @Override
    public void BuyCat(Cat cat)throws InsufficientBalanceException{
         balance-=cat.money;
         if(balance<0)
         {
             throw new InsufficientBalanceException("余额不足！购买失败！");
         }
         else {
             catList.add(cat);
             System.out.println("购买成功！");
         }
        }

    @Override
    public void serveCustomer(Customer customer)throws CatNotFoundException {

        customerList.add(customer);
        balance+=15;
        rua+=customer.rua;
        Random random=new Random();

        if(customerList.size()<=catList.size()) {
            for(int i=0;i<customer.rua;i++) {
                System.out.print("rua的猫猫信息 ： ");
                System.out.println(catList.get(random.nextInt(catList.size())));
            }
        }
        else throw new CatNotFoundException("猫猫不够惹");//理解是客人rua到歇业，当进入店里的客人数量大于猫猫数量是抛出异常
    }

    @Override
    public void Close() {
        int profit;//利润
        System.out.println("今日歇业时间到了");
        System.out.println("今日招待客人信息 ： \n"+customerList);
        profit=15*rua;
        System.out.println("今日利润 ： "+profit);
    }

}
