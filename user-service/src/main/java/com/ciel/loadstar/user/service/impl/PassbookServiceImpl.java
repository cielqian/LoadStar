package com.ciel.loadstar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.user.repository.PassbookRepository;
import com.ciel.loadstar.user.entity.Passbook;
import com.ciel.loadstar.user.service.PassbookService;
import org.springframework.stereotype.Service;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/29 11:38
 */
@Service
public class PassbookServiceImpl extends ServiceImpl<PassbookRepository, Passbook> implements PassbookService {
}
