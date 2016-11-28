package edu.matc.mdbdemo;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.*;

/**
 * @author paulawaite
 * @version 1.0 11/18/15.
 */
@MessageDriven
public class MessageBean implements MessageListener {
    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "TestMessageBean")
    private Queue answerQueue;

    public MessageBean() {
    }

    private final Logger logger = Logger.getLogger(this.getClass());

    public void onMessage(Message message) {
        logger.info("The message is " + message);

        String interestingMessage = "my interesting answer back...";


        Session session = null;
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            logger.info("about to send answerQueue back...");
            final MessageProducer answers = session.createProducer(answerQueue);
            answers.send(session.createTextMessage(interestingMessage));

        } catch (Exception exception) {
            logger.error(exception.getStackTrace());


        } finally {

            try {
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception exception) {
                logger.error(exception.getStackTrace());
            }
        }


        //TODO: Extra Challenge - write code to get the two beans talking
        // back and forth in a longer "conversation"
    }
}