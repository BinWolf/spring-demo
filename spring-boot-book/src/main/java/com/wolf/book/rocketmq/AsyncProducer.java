package com.wolf.book.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created on 17/12/28 20:24.
 *
 * @author wolf
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("AsyncProducerGroup");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i =0; i < 10 ; i++) {
            final int index = i;
            Message message = new Message("AsyncTopic",
                    "TagAsync",
                    "memberID",
                    ("Hello RocketMQ AsyncProducer! AsyncTopic " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message, new SendCallback(){
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }

        producer.shutdown();
    }
}
