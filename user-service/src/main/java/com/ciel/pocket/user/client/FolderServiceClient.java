package com.ciel.pocket.user.client;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 14:37
 */
@FeignClient(name = "link-service")
public interface FolderServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/folder/default/{userId}")
    ReturnModel createDefault(@PathVariable(name = "userId") Long userId);
}
