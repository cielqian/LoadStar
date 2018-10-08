package com.ciel.pocket.link.service.linkParser;

import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.link.infrastructure.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
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
    static String titlePattern = "(?<=<title>\r?\n?).*?(?=\r?\n?</title>)";

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

        String content = null;
        try {
            content = HttpUtil.get(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern pt = Pattern.compile(titlePattern);
        Matcher matcher = pt.matcher(content);

        while (matcher.find()) {
            String title = matcher.group().trim();
            result.setTitle(title);
            result.setName(title.length() > 12 ? title.substring(0,12) + "...": title);
            break;
         }


        Pattern icoLinkPt = Pattern.compile(icon1Pattern);
        Matcher icoLinkMatcher = icoLinkPt.matcher(content);

        while (icoLinkMatcher.find()) {
            String icoLink = icoLinkMatcher.group();

            Pattern icoPt = Pattern.compile(icon2Pattern);
            Matcher icoMatcher = icoPt.matcher(icoLink);

            while (icoMatcher.find()) {
                String ico = icoMatcher.group();
                if (ico.startsWith("//")){
                    ico = url.getProtocol() + "://" + ico.substring(2);
                }
                if (ico.startsWith("/")){
                    ico = url.getProtocol() + "://" + url.getHost() + ico;
                }
                if (!ico.startsWith("http")){
                    ico =  "http://" + url.getHost() + "/" + ico;
                }
                ico = ico.replace("https:", "http:");
                result.setIcon(ico);
                break;
            }

            break;
        }
        if(StringUtils.isEmpty(result.getIcon())){
            result.setIcon(defaultIcon);
        }

        DEFAULT_SITE_MAP.forEach((key, val) -> {
            if (uri.contains(key)){
                String[] tmp = StringUtils.split(val, "|");
                result.setIcon(tmp[0]);
                if (StringUtils.isEmpty(result.getName())){
                    result.setName(tmp[1]);
                }
            }
        });

        return result;
    }

    private String httpGet(String uri){
        boolean isHttps = uri.startsWith("https://");
        HttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet(uri);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
