import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageReceiver {

    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"

    // Name of the queue we will receive messages from
    private static String subject = "REGISTER";

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Destination destination;
    MessageConsumer consumer;
    Message message;

    MessageReceiver() throws JMSException {
        // Getting JMS connection from the server
        this.connectionFactory = new ActiveMQConnectionFactory(url);
        this.connection = connectionFactory.createConnection();
        this.connection.start();

        // Creating session for seding messages
        this.session = this.connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'JCG_QUEUE'
        this.destination = this.session.createQueue(subject);

        // MessageConsumer is used for receiving (consuming) messages
        this.consumer = this.session.createConsumer(destination);

        // Here we receive the message.

    }

    String recieve() throws JMSException{
        this.message = this.consumer.receive();

        // We will be using TestMessage in our example. MessageProducer sent us a TextMessage
        // so we must cast to it to get access to its .getText() method.
        if (this.message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            return textMessage.getText();
        }
        return null;
    }

    void stop(){
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

