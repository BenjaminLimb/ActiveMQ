package com.benjaminlimb.activemqdemo;

import org.apache.activemq.spring.ActiveMQConnectionFactory;

/**
 *
 * @author http://tech.lalitbhatt.net/2014/08/activemq-introduction.html
 * @author benjaminlimb
 *
 */
public class Main {

  public static void main(String[] args) {

    for (int i = 0; i < 10; i++) {

      //Create the connection factory
      ActiveMQConnectionFactory connectionFactory
              = new ActiveMQConnectionFactory();
      connectionFactory.setBrokerURL("tcp://localhost:61616");

//Create the consumer. It will wait to listen to the Topic
      Thread topicConsumerThread
              = new Thread(new TopicConsumer(connectionFactory));
      topicConsumerThread.start();

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

//Create a message. As soon as the message is published on the Topic,
//it will be consumed by the consumer
      Thread topicProducerThread
              = new Thread(new TopicProducer(connectionFactory));
      topicProducerThread.start();
    }

  }
}
