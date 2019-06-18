package com.ciel.loadstar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.user.repository.PluginRepository;
import com.ciel.loadstar.user.entity.Plugin;
import com.ciel.loadstar.user.service.PluginService;
import org.springframework.stereotype.Service;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/22 13:41
 */
@Service
public class PluginServiceImpl extends ServiceImpl<PluginRepository, Plugin> implements PluginService {
}
