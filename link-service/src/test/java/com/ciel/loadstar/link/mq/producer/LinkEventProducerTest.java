package com.ciel.loadstar.link.mq.producer;

import com.ciel.loadstar.infrastructure.events.link.LinkEvent;
import com.ciel.loadstar.infrastructure.events.link.LinkEventType;
import com.ciel.loadstar.link.LinkApplication;
import com.ciel.loadstar.link.entity.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkApplication.class)
public class LinkEventProducerTest {

    @Autowired
    LinkEventProducer linkEventProducer;

    @Test
    public void send() {
        Link link = new Link();
        link.setId(1L);
        link.setName("百度1");
        link.setTitle("百度1");
        link.setUrl("https://www.baidu.com");

        LinkEvent event = new LinkEvent(LinkEventType.CREATE);
        event.setId("1");
        event.setObj(link);

        linkEventProducer.send(event);
    }
}