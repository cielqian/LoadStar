package com.ciel.pocket.gateway.logback.ext;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/31 13:42
 */

public class AccessLogger {
    private static ThreadLocal<AdditionalInfo> currentInfo = new ThreadLocal<>();

    public static String getAndRemoveAdditionalInfo() {
        AdditionalInfo additionalInfo = currentInfo.get();
        currentInfo.remove();
        return additionalInfo.getInfo();
    }
    public static void setAdditionalInfo(String info){
        currentInfo.set(new AdditionalInfo(info));
    }
    public static final class AdditionalInfo {
        private String info;
        public AdditionalInfo(String info) {
            super();
            this.info = info;
        }
        public String getInfo() {
            return info;
        }
    }
}
