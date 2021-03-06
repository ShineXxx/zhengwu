# zhengwu

## 服务模块：

### 协助模块：
#### 描述：
> 1.登陆相关（登陆可配置-配置模块交互）
> 2.用户顺序填写表单
> 3.系统根据表单数据结合材料模板，生成word/pdf
> 4.根据配置选择：直接归档材料或提交到审批模块
> 5.若提交到审批模块，则进行结果的展示和报告生成逻辑
#### 功能点：
* html转word

### 审批模块：
#### 描述：
> 对接协助模块的输入，将其转化为标准格式，通过MQ和算法模块进行交互，最后保存格式化后的结果，供多种前端查询
#### 功能点：
* 1.通过Spring状态机实现办理流程的状态流转
* 2.通过RabbitMQ的主题模式，实现算法和Java审批模块的交互（线程池）（异常处理）(可靠投递+可靠消费+消息持久化)
* 3.通过MongoDB保存算法处理结果（嵌套层数较深，适合文档类型结构化查询）
* 4.MySQL进行索引优化（从建好索引和防止索引失效两个方面）
* 5.引入Redis保存临时数据，设置过期时间（去掉前版本的队列）
* 6.基于动态定时任务Quartz及持久化，实现可靠回调通知（处理算法结果成功 回调通知频率 15s/15s/30s/3m/10m/20m/30m/30m/30m/60m/3h/3h/3h/6h/6h）
* 7.支持任务分片（从之前的完整任务变成分片任务）（DAG任务编排）
* 8.查询结果通过策略模式实现
* 9.oauth静默授权
* 10.服务注册中心实现负载均衡


### 算法模块：
#### 描述：
> 对材料进行 图片OCR+智能分类+信息提取+规则引擎 ，仅通过MQ和审批模块进行交互
#### 功能点：
* 1.开源OCR
* 2.自实现规则引擎
* 3.实现任务可中断

### 配置模块：
#### 描述：
> 提供配置查询，配置第三方接口，控制页面材料展示数量和形式等
#### 功能点：
* 增删查改

### 数据模块：
#### 描述：
> 提供基础数据支持（其他模块只读）
功能点：
1.解析json，入库（MySQL & MongoDB）
2.通过flyway实现数据库的版本控制

### 部署：
* k8s+docker