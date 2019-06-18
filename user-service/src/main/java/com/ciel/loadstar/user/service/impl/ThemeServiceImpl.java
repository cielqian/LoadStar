package com.ciel.loadstar.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.infrastructure.enums.Language;
import com.ciel.loadstar.user.entity.Theme;
import com.ciel.loadstar.user.entity.ThemeModule;
import com.ciel.loadstar.user.entity.User;
import com.ciel.loadstar.user.infrastructure.enums.ListTypeEnum;
import com.ciel.loadstar.user.infrastructure.enums.ThemeModuleEnum;
import com.ciel.loadstar.user.repository.ThemeRepository;
import com.ciel.loadstar.user.service.ThemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeRepository, Theme> implements ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Override
    public Theme create(User account) {
        Theme theme = new Theme();
        theme.setListTypeEnum(ListTypeEnum.List1);
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
        log.info("change language to : {}", language.name());
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
