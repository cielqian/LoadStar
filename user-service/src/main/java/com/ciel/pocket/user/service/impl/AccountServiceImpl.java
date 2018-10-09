package com.ciel.pocket.user.service.impl;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.enums.Language;
import com.ciel.pocket.infrastructure.repositories.BaseRepository;
import com.ciel.pocket.infrastructure.service.BaseCrudService;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.client.AuthServiceClient;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.dto.input.CreateUser;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import com.ciel.pocket.user.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.pocket.user.repository.AccountRepository;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/15
 * @Comment
 */
@Service
public class AccountServiceImpl extends BaseCrudService<User, Long> implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    AuthServiceClient authServiceClient;

    @Override
    protected BaseRepository<User, Long> getRepository() {
        return accountRepository;
    }

    @Override
    public User queryById(Long id) {
        User user = findOne(id).orElseThrow(() -> new ObjectNotExistingException("用户不存在"));
        return user;
    }

    @Override
    public User create(CreateUser user) {
        User existing = accountRepository.findByUsername(user.getUsername());
        Assert.isNull(existing, "用户已存在");

        ReturnModel<String> remoteResult = authServiceClient.createUser(user);
        ReturnUtils.checkSuccess(remoteResult);

        User account = new User();
        account.setAccountId(remoteResult.getData());
        account.setNickname(user.getNickname());
        account.setUsername(user.getUsername());
        account.setLastSeen(new Date());

        accountRepository.save(account);

        Theme theme = new Theme();
        theme.setListTypeEnum(ListTypeEnum.Card);
        theme.setLanguage(Language.en);
        theme.setUser(account);

        themeRepository.save(theme);

        return account;
    }

    @Override
    public void delete(Long userId) {
        User user = queryById(userId);
        ReturnModel remoteResult = authServiceClient.deleteUser(user.getUsername());
        ReturnUtils.checkSuccess(remoteResult);

        getRepository().deleteById(user.getId());
    }

    @Override
    public User findByName(String username) {
        return accountRepository.findByUsername(username);
    }

}
