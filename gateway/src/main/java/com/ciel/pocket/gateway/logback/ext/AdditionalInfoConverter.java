package com.ciel.pocket.gateway.logback.ext;

import ch.qos.logback.access.pattern.AccessConverter;
import ch.qos.logback.access.spi.IAccessEvent;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/31 13:43
 */

public class AdditionalInfoConverter extends AccessConverter {
    @Override
    public String convert(IAccessEvent iAccessEvent) {
        String info = AccessLogger.getAndRemoveAdditionalInfo();
        return info!=null ? info : "-";
    }
}
