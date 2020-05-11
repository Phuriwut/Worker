package Worker;

import Database.DB;
import Database.Register;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import Message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RegisterWorker extends Worker<Register> implements Runnable {
    public RegisterWorker(String message, Messager messager){
        super(Register.class, message,messager);
    }

    @Override
    public void run() {
        sendDB();
        //System.out.println("in message : " +  this.data.getFirstname());
    }

    public void sendDB(){

        try {
            PreparedStatement ppsm = database.preparedQuery("INSERT INTO `user`(`firstname`, `lastname`, `email`, `password`, `age`, `career`, `income`, `bankid`, `bankname`, `is_accept`) VALUES (?,?,?,?,?,?,?,?,?,?)");
            ppsm.setString(1,this.data.getFirstname());
            ppsm.setString(2,this.data.getLastname());
            ppsm.setString(3,data.getEmail());
            ppsm.setString(4,data.getPassword());
            ppsm.setInt(5,this.data.getAge());
            ppsm.setInt(6,this.data.getCareer());
            ppsm.setInt(7,this.data.getIncome());
            ppsm.setString(8,this.data.getBank_id());
            ppsm.setInt(9,this.data.getBank_name());
            ppsm.setBoolean(10,true);
            ppsm.execute();
            System.out.println("OK na");

            JSONObject dataJSON = new JSONObject(data);
            dataJSON.toString();
            this.messager.send("{\"type\":\"NOTIFICATE\",\"session_id\":\"" + data.getSession_id() + "\",\"data\":\"{\\\"status\\\":0,\\\"title\\\":\\\"success\\\",\\\"detail\\\":\\\"☺\\\"}\"}");
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
            ;
        }

    }
}