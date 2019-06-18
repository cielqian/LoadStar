package com.ciel.loadstar.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/29 11:33
 */
@Data
@TableName("passbook")
public class Passbook extends BaseEntity {

    Long linkId;

    String note;

    String username;

    String password;

    Long userId;

    String link;
}
