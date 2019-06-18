package com.ciel.loadstar.link.es.model;

import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/7 11:04
 */
//@Document(indexName = "link", type = "link", shards = 1, replicas = 0, refreshInterval = "-1")
@Data
public class ESLink {

    String profile;

    Date createtime;

    Long tableId;

    Long userId;

    String name;

    String title;

    String url;
}
