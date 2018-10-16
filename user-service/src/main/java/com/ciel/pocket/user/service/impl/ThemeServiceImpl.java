package com.ciel.pocket.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.ciel.pocket.infrastructure.enums.Language;
import com.ciel.pocket.user.domain.*;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import com.ciel.pocket.user.infrastructure.enums.ThemeModuleEnum;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.service.ThemeService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public Theme create(User account) {
        Theme theme = new Theme();
        theme.setListTypeEnum(ListTypeEnum.Card);
        theme.setLanguage(Language.en);
        theme.setUser(account);

        List<ThemeModule> modules = new ArrayList<>();
        modules.add(new ThemeModule(ThemeModuleEnum.Recently, false));
        modules.add(new ThemeModule(ThemeModuleEnum.Top, false));

        theme.setModules(JSON.toJSONString(modules));
        return themeRepository.save(theme);
    }

    @Override
    public Theme queryByAccountId(Long accountId) {
        QTheme _theme = QTheme.theme;
        QUser _user = QUser.user;
        return queryFactory.selectFrom(_theme)
                .leftJoin(_theme.user, _user)
                .where(_user.accountId.eq(accountId))
                .fetchOne();
    }

    @Override
    public void updateListType(Long accountId, ListTypeEnum listTypeEnum) {
        Theme theme = queryByAccountId(accountId);
        theme.setListTypeEnum(listTypeEnum);
        themeRepository.save(theme);
    }

    @Override
    public void changeLanguage(Long accountId, Language language) {
        Theme theme = queryByAccountId(accountId);
        theme.setLanguage(language);
        themeRepository.save(theme);
    }

    @Override
    public void triggerModule(Long accountId, ThemeModuleEnum module) {
        Theme theme = queryByAccountId(accountId);
        String jsonString = theme.getModules();
        List<ThemeModule> modules = JSON.parseArray(jsonString, ThemeModule.class);
        modules.stream().filter(x -> x.getModule().equals(module)).forEach(x -> x.setShow(!x.isShow()));
        theme.setModules(JSON.toJSONString(modules));
        themeRepository.save(theme);
    }
}
