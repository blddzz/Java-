import java.time.LocalTime;

public class Customer{
    public String name;
    public int rua;
    LocalTime time= LocalTime.now();

    @Override
    public String toString() {
        return "顾客名字 ："+name+"\n"+"想要rua猫的次数 ： "+rua+"\n"+"到店时间 ："+time+"\n";
    }
}