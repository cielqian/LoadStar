package com.ciel.loadstar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.infrastructure.enums.Language;
import com.ciel.loadstar.user.entity.Theme;
import com.ciel.loadstar.user.entity.User;
import com.ciel.loadstar.user.enums.ListTypeEnum;
import com.ciel.loadstar.user.enums.ThemeModuleEnum;

public interface ThemeService extends IService<Theme> {
    Theme create(User account);

    Theme queryByAccountId(Long accountId);

    void updateListType(Long accountId, ListTypeEnum listTypeEnum);

    void changeLanguage(Long accountId, Language language);

    void triggerModule(Long accountId, ThemeModuleEnum module);
}
