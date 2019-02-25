package com.ciel.pocket.link.consumer;

import com.ciel.pocket.link.model.Folder;
import com.ciel.pocket.link.service.FolderService;
import com.netflix.discovery.converters.Auto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/25 19:16
 */
@Component
public class FolderCreateConsumer {
    @Autowired
    FolderService folderService;

    @KafkaListener(topics = "Loadstar_Folder_Creater_Dev")
    public void listen (ConsumerRecord<String, String> record) throws Exception {
        Long userId = Long.valueOf(record.value());
        Folder defaultFolder = new Folder();
        defaultFolder.setParentId(0L);
        defaultFolder.setName("未归档");
        defaultFolder.setCode("default");
        defaultFolder.setUserId(userId);
        defaultFolder.setIsSystem(true);

        Folder trashFolder = new Folder();
        trashFolder.setParentId(0L);
        trashFolder.setName("回收站");
        trashFolder.setCode("trash");
        trashFolder.setUserId(userId);
        trashFolder.setIsSystem(true);

        Folder loadStarFolder = new Folder();
        loadStarFolder.setParentId(0L);
        loadStarFolder.setName("快捷");
        loadStarFolder.setCode("loadstar");
        loadStarFolder.setUserId(userId);
        loadStarFolder.setIsSystem(true);

        folderService.create(defaultFolder);
        folderService.create(trashFolder);
        folderService.create(loadStarFolder);

//        System.out.printf("Loadstar_Folder_Creater_Dev Consumer : key = %s, value = %s \n", record.key(), record.value());
    }
}
