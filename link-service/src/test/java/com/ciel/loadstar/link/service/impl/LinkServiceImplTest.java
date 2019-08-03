package com.ciel.loadstar.link.service.impl;

import static org.mockito.Mockito.*;

import com.ciel.loadstar.infrastructure.constants.LinkConstants;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.repository.LinkRepository;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-08-03 12:00
 * @Comment
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkServiceImplTest {

    @InjectMocks
    LinkServiceImpl linkService = new LinkServiceImpl();

    @Mock
    LinkRepository linkPapper;

    Long accountId = 1121332677336698882L;

    @Before
    public void setUp() throws Exception {
        SessionResourceUtil.setCurrentAccountId(accountId);

        List<Link> links = new ArrayList<>();
        when(linkPapper.queryAllUnderFolder(accountId, LinkConstants.LOADSTAR_FOLDER_ID))
                .thenReturn(links);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryLinksUnderFolder() {

        List<Link> links = linkService.queryLinksUnderFolder(1L);
        Assert.assertTrue(links != null);
    }
}