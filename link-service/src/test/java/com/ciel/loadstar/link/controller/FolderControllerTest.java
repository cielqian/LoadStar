package com.ciel.loadstar.link.controller;

import com.alibaba.fastjson.JSON;
import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class FolderControllerTest {

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
    public void query() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/folder/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constants.Header_AccountId, accountId)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}