package com.ciel.pocket.link.dto.output;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/9/19
 * @Comment
 */
@Data
public class AnalysisLinkOutput {
    private String uri;

    private String name;

    private String title;

    private String icon;

    private String host;

    public String toString(){
        return JSON.toJSONString(this);
    }
}
