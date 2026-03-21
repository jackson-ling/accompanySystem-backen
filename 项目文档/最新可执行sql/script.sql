create table hospital
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    name        varchar(100)                       not null comment '医院名称',
    address     varchar(255)                       null comment '地址',
    level       varchar(20)                        null comment '医院等级: 三甲/三乙/二甲/二乙/一级',
    phone       varchar(20)                        null comment '联系电话',
    status      tinyint  default 1                 null comment '状态: 0-禁用, 1-正常',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '医院表' charset = utf8mb4;

create table department
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    name        varchar(50)                        not null comment '科室名称',
    hospital_id bigint                             null comment '医院ID',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_department_hospital
        foreign key (hospital_id) references hospital (id)
)
    comment '科室表' charset = utf8mb4;

create index idx_hospital_id
    on department (hospital_id);

create index idx_name
    on hospital (name);

create table service_category
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    name        varchar(50)                        not null comment '分类名称',
    icon        varchar(500)                       null comment '图标',
    sort        int      default 0                 null comment '排序',
    status      tinyint  default 1                 null comment '状态: 0-禁用, 1-正常',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '服务分类表' charset = utf8mb4;

create table service_item
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    category_id bigint                             null comment '分类ID',
    name        varchar(100)                       not null comment '服务名称',
    description text                               null comment '服务描述',
    features    json                               null comment '服务特点(JSON数组)',
    price       decimal(10, 2)                     not null comment '价格',
    type        varchar(50)                        null comment '服务类型: companion-陪诊, agency-代办',
    sales       int      default 0                 null comment '销量',
    image       varchar(500)                       null comment '图片',
    cover       varchar(500)                       null comment '封面图',
    duration    varchar(50)                        null comment '服务时长',
    status      tinyint  default 1                 null comment '状态: 0-下架, 1-上架',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '服务项目表' charset = utf8mb4;

create index idx_category_id
    on service_item (category_id);

create index idx_status
    on service_item (status);

create table sys_user
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    nickname    varchar(50)                              null comment '昵称',
    avatar      varchar(500)                             null comment '头像URL',
    phone       varchar(20)                              not null comment '手机号',
    password    varchar(255)                             not null comment '密码(加密)',
    balance     decimal(10, 2) default 0.00              null comment '账户余额',
    user_type   tinyint        default 1                 null comment '用户类型: 1-普通用户, 2-陪诊师, 3-管理员',
    gender      tinyint                                  null comment '性别: 0-未知, 1-男, 2-女',
    birthday    date                                     null comment '生日',
    status      tinyint        default 1                 null comment '状态: 0-禁用, 1-正常',
    create_time datetime       default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime                                 null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint phone
        unique (phone)
)
    comment '用户表' charset = utf8mb4;

create table ai_chat_history
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    user_id     bigint   not null comment '用户ID',
    message     text     not null comment '用户消息',
    reply       text     not null comment 'AI回复',
    suggestions json     null comment '建议问题(JSON数组)',
    time        datetime null comment '聊天时间',
    constraint fk_ai_chat_user
        foreign key (user_id) references sys_user (id)
            on delete cascade
)
    comment 'AI聊天记录表' charset = utf8mb4;

create index idx_user_id
    on ai_chat_history (user_id);

create table companion_profile
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    user_id     bigint                                  not null comment '关联用户ID',
    name        varchar(50)                             not null comment '姓名',
    avatar      varchar(500)                            null comment '头像URL',
    gender      varchar(10)                             null comment '性别',
    age         int                                     null comment '年龄',
    score       decimal(3, 2) default 5.00              null comment '评分(0-5)',
    orders      int           default 0                 null comment '总接单数',
    comments    int           default 0                 null comment '评论数',
    certified   tinyint       default 0                 null comment '是否认证: 0-否, 1-是',
    qualified   tinyint       default 0                 null comment '是否有资质: 0-否, 1-是',
    experience  varchar(50)                             null comment '工作年限',
    tags        json                                    null comment '标签数组',
    services    json                                    null comment '服务类别ID数组',
    intro       text                                    null comment '个人简介',
    is_online   tinyint       default 0                 null comment '是否在线: 0-否, 1-是',
    create_time datetime      default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime                                null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint user_id
        unique (user_id),
    constraint fk_companion_user
        foreign key (user_id) references sys_user (id)
            on delete cascade
)
    comment '陪诊师信息表' charset = utf8mb4;

create index idx_user_id
    on companion_profile (user_id);

create table companion_statistics
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    user_id      bigint                      not null comment '陪诊师用户ID',
    today_income decimal(10, 2) default 0.00 null comment '今日收入',
    today_orders int            default 0    null comment '今日订单数',
    month_income decimal(10, 2) default 0.00 null comment '本月收入',
    month_orders int            default 0    null comment '本月订单数',
    total_income decimal(10, 2) default 0.00 null comment '总收入',
    total_orders int            default 0    null comment '总订单数',
    score        decimal(3, 2)  default 5.00 null comment '评分',
    followers    int            default 0    null comment '粉丝数',
    work_days    int            default 0    null comment '工作天数',
    update_time  datetime                    null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint user_id
        unique (user_id),
    constraint fk_statistics_user
        foreign key (user_id) references sys_user (id)
            on delete cascade
)
    comment '陪诊师统计表' charset = utf8mb4;

create index idx_user_id
    on companion_statistics (user_id);

create table favorite
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    user_id     bigint       not null comment '用户ID',
    item_id     bigint       not null comment '收藏项ID',
    name        varchar(50)  null comment '名称',
    avatar      varchar(500) null comment '头像',
    description varchar(500) null comment '描述',
    time        datetime     null comment '收藏时间',
    constraint uk_user_item
        unique (user_id, item_id),
    constraint fk_favorite_user
        foreign key (user_id) references sys_user (id)
            on delete cascade
)
    comment '收藏表' charset = utf8mb4;

create index idx_user_id
    on favorite (user_id);

create table feedback
(
    id         bigint auto_increment comment '主键ID'
        primary key,
    user_id    bigint                        not null comment '用户ID',
    content    text                          not null comment '反馈内容',
    images     json                          null comment '图片(JSON数组)',
    contact    varchar(50)                   null comment '联系方式',
    status     varchar(20) default 'pending' null comment '状态: pending-待处理, processed-已处理',
    reply      text                          null comment '回复内容',
    reply_time datetime                      null comment '回复时间',
    time       datetime                      null comment '提交时间',
    constraint fk_feedback_user
        foreign key (user_id) references sys_user (id)
)
    comment '反馈表' charset = utf8mb4;

create index idx_status
    on feedback (status);

create index idx_user_id
    on feedback (user_id);

create table message_conversation
(
    id                bigint auto_increment comment '主键ID'
        primary key,
    user_id           bigint                             not null comment '用户ID',
    type              varchar(20)                        not null comment '会话类型: customer_service-客服, companion-陪诊师',
    target_id         bigint                             null comment '目标ID(客服ID或陪诊师ID)',
    name              varchar(50)                        null comment '名称',
    avatar            varchar(500)                       null comment '头像',
    last_message      varchar(255)                       null comment '最后一条消息',
    last_message_time datetime                           null comment '最后消息时间',
    unread_count      int      default 0                 null comment '未读数',
    create_time       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time       datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_conversation_user
        foreign key (user_id) references sys_user (id)
            on delete cascade
)
    comment '消息会话表' charset = utf8mb4;

create table chat_message
(
    id              bigint auto_increment comment '主键ID'
        primary key,
    conversation_id bigint                     not null comment '会话ID',
    user_id         bigint                     not null comment '发送者ID',
    text            text                       not null comment '消息内容',
    is_me           tinyint     default 0      null comment '是否我发送: 0-否, 1-是',
    type            varchar(20) default 'text' null comment '消息类型: text/image/voice',
    time            datetime                   null comment '发送时间',
    constraint fk_message_conversation
        foreign key (conversation_id) references message_conversation (id)
            on delete cascade,
    constraint fk_message_user
        foreign key (user_id) references sys_user (id)
)
    comment '聊天消息表' charset = utf8mb4;

create index idx_conversation_id
    on chat_message (conversation_id);

create index idx_user_id
    on chat_message (user_id);

create index idx_target_id
    on message_conversation (target_id);

create index idx_user_id
    on message_conversation (user_id);

create table patient
(
    id              bigint auto_increment comment '主键ID'
        primary key,
    user_id         bigint                             not null comment '用户ID',
    name            varchar(50)                        not null comment '就诊人姓名',
    phone           varchar(20)                        null comment '手机号',
    id_card         varchar(20)                        null comment '身份证号',
    gender          tinyint                            null comment '性别: 0-未知, 1-男, 2-女',
    age             int                                null comment '年龄',
    address         varchar(255)                       null comment '地址',
    relationship    varchar(20)                        null comment '与用户关系(本人/父母/配偶/子女/其他)',
    medical_card_no varchar(50)                        null comment '就诊卡号',
    is_default      tinyint  default 0                 null comment '是否默认: 0-否, 1-是',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_patient_user
        foreign key (user_id) references sys_user (id)
            on delete cascade
)
    comment '就诊人表' charset = utf8mb4;

create table orders
(
    id               bigint auto_increment comment '主键ID'
        primary key,
    order_no         varchar(32)                        not null comment '订单编号(唯一)',
    user_id          bigint                             not null comment '用户ID',
    companion_id     bigint                             null comment '陪诊师ID(可为空,抢单前为空)',
    patient_id       bigint                             not null comment '就诊人ID',
    service_id       bigint                             not null comment '服务项目ID',
    service_name     varchar(100)                       not null comment '服务名称',
    service_image    varchar(500)                       null comment '服务图片',
    hospital         varchar(100)                       null comment '医院',
    department       varchar(50)                        null comment '科室',
    appointment_time datetime                           null comment '预约时间',
    pickup_option    tinyint  default 1                 null comment '接人方式: 1-医院门口, 2-指定地点',
    pickup_address   varchar(255)                       null comment '接人地址',
    remarks          varchar(500)                       null comment '备注',
    amount           decimal(10, 2)                     not null comment '订单金额',
    actual_amount    decimal(10, 2)                     null comment '实付金额',
    pay_method       tinyint                            null comment '支付方式: 1-微信, 2-支付宝, 3-余额',
    pay_time         datetime                           null comment '支付时间',
    status           tinyint  default 0                 null comment '订单状态: 0-待支付, 1-待接单, 2-已接单, 3-已完成, 4-已取消, 5-退款中, 6-已退款,7-服务中',
    refund_reason    varchar(500)                       null comment '退款原因',
    refund_time      datetime                           null comment '退款时间',
    complete_time    datetime                           null comment '完成时间',
    create_time      datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time      datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint order_no
        unique (order_no),
    constraint fk_orders_companion
        foreign key (companion_id) references companion_profile (id)
            on delete set null,
    constraint fk_orders_patient
        foreign key (patient_id) references patient (id),
    constraint fk_orders_service
        foreign key (service_id) references service_item (id),
    constraint fk_orders_user
        foreign key (user_id) references sys_user (id)
)
    comment '订单表' charset = utf8mb4;

create table companion_income
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    user_id      bigint                                not null comment '陪诊师用户ID',
    order_id     bigint                                not null comment '订单ID',
    order_no     varchar(32)                           null comment '订单编号',
    service_name varchar(100)                          null comment '服务名称',
    amount       decimal(10, 2)                        not null comment '收入金额',
    status       varchar(20) default 'pending'         null comment '状态: pending-待结算, completed-已结算, cancelled-已取消',
    create_time  datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    settle_time  datetime                              null comment '结算时间',
    constraint fk_income_order
        foreign key (order_id) references orders (id),
    constraint fk_income_user
        foreign key (user_id) references sys_user (id)
)
    comment '陪诊师收入记录表' charset = utf8mb4;

create index idx_order_id
    on companion_income (order_id);

create index idx_user_id
    on companion_income (user_id);

create table order_review
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    order_id     bigint        not null comment '订单ID',
    user_id      bigint        not null comment '用户ID',
    companion_id bigint        not null comment '陪诊师ID',
    user_name    varchar(50)   null comment '用户名',
    user_avatar  varchar(500)  null comment '用户头像',
    score        decimal(3, 2) not null comment '评分(1-5)',
    content      text          null comment '评价内容',
    images       json          null comment '图片(JSON数组)',
    time         datetime      null comment '评价时间',
    constraint order_id
        unique (order_id),
    constraint fk_review_companion
        foreign key (companion_id) references companion_profile (id),
    constraint fk_review_order
        foreign key (order_id) references orders (id)
            on delete cascade,
    constraint fk_review_user
        foreign key (user_id) references sys_user (id)
)
    comment '订单评价表' charset = utf8mb4;

create index idx_companion_id
    on order_review (companion_id);

create index idx_order_id
    on order_review (order_id);

create index idx_user_id
    on order_review (user_id);

create index idx_appointment_time
    on orders (appointment_time);

create index idx_companion_id
    on orders (companion_id);

create index idx_create_time
    on orders (create_time);

create index idx_order_no
    on orders (order_no);

create index idx_status
    on orders (status);

create index idx_user_id
    on orders (user_id);

create index idx_user_id
    on patient (user_id);

create table recharge_record
(
    id            bigint auto_increment comment '主键ID'
        primary key,
    user_id       bigint                             not null comment '用户ID',
    order_no      varchar(32)                        not null comment '充值订单号',
    amount        decimal(10, 2)                     not null comment '充值金额',
    method        varchar(20)                        null comment '支付方式: wechat-微信, alipay-支付宝',
    pay_status    tinyint  default 0                 null comment '支付状态: 0-待支付, 1-支付成功, 2-支付失败',
    complete_time datetime                           null comment '完成时间',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint order_no
        unique (order_no),
    constraint fk_recharge_user
        foreign key (user_id) references sys_user (id)
)
    comment '充值记录表' charset = utf8mb4;

create index idx_order_no
    on recharge_record (order_no);

create index idx_user_id
    on recharge_record (user_id);

create index idx_phone
    on sys_user (phone);

create index idx_user_type
    on sys_user (user_type);

create table transaction_record
(
    id       bigint auto_increment comment '主键ID'
        primary key,
    user_id  bigint         not null comment '用户ID',
    type     varchar(20)    null comment '类型: recharge-充值, consume-消费, refund-退款',
    title    varchar(100)   null comment '标题',
    amount   decimal(10, 2) not null comment '金额(正数)',
    balance  decimal(10, 2) not null comment '余额',
    order_no varchar(32)    null comment '关联订单号',
    time     datetime       null comment '时间',
    constraint fk_transaction_user
        foreign key (user_id) references sys_user (id)
)
    comment '消费记录表' charset = utf8mb4;

create index idx_type
    on transaction_record (type);

create index idx_user_id
    on transaction_record (user_id);


