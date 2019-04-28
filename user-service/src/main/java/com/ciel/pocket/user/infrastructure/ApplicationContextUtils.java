package com.ciel.pocket.user.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/4/26 16:43
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    // 获取当前环境
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}
