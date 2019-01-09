package com.ciel.pocket.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.pocket.infrastructure.enums.Language;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import com.ciel.pocket.user.infrastructure.enums.ThemeModuleEnum;

public interface ThemeService extends IService<Theme> {
    Theme create(User account);

    Theme queryByAccountId(Long accountId);

    void updateListType(Long accountId, ListTypeEnum listTypeEnum);

    void changeLanguage(Long accountId, Language language);

    void triggerModule(Long accountId, ThemeModuleEnum module);
}
