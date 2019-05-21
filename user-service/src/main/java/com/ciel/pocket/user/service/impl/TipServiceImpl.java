package com.ciel.pocket.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.user.domain.Tip;
import com.ciel.pocket.user.repository.TipRepository;
import com.ciel.pocket.user.service.TipService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/16 19:37
 */

@Service
public class TipServiceImpl extends ServiceImpl<TipRepository, Tip> implements TipService {

    @Override
    public List<Tip> list(Wrapper<Tip> queryWrapper) {
        return baseMapper.selectAllTip(queryWrapper);
    }

    @Override
    public void readTip(Long userId, String tip) {
        QueryWrapper<Tip> qw = new QueryWrapper<Tip>();
        qw.eq("user_id", userId);
        qw.eq("tip", tip);
        String s = qw.getSqlSegment();
        Tip exist = getOne(qw);
        if (exist == null){
            Tip newTip = new Tip();
            newTip.setHasRead(false);
            newTip.setTip(tip);
            newTip.setUserId(userId);
        }else{
            exist.setHasRead(true);
            exist.setReadTime(new Date());
        }
        saveOrUpdate(exist);
    }
}
