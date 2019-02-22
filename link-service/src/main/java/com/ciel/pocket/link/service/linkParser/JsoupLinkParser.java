package com.ciel.pocket.link.service.linkParser;

import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/22 15:09
 */
@Log
public class JsoupLinkParser {
    public AnalysisLinkOutput analysis(String uri){
        AnalysisLinkOutput result = new AnalysisLinkOutput();
        try {
            URL url = new URL(uri);
            result.setUri(uri);
            result.setHost(url.getHost());

            Connection con = Jsoup.connect(uri);
            Document document = con.get();

            Element element = document.head().select("link[href~=.*\\.(ico|png)]").first();
            String icon = element.attr("href");
            String title = document.title();

            result.setTitle(title);
            result.setIcon(icon);
            if (StringUtils.isEmpty(icon)){
                result.setIcon(url.getProtocol() + "://" + url.getHost() + (url.getPort() == -1?"":(":" + url.getPort())) + "/favicon.ico");
            }
            if (!StringUtils.startsWith(icon, "http")){
                if (!StringUtils.startsWith(icon,"/")){
                    icon = "/" + icon;
                }
                result.setIcon(url.getProtocol() + "://" + url.getHost() + (url.getPort() == -1?"":(":" + url.getPort())) + icon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("parse :"+ result.toString());
        return result;
    }
}
