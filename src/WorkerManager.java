import Worker.RegisterWorker;

import javax.jms.JMSException;
import java.util.ArrayList;

public class WorkerManager {
    static ArrayList<Thread> workers = new ArrayList<Thread>();
    MessageReceiver receiver;
    private static int WORKER_NUMBER = 5;
    WorkerManager (){
        try {
            receiver = new MessageReceiver();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    void updateQueue(){
        while(true){
            try {
                this.clearThread();
                if(this.workers.size() >= 5) continue;
                String newMessage = this.receiver.recieve();
                Thread th = new Thread(new RegisterWorker(newMessage));
                workers.add(th);
                System.out.println(this.workers.size());
                th.start();
            } catch (JMSException e) {
                e.printStackTrace();
            }

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
                MessageReceiver receiver = new MessageReceiver();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
