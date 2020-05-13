import Message.Messager;
import Worker.LoginWorker;
import Worker.RegisterWorker;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constance.events.ServerEvents;

import javax.jms.JMSException;
import java.util.ArrayList;

public class WorkerManager {
    static ArrayList<Thread> workers = new ArrayList<Thread>();
    Messager messager;
    private static int WORKER_NUMBER = 5;
    WorkerManager (){
        try {
            messager = new Messager();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    void updateQueue(){
        JsonParser jsonParser = new JsonParser();
        while(true){
            try {
                this.clearThread();
                if(this.workers.size() >= 5) continue;
                String newMessage = this.messager.recieve();
                JsonObject objectFromString = jsonParser.parse(newMessage).getAsJsonObject();
                String type = objectFromString.get("type").getAsString();
                String data = objectFromString.get("data").getAsString();
                if(type.equals(ServerEvents.REGISTER.getString())){
                    Thread th = new Thread(new RegisterWorker(data, this.messager));
                    workers.add(th);
                    System.out.println(this.workers.size());
                    th.start();
                }else if(type.equals(ServerEvents.LOGIN.getString())){
                    Thread th = new Thread(new LoginWorker(data, this.messager));
                    workers.add(th);
                    System.out.println(this.workers.size());
                    th.start();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    void clearThread(){
        for (int i = 0; i < this.workers.size(); ) {
            if(workers.get(i).isAlive()) {
                i++;
                continue ;
            }
            workers.remove(i);
        }
    }

    void start(){
        this.updateQueue();
        for (Thread th: this.workers) {
            th.start();
        }
        while (true){
            try {
                Messager receiver = new Messager();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
