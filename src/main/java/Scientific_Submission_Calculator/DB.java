package Scientific_Submission_Calculator;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by hsenid on 11/3/2016.
 */
public class DB {
    private static Connection con;
    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator?characterEncoding=UTF-8&useSSL=false","root","1234");
        return con;
    }

}
