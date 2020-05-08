package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    public static void main(String args[]){
        String urls = "jdbc:mysql://localhost:3306/ksch?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(urls,"root","root");

            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM user ");
            while(rs.next() && rs != null)
            {
                //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
                String fn = rs.getString("firstname");
                System.out.println(fn);
                String ln = rs.getString("lastname");
                System.out.println(ln);

            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}

