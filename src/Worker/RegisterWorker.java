package Worker;

import Database.Register;

import Message.Messager;
import constance.events.ClientEvents;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterWorker extends Worker<Register> implements Runnable {
    public RegisterWorker(String message, Messager messager){
        super(Register.class, message,messager);
    }

    @Override
    public void run() {
        sendDB();
//        System.out.println("in message : " +  this.data.getFirstname());
    }

    public void sendDB() {
        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `user` WHERE email = ? LIMIT 1");
            ppsm.setString(1, data.getEmail());
            ppsm.execute();
            ResultSet rs = ppsm.getResultSet();

            if (rs.next()) {
                failRegister();
                return;
            }

            successRegister();

        }catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void successRegister() throws SQLException, JMSException{
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
    }

    public void failRegister() throws JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("title","WARNING");
        userEventData.put("detail","This's email already exists");
        userEventData.put("status",1);

        String userEventDataJSON = userEventData.toString();

        JSONObject workerToSocketData = new JSONObject();
        workerToSocketData.put("type",ClientEvents.NOTIFICATE.getString());
        workerToSocketData.put("session_id",this.data.getSession_id());
        workerToSocketData.put("data",userEventDataJSON);

        System.out.println(workerToSocketData.toString());

        this.messager.send(workerToSocketData.toString());
    }
}