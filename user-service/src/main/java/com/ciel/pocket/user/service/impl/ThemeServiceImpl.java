package com.ciel.pocket.user.service.impl;

import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Override
    public Theme queryByUserId(Long accountId) {
        return themeRepository.findByUserId(accountId);
    }

    @Override
    public void updateListType(Long accountId, ListTypeEnum listTypeEnum) {
        Theme theme = themeRepository.findByUserId(accountId);
        theme.setListTypeEnum(listTypeEnum);
        themeRepository.save(theme);
    }
}
