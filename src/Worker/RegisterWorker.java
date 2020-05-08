package Worker;

import Database.DB;
import Database.Register;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterWorker extends Worker<Register> implements Runnable {
    public RegisterWorker(String message){
        super(Register.class, message);
    }

    @Override
    public void run() {
        sendDB();
        //System.out.println("in message : " +  this.data.getFirstname());
    }

    public void sendDB(){

        try {
            PreparedStatement ppsm = database.preparedQuery("INSERT INTO `user`(`firstname`, `lastname`, `age`, `career`, `income`, `bankid`, `bankname`, `is_accept`) VALUES (?,?,?,?,?,?,?,?)");
            ppsm.setString(1,this.data.getFirstname());
            ppsm.setString(2,this.data.getLastname());
            ppsm.setInt(3,this.data.getAge());
            ppsm.setInt(4,this.data.getCareer());
            ppsm.setInt(5,this.data.getIncome());
            ppsm.setString(6,this.data.getBank_id());
            ppsm.setInt(7,this.data.getBank_name());
            ppsm.setBoolean(8,true);
            ppsm.execute();
            System.out.println("OK na");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ;
        }

    }
}