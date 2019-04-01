package com.ciel.pocket.link.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/27 15:18
 */
@Data
public class LinkIcon {
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    private String  hostname;

    private String icon;
}