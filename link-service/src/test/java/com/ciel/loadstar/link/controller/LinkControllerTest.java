package com.ciel.loadstar.link.controller;

import com.ciel.loadstar.infrastructure.constants.LinkConstants;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.dto.input.CreateLinkInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-08-03 12:47
 * @Comment
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkControllerTest {

    Long accountId = 1121332677336698882L;

    @Autowired
    LinkController linkController;

    @Before
    public void setUp() throws Exception {
        SessionResourceUtil.setCurrentAccountId(1121332677336698882L);
    }

    @Test
    public void createLink() {
        CreateLinkInput createLinkInput = new CreateLinkInput();
        createLinkInput.setFolderId(LinkConstants.LOADSTAR_FOLDER_ID);
        createLinkInput.setName("百度");
        createLinkInput.setTitle("百度");
        createLinkInput.setUrl("http://www.baidu.com");


        ReturnModel<Long> result = linkController.createLink(accountId, createLinkInput);
        Assert.assertEquals(result.getStatus(), HttpStatus.OK.value());
    }
}