package com.yqy.bigdata.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * @author bahsk
 * @createTime 2021-02-16 17:04
 * @description
 */
public class KafkaAdminTest {

    AdminClient admin = null;
    String topic = null;
    NewTopic newTopic = null;
    Collection<NewTopic> collections = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        Properties props = new Properties();
        props.put("bootstrap.servers", "master610:9092");
        admin = AdminClient.create(props);

        topic = "test";

        newTopic = new NewTopic(topic,1, (short) 1);
        collections.add(newTopic);
    }

    @Test
    public void createTopic() {

        admin.createTopics(collections);
    }

    @Test
    public void showTopicList() {

        ListTopicsResult listTopicsResult = admin.listTopics();


    }


    @After
    public void tearDown() {
        admin.close();
    }
}
