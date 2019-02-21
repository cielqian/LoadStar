package com.ciel.pocket.link.service.linkParser;

import com.ciel.pocket.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.infrastructure.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/9/20
 * @Comment
 */
public class DefaultLinkParser {
    static String icon1Pattern = "(?<=link).*href.*(png|ico|svg).*(?=>)";
    static String icon2Pattern = "(?<=(href|mec_href)\\=\").*?\\.(png|ico|svg)";
    static String titlePattern = "<title.*?</title>";//"(?<=<title>\r?\n?).*?(?=\r?\n?</title>)";
    //static String titlePattern2 = "(?<=<title.*>)(.*?)(?=</title>)";
    static String defaultIcon = "/static/logo.png";

    static Map<String, String> DEFAULT_SITE_MAP = new HashMap<>();

    static {
        DEFAULT_SITE_MAP.put("baidu.com", "/static/img/site/baidu.ico|百度一下，你就知道");
        DEFAULT_SITE_MAP.put("csdn.net", "/static/img/site/csdn.ico|CSDN");
    }

    public AnalysisLinkOutput analysis(String uri){
        AnalysisLinkOutput result = new AnalysisLinkOutput();

        URL url = null;

        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            throw new FriendlyException("Unkown Host");
        }

        result.setUri(uri);
        result.setHost(url.getHost());

        result.setIcon(url.getProtocol() + "://" + url.getHost() + (url.getPort() == -1?"":(":" + url.getPort())) + "/favicon.ico");
        String content = null;
        try {
            content = HttpUtil.get(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern pt = Pattern.compile(titlePattern);
        //content = null;
        Matcher matcher = pt.matcher(content.replaceAll("\r\n", ""));

        if (matcher.find()) {
            String titleContent = matcher.group().trim();
            if (StringUtils.isNotBlank(titleContent)){

                int startIdx = StringUtils.indexOf(titleContent, ">");
                int endIdx = StringUtils.lastIndexOf(titleContent, "<");
                String title = StringUtils.substring(titleContent, startIdx+1, endIdx).trim();
                result.setTitle(title);
                result.setName(title.length() > 24 ? (title.substring(0,24) + "..."): title);
            }
        }

//        Pattern icoLinkPt = Pattern.compile(icon1Pattern);
//        Matcher icoLinkMatcher = icoLinkPt.matcher(content);
//
//        if (icoLinkMatcher.find()) {
//            String icoLink = icoLinkMatcher.group();
//
//            Pattern icoPt = Pattern.compile(icon2Pattern);
//            Matcher icoMatcher = icoPt.matcher(icoLink);
//
//            if (icoMatcher.find()) {
//                String ico = icoMatcher.group();
//                if (ico.startsWith("//")){
//                    ico = url.getProtocol() + "://" + ico.substring(2);
//                }
//                if (ico.startsWith("/")){
//                    ico = url.getProtocol() + "://" + url.getHost() + ico;
//                }
//                if (!ico.startsWith("http")){
//                    ico =  "http://" + url.getHost() + "/" + ico;
//                }
//                ico = ico.replace("https:", "http:");
//                result.setIcon(ico);
//            }
//        }
//        if(StringUtils.isEmpty(result.getIcon())){
//            result.setIcon(defaultIcon);
//        }

        DEFAULT_SITE_MAP.forEach((key, val) -> {
            if (uri.contains(key)){
                String[] tmp = StringUtils.split(val, "|");
                //result.setIcon(tmp[0]);
                if (StringUtils.isEmpty(result.getName())){
                    result.setName(tmp[1]);
                    result.setTitle(tmp[1]);
                }
            }
        });

        return result;
    }

    private String httpGet(String uri){
        boolean isHttps = uri.startsWith("https://");
        HttpClient client = null;
        client = HttpClients.createDefault();

        HttpGet request = new HttpGet(uri);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**请求发送成功，并得到响应**/
        if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                String strResult = EntityUtils.toString(response.getEntity(), "gb2312");
                return strResult;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
