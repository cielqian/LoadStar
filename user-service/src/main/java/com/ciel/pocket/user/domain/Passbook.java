package com.ciel.pocket.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/29 11:33
 */
@Data
@TableName("passbook")
public class Passbook {
    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long linkId;

    String note;

    String username;

    String password;
}
