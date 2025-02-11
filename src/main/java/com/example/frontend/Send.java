package com.example.frontend;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.exchangeDeclare("SpotifyAppUserEvents", "direct");

            String message = "Hello World!";
            //channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish("SpotifyAppUserEvents", "search", null, message.getBytes());
            channel.basicPublish("SpotifyAppUserEvents", "analytics", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}