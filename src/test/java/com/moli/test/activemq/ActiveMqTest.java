package com.moli.test.activemq;

import com.moli.activemq.demo.ConsumerService;
import com.moli.activemq.demo.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/5/31
 */
@RunWith(SpringJUnit4ClassRunner.class)//要求junit-4.12及其以上
@ContextConfiguration(locations = "/spring-mvc.xml")
public class ActiveMqTest {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private Destination demoQueueDestination;

    @Test
    public void sendMsg()
    {
        System.out.println("producerService:" + producerService);
        System.out.println("demoQueueDestination:" + demoQueueDestination);

        producerService.sendMessage(demoQueueDestination,"发送一条消息啦");
    }

    @Test
    public void receiveMsg()
    {
        System.out.println("consumerService:" + consumerService);
        System.out.println("demoQueueDestination:" + demoQueueDestination);

        consumerService.receive(demoQueueDestination);
    }
}
