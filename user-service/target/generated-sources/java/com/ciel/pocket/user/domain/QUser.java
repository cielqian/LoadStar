package com.ciel.pocket.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 935210102L;

    public static final QUser user = new QUser("user");

    public final com.ciel.pocket.infrastructure.domain.QBaseEntity _super = new com.ciel.pocket.infrastructure.domain.QBaseEntity(this);

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final DateTimePath<java.util.Date> lastSeen = createDateTime("lastSeen", java.util.Date.class);

    public final StringPath nickname = createString("nickname");

    public final ListPath<Theme, QTheme> themes = this.<Theme, QTheme>createList("themes", Theme.class, QTheme.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

