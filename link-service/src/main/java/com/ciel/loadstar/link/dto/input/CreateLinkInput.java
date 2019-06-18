package com.ciel.loadstar.link.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@ApiModel
@Data
public class CreateLinkInput {
    @ApiModelProperty(value = "链接地址")
    private String url;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "文件夹Id")
    private Long folderId;

    @ApiModelProperty(value = "标签")
    private ArrayList<Long> tags;

    private boolean isOften;
}