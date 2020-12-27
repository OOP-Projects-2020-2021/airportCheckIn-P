package oop.project;
import java.sql.*;

public class MySqlCon {
    private static Connection con;
    public static void mySqlConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/airport_check_in", "root", "");
            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from flight");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void insertIntoDB(String statement){
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(statement);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
    public static int Query() {
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM identity_card ORDER BY id_id DESC LIMIT 1");

            return rs.getInt(1);
        }catch(Exception e) {
            System.out.println(e);
            return -1;
        }

    }
    public static int getLastIndex(String column, String statement){
        try{
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2.executeQuery(statement);
            System.out.println(rs.getInt(1));
            return 1;
        }catch(Exception e) {
            System.out.println(e);
            return -1;
        }
    }


    public static void mySqlDisconnect(){
        try {
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
