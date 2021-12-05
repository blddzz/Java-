import java.time.LocalTime;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyCatCafe mine=new MyCatCafe();
        LocalTime endTime=LocalTime.of(16,31,0);//歇业时间
        System.out.print("请输入猫咖的资金 ：");
        mine.balance=scanner.nextInt();//输入猫咖资金
        Cat a=new OrangeCat("小橘",2,"male",false);
        Cat b=new OrangeCat("大橘",5,"male",true);
        Cat c=new BlackCat("小黑",3,"female");
        try {
            mine.BuyCat(a);
            mine.BuyCat(b);
            mine.BuyCat(c);//初始化猫咖猫猫列表
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }

        while (true){
                Customer customer = new Customer();
                if(customer.time.isAfter(endTime))
                {
                    mine.Close();
                    break;
                }
                else {
                    customer.time = LocalTime.now();
                    System.out.print("顾客信息 : 名字 ： ");
                    customer.name = scanner.next();
                    System.out.print(" 想要rua猫的次数 : ");
                    customer.rua = scanner.nextInt();
                    try {
                        mine.serveCustomer(customer);
                    } catch (CatNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }

        }

}
