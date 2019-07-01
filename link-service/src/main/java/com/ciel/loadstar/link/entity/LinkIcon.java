package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/27 15:18
 */
@Data
public class LinkIcon {
    @TableId
    private Long id;

    private String hostname;

    private String icon;
}
