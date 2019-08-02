package com.ciel.loadstar.link.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.spi.FileTypeDetector;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/26 16:13
 */
@Log
public class ZimgUtil {
    public static String uploadImage(String url, BufferedImage bufferedImage) {
        try {
            String ext = "png";
            URL $url = new URL(url);
            URLConnection connection = $url.openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(25000);
            HttpURLConnection uc = (HttpURLConnection) connection;
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Connection", "Keep-Alive");
            uc.setRequestProperty("Cache-Control", "no-cache");
            uc.setRequestProperty("Content-Type", ext.toLowerCase());
            uc.setRequestProperty("COOKIE", "william");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.connect();
            OutputStream output = uc.getOutputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            byte[] buf = new byte[8192];
            while (true) {
                int len = input.read(buf);
                if (len <= 0)
                    break;
                output.write(buf, 0, len);
            }
            StringBuffer resp = new StringBuffer();
            InputStreamReader inReader = new InputStreamReader(uc.getInputStream(), "UTF-8");
            char[] bb = new char[8192];
            while (true) {
                int length = inReader.read(bb);
                if (length == -1)
                    break;
                char[] bc = new char[length];
                for (int i = 0; i < length; i++)
                    bc[i] = bb[i];
                resp.append(new String(bc));
            }
            output.close();
            input.close();
            inReader.close();
            uc.disconnect();
            JSONObject json = JSONObject.parseObject(resp.toString());
            if (json.getBooleanValue("ret")) {
                JSONObject info = json.getJSONObject("info");
                String md5 = info.getString("md5");
                return md5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
