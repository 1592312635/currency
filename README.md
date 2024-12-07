# currency
代币系统（简单项目）

项目介绍：
    虚拟货币系统，支持自定义

项目功能：
    基础功能——包含代币发放、代币扣减、代币账户查询、代币流水查询等
    进阶功能——自定义代币类型、自定义代币有效期、代币扣减确认、代币回退、代币过期等

微服务划分：
    currency-capi：c端核心微服务，用于接受外部请求处理代币toC相关业务（代币发放、扣减、查询等核心业务）
    currency-mapi：m端核心微服务，用于接受内部请求处理代币toB相关业务（新增币种、设置代币币种有效期等后台业务）
    currency-crond：定时任务微服务，用于定时处理代币有关数据（代币过期、代币订单超时等）
    common：公共层，存储对象、工具、异常等公共元素
    dao：sql层用于数据库交互
    附：项目可启动服务为——currency-capi、currency-mapi、currency-crond，其中currency-mapi服务功能可通过sql执行实现，故俺暂时懒得写

SQL语句：
    -- 代币账户表
    CREATE TABLE `currency_account` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` varchar(32) NOT NULL COMMENT '用户注册id',
    `currency` double(10,2) NOT NULL COMMENT '余额',
    `currency_type` int(4) NOT NULL DEFAULT '1' COMMENT '代币币种',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_tag` int(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
    UNIQUE KEY `currency_account_id_IDX` (`id`) USING BTREE,
    KEY `currency_account_user_id_IDX` (`user_id`,`currency_type`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=4013 DEFAULT CHARSET=utf8mb4;
    -- 代币幂等表
    CREATE TABLE `currency_idempotent` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `business_id` varchar(32) NOT NULL COMMENT '业务流水号',
    `behavior_code` varchar(64) NOT NULL COMMENT '行为编码',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_tag` int(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
    UNIQUE KEY `currency_idempotent_id_IDX` (`id`) USING BTREE,
    KEY `currency_idempotent_business_id_IDX` (`business_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=94530 DEFAULT CHARSET=utf8mb4;
    -- 代币订单表
    CREATE TABLE `currency_order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` varchar(32) NOT NULL COMMENT '用户注册id',
    `order_no` varchar(64) NOT NULL COMMENT '订单号',
    `amount` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
    `fail_amount` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '失败金额',
    `expire_amount` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '过期金额',
    `status` int(4) NOT NULL DEFAULT '0' COMMENT '订单状态',
    `currency_type` int(4) NOT NULL DEFAULT '1' COMMENT '代币币种',
    `handle_type` int(4) NOT NULL DEFAULT '1' COMMENT '处理标识',
    `behavior_code` varchar(64) NOT NULL COMMENT '行为标识',
    `expire_time` timestamp NULL DEFAULT NULL COMMENT '到期时间',
    `schedule_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '调度时间',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_tag` int(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
    UNIQUE KEY `currency_order_id_IDX` (`id`) USING BTREE,
    KEY `currency_order_user_id_IDX` (`user_id`,`order_no`,`status`,`currency_type`,`handle_type`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=79725 DEFAULT CHARSET=utf8mb4;
    -- 代币币种规则表
    CREATE TABLE `currency_rule` (
    `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `currency_type` int(11) NOT NULL COMMENT '代币类型',
    `currency_type_desc` varchar(128) NOT NULL COMMENT '代币类型描述',
    `effective_type` int(4) NOT NULL COMMENT '有效期类型',
    `effective_cycle` int(4) DEFAULT NULL COMMENT '生效周期',
    `effective_span` int(11) DEFAULT NULL COMMENT '生效域',
    `expire_cycle` int(4) DEFAULT NULL COMMENT '失效周期',
    `expire_span` int(11) DEFAULT NULL COMMENT '失效域',
    `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
    `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_tag` int(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
    UNIQUE KEY `currency_rule_id_IDX` (`id`) USING BTREE,
    KEY `currency_rule_currency_type_IDX` (`currency_type`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
    -- 代币流水表
    CREATE TABLE `currency_serial` (
    `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `business_id` varchar(32) NOT NULL COMMENT '业务流水号',
    `rollback_business_id` varchar(32) DEFAULT NULL COMMENT '回退流水号',
    `user_id` varchar(32) NOT NULL COMMENT '用户注册id',
    `behavior_code` varchar(32) NOT NULL COMMENT '行为标识',
    `behavior_desc` varchar(64) NOT NULL COMMENT '行为描述',
    `currency_type` int(4) NOT NULL DEFAULT '1' COMMENT '代币类型',
    `handle_type` int(4) NOT NULL DEFAULT '1' COMMENT '处理类型',
    `amount` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_tag` int(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
    UNIQUE KEY `currency_serial_id_IDX` (`id`) USING BTREE,
    KEY `currency_serial_user_id_IDX` (`user_id`,`currency_type`,`handle_type`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=120005 DEFAULT CHARSET=utf8mb4;
    --初始化代币规则（默认永久有效期货币）
    INSERT INTO currency_rule (currency_type,currency_type_desc,effective_type)
    VALUES (1,'测试货币',0);


接口测试：
1、发放接口
    接口名：/account/send
    入参：
    {
        "userId": "555555",
        "addCurrency": 10,
        "currencyType": 1,
        "businessId": 1433522190866,
        "behaviorCode": "sendCode",
        "behaviorDesc": "测试发放奖励"
    }
2、扣减接口
    接口名：/account/deduct
    入参：
    {
        "userId": "555555",
        "deductCurrency": 1,
        "currencyType": 1,
        "businessId": 928853471473,
        "behaviorCode": "deductCode",
        "behaviorDesc": "测试代币扣减"
    }
3、余额查询
    接口名：/account/get
    入参：
    {
        "userId": "555555",
        "currencyType": 1
    }
    出参：
    {
        "code": "200",
        "message": "成功",
        "data": {
            "currency": 0,
            "currencyType": 1
        }
    }


更多详细信息见飞书文档（飞书文档默认权限需要申请，人在国外飞书不常开，急+v：hmy_990528）
设计文档：https://c7nxmfumo3.feishu.cn/docx/TEFrdTtEhoX7uUxjkg7ccKnLnbg?from=from_copylink
接口文档：https://c7nxmfumo3.feishu.cn/docx/R7ZWdNjcho6pItxwFG2c13oKnId?from=from_copylink
数据库建表语句：https://c7nxmfumo3.feishu.cn/docx/JHpudwNu9oP9ebxzzkucpO9jnid?from=from_copylink
