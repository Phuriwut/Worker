package Worker;

import Database.DB;
import Database.Register;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import Message.Messager;
import constance.events.ClientEvents;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterWorker extends Worker<Register> implements Runnable {
    public RegisterWorker(String message, Messager messager){
        super(Register.class, message,messager);
    }

    @Override
    public void run() {
        sendDB();
        System.out.println("in message : " +  this.data.getFirstname());
    }

    public void sendDB(){

        try {
            PreparedStatement ppsm = database.preparedQuery("INSERT INTO `user`(`firstname`, `lastname`, `email`, `password`, `age`, `career`, `income`, `bankid`, `bankname`, `is_accept`) VALUES (?,?,?,?,?,?,?,?,?,?)");
            ppsm.setString(1,this.data.getFirstname());
            ppsm.setString(2,this.data.getLastname());
            ppsm.setString(3,this.data.getEmail());
            ppsm.setString(4,this.data.getPassword());
            ppsm.setInt(5,this.data.getAge());
            ppsm.setInt(6,this.data.getCareer());
            ppsm.setInt(7,this.data.getIncome());
            ppsm.setString(8,this.data.getBank_id());
            ppsm.setInt(9,this.data.getBank_name());
            ppsm.setBoolean(10,true);
            ppsm.execute();

            JSONObject notiData = new JSONObject();
            notiData.put("status",0);
            notiData.put("title","♦ Success ♦");
            notiData.put("detail","☺");

            JSONObject dataJSON = new JSONObject();
            dataJSON.put("type", ClientEvents.NOTIFICATE.getString());
            dataJSON.put("session_id",this.data.getSession_id());
            dataJSON.put("data",notiData.toString());

            this.messager.send(dataJSON.toString());
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
            ;
        }

    }
}