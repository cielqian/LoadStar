package com.ciel.pocket.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLink is a Querydsl query type for Link
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLink extends EntityPathBase<Link> {

    private static final long serialVersionUID = 842917460L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLink link = new QLink("link");

    public final com.ciel.pocket.infrastructure.domain.QBaseEntity _super = new com.ciel.pocket.infrastructure.domain.QBaseEntity(this);

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    public final QFolder folder;

    public final NumberPath<Long> folderId = createNumber("folderId", Long.class);

    public final StringPath icon = createString("icon");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final DateTimePath<java.util.Date> lastSeen = createDateTime("lastSeen", java.util.Date.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> sortIndex = createNumber("sortIndex", Integer.class);

    public final SetPath<Tag, QTag> tags = this.<Tag, QTag>createSet("tags", Tag.class, QTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final StringPath url = createString("url");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Integer> visitedCount = createNumber("visitedCount", Integer.class);

    public QLink(String variable) {
        this(Link.class, forVariable(variable), INITS);
    }

    public QLink(Path<? extends Link> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLink(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLink(PathMetadata metadata, PathInits inits) {
        this(Link.class, metadata, inits);
    }

    public QLink(Class<? extends Link> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folder = inits.isInitialized("folder") ? new QFolder(forProperty("folder")) : null;
    }

}

