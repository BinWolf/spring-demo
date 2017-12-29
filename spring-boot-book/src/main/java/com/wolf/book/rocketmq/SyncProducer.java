package com.wolf.book.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created on 17/12/28 19:35.
 *
 * @author wolf
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("sync_producer_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i =0; i < 10 ; i++) {
            Message message = new Message("SyncTopic", "TagSync", ("Hello RocketMQ! SyncTopic " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();
    }
}
