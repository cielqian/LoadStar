package com.ciel.pocket.link.es.model;

import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/7 11:04
 */
//@Document(indexName = "link", type = "link", shards = 1, replicas = 0, refreshInterval = "-1")
@Data
public class ESLink {

    Long tableId;

    Long userId;

    String name;

    String title;

}
