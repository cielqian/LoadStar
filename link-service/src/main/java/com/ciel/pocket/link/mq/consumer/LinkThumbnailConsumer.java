package com.ciel.pocket.link.mq.consumer;

import com.ciel.pocket.link.infrastructure.utils.ZimgUtil;
import com.ciel.pocket.link.model.Link;
import com.ciel.pocket.link.service.FolderService;
import com.ciel.pocket.link.service.LinkService;
import gui.ava.html.Html2Image;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.fit.cssbox.demo.ImageRenderer;
import org.fit.cssbox.layout.VisualContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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

            System.out.println("start");

//            MyImageRenderer render = new MyImageRenderer();
//
//            String url = link.getUrl();
//
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//            render.setWindowSize(new Dimension(500, 500), false);
//            render.renderURL(url, os, ImageRenderer.Type.PNG);
//
//            ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
//
//            BufferedImage bufferedImage = ImageIO.read(in);
//
//            int height = bufferedImage.getHeight() >= 500?500:bufferedImage.getHeight();
//            int wight = bufferedImage.getWidth() >= 1000?1000:bufferedImage.getWidth();
//
//            BufferedImage subImg = bufferedImage.getSubimage(0,0,wight,height);
//            Image scaledImage = subImg.getScaledInstance(500,500, Image.SCALE_DEFAULT);
//
//            BufferedImage bufImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
//
//
//            Graphics2D bGr = bufImage.createGraphics();
//            bGr.drawImage(scaledImage, 0, 0, null);
//            bGr.dispose();
//
//            FileOutputStream out = new FileOutputStream("D://" + linkId + ".png");
//            ImageIO.write(bufImage, "png", out);
//            System.out.println("saved");

            Html2Image img = Html2Image.fromURL(new URL(link.getUrl()));
            BufferedImage bufferedImage = img.getImageRenderer()
                    .setWidth(1000)
                    .setHeight(500)
                    .setImageType("png")
                    .setWriteCompressionQuality(0.5F)
                    .getBufferedImage();

            int height = bufferedImage.getHeight() >= 500?500:bufferedImage.getHeight();
            int wight = bufferedImage.getWidth() >= 1000?1000:bufferedImage.getWidth();

            BufferedImage subImg = bufferedImage.getSubimage(0,0,wight,height);
            Image scaledImage = subImg.getScaledInstance(500,500, Image.SCALE_DEFAULT);

            BufferedImage bufImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);


            Graphics2D bGr = bufImage.createGraphics();
            bGr.drawImage(scaledImage, 0, 0, null);
            bGr.dispose();
            ImageIO.write(bufImage, "png", new File("D://" + linkId + ".png"));
            System.out.println("saved");

//            img.getImageRenderer()
//                    .setHeight(250)
//                    .setWidth(250)
//                    .setImageType("png")
//                    .setWriteCompressionQuality(0.2F)
//                    .saveImage("/Users/ciel/Documents/project/" + linkId + ".png");
//            String mediaId = ZimgUtil.uploadImage(zimgHost, bufferedImage);
//            if (StringUtils.isNotEmpty(mediaId)){
//                link.setThumbnail(mediaId);
//                linkService.updateById(link);
//            }
        }
    }
}
