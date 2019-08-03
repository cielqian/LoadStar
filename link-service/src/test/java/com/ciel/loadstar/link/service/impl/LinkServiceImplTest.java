package com.ciel.loadstar.link.service.impl;

import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.service.LinkService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-08-03 12:00
 * @Comment
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkServiceImplTest {

    @Autowired
    LinkService linkService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryLinksUnderFolder() {
        SessionResourceUtil.setCurrentAccountId(1121332677336698882L);
        List<Link> links = linkService.queryLinksUnderFolder(1L);
        Assert.assertTrue(links != null);
    }
}