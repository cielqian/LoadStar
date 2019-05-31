package com.ciel.pocket.gateway.logback.ext;

import ch.qos.logback.access.PatternLayout;
import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/31 13:47
 */

public class AccessLogLayoutWrapper extends PatternLayoutEncoderBase<IAccessEvent> {
    @Override
    public void start() {
        PatternLayout patternLayout = new PatternLayout();
        patternLayout.getDefaultConverterMap().put("X", AdditionalInfoConverter.class.getName());
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }
}
