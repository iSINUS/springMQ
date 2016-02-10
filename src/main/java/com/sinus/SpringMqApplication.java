package com.sinus;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@SpringBootApplication
@EnableJms
public class SpringMqApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(SpringMqApplication.class, args);
    }

    final static String queueName = "sinus";

    @Autowired
    AnnotationConfigApplicationContext context;

    @Autowired
    JmsTemplate jmsTemplate;

    @Bean
    ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL("tcp://localhost:61616");
        return cf;
    }


    @Bean
    MessageCreator messageCreator() {
        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Ping!");
            }
        };
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Sending message...");
        jmsTemplate.send(queueName, messageCreator());
        context.close();
    }
}
