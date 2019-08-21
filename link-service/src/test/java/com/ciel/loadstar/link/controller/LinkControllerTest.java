package com.ciel.loadstar.link.controller;

import com.alibaba.fastjson.JSON;
import com.ciel.loadstar.infrastructure.constants.Constants;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2019-08-03 12:47
 * @Comment
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LinkControllerTest {

    Long accountId = 1121332677336698882L;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        SessionResourceUtil.setCurrentAccountId(1121332677336698882L);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createLink() throws Exception {
        CreateLinkInput createLinkInput = new CreateLinkInput();
        createLinkInput.setFolderId(LinkConstants.FOLDER_DEFAULT_ID);
        createLinkInput.setName("百度1");
        createLinkInput.setTitle("百度1");
        createLinkInput.setUrl("http://www.baidu.com");

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/link")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(createLinkInput))
                        .header(Constants.Header_AccountId, accountId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void queryLoadstarLinks() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/link/loadstar")
                        .header(Constants.Header_AccountId, accountId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}