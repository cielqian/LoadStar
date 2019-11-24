package com.ciel.loadstar.link.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/25 19:16
 */
@Component
@Slf4j
public class AccountEventConsumer {
//    @Autowired
//    FolderService folderService;
//
//    @Value("${loadstar.mq.host}")
//    String mqHost;
//
//    @Value("${spring.application.name}")
//    String topicGroupName;
//
//    @Value("${loadstar.mq.topic.AccountEvent}")
//    private String mqAccountEventTopic;
//
//    @PostConstruct
//    public void consume() throws Exception {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(topicGroupName);
//        consumer.setNamesrvAddr(mqHost);
//        consumer.subscribe(mqAccountEventTopic, "*");
//        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
//            for (MessageExt msg: msgs){
//                String bodyContent = "";
//                try {
//                    bodyContent = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
//                } catch (UnsupportedEncodingException e) {
//                    log.warn("received message error, messageId [{}]", msg.getMsgId());
//                }
//
//                JSONObject jsonObject = JSONObject.parseObject(bodyContent);
//                AccountEvent userAccountEvent = jsonObject.toJavaObject(AccountEvent.class);
//                String profile = userAccountEvent.getProfile();
//                if (!StringUtils.equals(ApplicationContextUtil.getActiveProfile(), profile)){
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//
//                String eventType = userAccountEvent.getEventType();
//                if (StringUtils.equals(eventType, AccountEventType.CREATE)){
//                    Long userId = Long.parseLong(userAccountEvent.getId());
//                    Folder defaultFolder = new Folder();
//                    defaultFolder.setParentId(0L);
//                    defaultFolder.setName("未归档");
//                    defaultFolder.setCode("default");
//                    defaultFolder.setUserId(userId);
//                    defaultFolder.setIsSystem(true);
//
//                    Folder trashFolder = new Folder();
//                    trashFolder.setParentId(0L);
//                    trashFolder.setName("回收站");
//                    trashFolder.setCode("trash");
//                    trashFolder.setUserId(userId);
//                    trashFolder.setIsSystem(true);
//
//                    Folder loadStarFolder = new Folder();
//                    loadStarFolder.setParentId(0L);
//                    loadStarFolder.setName("快捷");
//                    loadStarFolder.setCode("loadstar");
//                    loadStarFolder.setUserId(userId);
//                    loadStarFolder.setIsSystem(true);
//
//                    folderService.create(defaultFolder);
//                    folderService.create(trashFolder);
//                    folderService.create(loadStarFolder);
//
//                    log.info("create default folder for user [{}]", userId);
//
//                }
//            }
//
//
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });
//    }
}
