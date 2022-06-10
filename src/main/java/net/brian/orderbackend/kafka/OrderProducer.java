package net.brian.orderbackend.kafka;

import net.brian.orderbackend.objects.Order;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class OrderProducer {

    private static final String ORDER_TOPIC = "orders";
    private static final String BOOTSTRAP_IP = "localhost:9092";

    Producer<String,String> producer;

    public OrderProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_IP);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", 1);
        producer = new KafkaProducer<>(props);
    }


    public void sendOrder(Order order){
        ProducerRecord<String,String> record = new ProducerRecord<>(ORDER_TOPIC,order.getOrder_id().toString(),order.toString());
        producer.send(record);
        producer.flush();
    }

}
