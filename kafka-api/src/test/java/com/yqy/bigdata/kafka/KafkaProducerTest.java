package com.yqy.bigdata.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author bahsk
 * @createTime 2021-02-15 0:46
 * @description
 */


public class KafkaProducerTest {

    Producer<String, String> producer = null;
    String topic = null;


    @Before
    public void setUp() throws Exception {
        Properties props = new Properties();
        //务必要记住在阿里云上开下端口！！！
        props.put("bootstrap.servers", "master610:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //props.put("transactional.id", "my-transactional-id");
        producer = new KafkaProducer<String, String>(props);

    }

    @Test
    public void getConnection(){
        Assert.assertNotNull(producer);
    }

    @Test
    public void publishesRecords() {
        topic = "jiangzh-topic";
        for (int i = 0; i < 5; i++) {
            System.out.println("第" + i + "条");
            producer.send(new ProducerRecord<String, String>(topic, "第"+Integer.toString(i)+"cat"));
        }

        //TODO

    }

    @After
    public void tearDown() {
        producer.close();
    }
}
