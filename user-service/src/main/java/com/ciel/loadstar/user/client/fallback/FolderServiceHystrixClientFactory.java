package com.ciel.loadstar.user.client.fallback;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.user.client.FolderServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/1/9 14:54
 */
@Slf4j
@Component
public class FolderServiceHystrixClientFactory implements FallbackFactory<FolderServiceClient> {
    @Override
    public FolderServiceClient create(Throwable throwable) {
        return new FolderServiceClientWithFactory() {
            @Override
            public ReturnModel createDefault(Long userId) {
                log.error("CreateDefault Fail UserId " + userId);
                return null;
            }
        };
    }
}
