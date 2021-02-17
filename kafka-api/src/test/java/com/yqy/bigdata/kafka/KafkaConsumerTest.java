package com.yqy.bigdata.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author bahsk
 * @createTime 2021-02-15 1:40
 * @description
 */
public class KafkaConsumerTest {

    Consumer<String,String> consumer = null;
    String topic = null;

    @Before
    public void setUp() throws Exception {

        Properties props = new Properties();
        //务必要记住在阿里云上开下端口！！！
        props.setProperty("bootstrap.servers", "master610:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        consumer = new KafkaConsumer<String, String>(props);
    }

    @Test
    public void subscribeTopic(){
        topic = "test";
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.println(record.topic()+":"+record.value());
        }
    }

    @After
    public void tearDown() {
        consumer.close();
    }
}
