package com.ciel.loadstar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.user.entity.Tip;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/16 19:37
 */

public interface TipService extends IService<Tip> {

    void readTip(Long userId, String tip);
}
