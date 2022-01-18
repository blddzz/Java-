import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class JdbcUtils {//工具类：包含添加，修改，删除，查询等功能

    private static String url=null;
    private static String user_name=null;
    private static String password=null;
    static  {
         try {
             InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
             Properties properties = new Properties();
             properties.load(in);
             String driver = properties.getProperty("driver");
             url = properties.getProperty("url");
             user_name = properties.getProperty("user_name");
             password = properties.getProperty("password");
             Class.forName(driver);
         }catch (Exception e) {
             System.out.println("数据库连接失败");
         }

     }

    public static Connection getConnection() throws SQLException {//数据库连接

            return  DriverManager.getConnection(url, user_name, password);

    }


    public static void release(Connection conn, PreparedStatement st,ResultSet rs){//释放
         if(rs!=null) {
             try {
                 rs.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         if(st!=null) {
             try {
                 st.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         if(conn!=null) {
             try {
                 conn.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    }


    public static void update_order(String update, Object[] upd,Connection conn,PreparedStatement ps) {//修改信息
        try {
            ps = conn.prepareStatement(update);
            for (int i = 1; i <= upd.length; i++) {
                ps.setObject(i, upd[i - 1]);
            }
            int r = ps.executeUpdate();
            if(r>0){
                System.out.println("修改成功");
            }
        } catch (SQLException e) {
            System.out.println("修改失败");
        }finally {
            release(conn,ps,null);
        }
    }


    public static void add(String add,Object[] ad,Connection conn,PreparedStatement ps) {//添加
        try {

            ps = conn.prepareStatement(add);
            for (int i=1; i <=ad.length; i++) {
                ps.setObject(i, ad[i - 1]);
            }
            int r = ps.executeUpdate();
            if(r>0){
                System.out.println("添加成功");
            }
        } catch (SQLException e) {
            System.out.println("添加失败");
        } finally {
            release(conn, ps,null);
        }

    }


   public static void delete(String delete,int i,Connection conn,PreparedStatement ps){//删除
            try {
                ps=conn.prepareStatement(delete);

                    ps.setObject(1,i );
                int r=ps.executeUpdate();
                if(r>0){
                    System.out.println("删除成功");
                }
            } catch (SQLException e) {
                System.out.println("删除失败");
            }finally {
                release(conn,ps,null);
            }
    }

    //联表查询，可查看订单编号，商品信息订单时间
    public static void query_order(String query,Connection conn,PreparedStatement ps,ResultSet rs){
        try {
            ps= conn.prepareStatement(query);
            rs= ps.executeQuery();
            while (rs.next())
            {
                System.out.println("订单编号 ： " +rs.getString("order_id"));
                System.out.println("商品编号 ： " +rs.getString("goods_id"));
                System.out.println("商品名称 ： " +rs.getString("goods_name"));
                System.out.println("商品价格 ： " +rs.getString("goods_price"));
                System.out.println("订单时间 ： " +rs.getString("order_time"));
                System.out.println("-----------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("查询订单失败");
        }finally {
            release(conn,ps,rs);
        }
    }

    //在删除和修改订单之前对订单号进行查询，判断是否有该订单
    public static int del_upd_order_id_query(Connection conn,PreparedStatement ps,ResultSet rs,int update_oid){
            try {
                Scanner scanner = new Scanner(System.in);
                while (true){
                    String query_u="SELECT `order_id` FROM `order management system`.`order` WHERE `order_id`=?";
                    conn = JdbcUtils.getConnection();
                    ps=conn.prepareStatement(query_u);
                    ps.setObject(1,update_oid);
                    rs= ps.executeQuery();
                    if(!rs.next()){
                        System.out.print("该订单不存在，无需操作，请重新输入 ： ");
                        update_oid=scanner.nextInt();
                    }
                    else break;}
                return update_oid;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                release(conn,ps,rs);
            }
            return 0;
    }

    //在对订单进行添加前进行订单查询，保证不会有重复订单号
    public static int add_order_id_query(Connection conn,PreparedStatement ps,ResultSet rs,int update_oid){
        try {
            Scanner scanner = new Scanner(System.in);
            while (true){
                String query="SELECT `order_id` FROM `order management system`.`order` WHERE `order_id`=?";
                conn = JdbcUtils.getConnection();
                ps=conn.prepareStatement(query);
                ps.setObject(1,update_oid);
                rs= ps.executeQuery();
                if(rs.next()){
                    System.out.print("该订单编号已存在，无法添加，请重新输入 ： ");
                    update_oid=scanner.nextInt();
                }
                else break;}
            return update_oid;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release(conn,ps,rs);
        }
        return 0;
    }


    //在对订单进行添加和修改商品信息时，保证商品是商品表中存在的
    //在删除和修改商品之前对商品号进行查询，判断是否有该商品
    public static int add_update_goods_id_query(Connection conn,PreparedStatement ps,ResultSet rs,int update_gid){
        try {
            Scanner scanner = new Scanner(System.in);
            while (true){
                String query="SELECT `goods_id` FROM `order management system`.`goods` WHERE `goods_id`=?";
                conn = JdbcUtils.getConnection();
                ps=conn.prepareStatement(query);
                ps.setObject(1,update_gid);
                rs= ps.executeQuery();
                if(!rs.next()){
                    System.out.print("该商品不存在，无法操作，请重新输入 ： ");
                    update_gid=scanner.nextInt();
                }
                else break;}
            return update_gid;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release(conn,ps,rs);
        }
        return 0;
    }

    public static void query_goods(String query,Connection conn,PreparedStatement ps,ResultSet rs){//对商品表的查询
        try {
            ps= conn.prepareStatement(query);
            rs= ps.executeQuery();
            while (rs.next())
            {
                System.out.println("商品编号 ： " +rs.getString("goods_id"));
                System.out.println("商品名称 ： " +rs.getString("goods_name"));
                System.out.println("商品价格 ： " +rs.getString("goods_price"));
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("查询商品信息失败");
        }finally {
            release(conn,ps,rs);
        }
    }

    //在对商品进行添加前进行商品表查询，保证不会有重复商品号
    public static int add_goods_id_query(Connection conn,PreparedStatement ps,ResultSet rs,int update_oid){
        try {
            Scanner scanner = new Scanner(System.in);
            while (true){
                String query="SELECT `goods_id` FROM `order management system`.`goods` WHERE `goods_id`=?";
                conn = JdbcUtils.getConnection();
                ps=conn.prepareStatement(query);
                ps.setObject(1,update_oid);
                rs= ps.executeQuery();
                if(rs.next()){
                    System.out.print("该商品编号已存在，无法添加，请重新输入 ： ");
                    update_oid=scanner.nextInt();
                }
                else break;}
            return update_oid;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release(conn,ps,rs);
        }
        return 0;
    }

}

