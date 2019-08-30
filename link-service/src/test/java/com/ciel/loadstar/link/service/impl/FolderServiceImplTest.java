package com.ciel.loadstar.link.service.impl;

import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.dto.output.FolderTreeOutput;
import com.ciel.loadstar.link.repository.LinkRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FolderServiceImplTest {
    @Autowired
    @InjectMocks
    FolderServiceImpl folderService = new FolderServiceImpl();

    @Autowired
    LinkRepository linkRepository;

    Long accountId = 1121332677336698882L;

    @Test
    public void queryFolderTree() {
        SessionResourceUtil.setCurrentAccountId(accountId);

        List<FolderTreeOutput> folderTreeOutputs = folderService.queryFolderTree(accountId);

        Assert.assertTrue(folderTreeOutputs.size() >= 2);
    }
}