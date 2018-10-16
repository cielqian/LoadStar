package com.ciel.pocket.user.domain;

import com.ciel.pocket.user.infrastructure.enums.ThemeModuleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/16 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeModule {
    ThemeModuleEnum module;

    boolean isShow;


}
