# currency
代币系统（spring-cloud项目）

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

更多详细信息见飞书文档
设计文档：https://c7nxmfumo3.feishu.cn/docx/JZG9dwxPiol7LexqdtBc9eOenXb?from=from_copylink
接口文档：https://c7nxmfumo3.feishu.cn/docx/R7ZWdNjcho6pItxwFG2c13oKnId?from=from_copylink
数据库建表语句：https://c7nxmfumo3.feishu.cn/docx/JHpudwNu9oP9ebxzzkucpO9jnid?from=from_copylink
