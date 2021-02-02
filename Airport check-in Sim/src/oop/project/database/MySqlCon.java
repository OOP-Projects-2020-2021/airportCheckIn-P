package oop.project.database;
import java.sql.*;

public class MySqlCon {
    private static Connection con;
    public static void mySqlConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/airport_check_in", "root", "");

            /*Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from flight");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
*/
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void updateDB(String statement){
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(statement);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
    public static ResultSet Query(String statement) {
        try{
            Statement stmt = con.createStatement();
            return stmt.executeQuery(statement);
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }

    }
    public static int getLastIndex(String column, String statement){
        try{
            Statement stmt2 = con.createStatement();
            ResultSet rs = stmt2.executeQuery(statement);
            rs.next();
            return rs.getInt(column);
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
