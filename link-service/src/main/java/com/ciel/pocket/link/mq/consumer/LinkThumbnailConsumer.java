package com.ciel.pocket.link.mq.consumer;

import com.ciel.pocket.link.infrastructure.utils.ZimgUtil;
import com.ciel.pocket.link.model.Link;
import com.ciel.pocket.link.service.FolderService;
import com.ciel.pocket.link.service.LinkService;
import gui.ava.html.Html2Image;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/26 15:23
 */
@Component
public class LinkThumbnailConsumer {
    @Autowired
    LinkService linkService;

    @Value("${loadstar.kafka.topic.linkThumbnail}")
    private String linkThumbnailTopic;

    @Value("${loadstar.zimg}")
    private String zimgHost;

    @KafkaListener(topics = "${loadstar.kafka.topic.linkThumbnail}")
    public void listen (ConsumerRecord<String, String> record) throws Exception {
        Long linkId = Long.valueOf(record.value());
        System.out.println("paser linkThumbnail linkId: " + linkId);

        Link link = linkService.query(linkId);
        if (link!= null){
            Html2Image img = Html2Image.fromURL(new URL(link.getUrl()));
            BufferedImage bufferedImage = img.getImageRenderer().setImageType("png").setWriteCompressionQuality(0.5F).getBufferedImage();

            String mediaId = ZimgUtil.uploadImage(zimgHost, bufferedImage);
            if (StringUtils.isNotEmpty(mediaId)){
                link.setThumbnail(mediaId);
                linkService.updateById(link);
            }
        }
    }
}
