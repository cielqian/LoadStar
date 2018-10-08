package com.ciel.pocket.user.service.impl;

import com.ciel.pocket.user.domain.QTheme;
import com.ciel.pocket.user.domain.QUser;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.service.ThemeService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public Theme queryByAccountId(String accountId) {
        QTheme _theme = QTheme.theme;
        QUser _user = QUser.user;
        return queryFactory.selectFrom(_theme)
                .leftJoin(_theme.user, _user)
                .where(_user.accountId.eq(accountId))
                .fetchOne();
    }

    @Override
    public void updateListType(String accountId, ListTypeEnum listTypeEnum) {
        Theme theme = queryByAccountId(accountId);
        theme.setListTypeEnum(listTypeEnum);
        themeRepository.save(theme);
    }
}
