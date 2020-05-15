package Worker;

import Database.Login;
import Message.Messager;
import constance.events.ClientEvents;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWorker extends Worker<Login> implements Runnable {
    public LoginWorker(String message, Messager messager) {
        super(Login.class, message, messager);
    }

    @Override
    public void run() {
        checkDB();
        //System.out.println("in message : " +  this.data.getFirstname());
    }

    public void checkDB() {

        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `user` WHERE email = ? AND password = ? LIMIT 1");
            ppsm.setString(1, data.getEmail());
            ppsm.setString(2, data.getPassword());
            ppsm.execute();
            ResultSet rs = ppsm.getResultSet();

             if (!rs.next()) {
                 notFoundHandler();
                 return;
             }

            foundHandler(rs);

        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void notFoundHandler() throws JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("title","Fail");
        userEventData.put("detail","email or password is wrong");
        userEventData.put("status",2);

        String userEventDataJSON = userEventData.toString();

        JSONObject workerToSocketData = new JSONObject();
        workerToSocketData.put("type",ClientEvents.NOTIFICATE.getString());
        workerToSocketData.put("session_id",this.data.getSession_id());
        workerToSocketData.put("data",userEventDataJSON);

        System.out.println(workerToSocketData.toString());

        this.messager.send(workerToSocketData.toString());
    }

    public void foundHandler(ResultSet rs) throws SQLException, JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("firstname", rs.getString("firstname"));
        userEventData.put("lastname", rs.getString("lastname"));
        userEventData.put("email", rs.getString("email"));
        userEventData.put("age", rs.getInt("age"));
        userEventData.put("career",rs.getInt("career"));
        userEventData.put("income",rs.getInt("income"));
        userEventData.put("bank_id",rs.getString("bankid"));
        userEventData.put("bank_name",rs.getInt("bankname"));
        userEventData.put("is_accept", rs.getBoolean("is_accept"));
        userEventData.put("status","SUCCESS");

        String userEventDataJSON = userEventData.toString();

        JSONObject workerToSocketData = new JSONObject();
        workerToSocketData.put("type", ClientEvents.LOGIN_RECEIVE.getString());
        workerToSocketData.put("session_id",this.data.getSession_id());
        workerToSocketData.put("data",userEventDataJSON);

        System.out.println(workerToSocketData.toString());
        this.messager.send(workerToSocketData.toString());
    }


}
