import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Scanner scanner=new Scanner(System.in);
        int choose;//用来输入选择的功能

        System.out.println("        欢迎使用订单管理系统");
        out:while (true) {
            System.out.println("        1.查询订单信息");
            System.out.println("        2.添加订单信息");
            System.out.println("        3.修改订单信息");
            System.out.println("        4.删除订单信息");
            System.out.println("        5.添加商品信息");
            System.out.println("        6.修改商品信息");
            System.out.println("        7.删除商品信息");
            System.out.println("        8.查询商品信息");
            System.out.println("        9.退出订单系统");
            choose = scanner.nextInt();
            switch (choose) {
                case 1://实现查询订单功能
                    try {
                        conn=JdbcUtils.getConnection();

                        String query="SELECT `order_id`,`order management system`.`goods`.`goods_id`,`goods_name`,`goods_price`,`order_time` FROM `order management system`.`order` \n" +
                                "INNER JOIN `order management system`.`goods`  \n" +
                                "ON `order management system`.`order`.`goods_id`=`order management system`.`goods`.`goods_id`" +
                                "Order by `order_id` ASC";
                        JdbcUtils.query_order(query,conn,ps,rs);
                        //用工具类中的联表查询方法实现对数据库中订单表和商品表的查询汇总
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 2://实现添加订单功能
                    try {
                        conn = JdbcUtils.getConnection();
                        conn.setAutoCommit(false);//开启事务

                        System.out.print("添加订单编号为 ：");
                        int add_oid=scanner.nextInt();
                        add_oid=JdbcUtils.add_order_id_query(conn,ps,rs,add_oid);//输入要添加的订单编号并查询是否已经存在，只有输入不存在的订单号才能添加

                        System.out.print("添加的商品编号 ： ");
                        int add_gid=scanner.nextInt();
                        add_gid=JdbcUtils.add_update_goods_id_query(conn,ps,rs,add_gid);//输入商品编号并查询是否已经存在，只有输入存在的商品号才能添加

                        Object[] ad={add_oid,add_gid,new java.sql.Date(new Date().getTime())};//数组用来存要添加的订单编号，时间和商品编号

                        String add="INSERT INTO `order management system`.`order` (`order_id`,`goods_id`, `order_time`) VALUES (?,?,?)";
                        JdbcUtils.add(add,ad,conn,ps);//用工具类中的添加方法实现对数据库中数据的添加
                        conn.commit();//提交事务
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                                conn.rollback();//如果失败则回滚
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 3://实现修改订单功能
                    try {
                        conn = JdbcUtils.getConnection();
                        conn.setAutoCommit(false);//开启事务

                        System.out.print("修改的订单编号为 ：");
                        int update_oid=scanner.nextInt();
                        update_oid=JdbcUtils.del_upd_order_id_query(conn,ps,rs,update_oid);//输入要修改的订单编号并查询是否已经存在，只有输入存在的订单号才能修改

                        System.out.print("修改商品编号为 ：");
                        int update_gid=scanner.nextInt();
                        update_gid=JdbcUtils.add_update_goods_id_query(conn,ps,rs,update_gid);//输入商品编号并查询是否已经存在，只有输入存在的商品号才能修改

                        Object[] upd={update_gid,new java.sql.Date(new Date().getTime()),update_oid};//数组用来存要修改的订单编号，时间和商品编号

                        String update = "UPDATE `order management system`.`order` SET `goods_id` = ? , `order_time` = ? WHERE `order_id` = ?";
                        JdbcUtils.update_order(update,upd,conn,ps);//用工具类中的修改方法实现对数据库中数据的修改
                        conn.commit();//提交事务
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                                conn.rollback();//如果失败则回滚
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 4://实现删除订单功能
                    try {
                        conn = JdbcUtils.getConnection();
                        conn.setAutoCommit(false);//开启事务

                        System.out.print("请输入要删除的订单号 ： ");
                        int delete_id = scanner.nextInt();
                        delete_id=JdbcUtils.del_upd_order_id_query(conn,ps,rs,delete_id);//输入要删除的订单编号并查询是否已经存在，只有输入存在的订单号才能删除

                        String delete = "delete from `order management system`.`order` WHERE `order_id` = ? ";
                        JdbcUtils.delete(delete,delete_id, conn, ps);//用工具类中的删除方法实现对数据库中数据的删除
                        conn.commit();//提交事务
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                                conn.rollback();//如果失败则回滚
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 5:
                    try {
                        conn = JdbcUtils.getConnection();
                        conn.setAutoCommit(false);//开启事务

                        System.out.print("添加商品编号为 ：");
                        int add_gid=scanner.nextInt();
                        add_gid=JdbcUtils.add_goods_id_query(conn,ps,rs,add_gid);//输入要添加的商品编号并查询是否已经存在，只有输入不存在的订单号才能添加

                        System.out.print("添加的商品名称 ： ");
                        String name= scanner.next();//输入要添加商品的名称

                        System.out.print("添加的商品价格 ： ");
                        int add_pri=scanner.nextInt();//输入要添加商品的价格

                        Object[] ad={add_gid,name,add_pri};//数组用来存要添加的商品编号，名称和价格

                        String add="INSERT INTO `order management system`.`goods` (`goods_id`,`goods_name`, `goods_price`) VALUES (?,?,?)";
                        JdbcUtils.add(add,ad,conn,ps);//用工具类中的添加方法实现对数据库中数据的添加
                        conn.commit();//提交事务
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                                conn.rollback();//如果失败则回滚
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 6:
                    try {
                        conn = JdbcUtils.getConnection();
                        conn.setAutoCommit(false);//开启事务

                        System.out.print("修改的商品编号为 ：");
                        int update_gid=scanner.nextInt();
                        update_gid=JdbcUtils.add_update_goods_id_query(conn,ps,rs,update_gid);//输入要修改的商品编号并查询是否已经存在，只有输入存在的订单号才能修改

                        System.out.print("修改商品名称为 ： ");
                        String name=scanner.next();//修改商品名称

                        System.out.print("修改商品价格为 ：");
                        int update_pri=scanner.nextInt();//修改商品价格

                        Object[] upd={name,update_pri,update_gid};//数组用来存要修改的商品编号，名称和价格

                        String update = "UPDATE `order management system`.`goods` SET `goods_name` = ? , `goods_price` = ? WHERE `goods_id` = ?";
                        JdbcUtils.update_order(update,upd,conn,ps);//用工具类中的修改方法实现对数据库中数据的修改
                        conn.commit();//提交事务
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                                conn.rollback();//如果失败则回滚
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 7:
                    try {
                        conn = JdbcUtils.getConnection();
                        conn.setAutoCommit(false);//开启事务

                        System.out.print("请输入要删除的商品编号 ： ");
                        int delete_id = scanner.nextInt();
                        delete_id=JdbcUtils.add_update_goods_id_query(conn,ps,rs,delete_id);//输入要删除的订单编号并查询是否已经存在，只有输入存在的订单号才能删除

                        String delete = "delete from `order management system`.`goods` WHERE `goods_id` = ? ";
                        JdbcUtils.delete(delete,delete_id, conn, ps);//用工具类中的删除方法实现对数据库中数据的删除
                        conn.commit();//提交事务
                    } catch (SQLException e) {
                        try {
                            if (conn != null) {
                                conn.rollback();//如果失败则回滚
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 8:
                    try {
                        String query="SELECT * FROM `order management system`.`goods`" +
                                "ORDER BY `goods_id` ASC";
                        conn=JdbcUtils.getConnection();
                        JdbcUtils.query_goods(query,conn,ps,rs);//用工具类中的查询方法实现对数据库中商品表的查询

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------------------------------------------------");
                    break ;

                case 9://退出订单管理系统
                    System.out.println("Goodbye~~~~~~~~");
                    break out;

                default://选择了系统莫有的功能，对其进行提示
                    System.out.println("无此功能，请重新选择您的操作 ");
                    break ;
            }


        }
    }
}
