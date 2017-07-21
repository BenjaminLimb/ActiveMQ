package com.benjaminlimb.activemqdemo;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

/**
 *
 * @author http://tech.lalitbhatt.net/2014/08/activemq-introduction.html
 * @author Benjamin Limb
 */
public class TopicProducer implements Runnable {

  private String topicText = "CLIMATE";
  private String messageText = "Today is Hot";

//Connection Factory which will help in connecting to ActiveMQ serer
  ActiveMQConnectionFactory connectionFactory = null;

  public TopicProducer(ActiveMQConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void run() {
    try {
      // First create a connection
      Connection connection
              = connectionFactory.createConnection();
      connection.start();

      // Now create a Session
      Session session = connection.createSession(false,
              Session.AUTO_ACKNOWLEDGE);

      // Let's create a topic. If the topic exist, 
      //it will return that
      Destination destination = session.createTopic(topicText);

      // Create a MessageProducer from 
      //the Session to the Topic or Queue
      MessageProducer producer
              = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.PERSISTENT);

      // Create a messages for the current climate      
      TextMessage message = session.createTextMessage(messageText);

      // Send the message to topic
      System.out.println("Sending Message:" + message.getText());
      producer.send(message);

      // Do the cleanup
      session.close();
      connection.close();
    } catch (JMSException jmse) {
      System.out.println("Exception: " + jmse.getMessage());
    }
  }

  public TopicProducer(String pTopic, String pMessage) {
    topicText = pTopic;
    messageText = pMessage;

  }
}
