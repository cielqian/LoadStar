package com.ciel.pocket.link.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ciel.pocket.link.infrastructure.ApplicationContextUtils;
import com.ciel.pocket.link.model.Folder;
import com.ciel.pocket.link.service.FolderService;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/25 19:16
 */
@Component
public class UserAccountEventConsumer {
    @Autowired
    FolderService folderService;

    @KafkaListener(topics = "${loadstar.kafka.topic.UserAccountEvent}")
    public void listen (ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String profile = jsonObject.getString("profile");
        if (!StringUtils.equals(ApplicationContextUtils.getActiveProfile(), profile))
            return;

        String event = jsonObject.getString("event");
        if (StringUtils.equals(event, "NEW")){
            Long userId = jsonObject.getLong("id");
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
        }
//        System.out.printf("Loadstar_Folder_Creater_Dev Consumer : key = %s, value = %s \n", record.key(), record.value());
    }
}
