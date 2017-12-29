package com.wolf.book.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created on 17/12/28 20:38.
 *
 * @author wolf
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("OneWayProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("OneWayTopic", "TagOneWay", ("Hello RocketMQ OneWay!" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(message);
        }

        producer.shutdown();
    }
}
