public abstract class Cat {
    protected String name;
    protected int age;
    protected String sex;
    protected double money;

    public Cat(String name, int age, String sex){
        this.name=name;
        this.age=age;
        this.sex=sex;
    }
    public abstract String toString();

}

class OrangeCat extends Cat{
    protected boolean isFat;
    public  OrangeCat(String name,int age,String sex, boolean isFat){
        super(name,age,sex);
        super.money=200;
        this.isFat=isFat;
    }
    @Override
    public String toString() {
        return "猫猫名字是 ： "+name+", 年龄 ： "+age+"， 性别 ： "+sex+"， 胖不胖 ： "+isFat;
    }

}

class BlackCat extends Cat{
    public  BlackCat(String name,int age,String sex){
        super(name,age,sex);
        super.money=350;
    }
    @Override
    public String toString() {
        return "猫猫名字是 ： "+name+", 年龄 ： "+age+"， 性别 ： "+sex;
    }
}
