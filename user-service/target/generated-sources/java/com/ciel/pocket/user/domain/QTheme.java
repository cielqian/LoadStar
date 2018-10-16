package com.ciel.pocket.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTheme is a Querydsl query type for Theme
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTheme extends EntityPathBase<Theme> {

    private static final long serialVersionUID = -1074509186L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTheme theme = new QTheme("theme");

    public final com.ciel.pocket.infrastructure.domain.QBaseEntity _super = new com.ciel.pocket.infrastructure.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final EnumPath<com.ciel.pocket.infrastructure.enums.Language> language = createEnum("language", com.ciel.pocket.infrastructure.enums.Language.class);

    public final EnumPath<com.ciel.pocket.user.infrastructure.enums.ListTypeEnum> listTypeEnum = createEnum("listTypeEnum", com.ciel.pocket.user.infrastructure.enums.ListTypeEnum.class);

    public final StringPath modules = createString("modules");

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final QUser user;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QTheme(String variable) {
        this(Theme.class, forVariable(variable), INITS);
    }

    public QTheme(Path<? extends Theme> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTheme(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTheme(PathMetadata metadata, PathInits inits) {
        this(Theme.class, metadata, inits);
    }

    public QTheme(Class<? extends Theme> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

