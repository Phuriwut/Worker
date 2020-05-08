package Database;

import java.sql.*;

public class DB {
    static DBInstance instance;
    public static DBInstance getInstance() throws SQLException, ClassNotFoundException {
        if(instance == null){
            instance = new DBInstance();
        }
        return instance;
    }
}

