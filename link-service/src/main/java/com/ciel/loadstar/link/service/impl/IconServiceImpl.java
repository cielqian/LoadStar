package com.ciel.loadstar.link.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.link.entity.LinkIcon;
import com.ciel.loadstar.link.repository.LinkIconRepository;
import com.ciel.loadstar.link.service.IconService;
import org.springframework.stereotype.Service;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/27 15:39
 */
@Service
public class IconServiceImpl extends ServiceImpl<LinkIconRepository, LinkIcon> implements IconService {
}
