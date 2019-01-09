package com.ciel.pocket.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.infrastructure.enums.Language;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.domain.ThemeModule;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import com.ciel.pocket.user.infrastructure.enums.ThemeModuleEnum;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeRepository, Theme> implements ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Override
    public Theme create(User account) {
        Theme theme = new Theme();
        theme.setListTypeEnum(ListTypeEnum.Card);
        theme.setLanguage(Language.en);
        theme.setUserId(account.getAccountId());

        List<ThemeModule> modules = new ArrayList<>();
        modules.add(new ThemeModule(ThemeModuleEnum.Recently, false));
        modules.add(new ThemeModule(ThemeModuleEnum.Top, false));
        modules.add(new ThemeModule(ThemeModuleEnum.Private, false));

        theme.setModules(JSON.toJSONString(modules));
        themeRepository.insert(theme);
        return theme;
    }

    @Override
    public Theme queryByAccountId(Long userId) {
        return themeRepository.findByUserId(userId);
    }

    @Override
    public void updateListType(Long accountId, ListTypeEnum listTypeEnum) {
        Theme theme = queryByAccountId(accountId);
        theme.setListTypeEnum(listTypeEnum);
        themeRepository.updateById(theme);
    }

    @Override
    public void changeLanguage(Long accountId, Language language) {
        Theme theme = queryByAccountId(accountId);
        theme.setLanguage(language);
        themeRepository.updateById(theme);
    }

    @Override
    public void triggerModule(Long accountId, ThemeModuleEnum module) {
        Theme theme = queryByAccountId(accountId);
        String jsonString = theme.getModules();
        List<ThemeModule> modules = JSON.parseArray(jsonString, ThemeModule.class);
        modules.stream().filter(x -> x.getModule().equals(module)).forEach(x -> x.setShow(!x.isShow()));
        theme.setModules(JSON.toJSONString(modules));
        themeRepository.updateById(theme);
    }
}
