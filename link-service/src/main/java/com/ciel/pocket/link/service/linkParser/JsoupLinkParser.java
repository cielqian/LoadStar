package com.ciel.pocket.link.service.linkParser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.model.LinkIcon;
import com.ciel.pocket.link.model.LinkTag;
import com.ciel.pocket.link.service.IconService;
import gui.ava.html.Html2Image;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/22 15:09
 */
@Log
@Component
public class JsoupLinkParser {
    @Autowired
    IconService iconService;

    private String iconCssQuery = "link[href~=.*\\.(ico|png)]";

    private String mainHost = "";

    private URL sourceUrl;

    private URL redirectUrl;

    public AnalysisLinkOutput analysis(String uri){
        AnalysisLinkOutput result = new AnalysisLinkOutput();
        try {
            sourceUrl = new URL(uri);
            mainHost = sourceUrl.getProtocol() + "://" + sourceUrl.getHost() + (sourceUrl.getPort() == -1?"":(":" + sourceUrl.getPort()));

            result.setUri(uri);
            result.setHost(sourceUrl.getHost());

            Connection con = Jsoup.connect(uri);
            Document document = con.get();
            redirectUrl = new URL(document.location());
            result.setTitle(document.title());

            //load icon from db
            //TODO: optimize. load icon from mem cache
            QueryWrapper<LinkIcon> qw = new QueryWrapper<LinkIcon>();
            qw.eq("hostname", redirectUrl.getHost());
            LinkIcon linkIcon = iconService.getOne(qw);

            if (linkIcon != null){
                System.out.println("load icon from db cache");
                result.setIcon(linkIcon.getIcon());
            }
            else{
                Element element = document.head().select(iconCssQuery).first();
                if (element != null){
                    result.setIcon(element.attr("href"));
                }
                else{
                    Connection mainPageCon = Jsoup.connect(mainHost);
                    Document mainPageDocument = mainPageCon.get();

                    Element mainPageIconElement = mainPageDocument.head().select(iconCssQuery).first();
                    if (mainPageIconElement != null){
                        result.setIcon(mainPageIconElement.attr("href"));
                    }
                }

                if (StringUtils.isEmpty(result.getIcon())){
                    result.setIcon(mainHost + "/favicon.ico");
                }
                if (!StringUtils.startsWith(result.getIcon(), "http")){
                    if (StringUtils.startsWith(result.getIcon(),"//")){
                        result.setIcon("http:" + result.getIcon());
                    }else{
                        if (!StringUtils.startsWith(result.getIcon(),"/")){
                            result.setIcon("/" + result.getIcon());
                        }
                        result.setIcon(mainHost + result.getIcon());
                    }
                }
                result.setIcon(StringUtils.replace(result.getIcon(), "https", "http"));
                linkIcon = new LinkIcon();
                linkIcon.setHostname(sourceUrl.getHost());
                linkIcon.setIcon(result.getIcon());
                iconService.save(linkIcon);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("parse :"+ result.toString());
        return result;
    }
}
