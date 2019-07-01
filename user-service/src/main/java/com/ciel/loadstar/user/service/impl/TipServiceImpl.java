package com.ciel.loadstar.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.user.entity.Tip;
import com.ciel.loadstar.user.repository.TipRepository;
import com.ciel.loadstar.user.service.TipService;
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
        Tip exist = getOne(qw);
        if (exist == null){
            Tip newTip = new Tip();
            newTip.setHasRead(true);
            newTip.setTip(tip);
            newTip.setUserId(userId);
            newTip.setReadTime(new Date());
            save(newTip);
        }else{
            exist.setHasRead(true);
            exist.setReadTime(new Date());
            updateById(exist);
        }
    }
}
