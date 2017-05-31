package com.moli.activemq.demo.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/5/31
 */
public class QueueMessageListener implements MessageListener {

    //当收到消息后，自动调用该方法
    @Override
    public void onMessage(Message message)
    {
        TextMessage textMessage = (TextMessage) message;
        try
        {
            System.out.println("监听到消息:" + textMessage.getText());
        } catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
}
