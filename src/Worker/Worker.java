package Worker;

import Database.DB;
import Database.DBInstance;
import Database.Register;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.sql.SQLException;

public class Worker<T> {
    String message;
    DBInstance database;
    T data;

    Worker(Class<T> type,String message){
        this.message = message;
        //  System.out.println(this.message);
        Gson gson = new Gson();
        try {
            this.data = (T) gson.fromJson(this.message, type);
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        try {
            this.database = DB.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
