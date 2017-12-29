package com.wolf.book.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.util.List;

/**
 * Created on 17/12/28 21:07.
 *
 * @author wolf
 */
public class SimpleConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("simple_consumer_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setInstanceName("Consumer");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("AsyncTopic", "TagAsync || TagSync");
        consumer.subscribe("SyncTopic", SubscriptionData.SUB_ALL);
        consumer.subscribe("OneWayTopic", SubscriptionData.SUB_ALL);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Messages : " + msgs.size());
                MessageExt messageExt = msgs.get(0);
                String topic = messageExt.getTopic();
                String keys = messageExt.getKeys();
                String body = new String(messageExt.getBody());
                String tags = messageExt.getTags();
                System.out.printf("Receive New Message topic = %s key = %s body=%s tags=%s %n", topic, keys, body, tags);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        System.out.println("Consumer Started.%n");
    }
}
