package com.ciel.pocket.user.service;

import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;

public interface ThemeService {
    Theme queryByAccountId(String accountId);

    void updateListType(String accountId, ListTypeEnum listTypeEnum);
}
