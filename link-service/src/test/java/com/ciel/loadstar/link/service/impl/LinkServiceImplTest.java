package com.ciel.loadstar.link.service.impl;

import static org.mockito.Mockito.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciel.loadstar.infrastructure.constants.LinkConstants;
import com.ciel.loadstar.infrastructure.dto.web.PageOutput;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.dto.input.QueryLinkListInput;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
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

    @Inject
    LinkServiceImpl linkService;

    @Autowired
    LinkRepository linkRepository;

    Long accountId = 1121332677336698882L;

    QueryLinkListInput pageInput = new QueryLinkListInput();

    @Before
    public void setUp() throws Exception {
        SessionResourceUtil.setCurrentAccountId(accountId);

//        linkRepository = Mockito.mock(LinkRepository.class, CALLS_REAL_METHODS);

//        List<Link> links = new ArrayList<>();
//        when(linkRepository.queryAllUnderFolder(accountId, LinkConstants.FOLDER_DEFAULT_ID))
//                .thenReturn(links);
//        when(linkRepository.queryAll(null, null)).thenCallRealMethod();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryLinksUnderFolder() {

        List<Link> links = linkService.queryLinksUnderFolder(accountId, 1L);
        Assert.assertTrue(links != null);
    }

    @Test
    public void queryPageList() {

        pageInput.setCurrentPage(1);

        PageOutput<Link> links = linkService.queryPageList(accountId, pageInput);

        Assert.assertTrue(links.getTotal() > 0);

    }

    @Test
    public void fullTextSearch() {

        QueryLinkListInput queryInput = new QueryLinkListInput();
        queryInput.setKeyword("有道云");
        PageOutput<Link> result = linkService.fullTextSearch(1199928125973499905L, queryInput);
        Assert.assertEquals(result.getTotal(), 35);
    }
}