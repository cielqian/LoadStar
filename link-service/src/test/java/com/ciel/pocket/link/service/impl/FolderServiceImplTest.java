package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.link.service.FolderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FolderServiceImplTest {

    @Autowired
    FolderService folderService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void queryFolderTree() {
        folderService.queryFolderTree(3L, 3L);
    }
}