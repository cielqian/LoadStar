package com.ciel.pocket.link.dto.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 13:30
 */
@Data
public class FolderTreeOutput {
    private Long id;

    private String code;

    private String name;

    private boolean isSystem;

    private boolean isFolder;

    private List<FolderTreeOutput> childs;

    public FolderTreeOutput() {
        isFolder = true;
        childs = new ArrayList<>();
    }
}
