package com.ciel.pocket.link.infrastructure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/1/9 14:15
 */
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createTime", new Date(), metaObject);
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
    }
}
