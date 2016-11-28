package edu.matc.mdbdemo;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author paulawaite
 * @version 1.0 11/18/15.
 */

@WebServlet(
        name = "testMessageBean",
        urlPatterns = {"/testMessageBean"}
)

@MessageDriven
public class TestMessageBean extends HttpServlet implements MessageListener {
    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "MessageBean")
    private Queue questionQueue;

    private final Logger logger = Logger.getLogger(this.getClass());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        run(req.getParameter("message"));

        req.setAttribute("status", "Message sent, check the console/logs to " +
                "view the message");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }

    public void run(String message) {
        Session session = null;
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final MessageProducer questions = session.createProducer(questionQueue);
            questions.send(session.createTextMessage(message));

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
    }

    public void onMessage(Message message) {
        logger.info("The conversation continued by TestMessageBean is " + message);
    }
}